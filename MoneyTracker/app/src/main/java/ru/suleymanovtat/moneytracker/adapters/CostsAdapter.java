package ru.suleymanovtat.moneytracker.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.models.Costs;

public class CostsAdapter extends RecyclerView.Adapter<CostsAdapter.ViewHolder> {

    private ArrayList<Costs> listCosts;
    private Context context;

    public CostsAdapter(Context context, ArrayList<Costs> listCosts) {
        this.context = context;
        this.listCosts = listCosts;
    }

    @Override
    public CostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_costs, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Costs costs = listCosts.get(position);
        holder.tvName.setText(costs.getName());
        holder.tvPrice.setText(String.valueOf(costs.getPrice()));
        if (context != null) {
            String ruble = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ?
                    context.getResources().getString(R.string.ruble) :
                    context.getResources().getString(R.string.ruble21);
            holder.tvRuble.setText(ruble);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listCosts == null ? 0 : listCosts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvRuble;

        private ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvRuble = itemView.findViewById(R.id.tv_ruble);
        }
    }
}
