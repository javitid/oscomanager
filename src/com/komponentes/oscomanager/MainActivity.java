package com.komponentes.oscomanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up click listeners for all the buttons
		View button1 = findViewById(R.id.button1);
		button1.setOnClickListener(this);
		View button2 = findViewById(R.id.button2);
		button2.setOnClickListener(this);
		View button3 = findViewById(R.id.button3);
		button3.setOnClickListener(this);
		View button4 = findViewById(R.id.button4);
		button4.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1:
				listaPedidos();
				break;
			case R.id.button2:
				break;
			case R.id.button3:
				break;
			case R.id.button4:
				finish();
				break;
		}
	}
	
    public void listaPedidos(){
    	Intent h = new Intent(this, ListaPedidos.class);
    	startActivity(h);
    }

}
