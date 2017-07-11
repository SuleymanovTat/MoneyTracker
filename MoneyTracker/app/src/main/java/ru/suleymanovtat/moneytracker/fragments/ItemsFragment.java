package ru.suleymanovtat.moneytracker.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.suleymanovtat.moneytracker.MoneyTrackerApp;
import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.adapters.ItemAdapter;
import ru.suleymanovtat.moneytracker.api.MoneyTrackerApi;
import ru.suleymanovtat.moneytracker.models.AddResult;
import ru.suleymanovtat.moneytracker.models.Item;
import ru.suleymanovtat.moneytracker.models.Result;

public class ItemsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ItemAdapter.OnLongItemClickListener {

    private static final int REQUEST_CODE = 120;
    private static final int REQUEST_CODE_REMOVE = 121;
    private static final String ITEM_KEY = "item";
    public static final String BUNDLE_TYPE = "type";
    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_REMOVE = 2;
    private MoneyTrackerApi api;
    private String type;
    private ItemAdapter itemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        api = ((MoneyTrackerApp) getActivity().getApplication()).getApi();
        final RecyclerView items = (RecyclerView) view.findViewById(R.id.recycler_costs);
        ArrayList<Item> listItems = new ArrayList<>();
        itemAdapter = new ItemAdapter(getActivity(), listItems);
        items.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(this);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(items);
        fab.setOnClickListener(view1 -> {
            addItems();
        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        type = getArguments().getString(BUNDLE_TYPE);
        loadItems();
        return view;
    }


    private void addItems() {
        AddItemFragment fragment = new AddItemFragment();
        fragment.setTargetFragment(ItemsFragment.this, REQUEST_CODE);
        MainFragment myFragment = (MainFragment) getFragmentManager().findFragmentByTag("main");
        getActivity().getSupportFragmentManager().beginTransaction().hide(myFragment).add(R.id.maim_container, fragment).addToBackStack("null").commit();
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
                swipeRefreshLayout.setRefreshing(false);
                actionBar(getString(R.string.app_name), false);
                fab.setVisibility(View.VISIBLE);
                setHasOptionsMenu(false);
                itemAdapter.update();
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
            addItem(item);
        }
        if (requestCode == REQUEST_CODE_REMOVE && resultCode == Activity.RESULT_OK && data != null) {
            remove();
        } else {
            itemAdapter.update();
            setHasOptionsMenu(false);
            actionBar(getString(R.string.app_name), false);
        }

    }

    public void remove() {
        for (Item item : itemAdapter.listRemove) {
            getLoaderManager().initLoader(LOADER_REMOVE, null, new LoaderManager.LoaderCallbacks<Result>() {
                @Override
                public Loader<Result> onCreateLoader(int id, Bundle args) {
                    return new AsyncTaskLoader<Result>(getContext()) {
                        @Override
                        public Result loadInBackground() {
                            try {
                                return api.remove(item.getId()).execute().body();
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<Result> loader, Result data) {
                    if (data == null) {
                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    } else {
                        itemAdapter.remove(item);
                    }
                    loadItems();
                }

                @Override
                public void onLoaderReset(Loader<Result> loader) {

                }
            }).forceLoad();
        }
    }


    private void addItem(Item item) {
        api.addItem(item.getName(), item.getPrice(), type).enqueue(new Callback<AddResult>() {
            @Override
            public void onResponse(@NonNull Call<AddResult> call, @NonNull Response<AddResult> response) {
                if (response.isSuccessful()) {
                    itemAdapter.addItem(item);
                } else {
                    Toast.makeText(getActivity(), String.valueOf(response.errorBody()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddResult> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        loadItems();
    }

    @Override
    public void onLongItemClick(int size) {
        if (size == 0) {
            setHasOptionsMenu(false);
            fab.setVisibility(View.VISIBLE);
            actionBar(getString(R.string.app_name), false);
        } else {
            setHasOptionsMenu(true);
            fab.setVisibility(View.GONE);
            actionBar(size + " выбрано", true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            setHasOptionsMenu(false);
            fab.setVisibility(View.VISIBLE);
            actionBar(getString(R.string.app_name), false);
            itemAdapter.update();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_remove, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_remove) {
            DeleteDialogFragment fragment = new DeleteDialogFragment();
            fragment.setTargetFragment(this, REQUEST_CODE_REMOVE);
            fragment.show(getFragmentManager(), fragment.getClass().getName());
        }
        if (itemId == android.R.id.home) {
            itemAdapter.update();
            setHasOptionsMenu(false);
            actionBar(getString(R.string.app_name), false);
        }
        return super.onOptionsItemSelected(item);
    }

}
