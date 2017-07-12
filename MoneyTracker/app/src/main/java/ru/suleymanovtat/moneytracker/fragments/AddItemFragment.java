package ru.suleymanovtat.moneytracker.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.models.Item;

public class AddItemFragment extends BaseFragment implements TextWatcher, View.OnClickListener {

    private TextView tvAdd;
    private EditText editName;
    private EditText editPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        setHasOptionsMenu(isVisible());
        actionBar(getString(R.string.new_entry), true);
        tvAdd = (TextView) view.findViewById(R.id.tv_add);
        editName = (EditText) view.findViewById(R.id.edit_name);
        editPrice = (EditText) view.findViewById(R.id.edit_price);
        tvAdd.setOnClickListener(this);
        tvAdd.setEnabled(false);
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
        hideKeyboard();
        Intent intent = new Intent();
        intent.putExtra("item", new Item(editName.getText().toString().trim(), Integer.valueOf(editPrice.getText().toString().trim())));
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    private void hideKeyboard() {
        if (getActivity() != null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            hideKeyboard();
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        actionBar(getString(R.string.app_name), false);
    }
}
