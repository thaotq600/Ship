package com.example.quocthao.supership.shop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.quocthao.supership.R;

import java.util.ArrayList;
import java.util.List;

public class ContentShopActivity extends AppCompatActivity{

    private ViewPager vpOrder;
    private TabLayout tlOrder;
    private OrderFragmentAdapter fragmentAdapter;
    private CreateOrdersFragment.ICreateSuccessListener iCreateSuccessListener;

    private int[] tabIcons = {
            R.drawable.ic_orders_map,
            R.drawable.ic_orders_create,
            R.drawable.ic_orders_manage,
            R.drawable.ic_orders_notify
    };
    private int[] tabIconsSelected = {
            R.drawable.ic_orders_map_selected,
            R.drawable.ic_orders_create_selected,
            R.drawable.ic_orders_manage_selected,
            R.drawable.ic_orders_notify_selected
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_content);

        vpOrder = (ViewPager) findViewById(R.id.vp_order);
        tlOrder = (TabLayout) findViewById(R.id.tl_order);

        iCreateSuccessListener = new CreateOrdersFragment.ICreateSuccessListener() {
            @Override
            public void onSuccess() {
                vpOrder.setCurrentItem(0);
                vpOrder.setCurrentItem(2);
            }
        };

        setupViewPager(vpOrder);
        tlOrder.setupWithViewPager(vpOrder);
        setupTabIcons();

    }

    public void setupTabIcons() {
        tlOrder.getTabAt(0).setIcon(tabIconsSelected[0]);
        tlOrder.getTabAt(1).setIcon(tabIcons[1]);
        tlOrder.getTabAt(2).setIcon(tabIcons[2]);
        tlOrder.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        fragmentAdapter = new OrderFragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFrag(new MapOrdersFragment());
        fragmentAdapter.addFrag(new CreateOrdersFragment(iCreateSuccessListener));
        fragmentAdapter.addFrag(new ManageOrdersFragment());
        fragmentAdapter.addFrag(new ListNotifyFragment());
        viewPager.setAdapter(fragmentAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 4; i++) {
                    if(i == position)
                        tlOrder.getTabAt(i).setIcon(tabIconsSelected[i]);
                    else
                        tlOrder.getTabAt(i).setIcon(tabIcons[i]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class OrderFragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public OrderFragmentAdapter(FragmentManager manager) {
            super(manager);
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
