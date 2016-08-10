package com.baigiamasis.ed.app.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.activity.MainActivity;
import com.baigiamasis.ed.app.adapter.ListViewIngred2Adapter;
import com.baigiamasis.ed.app.object_data.Ingredientas;

import java.util.ArrayList;


/*
 * 2-as ViewPager Tabas skirtas paieskai pagal ingredientus paieskai
 *
 */
public class FragmentTwo extends Fragment {
    RelativeLayout relativeLayout;
    OnHeadlineSelectedListener mCallback;
    FrameLayout frameLayout;
    LayoutInflater inflater;
    LinearLayout linearLayout;
    ListView listView;
    ImageButton buttonBack;
    ImageButton buttonOK;
    ImageButton buttonClear;
    TextView search_text;
    View view;
    String url3 = "http://smkbaig.esy.es/search_ing_rec.php";
    // GridView masyvas ( 1-mas langas )
    String s[] = {"Mėsa", "Daržovės", "Vaisiai", "Grūdai", "Pienas", "Prieskoniai", "Kiti"};

    ArrayList<String> ingredientaiList = new ArrayList<>();

    // Pirminiai rezultatai
    ArrayList<Ingredientas> ingredientasAList = new ArrayList<>();
    // Filtruoti pagal "type" rezultatai
    ArrayList<Ingredientas> tempAList = new ArrayList<>();
    // Galutiniai rezultatai
    ArrayList<Ingredientas> ingredSearchAList = new ArrayList<>();


    public static FragmentTwo createFragment() {
        FragmentTwo fragmentTwo = new FragmentTwo();
        Log.w("myApp", "FragmentTwo ");

        return fragmentTwo;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("myApp", "B onCreateView ");
        view = inflater.inflate(R.layout.fragment_two, container, false);

        search_text = (TextView) view.findViewById(R.id.blank_result_text);

        buttonClear(view);
        buttonBack(view);
        buttonOK(view);

        return view;
    }

    //gali buti nereikalingas
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void getArrayList(final ArrayList<Ingredientas> ingredientasAList) {
//        this.ingredientasAList = ingredientasAList;
        for (Ingredientas i : ingredientasAList) {
            Ingredientas copy = new Ingredientas(i);
            this.ingredientasAList.add(copy);
        }

        if (ingredientasAList.size() != 0) {
            Log.w("myApp", "1GridAdapter " + this.ingredientasAList.get(0).getIngred_pav());
        } else {
            Log.w("myApp", "1GridAdapter TUSCIA");
        }
//================================================
        menuGridViewSearch();
    }

    public void menuGridViewSearch() {
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.blank_search_place);
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.blank_search_1, null);

        frameLayout.addView(relativeLayout);
        relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.blank_search_1);
        //gridAdapter = new GridAdapter(getActivity(), s);

        setListener();
        //gridView.setAdapter(gridAdapter);
