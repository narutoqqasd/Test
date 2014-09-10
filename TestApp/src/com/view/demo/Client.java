package com.view.demo;

import android.app.Activity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.lsd.smartconfig.lib.SmartConfigActivity;
import com.view.smartconfig.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class Client extends Activity{
	private String TAG = "===Client===";
	private String TAG1 = "===Send===";
	private TextView tv1 = null;
	Handler mhandler;
	Handler mhandlerSend;
	boolean isRun = true;
	EditText edtsendms;
	Button btnsend;
	private String sendstr = "";
	SharedPreferences sp;
	Button btnSetting;
	Button SM;
	private Context ctx;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	SocThread socketThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setMovementMethod(ScrollingMovementMethod.getInstance());  
		btnsend = (Button) findViewById(R.id.button1);
		SM = (Button) findViewById(R.id.button3);
		ctx = Client.this;
		edtsendms = (EditText) findViewById(R.id.editText1);
		btnSetting = (Button) findViewById(R.id.button2);
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
					MyLog.i(TAG, "mhandler���յ�msg=" + msg.what);
					if (msg.obj != null) {
						String s = msg.obj.toString();
						if (s.trim().length() > 0) {
							MyLog.i(TAG, "mhandler���յ�obj=" + s);
							MyLog.i(TAG, "��ʼ����UI");
							tv1.append("Server:" + s);
							MyLog.i(TAG, "����UI���");
						} else {
							Log.i(TAG, "û�����ݷ��ز�����");
						}
					}
				} catch (Exception ee) {
					MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		mhandlerSend = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
					MyLog.i(TAG, "mhandlerSend���յ�msg.what=" + msg.what);
					String s = msg.obj.toString();
					if (msg.what == 1) {
						tv1.append("\n ME: " + s + "      ���ͳɹ�");
					} else {
						tv1.append("\n ME: " + s + "     ����ʧ��");
					}
				} catch (Exception ee) {
					MyLog.i(TAG, "���ع��̳����쳣");
					ee.printStackTrace();
				}
			}
		};
		startSocket();
		btnsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��������
				MyLog.i(TAG, "׼����������");
				sendstr = edtsendms.getText().toString().trim();
				socketThread.Send(sendstr);

			}
		});
		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ת�����ý���
				Intent intent = new Intent();
				intent.setClass(Client.this, Setting.class);
				MyLog.i(TAG, "��ת�����ý���");
				ctx.startActivity(intent);// ���½���

			}
		});
		SM.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Client.this, SmartConfigActivityDemo.class);
				MyLog.i(TAG, "��ת�����ý���");
				ctx.startActivity(intent);// ���½���

			}
		});
	}

	public void startSocket() {
		socketThread = new SocThread(mhandler, mhandlerSend, ctx);
		socketThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

	private void stopSocket() {
		socketThread.isRun = false;
		socketThread.close();
		socketThread = null;
		MyLog.i(TAG, "Socket����ֹ");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "start onStart~~~");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "start onRestart~~~");
		startSocket();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "start onResume~~~");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "start onPause~~~");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "start onStop~~~");
		stopSocket();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "start onDestroy~~~");

	}

}


