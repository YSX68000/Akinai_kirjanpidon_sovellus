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

    EditText companyName,address,yTunnus,Emailnew,Passwordnew;

    //String name,add,yt,email,password,uid;
    Button register;
    DatabaseReference database;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        companyName = (EditText) findViewById(R.id.companyName);
        address = (EditText) findViewById(R.id.address);
        yTunnus = (EditText) findViewById(R.id.yTunnus);
        Emailnew = (EditText)findViewById(R.id.Emailnew);
        Passwordnew = (EditText)findViewById(R.id.Passwordnew);

        register = (Button) findViewById(R.id.register);

        /*name = companyName.getText().toString();
        add = address.getText().toString();
        yt = yTunnus.getText().toString();*/

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser(); //check if user has already logged in
        if(currentUser != null){
            //reload();
        }

        database = FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount3();
            }
        });


    }
    private void createAccount3() {
        final String name = companyName.getText().toString().trim();
        final String add = address.getText().toString().trim();
        final String yt = yTunnus.getText().toString().trim();
        final String email = Emailnew.getText().toString().trim();
        final String password = Passwordnew.getText().toString().trim();

        if (name.isEmpty() || add.isEmpty() || yt.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(newuser.this, "全てのフィールドを入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(newuser.this, "無効なメールアドレス形式です", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                User newUser = new User(add, email, name, uid, yt);

                                database.child("users").child(uid).setValue(newUser)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(newuser.this, "登録しました", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(newuser.this, "データベース書き込みエラー", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(newuser.this, "認証エラー: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //5/30データベース直して。
    //5/30 The data is registered in alphabetical order, so make sure to arrange it in alphabetical order as well.
}