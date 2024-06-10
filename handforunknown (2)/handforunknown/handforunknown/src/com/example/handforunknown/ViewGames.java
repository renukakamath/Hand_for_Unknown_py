package com.example.handforunknown;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewGames extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_games);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_games, menu);
		return true;
	}

}
