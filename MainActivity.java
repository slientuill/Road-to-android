package com.example.slien.excited;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button button,button2,button3;
    private EditText edit_Text;
    private ProgressBar progressBar;
    private ImageView imageview;
    boolean angry=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        edit_Text=(EditText) findViewById(R.id.edit_text);
        imageview=(ImageView)findViewById(R.id.imageview);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                int progress=progressBar.getProgress();
                progress=progress+10;
                progressBar.setProgress(progress);
                /*if(progressBar.getVisibility()== View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                }*/
                if(progressBar.getProgress()==100)
                    progressBar.setVisibility(View.GONE);
                break;
            case R.id.button2:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("长者与你对话");

                if(angry==true){
                    dialog.setMessage("你们这些年轻人啊，不要老想着搞个大新闻，你们按这个键我肯定是资次的，但是你不要老在按");

                }
                if(angry==false){
                    dialog.setMessage("都讲了不要再按了，哀木安戈瑞");

                }
                dialog.setPositiveButton("-1s",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        angry=true;
                    }
                });
                dialog.setNegativeButton("资次长者",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        angry=false;
                    }
                });
                dialog.show();
                break;
            case R.id.button3:
               /* Intent intent =new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);*/
                int selectPic=(int)(Math.random()*4);
               // String path="pic"+selectPic;
                switch (selectPic){
                    case 1:
                        imageview.setImageResource(R.drawable.pic1);
                    break;
                    case 0:
                        imageview.setImageResource(R.drawable.pic0);
                        break;
                    case 2:
                        imageview.setImageResource(R.drawable.pic2);
                        break;
                    case 3:
                        imageview.setImageResource(R.drawable.pic3);
                        break;
                }
            default:
                break;
        }
    }


}