package ru.suleymanovtat.moneytracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ru.suleymanovtat.moneytracker.adapters.ViewPagerAdapter;
import ru.suleymanovtat.moneytracker.fragments.BalanceFragment;
import ru.suleymanovtat.moneytracker.fragments.ItemsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItemsFragment(), getString(R.string.costs));
        adapter.addFragment(new ItemsFragment(), getString(R.string.income));
        adapter.addFragment(new BalanceFragment(), getString(R.string.balance));
        viewPager.setAdapter(adapter);
    }
}
