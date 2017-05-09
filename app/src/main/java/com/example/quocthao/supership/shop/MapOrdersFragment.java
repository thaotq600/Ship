package com.example.quocthao.supership.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quocthao.supership.R;

/**
 * Created by Quoc Thao on 4/1/2017.
 */

public class MapOrdersFragment extends Fragment {

    public MapOrdersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean check = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_orders_map, container, false);

//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.btn_wait);
//        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
//        final ImageView ivPlus = (ImageView) view.findViewById(R.id.iv_plus);
//        final ImageView ivMinus = (ImageView) view.findViewById(R.id.iv_minus);
//
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(check) {
//                    ivPlus.setVisibility(View.GONE);
//                    ivMinus.setVisibility(View.VISIBLE);
//                    frameLayout.setVisibility(View.VISIBLE);
//                    check = false;
//                } else {
//                    ivPlus.setVisibility(View.VISIBLE);
//                    ivMinus.setVisibility(View.GONE);
//                    frameLayout.setVisibility(View.GONE);
//                    check = true;
//                }
//            }
//        });

        return view;
    }


}