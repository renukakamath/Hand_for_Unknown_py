package com.example.hfu;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewotherparents extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

	ListView lv;
	String[] name,email,phone,place,details,lid;
	String logid,opids,latis,longis,l_id;
	SharedPreferences sh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_parents);
		
		lv=(ListView)findViewById(R.id.lvdoctors);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");


		lv.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse) Viewotherparents.this;
		String q="viewparent/?lid="+Login.logid;
		jr.execute(q);
		
		
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
				place=new String[ja.length()];
				lid=new String[ja.length()];

				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					name[i]=ja.getJSONObject(i).getString("name");
					phone[i]=ja.getJSONObject(i).getString("phone");
					email[i]=ja.getJSONObject(i).getString("email");
					lid[i]=ja.getJSONObject(i).getString("log_id");
					place[i]=ja.getJSONObject(i).getString("place");
					
//					
					details[i]="Name : "+name[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nPlace : "+place[i];
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


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

		l_id=lid[i];
		SharedPreferences.Editor e=sh.edit();
		e.putString("receiver_id",lid[i]);
		e.putString("name",name[i]);
		e.commit();


		final CharSequence[] items = {"Chat", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(Viewotherparents.this);
		builder.setTitle("Select Any!");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Chat"))
				{
					startActivity(new Intent(getApplicationContext(),ChatHere.class));

				}  else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
}
