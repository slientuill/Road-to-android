package com.example.slien.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button,button2;
    private EditText editText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);//注册名称
        editText=(EditText) findViewById(R.id.edit_text);//注册名称
        button2 =(Button) findViewById(R.id.button2);
        imageView =(ImageView)findViewById(R.id.image_view);
        button2.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                String inputText=editText.getText().toString();
                Toast.makeText(MainActivity.this,inputText,Toast.LENGTH_SHORT).show();
                //toast 短提示输出字符
                break;
            case R.id.button2:
                imageView.setImageResource(R.drawable.huaji2);
                break;
            default:
                break;
        }
    }

   /* public void onClick(View v2) {
       switch(v.getId()){
            case R.id.button2:
                imageView.setImageResource(R.drawable.huaji);
                break;
            default:
                break;
        }
    }*/
}
