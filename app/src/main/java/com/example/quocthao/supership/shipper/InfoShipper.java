package com.example.quocthao.supership.shipper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quocthao.supership.LoadFirebase;
import com.example.quocthao.supership.R;

public class InfoShipper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_info);

        Intent intent = new Intent(this, LoadFirebase.class);
        startActivity(intent);
    }
}
