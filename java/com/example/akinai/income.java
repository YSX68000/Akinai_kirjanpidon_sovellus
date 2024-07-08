package com.example.akinai;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class income extends AppCompatActivity {

    Button sales,advertisement,otherincome,korkotulot;

    String name, itemName;
    double alv14 = 0.14;
    double alv24 = 0.24;
    double totalAmount;
    double vat14, vat24;
    double pretaxamount;
    double roundedValue;

    private FirebaseHelper firebaseHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        sales = findViewById(R.id.sales);
        advertisement = findViewById(R.id.advertisement);
        otherincome = findViewById(R.id.otherincome);
        korkotulot = findViewById(R.id.korkotulot);

        // FirebaseHelperのインスタンスを初期化
        firebaseHelper = new FirebaseHelper(this);

        // 現在のユーザーのUIDを取得
        userId = firebaseHelper.getCurrentUserId();
        if (userId == null) {
            // ユーザーがログインしていない場合の処理
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View custom = inflater.inflate(R.layout.custom, null);

        //ad,other,korko
        final AlertDialog builder2 = new AlertDialog.Builder(this).create();
        LayoutInflater inflater2 = this.getLayoutInflater();
        final View custom2 = inflater.inflate(R.layout.custom2, null);

        sales.setOnClickListener(new View.OnClickListener() {
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
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        if (c1.isChecked()) {
                            pretaxamount = Math.round(totalAmount / (1 + alv14));
                            vat14 = pretaxamount * alv14;
                            int decimalPlaces = 2;

                            BigDecimal bd = new BigDecimal(Double.toString(vat14));
                            bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
                            roundedValue = bd.doubleValue();
                            Log.d("計算結果", String.valueOf(roundedValue));
                            Log.d("計算結果2", String.valueOf(pretaxamount));
                        } else if (c2.isChecked()) {
                            pretaxamount = Math.round(totalAmount / (1 + alv24));
                            vat24 = pretaxamount * alv24;
                            int decimalPlaces = 2;

                            BigDecimal bd = new BigDecimal(Double.toString(vat24));
                            bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
                            roundedValue = bd.doubleValue();
                            Log.d("計算結果", String.valueOf(roundedValue));
                            Log.d("計算結果2", String.valueOf(pretaxamount));
                        }

                        editText.getText().clear();
                        builder.dismiss();

                        updateData2000(pretaxamount);
                        updateDataSvat(roundedValue);
                    }
                });
                builder.setView(custom);
                builder.show();
            }
        });

        advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = custom2.findViewById(R.id.edittext);
                final EditText item = custom2.findViewById(R.id.item);

                Button cancel1 = custom2.findViewById(R.id.cancel);
                Button register = custom2.findViewById(R.id.register);

                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder2.dismiss();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        editText.getText().clear();
                        builder.dismiss();

                        advertisement(totalAmount);
                        //updateDataSvat(roundedValue);
                    }
                });
                builder2.setView(custom2);
                builder2.show();
            }
        });

        otherincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = custom2.findViewById(R.id.edittext);
                final EditText item = custom2.findViewById(R.id.item);

                Button cancel1 = custom2.findViewById(R.id.cancel);
                Button register = custom2.findViewById(R.id.register);

                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder2.dismiss();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        editText.getText().clear();
                        builder.dismiss();

                        otherincome(totalAmount);
                        //updateDataSvat(roundedValue);
                    }
                });
                builder2.setView(custom2);
                builder2.show();
            }
        });

        korkotulot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = custom2.findViewById(R.id.edittext);
                final EditText item = custom2.findViewById(R.id.item);

                Button cancel1 = custom2.findViewById(R.id.cancel);
                Button register = custom2.findViewById(R.id.register);

                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder2.dismiss();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        editText.getText().clear();
                        builder.dismiss();

                        korkotulot(totalAmount);
                        //updateDataSvat(roundedValue);
                    }
                });
                builder2.setView(custom2);
                builder2.show();
            }
        });

    }

    private void updateData2000(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateRevenue(userId, "Myyntitulot", timestamp, amount, itemName);
    }

    private void advertisement(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateRevenue(userId, "Mainostulot", timestamp, amount, itemName);
    }

    private void otherincome(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateRevenue(userId, "Muut", timestamp, amount, itemName);
    }

    private void korkotulot(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateRevenue(userId, "Korkotulot", timestamp, amount, itemName);
    }

    private void updateDataSvat(double amountPV) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateTaxSales(userId, timestamp, amountPV);
    }
}
