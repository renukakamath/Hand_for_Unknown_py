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

public class Selectachildfortask extends Activity implements JsonResponse, OnItemClickListener{

	ListView lv;
	String[] child_id,name;
	String logid,opids,latis,longis;
	SharedPreferences sh;
	public static String cid,cnames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectachildfortask);
		
		lv=(ListView)findViewById(R.id.lvtchild);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Selectachildfortask.this;
		String q="viewchildss/?lid="+logid;
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selectachildfortask, menu);
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
				child_id= new String[ja.length()];
				
				for(int i=0;i<ja.length();i++)
				{
					child_id[i]=ja.getJSONObject(i).getString("child_id");
					name[i]=ja.getJSONObject(i).getString("name");
					
//					
//					details[i]="Name : "+name[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nQualification : "+qualification[i];
				}
				//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
				lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,name));
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
		
		cid=child_id[arg2];
		cnames=name[arg2];
		
		final CharSequence[] items = {"View Assigned Tasks", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Selectachildfortask.this);
        builder.setTitle("Select Any!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Assigned Tasks")) 
                {
                    startActivity(new Intent(getApplicationContext(),ViewTask.class));

                }  else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
		
	}
	
}
