package com.example.hfu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Selectchildforgames extends Activity implements OnItemClickListener,JsonResponse {

	ListView lv;
	String[] child_id,name;
	String logid,opids,latis,longis;
	SharedPreferences sh;
	public static String cid,cnames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectchildforgames);
		
		lv=(ListView)findViewById(R.id.lvgchild);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Selectchildforgames.this;
		String q="viewchildss/?lid="+logid;
		jr.execute(q);
		
		
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		cid=child_id[arg2];
		cnames=name[arg2];
		
		final CharSequence[] items = {"View Assigned Games", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Selectchildforgames.this);
        builder.setTitle("Select Any!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Assigned Games")) 
                {
                    startActivity(new Intent(getApplicationContext(),Selectgame.class));

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
	
	
	

}
