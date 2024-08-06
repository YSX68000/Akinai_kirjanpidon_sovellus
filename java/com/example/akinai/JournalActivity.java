package com.example.akinai;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JournalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JournalAdapter journalAdapter;
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private Spinner spinnerCategory;
    private String userId;
    private String currentCategory = ""; // 現在表示しているカテゴリを保持

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journalAdapter = new JournalAdapter(journalEntries);
        recyclerView.setAdapter(journalAdapter);

        spinnerCategory = findViewById(R.id.spinnerCategory);

        // FirebaseHelperのインスタンスを初期化
        databaseReference = FirebaseDatabase.getInstance().getReference("journals");

        // 現在のユーザーのUIDを取得
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
            fetchCategories();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCategories() {
        if (userId == null) return;

        final Set<String> categories = new HashSet<>();

        // "expenses"ノードから勘定科目（category）のリストを取得
        DatabaseReference expensesRef = databaseReference.child(userId).child("expenses");
        expensesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getKey();
                    if (category != null) {
                        categories.add(category);
                    }
                }
                setupSpinner(new ArrayList<>(categories));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JournalActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });

        // "revenues"ノードから勘定科目（category）のリストを取得
        DatabaseReference revenuesRef = databaseReference.child(userId).child("revenues");
        revenuesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getKey();
                    if (category != null) {
                        categories.add(category);
                    }
                }
                setupSpinner(new ArrayList<>(categories));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JournalActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinner(final List<String> categories) {
        // プルダウンメニューにカテゴリーリストを設定
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(JournalActivity.this,
                android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // プルダウン選択時のリスナーを設定
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                if (!selectedCategory.equals(currentCategory)) { // 新しいカテゴリが選択された場合のみ処理
                    currentCategory = selectedCategory;
                    fetchJournalEntries(selectedCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });
    }

    private void fetchJournalEntries(final String category) {
        if (userId == null) return;

        // journalEntriesをクリア
        journalEntries.clear();

        // "expenses"ノードからジャーナルエントリを取得
        fetchEntriesForCategory("expenses", category);

        // "revenues"ノードからジャーナルエントリを取得
        fetchEntriesForCategory("revenues", category);
    }

    private void fetchEntriesForCategory(String type, final String category) {
        databaseReference.child(userId).child(type).child(category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                            String date = dateSnapshot.getKey();
                            for (DataSnapshot timestampSnapshot : dateSnapshot.getChildren()) {
                                String timestamp = timestampSnapshot.getKey();
                                Double amount = timestampSnapshot.child("amount").getValue(Double.class);
                                if (amount != null) {
                                    journalEntries.add(new JournalEntry(timestamp, category, amount));
                                }
                            }
                        }
                        journalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(JournalActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
