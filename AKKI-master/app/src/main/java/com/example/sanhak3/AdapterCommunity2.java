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

public class AdapterCommunity2 extends RecyclerView.Adapter<AdapterCommunity2.MyViewHolder> {
    private List<ContentData> mDataset;
    private Context mContext;
    private String mMenu;
    private Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public TextView textViewContent;
        public MyViewHolder(@NonNull View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.comu_title);
            textViewContent = v.findViewById(R.id.comu_content);
        }
    }

    public AdapterCommunity2(List<ContentData> contentSet, Context context, String menu) {
        mDataset = contentSet;
        mContext = context;
        mMenu = menu;
    }

    @NonNull
    @Override
    public AdapterCommunity2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_comu_menu2, parent, false);
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_comu_menu2, parent, false);
        AdapterCommunity2.MyViewHolder vh = new AdapterCommunity2.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ContentData content = mDataset.get(position);

        holder.textViewTitle.setText(content.getTitle());
        holder.textViewContent.setText(content.getContent());

        holder.textViewTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = mContext.getApplicationContext();
                intent = new Intent(context, ActivityArticle.class);
                intent.putExtra("menu", mMenu);
                intent.putExtra("articleNumber", content.getNumber());
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.textViewContent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = mContext.getApplicationContext();
                intent = new Intent(context, ActivityArticle.class);
                intent.putExtra("menu", mMenu);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }


    @Override
    public int getItemCount() { return mDataset == null ? 0 : mDataset.size(); }
}
