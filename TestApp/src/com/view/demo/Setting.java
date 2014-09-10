package com.view.demo;
import com.view.smartconfig.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Setting extends Activity{
	private Button btnsave;
	private EditText edtip;
	private EditText edtport;
	SharedPreferences sp;
	private String TAG="=Setting=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		btnsave = (Button) findViewById(R.id.button1);
		edtip = (EditText) findViewById(R.id.editText1);
		edtport = (EditText) findViewById(R.id.editText2);
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		edtip.setText(sp.getString("ipstr", ""));
		edtport.setText(sp.getString("port", ""));

		btnsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG,"开始修改");
				String ip = edtip.getText().toString();//
				String port = edtport.getText().toString();//
				Editor editor = sp.edit();
				editor.putString("ipstr", ip);
				editor.putString("port", port);
				editor.commit();//保存新数据
				//Log.i(TAG, "保存成功"+sp.getString("ipstr", "")+";"+sp.getString("port", ""));
				Toast.makeText(Setting.this, "保存成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
	    		intent.setClass(Setting.this, Client.class);
	    		Setting.this.startActivity(intent);
			}
		});
	}
}
