package com.baigiamasis.ed.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baigiamasis.ed.app.R;

//TODO grid adapter reikia kaskokios tai datos
//TODO datos is main activity dalintis per abu fragment
public class GridAdapter extends BaseAdapter {

    String s[];
    private LayoutInflater layoutinflater;
    private Context context;

    public GridAdapter(Context context, String[] s) {
        this.context = context;
        this.s = s;
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.grid_view_item, parent, false);
            listViewHolder.textView = (TextView) convertView.findViewById(R.id.gridViewItem);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }

//        Log.w("myApp", "GridAdapter " + s[position]);
        listViewHolder.textView.setText(s[position]);

        return convertView;

        //==========================================================
/*
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(ingredientaiAList.get(position).getIngred_pav());
        return textView;


        //========================================================
        /*
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        gridView = new View(context);
        // get layout from xml
        gridView = inflater.inflate(R.layout.fragment_two, null);

        // set value into textview
        TextView textView = (TextView) gridView
                .findViewById(R.id.gridViewItem);
        textView.setText(ingredientaiAList.get(position).getIngred_pav());


        return gridView;*/
    }

    @Override
    public int getCount() {
        return this.s.length;
    }

    @Override
    public Object getItem(int position) {
        return this.s[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView textView;
    }


}