package com.example.sanhak3;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class top3Adapter extends RecyclerView.Adapter<top3Adapter.CustomViewHolder>{

    private ArrayList<cards> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected ImageView rank;
        protected TextView cost;


        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.imageView2);
            this.rank = (ImageView) view.findViewById(R.id.imageView3);
            this.cost = (TextView) view.findViewById(R.id.textView5);
        }
    }


    public top3Adapter(ArrayList<cards> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top3layout, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        Picasso.get().load(mList.get(position).url).into(viewholder.image);
        switch(position) {
            case 0:
                viewholder.rank.setImageResource(R.drawable.rank1);
                break;
            case 1:
                viewholder.rank.setImageResource(R.drawable.rank2);
                break;
            case 2:
                viewholder.rank.setImageResource(R.drawable.rank3);
                break;
        }
        viewholder.cost.setText("월 "+mList.get(position).sum+"원을 아낄 수 있습니다.");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
