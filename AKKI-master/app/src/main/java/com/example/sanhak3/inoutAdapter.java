package com.example.sanhak3;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
public class inoutAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_THIN = 0 ;
    private static final int ITEM_VIEW_TYPE_THICK = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;

    // 아이템 데이터 리스트.
    private ArrayList<ListviewInout> listViewInoutList = new ArrayList<ListviewInout>() ;

    public inoutAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewInoutList.size() ;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }

    // position 위치의 아이템 타입 리턴.
    @Override
    public int getItemViewType(int position) {
        return listViewInoutList.get(position).getType() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            ListviewInout listviewInout = listViewInoutList.get(position);

            switch (viewType) {
                case ITEM_VIEW_TYPE_THICK:
                    convertView = inflater.inflate(R.layout.listview_inout_item1,
                            parent, false);
                    TextView categoryTextView = (TextView) convertView.findViewById(R.id.tv_category) ;
                    TextView comnameTextView = (TextView) convertView.findViewById(R.id.tv_comname) ;
                    TextView moneyTextView = (TextView) convertView.findViewById(R.id.tv_money) ;
                    TextView bankTextView = (TextView) convertView.findViewById(R.id.tv_bank) ;

                    categoryTextView.setText(listviewInout.getCategory());
                    comnameTextView.setText(listviewInout.getComname());
                    moneyTextView.setText(listviewInout.getMoney());
                    bankTextView.setText(listviewInout.getBank());
                    break;

                case ITEM_VIEW_TYPE_THIN:
                    convertView = inflater.inflate(R.layout.listview_inout_item2,
                            parent, false);

                    TextView date2TextView = (TextView) convertView.findViewById(R.id.tv_date2) ;
                    TextView inputTextView = (TextView) convertView.findViewById(R.id.tv_input) ;
                    TextView outputTextView = (TextView) convertView.findViewById(R.id.tv_output) ;

                    date2TextView.setText(listviewInout.getDate2());
                    inputTextView.setText(listviewInout.getInput());
                    outputTextView.setText(listviewInout.getOutput());
                    break;
            }
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewInoutList.get(position) ;
    }

    // 첫 번째 아이템 추가를 위한 함수.
    public void addItem(String date2, String input, String output) {
        ListviewInout item = new ListviewInout() ;

        item.setType(ITEM_VIEW_TYPE_THIN) ;
        item.setDate2(date2); ;
        item.setInput(input) ;
        item.setOutput(output); ;

        listViewInoutList.add(item) ;
    }

    // 두 번째 아이템 추가를 위한 함수.
    public void addItem(String category, String comname, String money,String bank) {
        ListviewInout item = new ListviewInout() ;

        item.setType(ITEM_VIEW_TYPE_THICK) ;
        item.setCategory(category); ;
        item.setComname(comname); ;
        item.setMoney(money);
        item.setBank(bank);
        listViewInoutList.add(item);
    }
}
