package com.example.sanhak3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCommunity extends Fragment {
    private ServiceApiCommunity service;

    List<JsonObject> comu_menu_array = new ArrayList<>();
    List<String> comu_menu_list = new ArrayList<>();
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_community, container, false);

        service = RetrofitClient.getClient().create(ServiceApiCommunity.class);

        recyclerView = v.findViewById(R.id.comu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        attemptLoadMenu();

        recyclerView.setAdapter(new AdapterCommunity(comu_menu_list, getActivity()));

        return v;
    }

    private void attemptLoadMenu() {
        // 리사이클러뷰 어댑터 내용 부착해야 됨
        service.getComuMenu().enqueue(new Callback<CommunityResponse>() {
            @Override
            public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                CommunityResponse result = response.body();
                Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                comu_menu_array = result.getMenulist();
                Log.d("메뉴Array", comu_menu_array.toString());
                for(int i = 0; i < comu_menu_array.size(); i++) {
                    comu_menu_list.add(comu_menu_array.get(i).get("MenuName").getAsString());
                }
            }

            @Override
            public void onFailure(Call<CommunityResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "메뉴 로딩 중 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("메뉴 로딩 중 에러 발생", t.getMessage());
            }
        });
    }


}
