package com.zhengqi.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import com.highsoft.highcharts.Core.*;
import com.highsoft.highcharts.Common.HIChartsClasses.*;
public class MainActivity extends AppCompatActivity {
    private String user;
    private String pwd;
    private Boolean save;
    private TextView user_value;
    private TextView pwd_value;
    private Button login_btn;
    private StringBuffer s;
    private CheckBox save_box;
    private Switch pwd_switch;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayList dataArrayList=new ArrayList<>(Arrays.asList());
    private String filename1=Environment.getExternalStorageDirectory().getPath() + "/acc1.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try{
//            Log.v("In","InMOuth");
//            FileInputStream fileInputStream=new FileInputStream(filename1);
////            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
//            byte buffer[]=new byte[1024];
//            byte b[]=new byte[1];
//            String dataString="";
//            int pointNum=0;
//            int len;
//            while (pointNum<500){
//                Log.v("PointNum",String.valueOf(pointNum));
//                len=fileInputStream.read(buffer);
//                if (pointNum==0){
//                    dataString=dataString+new String(buffer,1,len-1);
//                }
//                else {
//                    dataString=dataString+new String(buffer);
//                }
//
//                Log.v("data",dataString);
//                fileInputStream.read(b);
//                while (!new String(b).equals("#")){
//                    dataString=dataString+new String(b);
//                    fileInputStream.read(b);
//                }
//                String[] pointList=dataString.split("#");
//
//                for(int i=0;i<pointList.length;i++){
//                    String[] point=pointList[i].split(",");
//                    ArrayList point_xy=new ArrayList<>(Arrays.asList());
////                    Log.v("x",point[0].toString());
//                    point_xy.add(Double.parseDouble(point[0].toString()));
//                    point_xy.add(Float.parseFloat(point[1].toString()));
//                    dataArrayList.add(point_xy);
////                    Log.v("00000",String.valueOf(point_xy));
////                    point_xy.clear();
//
//                }
//                pointNum+=count(dataString,"#");
//                dataString="";
//            }
//            fileInputStream.close();
//            Collections.reverse(dataArrayList);
//
////            Log.v("type",dataArrayList.get(0).toString());
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        user_value=findViewById(R.id.user_value);
        pwd_value=findViewById(R.id.pwd_value);
//        pwd_switch=findViewById(R.id.pwd_switch);
        save_box=findViewById(R.id.save_box);
        login_btn=findViewById(R.id.login_btn);

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        user_value.setText(pref.getString("user",""));
        pwd_value.setText(pref.getString("pwd",""));
//        pwd_switch.setChecked(pref.getBoolean("save",false));
        save_box.setChecked(pref.getBoolean("save",false));
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
//                saveUser();
//                Test();
//                Get();
                if(user.equals("user1") && pwd.equals("123123")){
                    saveUser();
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RouteActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        pwd_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    pwd_value.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//                else {
//                    pwd_value.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

    }
    private void getEditString(){
        user=user_value.getText().toString().trim();
        pwd=pwd_value.getText().toString().trim();
    }
    private void saveUser(){
//        save=save_box.isChecked();
        editor=pref.edit();
        if(save_box.isChecked()){
            editor.putString("user",user);
            editor.putString("pwd",pwd);
            editor.putBoolean("save",true);
//            Toast.makeText(MainActivity.this,"jizhu",Toast.LENGTH_LONG).show();
        }
        else {
            editor.clear();
        }
        editor.apply();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    private void Test(){
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
////            Driver driver= new com.mysql.jdbc.Driver();
////            String url="jdbc:mysql://cdb-g3jjtxsx.gz.tencentcdb.com:1012/my_subway";
////            Properties info= new Properties();
////            info.setProperty("user","root");
////            info.setProperty("password","root123123");
////            Connection connection=driver.connect(url,info);
////            System.out.println(connection);
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//
//
//
//    }

    private void Get(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer stringBuffer=new StringBuffer();
                try {
                    URL url = new URL("http://111.231.62.68/login/?next=/%3Fnsukey%3Dw4l117DLAkwdEZ8X40tUPm58zqJzTv8Jh%252F2CdbSNKjFoS2Bk7QtkWeKZUCS23%252BjWfBVyhTL1wHSNQjsMClkuIR1QdxdgZDUVcuj3pM3pJM0lgy1lzP5KB%252BQm7cxvDX0SA0UK7ma7jiOoTD8Zv%252BZCfbNdoPIHwqsbc8w8c4HmPvxQ3mH5LeYI4fp6LIljXds%252FJEWcMpkQgzdd7hUTPX0O6g%253D%253D&nsukey=%2FsX4tcAy%2B0F9bxuEcnWl5zSkflN9bY47vmE5yFEH91IIsnYUziAgSxcFmbqrE8zphBeF9vghyeaxfr2PcCNqIqSU2rPt4YdYb4tALLKZlrLh2sPTUp4VkX4ZIbi8xfU58N0QVGooyYsuy58pYPhMsnINoVOau51wbI%2FWIrmKz0rKxWomd0tGIIS%2BPsypVxXWRlNTRESwuvtygCphuEWG%2FA%3D%3D");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(60 * 1000);
                    urlConnection.setReadTimeout(60 * 1000);
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            stringBuffer.append(new String(bytes, 0, len));
                        }
                        s=stringBuffer;
                    }

//                    Toast.makeText(MainActivity.this,"你好",Toast.LENGTH_LONG);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int count(String data,String reg){
        int num=0;
        int near=0;
        for(int index=0;index<data.length();index++){
            if((near=data.indexOf(reg,index))!=-1){
                num+=1;
                index=near;
            }
        }
        return num;
    }
}
