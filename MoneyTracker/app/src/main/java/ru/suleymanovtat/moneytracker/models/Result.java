package ru.suleymanovtat.moneytracker.models;


import android.text.TextUtils;

public class Result {
    private String status;

    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }
}