package com.example.hfu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Checksymptoms extends AppCompatActivity implements JsonResponse {
RadioButton r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12;

String rb1,rb2,rb3,rb4,rb5,rb6;
public static String outss;

Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksymptoms);

        b1=(Button) findViewById(R.id.btsubs);
        r1=(RadioButton) findViewById(R.id.r1);
        r2=(RadioButton) findViewById(R.id.r2);
        r3=(RadioButton) findViewById(R.id.r3);
        r4=(RadioButton) findViewById(R.id.r4);
        r5=(RadioButton) findViewById(R.id.r5);
        r6=(RadioButton) findViewById(R.id.r6);
        r7=(RadioButton) findViewById(R.id.r7);
        r8=(RadioButton) findViewById(R.id.r8);
        r9=(RadioButton) findViewById(R.id.r9);
        r10=(RadioButton) findViewById(R.id.r10);
        r11=(RadioButton) findViewById(R.id.r11);
        r12=(RadioButton) findViewById(R.id.r12);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1.isChecked())
                {
                    rb1="1";
                }
                else if(r2.isChecked())
                {
                    rb1="0";
                }
                if(r3.isChecked())
                {
                    rb2="1";
                }else if(r4.isChecked())
                {
                    rb2="0";
                }
                if(r5.isChecked())
                {
                    rb3="1";
                }
                else if(r6.isChecked())
                {
                    rb3="0";
                }
                if(r7.isChecked())
                {
                    rb4="1";
                }else if(r8.isChecked())
                {
                    rb4="0";
                }
                if(r9.isChecked())
                {
                    rb5="1";
                }else if(r10.isChecked())
                {
                    rb5="0";
                }
                if(r11.isChecked())
                {
                    rb6="1";
                }else if(r12.isChecked())
                {
                    rb6="0";
                }

                JsonReq jr= new JsonReq();
                jr.json_response=(JsonResponse) Checksymptoms.this;
                String q="checksymptoms/?r1="+rb1+"&r2="+rb2+"&r3="+rb3+"&r4="+rb4+"&r5="+rb5+"&r6="+rb6;
                q.replace("", "%20");
                jr.execute(q);

            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
            String out = jo.getString("out");

                startActivity(new Intent(getApplicationContext(),ParentHome.class));
                Toast.makeText(getApplicationContext(), "Predicted Output is "+out, Toast.LENGTH_LONG).show();
                outss=out;
                startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Symptoms Submitted failed..!!", Toast.LENGTH_LONG).show();
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
        Intent b=new Intent(getApplicationContext(),ParentHome.class);
        startActivity(b);
    }




}
