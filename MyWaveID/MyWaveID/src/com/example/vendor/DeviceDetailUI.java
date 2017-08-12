package com.example.vendor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

//import com.avilyne.android.AndroidRESTClientActivity;
//import com.avilyne.android.AndroidRESTClientActivity.WebServiceTask;
//import com.example.androidrestclient.R;



import com.example.vendor.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceDetailUI extends Activity{
	final DatabaseHandler db = new DatabaseHandler(this);
	String Manufacturer=null;
	String Model =null;
	
	 private static final String SERVICE_URL = "http://192.168.1.25:5656/RestWebServiceDemo/rest/person";
	 List<Contact> contact=null;
	    private static final String TAG = "AndroidRESTClientActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_detail);
		Button btn=(Button)findViewById(R.id.deviceDetailbtn);
		TextView tv1 = (TextView) findViewById(R.id.ModelNumberUI);
		TextView tv2 = (TextView) findViewById(R.id.companyUI);
		 Manufacturer = Build.MANUFACTURER;
		//String brand        = Build.BRAND;
		//String product      = Build.PRODUCT;
		 Model        = Build.MODEL;
		tv1.setText(Manufacturer);
		tv2.setText(Model);
		
		
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postData();
				Intent i=new Intent("com.example.vendor.SOUNDRECORDINGEXAMPLE2");
				startActivity(i);
				
			}
		});
	}
////////////
	public void retrieveSampleData(View vw) {
		 
        String sampleURL = SERVICE_URL + "/sample";
 
        WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this, "GETting data...");
         
        wst.execute(new String[] { sampleURL });
         
    }
 
    public void clearControls(View vw) {
 
       
           ////      
    }
     
    public void postData() {
 Contact con=null;
    	List<Contact> contact= db.getAllContacts();
		for (Contact cn : contact) {
		con=cn;
            String log = "Id: "+cn.getID()+" ,Name: " + cn.get_fname() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Activity: ", log);
        
		}
 
        String firstName = con.get_fname();
        String lastName = con.get_lname();
        String email = con.get_email();
        String address = con.get_address();
        String phone = con.getPhoneNumber();
        String company = Manufacturer;
        String model = Model;
 
        if (firstName.equals("") || lastName.equals("") || email.equals("")) {
            Toast.makeText(this, "Please enter in all required fields.",
                    Toast.LENGTH_LONG).show();
            return;
        }
 
        WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this, "Posting data...");
 
        wst.addNameValuePair("firstName", firstName);
        wst.addNameValuePair("lastName", lastName);
        wst.addNameValuePair("email", email);
        wst.addNameValuePair("address", address);
        wst.addNameValuePair("phone", phone);
        wst.addNameValuePair("Company", company);
        wst.addNameValuePair("Model", model);
 
        // the passed String is the URL we will POST to
        wst.execute(new String[] { SERVICE_URL });
 
    }
 
    /*public void handleResponse(String response) {
         
        EditText edFirstName = (EditText) findViewById(R.id.first_name);
        EditText edLastName = (EditText) findViewById(R.id.last_name);
        EditText edEmail = (EditText) findViewById(R.id.email);
         
        edFirstName.setText("");
        edLastName.setText("");
        edEmail.setText("");
         
        try {
             
            JSONObject jso = new JSONObject(response);
             
            String firstName = jso.getString("firstName");
            String lastName = jso.getString("lastName");
            String email = jso.getString("email");
             
            edFirstName.setText(firstName);
            edLastName.setText(lastName);
            edEmail.setText(email);         
             
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
         
    }*/
 
   /* private void hideKeyboard() {
 
        InputMethodManager inputManager = (InputMethodManager) DeviceDetail.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
 
        inputManager.hideSoftInputFromWindow(
                DeviceDetail.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }*/
     
     
    private class WebServiceTask extends AsyncTask<String, Integer, String> {
 
        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;
         
        private static final String TAG = "WebServiceTask";
 
        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 30000;
         
        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;
         
        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Processing...";
 
        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
 
        private ProgressDialog pDlg = null;
 
        public WebServiceTask(int taskType, Context mContext, String processMessage) {
 
            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }
 
        public void addNameValuePair(String name, String value) {
 
            params.add(new BasicNameValuePair(name, value));
        }
 
  /*      private void showProgressDialog() {
             
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();
 
        }*/
 
        @Override
      //  protected void onPreExecute() {
 
           // hideKeyboard();
           // showProgressDialog();
 
      //  }
 
        protected String doInBackground(String... urls) {
 
            String url = urls[0];
            String result = "";
 
            HttpResponse response = doResponse(url);
 
            if (response == null) {
                return result;
            } else {
 
                try {
 
                    result = inputStreamToString(response.getEntity().getContent());
 
                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
 
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }
 
            }
 
            return result;
        }
 
       // @Override
       // protected void onPostExecute(String response) {
             
            //handleResponse(response);
        //    pDlg.dismiss();
             
       // }
         
        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {
             
            HttpParams htpp = new BasicHttpParams();
             
            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);
             
            return htpp;
        }
         
        private HttpResponse doResponse(String url) {
             
            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());
 
            HttpResponse response = null;
 
            try {
                switch (taskType) {
 
                case POST_TASK:
                    HttpPost httppost = new HttpPost(url);
                    // Add parameters
                    httppost.setEntity(new UrlEncodedFormEntity(params));
 
                    response = httpclient.execute(httppost);
                    break;
                case GET_TASK:
                    HttpGet httpget = new HttpGet(url);
                    response = httpclient.execute(httpget);
                    break;
                }
            } catch (Exception e) {
 
                Log.e(TAG, e.getLocalizedMessage(), e);
 
            }
 
            return response;
        }
         
        private String inputStreamToString(InputStream is) {
 
            String line = "";
            StringBuilder total = new StringBuilder();
 
            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
 
            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
 
            // Return full string
            return total.toString();
        }
 
    }
	///////////////
}
