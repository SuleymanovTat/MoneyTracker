package ru.suleymanovtat.moneytracker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.adapters.CostsAdapter;
import ru.suleymanovtat.moneytracker.models.Costs;

public class CostsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_costs, container, false);
        final RecyclerView items = view.findViewById(R.id.recycler_costs);
        ArrayList<Costs> listCosts = new ArrayList<>();
        listCosts.add(new Costs("Спички", 5.555));
        listCosts.add(new Costs("Молоко", 45));
        listCosts.add(new Costs("Хлеб", 130));
        listCosts.add(new Costs("Бензин", 1136));
        listCosts.add(new Costs("Шоколад", 11150.));
        listCosts.add(new Costs("Яблоко", 111175));
        listCosts.add(new Costs("Картофель", 1111140.69));
        listCosts.add(new Costs("Морковь", 1111130));
        listCosts.add(new Costs("Лук", 50));
        listCosts.add(new Costs("салат летний", 40));
        items.setAdapter(new CostsAdapter(getActivity(),listCosts));
        return view;
    }
}
