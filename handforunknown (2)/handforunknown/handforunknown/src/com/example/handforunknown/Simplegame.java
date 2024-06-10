package com.example.handforunknown;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Simplegame extends Activity implements JsonResponse {

	ImageView iv;
	Button b1;
	//EditText e1;
	TextView t1;
	 String method="";
	 String namespace="http://tempuri.org/";
	 String soapaction;
	 String url;
	 SharedPreferences sh;
	 String[] id,imagename,path,options;
	 Integer kid,v=0;
	 Integer vals=0,j=1;
	 RadioButton r1,r2,r3,r4;
	 String newval;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplegame);
		v=0;
		vals=0;
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url=sh.getString("url", "");
		
		//e1=(EditText)findViewById(R.id.etval);
		
		b1=(Button)findViewById(R.id.btsub);
		iv=(ImageView)findViewById(R.id.ivimage);
		t1=(TextView)findViewById(R.id.tvval);
		
		r1=(RadioButton)findViewById(R.id.rdone);
		r2=(RadioButton)findViewById(R.id.rdtwo);
		r3=(RadioButton)findViewById(R.id.rdthree);
		r4=(RadioButton)findViewById(R.id.rdfour);
		
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Simplegame.this;
		String q="selectimages/?gid="+Selectgame.gameids;
		jr.execute(q);
		
		
		
