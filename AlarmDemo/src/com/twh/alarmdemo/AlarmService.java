package com.twh.alarmdemo;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service{

	MediaPlayer mp = new MediaPlayer();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		MainActivity app = MainActivity.getApp();
		app.btEvent("from notifyservice");
		Toast.makeText(this,"hello my alarm", Toast.LENGTH_LONG).show();
		
		
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer arg0) {
				Toast.makeText(AlarmService.this,"over", Toast.LENGTH_LONG).show();
				mp.release();
			}
		});
		
		
		mp.reset();
		mp = MediaPlayer.create(AlarmService.this, R.raw.down);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start();
		
		Timer mTimer = new Timer();
		TimerTask mTimerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				onDestroy();
			}
		};
		mTimer.schedule(mTimerTask, 20000);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mp.stop();
		mp.release();
		mp = null;
		super.onDestroy();
	}
	
	
}
