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
	final int DIALOG_TIME = 0;    //���öԻ���id  
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
        Toast.makeText(this, "ѡ��˵��ر���", Toast.LENGTH_LONG).show();
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
							Calendar c=Calendar.getInstance();//��ȡ���ڶ���      
                            c.setTimeInMillis(System.currentTimeMillis());        //����Calendar����  
                            c.set(Calendar.HOUR, hourOfDay);        //��������Сʱ��  
                            c.set(Calendar.MINUTE, minute);            //�������ӵķ�����  
                            c.set(Calendar.SECOND, 0);                //�������ӵ�����  
                            c.set(Calendar.MILLISECOND, 0);            //�������ӵĺ�����  
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //����Intent����  
                            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);    //����PendingIntent  
                            //alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);        //��������  
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);        //�������ӣ���ǰʱ��ͻ���  
                            Toast.makeText(MainActivity.this, "�������óɹ�", Toast.LENGTH_LONG).show();//��ʾ�û�  
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
