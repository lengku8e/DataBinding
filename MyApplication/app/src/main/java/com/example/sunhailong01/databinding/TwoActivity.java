package com.example.sunhailong01.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.sunhailong01.databinding.databinding.ActivityTwoBinding;

/**
 * Created by sunhailong01 on 17/9/20.
 */

public class TwoActivity extends Activity {
    private ActivityTwoBinding binding;
    private User model;
    public ObservableArrayList<User> sugDataList = new ObservableArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        model = new User("yibuyibu", this, "sunhailong", 20);

        sugDataList.add(model);

        binding = DataBindingUtil.setContentView(TwoActivity.this, R.layout.activity_two);
        DataAdapter<User> dataAdapter = new DataAdapter(R.layout.poi_search_adapter_item, sugDataList, BR.model, getBaseContext());
        binding.hisListView.setAdapter(dataAdapter);
        binding.resetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User model = new User("asd", getBaseContext(), "hailongsun", 21);
                sugDataList.add(model);
            }
        });
    }
}
