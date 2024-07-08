package com.example.akinai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity2 extends AppCompatActivity {

    Button meno,tulo,pvkirja,Laskelma,kvilmoitus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        meno = (Button)findViewById(R.id.meno);
        tulo = (Button)findViewById(R.id.tulo);
        pvkirja = (Button)findViewById(R.id.pvkirja);
        Laskelma = (Button)findViewById(R.id.lask);
        kvilmoitus = (Button)findViewById(R.id.kvilmoitus);

        meno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,expenses.class);
                startActivity(intent);
            }
        });

        pvkirja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,JournalActivity.class);
                startActivity(intent);
            }
        });

        Laskelma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,ProfitAndLossActivity.class);
                startActivity(intent);
            }
        });

        tulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,income.class);
                startActivity(intent);
            }
        });

        kvilmoitus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,KausiveroilmoitusActivity.class);
                startActivity(intent);
            }
        });


        //setupTabLayout();
    }

    private void setupTabLayout() {
        /*TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabTapped(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabTapped(tab.getPosition());
            }
        });*/
    }
    private void onTabTapped(int position) {
        /*switch (position) {
            case 0:

                android.content.Intent intent = new android.content.Intent(MainActivity2.this, MainActivity2.class);
                startActivity(intent);

                break;

            case 1:

                //android.content.Intent intentto = new android.content.Intent(MainActivity2.this, Odai.class);
                //startActivity(intentto);
                break;



            default:

        }*/
    }
}