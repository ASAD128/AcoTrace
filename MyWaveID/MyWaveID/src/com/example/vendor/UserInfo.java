package com.example.vendor;

//package com.example.fyp;

//package com.example.fyp;

import java.util.List;

//import com.androidhive.androidsqlite.Contact;
//import com.androidhive.androidsqlite.DatabaseHandler;

//import com.androidhive.androidsqlite.Contact;
//import com.androidhive.androidsqlite.DatabaseHandler;


import com.example.vendor.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn=(Button) findViewById(R.id.personalInfobtn);
		final EditText firstName=(EditText)findViewById(R.id.editText3);
		final EditText lastName=(EditText)findViewById(R.id.editText4);
		final EditText emailName=(EditText)findViewById(R.id.editText1);
		final EditText addressName=(EditText)findViewById(R.id.editText2);
		final EditText phoneName=(EditText)findViewById(R.id.editText5);
		
		int _ID=1;
		/*String _FNAME=firstName.getText().toString();
		String _LNAME=lastName.getText().toString();
		String _EMAIL=emailName.getText().toString();
		String _ADDRESS=addressName.getText().toString();
		String _PHONE=phoneName.getText().toString();
		/////////////////////
DatabaseHandler db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * 
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact(_FNAME, _PHONE));
 
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts(); 
        String logs=db.getContact(1).getName();
        Log.d("Junaid: ", logs);
 
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Name: ", log);
        
        }*/
		 final DatabaseHandler db = new DatabaseHandler(this);
		  
	        
	        /**
	         * CRUD Operations
	         * 
	        // Inserting Contacts
	        Log.d("Insert: ", "Inserting ..");
	        db.addContact(new Contact("Majid", "9100000000"));
	        db.addContact(new Contact(_FNAME,_PHONE));
	      //  db.addContact(new Contact("Srinivas", "9199999999"));
	       // db.addContact(new Contact("Tommy", "9522222222"));
	       // db.addContact(new Contact("Karthik", "9533333333"));
	        //
	        Contact c=db.getContact(_ID);
	        Log.d("Reading: ", c.getName());*/
	       //String log="Contact"+c.getFirstName();
	      //  Log.d("Name: ",log);
	        //LOg.d
	        
	 
	      // Reading all contacts
	        /*Log.d("Reading: ", "Reading all contacts..");
	        List<Contact> contacts = db.getAllContacts();       
	 
	        for (Contact cn : contacts) {
	            String log = "Id: "+cn.getID()+" ,Name: " + cn.getFirstName() + " ,Phone: " + cn.getPhoneNumber();
	                // Writing Contacts to log
	        Log.d("Name: ", log);
	        }*/
		/////////////////////
		
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				////////////
				String _FNAME=firstName.getText().toString();
				String _LNAME=lastName.getText().toString();
				String _EMAIL=emailName.getText().toString();
				String _ADDRESS=addressName.getText().toString();
				String _PHONE=phoneName.getText().toString();
				/////////////////////
		//DatabaseHandler db = new DatabaseHandler(this);
		        
		        /**
		         * CRUD Operations
		         * */
		        // Inserting Contacts
		        Log.d("Insert: ", "Inserting ..");
		       // db.addContact(new Contact("Ravi", "9100000000"));
		       // db.addContact(new Contact(_FNAME, _PHONE));
		        db.addContact(new Contact(_FNAME,_LNAME,_EMAIL,_ADDRESS, _PHONE));
		 
		        // Reading all contacts
		        Log.d("Reading: ", "Reading all contacts..");
		        List<Contact> contacts = db.getAllContacts(); 
		        //String logs=db.getContact(1).getName();
		        //Log.d("Junaid: ", logs);
		 
		       // for (Contact cn : contacts) {
		       //     String log = "Id: "+cn.getID()+" ,Name: " + cn.get_fname() + " ,Phone: " + cn.getPhoneNumber();
		                // Writing Contacts to log
		       // Log.d("Name: ", log);
		        
		       // }
				////////////
				Intent i=new Intent("com.example.fyp.DEVICEDETAIL");
		        //Intent i=new Intent("com.example.fyp.PLAYSOUND");
		        
				startActivity(i);
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

