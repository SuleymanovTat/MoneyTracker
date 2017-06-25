package ru.suleymanovtat.moneytracker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.suleymanovtat.moneytracker.R;

public class NewEntryFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private TextView tvAdd;
    private EditText editName;
    private EditText editPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_entry, container, false);
        tvAdd = view.findViewById(R.id.tv_add);
        editName = view.findViewById(R.id.edit_name);
        editPrice = view.findViewById(R.id.edit_price);
        tvAdd.setOnClickListener(this);
        editName.addTextChangedListener(this);
        editPrice.addTextChangedListener(this);
        return view;
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
        Toast.makeText(getActivity(), R.string.click_button, Toast.LENGTH_LONG).show();
    }
}
