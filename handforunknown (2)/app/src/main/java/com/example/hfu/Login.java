package com.example.hfu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends Activity implements JsonResponse {

	EditText e1,e2;
	Button b1,b2;
	String username,password;
	public static String logid,type,checkb;
	SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		e1=(EditText)findViewById(R.id.etusername);
        e2=(EditText)findViewById(R.id.etpassword);
        b1=(Button)findViewById(R.id.btlogin);
        b2=(Button)findViewById(R.id.btnewuser);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				username=e1.getText().toString();
				password=e2.getText().toString();
				
				JsonReq JR= new JsonReq();
				JR.json_response=(JsonResponse)Login.this;
				String q="login/?username=" + username + "&password=" + password;
				JR.execute(q);
				
				
			}
		
		});
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Registration.class));
				
			}
		});
        
        
    }


  

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		 try {
	            String status = jo.getString("status");
	            Log.d("result", status);

	            if (status.equalsIgnoreCase("success")) {
	                JSONArray ja = (JSONArray) jo.getJSONArray("data");
	                logid = ja.getJSONObject(0).getString("log_id");
	                type = ja.getJSONObject(0).getString("usertype");
	                SharedPreferences.Editor ed = sh.edit();
	                ed.putString("logid", logid);
	                ed.putString("checkb", "False");
	                ed.commit();

	                //startService(new Intent(getApplicationContext(), LocationService.class));
	                
	                if (type.equals("parent"))
	                {
	                	Toast.makeText(getApplicationContext(), "Login Successfull....." , Toast.LENGTH_LONG).show();
//	                	startService(new Intent(getApplicationContext(),LocationService.class));
	                    startActivity(new Intent(getApplicationContext(), ParentHome.class));
	               
	                }
	                
	                else
	                {
	                    Toast.makeText(getApplicationContext(), "Login failed..!!", Toast.LENGTH_LONG).show();
	                }
	            } else {
	                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	        }
	}
	  public void onBackPressed() 
			{
				// TODO Auto-generated method stub
				super.onBackPressed();
				Intent b=new Intent(getApplicationContext(),IPSetting.class);			
				startActivity(b);
			}

	  
	  

}
