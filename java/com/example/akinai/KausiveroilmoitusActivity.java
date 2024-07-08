package com.example.akinai;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KausiveroilmoitusActivity extends AppCompatActivity {

    private TextView totalTaxPurchaseTextView, totalTaxSalesTextView, netTaxTextView;
    private RecyclerView purchaseRecyclerView, salesRecyclerView;
    private TaxAdapter purchaseAdapter, salesAdapter;
    private List<TaxEntry> purchaseEntries = new ArrayList<>();
    private List<TaxEntry> salesEntries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private String userId;
    private TextView startDateTextView, endDateTextView;
    private Button startDateButton, endDateButton, fetchDataButton;
    private Calendar startDate, endDate;

    private static final String TAG = "KausiveroilmoitusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kausiveroilmoitus);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        totalTaxPurchaseTextView = findViewById(R.id.totalTaxPurchaseTextView);
        totalTaxSalesTextView = findViewById(R.id.totalTaxSalesTextView);
        netTaxTextView = findViewById(R.id.netTaxTextView);
        purchaseRecyclerView = findViewById(R.id.purchaseRecyclerView);
        salesRecyclerView = findViewById(R.id.salesRecyclerView);

        /*startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);*/
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        fetchDataButton = findViewById(R.id.fetchDataButton);

        purchaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchaseAdapter = new TaxAdapter(purchaseEntries);
        salesAdapter = new TaxAdapter(salesEntries);
        purchaseRecyclerView.setAdapter(purchaseAdapter);
        salesRecyclerView.setAdapter(salesAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("journals");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(v -> showDatePickerDialog(startDate, startDateButton));
        endDateButton.setOnClickListener(v -> showDatePickerDialog(endDate, endDateButton));
        fetchDataButton.setOnClickListener(v -> fetchTaxData());
    }

    private void showDatePickerDialog(final Calendar calendar, final Button button) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            calendar.set(year1, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            button.setText(sdf.format(calendar.getTime()));
        }, year, month, day);

        datePickerDialog.show();
    }

    private void fetchTaxData() {
        if (userId == null) return;

        databaseReference.child(userId).child("tax").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double totalTaxPurchase = 0.0;
                double totalTaxSales = 0.0;
                purchaseEntries.clear();
                salesEntries.clear();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                for (DataSnapshot typeSnapshot : dataSnapshot.getChildren()) {
                    String type = typeSnapshot.getKey();

                    if (type == null) {
                        Log.d(TAG, "Type is null for snapshot: " + typeSnapshot);
                        continue;
                    }

                    for (DataSnapshot dateSnapshot : typeSnapshot.getChildren()) {
                        try {
                            Date date = sdf.parse(dateSnapshot.getKey());
                            if (date != null && (date.after(startDate.getTime()) || date.equals(startDate.getTime())) &&
                                    (date.before(endDate.getTime()) || date.equals(endDate.getTime()))) {

                                for (DataSnapshot entrySnapshot : dateSnapshot.getChildren()) {
                                    Double amount = entrySnapshot.child("amount").getValue(Double.class);

                                    if (amount != null) {
                                        if (type.equals("purchases")) {
                                            purchaseEntries.add(new TaxEntry(amount));
                                            totalTaxPurchase += amount;
                                        } else if (type.equals("sales")) {
                                            salesEntries.add(new TaxEntry(amount));
                                            totalTaxSales += amount;
                                        }
                                    } else {
                                        Log.d(TAG, "Amount is null for entry: " + entrySnapshot.getKey());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Date parsing error: " + e.getMessage());
                        }
                    }
                }

                purchaseAdapter.notifyDataSetChanged();
                salesAdapter.notifyDataSetChanged();

                totalTaxPurchaseTextView.setText("Ostosta arvonlisäverot: " + totalTaxPurchase + " €");
                totalTaxSalesTextView.setText("Myynista arvonlisäverot: " + totalTaxSales + " €");
                netTaxTextView.setText("Maksettava/Palautettava arvonlisäverot: " + (totalTaxSales - totalTaxPurchase) + " €");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(KausiveroilmoitusActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
