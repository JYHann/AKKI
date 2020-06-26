package com.example.sanhak3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentPattern extends Fragment {

    private Button showBtn;
    private Button inputBtn;
    private TextView showTotal;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    // db에 저장되어 있는 값들을 담는 ArrayList
    private List<InoutData> inoutDatas = new ArrayList<>();
    private List<ManuFactureData> manuFactureDatas = new ArrayList<>();
    private List<String> categoryData = new ArrayList<>();

    // firebase 불러오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference database = firebaseDatabase.getReference();

    //piechart 불러오기
    private PieChart pieChart;

    //category가 있을 경우 해당 카테고리에 값을 더하고
    //없을 경우에는 카테고리를 추가해준다.
    boolean isCategory = false;

    // PieChart에 표시하기 위한 변수들..
    private float food = 0f;
    private float traffic = 0f;
    private float academy = 0f;
    private float market = 0f;
    private float cafe = 0f;
    private float etc = 0f;

    //input
    private TextView tv;
    private EditText et;
    private Spinner s;
    private Button saveBtn;
    private Button backBtn;
    private String idx;

    private InoutData inoutData = new InoutData();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pattern, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        showBtn = view.findViewById(R.id.show_piechart);
        inputBtn = view.findViewById(R.id.input_data);
        showTotal = view.findViewById(R.id.total);

        s = view.findViewById(R.id.category);
        tv = view.findViewById(R.id.using_money);
        et = view.findViewById(R.id.input_price);
        saveBtn = view.findViewById(R.id.saveBtn);

        database.child("inout").child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // 데이터 읽기
                inoutDatas.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 데이터 리스트에 담기
                    InoutData inoutData = snapshot.getValue(InoutData.class);
                    inoutDatas.add(inoutData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //데이터 읽기 실패!
                Toast errorMsg = Toast.makeText(view.getContext(), "데이터 읽어오기 실패!", Toast.LENGTH_SHORT);
                errorMsg.show();
            }
        });

        // spinner를 통해 값 선택 후 저장
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idx = database.child("inout").child(mFirebaseUser.getUid()).push().getKey(); // db에 저장될 각 데이터들의 idx
                tv.setText(parent.getItemAtPosition(position).toString() + "에 들어간 돈 ");
                inoutData.setCategory(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inoutData.setPrice(et.getText().toString());
                if(inoutDatas.size() == 0){
                    inoutData.setTotalPrice(Float.parseFloat(et.getText().toString()));
                } else {
                    inoutData.setTotalPrice(inoutDatas.get(inoutDatas.size() - 1).getTotalPrice() + Float.parseFloat(et.getText().toString()));
                }

                inoutData.setIdx(idx);

                if(inoutData.getPrice().length() != 0 && isdisit(inoutData.getPrice()) && inoutData.getCategory().length() != 0){
                    database.child("inout").child(mFirebaseUser.getUid()).child(idx).setValue(inoutData); // 파이어베이스에 데이터 저장
                    et.setText("");
                    Toast successMsg = Toast.makeText(view.getContext(), "저장 성공!!", Toast.LENGTH_SHORT);
                    successMsg.show();
                }
                else{
                    Toast errorMsg = Toast.makeText(view.getContext(),"잘못된 값이 입력 되었습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT);
                    errorMsg.show();
                    Log.d("Tag", "input nothing or input wrong data");
                }
            }
        });

        setManuFactureData();
        drawPieChart(view);

        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(view.getContext(), SaveDataActivity.class));
                view.findViewById(R.id.piechart).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.input_inout_data).setVisibility(View.VISIBLE);
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inoutDatas.size() != 0) {
                    showTotal.setText("총 사용한 돈 : " + inoutDatas.get(inoutDatas.size() - 1).getTotalPrice());
                } else{
                    showTotal.setText("총 사용한 돈 : 0");
                }
                view.findViewById(R.id.input_inout_data).setVisibility(View.GONE);
                view.findViewById(R.id.piechart).setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

    public Boolean isdisit(String str){
        // price값이 입력 될때 아무것도 입력 받지 않은 겨우 혹은 숫자 이외에 다른 값이 입력 됬을 경우 db저장 제한

        Boolean strisdisit = true;
        char check;

        for(int i = 0; i < str.length(); i++){
            check = str.charAt(i);
            if((check >= 'a' && check <= 'z') || (check >='A' && check <= 'Z')) {
                strisdisit = false;
                break;
            }
        }
        if (str.equals("") || str.equals(" ")){
            strisdisit = false;
        }
        return strisdisit;
    }

    public void setManuFactureData(){
        for(int i = 0; i < inoutDatas.size(); i++){
            if(inoutDatas.get(i).getCategory().equals("음식")) {
                food += Float.parseFloat(inoutDatas.get(i).getPrice());
            }
            else if (inoutDatas.get(i).getCategory().equals("교통"))
                traffic += Float.parseFloat(inoutDatas.get(i).getPrice());
            else if (inoutDatas.get(i).getCategory().equals("학원"))
                academy += Float.parseFloat(inoutDatas.get(i).getPrice());
            else if (inoutDatas.get(i).getCategory().equals("편의점"))
                market += Float.parseFloat(inoutDatas.get(i).getPrice());
            else if (inoutDatas.get(i).getCategory().equals("카페"))
                cafe += Float.parseFloat(inoutDatas.get(i).getPrice());
            else if (inoutDatas.get(i).getCategory().equals("기타"))
                etc += Float.parseFloat(inoutDatas.get(i).getPrice());
            else{

            }
        }
    }

    public void drawPieChart(View v){
        pieChart = (PieChart)v.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,1,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);


        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        Log.d("Tag >>> ", "Food : " + food);

        if (food > 0)
            yValues.add(new PieEntry(food,"음식"));
        if (traffic > 0)
            yValues.add(new PieEntry(traffic,"교통"));
        if (academy > 0)
            yValues.add(new PieEntry(academy,"학원"));
        if (market > 0)
            yValues.add(new PieEntry(market,"편의점"));
        if (cafe > 0)
            yValues.add(new PieEntry(cafe,"카페"));
        if (etc > 0)
            yValues.add(new PieEntry(etc,"기타"));

        Description description = new Description();
        description.setText("소비패턴 분석 결과 (단위 : %)"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues,"소비패턴");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(6f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
    }
}
