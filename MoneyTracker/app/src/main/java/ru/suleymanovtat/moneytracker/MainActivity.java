package ru.suleymanovtat.moneytracker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import ru.suleymanovtat.moneytracker.fragments.CostsFragment;
import ru.suleymanovtat.moneytracker.fragments.NewEntryFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNewEntry = (Button) findViewById(R.id.btn_new_entry);
        btnNewEntry.setOnClickListener(view -> showFragment(new NewEntryFragment()));
        Button btnCosts = (Button) findViewById(R.id.btn_costs);
        btnCosts.setOnClickListener(view -> showFragment(new CostsFragment()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
