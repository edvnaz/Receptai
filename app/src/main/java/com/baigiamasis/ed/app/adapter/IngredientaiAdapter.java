package com.baigiamasis.ed.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.object_data.Ingredientas;

import java.util.ArrayList;


public class IngredientaiAdapter extends ArrayAdapter<Ingredientas> {
    public IngredientaiAdapter(Context context, ArrayList<Ingredientas> ingredientas) {
        super(context, 0, ingredientas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ingredientas ingredientas = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredientai_sarasas, parent, false);
        }
        // Lookup view for data population
        TextView ingredPav = (TextView) convertView.findViewById(R.id.ingred_pavadinimas);
        // Populate the data into the template view using the data object
        ingredPav.setText(ingredientas.getIngred_pav());
        if (!ingredientas.getAmount().equals("null")) {
            ingredPav.append(" -  " + ingredientas.getAmount());
        }
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
