package com.example.vendor;

import com.example.vendor.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Browser extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		Intent i = new Intent(Intent.ACTION_VIEW, 
			       Uri.parse("http://192.168.1.9:5656/JAXRS-FileUpload/form.html"));
			startActivity(i);
	}

}
