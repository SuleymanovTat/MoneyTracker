package ru.suleymanovtat.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private TextView tvAdd;
    private EditText editName;
    private EditText editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        editName = (EditText) findViewById(R.id.edit_name);
        editPrice = (EditText) findViewById(R.id.edit_price);
        tvAdd.setOnClickListener(this);
        editName.addTextChangedListener(this);
        editPrice.addTextChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence text, int start, int before, int count) {
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        tvAdd.setEnabled(!TextUtils.isEmpty(editName.getText().toString().trim())
                && !TextUtils.isEmpty(editPrice.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, R.string.click_button, Toast.LENGTH_LONG).show();
    }
}
