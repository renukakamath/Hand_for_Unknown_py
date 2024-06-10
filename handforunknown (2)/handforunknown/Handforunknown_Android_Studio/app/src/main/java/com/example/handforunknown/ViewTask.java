package com.example.handforunknown;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewTask extends Activity implements JsonResponse, OnItemClickListener {
	
	ListView lv;
	String[] assign_id,title,description,assigndate,details;
	String logid,opids,latis,longis;
	SharedPreferences sh;
	public static String assignids,tasks;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		
		lv=(ListView)findViewById(R.id.lvtask);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ViewTask.this;
		String q="viewtask/?cid="+Selectachildfortask.cid;
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task, menu);
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
				assign_id=new String[ja.length()];
				title= new String[ja.length()];
				description= new String[ja.length()];
				assigndate= new String[ja.length()];
				
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					assign_id[i]=ja.getJSONObject(i).getString("assign_id");
					title[i]=ja.getJSONObject(i).getString("title");
					description[i]=ja.getJSONObject(i).getString("description");
					assigndate[i]=ja.getJSONObject(i).getString("assign_date");
					
					
//					
					details[i]="\nTitle : "+title[i]+"\nDescription : "+description[i]+"\nAssigned Date : "+assigndate[i];
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		assignids=assign_id[arg2];
		tasks=title[arg2];
		
		final CharSequence[] items = {"Upload Perfomance", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTask.this);
        builder.setTitle("Select Any!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Upload Perfomance")) 
                {
                    startActivity(new Intent(getApplicationContext(),Updatetaskperfomance.class));

                }  else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
	}

}
