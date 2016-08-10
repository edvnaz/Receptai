package com.baigiamasis.ed.app.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.fragment.FragmentOne;
import com.baigiamasis.ed.app.fragment.FragmentTwo;
import com.baigiamasis.ed.app.object_data.Ingredientas;
import com.baigiamasis.ed.app.object_data.Receptas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FragmentOne.OnHeadlineSelectedListener, FragmentTwo.OnHeadlineSelectedListener {
    private final String url1 = "http://smkbaig.esy.es/receptai_gauti.php";
    private final String url2 = "http://smkbaig.esy.es/ingredientai_gauti.php";

    PagerAdapter pagerAdapter;
    //-------------------------------------
    RequestQueue requestQueue;
    ArrayList<Receptas> receptasAList = new ArrayList<>();
    ArrayList<Ingredientas> ingredientasAList = new ArrayList<>();
    Receptas receptas;
    Ingredientas ingredientas;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Log.w("myApp", "onCreate -started- ");
            createToolbar();
            getReceptaiArray(url1);
            createViewPagerAndTabs();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Log.w("myApp", "P onCreateOptionsMenu -started- ");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.hint));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //paspaudus paieskos myguka perkeliama i 1 fragmenta
                viewPager.setCurrentItem(0);
            }
        });

        searchView.setOnQueryTextListener(

                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.w("myApp", "onQueryTextSubmit query = " + query);
                        FragmentOne fragment = (FragmentOne) pagerAdapter.fragmentList.get(0);
                        fragment.refresh(query, true);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.w("myApp", "onQueryTextChange ");
                        FragmentOne fragment = (FragmentOne) pagerAdapter.fragmentList.get(0);
                        fragment.refresh(newText, false);

                        return false;
                    }
                });
        return true;
    }

    private void createToolbar() {
        Log.w("myApp", "createToolbar -started- ");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void createViewPagerAndTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        Log.w("myApp", " > createViewPagerAndTabs ");
        pagerAdapter.addFragment(FragmentOne.createFragment(), getString(R.string.tab_1));
        pagerAdapter.addFragment(FragmentTwo.createFragment(), getString(R.string.tab_2));

        Log.w("myApp", " createViewPagerAndTabs > ");
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
/*
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            // optional
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // optional
            @Override
            public void onPageSelected(int position) {
            }

            // optional
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/

    }


    //kai paskaudzia toolbaro iconas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onArticleSelected(int position) {

    }

    //- - - -  V O L L E Y    I N G R E D  - - - -
    public void getIngredientaiArray(String url, String q) {
        Log.w("myApp", "getIngredientaiArray ");

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ingredientasAList.clear();
                            JSONArray ja = response.getJSONArray("Ingredients");
                            for (int i = 0; i < ja.length(); i++) {
                                //gaunami: ingred.. id, pav, type
                                JSONObject jsonObject = ja.getJSONObject(i);
                                ingredientas = new Ingredientas();
                                ingredientas.setAmount(jsonObject.getString("amount"));
                                ingredientas.setIngred_ID(jsonObject.getInt("ingred_id"));
                                ingredientas.setIngred_pav(jsonObject.getString("ingred_pav"));
                                ingredientas.setType(jsonObject.getInt("type"));

                                //Log.w("myApp", "ingredientasAList.add(receptas); " + ingredientas.getType());
                                ingredientasAList.add(ingredientas);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        myAdapter.setReceptasList(receptasAList);
                        fromActivityToFragment2(ingredientasAList);
                        Log.w("myApp", "fromActivityToFragment2");
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                            }
                        }
                );

        requestQueue.add(jsObjRequest);
        Log.w("myApp", "requestQueue.add(jsObjRequest);");
    }

    //- - - -  V O L L E Y    R E C  - - - -
    public void getReceptaiArray(String url) {

        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//        dialog.setTitle("Please Wait");
        dialog.setMessage("Gaunami receptai");
        dialog.setCancelable(false);
        dialog.show();


        Log.w("myApp", "getReceptaiArray ");
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest


                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        try {
                            int checkID = 0;
                            receptasAList.clear();
                            JSONArray ja = response.getJSONArray("receptai");
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonObject = ja.getJSONObject(i);
                                if (checkID != jsonObject.getInt("rec_id")) {
                                    receptas = new Receptas();
                                }
                                ingredientas = new Ingredientas();
                                ingredientas.setIngred_pav(jsonObject.getString("ingred_pav"));
                                ingredientas.setIngred_ID(jsonObject.getInt("ingred_id"));
                                ingredientas.setAmount(jsonObject.getString("amount"));
                                ingredientas.setType(jsonObject.getInt("type"));
                                receptas.setIngredientas(ingredientas);

                                if (checkID != jsonObject.getInt("rec_id")) {
                                    receptas.setId(jsonObject.getInt("rec_id"));
                                    receptas.setPav(jsonObject.getString("rec_pav"));
                                    receptas.setApras(jsonObject.getString("aprasymas"));
                                    receptas.setIngredSk(jsonObject.getInt("ingred_sk"));

                                    Log.w("myApp", "receptasAList.add(receptas); " + receptas.getApras());
                                    receptasAList.add(receptas);
                                }
                                checkID = jsonObject.getInt("rec_id");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        myAdapter.setReceptasList(receptasAList);
                        fromActivityToFragment1(receptasAList);
                        //tik tada gaunamas atskiras ingredientu sarasas
                        getIngredientaiArray(url2, "");


                        Log.w("myApp", "fromActivityToFragment1");
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }

                );


        requestQueue.add(jsObjRequest);
        Log.w("myApp", "requestQueue.add(jsObjRequest);");
    }


    public void filtruotiRList(ArrayList<Ingredientas> ingredSearchAList) {
        FragmentOne fragment = (FragmentOne) pagerAdapter.fragmentList.get(0);

        fragment.finterByIngred(ingredSearchAList);
    }

    public void filtruotiRListEmpty() {
        FragmentOne fragment = (FragmentOne) pagerAdapter.fragmentList.get(0);
        fragment.filtruotiRListEmpty();
    }

    public void fromActivityToFragment1(ArrayList<Receptas> arrayList) {
        FragmentOne fragment = (FragmentOne) pagerAdapter.fragmentList.get(0);
        Log.w("myApp", "fromActivityToFragment1 " + arrayList.get(0).getPav());
        fragment.getArrayList(arrayList);
    }

    public void fromActivityToFragment2(ArrayList<Ingredientas> arrayList) {
        FragmentTwo fragment2 = (FragmentTwo) pagerAdapter.fragmentList.get(1);
        Log.w("myApp", "fromActivityToFragment2 " + ingredientasAList.get(0).getType());
        //Log.w("myApp", "fromActivityToFragments size " + ingredientasAList.size());
        fragment2.getArrayList(arrayList);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}
