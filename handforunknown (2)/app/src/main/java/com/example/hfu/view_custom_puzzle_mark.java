package com.example.hfu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class view_custom_puzzle_mark extends ArrayAdapter<String> {
    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] puzzle;
    private String[] mark;
    private String[] district;
    private String[] state;
    private String[] st;
    private String[] detail;


    public view_custom_puzzle_mark(Activity context, String[] puzzle, String[] mark) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_view_custom_puzzle, puzzle);
        this.context = context;
        this.puzzle = puzzle;
        this.mark = mark;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_view_custom_puzzle, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

        t1.setText("Mark:"+mark[position]);
//        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+IPSetting.ip+"/"+puzzle[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(im);

        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}