package com.komponentes.oscomanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ListaPedidosDetalle extends Activity implements OnClickListener{
	
	private Button buttonEstado;
	private final String RETURN = "\n";
	private int estado = 0;
	private String orders_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_pedidos_detalle);
		
		// Set up click listeners for all the buttons
		View buttonVolver = findViewById(R.id.buttonVolver);
		buttonVolver.setOnClickListener(this);
		buttonEstado = (Button)findViewById(R.id.buttonEstado);
		buttonEstado.setOnClickListener(this);
		
		TextView numeroPedido = (TextView) findViewById(R.id.numeroPedido);
		TextView detallesPedido = (TextView) findViewById(R.id.detallesPedido);
		TextView detallesCliente = (TextView) findViewById(R.id.detallesCliente);
		TextView detallesEnvio = (TextView) findViewById(R.id.detallesEnvio);
		
		// Received parameters
		String fecha = getIntent().getStringExtra("fecha");
		String datos = getIntent().getStringExtra("datos");
		String detallesPedidoStr = fecha + RETURN;
		String detallesClienteStr = "";
		String detallesEnvioStr = "";

		// Draw the json data in the layout
		JSONObject jsonOrder = null;
		try {
			// ID
			jsonOrder = new JSONObject(datos);
		   	if (jsonOrder.has("orders_id")){
		   		orders_id = jsonOrder.getString("orders_id");
	    		numeroPedido.setText(orders_id);
	    	}
		   	
		   	// STATUS
	    	if (jsonOrder.has("orders_status")){
	    		estado = jsonOrder.getInt("orders_status");
	    		buttonEstado.setText(estadoToString(estado));
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
	    		JSONArray jsonArrayOrders = jsonOrder.getJSONArray("order_products");
	    		JSONObject jsonProductsDetails = null;
	    		int precioBruto = 0;
	    		int precioTotal = 0;
	    		Double taxes = 0.00;
		        for (int j = 0; j < jsonArrayOrders.length(); j++) {
		        	jsonProductsDetails = jsonArrayOrders.getJSONObject(j);
		        	detallesPedidoStr += jsonProductsDetails.getString("products_quantity") + " x " + jsonProductsDetails.getString("products_name") + RETURN;
		        	precioBruto += Double.parseDouble(jsonProductsDetails.getString("final_price"));
		        	precioTotal += Double.parseDouble(jsonProductsDetails.getString("final_price"));
		        	taxes = Double.parseDouble(jsonProductsDetails.getString("products_tax"));
		        }
	    		detallesPedidoStr += "Precio: " + String.valueOf(precioBruto) + RETURN;
	    		detallesPedidoStr += "IVA: " + String.valueOf(taxes) + RETURN;
	    		detallesPedidoStr += "Total: " + String.valueOf(precioTotal) + " " + jsonOrder.getString("currency") + RETURN;
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
            	Intent i = new Intent(getBaseContext(), Estados.class);
            	i.putExtra("estado", estado);
            	i.putExtra("orders_id", orders_id);
                startActivityForResult(i, 1);	
				break;
		}
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if(resultCode == RESULT_OK){      
				int result = data.getIntExtra("result", 0);
				buttonEstado.setText(estadoToString(result));
				estado = result;
			}
			if (resultCode == RESULT_CANCELED) {    
				//Write your code if there's no result
			}
		}
	}

	private String estadoToString(int estado){
		String estadoStr;
		switch (estado){
			case 1:
				estadoStr = getString(R.string.estado_1);
				break;
			case 2:
				estadoStr = getString(R.string.estado_2);
				break;
			case 3:
				estadoStr = getString(R.string.estado_3);
				break;
			case 4:
				estadoStr = getString(R.string.estado_4);
				break;
			default:
				estadoStr = "ERROR";
		}
		return estadoStr;
	}

}
