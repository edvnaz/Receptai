package com.baigiamasis.ed.app.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baigiamasis.ed.app.R;

//nebuvo static
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    TextView mItemTextView, mItemTextView2;

//    public RecyclerItemViewHolder(final View parent, TextView itemTextView, TextView itemTextView2) {
//        super(parent);
//        this.mItemTextView = itemTextView;
//        this.mItemTextView2 = itemTextView2;
//    }

    //    public static RecyclerItemViewHolder newInstance(View parent) {
//        TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextViewPav);
//        TextView itemTextView2 = (TextView) parent.findViewById(R.id.itemTextViewIngred_sk);
//        return new RecyclerItemViewHolder(parent, itemTextView, itemTextView2);
//    }
    RecyclerItemViewHolder(View parent) {
        super(parent);
        this.mItemTextView = (TextView) parent.findViewById(R.id.itemTextViewPav);
        this.mItemTextView2 = (TextView) parent.findViewById(R.id.itemTextViewIngred_sk);
    }

//    public void setItemText(CharSequence text) {
//        mItemTextView.setText(text);
//    }

    //nebuvo uzkomentuotas
//    public void setItemText(CharSequence text, CharSequence text2) {
//        mItemTextView.setText(text);
//        mItemTextView2.setText(text2);
//    }

}

