package ru.suleymanovtat.moneytracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.adapters.ItemAdapter;
import ru.suleymanovtat.moneytracker.models.Item;

public class ItemsFragment extends Fragment {

    public static final String BUNDLE_TYPE = "type";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_costs, container, false);
        final RecyclerView items = view.findViewById(R.id.recycler_costs);
        ArrayList<Item> listCosts = new ArrayList<>();
        listCosts.add(new Item("Спички", 5.555));
        listCosts.add(new Item("Молоко", 45));
        listCosts.add(new Item("Хлеб", 130));
        listCosts.add(new Item("Бензин", 1136));
        listCosts.add(new Item("Шоколад", 11150));
        listCosts.add(new Item("Яблоко", 111175));
        listCosts.add(new Item("Картофель", 1111140.69));
        listCosts.add(new Item("Морковь", 1111130));
        listCosts.add(new Item("Лук", 50));
        listCosts.add(new Item("салат летний", 40));
        items.setAdapter(new ItemAdapter(getActivity(),listCosts));
        String type = getArguments().getString(BUNDLE_TYPE);
        return view;
    }
}
