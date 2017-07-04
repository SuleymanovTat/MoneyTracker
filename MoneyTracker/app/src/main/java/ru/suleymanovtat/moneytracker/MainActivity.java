package ru.suleymanovtat.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.suleymanovtat.moneytracker.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.maim_container, new MainFragment()).commit();
    }
}
