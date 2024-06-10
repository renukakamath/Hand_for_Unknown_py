package com.example.hfu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class puzzle_mark extends AppCompatActivity implements JsonResponse{

    String[] puzzle,mark,details;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_mark);

        l1=(ListView) findViewById(R.id.lv);


        JsonReq JR= new JsonReq();
        JR.json_response=(JsonResponse)puzzle_mark.this;
        String q="presult/?cid="+Selectchildforgames.cid;
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                puzzle=new String[ja.length()];
                mark= new String[ja.length()];


                details= new String[ja.length()];


                for(int i=0;i<ja.length();i++)
                {
                    puzzle[i]=ja.getJSONObject(i).getString("image");
                    mark[i]=ja.getJSONObject(i).getString("mark");


//
                    details[i]="Game : "+puzzle[i]+"\nMark : "+mark[i];
                }
                //driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
//                l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,details));
                view_custom_puzzle_mark a = new view_custom_puzzle_mark(this, puzzle,mark);
                l1.setAdapter(a);
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