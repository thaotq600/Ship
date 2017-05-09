package com.example.quocthao.supership.shop.manage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quocthao.supership.CustomDialog;
import com.example.quocthao.supership.R;
import com.example.quocthao.supership.objects.ObjBill;
import com.example.quocthao.supership.objects.ObjInfo;
import com.example.quocthao.supership.shop.services.DataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import bolts.Continuation;
import bolts.Task;

public class WaitShipFragment extends Fragment {

    private ListView lvBill;
    private QuickAdapter quickAdapter;
    private FirebaseUser userShop;
    private DatabaseReference drShop;
    private ArrayList<ObjBill> listBills;
    private DataService dataService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_manage_wait_ship, container, false);
        userShop = FirebaseAuth.getInstance().getCurrentUser();
        drShop = FirebaseDatabase.getInstance().getReference();
        listBills = new ArrayList<>();
        dataService = new DataService();

        lvBill = (ListView) view.findViewById(R.id.manage_wait_ship_lv_bill);

        quickAdapter = new QuickAdapter<ObjBill>(getContext(), R.layout.item_orders_ship) {
            @Override
            protected void convert(BaseAdapterHelper helper, ObjBill item) {
                helper.setText(R.id.item_bill_address_send, item.getAddressSend())
                        .setText(R.id.item_bill_address_receive, item.getAddressReceive());
            }

        };

        dataService.loadBill().continueWith(new Continuation<HashMap<String,ObjBill>, Object>() {
            @Override
            public Object then(Task<HashMap<String, ObjBill>> task) throws Exception {

                for (HashMap.Entry<String, ObjBill> bill : task.getResult().entrySet()) {
                    listBills.add(bill.getValue());
                }

                quickAdapter.addAll(listBills);
                lvBill.setAdapter(quickAdapter);

                readBills();

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        return view;
    }

    private void readBills(){

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDialog dialog = new CustomDialog(getContext(), R.layout.dialog_content_bill);

                final TextView etAddressSend = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_address_send);
                final TextView etAddressReceive = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_address_receive);
                final TextView etTimeOut = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_time_out);
                final TextView etCategory = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_category);
                final TextView etWeight = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_weight);
                final TextView etMoneyItem = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_money_item);
                final TextView etMoneyShip = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_money_ship);
                final TextView etPhoneSender = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_phone_sender);
                final TextView etPhoneReceiver = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_phone_receiver);
                final TextView etNote = (TextView) dialog.getView().findViewById(R.id.content_bill_tv_note);



                etAddressSend.append(listBills.get(position).getAddressSend());
                etAddressReceive.append(listBills.get(position).getAddressReceive());
                etTimeOut.append(listBills.get(position).getTimeOut());
                etCategory.append(listBills.get(position).getCategory());
                etWeight.append(listBills.get(position).getWeight());
                etMoneyItem.append(listBills.get(position).getMoneyItem());
                etMoneyShip.append(listBills.get(position).getMoneyShip());
                etPhoneSender.append(listBills.get(position).getPhoneSender());
                etPhoneReceiver.append(listBills.get(position).getPhoneReceiver());
                etNote.append(listBills.get(position).getNote());

                dialog.setCancelable(true);
                dialog.show();


            }
        });
    }
}
