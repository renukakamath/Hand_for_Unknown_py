package com.example.hfu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class view_puzzle extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {
    String[] puzzle_id,path, fname;

    ListView li;
    SharedPreferences sh;
    String url,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_puzzle);

        li=findViewById(R.id.list);
        li.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        ip = sh.getString("url", "");
//        url = "192.168.29.167"+ "/and_view_puzzle";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            JsonReq JR= new JsonReq();
            JR.json_response=(JsonResponse)view_puzzle.this;
            String q="/and_view_puzzle";
            JR.execute(q);
//
//        new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//
//            //                value Passing android to python
//            @Override
//            protected Map<String, String> getParams() {
//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", sh.getString("lid",""));//passing to python
//                return params;
//            }
//        };
//
//
//        int MY_SOCKET_TIMEOUT_MS = 100000;
//
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(postRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("pid", puzzle_id[i]);
        ed.putString("pt", fname[i]);
        ed.commit();
        Intent ij=new Intent(getApplicationContext(), Puzzle_form.class);
        startActivity(ij);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("ok"))
            {

//                JSONArray js = jsonObj.getJSONArray("data");//from python
//                puzzle_id = new String[js.length()];
//                path = new String[js.length()];
//                fname = new String[js.length()];
//                                rdate = new String[js.length()];
            JSONArray ja=(JSONArray)jo.getJSONArray("data");
            puzzle_id=new String[ja.length()];
            path= new String[ja.length()];
            fname= new String[ja.length()];


                for(int i=0;i<ja.length();i++)
                {
                    puzzle_id[i]=ja.getJSONObject(i).getString("puzzle_id");
                    path[i]=ja.getJSONObject(i).getString("image");
                    fname[i]=ja.getJSONObject(i).getString("fname");
//

                }
//                li.setAdapter(new view_custom_puzzle(getApplicationContext(),puzzle_id,path));//custom_view_service.xml and li is the listview object
                view_custom_puzzle a = new view_custom_puzzle(this, puzzle_id,path);
                li.setAdapter(a);

            } else {
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}