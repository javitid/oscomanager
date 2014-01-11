package com.komponentes.oscomanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ListaPedidos extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_pedidos);
		
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
				getPedidos();
				break;
			case R.id.button2:
				finish();
				break;
		}
	}
	
	
    public void getPedidos(){
    	// TODO: petición REST
    }
}
