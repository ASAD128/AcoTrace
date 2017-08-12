package com.example.vendor;

//import com.example.audio.MainActivity;
//import com.example.audio.R;

//import Tone;
import java.io.IOException;

import com.example.vendor.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PlaySound extends Activity{
	private MediaRecorder mRecorder = null;
	String mFileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_sound);
		Button btn=(Button) findViewById(R.id.btnrecordsoundui);
		Button btn1=(Button) findViewById(R.id.btnsubmitserverui);
		
		
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//AudioRecordTest();
				//startRecording();
				
			}
		});
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MediaPlayer mp = MediaPlayer.create(PlaySound.this, R.raw.beep); 
				mp.getDuration();
				mp.start();
				//Tone.sound(14000,1000,0.8);
				//stopRecording();
				Intent i=new Intent("com.example.fyp.SUBMITTOSERVER");
				startActivity(i);
				
			}
		});
	}
	/*private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("Audio", "prepare() failed");
        }

        mRecorder.start();
    }
	public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
*/

}
