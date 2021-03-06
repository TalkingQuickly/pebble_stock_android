package com.talkingquickly.stockpebble;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import com.talkingquickly.stockpebble.GoogleFinanceASyncTask;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void onGetPriceClick(View view) {
		
		Log.w("StockPebble", "StockPebble The button has been clicked");
		GoogleFinanceASyncTask task = new GoogleFinanceASyncTask(this);
        Intent i = this.getIntent();
        task.execute();
	}

}
