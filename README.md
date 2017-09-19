

DataBinding使用笔记



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
	
	
	
	
3.view绑定Model数据
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
		
		
			
		
d. 绑定点击事件
     1.注册点击事件回调
     	定义一个接口
			public interface ClickListener{
			public void click1(View v);
		    }
     	   布局data中加入引用
     		     <variable
            		name="clickEvent"
            		type="com.example.sunhailong01.databinding.User.ClickListener"/>
	    button中加入点击事件
			 <Button
			    android:id="@+id/button2"
			    android:layout_width="match_parent"
			    android:layout_height="wrap_content"
			    android:text="@{callbackClick}"
			    android:clickable="@{enabled}"
			    android:onClick="@{clickEvent.click1}"/>
			
		 xml点击事件的引用写法有以下几种写法
			  1.android:onClick="@{event.click1}"
			  2.android:onClick="@{event::click2}"
			  3.android:onClick="@{()->event.cilck3(title4)}"
			    [注]：()->event.cilck3(title4)是lambda表达式写法，
			   
	  最后在代码中注册点击事件
			    binding.setClickEvent(new User.ClickListener() {
			    @Override
			    public void click1(View v) {
				Toast.makeText(getBaseContext(), "button3 callback方式点击", Toast.LENGTH_LONG).show();
			    }
			});

			    
		
     2.直接接收点击事件
     	定义一个方法作为点击事件的接收 
		    public void ClickEvent(View view) {
			switch (view.getId()) {
			    case R.id.button3:
				Toast.makeText(context, "button3 User点击", Toast.LENGTH_LONG).show();
				break;
			    default:
				break;
			}

		    }
		    
	  然后只需要在button中增加该方法的引用即可
		    <Button
			    android:id="@+id/button3"
			    android:layout_width="match_parent"
			    android:layout_height="wrap_content"
			    android:text="@{userclick}"
			    android:clickable="@{enabled}"
			    android:onClick="@{user::ClickEvent}"/>
			    
	  此时，ActivityMainBinding中自动增加一个oncick方法，即可响应点击事件
	  
	  
	  
	  
	  



进阶使用

  1.Databinding实现xml中调用静态方法
				 <data>
					<variable
					    name="callbackClick"
					    type="String" />
					<variable
					    name="userclick"
					    type="String" />

					<variable
					    name="enabled"
					    type="boolean" />
					<variable
					    name="buttoncolor"
					    type="int"/>

					<variable
					    name="user"
					    type="com.example.sunhailong01.databinding.User" />
					<variable
					    name="clickEvent"
					    type="com.example.sunhailong01.databinding.User.ClickListener"/>
					
					// 通过在data中import 静态Util类，实现类似导包功能，此时在xml中即可使用该静态类中的防范
					<import type="com.example.sunhailong01.databinding.ClassUtil"/>

		    		</data>
  		
		
  				<Button
				    android:id="@+id/button4"
				    android:layout_width="match_parent"
				    android:layout_height="wrap_content"
				    
				   // 这里可以直接使用静态类中的方法
				    android:text="@{ClassUtil.getClassName(user)}"
				    android:clickable="@{enabled}"
				    android:onClick="@{user::ClickEvent}"/>
				    
				    
				    
2.使用xml中使用运算符操作数据
	 实现在xml中进行一些数据逻辑判断处理，而展示不同的内容
	 
	 <Button
            android:id="@+id/button1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
	    // 实现了当user text 为空时 使用 @string/app_name 不空时使用text 等价与   
	    // android:text="@{user2.content!=null? user2.content : @string/app_name}
            android:text="@{user.text ?? @string/app_name}"
            android:clickable="@{enabled}"
            android:background="@{buttoncolor}"/>
	    
	    支持的运算符有：
	    		数学表达式： + - / * %

			字符串拼接 +

			逻辑表达式 && ||

			位操作符 & | ^

			一元操作符 + - ! ~

			位移操作符 >> >>> <<

			比较操作符 == > < >= <=

			instanceof

			分组操作符 ()

			字面量 - character, String, numeric, null

			强转、方法调用

			字段访问

			数组访问 []

			三元操作符 ?:

			聚合判断（Null Coalescing Operator）语法 ‘??’
			
			
			
			
