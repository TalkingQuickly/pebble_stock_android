package com.talkingquickly.stockpebble;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

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
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import java.util.UUID;

public class GoogleFinanceASyncTask extends AsyncTask<Void, Void, String>  {
	
	private Context ctx = null;
	private static final UUID WEATHER_UUID = UUID.fromString("42c86ea4-1c3e-4a07-b889-2cccca914198");
	// the tuple key corresponding to the weather icon displayed on the watch
    private static final int UNUSED_KEY = 0;
    // the tuple key corresponding to the temperature displayed on the watch
    private static final int PRICE_KEY = 1;
    
    TimerTask task = new TimerTask()
    {
        public void run()
        {

            //this line causes the app to fail
            sendToPebble();
        }
    };
	
    
    public GoogleFinanceASyncTask(Context ctx) {
        this.ctx = ctx;
    }
	
	@Override
	protected String doInBackground(Void... arg0) {
		new Timer().scheduleAtFixedRate(task, 1000, 5000);
		return new GoogleFinanceService().getPrice();
	}
	
	@Override
    protected void onPostExecute(String result) {
		Log.i("StockPebble", "StockPebble onPostExecute  Invoked");
//        this.populateActivity(result);
    }
	
	private void sendToPebble() {
		String thePrice = new GoogleFinanceService().getPrice();
		Log.i("StockPebble", "sendToPebble invoked");
		try {
			JSONObject jsonResponse = new JSONObject(thePrice);
			String price = jsonResponse.getString("l");
			Log.i("StockPebble", price);
			PebbleDictionary data = new PebbleDictionary();
	        data.addString(PRICE_KEY,price);
	        data.addInt16(UNUSED_KEY, (short) 5);

	        // Send the assembled dictionary to the weather watch-app; this is a no-op if the app isn't running or is not
	        // installed
	        Activity parent = (Activity) ctx;
	        PebbleKit.sendDataToPebble(parent.getApplicationContext(), WEATHER_UUID, data);
		} catch (JSONException e) {
			Log.i("StockPebble", "JSON parsing failed");
			e.printStackTrace();
		}
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
			PebbleDictionary data = new PebbleDictionary();
	        data.addString(PRICE_KEY,price);
	        data.addInt16(UNUSED_KEY, (short) 5);

	        // Send the assembled dictionary to the weather watch-app; this is a no-op if the app isn't running or is not
	        // installed
	        PebbleKit.sendDataToPebble(parent.getApplicationContext(), WEATHER_UUID, data);
		} catch (JSONException e) {
			Log.i("StockPebble", "JSON parsing failed");
			e.printStackTrace();
		}
	}
}