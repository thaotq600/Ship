package com.example.quocthao.supership.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quocthao.supership.R;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

public class InfoCustomer extends AppCompatActivity {

    private Button btnSubmit;
    private EditText etName, etAddress, etPhone;
    private FirebaseUser userCustomer = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference drCustomer = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        initContent();
        drCustomer = drCustomer.child("infomation").child("customer");

        Log.d("Show ID: ", userCustomer.getUid());
        Log.d("Show Email: ", userCustomer.getEmail());

        if (drCustomer.child(userCustomer.getUid()) != null) {
            drCustomer.child(userCustomer.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    etName.setText((dataSnapshot.child("name").getValue() == null) ?
                            "" : dataSnapshot.child("name").getValue().toString());

                    etAddress.setText((dataSnapshot.child("address").getValue() == null) ?
                            "" : dataSnapshot.child("address").getValue().toString());

                    etPhone.setText((dataSnapshot.child("phone").getValue() == null) ?
                            "" : dataSnapshot.child("phone").getValue().toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drCustomer.child(userCustomer.getUid()).child("name").setValue(etName.getText().toString());
                drCustomer.child(userCustomer.getUid()).child("address").setValue(etAddress.getText().toString());
                drCustomer.child(userCustomer.getUid()).child("phone").setValue(etPhone.getText().toString());
                drCustomer.child(userCustomer.getUid()).child("email").setValue(userCustomer.getEmail());

                Hawk.put("name", etName.getText().toString());
                Intent intentContent = new Intent(InfoCustomer.this, ContentCustomer.class);
                startActivity(intentContent);
            }
        });


    }

    private void initContent() {
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
    }
}