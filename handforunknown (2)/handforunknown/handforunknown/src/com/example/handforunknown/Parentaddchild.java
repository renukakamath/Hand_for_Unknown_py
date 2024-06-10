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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Parentaddchild extends Activity implements JsonResponse
{

	EditText e1,e2;
	Button b1;
	RadioButton r1,r2;
    String name,dob,gender;
    
    ListView lv;
	String[] id,names,dod,genders,details;
	String logid;
	SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parentaddchild);
		
		e1=(EditText)findViewById(R.id.etname);
		e2=(EditText)findViewById(R.id.etdob);
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		
		b1=(Button)findViewById(R.id.btsubmit);
		
		lv=(ListView)findViewById(R.id.listView1);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Parentaddchild.this;
		String q="viewchilds/?logid="+logid; 
		jr.execute(q);
		
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name=e1.getText().toString();
				dob=e2.getText().toString();
				if(r1.isChecked())
				{
					gender="Male";
				}
				else
				{
					gender="Female";
				}
				
//				Toast.makeText(getApplicationContext(),"FIRSTNAME="+firstname+"\nLASTNAME="+lastname+"\nHOUSENAME="+housename+"\nHOMETOWN="+HomeTown+"\nPINCODE="+Pincode+"\nEMAIL="+email+"\nCONTACT="+contact,Toast.LENGTH_LONG).show();
//				startActivity(new Intent(getApplicationContext(),Login.class));
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Parentaddchild.this;
				String q="childregister/?name="+name+"&gender="+gender+"&dob="+dob+"&logid="+logid;
				q.replace("", "%20");
				jr.execute(q);
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parentaddchild, menu);
		return true;
	}
	
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),ParentHome.class);			
			startActivity(b);
		}

@Override
public void response(JSONObject jo) {
	// TODO Auto-generated method stub
	try
	{
		String method=jo.getString("method");
		Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();
		if(method.equalsIgnoreCase("childregister"))
		{
			try
			{
				String status=jo.getString("status");
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),ParentHome.class));
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
		if(method.equalsIgnoreCase("viewchilds"))
		{
			try{
				String status=jo.getString("status");
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					id=new String[ja.length()];
					names= new String[ja.length()];
					dod=new String[ja.length()];
					genders= new String[ja.length()];
					details= new String[ja.length()];
					
					
					for(int i=0;i<ja.length();i++)
					{
						id[i]=ja.getJSONObject(i).getString("child_id");
						names[i]=ja.getJSONObject(i).getString("name");
						dod[i]=ja.getJSONObject(i).getString("dob");
						genders[i]=ja.getJSONObject(i).getString("gender");
						
						details[i]="Name : "+names[i]+"\nDate of Birth : "+dod[i]+"\nGender : "+genders[i];
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

	}
	catch(Exception e)
	{
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
	}
}


}
