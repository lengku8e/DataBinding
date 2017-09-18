
1.DataBinding介绍
    2015年谷歌I/O大会上介绍了一个框架DataBinding，DataBinding是一个数据绑定框架DataBinding主要解决了两个问题： 
- 需要多次使用findViewById，损害了应用性能且令人厌烦 
- 更新UI数据需切换至UI线程，将数据分解映射到各个view比较麻烦
2.具体使用
  导入项目
  在app中的build文件中加入databiding支持
                //导入dataBinding支持
                dataBinding{
                    enabled true
                }
                
3.先简单介绍四个使用场景
          不再findviewbyid
          view与基本数据类型及String绑定
          view绑定PoJo数据
          绑定事件
          
    a.不再findviewbyid
      新建一个项目 用layout创建activity——main布局
               <!--布局以layout作为根布局-->
                <layout>
                    <!--我们需要展示的布局-->
                    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:orientation="vertical"
                        tools:context="MainActivity">

                        <Button
                            android:id="@+id/button1"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:text="不再findviewbyid" />
                    </LinearLayout>
                </layout>
                
                
    activity中调用通过DataBindingUtils.setContentView()将上述布局加载到代码中，同时生成对应一个Binding对象，对象名是布局文件文称加上Binding后缀
    
    public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;//这个类是自动生成的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
       
       
       // 这里直接可以调用生成的bind调用button1即可
       binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.button1.setText("sunhailong");
            }
        });
    }
}

b.view与基本数据类型及String绑定
				<!--布局以layout作为根布局-->
				<layout>
				    <!--我们需要展示的布局-->
				    <data>

					<variable
					    name="buttonname"
					    type="String" />

					<variable
					    name="enabled"
					    type="boolean" />
					<variable
					    name="buttoncolor"
					    type="int"/>
				    </data>
				    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
					xmlns:tools="http://schemas.android.com/tools"
					android:layout_width="match_parent"
					android:layout_height="match_parent"
					android:orientation="vertical"
					tools:context=".MainActivity">
					<Button
					    android:id="@+id/button1"
					    android:layout_width="match_parent"
					    android:layout_height="wrap_content"
					    android:text="@{buttonname}"
					    android:clickable="@{enabled}"
					    android:background="@{buttoncolor}"/>
				    </LinearLayout>

				</layout>
				
        name:对象命名，类似于id
        type:  和java代码中的类型是一致的
	在布局中是通过@{}来绑定数据的，{}中是布局中该控件属性对应的数据类型数据
	
activity中为view设置状态调用刚才命名的name的set方法即可为view设置状态
	binding.setButtonname("第一个button"); // button的text
        binding.setEnabled(true); // button可点击
        binding.setButtoncolor(R.color.colorPrimaryDark); // button颜色
	
	
	
	
3.view绑定PoJo数据
简单创建一个User的数据模型

			public class User {
			    private String text;

			    public User(String text) {
				this.text = text;
			    }

			    public String getText() {
				return text;
			    }

			    public void setText(String text) {
				this.text = text;
			    }
			}
布局中增加数据模型引用
        <variable
            name="user"
            type="com.example.sunhailong01.databinding.User" />
此时在button中即可以拿到该bean的字段引用，当该bean的text改变时，即可更新button状态
	  <Button
            android:id="@+id/button1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{user.text}"
            android:clickable="@{enabled}"
            android:background="@{buttoncolor}"/>
然后，在java代码中创建一个user对象，并设置text字段
		User u = new User("sunhailong");
		binding.setUser(u);  调用set方法，即可实现model与view的绑定
		
		
		
	    
	    
	    
	    
	    



		
	
	
	

                
                
                
                
          
          

 
