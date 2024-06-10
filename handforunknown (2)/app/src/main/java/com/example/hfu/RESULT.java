package com.example.hfu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RESULT extends AppCompatActivity implements View.OnClickListener,JsonResponse{
    ImageView im;
    Button b;
    //TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        im = (ImageView) findViewById(R.id.imageView10);
        b = (Button) findViewById(R.id.button42);
        b.setOnClickListener(this);
        // tv = (TextView) findViewById(R.id.textView19);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String ip = IPSetting.ip;
//        final String url = "http://" + ip + "/UpdateProgress";
//
//
//        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String s) {
//
//
//                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//
//                try {
//                    JSONObject json = new JSONObject(s);
//                    String res = json.getString("status");
//
//                    if (res.equals("ok") == true) {
//
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//                Toast.makeText(getApplicationContext(), "Error........", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<String, String>();
//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                String type = sh.getString("Utype", "");
//                String time = sh.getString("time", "");
//                String cid = sh.getString("lid", "");
//                String pid = Selectchildforgames.cid;
//                params.put("type", type);
//                params.put("time", time);
//                params.put("cid", cid);
//                params.put("pid", pid);
//                return (params);
//
//            }
//        };
//        requestqueue.add(postrequest);
//    }

        JsonReq JR= new JsonReq();
        JR.json_response=(JsonResponse)RESULT.this;
        String q="UpdateProgress?pid=" + sh.getString("pid","") + "&cid=" + Selectchildforgames.cid+"&time="+sh.getString("time","");
        JR.execute(q);


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), ParentHome.class);
        startActivity(i);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("ok")) {
                ;

                //startService(new Intent(getApplicationContext(), LocationService.class));


            } else {
                Toast.makeText(getApplicationContext(), "Error........", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}