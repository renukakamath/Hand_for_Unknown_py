package com.example.hfu;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class SocialDistanceAlert extends Service  {

	Handler hd;
	int NOTIFICATION_ID = 234;
	NotificationManager notificationManager;
	SharedPreferences sh;

	String alerts;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		try {
			if (Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		hd = new Handler();
		hd.post(r);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		hd.removeCallbacks(r);
	}

	public Runnable r = new Runnable() {

		@Override
		public void run() {
			try {
				getNotification();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}

//			hd.postDelayed(r, 30000);
		}
	};

	public void notification_popup() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			String CHANNEL_ID = "my_channel_01";
			CharSequence name = "my_channel";
			String Description = "This is my channel";
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
			mChannel.setDescription(Description);
			mChannel.enableLights(true);
			mChannel.setLightColor(Color.RED);
			mChannel.enableVibration(true);
			mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//			mChannel.setVibrationPattern(new long[]{0, 800, 200, 1200, 300, 2000, 400, 4000});
			mChannel.setShowBadge(false);
			notificationManager.createNotificationChannel(mChannel);
			stopService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "my_channel_01")
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("Predicted Output ")
				.setContentText(""+Checksymptoms.outss);
//				Toast.makeText(getApplicationContext(),"hellooooo",Toast.LENGTH_LONG).show();
//		Intent resultIntent = new Intent(getApplicationContext(), Details.class);
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//		stackBuilder.addParentStack(MainActivity.class);
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//		builder.setContentIntent(resultPendingIntent);
		notificationManager.notify(NOTIFICATION_ID, builder.build());
	}

	void getNotification() {
		try {
//			if (sh.getString("alerttype","").equalsIgnoreCase("ON")){
////					LocationService.lati.length() > 5 && LocationService.logi.length() > 5) {
////				String latitude = LocationService.lati.substring(0, 6);
////				String longitude = LocationService.logi.substring(0, 6);
////				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
////				String date_time = sdf.format(new Date());
////				Toast.makeText(getApplicationContext(), date_time, Toast.LENGTH_LONG).show();
//				JsonReq JR = new JsonReq();
//				JR.json_response = (JsonResponse) SocialDistanceAlert.this;
//				String q = "/get_social_distancing?latitude=" + LocationService.lati + "&longitude=" + LocationService.logi;
//				JR.execute(q);

			notification_popup();
//			}
		}
		catch (Exception e) {

		}
	}

//	MediaPlayer mPlayer2;

//	@Override
//	public void response(JSONObject jo) {
//		try {
//			String status = jo.getString("status");
//			Log.d("pearl", status);
//
//			if (status.equalsIgnoreCase("success")) {
//
//				JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
//
//
//				for (int i = 0; i < ja1.length(); i++) {
//					alerts= ja1.getJSONObject(i).getString("user_distance");
//					Log.d("pearl", "ALert"+alerts);
//					Toast.makeText(getApplicationContext(), alerts, Toast.LENGTH_LONG).show();
//					if(Float.parseFloat( alerts)<15.0){
//						notification_popup();
//					}
//				}
//
////				int count = Integer.parseInt(jo.getString("data"));
////				Log.v("pearl_count--", count + "");
////				if (count > 0) {
//////					mPlayer2 = MediaPlayer.create(this, R.raw.noti_sound);
//////					mPlayer2.start();
////					notification_popup();
////				}
//			} else {
//				Toast.makeText(getApplicationContext(), "Nothing to show you!!", Toast.LENGTH_LONG).show();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
//		}
//	}
}