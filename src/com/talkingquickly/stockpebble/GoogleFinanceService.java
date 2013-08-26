package com.talkingquickly.stockpebble;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GoogleFinanceService {
	private static final String GFINANCE_RESTURL 
		= "http://finance.google.com/finance/info?client=ig&q=INDEXFTSE%3aUKX";
	
	public String getPrice() {
		HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(GFINANCE_RESTURL);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            Log.w("StockPebble", "StockPebble Got an entity");
             
            if(entity != null) {
            	Log.w("StockPebble", "StockPebble Entity if not null");
                InputStream stream = entity.getContent();
                Log.w("StockPebble", "StockPebble Entity gotted some content");
                String responseString = convertStreamToString(stream);
                Log.w("StockPebble", "StockPebble Responding with a string like thing");
                return responseString;
            }
             
        } catch (ClientProtocolException e) {
        	Log.w("StockPebble", "StockPebble HTTP Crash");
            e.printStackTrace();
        } catch (IOException e) {
        	Log.w("StockPebble", "StockPebble HTTP Crash");
            e.printStackTrace();
        }
        Log.w("StockPebble", "StockPebble Returning null :(");
        return null;
	}
	
	
	private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
  
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        	Log.w("StockPebble", "StockPebble HTTP Crash");
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
