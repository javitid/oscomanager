package com.komponentes.oscomanager;

import com.komponentes.oscomanager.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Configurar extends PreferenceActivity {
	   @Override protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      addPreferencesFromResource(R.xml.configurar);
	   }
}
