package com.baigiamasis.ed.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baigiamasis.ed.app.object_data.Ingredientas;

import java.util.ArrayList;


/**
 * Antro ingredientu paieskos lango Adapter
 * Naudoja android simple list (android default) ir priskiriamas TextView
 *
 */
public class ListViewIngred2Adapter extends ArrayAdapter<Ingredientas> {

    public ListViewIngred2Adapter(Context context, ArrayList<Ingredientas> ingredientas) {
        super(context, 0, ingredientas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ingredientas ingredientas = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
        // Populate the data into the template view using the data object
        text1.setText(ingredientas.getIngred_pav());

        // Return the completed view to render on screen
        return convertView;
    }



    //true = ON paspaudimai (onClick)
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
