package com.example.sanhak3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentInout2_2 extends Fragment {
    /*연/월 텍스트뷰*/
    private TextView tvDate;
    /*그리드뷰 어댑터*/
    private GridAdapter gridAdapter;
    /* 일 저장 할 리스트*/
    private ArrayList<String> dayList;
    /*입금 저장 할 리스트*/
    private ArrayList<String> incomeList;
    /*출금 저장 할 리스트*/
    private ArrayList<String> expenseList;
    /*그리드뷰 */
    private GridView gridView;
    /*캘린더 변수*/
    private Calendar mCal;


    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inout2_2,container,false);

        tvDate = (TextView)view.findViewById(R.id.tv_date);
        gridView = (GridView)view.findViewById(R.id.gridview);

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        //현재 날짜 텍스트뷰에 뿌려줌
        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));

        //gridview 요일 표시
        dayList = new ArrayList<String>();
        incomeList = new ArrayList<String>();
        expenseList = new ArrayList<String>();

        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        mCal = Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");

        }
        for (int i = 1; i < dayNum+7; i++) {
            incomeList.add("");
            expenseList.add("");
        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        List<String> list = null;
        gridAdapter = new GridAdapter(getActivity().getApplicationContext(), dayList,incomeList,expenseList);
        gridView.setAdapter(gridAdapter);

        return view;
    }

    /*해당 월에 표시할 일 수 구함
     @param month
    */
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            incomeList.add("income");
        }
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            expenseList.add("expense");
        }
    }

    //그리드뷰 어댑터
    private class GridAdapter extends BaseAdapter {

        private final List<String> list;
        private final List<String> list2;
        private final List<String> list3;
        private final LayoutInflater inflater;

        /**
         * 생성자
         * @param context
         *
         * @param list
         *
         */
        public GridAdapter(Context context, List<String> list, List<String> list2, List<String> list3) {
            this.list = list;
            this.list2=list2;
            this.list3=list3;

            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public int getCount2() {
            return list2.size();
        }
        public int getCount3() {
            return list3.size();
        }


        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        public String getItem2(int position) {
            return list2.get(position);
        }
        public String getItem3(int position) {
            return list3.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);
                holder.tv2ItemGridView = (TextView)convertView.findViewById(R.id.tv2_item_gridview);
                holder.tv3ItemGridView = (TextView)convertView.findViewById(R.id.tv3_item_gridview);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));
            holder.tv2ItemGridView.setText("" + getItem2(position));
            holder.tv3ItemGridView.setText("" + getItem3(position));


            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.color_000000));
            }
            return convertView;
        }
    }

    private class ViewHolder {

        TextView tvItemGridView;
        TextView tv2ItemGridView;
        TextView tv3ItemGridView;
    }
}

