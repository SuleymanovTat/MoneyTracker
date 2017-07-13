package ru.suleymanovtat.moneytracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ru.suleymanovtat.moneytracker.MoneyTrackerApp;
import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.models.BalanceResult;
import ru.suleymanovtat.moneytracker.view.DiagramView;

public class BalanceFragment extends Fragment {

    private TextView balance;
    private TextView expense;
    private TextView income;
    private DiagramView diagram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        balance = (TextView) view.findViewById(R.id.tv_balance);
        expense = (TextView) view.findViewById(R.id.tv_expense);
        income = (TextView) view.findViewById(R.id.tv_income);
        diagram = (DiagramView) view.findViewById(R.id.diagram);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            updateData();
        }
    }

    private void updateData() {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<BalanceResult>() {
            @Override
            public Loader<BalanceResult> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<BalanceResult>(getContext()) {
                    @Override
                    public BalanceResult loadInBackground() {
                        try {
                            return ((MoneyTrackerApp) getActivity().getApplicationContext()).getApi().balance().execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<BalanceResult> loader, BalanceResult balanceResult) {
                if (balanceResult != null && balanceResult.isSuccess()) {
                    balance.setText(getString(R.string.price, balanceResult.getTotal_income() - balanceResult.getTotal_expenses()));
                    expense.setText(getString(R.string.price, balanceResult.getTotal_expenses()));
                    income.setText(getString(R.string.price, balanceResult.getTotal_income()));
                    if (balanceResult.getTotal_expenses() == 0 && balanceResult.getTotal_income() == 0) {
                        balanceResult.setTotal_income(100);
                    }
                    diagram.update(balanceResult.getTotal_expenses(), balanceResult.getTotal_income());
                } else {
                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
                getLoaderManager().destroyLoader(loader.getId());
            }

            @Override
            public void onLoaderReset(Loader<BalanceResult> loader) {

            }
        }).forceLoad();
    }
}
