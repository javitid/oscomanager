package com.komponentes.oscomanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

public class ListaArticulosDetalle extends Activity implements OnClickListener{
	
	private final String RETURN = "\n";
	private String stock = "No disponible";
	private String products_id;
	private String URL;
	private String image;
	private String product_url;
	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_articulos_detalle);
		
		// Preferencias
		preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        URL = preferences.getString("servidor", "");
		
		// Set up click listeners for all the buttons
		View buttonVolver = findViewById(R.id.buttonVolver);
		buttonVolver.setOnClickListener(this);
		
		TextView numeroArticulo = (TextView) findViewById(R.id.numeroArticulo);
		TextView stockArticulo = (TextView) findViewById(R.id.stock);
		TextView detallesArticuloTxt = (TextView) findViewById(R.id.detallesArticulo);
		WebView detallesArticuloWebView = (WebView) findViewById(R.id.webView);
		
		// Received parameters
		String datos = getIntent().getStringExtra("datos");
		String detallesArticuloStr = "";
		String html = "";

		// Draw the json data in the layout
		JSONObject jsonOrder = null;
		JSONArray jsonArrayProducts = null;
		JSONObject jsonOrderSpanishDescription = null;
		try {
			// ID
			jsonOrder = new JSONObject(datos);
		   	if (jsonOrder.has("products_id")){
		   		products_id = jsonOrder.getString("products_id");
	    		numeroArticulo.setText(products_id);
	    	}
		   	
		   	// IMAGE
		   	if (jsonOrder.has("products_image")){
		   		image = Uri.parse(URL + "/images/" + jsonOrder.getString("products_image")).toString();
	    	}
		   	
		   	// STOCK
	    	if (jsonOrder.has("products_quantity")){
	    		stock = jsonOrder.getString("products_quantity");
	    		stockArticulo.setText(stock);
	    	}
	    	
	    	// URL
	    	if (jsonOrder.has("product_url")){
	    		product_url = jsonOrder.getString("product_url");
	    	}
	    	
	    	// DATA OF THE ARTICLE
	    	if (jsonOrder.has("products_description")){
	    		jsonArrayProducts = jsonOrder.getJSONArray("products_description");
		        jsonOrderSpanishDescription = jsonArrayProducts.getJSONObject(1);
		        
		    	if (jsonOrderSpanishDescription.has("products_name")){
		    		detallesArticuloStr += jsonOrderSpanishDescription.getString("products_name");
		    	}
		    	if (jsonOrderSpanishDescription.has("products_description")){
		    		html += "<a href=\"" + product_url + "\" style=\"float: right\">Ver en web</a>";
		    		html += "<center><img src=\"" + image + "\"></center>" + RETURN;
		    		html += jsonOrderSpanishDescription.getString("products_description") + RETURN;
		    	}
	    	}
		   	
		}catch (JSONException e) {
			e.printStackTrace();
		}
		detallesArticuloTxt.setText(detallesArticuloStr);
		detallesArticuloWebView.loadDataWithBaseURL("", html, "text/html","utf-8", "");
		detallesArticuloWebView.setInitialScale(150);
		detallesArticuloWebView.getSettings().setSupportZoom(true);
		detallesArticuloWebView.getSettings().setBuiltInZoomControls(true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonVolver:
				finish();
				break;
		}
		
	}

}
