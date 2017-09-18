package com.example.sunhailong01.databinding;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by sunhailong01 on 17/9/18.
 */

public class User {

    private String text;
    private Context context;
    public User(String text, Context context) {
        this.text = text;
        this.context = context;
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
            default:
                break;
        }

    }




}
