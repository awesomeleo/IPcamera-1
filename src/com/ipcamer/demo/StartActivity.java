package com.ipcamer.demo;

import java.util.Date;

import object.p2pipcam.nativecaller.BridgeService;
import object.p2pipcam.nativecaller.NativeCaller;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity {
	//���к��һ������
	//��ʱ����ʾ��˾logo
	private static final String LOG_TAG = "StartActivity";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Intent in = new Intent(StartActivity.this, AddCameraActivity.class);//��ת���ҳ��
			startActivity(in);
			finish();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "StartActivity onCreate");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
		Intent intent = new Intent();
		intent.setClass(StartActivity.this, BridgeService.class);//1�ȿ���service
		startService(intent);
		new Thread(new Runnable() {//��ʱ����3�����ת
			@Override
			public void run() {
				try {
					NativeCaller
							.PPPPInitial("EBGAEOBOKHJMHMJMENGKFIEEHBMDHNNEGNEBBCCCBIIHLHLOCIACCJOFHHLLJEKHBFMPLMCHPHMHAGDHJNNHIFBAMC");//2��jni���еķ���
					long lStartTime = new Date().getTime();
					int nRes = NativeCaller.PPPPNetworkDetect();
					long lEndTime = new Date().getTime();
					if (lEndTime - lStartTime <= 1000) {
						Thread.sleep(3000);
					}
					Message msg = new Message();
					mHandler.sendMessage(msg);
				} catch (Exception e) {

				}
			}
		}).start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		return super.onKeyDown(keyCode, event);
	}

}