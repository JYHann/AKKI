package com.example.sanhak3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCommunity extends RecyclerView.Adapter<AdapterCommunity.MyViewHolder> {
    private List<String> comu_menu_list;
    private Context mContext;
    private Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewMenu;
        public MyViewHolder(@NonNull View v) {
            super(v);
            textViewMenu = v.findViewById(R.id.comu_menu);
        }
    }

    public AdapterCommunity(List<String> menuSet, Context context) {
        comu_menu_list = menuSet;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comu_menu, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final int positionS = position + 1;
        final String comu_menu = comu_menu_list.get(position);
//        final Intent[] intent = {new Intent(), new Intent(), new Intent(), new Intent(), new Intent()};
        holder.textViewMenu.setText(comu_menu);

        holder.textViewMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = mContext.getApplicationContext();
                intent = new Intent(context, ActivityCommunity.class);
                intent.putExtra("menu", comu_menu);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                Toast.makeText(context, positionS + "번째 게시판", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comu_menu_list == null ? 0 : comu_menu_list.size();
    }
}
