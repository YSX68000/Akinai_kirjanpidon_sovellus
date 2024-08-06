package com.example.akinai;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newuser extends AppCompatActivity {

    // 入力フィールドの変数を宣言
    EditText companyName, address, yTunnus, Emailnew, Passwordnew;

    // 登録ボタンの変数を宣言
    Button register;
    // データベース参照の変数を宣言
    DatabaseReference database;

    // Firebase Authenticationのインスタンスを宣言
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        // ActionBarをカスタムレイアウトに設定
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // レイアウトからUI要素を初期化
        companyName = (EditText) findViewById(R.id.companyName);
        address = (EditText) findViewById(R.id.address);
        yTunnus = (EditText) findViewById(R.id.yTunnus);
        Emailnew = (EditText) findViewById(R.id.Emailnew);
        Passwordnew = (EditText) findViewById(R.id.Passwordnew);

        // 登録ボタンの初期化
        register = (Button) findViewById(R.id.register);

        // Firebase Authenticationのインスタンスを取得
        mAuth = FirebaseAuth.getInstance();

        // 現在のユーザーがログインしているか確認
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // ユーザーがログインしている場合の処理（コメントアウト中）
            // reload();
        }

        // Firebase Realtime Databaseの参照を初期化
        database = FirebaseDatabase.getInstance().getReference();

        // 登録ボタンのクリックリスナーを設定
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // アカウント作成メソッドを呼び出す
                createAccount3();
            }
        });
    }

    // アカウント作成メソッド
    private void createAccount3() {
        // 入力フィールドの内容を取得
        final String name = companyName.getText().toString().trim();
        final String add = address.getText().toString().trim();
        final String yt = yTunnus.getText().toString().trim();
        final String email = Emailnew.getText().toString().trim();
        final String password = Passwordnew.getText().toString().trim();

        // 入力フィールドが空でないかチェック
        if (name.isEmpty() || add.isEmpty() || yt.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(newuser.this, "全てのフィールドを入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        // メールアドレスの形式が正しいかチェック
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(newuser.this, "無効なメールアドレス形式です", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authenticationで新規ユーザーを作成
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // アカウント作成が成功した場合
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                // 新しいユーザーオブジェクトを作成
                                User newUser = new User(add, email, name, uid, yt);

                                // データベースにユーザー情報を保存
                                database.child("users").child(uid).setValue(newUser)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // データベースへの書き込みが成功した場合
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(newuser.this, "登録しました", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // 書き込みエラーが発生した場合
                                                    Toast.makeText(newuser.this, "データベース書き込みエラー", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // 認証エラーが発生した場合
                            Toast.makeText(newuser.this, "認証エラー: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    
}
