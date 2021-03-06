package com.example.sunhailong01.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by sunhailong01 on 17/9/20.
 */

public class DataAdapter<T> extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    int layoutId;
    int variableId;
    private ObservableArrayList<T> list;

    public DataAdapter(int layoutId, ObservableArrayList<T> list, int resId, Context context) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.variableId = resId;
        inflater = LayoutInflater.from(context);
        initNotifyChangeListener();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewDataBinding dataBinding;
        if (convertView == null) {
             dataBinding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);

        } else {
            dataBinding = DataBindingUtil.getBinding(convertView);
        }
        T itemData = list.get(i);
        dataBinding.setVariable(variableId, itemData);
        return dataBinding.getRoot();
    }


    private void initNotifyChangeListener() {
        list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }
        });
    }
}
