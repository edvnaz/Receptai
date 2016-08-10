package com.baigiamasis.ed.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.baigiamasis.ed.app.NonScrollListView;
import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.adapter.IngredientaiAdapter;
import com.baigiamasis.ed.app.object_data.Ingredientas;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    String pavadinimas, aprasymas;
    TextView aprasymasTextView;
    int ingredSK = 0;
    ArrayList<Ingredientas> ingrediantaiArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        aprasymasTextView = (TextView) findViewById(R.id.aprasymas);
        getIntents();
        initToolbar();
        putYesList();
//        putNoList();
        putAprasymas();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void putAprasymas() {
        aprasymasTextView.setText(aprasymas);
    }

    private void putNoList() {
        // Create the adapter to convert the array to views
        IngredientaiAdapter adapter = new IngredientaiAdapter(this, ingrediantaiArrayList);
        // Attach the adapter to a ListView
//        listView = (ListView) findViewById(R.id.ingred_nera_listView);
//        listView.setAdapter(adapter);


        NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.ingred_nera_nonListView);
        non_scroll_list.setAdapter(adapter);

    }

    private void putYesList() {
        IngredientaiAdapter adapter = new IngredientaiAdapter(this, ingrediantaiArrayList);
//        listView = (ListView) findViewById(R.id.ingred_yra_nonListView);
//        listView.setAdapter(adapter);

        NonScrollListView non_scroll_list = (NonScrollListView) findViewById(R.id.ingred_yra_nonListView);
        non_scroll_list.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(pavadinimas);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    public void getIntents() {
        this.pavadinimas = getIntent().getStringExtra("pavadinimas");
        this.aprasymas = getIntent().getStringExtra("aprasymas");
        Log.w("MyApp", aprasymas);
        ingredSK = getIntent().getIntExtra("ingredSK", 0);
        ingrediantaiArrayList = getIntent().getParcelableArrayListExtra("arrayList");
    }


}
