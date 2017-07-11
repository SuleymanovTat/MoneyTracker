package ru.suleymanovtat.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.suleymanovtat.moneytracker.fragments.AuthFragment;
import ru.suleymanovtat.moneytracker.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        if (((MoneyTrackerApp) getApplication()).isLoggedIn()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.maim_container, new MainFragment(), "main").commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.maim_container, new AuthFragment(), "auth").commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
