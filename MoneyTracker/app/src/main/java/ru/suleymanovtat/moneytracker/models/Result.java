package ru.suleymanovtat.moneytracker.models;


import android.text.TextUtils;

public class Result {
    String status;

    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }
}