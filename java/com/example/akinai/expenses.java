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

public class expenses extends AppCompatActivity {

    Button nkulu8650,matkakulut,mainoskulut,toimistot,atk,Puhelinkulut,Pienhankinnat,tyohuone,palkka,yel,Vakuutusmaksut,korko,muut;

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
        setContentView(R.layout.activity_expenses);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        nkulu8650 = findViewById(R.id.nkulu);
        matkakulut = findViewById(R.id.matkakulut);
        mainoskulut = findViewById(R.id.mainoskulut);
        toimistot = findViewById(R.id.toimistot);
        atk= findViewById(R.id.atk);
        Puhelinkulut= findViewById(R.id.Puhelinkulut);
        Pienhankinnat= findViewById(R.id.Pienhankinnat);
        tyohuone= findViewById(R.id.tyohuone);
        palkka= findViewById(R.id.palkka);
        yel= findViewById(R.id.yel);
        Vakuutusmaksut= findViewById(R.id.Vakuutusmaksut);
        korko= findViewById(R.id.korko);
        muut= findViewById(R.id.muut);

        firebaseHelper = new FirebaseHelper(this);

        userId = firebaseHelper.getCurrentUserId();
        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View custom = inflater.inflate(R.layout.custom, null);

        //YEL,palkka builder
        final AlertDialog builder2 = new AlertDialog.Builder(this).create();
        LayoutInflater inflater2 = this.getLayoutInflater();
        final View custom2 = inflater.inflate(R.layout.custom2, null);

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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        updateData8650(pretaxamount);
                        updateDataPvat(roundedValue);

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
                builder.setView(custom);
                builder.show();
            }
        });

        matkakulut.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        matkakulut(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        mainoskulut.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        mainoskulut(pretaxamount);
                        updateDataPvat(roundedValue);

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });


        toimistot.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        toimistotarvikkeet(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        atk.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        toimistotarvikkeet(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        Puhelinkulut.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        puhelinkulut(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        Pienhankinnat.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        pienhankinnat(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        tyohuone.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        tyohuone(pretaxamount);
                        updateDataPvat(roundedValue);

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        palkka.setOnClickListener(new View.OnClickListener() {
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
                        try{
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        editText.getText().clear();
                        item.getText().clear();
                        builder2.dismiss();

                        palkka(totalAmount);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder2.setView(custom2);
                builder2.show();
            }
        });

        yel.setOnClickListener(new View.OnClickListener() {
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
                        try{
                        name = editText.getText().toString();
                        itemName = item.getText().toString();
                        totalAmount = Double.parseDouble(name);

                        editText.getText().clear();
                        item.getText().clear();
                        builder2.dismiss();

                        yel(totalAmount);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder2.setView(custom2);
                builder2.show();
            }
        });

        Vakuutusmaksut.setOnClickListener(new View.OnClickListener() {
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
                        try{
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
                        item.getText().clear();
                        builder.dismiss();

                        vakuutusmaksut(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        korko.setOnClickListener(new View.OnClickListener() {
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
                        try {
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
                            item.getText().clear();
                            builder.dismiss();

                            korkomaksut(pretaxamount);
                            updateDataPvat(roundedValue);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });

        muut.setOnClickListener(new View.OnClickListener() {
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
                    try {
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
                        item.getText().clear();
                        builder.dismiss();

                        muut(pretaxamount);
                        updateDataPvat(roundedValue);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Kirjoita kelvolliset arvot", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


                builder.setView(custom);
                builder.show();
            }
        });



    }

    private void updateData8650(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Edustuskulut", timestamp, amount, itemName);
    }

    private void matkakulut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Matkakulut", timestamp, amount, itemName);
    }

    private void mainoskulut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Mainoskulut", timestamp, amount, itemName);
    }

    private void toimistotarvikkeet(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Toimistotarvikkeet", timestamp, amount, itemName);
    }

    private void puhelinkulut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Puhelinkulut", timestamp, amount, itemName);
    }
    private void pienhankinnat(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Pienhankinnat", timestamp, amount, itemName);
    }

    private void tyohuone(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Tyohuone", timestamp, amount, itemName);
    }

    private void palkka(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Palkka", timestamp, amount, itemName);
    }

    private void yel(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "YEL", timestamp, amount, itemName);
    }

    private void vakuutusmaksut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Vakuutusmaksut", timestamp, amount, itemName);
    }

    private void korkomaksut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Korkomaksut", timestamp, amount, itemName);
    }

    private void muut(double amount) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateExpense(userId, "Muut", timestamp, amount, itemName);
    }

    private void updateDataPvat(double amountPV) {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateTaxPurchase(userId, timestamp, amountPV);
    }

    private void updateData() {
        long timestamp = System.currentTimeMillis();
        firebaseHelper.updateRevenue(userId, "productSales", timestamp, 2000.0, itemName);
        firebaseHelper.updateTaxSales(userId, timestamp, 300.0);
    }
}
