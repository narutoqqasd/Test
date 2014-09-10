package com.view.demo;

import java.util.ArrayList;

import com.view.smartconfig.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class WhatsNew extends Activity {
	private ViewPager viewPager;
	private ImageView imageView;
	private ArrayList<View> pageViews;
	private ImageView[] imageViews;
	private ViewGroup viewPictures;
	private ViewGroup viewPoints;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();
		pageViews = new ArrayList<View>();
		pageViews.add(inflater.inflate(R.layout.viewpager01, null));
		pageViews.add(inflater.inflate(R.layout.viewpager02, null));
		pageViews.add(inflater.inflate(R.layout.viewpager03, null));
		pageViews.add(inflater.inflate(R.layout.viewpager04, null));
		pageViews.add(inflater.inflate(R.layout.viewpager05, null));
		pageViews.add(inflater.inflate(R.layout.viewpager06, null));

		imageViews = new ImageView[pageViews.size()];
		viewPictures = (ViewGroup) inflater.inflate(R.layout.viewpagers, null);
		viewPager = (ViewPager) viewPictures.findViewById(R.id.guidePagers);
		viewPoints = (ViewGroup) viewPictures.findViewById(R.id.viewPoints);
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(WhatsNew.this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 0, 5, 0);
			// ��СԲ��Ž�������
			imageViews[i] = imageView;
			// Ĭ��ѡ�е��ǵ�һ��ͼƬ����ʱ��һ��СԲ����ѡ��״̬����������
			if (i == 0)
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
			else
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_unfocused));
			// ��imageviews��ӵ�СԲ����ͼ��
			viewPoints.addView(imageViews[i]);
		}

		setContentView(viewPictures);

		viewPager.setAdapter(new NavigationPageAdapter());
		// Ϊviewpager��Ӽ�������view�����仯ʱ����Ӧ
		viewPager.setOnPageChangeListener(new NavigationPageChangeListener());
	}

	// ����ͼƬview��������������Ҫʵ�ֵ��������ĸ�����
	class NavigationPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// ��ʼ��ÿ��Item
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(pageViews.get(position));
			return pageViews.get(position);
		}

		// ����ÿ��Item
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(pageViews.get(position));
		}

	}

	// viewpager�ļ���������Ҫ��onPageSelectedҪʵ��
	class NavigationPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// ѭ����Ҫ�ǿ��Ƶ�����ÿ��СԲ���״̬
			for (int i = 0; i < imageViews.length; i++) {
				// ��ǰview������СԲ��Ϊѡ��״̬
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
				// ��������Ϊ��ѡ��״̬
				if (position != i)
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_indicator_unfocused));
			}
		}

	}

	// ��ʼ��ť��������ʼ��ť��XML�ļ���onClick�������ã�
	// ����ͼ�Ѱ�ť�ڱ�activity��ʵ���������õ�������������Ǳ���ʹ�����������û�б���ԭ��û�ҵ�
	public void startbutton(View v) {
		Intent intent = new Intent(WhatsNew.this, SmartConfigActivityDemo.class);
		startActivity(intent);
		WhatsNew.this.finish();
	}

}
