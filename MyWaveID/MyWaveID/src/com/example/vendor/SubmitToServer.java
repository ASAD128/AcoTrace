package com.example.vendor;

import com.example.vendor.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubmitToServer extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_server);
		Button btn=(Button) findViewById(R.id.sentToServerbtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent i =new Intent("com.example.fyp.USERINFO");
				//startActivity(i);
				//Intent i=new Intent("com.example.fyp.SOUNDRECORDINGEXAMPLE2");
				//startActivity(i);
				Intent i = new Intent(Intent.ACTION_VIEW, 
					       Uri.parse("http://192.168.1.25:5656/JAXRS-FileUpload/form.html"));
					startActivity(i);
				
				
			}
		});
	}

}
