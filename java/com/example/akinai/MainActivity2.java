package com.example.akinai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {

    // ボタンの変数を宣言
    Button meno, tulo, pvkirja, Laskelma, kvilmoitus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // ActionBarをカスタムレイアウトに設定
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // レイアウトからボタンを初期化
        meno = (Button) findViewById(R.id.meno);
        tulo = (Button) findViewById(R.id.tulo);
        pvkirja = (Button) findViewById(R.id.pvkirja);
        Laskelma = (Button) findViewById(R.id.lask);
        kvilmoitus = (Button) findViewById(R.id.kvilmoitus);

        // menoボタンにクリックリスナーを設定し、expensesアクティビティを開始
        meno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, expenses.class);
                startActivity(intent);
            }
        });

        // pvkirjaボタンにクリックリスナーを設定し、JournalActivityアクティビティを開始
        pvkirja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, JournalActivity.class);
                startActivity(intent);
            }
        });

        // Laskelmaボタンにクリックリスナーを設定し、ProfitAndLossActivityアクティビティを開始
        Laskelma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, ProfitAndLossActivity.class);
                startActivity(intent);
            }
        });

        // tuloボタンにクリックリスナーを設定し、incomeアクティビティを開始
        tulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, income.class);
                startActivity(intent);
            }
        });

        // kvilmoitusボタンにクリックリスナーを設定し、KausiveroilmoitusActivityアクティビティを開始
        kvilmoitus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, KausiveroilmoitusActivity.class);
                startActivity(intent);
            }
        });

        // TabLayoutのセットアップ
        setupTabLayout();
    }

    // TabLayoutの設定を行うメソッド
    private void setupTabLayout() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // タブが選択された時の処理
                onTabTapped(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // タブが選択解除された時の処理（特に何も行わない）
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // タブが再選択された時の処理
                onTabTapped(tab.getPosition());
            }
        });
    }

    // タブがタップされた時の処理
    private void onTabTapped(int position) {
        switch (position) {
            case 0:
                // 位置0のタブがタップされた場合、MainActivity2を再起動
                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                startActivity(intent);
                break;
            case 1:
                // 位置1のタブがタップされた場合、investmentアクティビティを開始
                Intent intentto = new Intent(MainActivity2.this, investment.class);
                startActivity(intentto);
                break;
            default:
                // デフォルトの処理（特に何も行わない）
                break;
        }
    }
}
