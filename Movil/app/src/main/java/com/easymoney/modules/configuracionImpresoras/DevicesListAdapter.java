package com.easymoney.modules.configuracionImpresoras;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymoney.R;

import java.util.Vector;

public class DevicesListAdapter extends BaseAdapter {

    private Vector<BluetoothItem> items = new Vector<>();
    private DataSetObserver dataObserver;

    public int getCount() {
        return items.size();
    }

    public Object getItem(int index) {
        return items.elementAt(index);
    }

    public long getItemId(int index) {
        return index;
    }

    public int getItemViewType(int index) {
        return 0;
    }

    public View getView(int index, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.bt_device_list_item, null);
        }
        BluetoothItem item = items.get(index);
        TextView nameText = view.findViewById(R.id.bt_item_name);
        TextView macText = view.findViewById(R.id.bt_item_mac);

        nameText.setText(item.name);
        macText.setText(item.macAddress);

        ImageView image = view.findViewById(R.id.bt_item_image);
        if (item.visible) {
            image.setImageResource(R.drawable.ic_bt_on);
        } else {
            image.setImageResource(R.drawable.ic_bt_off);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void registerDataSetObserver(DataSetObserver dataObserver) {
        this.dataObserver = dataObserver;
    }

    public void unregisterDataSetObserver(DataSetObserver arg0) {
        this.dataObserver = null;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int index) {
        return true;
    }

    public void addItem(String name, String macAddress, boolean visible) {
        BluetoothItem item = new BluetoothItem(name, macAddress, visible);
        if (items.contains(item)) {
            items.elementAt(items.indexOf(item)).name = item.name;
            items.elementAt(items.indexOf(item)).visible = item.visible;
        } else {
            items.add(item);
        }
        if (dataObserver != null) dataObserver.onChanged();
    }

    public class BluetoothItem {
        private String name;
        private String macAddress;
        private boolean visible;

        public BluetoothItem(String name, String macAddress, boolean visible) {
            this.name = name;
            this.macAddress = macAddress;
            this.visible = visible;
        }

        @Override
        public String toString() {
            return macAddress;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) return false;
            return (other.toString().equals(toString()));
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }

}