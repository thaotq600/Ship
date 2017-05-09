package com.example.quocthao.supership.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocthao.supership.R;
import com.example.quocthao.supership.shop.custom_viewpager.CustomeViewPager;
import com.example.quocthao.supership.shop.manage.ShippedFragment;
import com.example.quocthao.supership.shop.manage.ShippingFragment;
import com.example.quocthao.supership.shop.manage.WaitShipFragment;

import java.util.ArrayList;
import java.util.List;

public class ManageOrdersFragment extends Fragment {

    private CustomeViewPager vpManage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_manage, container, false);

        vpManage = (CustomeViewPager) view.findViewById(R.id.vp_manage);

        LinearLayout layoutWait = (LinearLayout) view.findViewById(R.id.btn_wait);
        LinearLayout layoutShipping = (LinearLayout) view.findViewById(R.id.btn_shipping);
        LinearLayout layoutShipped = (LinearLayout) view.findViewById(R.id.btn_shipped);

        final TextView tvWait = (TextView) view.findViewById(R.id.tv_wait);
        final TextView tvShipping = (TextView) view.findViewById(R.id.tv_shipping);
        final TextView tvShipped = (TextView) view.findViewById(R.id.tv_shipped);


        ManageOrdersFragment.ManageAdapter adapter = new ManageOrdersFragment.ManageAdapter(getChildFragmentManager());
        adapter.addFrag(new WaitShipFragment());
        adapter.addFrag(new ShippingFragment());
        adapter.addFrag(new ShippedFragment());
        vpManage.setAdapter(adapter);

        settingViewPager(layoutWait, layoutShipping, layoutShipped, tvWait, tvShipping, tvShipped);

        return view;
    }

    private void settingViewPager(LinearLayout layoutWait, LinearLayout layoutShipping, LinearLayout layoutShipped, final TextView tvWait, final TextView tvShipping, final TextView tvShipped) {
        layoutWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpManage.setCurrentItem(0);
                tvWait.setTextColor(0xffed8c36);
                tvShipping.setTextColor(0xffffffff);
                tvShipped.setTextColor(0xffffffff);
            }
        });

        layoutShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpManage.setCurrentItem(1);
                tvWait.setTextColor(0xffffffff);
                tvShipping.setTextColor(0xffed8c36);
                tvShipped.setTextColor(0xffffffff);
            }
        });

        layoutShipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpManage.setCurrentItem(2);
                tvWait.setTextColor(0xffffffff);
                tvShipping.setTextColor(0xffffffff);
                tvShipped.setTextColor(0xffed8c36);
            }
        });

        vpManage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tvWait.setTextColor(0xffed8c36);
                        tvShipping.setTextColor(0xffffffff);
                        tvShipped.setTextColor(0xffffffff);
                        break;
                    case 1:
                        tvWait.setTextColor(0xffffffff);
                        tvShipping.setTextColor(0xffed8c36);
                        tvShipped.setTextColor(0xffffffff);
                        break;
                    case 2:
                        tvWait.setTextColor(0xffffffff);
                        tvShipping.setTextColor(0xffffffff);
                        tvShipped.setTextColor(0xffed8c36);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ManageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ManageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

}
