package com.example.slien.excited;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private String[]data={"苟","利","国","家","生","死","以","岂","因","祸",
    "福","避","趋","之"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Handler x=new Handler();
        //x.postDelayed(new splashhandler(),3000);
        ArrayAdapter<String>adapter =new ArrayAdapter<String>(
                SplashActivity.this,android.R.layout.simple_list_item_1,data
        );//构造适配器对象
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);//传递构造好的适配器对象
    }
   /* class splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(),MainActivity.class));
            SplashActivity.this.finish();
        }
    }*/
    public class Fruit{
       private String name;
       private  int imageId;
       public Fruit(String name,int imageId){
           this.name=name;
           this.imageId=imageId;
       }
       public String getName(){
           return name;
       }
       public  int getImageId(){
           return imageId;
       }

    }
    public  class FruitAdapter extends ArrayAdapter<Fruit> {
        private int resourceId;

        public FruitAdapter(Context context, int textViewResourceId,
                            List<Fruit> objects) {
            super(context,textViewResourceId,objects);
            resourceId=textViewResourceId;
        }
        public View getView(int position, View convertView, ViewGroup parent){
        Fruit fruit = getItem(position); // 获取当前项的Fruit实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName=(TextView)view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());
        return view;
        }


    }



}
