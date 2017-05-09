package com.example.quocthao.supership.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.quocthao.supership.CustomDialog;
import com.example.quocthao.supership.KeyboardUtil;
import com.example.quocthao.supership.customer.InfoCustomer;
import com.example.quocthao.supership.shop.info.InfoShopActivity;
import com.example.quocthao.supership.shipper.InfoShipper;
import com.example.quocthao.supership.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.hawk.Hawk;

public class LoginActivity extends Activity {

    private ViewFlipper vfLogo;
    private EditText etUser;
    private EditText etPass;
    private Button btnLogin;
    private TextView tvForgetPass;
    private TextView tvRegister;
    private LinearLayout layoutLogin;

    private Intent intentInfo;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initValue();
        Hawk.init(this).build();

        checkEmailExists();

    }

    //Kiểm tra tài khoản người dùng
    public void checkEmailExists() {
        if (Hawk.get("email") == null) {

            vfLogo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    vfLogo.setInAnimation(LoginActivity.this, R.anim.anim_around_in);
                    vfLogo.setOutAnimation(LoginActivity.this, R.anim.anim_around_out);
                    vfLogo.showNext();

                    return false;
                }


            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startLogin();
                }
            });

            tvForgetPass.setMovementMethod(LinkMovementMethod.getInstance()); //link web

            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intentRegister);
                }
            });
        } else {
            chooseWho();
        }
    }

    private void initValue() {
        vfLogo = (ViewFlipper) findViewById(R.id.vf_logo);
        etUser = (EditText) findViewById(R.id.et_user);
        etPass = (EditText) findViewById(R.id.et_pass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvForgetPass = (TextView) findViewById(R.id.tv_forget_pass);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        layoutLogin = (LinearLayout) findViewById(R.id.activity_login);

        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideSoftKeyboard(LoginActivity.this);
            }
        });
    }

    private void chooseWho() {

        final CustomDialog dialogWho = new CustomDialog(this, R.layout.dialog_login_who);

        LinearLayout layoutSaler = (LinearLayout) dialogWho.getView().findViewById(R.id.layout_saler);
        LinearLayout layoutShipper = (LinearLayout) dialogWho.getView().findViewById(R.id.layout_shipper);
        LinearLayout layoutCustomer = (LinearLayout) dialogWho.getView().findViewById(R.id.layout_customer);
        TextView tvAccount = (TextView) dialogWho.getView().findViewById(R.id.dialog_login_who_tv_account);

        layoutSaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentInfo = new Intent(LoginActivity.this, InfoShopActivity.class);
                startActivity(intentInfo);
            }
        });

        layoutShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentInfo = new Intent(LoginActivity.this, InfoShipper.class);
                startActivity(intentInfo);
            }
        });

        layoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentInfo = new Intent(LoginActivity.this, InfoCustomer.class);
                startActivity(intentInfo);
            }
        });

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.deleteAll();
                dialogWho.cancel();
                checkEmailExists();
            }
        });

        dialogWho.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    LoginActivity.this.finish();
                    return true;
                }
                return false;
            }
        });

        ((ViewGroup)dialogWho.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this, R.anim.anim_zoom_in));

        dialogWho.show();
    }

    private void startLogin() {
        final CustomDialog dialogWaiting = new CustomDialog(this, R.layout.dialog_waiting);
        dialogWaiting.show();

        final String email = etUser.getText().toString();
        final String pass = etPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                            Hawk.put("email", email);

                            dialogWaiting.dismiss();
                            chooseWho();
                        } else {
                            Toast.makeText(LoginActivity.this, "Fail",
                                    Toast.LENGTH_SHORT).show();

                            dialogWaiting.dismiss();
                        }
                    }
                });
    }

    private void forgotPassword() {

    }
}
