package com.talkingquickly.stockpebble;

import com.talkingquickly.stockpebble.GoogleFinanceService;
import com.talkingquickly.stockpebble.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleFinanceASyncTask extends AsyncTask<Void, Void, String>  {
	
	private Context ctx = null;
    
    public GoogleFinanceASyncTask(Context ctx) {
        this.ctx = ctx;
    }
	
	@Override
	protected String doInBackground(Void... arg0) {
		return new GoogleFinanceService().getPrice();
	}
	
	@Override
    protected void onPostExecute(String result) {
		Log.i("StockPebble", "StockPebble onPostExecute  Invoked");
        this.populateActivity(result);
    }

	private void populateActivity(String result) {
		Log.w("StockPebble", "StockPebble" + result);
		try {
			JSONObject jsonResponse = new JSONObject(result);
			String price = jsonResponse.getString("l");
			Log.i("StockPebble", price);
			Activity parent = (Activity) ctx;
			TextView view = (TextView) parent.findViewById(R.id.ftse_price);
			view.setText(price);
		} catch (JSONException e) {
			Log.i("StockPebble", "JSON parsing failed");
			e.printStackTrace();
		}
	}
}