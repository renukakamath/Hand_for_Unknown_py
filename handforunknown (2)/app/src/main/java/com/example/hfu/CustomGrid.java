package com.example.hfu;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;


public class CustomGrid extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[]sp22;
    String dd;




    public CustomGrid(android.content.Context applicationContext, String[] c) {

        this.Context=applicationContext;
        this.c=c;



    }

    @Override
    public int getCount() {

        return c.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.custom_grid2, null);



        }
        else
        {
            gridView=(View)convertview;

        }

        ImageView img=(ImageView)gridView.findViewById(R.id.imageView);
//        final LinearLayout lin=(LinearLayout)gridView.findViewById(R.id.linr22);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
        String ip=sh.getString("url","");

        dd=sh.getString("pt","");
        Log.d("=================+++",dd);
        String[] sk=dd.split("\\.");

        Log.d("===========","http://"+IPSetting.ip+"/static/jigsaw_sliced/"+c[position]+"-"+sk[0]+".jpg");
        //Toast.makeText(Context, "http://"+ip+":8000/static/uploads/images/jigsaw/jigsaw_sliced/"+c[position]+"-"+sk[0]+".jpg", Toast.LENGTH_SHORT).show();
        try {
//            URL url = new URL("http://"+IPSetting.ip+"/static/jigsaw_sliced/"+c[position]+"-"+sk[0]+".jpg");
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            img.setImageBitmap(bmp);
            Picasso.with(Context).load("http://"+IPSetting.ip+"/static/jigsaw_sliced/"+c[position]+"-"+sk[0]+".jpg").into(img);

        }catch (Exception e){
            Log.d("==========eeeee",e.toString());
        }
        return gridView;
    }


}




