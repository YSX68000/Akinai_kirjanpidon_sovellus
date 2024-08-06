package com.example.akinai;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseHelper {
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "JournalPreferences";
    private static final String KEY_PREFIX = "Journal_";

    public FirebaseHelper(Context context) {
        // Firebase Realtime Databaseのルートリファレンスを取得
        databaseReference = FirebaseDatabase.getInstance().getReference("journals");
        // SharedPreferencesの初期化
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // 日付フォーマットメソッド
    private String getFormattedTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    // SharedPreferencesから次の連番を取得し、インクリメントして保存するメソッド
    private int getNextId(String category) {
        int currentId = sharedPreferences.getInt(KEY_PREFIX + category, 0);
        sharedPreferences.edit().putInt(KEY_PREFIX + category, currentId + 1).apply();
        return currentId + 1;
    }

    // 勘定科目ごとの費用を更新するメソッド
    public void updateExpense(String userId, String category, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextId(category);
        DatabaseReference expenseRef = databaseReference.child(userId).child("expenses").child(category).child(formattedTimestamp).child(String.valueOf(nextId)).child("amount");
        updateAmount(expenseRef, amount);
    }

    // 勘定科目ごとの収益を更新するメソッド
    public void updateRevenue(String userId, String category, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextId(category);
        DatabaseReference revenueRef = databaseReference.child(userId).child("revenues").child(category).child(formattedTimestamp).child(String.valueOf(nextId)).child("amount");
        updateAmount(revenueRef, amount);
    }

    // 消費税の購買を更新するメソッド
    public void updateTaxPurchase(String userId, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextId("tax_purchase");
        DatabaseReference taxPurchaseRef = databaseReference.child(userId).child("tax").child("purchases").child(formattedTimestamp).child(String.valueOf(nextId)).child("amount");
        updateAmount(taxPurchaseRef, amount);
    }

    // 消費税の販売を更新するメソッド
    public void updateTaxSales(String userId, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextId("tax_sales");
        DatabaseReference taxSalesRef = databaseReference.child(userId).child("tax").child("sales").child(formattedTimestamp).child(String.valueOf(nextId)).child("amount");
        updateAmount(taxSalesRef, amount);
    }

    // UIDを取得するメソッド
    public String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    // 共通の更新メソッド
    private void updateAmount(DatabaseReference ref, double amount) {
        ref.setValue(amount).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("Transaction successful: " + amount);
            } else {
                System.err.println("Transaction failed: " + task.getException().getMessage());
            }
        });
    }
}
