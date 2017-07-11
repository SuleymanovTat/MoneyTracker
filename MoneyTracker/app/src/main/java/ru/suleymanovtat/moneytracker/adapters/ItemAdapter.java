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
import ru.suleymanovtat.moneytracker.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private OnLongItemClickListener listener;
    public ArrayList<Item> listRemove;

    public interface OnLongItemClickListener {
        void onLongItemClick(int position);
    }

    public void setOnItemClickListener(OnLongItemClickListener listener) {
        this.listener = listener;
    }

    private ArrayList<Item> listItems;
    private Context context;

    public ItemAdapter(Context context, ArrayList<Item> listItems) {
        this.context = context;
        this.listItems = listItems;
        listRemove = new ArrayList<>();
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = listItems.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(String.valueOf(item.getPrice()));
        holder.itemView.setBackgroundResource(item.isSelected() ? R.color.colorDustyOrange : R.color.colorName);
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
        return listItems == null ? 0 : listItems.size();
    }

    public void clear() {
        listItems.clear();
    }

    public void addAll(ArrayList<Item> items) {
        this.listItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        listItems.add(0, item);
        notifyDataSetChanged();
    }

    public void remove() {
        listItems.removeAll(listRemove);
        notifyDataSetChanged();
    }

    public void remove(Item item) {
        listItems.remove(item);
        notifyDataSetChanged();
    }

    public void update() {
        for (Item item : listRemove) {
            item.setSelected(false);
        }
        listRemove.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvRuble;
        private View itemView;

        private ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvRuble = (TextView) itemView.findViewById(R.id.tv_ruble);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Item item = listItems.get(getAdapterPosition());
            item.setSelected(!item.isSelected());
            if (item.isSelected()) {
                listRemove.add(item);
            } else {
                listRemove.remove(item);
            }
            notifyItemChanged(getAdapterPosition());
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (listRemove != null) {
                        listener.onLongItemClick(listRemove.size());
                    }
                }
            }
            return true;
        }
    }
}