//        gridAdapter.notifyDataSetChanged();

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(getActivity(), "" + ingredientasAList.get(position).getIngred_ID(),
//                        Toast.LENGTH_SHORT).show();
//
//                menuListViewSearch(position);
//            }
//        });
    }

    public void setListener() {
        ImageButton one = (ImageButton) view.findViewById(R.id.butt1);
        ImageButton two = (ImageButton) view.findViewById(R.id.butt2);
        ImageButton three = (ImageButton) view.findViewById(R.id.butt3);
        ImageButton four = (ImageButton) view.findViewById(R.id.butt4);
        ImageButton five = (ImageButton) view.findViewById(R.id.butt5);
        ImageButton six = (ImageButton) view.findViewById(R.id.butt6);
        Button seven = (Button) view.findViewById(R.id.butt7);

        one.setTag(0);
        two.setTag(1);
        three.setTag(2);
        four.setTag(3);
        five.setTag(4);
        six.setTag(5);
        seven.setTag(6);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                menuListViewSearch((int) v.getTag());
            }
        };

        one.setOnClickListener(onClickListener);
        two.setOnClickListener(onClickListener);
        three.setOnClickListener(onClickListener);
        four.setOnClickListener(onClickListener);
        five.setOnClickListener(onClickListener);
        six.setOnClickListener(onClickListener);
        seven.setOnClickListener(onClickListener);
    }

    /**
     * Ingredientu paiesko meniu 2-as langas (ingredientu sarasas)
     * <p>
     * Filtruoja pagal type id ingredientasAList Ingredientas objektus ir isrenka
     * atitinkama tipa ir perkelia i sioje funkcijoje naudojama tempAList
     */
    public void menuListViewSearch(int position) {
        frameLayout.removeView(relativeLayout);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.blank_search_2, null);
        TextView textView = (TextView) linearLayout.findViewById(R.id.maisto_grupe);
        textView.setText(s[position]);
        frameLayout.addView(linearLayout);


        //listView reference
        listView = (ListView) getActivity().findViewById(R.id.blank_search_listView);
        ListViewIngred2Adapter adapter = new ListViewIngred2Adapter(getActivity(), addIngredientaiList(position));
        listView.setAdapter(adapter);


        buttonBack.setVisibility(View.VISIBLE);
        buttonOK.setVisibility(View.VISIBLE);

        //klauso ingredientu pasirinkimo, 2-jo lango paspaudimus
        //naudojama 'ingredientasAList' kopijos ArrayList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), " " + tempAList.get(position).getIngred_pav() + " id: " +
                                tempAList.get(position).getIngred_ID(),
                        Toast.LENGTH_SHORT).show();

                checked(position);

            }
        });

    }


    // ListView elementu pazyejimo elgsena
    public void checked(int position) {
        if (tempAList.get(position).isChecked()) {
            tempAList.get(position).setChecked(false);
            listView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
        } else {
            tempAList.get(position).setChecked(true);
            listView.getChildAt(position).setBackgroundColor(Color.GREEN);
        }
    }


    // Filtravimas pagal 'type'
    public ArrayList<Ingredientas> addIngredientaiList(int position) {
        tempAList.clear();

        for (Ingredientas i : ingredientasAList) {
            if (i.getType() == position) {
                Ingredientas copy = new Ingredientas(i);
                tempAList.add(copy);
            }
        }
        return tempAList;
    }

    //paieskos meniu Back mygtukas listener
    public void buttonBack(View v) {
        buttonBack = (ImageButton) v.findViewById(R.id.blank_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Isvalomas Temp ArrayList
                tempAList.clear();

                // Kuriamas pirmasis langas ( GridView )
                frameLayout.removeView(linearLayout);
                buttonBack.setVisibility(View.GONE);
                buttonOK.setVisibility(View.GONE);
                menuGridViewSearch();


                Log.w("myApp", "B blank_button_back ");
            }
        });
    }

    public void buttonOK(View v) {
        buttonOK = (ImageButton) v.findViewById(R.id.blank_button_ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * Perkeliami Temp ArrayList elementai i Final ArrayList
                 * Suranda tik pazymetus elementus ir juos perkelia
                 */
                if (ingredSearchAList.isEmpty()) {
                    // Yra tuscia ir ikeliama iskarto
                    for (Ingredientas i : tempAList) {
                        if (i.isChecked()) {
                            Ingredientas copy = new Ingredientas(i);
                            ingredSearchAList.add(copy);
                            search_text.append(copy.getIngred_pav() + ", ");
                        }
                    }
                } else {
                    boolean toAdd = true;
                    // Nera tuscia, pries keliant patiktinama
                    for (int n = 0; n < tempAList.size(); n++) {
                        for (int nn = 0; nn < ingredSearchAList.size(); nn++) {
                            if (tempAList.get(n).getIngred_pav().equals(ingredSearchAList.get(nn).getIngred_pav())) {
                                toAdd = false;
                            }
                        }
                        if (toAdd) {
                            if (tempAList.get(n).isChecked()) {
                                Ingredientas copy = new Ingredientas(tempAList.get(n));
                                ingredSearchAList.add(copy);
                                search_text.append(copy.getIngred_pav() + ", ");
                            }
                        }
                        toAdd = true;
                    }
                }

                // Isvalomas Temp ArrayList
                tempAList.clear();

                // Kuriamas pirmasis langas ( GridView )
                frameLayout.removeView(linearLayout);
                buttonBack.setVisibility(View.GONE);
                buttonOK.setVisibility(View.GONE);

                // Atnaujina paiesko rezultatus
//                makeCustomVolleySearchRequest(ingredSearchAList);
                kaskokiaNesamoneBeVolley();

                // Sukuria pirmini GridView meniu
                menuGridViewSearch();
                Log.w("myApp", "B blank_button_ok ");
            }
        });

    }

    public void buttonClear(View v) {
        buttonClear = (ImageButton) v.findViewById(R.id.blank_button_delete);
        buttonClear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Isvalo Final search ArrayList
                search_text.setText(null);
                ingredSearchAList.clear();

                MainActivity activityName = (MainActivity) getActivity();
                if (activityName != null) {
                    activityName.filtruotiRListEmpty();
                }

            }
        });

    }


    public void kaskokiaNesamoneBeVolley() {
        MainActivity activityName = (MainActivity) getActivity();
        if (activityName != null) {
            activityName.filtruotiRList(ingredSearchAList);
        }
    }


    /*
    public void makeCustomVolleySearchRequest(ArrayList<Ingredientas> finalSearchAList) {
        x = finalSearchAList.size();
        String[] arr = new String[x + 1];
        /*JSONObject jsonObject = new JSONObject();
        for (int i = 1; i <= x; i++) {
            arr[i] = String.valueOf(ingredSearchAList.get(i-1).getIngred_ID());
            try {
                jsonObject.put("params_" + i, arr[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}*//*
        Map<String, String> params = new HashMap<String, String>();
        //params.put("params", jsonObject.toString());
        params.put("x", String.valueOf(3));
        for (int i = 1; i <= x; i++) {
            params.put("param_" + i, String.valueOf(finalSearchAList.get(i - 1).getIngred_ID()));
        }
        Log.w("myApp", String.valueOf(finalSearchAList.get(0).getIngred_ID()));

        //-----------------
        RequestQueue que = Volley.newRequestQueue(getActivity());

        CustomJobjectRequest jsObjRequest = new CustomJobjectRequest(Request.Method.POST, url3, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.w("myApp _atsakymasCustomV", String.valueOf(response));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {

                Log.w("myApp", response.toString());
                Toast.makeText(getActivity(), "Unable to Send Data!" + " " + response.toString(), Toast.LENGTH_LONG).show();
            }
        });

        que.add(jsObjRequest);

    }
    */

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }
//--
}
