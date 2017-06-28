package ru.suleymanovtat.moneytracker.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.suleymanovtat.moneytracker.fragments.ItemsFragment;
import ru.suleymanovtat.moneytracker.models.Item;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);
        Log.e("debug", "pos " + position);
        Bundle bundle = new Bundle();
        if (position == 0)
            bundle.putString(ItemsFragment.BUNDLE_TYPE, Item.TYPE_EXPENSE);
        if (position == 1)
            bundle.putString(ItemsFragment.BUNDLE_TYPE, Item.TYPE_INCOME);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}