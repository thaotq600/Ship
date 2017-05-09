package com.example.quocthao.supership.shop.info;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quocthao.supership.CustomDialog;
import com.example.quocthao.supership.R;
import com.example.quocthao.supership.login_register.LoginActivity;
import com.example.quocthao.supership.shop.ContentShopActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

public class InfoShopActivity extends AppCompatActivity {

    private Button btnSubmit;
    private Intent intentContent;
    private EditText etName, etAddress, etPhone;
    private FirebaseUser userShop = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference drShop = FirebaseDatabase.getInstance().getReference();
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        initValue();
        Hawk.init(this).build();
        intentContent = new Intent(InfoShopActivity.this, ContentShopActivity.class);
        customDialog = new CustomDialog(this, R.layout.dialog_waiting);

        customDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    InfoShopActivity.this.finish();
                    return true;
                }
                return false;

            }
        });

        customDialog.show();

        if (Hawk.get("infoShop") == null) {
            drShop = drShop.child("infomation").child("shop").child(userShop.getUid());
            checkInfo();
        } else {
            startActivity(intentContent);
            customDialog.dismiss();
            finish();
        }

    }

    private void initValue() {
        btnSubmit = (Button) findViewById(R.id.shop_info_btn_submit);
        etName = (EditText) findViewById(R.id.shop_info_et_name);
        etAddress = (EditText) findViewById(R.id.shop_info_et_address);
        etPhone = (EditText) findViewById(R.id.shop_info_et_phone);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                Hawk.put("infoShop", "yes");
                startActivity(intentContent);
                finish();
            }
        });
    }

    private void saveInfo() {
        drShop.child("name").setValue(etName.getText().toString());
        drShop.child("address").setValue(etAddress.getText().toString());
        drShop.child("phone").setValue(etPhone.getText().toString());
        drShop.child("email").setValue(userShop.getEmail());
    }

    private void checkInfo() {
        if (drShop != null) {
            drShop.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    etName.setText((dataSnapshot.child("name").getValue() == null) ?
                            "" : dataSnapshot.child("name").getValue().toString());

                    etAddress.setText((dataSnapshot.child("address").getValue() == null) ?
                            "" : dataSnapshot.child("address").getValue().toString());

                    etPhone.setText((dataSnapshot.child("phone").getValue() == null) ?
                            "" : dataSnapshot.child("phone").getValue().toString());

                    customDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    customDialog.dismiss();
                }
            });
        }
    }
}