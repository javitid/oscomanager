package com.komponentes.oscomanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ListaPedidos extends Activity implements OnClickListener{

	SharedPreferences preferences;
	String URL, USER, PWD;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_pedidos);
		
		
		// Preferencias
		preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        URL = preferences.getString("servidor", "");
        USER = preferences.getString("user", "");
        PWD = preferences.getString("password", "");
		
		// Set up click listeners for all the buttons
		View button1 = findViewById(R.id.button1);
		button1.setOnClickListener(this);
		View button2 = findViewById(R.id.button2);
		button2.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1:
				OperacionAccesoRed obtenerListaPedidos = new OperacionAccesoRed();
				obtenerListaPedidos.execute();
				break;
			case R.id.button2:
				finish();
				break;
		}
	}
	
    
    private class OperacionAccesoRed extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        	getPedidos();
			return null;
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    
    
    public void getPedidos(){
    	RestClient client = new RestClient(URL);
	    //client.AddParam("param 1 name", "param 1 value");
	    //client.AddHeader("header 1 name", "header 1 value");
	     
	    try {
	        client.Execute(RestClient.RequestMethod.GET);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    String response = client.getResponse();
	    if (response != null){
	    	Log.i("REST service response: ", response);
	    }
    }
}
