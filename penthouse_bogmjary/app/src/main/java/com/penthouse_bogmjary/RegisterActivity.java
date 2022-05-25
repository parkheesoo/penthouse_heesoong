package com.penthouse_bogmjary;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        EditText idEdit = (EditText) findViewById(R.id.login_et_id) ;
        EditText pwEdit = (EditText) findViewById(R.id.login_et_pw);
        EditText pwChEdit =(EditText) findViewById(R.id.login_et_pw_ch);
        EditText eMailEdit = (EditText) findViewById(R.id.login_et_email);
        EditText nickNameEdit = (EditText) findViewById(R.id.login_et_nickname);
        Button joinButton = (Button) findViewById(R.id.joinBtn);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEdit.getText().toString().trim();
                String pw = pwEdit.getText().toString().trim();
                String pwCh= pwChEdit.getText().toString().trim();
                String email = eMailEdit.getText().toString().trim();
                String nickName = nickNameEdit.getText().toString().trim();

                if(pw.equals(pwCh)){
                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setMessage("가입진행중 입니다...");
                    mDialog.show();

                    mAuth.createUserWithEmailAndPassword(id, pw)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()  {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String idId = user.getEmail();
                                        String uid = user.getUid();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

}