package com.example.hfu;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ParentHome extends Activity {

	Button b1,b2,b3,b4,b5,b6,b7,b8;
	FloatingActionButton fb1;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_home);
		
		b1=(Button)findViewById(R.id.btchild);
		b2=(Button)findViewById(R.id.btdoctors);
		b3=(Button)findViewById(R.id.btviewtasks);
		b4=(Button)findViewById(R.id.btviewgames);
		b5=(Button)findViewById(R.id.btmessage);
		b6=(Button)findViewById(R.id.btenquiry);
		b7=(Button)findViewById(R.id.btlogout);
		b8=(Button)findViewById(R.id.btparent);
//		fb1=(FloatingActionButton) findViewById(R.id.fb1);
//
//		fb1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				startActivity(new Intent(getApplicationContext(),RecentChat.class));
//			}
//		});

		b8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(),Viewotherparents.class));
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Parentaddchild.class));
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),ViewDoctors.class));
				
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Selectachildfortask.class));
				
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Selectchildforgames.class));
				
			}
		});
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Message.class));
				
			}
		});
		
		b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Enquiry.class));
				
			}
		});
		
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
				
			}
		});


	}



}
