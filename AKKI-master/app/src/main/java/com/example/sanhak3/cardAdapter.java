package com.example.sanhak3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cardAdapter extends BaseAdapter {


    private ArrayList<cards> cardsData = new ArrayList<cards>();
    private int datacount=0;
    LayoutInflater inflater = null;


    public cardAdapter(ArrayList<cards> CardsData)
    {

        cardsData = CardsData;
        datacount = cardsData.size();


    }

    @Override
    public int getCount()
    {
        return datacount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.card_info, parent, false);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Zzzzzzzzzzzzzzzzz","loglog");
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("카드 혜택");
                    String message = "";
                    for(int i=0;i<cardsData.get(position).options.size();i++)
                    {
                        message+=cardsData.get(position).options.get(i).key+":"+cardsData.get(position).options.get(i).value+"\n";
                    }
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
        ImageView image = convertView.findViewById(R.id.imageView);
        Picasso.get().load(cardsData.get(position).url).into(image);
        TextView cardtext = convertView.findViewById(R.id.Cname);
        cardtext.setText(cardsData.get(position).Cname);
        TextView sumtext = convertView.findViewById(R.id.sum);
        sumtext.setText(cardsData.get(position).sum+"원");
        return convertView;
    }
}

