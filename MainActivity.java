package com.example.slien.nethomework;

import android.app.Activity;
import android.content.Context;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
public class MainActivity extends Activity implements View.OnClickListener {
    private TextView macText_view,gpsText_view,network_text,answer_text,wifistate_text,gpsstate_text,board;
    private Button button1,button2,button3,button4,wifi_State_button,gps_state_button;
    private LocationManager locationManager;
    private String provider;
    private double la,lo;
    public static  final  int SHOW_RESPONCE =0;
    public static  final  int SHOW_WHAT =1;
    private Handler handler=new Handler(){//不能在子线程重更新UI messagehandler用于接收子线程的数据
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONCE:{
                    String response=(String) msg.obj;
                    if(response!=null)
                        network_text.setText("link is ready");
                    else
                        network_text.setText("link failed");
                }
                case SHOW_WHAT:{
                    String what=(String)msg.obj;
                    if(what!=null)
                        board.setText(what);
                    else
                        answer_text.setText("没有返回值");
                }


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        macText_view=(TextView)findViewById(R.id.mac_text);
        gpsText_view=(TextView)findViewById(R.id.gps_text);
        network_text=(TextView)findViewById(R.id.networktext);
        answer_text=(TextView)findViewById(R.id.ansewer_Text);
        wifistate_text=(TextView)findViewById(R.id.wifiState_text);
        gpsstate_text=(TextView)findViewById(R.id.gpsState_text);
        board=(TextView)findViewById(R.id.showboard);
        button1=(Button)findViewById(R.id.mac_button);
        button1.setOnClickListener(this);
        button3=(Button)findViewById(R.id.upLoadAnswer);
        button3.setOnClickListener(this);
        button2=(Button)findViewById(R.id.gps_button);
        button2.setOnClickListener(this);
        button4=(Button)findViewById(R.id.checkInternet_button);
        button4.setOnClickListener(this);
        wifi_State_button=(Button)findViewById(R.id.wifiState_button);
        wifi_State_button.setOnClickListener(this);
        gps_state_button=(Button)findViewById(R.id.gps_State_button);
        gps_state_button.setOnClickListener(this);
        //GPS 定位---------------------------------------------------------
        locationManager = (LocationManager) getSystemService(Context.
                LOCATION_SERVICE); // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else { // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try{
             Location location = locationManager.getLastKnownLocation(provider);
             if (location != null) { // 显示当前设备的位置信息
                showLocation(location);
                 la=location.getLatitude();
                 lo=location.getLongitude();
             }
             locationManager.requestLocationUpdates(provider, 5000, 1, locationListener); }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }
    protected void onDestroy() {
            super.onDestroy();
            if (locationManager != null) { // 关闭程序时将监听器移除
                try{
                    locationManager.removeUpdates(locationListener);
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }

            }
    }
    LocationListener locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) { }
            @Override
            public void onProviderDisabled(String provider) { }
            @Override
            public void onLocationChanged(Location location) {
                // 更新当前设备的位置信息
             showLocation(location);
            }
        };
        private void showLocation(Location location) {
            String currentPosition =   location.getLatitude() + "," + location.getLongitude();
            gpsText_view.setText(currentPosition);
        }
        private double setla(Location location){
            return location.getLatitude();
        }
        private double setlo(Location location){
            return location.getLongitude();
        }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.mac_button:
                macText_view.setText(getConnectedWifiMacAddress());
            case R.id.upLoadAnswer:
                UploadAnswer(getConnectedWifiMacAddress(),lo,la,"slient");
            case R.id.checkInternet_button:
                checkInternet();
            case R.id.wifiState_button:
                int state=getWIFISTATE();
                if(state==1)
                    wifistate_text.setText("wifi未打开");
                else
                    wifistate_text.setText("wifi就绪"+"SSID:"+getWIFISSID());
            case R.id.gps_State_button:
                int gstate=getGPSState();
                if(gstate==1)
                    gpsstate_text.setText("GPS定位就绪");
                if(gstate==2)
                    gpsstate_text.setText("网络定位就绪");
                if(gstate==0)
                    gpsstate_text.setText("获取定位服务失败" );
        }
    }
    private int getWIFISTATE(){
        WifiManager manager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=manager.getConnectionInfo();
        int state=manager.getWifiState();
        if(state!=WifiManager.WIFI_STATE_ENABLED){
            return 1;
        }
        else{
            return 0;
        }

    }
    private String getWIFISSID(){
        WifiManager manager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=manager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
    private int getGPSState(){
        locationManager = (LocationManager) getSystemService(Context.
                LOCATION_SERVICE); // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            return 1;
        }
        else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
           return 2;
        }
        else {
           return 0;
        }
    }
    private String getConnectedWifiMacAddress() {
        String connectedWifiMacAddress = null;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiList;
        if (wifiManager != null) {
            wifiList = wifiManager.getScanResults();
            WifiInfo info = wifiManager.getConnectionInfo();
            if (wifiList != null && info != null) {
                for (int i = 0; i < wifiList.size(); i++) {
                    ScanResult result = wifiList.get(i);
                    if (info.getBSSID().equals(result.BSSID)) {
                        connectedWifiMacAddress = result.BSSID;
                    }
                }
            }
        }
        return connectedWifiMacAddress;
    }
    private String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macSerial;
    }
    private void checkInternet()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL("http://www.baidu.com");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response =new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    Message message =new Message();
                    message.what=SHOW_RESPONCE;
                    message.obj=response.toString();
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void UploadAnswer(final String MACAddress,final double longitude,final double latitude,final String name)  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL("http://211.87.238.61/submit?wifi-mac="+MACAddress+
                            "gps="+longitude+";"+latitude+"&iam="+name);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response =new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    Message message2 =new Message();
                    message2.what=SHOW_WHAT;
                    message2.obj=response.toString();
                    handler.sendMessage(message2);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }





}
