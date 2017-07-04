package ru.suleymanovtat.moneytracker.fragments;

import android.app.Activity;
import android.content.Intent;
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
import ru.suleymanovtat.moneytracker.models.Item;

public class ItemsFragment extends Fragment {

    private static final int REQUEST_CODE = 120;
    private static final String ITEM_KEY = "item";
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
        final RecyclerView items = (RecyclerView) view.findViewById(R.id.recycler_costs);
        ArrayList<Item> listItems = new ArrayList<>();
        itemAdapter = new ItemAdapter(getActivity(), listItems);
        items.setAdapter(itemAdapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(items);
        fab.setOnClickListener(view1 -> {
            addItems();
        });
        type = getArguments().getString(BUNDLE_TYPE);
        loadItems();
        return view;
    }

    private void addItems() {
        AddItemFragment fragment = new AddItemFragment();
        fragment.setTargetFragment(ItemsFragment.this, REQUEST_CODE);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maim_container, fragment).addToBackStack("null").commit();

//        getLoaderManager().initLoader(LOADER_ADD, null, new LoaderManager.LoaderCallbacks<AddResult>() {
//            @Override
//            public Loader<AddResult> onCreateLoader(int id, Bundle args) {
//                return new AsyncTaskLoader<AddResult>(getContext()) {
//                    @Override
//                    public AddResult loadInBackground() {
//                        try {
//                            return api.add("Молоко", 5, type).execute().body();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                    }
//                };
//
//            }
//
//            @Override
//            public void onLoadFinished(Loader<AddResult> loader, AddResult data) {
//                if (data == null) {
//                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
//                } else {
//                    itemAdapter.addItem(data);
//                }
//            }
//
//            @Override
//            public void onLoaderReset(Loader<AddResult> loader) {
//
//            }
//        }).forceLoad();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Item item = data.getParcelableExtra(ITEM_KEY);
            itemAdapter.addItem(item);
        }
    }
}
