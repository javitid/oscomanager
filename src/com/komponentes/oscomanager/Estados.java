package com.komponentes.oscomanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Estados extends Activity implements OnCheckedChangeListener{
	
	private SharedPreferences preferences;
	private String URL, USER, PWD;
	private String orders_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estados);
		
		// Preferencias
		preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        URL = preferences.getString("servidor", "") + "?action=change_order_status";
        USER = preferences.getString("user", "");
        PWD = preferences.getString("password", "");
        
		// Received parameters
		int estado = getIntent().getIntExtra("estado", 0);
		orders_id = getIntent().getStringExtra("orders_id");
		int buttonId = 0;
		switch (estado){
			case 1:
				buttonId = R.id.radioButton1;
				break;
			case 2:
				buttonId = R.id.radioButton2;
				break;
			case 3:
				buttonId = R.id.radioButton3;
				break;
			case 4:
				buttonId = R.id.radioButton4;
				break;
		}
	
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup.check(buttonId);
		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int buttonSelected = 0;
		switch (checkedId){
			case R.id.radioButton1:
				buttonSelected = 1;
				break;
			case R.id.radioButton2:
				buttonSelected = 2;
				break;
			case R.id.radioButton3:
				buttonSelected = 3;
				break;
			case R.id.radioButton4:
				buttonSelected = 4;
				break;
		}

		URL += "&orders_id=" + orders_id + "&orders_status=" + String.valueOf(buttonSelected);
		//send REST request to change status
		OperacionAccesoRed cambiarEstadoPedido = new OperacionAccesoRed();
		cambiarEstadoPedido.execute();
		// Devolver el estado actual del pedido para repintar la pantalla con el nuevo valor
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", buttonSelected);
		setResult(RESULT_OK, returnIntent);     
		finish();
	}
	
	
    private class OperacionAccesoRed extends AsyncTask<String, Void, String> {
    	
        @Override
        protected String doInBackground(String... params) {
        	cambiarEstado();
			return null;
        }

        @Override
        protected void onPostExecute(String result) {
        	
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    
    
    public String cambiarEstado(){
    	RestClient client = new RestClient(URL);
	    //client.AddParam("param 1 name", "param 1 value");
	    //client.AddHeader("header 1 name", "header 1 value");
    	String authentication = USER + ":" + PWD;
    	String encoding = Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
    	client.AddHeader("Authorization", "Basic " + encoding);
	    
	    try {
	        client.Execute(RestClient.RequestMethod.GET);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    String response = client.getResponse();
	    if (response != null){
	    	Log.i("REST service response: ", response);
	    }
	    return response;
    }
}