//		try
//		{
//			method="selectimages";
//			soapaction=namespace+method;
//			SoapObject sop=new SoapObject(namespace,method);
//			sop.addProperty("type",Gamehome.val);
//			Toast.makeText(getApplicationContext(),Gamehome.val,Toast.LENGTH_LONG).show();
//			SoapSerializationEnvelope sev=new SoapSerializationEnvelope(SoapEnvelope.VER11);
//			sev.setOutputSoapObject(sop);
//			sev.dotNet=true;
//			HttpTransportSE htp=new HttpTransportSE(url);
//			htp.call(soapaction,sev);
//			String rev=sev.getResponse().toString();
//			//Toast.makeText(getApplicationContext(), rev,Toast.LENGTH_LONG).show();
//			String k[]=rev.split("\\#");
//			id=new String[k.length];
//			imagename=new String[k.length];
//			path=new String[k.length];
//			options=new String[k.length];
//			kid=k.length;
//			for(int i=0;i<k.length;i++)
//			{
//				String r[]=k[i].split("\\$");
//				id[i]=r[0];
//				imagename[i]=r[1];
//				path[i]=r[2];
//				options[i]=r[3];
//			}
//			if(path.equals(0)){
//				startActivity(new Intent(getApplicationContext(),Gamehome.class));
//				
//			}
//			else
//			{
//			 String pth = "http://"+IPSettings.ip+path[0];
//			 
//		       pth = pth.replace("~", "");
//		       //Toast.makeText(getApplicationContext(), pth,Toast.LENGTH_LONG).show();
//		        
//		        Log.d("-------------", pth);
//		        Picasso.with(getApplicationContext())
//		                .load(pth)
//		                .placeholder(R.drawable.ic_launcher)
//		                .error(R.drawable.ic_launcher).into(iv);
//
//		        t1.setText(imagename[0]);
//		        String ks[]=options[0].split("\\,");
//		        r1.setText(ks[0]);
//		        r2.setText(ks[1]);
//		        r3.setText(ks[2]);
//		        r4.setText(ks[3]);
//			}
//		}
//		catch(Exception e)
//		{
//			Toast.makeText(getApplicationContext(), "Exception"+e,Toast.LENGTH_LONG).show();
//		}
		
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(r1.isChecked())
				{
					newval=r1.getText().toString();
					r1.setChecked(false);
				}
				else if(r2.isChecked())
				{
					newval=r2.getText().toString();
					r2.setChecked(false);
				}
				else if(r3.isChecked())
				{
					newval=r3.getText().toString();
					r3.setChecked(false);
				}
				else if(r4.isChecked())
				{
					newval=r4.getText().toString();
					r4.setChecked(false);
				}
				
				if(newval.equalsIgnoreCase(t1.getText().toString())){
				
					
					Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
					vals=vals+1;
					
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Not Correct", Toast.LENGTH_LONG).show();
				}
				
				 //Toast.makeText(getApplicationContext(), "Kid "+kid+"\nv "+v, Toast.LENGTH_LONG).show();
			        if(j==kid)
					{
			        	
			        	
			        	
			        	JsonReq jr= new JsonReq();
			    		jr.json_response=(JsonResponse)Simplegame.this;
			    		String q="insertscore/?assigndid="+Selectgame.assignids+"&questions="+kid+"&marks="+vals;
			    		jr.execute(q);
			    		
			    		
			        	
//			        	try 
//						{method="insertscore";
//						soapaction=namespace+method;
//							SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//							url = sh.getString("url", "");
//							
//							SoapObject sop=new  SoapObject(namespace, method);
//							sop.addProperty("cid",Gamehome.childid);
//							sop.addProperty("questions",kid);
//							sop.addProperty("marks",vals);
//							sop.addProperty("type",Gamehome.val);
//						
//							SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
//							snv.setOutputSoapObject(sop);
//							snv.dotNet=true;
//							HttpTransportSE hp=new HttpTransportSE(url);
//							hp.call(soapaction, snv);
//							String result=snv.getResponse().toString();
//							Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
//							if(result.equalsIgnoreCase("ok"))
//							{
//								Toast.makeText(getApplicationContext(),"Added Successfully...",Toast.LENGTH_LONG).show();
//								Intent b=new Intent(getApplicationContext(),Homepage.class);			
//								startActivity(b);
//							}
//							else
//							{
//								Toast.makeText(getApplicationContext(),"Unsuccessfull...",Toast.LENGTH_LONG).show();
//							}
//						} catch (Exception e) 
//						{
//							Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//						}
			        	j=1;
//			        	Toast.makeText(getApplicationContext(), "Marks Obtained "+ vals, Toast.LENGTH_LONG).show();
//						startActivity(new Intent(getApplicationContext(),Gamehome.class));
					
					}
			        
			        if(path.equals(1)){
						startActivity(new Intent(getApplicationContext(),ViewGames.class));
						
					}
					else
					{
						if(j<kid)
						{
							String pth = "http://"+sh.getString("ip", "")+"/"+path[j];
						       pth = pth.replace("~", "");
//						       Toast.makeText(getApplicationContext(), pth,Toast.LENGTH_LONG).show();
						        Log.d("-------------", pth);
						        Picasso.with(getApplicationContext())
						                .load(pth)
						                .placeholder(R.drawable.ic_launcher)
						                .error(R.drawable.ic_launcher).into(iv);

						        t1.setText(imagename[j]);
						        String ks[]=options[j].split("\\,");
						        r1.setText(ks[0]);
						        r2.setText(ks[1]);
						        r3.setText(ks[2]);
						        r4.setText(ks[3]);
						        j++;
						        Toast.makeText(getApplicationContext(), j+"",Toast.LENGTH_LONG).show();
						        v=j;
						        
						       
						}
					}
					
				}
				
				
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simplegame, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
	
		
		try
		{
			String method=jo.getString("method");
			Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();
			if(method.equalsIgnoreCase("insertscore"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Added Score", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),Selectgame.class));
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
			else if(method.equalsIgnoreCase("selectimages"))
			{

				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						id=new String[ja.length()];
						imagename=new String[ja.length()];
						path= new String[ja.length()];
						options= new String[ja.length()];
						kid=ja.length();
						
						
						for(int i=0;i<ja.length();i++)
						{
							id[i]=ja.getJSONObject(i).getString("id");
							imagename[i]=ja.getJSONObject(i).getString("image_name");
							path[i]=ja.getJSONObject(i).getString("imagepath");
							options[i]=ja.getJSONObject(i).getString("options");
							
						}
						
						if(path.equals(0)){
							startActivity(new Intent(getApplicationContext(),Selectgame.class));
							
						}
						else
						{
						 String pth = "http://"+IPSetting.ip+"/"+path[0];
						 
					       pth = pth.replace("~", "");
//					       Toast.makeText(getApplicationContext(), pth,Toast.LENGTH_LONG).show();
					        
					        Log.d("-------------", pth);
					        Picasso.with(getApplicationContext())
					                .load(pth)
					                .placeholder(R.drawable.ic_launcher)
					                .error(R.drawable.ic_launcher).into(iv);
			
					        t1.setText(imagename[0]);
					        String ks[]=options[0].split("\\,");
					        r1.setText(ks[0]);
					        r2.setText(ks[1]);
					        r3.setText(ks[2]);
					        r4.setText(ks[3]);
						}
						
						
						
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
