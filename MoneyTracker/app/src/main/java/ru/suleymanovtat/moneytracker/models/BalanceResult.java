package ru.suleymanovtat.moneytracker.models;

public class BalanceResult extends Result {

    private int total_expenses;
    private int total_income;

    public int getTotal_expenses() {
        return total_expenses;
    }

    public void setTotal_expenses(int total_expenses) {
        this.total_expenses = total_expenses;
    }

    public int getTotal_income() {
        return total_income;
    }

    public void setTotal_income(int total_income) {
        this.total_income = total_income;
    }
}