databinding 动态数据加载
    实现:根据model变量改变自动更新数据
    布局中增加一个textview，想要实现根据user的name和age的变化动态更新数据
		    <TextView
			    android:layout_width="match_parent"
			    android:layout_height="wrap_content"
			    android:gravity="center"
			    android:textSize="24dp"
			    android:hint="动态数据使用"
			    android:text="@{user.name + user.age}"/>
	    
	    增加一个button
		       <Button
			    android:id="@+id/button4"
			    android:layout_width="match_parent"
			    android:layout_height="wrap_content"
			    android:text="@{ClassUtil.getClassName(user)}"
			    android:clickable="@{enabled}"
			    android:onClick="@{user::ClickEvent}"/>
	    
	    点击事件，动态改变user的name和age
		      public void ClickEvent(View view) {
				switch (view.getId()) {
				    case R.id.button3:
					Toast.makeText(context, "button3 User点击", Toast.LENGTH_LONG).show();
					break;
				    case R.id.button4:
					setNameg("孙海龙");
					setAge(26);
					break;
				    default:
					break;
				}

			    }
		    
		    
		    
	User 类代码
	User类继承BaseObservable,BaseObservable实现android.databinding.Observable接口，Observable接口可以允许附加一个监听器到model对象以便监听对象上的所有属性的变化。
	


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
    @Bindable
    public int getAge() {
        return age;
    }

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



	 给需要动态变化的数据的get或set方法增加  @Bindable 注解， Observable接口有一个机制来添加和删除监听器，但通知与否由开发人员管理。为了使开发更容易，BaseObservable实现了监听器注册机制。DataBinding实现类依然负责通知当属性改变时。这是通过指定一个Bindable注解给getter以及setter内通知来完成的。
 	此时，在com.example.sunhailong01.databinding.BR 文件中会增加age字段的引用
 
 	然后，在数据变化时候调用notifyPropertyChanged，传入age的id即可实现动态变化
  		notifyPropertyChanged(com.example.sunhailong01.databinding.BR.age);
		notifyPropertyChanged(BR.参数名)通知更新这一个参数，
需要与@Bindable注解配合使用。notifyChange()通知更新所有参数，可以不用和@Bindable注解配合使用





Observable数据实现改变自动更新
    通过Observable数据不再需要 bindable标签即可实现数据动态更新的方式
    它的子类有BaseObservable,ObservableField,ObservableBoolean, ObservableByte, ObservableChar, ObservableShort, ObservableInt, 	           ObservableLong, ObservableFloat, ObservableDouble, and ObservableParcelable，ObservableArrayList,ObservableArrayMap
    
    首先，data中定义两个obserble数据类型
	    <variable
		    name="list"
		    type="android.databinding.ObservableArrayList&lt;String&gt;"/>

		<variable
		    name="map"
		    type="android.databinding.ObservableArrayMap&lt;String,String&gt;"/>
	    这里要注意泛型的使用，泛型的支持会在编译时期报红线，但是是可以直接运行的，但是需要通过转义字符才行，使用&lt;数据类型&gt，如果有两个变量，     中间用“，”隔开
	   
	   
	 java代码中 需要
	      // 使用  ObservableArrayList
	final ObservableArrayList<String> list = new ObservableArrayList<>();
	list.add("cat");
	binding.setList(list);
	// 使用  ObservableMap
	final ObservableArrayMap<String, String> map = new ObservableArrayMap<>();
	map.put("name","dog");
	binding.setMap(map);

	  如果list动态可变，那么会通知到该list引用的位置，进行数据更新

	  也可使用ObservableField或ObservableInt 
		public final ObservableField<String> myObName  = new ObservableField<>() ;
		public final ObservableInt myObAge = new ObservableInt();
	  系统会自动生成两个变量的set和get方法,控制变量的更改
	
	  
	  
	  
	    
	    
	    



		
		
		
		

		     
		    
		    
		    

    
    

   


			
			
			
			
	 
	
				   
				    
				    
				    
  
	  
	  
			    
     
     	
			
		
		
		
	    
	    
	    
	    
	    



		
	
	
	

                
                
                
                
          
          

 
