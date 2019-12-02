package com.example.analogalarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class MainActivity extends AppCompatActivity {
    MySurfaceView mySurfaceView=null;
    SurfaceView surfaceView1;
    SharedPreferences sp = null;
    Button starAlarmBtn,setColorBtn;
    ColorSeekBar seekBar;
    String hrHand,minHand,secHand,milliHand,bodyClock;
    int hr,min,sec,milli,body;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getSharedPreferences("myPref",MODE_PRIVATE)!=null) {
            sp = getSharedPreferences("myPref", MODE_PRIVATE);
        }
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this,300);

        hrHand = sp.getString("hrhand","4598972");
        Log.d("Debug", "Call Alarm 1" + hrHand);

        minHand = sp.getString("minhand","4598972");
        Log.d("Debug", "Call Alarm 2" + minHand);

        secHand = sp.getString("sechand","4598972");
        Log.d("Debug", "Call Alarm 3" + secHand);

        milliHand = sp.getString("millihand","4598972");
        Log.d("Debug", "Call Alarm 4" + milliHand);

        bodyClock= sp.getString("bodyClock","4598972");

        Log.d("Debug","Lnght" + hrHand.length());
        if(flag) {
            mySurfaceView.setSurfaceViewCOlor(hrHand, 1);
            mySurfaceView.setSurfaceViewCOlor(minHand, 2);
            mySurfaceView.setSurfaceViewCOlor(secHand, 3);
            mySurfaceView.setSurfaceViewCOlor(milliHand,4);
            mySurfaceView.setSurfaceViewCOlor(bodyClock, 5);
        }
        else{

            mySurfaceView.setSurfaceViewCOlor("4598972", 6);
            mySurfaceView.setSurfaceViewCOlor("4598972", 6);
            mySurfaceView.setSurfaceViewCOlor("4598972", 6);
            mySurfaceView.setSurfaceViewCOlor("4598972", 6);
            mySurfaceView.setSurfaceViewCOlor("4598972", 6);
        }



        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.surfaceView);
        linearLayout.addView(mySurfaceView);

        final SharedPreferences.Editor editor =getSharedPreferences("myPref", MODE_PRIVATE).edit();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debugg","Call Alarm Activity");
                Intent intent = new Intent(MainActivity.this,
                        AlarmActivity.class);
                startActivity(intent);
            }
        });
        seekBar = (ColorSeekBar) findViewById(R.id.colorSlider);

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg);
        seekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i, int i1, int i2) {
                if (hr == 1) {
                    mySurfaceView.setSurfaceViewCOlor(String.valueOf(seekBar.getColor()),1);
                    editor.putString("hrHand", String.valueOf(seekBar.getColor()));
                }
                else if (min == 1){
                    mySurfaceView.setSurfaceViewCOlor(String.valueOf(seekBar.getColor()),2);
                    editor.putString("minHand", String.valueOf(seekBar.getColor()));
                }
                else if (sec == 1){
                    mySurfaceView.setSurfaceViewCOlor(String.valueOf(seekBar.getColor()),3);
                    editor.putString("secHand", String.valueOf(seekBar.getColor()));
                }
                else if (milli == 1){
                    mySurfaceView.setSurfaceViewCOlor(String.valueOf(seekBar.getColor()),4);
                    editor.putString("milliHand", String.valueOf(seekBar.getColor()));
                }
                else if (body == 1){
                    mySurfaceView.setSurfaceViewCOlor(String.valueOf(seekBar.getColor()),5);
                    editor.putString("bodyClock", String.valueOf(seekBar.getColor()));
                }
                editor.apply();
                editor.commit();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rhr =(RadioButton)findViewById(checkedId);
                Log.d("Debug","Radio Checked:" +checkedId);
                if (checkedId == R.id.rb_hr){
                    hr=1;
                    min = 0;
                    sec = 0;
                    milli = 0;
                    body = 0;
                    flag = true;
                    Log.d("Debug", "Hour Radio Checked:" + checkedId);
                    seekBar.setColorBarPosition(Integer.parseInt(hrHand));
                }
                if (checkedId == R.id.rb_min){
                    hr=0;
                    min = 1;
                    sec = 0;
                    milli = 0;
                    body = 0;
                    flag = true;
                    Log.d("Debug", "Minutes Radio Checked:" + checkedId);
                    seekBar.setColorBarPosition(Integer.parseInt(minHand));
                }
                if (checkedId == R.id.rb_sec){
                    hr=0;
                    min = 0;
                    sec = 1;
                    milli = 0;
                    body = 0;
                    flag = true;
                    Log.d("Debug", "Seconds Radio Checked:" + checkedId);
                    seekBar.setColorBarPosition(Integer.parseInt(secHand));
                }
                if (checkedId == R.id.rb_milli){
                    hr=0;
                    min = 0;
                    sec = 0;
                    milli = 1;
                    body = 0;
                    flag = true;
                    Log.d("Debug", "Milliseconds Radio Checked:" + checkedId);
                    seekBar.setColorBarPosition(Integer.parseInt(milliHand));
                }
                if (checkedId == R.id.clk_body){
                    hr=0;
                    min = 0;
                    sec = 0;
                    milli = 0;
                    body = 1;
                    flag = true;
                    Log.d("Debug", "Clock Body Checked:" + checkedId);
                    seekBar.setColorBarPosition(Integer.parseInt(bodyClock));
                }
                else{
                    flag = false;
                }

                Log.d("Debug","Hour Radio Button");
            };
        });

    }
    protected void onResume(){
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }
}