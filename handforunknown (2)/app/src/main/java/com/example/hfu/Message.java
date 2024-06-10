package com.example.hfu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Message extends Activity implements JsonResponse {

	EditText e1;
	Button b1;
	ListView l1;
	String message,logid;
	String[] messagess,replyss,datess,details;
	SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		e1=(EditText)findViewById(R.id.etmessage);
		b1=(Button)findViewById(R.id.btmessage);
		l1=(ListView)findViewById(R.id.lvmesage);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse) Message.this;
		String q="viewmessage/?logid="+logid;
		q.replace("", "%20");
		jr.execute(q);
		
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message=e1.getText().toString();
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Message.this;
				String q="message/?messages="+message+"&logid="+logid;
				q.replace("", "%20");
				jr.execute(q);
				
				
			}
		});
	}


	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub

		try
		{
			String method=jo.getString("method");
			Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();
			if(method.equalsIgnoreCase("message"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Enquiry Added", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),Message.class));
					}
					
					else
					{
						Toast.makeText(getApplicationContext(), "Not Successfull", Toast.LENGTH_LONG).show();
					}
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("viewmessage"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						messagess=new String[ja.length()];
						replyss= new String[ja.length()];
						datess= new String[ja.length()];
						details= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
							messagess[i]=ja.getJSONObject(i).getString("mess_des");
							
//							Toast.makeText(getApplicationContext(), ja.getJSONObject(i).getString("replay"), Toast.LENGTH_LONG).show();
							if(ja.getJSONObject(i).getString("mess_reply").equals("pending"))
							{
								replyss[i]="Not Replyed";
							}
							else
							{
								replyss[i]=ja.getJSONObject(i).getString("reply");
							}
							
							datess[i]=ja.getJSONObject(i).getString("date");
							details[i]="Enquiry : "+messagess[i]+"\nReply : "+replyss[i]+"\nDate : "+datess[i];
						}
						//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
						l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,details));
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
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
