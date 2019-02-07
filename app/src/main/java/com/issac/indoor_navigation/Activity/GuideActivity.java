package com.issac.indoor_navigation.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.issac.indoor_navigation.Adapter.FragAdapter;
import com.issac.indoor_navigation.Fragment.InfoFragment;
import com.issac.indoor_navigation.Fragment.SelectFragment;
import com.issac.indoor_navigation.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends AppCompatActivity {
    private  ViewPager viewPager;
//    private SamplePagerAdapter samplePagerAdapter;
    private FragAdapter fragAdapter;
    private  ArrayList<Fragment> fragments;
//    private ArrayList<View> views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        Button next=(Button)findViewById(R.id.next);
        LayoutInflater li = getLayoutInflater();
        //构造适配器

        fragments = new ArrayList<Fragment>();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() < 2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
                else{
//                    SharedPreferences preferences = getSharedPreferences("userType",MODE_PRIVATE);
//                    int type = preferences.getInt("type",0);
//                    Log.i("Guide","type is " + type);
                    finish();//之后改成跳转到主界面的接口
                }
            }
        });


        SelectFragment selectFragment = new SelectFragment();
        fragments.add(selectFragment);
        fragments.add(new InfoFragment());
        fragments.add(new InfoFragment());
        fragAdapter = new FragAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        viewPager.setAdapter(fragAdapter);
//        viewPager.notify();
        indicator.setViewPager(viewPager);
    }
}
