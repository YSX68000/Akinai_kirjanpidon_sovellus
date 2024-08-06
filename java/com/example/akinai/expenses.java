package com.example.akinai;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class expenses extends AppCompatActivity {

    Button nkulu8650;

    String name, itemName;
    double alv14 = 0.14;
    double alv24 = 0.24;
    double totalAmount;
    double vat14, vat24;
    double pretaxamount;
    double roundedValue;

    private FirebaseHelper firebaseHelper;
    private String userId;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        nkulu8650 = findViewById(R.id.nkulu);

        // FirebaseHelperのインスタンスを初期化
        firebaseHelper = new FirebaseHelper(this);

        // 現在のユーザーのUIDを取得
        userId = firebaseHelper.getCurrentUserId();
        if (userId == null) {
            // ユーザーがログインしていない場合の処理
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        //updateData();

        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View custom = inflater.inflate(R.layout.custom, null);

        nkulu8650.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = custom.findViewById(R.id.edittext);
                final EditText item = custom.findViewById(R.id.item);

                final RadioButton c1 = custom.findViewById(R.id.c1);
                final RadioButton c2 = custom.findViewById(R.id.c2);

                Button cancel1 = custom.findViewById(R.id.cancel);
                Button register = custom.findViewById(R.id.register);

                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = editText.getText().toString(); // 価格アイテムメモ登録テキスト
                        itemName = item.getText().toString(); // item title
                        totalAmount = Double.parseDouble(name);

                        if (c1.isChecked()) {
                            pretaxamount = Math.round(totalAmount / (1 + alv14));
                            vat14 = pretaxamount * alv14;
                            int decimalPlaces = 2; // 四捨五入する小数点以下の桁数

                            BigDecimal bd = new BigDecimal(Double.toString(vat14));
                            bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
                            roundedValue = bd.doubleValue();
                            Log.d("計算結果", String.valueOf(roundedValue));
                            Log.d("計算結果2", String.valueOf(pretaxamount));


                        } else if (c2.isChecked()) {
                            pretaxamount = Math.round(totalAmount / (1 + alv24));
                            vat14 = pretaxamount * alv24;
                            int decimalPlaces = 2; // 四捨五入する小数点以下の桁数

                            BigDecimal bd = new BigDecimal(Double.toString(vat24));
                            bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
                            roundedValue = bd.doubleValue();
                            Log.d("計算結果", String.valueOf(roundedValue));
                            Log.d("計算結果2", String.valueOf(pretaxamount));
                        }

                        editText.getText().clear();
                        builder.dismiss();

                        // updateData8650メソッドを呼び出し、データを保存する
                        updateData8650(pretaxamount);
                        updateDataPvat(roundedValue);
                    }
                });
                builder.setView(custom);
                builder.show();
            }
        });
    }

    // updateData8650メソッド
    private void updateData8650(double amount) {
        long timestamp = System.currentTimeMillis(); // 現在のタイムスタンプを取得
        //long timestamp2 = System.currentTimeMillis();

        // 費用を更新
        firebaseHelper.updateExpense(userId, "Edustuskulut", timestamp, amount);
    }

    private void updateDataPvat(double amountPV) {
        long timestamp = System.currentTimeMillis(); // 現在のタイムスタンプを取得


        // 消費税の購買を更新
        firebaseHelper.updateTaxPurchase(userId, timestamp, amountPV);

    }


    private void updateData() {
        long timestamp = System.currentTimeMillis(); // 現在のタイムスタンプを取得
        long timestamp2 = System.currentTimeMillis();

        // 収益を更新
        firebaseHelper.updateRevenue(userId, "productSales", timestamp,2000.0);

        // 消費税の販売を更新
        firebaseHelper.updateTaxSales(userId, timestamp, 300.0);
    }
}
