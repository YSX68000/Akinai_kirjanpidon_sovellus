package com.example.akinai;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProfitAndLossActivity extends AppCompatActivity {

    private TextView totalRevenueTextView, totalExpenseTextView, netProfitTextView;
    private RecyclerView revenueRecyclerView, expenseRecyclerView;
    private ProfitAndLossAdapter revenueAdapter, expenseAdapter;
    private List<ProfitAndLossEntry> revenueEntries = new ArrayList<>();
    private List<ProfitAndLossEntry> expenseEntries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private String userId;

    private Button startDateButton, endDateButton, calculateButton;
    private Calendar startDate, endDate;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_and_loss);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        totalRevenueTextView = findViewById(R.id.totalRevenueTextView);
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView);
        netProfitTextView = findViewById(R.id.netProfitTextView);
        revenueRecyclerView = findViewById(R.id.revenueRecyclerView);
        expenseRecyclerView = findViewById(R.id.expenseRecyclerView);
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        calculateButton = findViewById(R.id.calculateButton);

        revenueRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        revenueAdapter = new ProfitAndLossAdapter(revenueEntries);
        expenseAdapter = new ProfitAndLossAdapter(expenseEntries);
        revenueRecyclerView.setAdapter(revenueAdapter);
        expenseRecyclerView.setAdapter(expenseAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("journals");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDate, startDateButton);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDate, endDateButton);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFinancialData();
            }
        });
    }

    private void showDatePickerDialog(final Calendar date, final Button button) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                button.setText(dateFormat.format(date.getTime()));
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void fetchFinancialData() {
        if (userId == null) return;

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double totalRevenue = 0.0;
                double totalExpense = 0.0;

                Map<String, Double> revenueTotals = new HashMap<>();
                Map<String, Double> expenseTotals = new HashMap<>();

                for (DataSnapshot typeSnapshot : dataSnapshot.getChildren()) {
                    String type = typeSnapshot.getKey();

                    for (DataSnapshot accountSnapshot : typeSnapshot.getChildren()) {
                        String account = accountSnapshot.getKey();

                        double accountTotal = 0.0;

                        for (DataSnapshot dateSnapshot : accountSnapshot.getChildren()) {
                            String dateStr = dateSnapshot.getKey();
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                long dateLong = sdf.parse(dateStr).getTime();

                                if (dateLong >= startDate.getTimeInMillis() && dateLong <= endDate.getTimeInMillis()) {
                                    for (DataSnapshot timestampSnapshot : dateSnapshot.getChildren()) {
                                        Double amount = timestampSnapshot.child("amount").getValue(Double.class);

                                        if (amount != null) {
                                            accountTotal += amount;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (type.equals("revenues")) {
                            revenueTotals.put(account, revenueTotals.getOrDefault(account, 0.0) + accountTotal);
                            totalRevenue += accountTotal;
                        } else if (type.equals("expenses")) {
                            expenseTotals.put(account, expenseTotals.getOrDefault(account, 0.0) + accountTotal);
                            totalExpense += accountTotal;
                        }
                    }
                }

                revenueEntries.clear();
                expenseEntries.clear();

                for (Map.Entry<String, Double> entry : revenueTotals.entrySet()) {
                    revenueEntries.add(new ProfitAndLossEntry(entry.getKey(), entry.getValue()));
                }

                for (Map.Entry<String, Double> entry : expenseTotals.entrySet()) {
                    expenseEntries.add(new ProfitAndLossEntry(entry.getKey(), entry.getValue()));
                }

                revenueAdapter.notifyDataSetChanged();
                expenseAdapter.notifyDataSetChanged();

                totalRevenueTextView.setText("Tulot Yhteensä: " + totalRevenue + " €");
                totalExpenseTextView.setText("Menot Yhteensä: " + totalExpense + " €");
                netProfitTextView.setText("Nettosumma: " + (totalRevenue - totalExpense) + " €");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfitAndLossActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
