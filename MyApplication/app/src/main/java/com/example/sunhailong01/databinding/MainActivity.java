package com.example.sunhailong01.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sunhailong01.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //通过DataBInding加载布局都会对应的生成Binding对象，如ActivityMainBinding，对象名在布局文件名称后加上了一个后缀Binding
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        //通过binfding.id名称---就相当于获取了改控件对象了
        binding.setEnabled(true);
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.button1.setText("可点击");
            }
        });

        binding.setCallbackClick("通过callback点击");
        binding.setClickEvent(new User.ClickListener() {
            @Override
            public void click1(View v) {
                Toast.makeText(getBaseContext(), "button3 callback方式点击", Toast.LENGTH_LONG).show();
            }
        });

        binding.setUserclick("user模型中的点击"); // button的text
        binding.setButtoncolor(R.color.colorPrimaryDark); // button颜色
        u = new User(null, getBaseContext(), "龙海孙", 21);
        binding.setUser(u);
    }
}
