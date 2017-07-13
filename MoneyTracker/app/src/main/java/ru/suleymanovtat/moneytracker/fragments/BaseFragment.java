package ru.suleymanovtat.moneytracker.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import ru.suleymanovtat.moneytracker.MainActivity;

public class BaseFragment extends Fragment {

    public void actionBar(String title, boolean back) {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(back);
        actionBar.setDisplayShowHomeEnabled(back);
    }
}
