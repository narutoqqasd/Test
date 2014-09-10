package com.view.demo;

import com.view.smartconfig.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeA extends Activity {
	SharedPreferences preferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
				int count = preferences.getInt("count", 0);
				// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
				Intent intent = new Intent();
				if (count == 0) {
					intent.setClass(getApplicationContext(), WhatsNew.class);
				} else {
					intent.setClass(getApplicationContext(),
							Client.class);
				}
				startActivity(intent);
				finish();
				Editor editor = preferences.edit();
				// 存入数据
				editor.putInt("count", ++count);
				// 提交修改
				editor.commit();

			}
		}, 2000);
	}

}