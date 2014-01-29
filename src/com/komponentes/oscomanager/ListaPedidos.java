package com.komponentes.oscomanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaPedidos extends Activity implements OnClickListener{

	private SharedPreferences preferences;
	private String URL, USER, PWD;
	/*private String TESTING = "{\"pedidos\":[{\"orders_id\":\"1\",\"customers_id\":\"2\",\"customers_name\":\"miguel reyes\",\"customers_company\":\"\",\"customers_street_address\":" +
			"\"c/ hernan ruiz, 51\",\"customers_suburb\":\"\",\"customers_city\":null,\"customers_postcode\":\"41530\",\"customers_state\":\"Sevilla\",\"customers_country\"" +
			":\"Spain\",\"customers_telephone\":\"652820639\",\"customers_email_address\":\"miguel.reyes@wanadoo.es\",\"customers_address_format_id\":\"3\",\"delivery_name\"" +
			":\"miguel reyes\",\"delivery_company\":\"\",\"delivery_street_address\":\"c/ hernan ruiz, 51\",\"delivery_suburb\":\"\",\"delivery_city\"" +
			":\"mor\u00f3n de la frontera\",\"delivery_postcode\":\"41530\",\"delivery_state\":\"Sevilla\",\"delivery_country\":\"Spain\",\"delivery_address_format_id\":\"3\"" +
			",\"billing_name\":\"miguel reyes\",\"billing_company\":\"\",\"billing_street_address\":\"c/ hernan ruiz, 51\",\"billing_suburb\":\"\",\"billing_city\":null," +
			"\"billing_postcode\":\"41530\",\"billing_state\":\"Sevilla\",\"billing_country\":\"Spain\",\"billing_address_format_id\":\"3\",\"payment_method\":\"PayPal " +
			"Express\",\"cc_type\":\"\",\"cc_owner\":\"\",\"cc_number\":\"\",\"cc_expires\":\"\",\"last_modified\":\"2013-10-02 17:41:16\",\"date_purchased\":\"2013-09-23 " +
			"18:03:45\",\"orders_status\":\"4\",\"orders_date_finished\":null,\"currency\":\"EUR\",\"currency_value\":\"1.000000\",\"order_products\":{\"orders_products_id\":\"1\",\"orders_id\":\"1\",\"products_id\":\"33\",\"products_model\":\"\",\"products_name\":\"Sodimm sdram pc133 512mb\",\"products_price\":\"20.0000\",\"final_price\":\"20.0000\",\"products_tax\":\"0.0000\",\"products_quantity\":\"1\"}}]}";
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_pedidos);
		
		// Preferencias
		preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        URL = preferences.getString("servidor", "");
        USER = preferences.getString("user", "");
        PWD = preferences.getString("password", "");

		
		// Set up click listeners for all the buttons
		View button1 = findViewById(R.id.buttonEstado);
		button1.setOnClickListener(this);
		View button2 = findViewById(R.id.button2);
		button2.setOnClickListener(this);
		
		// Obtener lista de pedidos y pintar lista
		OperacionAccesoRed obtenerListaPedidos = new OperacionAccesoRed();
		obtenerListaPedidos.execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonEstado:
				OperacionAccesoRed obtenerListaPedidos = new OperacionAccesoRed();
				obtenerListaPedidos.execute();
				break;
			case R.id.button2:
				finish();
				break;
		}
	}
	
    
    private class OperacionAccesoRed extends AsyncTask<String, Void, String> {
    	
    	private String listaPedidos;
    	
        @Override
        protected String doInBackground(String... params) {
        	listaPedidos = getPedidos();
			return null;
        }

        @Override
        protected void onPostExecute(String result) {
        	pintaLista(listaPedidos);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    
    
    public String getPedidos(){
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
    
    public void pintaLista(String response){
		// ArrayList and ArrayAdapter
		ListView listView = (ListView) findViewById(R.id.listView);
		
        // Create the grid item mapping
		String[] from = new String[] {"fecha", "total"};
		int[] to = new int[] {R.id.text1, R.id.text2};
		
		//HashMap<String, String> direcciones = new HashMap<String, String>();
		List<String> fechas = new ArrayList<String>();
		List<String> totales = new ArrayList<String>();
		List<HashMap<String, String>> pedidos = new ArrayList<HashMap<String, String>>();
		final List<String> pedidos_array = new ArrayList<String>();
		
		
	    // Check if the connection fails
	    if (response != null){
			// Add messages from JSON response
		    try {
		    	JSONObject jsonMessages = new JSONObject(response);
		    	JSONArray jsonArray = jsonMessages.getJSONArray("pedidos");
		    	for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject jsonObject = jsonArray.getJSONObject(i);
			        fechas.add(jsonObject.getString("date_purchased"));
			        totales.add(jsonObject.getJSONObject("order_products").getString("products_name"));
			        pedidos_array.add(jsonObject.toString());
		    	}
			} catch (JSONException e) {
				e.printStackTrace();
			}	
	    }

		// Add each String,String element to a new HashMap with keys: "fechas", "totales"
		for (int i=0; i<fechas.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fecha", fechas.get(i));
			map.put("total", totales.get(i));
			pedidos.add(map);
		}

		final SimpleAdapter adapter = new SimpleAdapter(this, pedidos, R.layout.simple_list_2lines, from, to);    
		listView.setAdapter(adapter);
		
		// Click listener
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	Intent i = new Intent(getBaseContext(), ListaPedidosDetalle.class);
            	i.putExtra("fecha", ((TextView)arg1.findViewById(R.id.text1)).getText().toString());
            	i.putExtra("datos", pedidos_array.get(arg2));
                startActivity(i);	
			}
        });
		
        // Texto de busqueda
     	final EditText textoBusqueda = (EditText) findViewById(R.id.textoBusqueda);
		// Set up search
		TextWatcher filterTextWatcher = new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s); //Filter from my adapter
		        adapter.notifyDataSetChanged(); //Update my view
			}
		};
		textoBusqueda.addTextChangedListener(filterTextWatcher);
    }
}
