package com.example.handforunknown;


import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewDoctors extends Activity implements JsonResponse {

	ListView lv;
	String[] name,email,phone,qualification,details;
	String logid,opids,latis,longis;
	SharedPreferences sh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_doctors);
		
		lv=(ListView)findViewById(R.id.lvdoctors);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ViewDoctors.this;
		String q="viewdoctors/";
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_doctors, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				name=new String[ja.length()];
				phone= new String[ja.length()];
				email= new String[ja.length()];
				qualification=new String[ja.length()];
				
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					name[i]=ja.getJSONObject(i).getString("name");
					phone[i]=ja.getJSONObject(i).getString("phone");
					email[i]=ja.getJSONObject(i).getString("email");
					qualification[i]=ja.getJSONObject(i).getString("qualification");
					
//					
					details[i]="Name : "+name[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nQualification : "+qualification[i];
				}
				//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
				lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,details));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),ParentHome.class);			
		startActivity(b);
	}


	
}
