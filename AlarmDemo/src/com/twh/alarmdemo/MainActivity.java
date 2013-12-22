package com.twh.alarmdemo;

import java.util.Calendar;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static MainActivity appRef=null;
	final int DIALOG_TIME = 0;    //设置对话框id  
	Calendar cal=Calendar.getInstance();  
	private AlarmManager alarmManager=null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        appRef=this;

	}

    public void call_receive(){
    	setTitle("waiting...Alarm=5min");
    	Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
    	PendingIntent p_intent= PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND,5);
    	AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
    	am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), p_intent);
    }
    
    public void exit_receive(){
    	Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
    	PendingIntent p_intent= PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
    	AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
    	am.cancel(p_intent);
    	setTitle("stop");
    	
		Intent i = new Intent("com.twh.alarmdemo.AlarmService"); 
		stopService(i);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	menu.add(1, 1, 1, "startAlarmService");
    	menu.add(1, 2, 2, "stopAlarmService");
    	return true;
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	switch (item.getItemId()){
    	case 1:
//    		call_receive();
    		showDialog(0);
    		break;
    	case 2:
    		exit_receive();
    		break;
    	default:
    		break;
    	}
		return super.onOptionsItemSelected(item);
	}

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
    }
    
	public static MainActivity getApp(){
    	return appRef;
    }
    public void btEvent(String data){
    	setTitle(data);
    }
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
        Dialog dialog=null;  
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE); 
        switch (id) {  
        case DIALOG_TIME:  
            dialog=new TimePickerDialog(  
                    this,   
                    new TimePickerDialog.OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							// TODO Auto-generated method stub
							Calendar c=Calendar.getInstance();//获取日期对象      
                            c.setTimeInMillis(System.currentTimeMillis());        //设置Calendar对象  
                            c.set(Calendar.HOUR, hourOfDay);        //设置闹钟小时数  
                            c.set(Calendar.MINUTE, minute);            //设置闹钟的分钟数  
                            c.set(Calendar.SECOND, 0);                //设置闹钟的秒数  
                            c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数  
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //创建Intent对象  
                            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);    //创建PendingIntent  
                            //alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);        //设置闹钟  
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);        //设置闹钟，当前时间就唤醒  
                            Toast.makeText(MainActivity.this, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户  
						}
					},
                    cal.get(Calendar.HOUR_OF_DAY),   
                    cal.get(Calendar.MINUTE),  
                    false);  
 
            break;  
        }  
        return dialog;  
    }  

}
