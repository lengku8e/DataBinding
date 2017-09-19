package com.example.sunhailong01.databinding;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by sunhailong01 on 17/9/18.
 */

public class User  extends BaseObservable{

    private String text;
    private Context context;
    @Bindable
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.sunhailong01.databinding.BR.name);
    }
    public int getAge() {
        return age;
    }
    @Bindable
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(com.example.sunhailong01.databinding.BR.age);
    }

    private String name;
    private int age;
    public User(String text, Context context, String name, int age) {
        this.text = text;
        this.context = context;
        this.name = name;
        this.age = age;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public interface ClickListener{
        public void click1(View v);
    }


    public void ClickEvent(View view) {
        switch (view.getId()) {
            case R.id.button3:
                Toast.makeText(context, "button3 User点击", Toast.LENGTH_LONG).show();
                break;
            case R.id.button4:
                setName("孙海龙");
                setAge(26);
                break;
            default:
                break;
        }

    }




}
