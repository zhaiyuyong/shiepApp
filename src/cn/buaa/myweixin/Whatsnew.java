package cn.buaa.myweixin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.edu.shiep.view.ClassView;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Whatsnew extends Activity {
	
	private ViewPager mViewPager;	
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private ImageView mPage5;
	private Context context;
	private AppSystem appSystem;
	
	private int dayOfWeek;
		
	private int currIndex = 0;
	private TextView zhouji_title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatsnew_viewpager);
        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);        
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        context = this;
        appSystem = (AppSystem) this.getApplication();
        mPage0 = (ImageView)findViewById(R.id.page0);
        mPage1 = (ImageView)findViewById(R.id.page1);
        mPage2 = (ImageView)findViewById(R.id.page2);
        mPage3 = (ImageView)findViewById(R.id.page3);
        mPage4 = (ImageView)findViewById(R.id.page4);
        mPage5 = (ImageView)findViewById(R.id.page5);
        
      //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        ClassView classView = new ClassView(context,appSystem);
      //  View view1 = new DayOneView(context).getView();//mLi.inflate(R.layout.whats1, null);
        View view1 = classView.getView(1);
        View view2 = classView.getView(2);//mLi.inflate(R.layout.whats2, null);
        View view3 = classView.getView(3);//mLi.inflate(R.layout.whats3, null);
        View view4 = classView.getView(4);//mLi.inflate(R.layout.whats4, null);
        View view5 = classView.getView(5);//mLi.inflate(R.layout.whats5, null);
        View view6 = classView.getView(6);//mLi.inflate(R.layout.whats6, null);
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        
      //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
		System.out.println("dayOfWeek-------------"+dayOfWeek);
		mViewPager.setCurrentItem(dayOfWeek-2);
		//zhouji_title = 
		if((dayOfWeek-1)==1){
			zhouji_title = (TextView) view1.findViewById(R.id.day_zhouji);
		}else if((dayOfWeek-1)==2){
			zhouji_title = (TextView) view2.findViewById(R.id.day_zhouji);
		}else if((dayOfWeek-1)==3){
			zhouji_title = (TextView) view3.findViewById(R.id.day_zhouji);
		}else if((dayOfWeek-1)==4){
			zhouji_title = (TextView) view3.findViewById(R.id.day_zhouji);
		}else if((dayOfWeek-1)==5){
			zhouji_title = (TextView) view5.findViewById(R.id.day_zhouji);
		}else if((dayOfWeek-1)==6){
			zhouji_title = (TextView) view6.findViewById(R.id.day_zhouji);
		}
		if(zhouji_title!=null){
			zhouji_title.setText(zhouji_title.getText().toString().trim()+"(今天)");
			zhouji_title.setTextColor(Color.BLUE);
		}
		
    }    
    

    public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:				
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 4:
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 5:
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			}
			currIndex = arg0;
			//animation.setFillAfter(true);// True:图片停在动画结束位置
			//animation.setDuration(300);
			//mPageImg.startAnimation(animation);
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
    public void startbutton(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Whatsnew.this,WhatsnewDoor.class);
		startActivity(intent);
		this.finish();
      }  
    
}
