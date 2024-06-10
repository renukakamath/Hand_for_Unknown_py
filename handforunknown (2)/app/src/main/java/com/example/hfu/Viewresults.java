package com.example.hfu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewresults extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] game,mark,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewresults);

        l1=(ListView) findViewById(R.id.lvresult);

        JsonReq JR= new JsonReq();
        JR.json_response=(JsonResponse)Viewresults.this;
        String q="result/?aid="+Selectgame.assignids+"&cid="+Selectchildforgames.cid;
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                game=new String[ja.length()];
                mark= new String[ja.length()];


                details= new String[ja.length()];


                for(int i=0;i<ja.length();i++)
                {
                    game[i]=ja.getJSONObject(i).getString("description");
                    mark[i]=ja.getJSONObject(i).getString("mark");


//
                    details[i]="Game : "+game[i]+"\nMark : "+mark[i];
                }
                //driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
                l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,details));
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