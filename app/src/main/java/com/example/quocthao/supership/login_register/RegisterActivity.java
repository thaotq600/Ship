package com.example.quocthao.supership.login_register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quocthao.supership.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btnSubmit;
    private ImageView ivBack;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initContent();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }

    private void initContent() {
        etEmail = (EditText) findViewById(R.id.register_et_email);
        etPass = (EditText) findViewById(R.id.register_et_password);
        btnSubmit = (Button) findViewById(R.id.register_btn_submit);
        ivBack = (ImageView) findViewById(R.id.register_iv_back);
    }

    private void startRegister() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if(!email.equals("") && !pass.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Success",
                                        Toast.LENGTH_SHORT).show();
                                etEmail.setText("");
                                etPass.setText("");
                                etEmail.requestFocus();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Fail",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
