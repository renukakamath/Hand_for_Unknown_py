package com.example.hfu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class RecentChat extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {

    ListView l1;
    String search;
    ImageButton c1;
    String[] plot, place, district, state, image,lati,longi,log_id ,value,st,plot_id,pas_id;
    SharedPreferences sh;
    public static String plotid,sts,pasid,latitude,longitude ,logid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lv2);






        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) RecentChat.this;
        String q = "/recentchat?logid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


//        st=stock[i];

        logid=log_id[i];
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",log_id[i]);

        e.commit();

        final CharSequence[] items = {"Chat","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RecentChat.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Chat")) {
                    startActivity(new Intent(getApplicationContext(),ChatHere.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                place = new String[ja1.length()];
                log_id = new String[ja1.length()];

                value = new String[ja1.length()];
//                stock = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    place[i] = ja1.getJSONObject(i).getString("fname")+ ja1.getJSONObject(i).getString("lname");
                    log_id[i] = ja1.getJSONObject(i).getString("login_id");

//                    stock[i] = ja1.getJSONObject(i).getString("availability");

                    value[i] = "Name: " + place[i] + "\n";

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
//                Custimageviewproduct a = new Custimageviewproduct(this, place, district, state, image,st);
//                l1.setAdapter(a);
            }
            if (status.equalsIgnoreCase("failed")) {
                Toast.makeText(getApplicationContext(),"Request Already sented", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), ParentHome.class);
        startActivity(b);


    }
}