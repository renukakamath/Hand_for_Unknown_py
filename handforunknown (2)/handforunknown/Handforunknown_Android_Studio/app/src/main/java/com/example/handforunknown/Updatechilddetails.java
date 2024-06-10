package com.example.handforunknown;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Updatechilddetails extends Activity implements JsonResponse {

    EditText e1,e2,e3;
    Button b1,b2;
    String name,gender,dob;
    String [] names,age,gen;
    public static String logid,type,checkb;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatechilddetails);

        e1=(EditText)findViewById(R.id.name);
        e2=(EditText)findViewById(R.id.gender);
        e3=(EditText)findViewById(R.id.dob);
        b1=(Button)findViewById(R.id.btlogin);

        JsonReq JR= new JsonReq();
        JR.json_response=(JsonResponse) Updatechilddetails.this;
        String q="selectchild/?cid="+Parentaddchild.cid;
        JR.execute(q);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                name=e1.getText().toString();
                gender=e2.getText().toString();
                dob=e3.getText().toString();

                JsonReq JR= new JsonReq();
                JR.json_response=(JsonResponse) Updatechilddetails.this;
                String q="update/?name=" + name + "&gender=" + gender+ "&dob=" + dob+"&cid="+Parentaddchild.cid;
                JR.execute(q);


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
                name = ja.getJSONObject(0).getString("name");
                gender = ja.getJSONObject(0).getString("gender");
                dob = ja.getJSONObject(0).getString("dob");

                e1.setText(name);
                e2.setText(gender);
                e3.setText(dob);

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
        Intent b=new Intent(getApplicationContext(),Parentaddchild.class);
        startActivity(b);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}
