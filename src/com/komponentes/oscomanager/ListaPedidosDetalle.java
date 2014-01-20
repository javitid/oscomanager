package com.komponentes.oscomanager;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ListaPedidosDetalle extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_pedidos_detalle);
		
		// Set up click listeners for all the buttons
		View buttonVolver = findViewById(R.id.buttonVolver);
		buttonVolver.setOnClickListener(this);
		
		TextView textoMostrar = (TextView) findViewById(R.id.detallesPedidoView);
		
		// Received parameters
		String fecha = getIntent().getStringExtra("fecha");
		String datos = getIntent().getStringExtra("datos");
		String detalles = "";
		
		try {
			JSONObject jsonOrder = new JSONObject(datos);
			Iterator<?> keys = jsonOrder.keys();
			while(keys.hasNext()){
	            String key = (String)keys.next();
	            if (jsonOrder.get(key) instanceof String){
	            	detalles += key.toUpperCase() + ": " + jsonOrder.get(key) + "\n";
	            }
	            else if(jsonOrder.get(key) instanceof JSONObject){
	            	detalles += key.toUpperCase() + ": " + jsonOrder.get(key) + "\n";
	            }
	        }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		textoMostrar.setText(fecha + "\n\n" + detalles);

	
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
