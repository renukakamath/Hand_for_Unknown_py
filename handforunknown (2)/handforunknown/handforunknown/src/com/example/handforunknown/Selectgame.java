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
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Selectgame extends Activity implements OnItemClickListener,JsonResponse {
	
	ListView lv;
	String[] assignid,game_id,title,description,assigndate,details;
	SharedPreferences sh;
	String logid;
	public static String assignids,gameids,titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectgame);
		
		lv=(ListView)findViewById(R.id.lvgames);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Selectgame.this;
		String q="viewgame/?cid="+Selectchildforgames.cid;
		jr.execute(q);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selectgame, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		assignids=assignid[arg2];
		titles=title[arg2];
		gameids=game_id[arg2];
		
		final CharSequence[] items = {"Start Game", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Selectgame.this);
        builder.setTitle("Select Any!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Start Game")) 
                {
                    startActivity(new Intent(getApplicationContext(),Simplegame.class));

                }  else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				assignid=new String[ja.length()];
				game_id=new String[ja.length()];
				title= new String[ja.length()];
				description= new String[ja.length()];
				assigndate= new String[ja.length()];
				
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					assignid[i]=ja.getJSONObject(i).getString("ass_game_id");
					game_id[i]=ja.getJSONObject(i).getString("game_id");
					title[i]=ja.getJSONObject(i).getString("title");
					description[i]=ja.getJSONObject(i).getString("description");
					assigndate[i]=ja.getJSONObject(i).getString("ass_date");
					
					
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
	
	

}
