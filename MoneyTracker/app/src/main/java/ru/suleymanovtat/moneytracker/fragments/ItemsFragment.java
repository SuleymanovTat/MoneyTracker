package ru.suleymanovtat.moneytracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import ru.suleymanovtat.moneytracker.MoneyTrackerApp;
import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.adapters.ItemAdapter;
import ru.suleymanovtat.moneytracker.api.MoneyTrackerApi;
import ru.suleymanovtat.moneytracker.models.AddResult;
import ru.suleymanovtat.moneytracker.models.Item;

public class ItemsFragment extends Fragment {

    public static final String BUNDLE_TYPE = "type";
    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ADD = 1;
    private static final int LOADER_REMOVE = 2;
    private MoneyTrackerApi api;
    private String type;
    private ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        api = ((MoneyTrackerApp) getActivity().getApplication()).getApi();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            addItems();
        });
        final RecyclerView items = view.findViewById(R.id.recycler_costs);
        ArrayList<Item> listItems = new ArrayList<>();
        itemAdapter = new ItemAdapter(getActivity(), listItems);
        items.setAdapter(itemAdapter);
        type = getArguments().getString(BUNDLE_TYPE);
        loadItems();
        return view;
    }

    private void addItems() {
        getLoaderManager().initLoader(LOADER_ADD, null, new LoaderManager.LoaderCallbacks<AddResult>() {
            @Override
            public Loader<AddResult> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<AddResult>(getContext()) {
                    @Override
                    public AddResult loadInBackground() {
                        try {
                            return api.add("Молоко", 5, type).execute().body();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };

            }

            @Override
            public void onLoadFinished(Loader<AddResult> loader, AddResult data) {
                if (data == null) {
                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                } else {
                    itemAdapter.addItem(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<AddResult> loader) {

            }
        }).forceLoad();
    }

    private void loadItems() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<ArrayList<Item>>() {
            @Override
            public Loader<ArrayList<Item>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<ArrayList<Item>>(getContext()) {
                    @Override
                    public ArrayList<Item> loadInBackground() {
                        try {
                            return api.items(type).execute().body();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> data) {
                if (data == null) {
                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                } else {
                    itemAdapter.clear();
                    itemAdapter.addAll(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<ArrayList<Item>> loader) {

            }
        }).forceLoad();
    }
}
