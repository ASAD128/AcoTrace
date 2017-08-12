package com.example.vendor;


import java.util.List;

import com.example.vendor.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfoValidationUI extends Activity {
	Button btn=null;
	EditText firstName=null;
	 EditText lastName=null;
	 EditText emailName=null;
	 EditText addressName=null;
	 EditText phoneName=null;
	 DatabaseHandler db=null;
	 int _ID=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_ui);
		 
		  
	        
		 registerViews();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 private void registerViews() {
		 
		 btn=(Button) findViewById(R.id.bn_retrieve);
		 firstName=(EditText)findViewById(R.id.first_name);
	 lastName=(EditText)findViewById(R.id.last_name);
		 emailName=(EditText)findViewById(R.id.email);
		 addressName=(EditText)findViewById(R.id.address);
		 phoneName=(EditText)findViewById(R.id.phone);
		
		
	
		  db = new DatabaseHandler(this);
	        //etNormalText = (EditText) findViewById(R.id.et_normal_text);
	        // TextWatcher would let us check validation error on the fly
	        firstName.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(firstName);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	        //etEmailAddrss = (EditText) findViewById(R.id.et_email_address);
	        emailName.addTextChangedListener(new TextWatcher() {
	            // after every change has been made to this editText, we would like to check validity
	            public void afterTextChanged(Editable s) {
	                Validation.isEmailAddress(emailName, true);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	        //phoneName = (EditText) findViewById(R.id.et_phone_number);
	        phoneName.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.isPhoneNumber(phoneName, false);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	       // btn = (Button) findViewById(R.id.);
	        btn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                /*
	                Validation class will check the error and display the error on respective fields
	                but it won't resist the form submission, so we need to check again before submit
	                 */
	            	String _FNAME=firstName.getText().toString();
					String _LNAME=lastName.getText().toString();
					String _EMAIL=emailName.getText().toString();
					String _ADDRESS=addressName.getText().toString();
					String _PHONE=phoneName.getText().toString();
				
			        Log.d("Insert: ", "Inserting ..");
			       // db.addContact(new Contact("Ravi", "9100000000"));
			       // db.addContact(new Contact(_FNAME, _PHONE));
			        db.addContact(new Contact(_FNAME,_LNAME,_EMAIL,_ADDRESS, _PHONE));
			 
			        // Reading all contacts
			        Log.d("Reading: ", "Reading all contacts..");
			        List<Contact> contacts = db.getAllContacts(); 
			        
			        //registerViews();
			        
			        if ( checkValidation () )
			        {
	                    submitForm();
			        }
	                else
	                {
	                    Toast.makeText(UserInfoValidationUI.this, "Form contains error", Toast.LENGTH_LONG).show();
	                }
			        
			        
					Intent i=new Intent("com.example.vendor.DEVICEDETAIL");
			        //Intent i=new Intent("com.example.fyp.PLAYSOUND");
			        
					startActivity(i);
	            }
	        });
	    }
	 
	    private void submitForm() {
	        // Submit your form here. your form is valid
	       // Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
	    }
	 
	    private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasText(firstName)) ret = false;
	        if (!Validation.isEmailAddress(emailName, true)) ret = false;
	        if (!Validation.isPhoneNumber(phoneName, false)) ret = false;
	 
	        return ret;
	    }
}

