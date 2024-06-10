package com.example.hfu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Updatetaskperfomance extends Activity implements JsonResponse {

	TextView t1,t2;
	EditText e1;
	Button b1;
	String perfomance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatetaskperfomance);
		
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView3);
		e1=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		
		t1.setText("Child : "+Selectachildfortask.cnames);
		t2.setText("Task : "+ViewTask.tasks);
		
b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				perfomance=e1.getText().toString();
				
				
//				Toast.makeText(getApplicationContext(),"FIRSTNAME="+firstname+"\nLASTNAME="+lastname+"\nHOUSENAME="+housename+"\nHOMETOWN="+HomeTown+"\nPINCODE="+Pincode+"\nEMAIL="+email+"\nCONTACT="+contact,Toast.LENGTH_LONG).show();
//				startActivity(new Intent(getApplicationContext(),Login.class));
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Updatetaskperfomance.this;
				String q="updatetaskperfomance/?assignid="+ViewTask.assignids+"&perfomance="+perfomance;
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
	
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Login.class);			
		startActivity(b);
	}

}
