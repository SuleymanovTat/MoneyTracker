package ru.suleymanovtat.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.suleymanovtat.moneytracker.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (((MoneyTrackerApp) getApplication()).isLoggedIn()) {
            getSupportActionBar().setElevation(0);
            getSupportFragmentManager().beginTransaction().replace(R.id.maim_container, new MainFragment(), "main").commit();
        } else {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
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
