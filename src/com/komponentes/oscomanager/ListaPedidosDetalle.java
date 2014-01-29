package com.komponentes.oscomanager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ListaPedidosDetalle extends Activity implements OnClickListener{
	
	final String RETURN = "\n";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_pedidos_detalle);
		
		// Set up click listeners for all the buttons
		View buttonVolver = findViewById(R.id.buttonVolver);
		buttonVolver.setOnClickListener(this);
		Button buttonEstado = (Button)findViewById(R.id.buttonEstado);
		buttonEstado.setOnClickListener(this);
		
		TextView numeroPedido = (TextView) findViewById(R.id.numeroPedido);
		TextView detallesPedido = (TextView) findViewById(R.id.detallesPedido);
		TextView detallesCliente = (TextView) findViewById(R.id.detallesCliente);
		TextView detallesEnvio = (TextView) findViewById(R.id.detallesEnvio);
		
		// Received parameters
		String fecha = getIntent().getStringExtra("fecha");
		String datos = getIntent().getStringExtra("datos");
		String estadoStr = "";
		int estadoColor = 0;
		String detallesPedidoStr = fecha + RETURN;
		String detallesClienteStr = "";
		String detallesEnvioStr = "";

		// Draw the json data in the layout
		JSONObject jsonOrder = null;
		try {
			// ID
			jsonOrder = new JSONObject(datos);
		   	if (jsonOrder.has("orders_id")){
	    		numeroPedido.setText(jsonOrder.getString("orders_id"));
	    	}
		   	
		   	// STATUS
	    	if (jsonOrder.has("orders_status")){
	    		switch (jsonOrder.getInt("orders_status")){
	    			case 1:
	    				estadoStr = "Pendiente";
	    				estadoColor = 0xFF0080FF;
	    				break;
	    			case 2:
	    				estadoStr = "Procesando";
	    				estadoColor = 0xFFFE9A2E;
	    				break;
	    			case 3:
	    				estadoStr = "Enviado";
	    				estadoColor = 0xFFFF0000;
	    				break;
	    			case 4:
	    				estadoStr = "Paypal";
	    				estadoColor = 0xFF3ADF00;
	    				break;
	    		}
	    		buttonEstado.setText(estadoStr);
	    		buttonEstado.setBackgroundColor(estadoColor);
	    	}
	    	
	    	// DATA OF THE CLIENT
	    	if (jsonOrder.has("customers_name")){
	    		detallesClienteStr = jsonOrder.getString("customers_name") + RETURN;
	    	}
	    	if (jsonOrder.has("customers_street_address")){
	    		detallesClienteStr += jsonOrder.getString("customers_street_address") + RETURN;
	    	}
	    	if (jsonOrder.has("customers_postcode")){
	    		detallesClienteStr += jsonOrder.getString("delivery_postcode") + " - ";
	    	}
	    	if (jsonOrder.has("delivery_city")){
	    		detallesClienteStr += jsonOrder.getString("delivery_city") + RETURN;
	    	}
	    	if (jsonOrder.has("customers_state")){
	    		detallesClienteStr += jsonOrder.getString("delivery_state") + RETURN;
	    	}
	    	if (jsonOrder.has("customers_email_address")){
	    		detallesClienteStr += jsonOrder.getString("customers_email_address") + RETURN;
	    	}
	    	if (jsonOrder.has("customers_telephone")){
	    		detallesClienteStr += jsonOrder.getString("customers_telephone") + RETURN;
	    	}
	    	
	    	// DATA OF THE ORDER
	    	if (jsonOrder.has("delivery_name")){
	    		detallesEnvioStr = jsonOrder.getString("customers_name") + RETURN;
	    	}
	    	if (jsonOrder.has("delivery_street_address")){
	    		detallesEnvioStr += jsonOrder.getString("customers_street_address") + RETURN;
	    	}
	    	if (jsonOrder.has("delivery_postcode")){
	    		detallesEnvioStr += jsonOrder.getString("delivery_postcode") + " - ";
	    	}
	    	if (jsonOrder.has("delivery_city")){
	    		detallesEnvioStr += jsonOrder.getString("delivery_city") + RETURN;
	    	}
	    	if (jsonOrder.has("delivery_state")){
	    		detallesEnvioStr += jsonOrder.getString("delivery_state") + RETURN;
	    	}
	    	
	    	// DATA OF THE ORDER
	    	if (jsonOrder.has("order_products")){
	    		JSONObject jsonProductsDetails = new JSONObject(jsonOrder.getString("order_products"));
	    		detallesPedidoStr += jsonProductsDetails.getString("products_name") + RETURN;
	    		detallesPedidoStr += "Precio: " + jsonProductsDetails.getString("products_price") + RETURN;
	    		detallesPedidoStr += "IVA: " + jsonProductsDetails.getString("products_tax") + RETURN;
	    		detallesPedidoStr += "Total: " + jsonProductsDetails.getString("final_price") + " " + jsonOrder.getString("currency") + RETURN;
	    	}
		   	
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		detallesPedido.setText(detallesPedidoStr);
		detallesCliente.setText(detallesClienteStr);
		detallesEnvio.setText(detallesEnvioStr);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonVolver:
				finish();
				break;
			case R.id.buttonEstado:
				break;
		}
		
	}

}
