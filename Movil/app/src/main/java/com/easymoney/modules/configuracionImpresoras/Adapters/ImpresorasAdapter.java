package com.easymoney.modules.configuracionImpresoras.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easymoney.R;

import java.util.ArrayList;
import java.util.List;

public class ImpresorasAdapter extends BaseAdapter {
    Context context;
    List<String> modelos = new ArrayList<>();

    public ImpresorasAdapter(Context context){
        this.context = context;
        modelos.add("Zebra 220");
        modelos.add("Bixolon R200III");
    }

    @Override
    public int getCount() {
        return modelos.size();
    }

    @Override
    public Object getItem(int i) {
        return modelos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_modelo_impresora, viewGroup, false);
        }

        // get current item to be displayed
        String currentItem = (String)getItem(i);

        // get the TextView for item name and item description
        TextView tvModeloImpresora = (TextView) convertView.findViewById(R.id.tvModelo);

        //sets the text for item name and item description from the current item object
        tvModeloImpresora.setText(currentItem);

        // returns the view for the current row
        return convertView;
    }
}
