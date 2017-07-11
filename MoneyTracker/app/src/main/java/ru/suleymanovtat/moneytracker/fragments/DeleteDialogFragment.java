package ru.suleymanovtat.moneytracker.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.suleymanovtat.moneytracker.R;

public class DeleteDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Intent intent = new Intent();
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.budget_accounting)
                .setMessage(R.string.delete_items)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                })
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }).create();
    }
}
