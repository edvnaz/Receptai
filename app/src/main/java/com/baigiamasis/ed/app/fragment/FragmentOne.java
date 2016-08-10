package com.baigiamasis.ed.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baigiamasis.ed.app.R;
import com.baigiamasis.ed.app.adapter.RecyclerAdapter;
import com.baigiamasis.ed.app.object_data.Ingredientas;
import com.baigiamasis.ed.app.object_data.Receptas;

import java.util.ArrayList;


public class FragmentOne extends Fragment {

    RecyclerAdapter myAdapter;
    RecyclerView recyclerView;
    Activity mActivity;
    OnHeadlineSelectedListener mCallback;


    private LayoutInflater layoutInflater;


    public static FragmentOne createFragment() {
        FragmentOne fragmentOne = new FragmentOne();
        Log.w("myApp", "createFragment ");
        return fragmentOne;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("myApp", "P onCreateView ");

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        myAdapter = new RecyclerAdapter(mActivity);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void finterByIngred(ArrayList<Ingredientas> ingredSearchAList) {
        myAdapter.finterByIngred(ingredSearchAList);
    }

    public void filtruotiRListEmpty() {
        myAdapter.filtruotiRListEmpty();
    }

    //gauna receptus is Json
    public void getArrayList(ArrayList<Receptas> receptasAList) {
        myAdapter.setReceptasList(receptasAList);
    }

    public void refresh(String newTxt, Boolean submit) {
        //veikiantys
//        final List<Receptas> receptasFilter = myAdapter.filter(receptasAList, newTxt);
//        myAdapter.setFilter(receptasFilter, submit, getActivity());
        //2 i 1
        myAdapter.setFilterv2(submit, getActivity(), newTxt);

    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

}
