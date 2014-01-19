package org.pmm.pantalladepreferencias;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MisPreferncias extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
		
	}
	

}
