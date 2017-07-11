package ru.suleymanovtat.moneytracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";
    private String name;
    private int price;
    private boolean selected;
    private int id;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.price);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
    }

    protected Item(Parcel in) {
        this.name = in.readString();
        this.price = in.readInt();
        this.selected = in.readByte() != 0;
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", selected=" + selected +
                ", id=" + id +
                '}';
    }
}
