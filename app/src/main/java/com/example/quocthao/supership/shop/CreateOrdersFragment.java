package com.example.quocthao.supership.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quocthao.supership.CustomDialog;
import com.example.quocthao.supership.R;
import com.example.quocthao.supership.shop.services.DataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Quoc Thao on 3/31/2017.
 */

public class CreateOrdersFragment extends Fragment{

    private EditText etMail, etAddressSend, etAddressReceive, etTimeOut, etCategory, etWeight;
    private EditText etMoneyItem, etMoneyShip, etPhoneSender, etPhoneReceiver, etNote;
    private Button btnOk;
    private FirebaseUser userShop;
    private DatabaseReference drBill, drShop;
    private ICreateSuccessListener iCreateSuccessListener;
    protected DataService dataService;

    public CreateOrdersFragment(ICreateSuccessListener iCreateSuccessListener) {
        this.iCreateSuccessListener = iCreateSuccessListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_create, container, false);
        initValue(view);
        userShop = FirebaseAuth.getInstance().getCurrentUser();
        dataService = new DataService();
        drBill = FirebaseDatabase.getInstance().getReference().child("bills");
        drShop = FirebaseDatabase.getInstance().getReference().child("infomation")
                .child("shop").child(userShop.getUid()).child("bills");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CustomDialog dialogWaiting = new CustomDialog(getContext(), R.layout.dialog_waiting);
                dialogWaiting.show();

                dataService.newBill(etMail.getText().toString()).continueWith(new Continuation<String, Void>() {
                    @Override
                    public Void then(Task<String> task) throws Exception {
                        String idOrder = task.getResult();

                        dialogWaiting.dismiss();

                        saveOrders(drBill.child(idOrder));
                        saveOrders(drShop.child(idOrder));

                        if (iCreateSuccessListener != null)
                            iCreateSuccessListener.onSuccess();

                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        });

        return view;
    }

    private void initValue(View view) {
        etMail = (EditText) view.findViewById(R.id.order_create_et_email);
        etAddressSend = (EditText) view.findViewById(R.id.order_create_et_address_send);
        etAddressReceive = (EditText) view.findViewById(R.id.order_create_et_address_receive);
        etTimeOut = (EditText) view.findViewById(R.id.order_create_et_time_out);
        etCategory = (EditText) view.findViewById(R.id.order_create_et_category);
        etWeight = (EditText) view.findViewById(R.id.order_create_et_weight);
        etMoneyItem = (EditText) view.findViewById(R.id.order_create_et_money_item);
        etMoneyShip = (EditText) view.findViewById(R.id.order_create_et_money_ship);
        etPhoneSender = (EditText) view.findViewById(R.id.order_create_et_phone_sender);
        etPhoneReceiver = (EditText) view.findViewById(R.id.order_create_et_phone_receiver);
        etNote = (EditText) view.findViewById(R.id.order_create_et_note);
        btnOk = (Button) view.findViewById(R.id.order_create_btn_ok);

    }

    private void saveOrders(DatabaseReference databaseReference){
        databaseReference.child("address_send").setValue(etAddressSend.getText().toString());
        databaseReference.child("address_receive").setValue(etAddressReceive.getText().toString());
        databaseReference.child("time_out").setValue(etTimeOut.getText().toString());
        databaseReference.child("category").setValue(etCategory.getText().toString());
        databaseReference.child("weight").setValue(etWeight.getText().toString());
        databaseReference.child("money_item").setValue(etMoneyItem.getText().toString());
        databaseReference.child("money_ship").setValue(etMoneyShip.getText().toString());
        databaseReference.child("phone_sender").setValue(etPhoneSender.getText().toString());
        databaseReference.child("phone_receiver").setValue(etPhoneReceiver.getText().toString());
        databaseReference.child("note").setValue(etNote.getText().toString());
    }

    public interface ICreateSuccessListener{
        void onSuccess();
    }
}