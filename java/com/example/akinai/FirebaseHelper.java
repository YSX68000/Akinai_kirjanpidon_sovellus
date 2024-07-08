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
    private static final String KEY_PREFIX_EXPENSES = "Journal_Expenses_";
    private static final String KEY_PREFIX_REVENUES = "Journal_Revenues_";
    private static final String KEY_PREFIX_TAX_PURCHASE = "Journal_TaxPurchase_";
    private static final String KEY_PREFIX_TAX_SALES = "Journal_TaxSales_";

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

    // SharedPreferencesから次の連番を取得し、インクリメントして保存するメソッド（Expenses）
    private int getNextExpenseId(String category) {
        int currentId = sharedPreferences.getInt(KEY_PREFIX_EXPENSES + category, 0);
        sharedPreferences.edit().putInt(KEY_PREFIX_EXPENSES + category, currentId + 1).apply();
        return currentId + 1;
    }

    // SharedPreferencesから次の連番を取得し、インクリメントして保存するメソッド（Revenues）
    private int getNextRevenueId(String category) {
        int currentId = sharedPreferences.getInt(KEY_PREFIX_REVENUES + category, 0);
        sharedPreferences.edit().putInt(KEY_PREFIX_REVENUES + category, currentId + 1).apply();
        return currentId + 1;
    }

    // SharedPreferencesから次の連番を取得し、インクリメントして保存するメソッド（Tax Purchase）
    private int getNextTaxPurchaseId() {
        int currentId = sharedPreferences.getInt(KEY_PREFIX_TAX_PURCHASE, 0);
        sharedPreferences.edit().putInt(KEY_PREFIX_TAX_PURCHASE, currentId + 1).apply();
        return currentId + 1;
    }

    // SharedPreferencesから次の連番を取得し、インクリメントして保存するメソッド（Tax Sales）
    private int getNextTaxSalesId() {
        int currentId = sharedPreferences.getInt(KEY_PREFIX_TAX_SALES, 0);
        sharedPreferences.edit().putInt(KEY_PREFIX_TAX_SALES, currentId + 1).apply();
        return currentId + 1;
    }

    // 勘定科目ごとの費用を更新するメソッド
    public void updateExpense(String userId, String category, long timestamp, double amount, String itemName) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextExpenseId(category);
        DatabaseReference expenseRef = databaseReference.child(userId)
                .child("expenses")
                .child(category)
                .child(formattedTimestamp)
                .child(String.valueOf(nextId));
        expenseRef.child("amount").setValue(amount); //setValueで値をいれる。ノードにならない。
        expenseRef.child("itemName").setValue(itemName);
    }

    // 勘定科目ごとの収益を更新するメソッド
    public void updateRevenue(String userId, String category, long timestamp, double amount, String itemName) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextRevenueId(category);
        DatabaseReference revenueRef = databaseReference.child(userId)
                .child("revenues")
                .child(category)
                .child(formattedTimestamp)
                .child(String.valueOf(nextId));
        revenueRef.child("amount").setValue(amount);
        revenueRef.child("itemName").setValue(itemName);
    }

    // 消費税の購買を更新するメソッド
    public void updateTaxPurchase(String userId, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextTaxPurchaseId();
        DatabaseReference taxPurchaseRef = databaseReference.child(userId)
                .child("tax")
                .child("purchases")
                .child(formattedTimestamp)
                .child(String.valueOf(nextId));
        taxPurchaseRef.child("amount").setValue(amount);
    }

    // 消費税の販売を更新するメソッド
    public void updateTaxSales(String userId, long timestamp, double amount) {
        String formattedTimestamp = getFormattedTimestamp(timestamp);
        int nextId = getNextTaxSalesId();
        DatabaseReference taxSalesRef = databaseReference.child(userId)
                .child("tax")
                .child("sales")
                .child(formattedTimestamp)
                .child(String.valueOf(nextId));
        taxSalesRef.child("amount").setValue(amount);
    }

    // UIDを取得するメソッド
    public String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }
}
