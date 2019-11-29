package com.zhengqi.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Toast;

import com.highsoft.highcharts.Common.HIChartsClasses.HIChart;
import com.highsoft.highcharts.Common.HIChartsClasses.HICredits;
import com.highsoft.highcharts.Common.HIChartsClasses.HILine;
import com.highsoft.highcharts.Common.HIChartsClasses.HIOptions;
import com.highsoft.highcharts.Common.HIChartsClasses.HISeries;
import com.highsoft.highcharts.Common.HIChartsClasses.HITitle;
import com.highsoft.highcharts.Common.HIChartsClasses.HITooltip;
import com.highsoft.highcharts.Common.HIChartsClasses.HIXAxis;
import com.highsoft.highcharts.Common.HIChartsClasses.HIYAxis;
import com.highsoft.highcharts.Core.HIChartView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SecondDataActivity extends AppCompatActivity {
    private String filename1=Environment.getExternalStorageDirectory().getPath() + "/acc3.txt";
    private String filename2=Environment.getExternalStorageDirectory().getPath() + "/acc4.txt";
    private HIChartView chartView1;
    private HIOptions options1=new HIOptions();
    private HILine line1=new HILine();
    private ArrayList dataArrayList1=new ArrayList<>(Arrays.asList());
    private HIChartView chartView2;
    private HIOptions options2=new HIOptions();
    private HILine line2=new HILine();
    private ArrayList dataArrayList2=new ArrayList<>(Arrays.asList());
    private Button btn1;
    private static final int FINISH=1;
    private static final int DOWN=0;
    private static final int READ1=2;
    private static final int READ2=3;
    public Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case FINISH:
                    int index=(int)(Math.random()*200);
                    dataArrayList1.remove(0);
                    ArrayList p=(ArrayList)dataArrayList1.get(index);
                    float y=(float)p.get(1);
                    ArrayList last=(ArrayList)dataArrayList1.get(dataArrayList1.size()-1);
                    double x=(double)last.get(0)+1;
//                    x+=(double)1;
                    ArrayList temp=new ArrayList<>(Arrays.asList(x,y));
//                    ArrayList temp=(ArrayList)dataArrayList.get(index);
//                temp.add(dataArrayList(index))
                    dataArrayList1.add(temp);
                    line1.setData(dataArrayList1);
                    options1.setSeries(new ArrayList<HISeries>(Collections.singletonList(line1)));
                    chartView1.setOptions(options1);

                    break;
                case READ1:
//                    Toast.makeText(NewDataActivity.this,"hhhh",Toast.LENGTH_LONG).show();
                    line1.setData(dataArrayList1);
                    options1.setSeries(new ArrayList<HISeries>(Collections.singletonList(line1)));
                    chartView1.setOptions(options1);
                    break;
                case READ2:
//                    Toast.makeText(NewDataActivity.this,"hhhh",Toast.LENGTH_LONG).show();
                    line2.setData(dataArrayList2);
                    options2.setSeries(new ArrayList<HISeries>(Collections.singletonList(line2)));
                    chartView2.setOptions(options2);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);
        if(ContextCompat.checkSelfPermission(SecondDataActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SecondDataActivity.this,new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if(ContextCompat.checkSelfPermission(SecondDataActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SecondDataActivity.this,new
                    String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }



        chartView1=findViewById(R.id.hc1);
        chartView2=findViewById(R.id.hc2);
        getData();
//        readData();
        showPic1();
        showPic2();
//        update();

    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                if(ContextCompat.checkSelfPermission(NewDataActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(NewDataActivity.this,new
//                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }
//                if(ContextCompat.checkSelfPermission(NewDataActivity.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(NewDataActivity.this,new
//                            String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//                }
                try {
                    URL url = new URL("http://111.231.62.68/Required_data/2/SH_Acc_list/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5 * 1000);
                    urlConnection.setReadTimeout(5 * 1000);
                    FileOutputStream fileOutputStream=new FileOutputStream(filename1);
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        int pointNum=0;
                        for (int i=0;i<19;i++){
                            len=inputStream.read(bytes);
//                            pointNum+=count(new String(bytes, 0, len),"#");
//                            Log.v("len77777777777",new String(bytes, 0, len));
                            fileOutputStream.write(bytes,0,len);
//                            Log.v("len77777777777",String.valueOf(len));
//                            if(i==0){
////                                stringBuffer.append(new String(bytes, 16, len));
//                                fileOutputStream.write(bytes,17,len-17);
//                            }
//                            else {
////                                stringBuffer.append(new String(bytes, 0, len));
//                                fileOutputStream.write(bytes,0,len);
//                            }
                        }

//                        for (int i=0;i<30;i++){
//                            len=inputStream.read(bytes);
//                            Log.v("len77777777777",String.valueOf(len));
//                            if(i==0){
////                                stringBuffer.append(new String(bytes, 16, len));
//                                fileOutputStream.write(bytes,17,len-17);
//                            }
//                            else {
////                                stringBuffer.append(new String(bytes, 0, len));
//                                fileOutputStream.write(bytes,0,len);
//                            }


//                        }
//                        while ((len = inputStream.read(bytes)) != -1) {
//                            stringBuffer.append(new String(bytes, 0, len));
//                        }
//                        Log.v("Show","ShowPIC1");
//                        showPic1();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File file=new File(filename1);
                        Uri uri= Uri.fromFile(file);
                        intent.setData(uri);
                        Context context=SecondDataActivity.this.getApplicationContext();
                        context.sendBroadcast(intent);


                        Log.v("file",Environment.getExternalStorageDirectory().getPath() + "/acc1.txt");
                        readData1();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                if(ContextCompat.checkSelfPermission(NewDataActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(NewDataActivity.this,new
//                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }
//                if(ContextCompat.checkSelfPermission(NewDataActivity.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(NewDataActivity.this,new
//                            String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//                }
                try {
                    URL url = new URL("http://111.231.62.68/Required_data/2/SH_Spe_list/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5 * 1000);
                    urlConnection.setReadTimeout(5 * 1000);
                    FileOutputStream fileOutputStream=new FileOutputStream(filename2);
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        int pointNum=0;
                        for (int i=0;i<18;i++){
                            len=inputStream.read(bytes);
//                            pointNum+=count(new String(bytes, 0, len),"#");
//                            Log.v("len77777777777",new String(bytes, 0, len));
                            fileOutputStream.write(bytes,0,len);
//                            Log.v("len77777777777",String.valueOf(len));
//                            if(i==0){
////                                stringBuffer.append(new String(bytes, 16, len));
//                                fileOutputStream.write(bytes,17,len-17);
//                            }
//                            else {
////                                stringBuffer.append(new String(bytes, 0, len));
//                                fileOutputStream.write(bytes,0,len);
//                            }
                        }

//                        for (int i=0;i<30;i++){
//                            len=inputStream.read(bytes);
//                            Log.v("len77777777777",String.valueOf(len));
//                            if(i==0){
////                                stringBuffer.append(new String(bytes, 16, len));
//                                fileOutputStream.write(bytes,17,len-17);
//                            }
//                            else {
////                                stringBuffer.append(new String(bytes, 0, len));
//                                fileOutputStream.write(bytes,0,len);
//                            }


//                        }
//                        while ((len = inputStream.read(bytes)) != -1) {
//                            stringBuffer.append(new String(bytes, 0, len));
//                        }
//                        Log.v("Show","ShowPIC1");
//                        showPic1();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File file=new File(filename2);
                        Uri uri= Uri.fromFile(file);
                        intent.setData(uri);
                        Context context=SecondDataActivity.this.getApplicationContext();
                        context.sendBroadcast(intent);


                        Log.v("file",Environment.getExternalStorageDirectory().getPath() + "/acc1.txt");
                        readData2();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void readData1(){
        try{
            FileInputStream fileInputStream=new FileInputStream(filename1);
//            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            byte buffer[]=new byte[1024];
            byte b[]=new byte[1];
            String dataString="";
            int pointNum=0;
            int len;
            while (pointNum<400){
                Log.v("PointNum",String.valueOf(pointNum));
                len=fileInputStream.read(buffer);
                if (pointNum==0){
                    dataString=dataString+new String(buffer,1,len-1);
                }
                else {
                    dataString=dataString+new String(buffer,0,len);
                }

                Log.v("data",dataString);
                fileInputStream.read(b);
                while (!new String(b).equals("#")){
                    dataString=dataString+new String(b);
                    fileInputStream.read(b);
                }
                String[] pointList=dataString.split("#");

                for(int i=0;i<pointList.length;i++){
                    String[] point=pointList[i].split(",");
                    ArrayList point_xy=new ArrayList<>(Arrays.asList());
//                    Log.v("x",point[0].toString());
                    point_xy.add(Double.parseDouble(point[0].toString()));
                    point_xy.add(Float.parseFloat(point[1].toString())*1000);
                    dataArrayList1.add(point_xy);
                }
                pointNum+=count(dataString,"#");
                dataString="";
            }
            fileInputStream.close();
            Collections.reverse(dataArrayList1);
            Message message=new Message();
            message.what=READ1;
            handler.sendMessage(message);
//            Log.v("type",dataArrayList.get(0).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readData2(){
        try{
            FileInputStream fileInputStream=new FileInputStream(filename2);
//            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            byte buffer[]=new byte[1024];
            byte b[]=new byte[1];
            String dataString="";
            int pointNum=0;
            int len;
            while (pointNum<400){
                Log.v("PointNum",String.valueOf(pointNum));
                len=fileInputStream.read(buffer);
                if (pointNum==0){
                    dataString=dataString+new String(buffer,1,len-1);
                }
                else {
                    dataString=dataString+new String(buffer,0,len);
                }

                Log.v("data",dataString);
                fileInputStream.read(b);
                while (!new String(b).equals("#")){
                    dataString=dataString+new String(b);
                    fileInputStream.read(b);
                }
                String[] pointList=dataString.split("#");

                for(int i=0;i<pointList.length;i++){
                    String[] point=pointList[i].split(",");
                    ArrayList point_xy=new ArrayList<>(Arrays.asList());
//                    Log.v("x",point[0].toString());
                    point_xy.add(Double.parseDouble(point[0].toString()));
                    BigDecimal d1 = new BigDecimal(Double.toString(Double.parseDouble(point[1].toString())*1000));
                    point_xy.add(d1);
                    dataArrayList2.add(point_xy);
                }
                pointNum+=count(dataString,"#");
                dataString="";
            }
            fileInputStream.close();
            Message message=new Message();
            message.what=READ2;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    private void showPic1(){
        HIChart chart=new HIChart();
        chart.setType("line");
        chart.setZoomType("xy");
        options1.setChart(chart);
        HITitle title=new HITitle();
        title.setText("时域图");
        options1.setTitle(title);
        HICredits credits=new HICredits();
        credits.setHref("http://111.231.62.68/Required_data/2/SH_Acc_list/");
        credits.setText("http://111.231.62.68/Required_data/2/SH_Acc_list/");
        credits.setEnabled(false);
        options1.setCredits(credits);
//        dataArrayList=new ArrayList<>(Arrays.asList(1,2,3));
        line1.setLineWidth(0.2);
        line1.setData(dataArrayList1);
        final HIXAxis axis=new HIXAxis();
        axis.setType("datetime");
        axis.setTitle(new HITitle());
        axis.getTitle().setText("时间");
        options1.setXAxis(new ArrayList(){{add(axis);}});
        final HIYAxis hiyAxis = new HIYAxis();
//        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("幅值/(10^-3)");
        options1.setYAxis(new ArrayList(){{add(hiyAxis);}});
        options1.setSeries(new ArrayList<HISeries>(Collections.singletonList(line1)));
        HITooltip tooltip=new HITooltip();
        tooltip.setValueDecimals(3);
        tooltip.setXDateFormat("%Y-%m-%d %H:%M:%S.%L");
        options1.setTooltip(tooltip);

        chartView1.setOptions(options1);
    }

    private void showPic2(){
        HIChart chart=new HIChart();
        chart.setType("line");
        chart.setZoomType("xy");
        options2.setChart(chart);
        HITitle title=new HITitle();
        title.setText("频谱图");
        options2.setTitle(title);
        HICredits credits=new HICredits();
        credits.setHref("http://111.231.62.68/Required_data/2/SH_Acc_list/");
        credits.setText("http://111.231.62.68/Required_data/2/SH_Acc_list/");
        credits.setEnabled(false);
        options2.setCredits(credits);
//        dataArrayList=new ArrayList<>(Arrays.asList(1,2,3));
        line2.setLineWidth(0.2);
        line2.setData(dataArrayList2);
        final HIXAxis axis=new HIXAxis();
//        axis.setType("datetime");
        axis.setTitle(new HITitle());
        axis.getTitle().setText("频率/(Hz)");
        options2.setXAxis(new ArrayList(){{add(axis);}});
        final HIYAxis hiyAxis = new HIYAxis();
//        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("幅值/(10^-3)");
        options2.setYAxis(new ArrayList(){{add(hiyAxis);}});
        options2.setSeries(new ArrayList<HISeries>(Collections.singletonList(line2)));
        chartView2.setOptions(options2);
        HITooltip tooltip=new HITooltip();
        tooltip.setValueDecimals(3);
        options2.setTooltip(tooltip);
    }

    private void update(){
        Timer timer = new Timer();// 实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
//                int index=(int)(Math.random()*200);
//                dataArrayList.remove(0);
//                ArrayList temp=(ArrayList)dataArrayList.get(index);
////                temp.add(dataArrayList(index))
//                dataArrayList.add(temp);
//                line.setData(dataArrayList);
//                options.setSeries(new ArrayList<HISeries>(Collections.singletonList(line)));
//                chartView1.setOptions(options);
                Message message=new Message();
                message.what=FINISH;
                handler.sendMessage(message);
//                btn1.performClick();
//                btn1.performAccessibilityAction()
                update();
            }
        }, 1000);
    }

}
