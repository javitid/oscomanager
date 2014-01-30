package com.komponentes.oscomanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Estados extends Activity implements OnCheckedChangeListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estados);
		
		// Received parameters
		int estado = getIntent().getIntExtra("estado", 0);
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
		//TODO: send REST request to change status
		finish();
	}
}
