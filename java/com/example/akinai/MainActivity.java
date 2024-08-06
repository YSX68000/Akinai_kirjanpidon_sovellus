package com.example.akinai;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // メールアドレス入力フィールドを格納するEditText変数
    EditText email, pass;

    // ボタンを格納するButton変数
    Button enter, newUser, test;

    // Firebase認証用のインスタンス変数
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBarをカスタムレイアウトに設定
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // レイアウトからメールアドレスとパスワードのEditTextを初期化
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        // レイアウトからボタンを初期化
        enter = (Button) findViewById(R.id.enter);
        newUser = (Button) findViewById(R.id.newUser);

        // FirebaseAuthのインスタンスを初期化
        mAuth = FirebaseAuth.getInstance();

        // enterボタンにクリックリスナーを設定
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // EditTextから入力されたメールアドレスとパスワードを取得
                    String emailst = email.getText().toString();
                    String passwordst = pass.getText().toString();

                    // 取得したメールアドレスとパスワードを用いてsignInメソッドを呼び出す
                    signIn(emailst, passwordst);
                } catch (Exception e) {
                    // 例外が発生した場合、Toastでエラーメッセージを表示
                    Toast.makeText(MainActivity.this, "Laita sähköposti ja salasana", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* コメントアウトされたtestボタンのクリックリスナー
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });*/

        // newUserボタンにクリックリスナーを設定
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // newuserアクティビティを開始
                Intent intent = new Intent(MainActivity.this, newuser.class);
                startActivity(intent);
            }
        });
    }

    // Firebase認証を使用してサインインを行うメソッド
    private void signIn(String email, String password) {
        // FirebaseAuthを使用してメールアドレスとパスワードでサインイン
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // サインインに成功した場合、MainActivity2を開始し、ログに成功メッセージを出力
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // サインインに失敗した場合、ログに失敗メッセージを出力
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}
