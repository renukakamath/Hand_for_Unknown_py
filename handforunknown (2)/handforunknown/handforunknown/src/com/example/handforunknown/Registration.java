package com.example.handforunknown;



import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity implements JsonResponse
{

	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	Button b1;
    String firstname;
	String lastname;
	String contact,pincode;
	String email,username,password,house,place;
	RadioButton r1,r2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		e1=(EditText)findViewById(R.id.etfirstname);
		e2=(EditText)findViewById(R.id.etlastname);
		e3=(EditText)findViewById(R.id.ethouse);
		e4=(EditText)findViewById(R.id.etplace);
		e5=(EditText)findViewById(R.id.etpincode);
		e6=(EditText)findViewById(R.id.etphone);
		e7=(EditText)findViewById(R.id.etemail);
		e8=(EditText)findViewById(R.id.etusername);
		e9=(EditText)findViewById(R.id.etpassword);
		
		
		b1=(Button)findViewById(R.id.btsubmit);
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				firstname=e1.getText().toString();
				lastname=e2.getText().toString();
				house=e3.getText().toString();
				place=e4.getText().toString();
				pincode=e5.getText().toString();
				contact=e6.getText().toString();
				email=e7.getText().toString();
				username=e8.getText().toString();
				password=e9.getText().toString();
			
//				Toast.makeText(getApplicationContext(),"FIRSTNAME="+firstname+"\nLASTNAME="+lastname+"\nHOUSENAME="+housename+"\nHOMETOWN="+HomeTown+"\nPINCODE="+Pincode+"\nEMAIL="+email+"\nCONTACT="+contact,Toast.LENGTH_LONG).show();
//				startActivity(new Intent(getApplicationContext(),Login.class));
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Registration.this;
				String q="register/?firstname="+firstname+"&lastname="+lastname+"&house="+house+"&place="+place+"&pincode="+pincode+"&phone="+contact+"&email="+email+"&username="+username+"&password="+password;
				q.replace("", "%20");
				jr.execute(q);
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
	
	  public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),Login.class);			
			startActivity(b);
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
			Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
			startActivity(new Intent(getApplicationContext(),Login.class));
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

}
