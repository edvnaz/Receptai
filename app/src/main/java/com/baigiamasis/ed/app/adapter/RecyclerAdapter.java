package com.baigiamasis.ed.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.activity.InfoActivity;
import com.baigiamasis.ed.app.activity.MainActivity;
import com.baigiamasis.ed.app.object_data.Ingredientas;
import com.baigiamasis.ed.app.object_data.Receptas;

import java.util.ArrayList;
import java.util.List;


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //pagrindinis list (gautas Json)
    public ArrayList<Receptas> receptasL = new ArrayList<>();
    //perkelta is receptasL, naudojama tik pavadinimo paieskoje
    public ArrayList<Receptas> searchReceptasList;

    Context context;
    private LayoutInflater layoutInflater;

    public RecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //gauna receptus is Json
    public void setReceptasList(ArrayList<Receptas> receptasL) {
        this.receptasL = receptasL;
        this.searchReceptasList = new ArrayList<Receptas>();
        this.searchReceptasList.addAll(this.receptasL);
        notifyDataSetChanged();
        //notifyItemRangeChanged(0, receptasL.size());
        Log.w("myApp", "recetasL CHECK - " + receptasL.size() );
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w("myApp", "onCreateViewHolder  ");
//        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view, receptasL, context);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Receptas receptas = receptasL.get(position);
        Log.w("myApp", "onBindViewHolder position: " + position + " receptas.pav - " + receptas.getPav());
        holder.itemTextViewPav.setText(receptas.getPav());
        holder.itemTextViewIngred_sk.setText(String.valueOf(receptas.getIngredSk()));
    }

    @Override
    public int getItemCount() {
        return receptasL == null ? 0 : receptasL.size();
    }

    // - - - - - - - - - - - - -  S E A R C H   V I E W  V2! - - - - - - - - -
    public void setFilterv2(Boolean submit, Context context, String newTxt) {
        newTxt = newTxt.toLowerCase();
        final List<Receptas> filteredReceptasList = new ArrayList<>();

        //submit = true, jai paspaustas paieskoks OK mygtukas ir jai receptu ReList NERA tuscias
        if (submit == true & receptasL.size() == 0) {
            Toast toast = Toast.makeText(context, "nerasta", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        } else {
//            receptasL = new ArrayList<>();
            //receptasL kopija searchReceptasList ieskoma paieskos zodzio
            for (Receptas searchReceptas : searchReceptasList) {
                final String searchText = searchReceptas.getPav().toLowerCase();
                if (searchText.contains(newTxt)) {
                    filteredReceptasList.add(searchReceptas);
                }
            }
            //receptasL turinys pakeiciamas tik surastais elementais ir apie tai pranesama (notify)
            receptasL.clear();
            receptasL.addAll(filteredReceptasList);
        }
        //pranesama apie pakeitimus sistemai
        notifyDataSetChanged();
    }

    public void filtruotiRListEmpty() {
        receptasL.clear();
        receptasL.addAll(searchReceptasList);

        notifyDataSetChanged();
    }

    public void finterByIngred(ArrayList<Ingredientas> ingredSearchAList) {
        final List<Receptas> filteredReceptasList = new ArrayList<>();

        //perrenka receptus
        for (int i = 0; i < searchReceptasList.size(); i++) {

            //perrenka recepto ingredinetus
            for (int ii = 0; ii < searchReceptasList.get(i).getIngredientas().size(); ii++) {

                //ziuri ar yra sutampanciu recepto (is Json) ingredinetu su ieskomai ingredientais (is ingred meniu)
                for (int iii = 0; iii < ingredSearchAList.size(); iii++) {

                    if (searchReceptasList.get(i).getIngredientas().get(ii).getIngred_ID() == ingredSearchAList.get(iii).getIngred_ID()) {
                        //tikrinti, kad nebutu pasikartojanciu elementu
                        if (!filteredReceptasList.contains(searchReceptasList.get(i))) {
                            filteredReceptasList.add(searchReceptasList.get(i));
                        }
                    }
                }
            }
        }

        //receptasL (naudojamas ViewHolder) turinys pakeiciamas i surastus elementus ir apie tai pranesama (notify)
        receptasL.clear();
        receptasL.addAll(filteredReceptasList);
        //pranesama apie pakeitimus sistemai
        notifyDataSetChanged();
    }

    //- - - - - - - - - - - - -  S E A R C H   V I E W  #2  - - - - - - - - -
    /*public void setFilter(List<Receptas> receptasFilter, Boolean submit, Context context) {
// be sito veikia naujas Activity paieskoje, neveikia istrinus paieska
        receptasL = new ArrayList<>();
        receptasL.clear();
        receptasL.addAll(receptasFilter);
        if (submit == true & receptasFilter.size() == 0) {
            Toast toast = Toast.makeText(context, "nerasta", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
        notifyDataSetChanged();
    }*/

    /*public List<Receptas> filter(List<Receptas> receptai, String query) {
        query = query.toLowerCase();

        final List<Receptas> filteredReceptasList = new ArrayList<>();

        for (Receptas receptas : receptai) {
            final String text = receptas.getPav().toLowerCase();
            if (text.contains(query)) {
                filteredReceptasList.add(receptas);
            }
        }
        return filteredReceptasList;
    }*/

    // - - -- - - - - - - - -- - - - - T E S T - - - - - - - - - -
    // Filter Class
    /*public void filterr(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        receptasL.clear();
        if (charText.length() == 0) {
            receptasL.addAll(searchReceptasList);
        } else {
            for (Receptas rec : searchReceptasList) {
                if (rec.getPav().toLowerCase(Locale.getDefault()).contains(charText)) {
                    receptasL.add(rec);
                }
            }
        }
        notifyDataSetChanged();
    }*/


    // - - - - - - - - - -   C L A S S  - - - - - - - - - - -
    public class ViewHolder extends RecyclerView.ViewHolder {
        public MainActivity mainActivity = new MainActivity();
        TextView itemTextViewPav, itemTextViewIngred_sk;
        ArrayList<Receptas> receptasL;
        Context context;
        RecyclerView recyclerView;

        public ViewHolder(final View itemView, final ArrayList<Receptas> receptasL, final Context context) {
            super(itemView);
            this.receptasL = receptasL;
            itemTextViewPav = (TextView) itemView.findViewById(R.id.itemTextViewPav);
            itemTextViewIngred_sk = (TextView) itemView.findViewById(R.id.itemTextViewIngred_sk);
            this.context = context;
            // Attach a click listener to the entire row view
//            itemView.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    int position = getLayoutPosition();
                    // Send single item click data to SingleItemView Class
//                    Toast.makeText(itemView.getContext(), "onClick " + receptasL.get(position).getPav(), Toast.LENGTH_SHORT).show();

                    //perkeliami duomenys i InfoActivity
                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra("pavadinimas", receptasL.get(position).getPav());
                    intent.putExtra("aprasymas", receptasL.get(position).getApras());
                    intent.putExtra("ingredSK", receptasL.get(position).getIngredSk());
                    ArrayList<Ingredientas> ingredientasArrayList = receptasL.get(position).getIngredientas();
                    intent.putParcelableArrayListExtra("arrayList", ingredientasArrayList);


                    // Start SingleItemView Class
                    context.startActivity(intent);
                }
            });
        }

    }

}