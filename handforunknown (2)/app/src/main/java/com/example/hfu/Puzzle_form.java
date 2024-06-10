package com.example.hfu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Puzzle_form extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener,JsonResponse {
    GridView gv;
    Handler h;
    String aa[];
    CustomGrid customGrid;
    Button b1;
    String cmr_rst="1,5,9,13,2,6,10,14,3,7,11,15,4,8,12,16,";
    String res_chk="";
    TextView t;

    int scount=0,mcount=0;




    Runnable timer = new Runnable() {
        @Override
        public void run() {




            if(scount < 60){
                scount=scount+1;

            }
            else{
                scount = 00;
                mcount = mcount+1;
            }

            t.setText(mcount+" : "+scount);


            h.postDelayed(timer,1000);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_form);

        t =(TextView)findViewById(R.id.textView5);
        h=new Handler();

        h.post(timer);


        b1 =(Button)findViewById(R.id.button3);
        b1.setOnClickListener(this);

        //Toast.makeText(getApplicationContext(),"name999",Toast.LENGTH_LONG).show();
        gv=(GridView)findViewById(R.id.gd1);
        gv.setOnItemClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR= new JsonReq();
        JR.json_response=(JsonResponse)Puzzle_form.this;
        String q="/edt_fed_viw?pid="+sh.getString("pid","");
        JR.execute(q);
        //Toast.makeText(getApplicationContext(),"url="+url,Toast.LENGTH_LONG).show();

//        try {
//
//            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }catch (Exception e){}
//
//        gv.setOnItemLongClickListener(this);
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        //    Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void response(JSONObject jo){

//                        try {
//                            String status=jo.getString("status");
//                            if(status.equalsIgnoreCase("ok"))
//                            {
//
//
//                                String jsa=jsonObj.getString("fname");
//                                aa=jsa.split(",");
//                                gv.setAdapter(new CustomGrid(getApplicationContext(),aa));
//                            }
//                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(),"error!"+e.toString(),Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(),"error!"+error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                Map<String, String>  params = new HashMap<String, String>();
//
//
//                 params.put("pid", sh.getString("pid",""));
//
//
//                return params;
//            }
//        };
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        requestQueue.add(postRequest);
//

//
//                }




    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Toast.makeText(this, "clicked @ "+i, Toast.LENGTH_SHORT).show();
        return false;
    }
    int cnt=0;
    int ps1=0,ps2=0;
    String va1="",va2="";


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LinearLayout ll=(LinearLayout)view;

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(1, Color.BLACK);
        drawable.setCornerRadius(2);
        drawable.setColor(Color.BLUE);
        ll.setBackgroundDrawable(drawable);


        String ve=aa[i];
        String temp="";


        cnt=cnt+1;


        // Toast.makeText(getApplicationContext(),cmr_rst+"-"+res_chk,Toast.LENGTH_LONG).show();


        if("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,".equalsIgnoreCase(res_chk))
        {
            // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(cnt==1) {
                va1 = ve;
                ps1 = i;
            }
            if(cnt==2) {
                va2 = ve;
                ps2 = i;

                temp = va1;
                va1 = va2;
                va2 = temp;

                aa[ps2] = va2;
                aa[ps1] = va1;
                res_chk="";

                for (int im = 0; im < aa.length; im++) {
                    res_chk += aa[im] + ",";
                }
                String jsa = res_chk;
                aa = jsa.split(",");
                gv.setAdapter(new CustomGrid(getApplicationContext(), aa));

                cnt = 0;
                va1 = "";
                va2 = "";
                ps1 = 0;
                ps2 = 0;
                temp = "";
            }
        }




    }

    @Override
    public void onClick(View v) {




        if("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,".equalsIgnoreCase(res_chk))
        {
            String type ="jigsaw";
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("Utype",type);
            String time = t.getText().toString();
            ed.putString("time",time);
            ed.commit();
            Toast.makeText(getApplicationContext(),"SUCCESS",Toast.LENGTH_SHORT).show();
            Intent i = new Intent( getApplicationContext(),RESULT.class);
            startActivity(i);

        }
        else{
            Toast.makeText(getApplicationContext(),"THE PUZZLE IS INCOMPLETE",Toast.LENGTH_SHORT).show();
            Intent i = new Intent( getApplicationContext(),ParentHome.class);
            startActivity(i);

        }
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("ok"))
            {


                String jsa= jo.getString("data");
                aa=jsa.split(",");
                gv.setAdapter(new CustomGrid(getApplicationContext(),aa));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"happy BIRTHDAY!"+e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}

