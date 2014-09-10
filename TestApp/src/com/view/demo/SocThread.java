package com.view.demo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
public class SocThread extends Thread{
	private String ip = "10.0.0.113";
	private int port = 13000;
	private String TAG = "socket thread";
	private int timeout = 10000;

	public Socket client = null;
	PrintWriter out;
	BufferedReader in;
	public boolean isRun = true;
	Handler inHandler;
	Handler outHandler;
	Context ctx;
	private String TAG1 = "===Send===";
	SharedPreferences sp;

	public SocThread(Handler handlerin, Handler handlerout, Context context) {
		inHandler = handlerin;
		outHandler = handlerout;
		ctx = context;
		MyLog.i(TAG, "�����߳�socket");
	}

	/**
	 * ����socket������
	 */
	public void conn() {

		try {
			initdate();
			Log.i(TAG, "�����С���");
			client = new Socket(ip, port);
			client.setSoTimeout(timeout);// ��������ʱ��
			MyLog.i(TAG, "���ӳɹ�");
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream())), true);
			MyLog.i(TAG, "�����������ȡ�ɹ�");
		} catch (UnknownHostException e) {
			MyLog.i(TAG, "���Ӵ���UnknownHostException ���»�ȡ");
			e.printStackTrace();
			conn();
		} catch (IOException e) {
			MyLog.i(TAG, "���ӷ�����io����");
			e.printStackTrace();
		} catch (Exception e) {
			MyLog.i(TAG, "���ӷ���������Exception" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void initdate() {
		sp = ctx.getSharedPreferences("SP", ctx.MODE_PRIVATE);
		ip = sp.getString("ipstr", ip);
		port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
		MyLog.i(TAG, "��ȡ��ip�˿�:" + ip + ";" + port);
	}

	/**
	 * ʵʱ��������
	 */
	@Override
	public void run() {
		MyLog.i(TAG, "�߳�socket��ʼ����");
		conn();
		MyLog.i(TAG, "1.run��ʼ");
		String line = "";
		while (isRun) {
			try {
				if (client != null) {
					MyLog.i(TAG, "2.�������");
					while ((line = in.readLine()) != null) {
						MyLog.i(TAG, "3.getdata" + line + " len=" + line.length());
						MyLog.i(TAG, "4.start set Message");
						Message msg = inHandler.obtainMessage();
						msg.obj = line;
						inHandler.sendMessage(msg);// ������ظ�UI����
						MyLog.i(TAG1, "5.send to handler");
					}

				} else {
					MyLog.i(TAG, "û�п�������");
					conn();
				}
			} catch (Exception e) {
				MyLog.i(TAG, "���ݽ��մ���" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @param mess
	 */
	public void Send(String mess) {
		try {
			if (client != null) {
				MyLog.i(TAG1, "����" + mess + "��"
						+ client.getInetAddress().getHostAddress() + ":"
						+ String.valueOf(client.getPort()));
				out.println(mess);
				out.flush();
				MyLog.i(TAG1, "���ͳɹ�");
				Message msg = outHandler.obtainMessage();
				msg.obj = mess;
				msg.what = 1;
				outHandler.sendMessage(msg);// ������ظ�UI����
			} else {
				MyLog.i(TAG, "client ������");
				Message msg = outHandler.obtainMessage();
				msg.obj = mess;
				msg.what = 0;
				outHandler.sendMessage(msg);// ������ظ�UI����
				MyLog.i(TAG, "���Ӳ�������������");
				conn();
			}

		} catch (Exception e) {
			MyLog.i(TAG1, "send error");
			e.printStackTrace();
		} finally {
			MyLog.i(TAG1, "�������");

		}
	}

	/**
	 * �ر�����
	 */
	public void close() {
		try {
			if (client != null) {
				MyLog.i(TAG, "close in");
				in.close();
				MyLog.i(TAG, "close out");
				out.close();
				MyLog.i(TAG, "close client");
				client.close();
			}
		} catch (Exception e) {
			MyLog.i(TAG, "close err");
			e.printStackTrace();
		}

	}
}
