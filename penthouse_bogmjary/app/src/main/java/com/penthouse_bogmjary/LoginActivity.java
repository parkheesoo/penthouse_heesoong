package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText logViewEmail = (EditText) findViewById(R.id.login_view_email);
        EditText logViewPw = (EditText)findViewById(R.id.login_view_pw);
        Button doLoginBtn = (Button)findViewById(R.id.do_login_btn);

        mAuth = FirebaseAuth.getInstance();

        doLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String inputEmail = logViewEmail.getText().toString().trim();
                String inputPw = logViewPw.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(inputEmail, inputPw)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(LoginActivity.this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}