package com.example.vendor;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.vendor.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SoundRecordingExample2 extends Activity {
        private static final int RECORDER_BPP = 16;
        private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
        private static final String AUDIO_RECORDER_FOLDER = "AudioRecorderVendor";
        private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
        private static final int RECORDER_SAMPLERATE = 44100;
        private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
        private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
        
        private AudioRecord recorder = null;
        private int bufferSize = 0;
        private Thread recordingThread = null;
        private boolean isRecording = false;
        Contact info=null;
        String recordedFileName=null;
        final DatabaseHandler DB = new DatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_sound);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             recordedFileName = extras.getString("RecFileName");
        }
        
        setButtonHandlers();
        enableButtons(false);
        
        bufferSize = AudioRecord.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
    }

        private void setButtonHandlers() {
                ((Button)findViewById(R.id.btnrecordsoundui)).setOnClickListener(btnClick);
        ((Button)findViewById(R.id.btnsubmitserverui)).setOnClickListener(btnClick);
        }
        
        private void enableButton(int id,boolean isEnable){
                ((Button)findViewById(id)).setEnabled(true);
        }
        
        private void enableButtons(boolean isRecording) {
                enableButton(R.id.btnrecordsoundui,!isRecording);
                enableButton(R.id.btnsubmitserverui,isRecording);
        }
        
        private String getFilename(){
                String filepath = Environment.getExternalStorageDirectory().getPath();
                File file = new File(filepath,AUDIO_RECORDER_FOLDER);
                
                if(!file.exists()){
                        file.mkdirs();
                }
     
                
		        //return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + AUDIO_RECORDER_FILE_EXT_WAV);
		        return (file.getAbsolutePath() + "/" + recordedFileName  + AUDIO_RECORDER_FILE_EXT_WAV);
        }
        
        private String getTempFilename(){
                String filepath = Environment.getExternalStorageDirectory().getPath();
                File file = new File(filepath,AUDIO_RECORDER_FOLDER);
                
                if(!file.exists()){
                        file.mkdirs();
                }
                
                File tempFile = new File(filepath,AUDIO_RECORDER_TEMP_FILE);
                
                if(tempFile.exists())
                        tempFile.delete();
                
                return (file.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
        }
        
        private void startRecording(){
                recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                                RECORDER_SAMPLERATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, bufferSize);
                
                int i = recorder.getState();
                if(i==1)
                	recorder.startRecording();
                Toast.makeText(getApplicationContext(), " Start Recording ",
             		   Toast.LENGTH_LONG).show();
                
                isRecording = true;
                
                recordingThread = new Thread(new Runnable() {
                        
                        @Override
                        public void run() {
                                writeAudioDataToFile();
                        }
                },"AudioRecorder Thread");
                
                recordingThread.start();
        }
        
        private void writeAudioDataToFile(){
                byte data[] = new byte[bufferSize];
                String filename = getTempFilename();
                FileOutputStream os = null;
                
                try {
                        os = new FileOutputStream(filename);
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                int read = 0;
                
                if(null != os){
                        while(isRecording){
                                read = recorder.read(data, 0, bufferSize);
                                
                                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                                        try {
                                                os.write(data);
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                        
                        try {
                                os.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
        
        private void stopRecording(){
        	Toast.makeText(getApplicationContext(), " Stop Recording ",
          		   Toast.LENGTH_LONG).show();
                if(null != recorder){
                        isRecording = false;
                        
                        int i = recorder.getState();
                        if(i==1)
                        	recorder.stop();
                        recorder.release();
                        
                        recorder = null;
                        recordingThread = null;
                }
                
                copyWaveFile(getTempFilename(),getFilename());
                //deleteTempFile();
        }

        private void deleteTempFile() {
                File file = new File(getTempFilename());
                
                file.delete();
        }
        
        private void copyWaveFile(String inFilename,String outFilename){
                FileInputStream in = null;
                FileOutputStream out = null;
                long totalAudioLen = 0;
                long totalDataLen = totalAudioLen + 36;
                long longSampleRate = RECORDER_SAMPLERATE;
                int channels = 2;
                long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;
                
                byte[] data = new byte[bufferSize];
                
                try {
                        in = new FileInputStream(inFilename);
                        out = new FileOutputStream(outFilename);
                        totalAudioLen = in.getChannel().size();
                        totalDataLen = totalAudioLen + 36;
                        
                        AppLog.logString("File size: " + totalDataLen);
                        
                        WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                                        longSampleRate, channels, byteRate);
                        
                        while(in.read(data) != -1){
                                out.write(data);
                        }
                        
                        in.close();
                        out.close();
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void WriteWaveFileHeader(
                        FileOutputStream out, long totalAudioLen,
                        long totalDataLen, long longSampleRate, int channels,
                        long byteRate) throws IOException {
                
                byte[] header = new byte[44];
                
                header[0] = 'R';  // RIFF/WAVE header
                header[1] = 'I';
                header[2] = 'F';
                header[3] = 'F';
                header[4] = (byte) (totalDataLen & 0xff);
                header[5] = (byte) ((totalDataLen >> 8) & 0xff);
                header[6] = (byte) ((totalDataLen >> 16) & 0xff);
                header[7] = (byte) ((totalDataLen >> 24) & 0xff);
                header[8] = 'W';
                header[9] = 'A';
                header[10] = 'V';
                header[11] = 'E';
                header[12] = 'f';  // 'fmt ' chunk
                header[13] = 'm';
                header[14] = 't';
                header[15] = ' ';
                header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
                header[17] = 0;
                header[18] = 0;
                header[19] = 0;
                header[20] = 1;  // format = 1
                header[21] = 0;
                header[22] = (byte) channels;
                header[23] = 0;
                header[24] = (byte) (longSampleRate & 0xff);
                header[25] = (byte) ((longSampleRate >> 8) & 0xff);
                header[26] = (byte) ((longSampleRate >> 16) & 0xff);
                header[27] = (byte) ((longSampleRate >> 24) & 0xff);
                header[28] = (byte) (byteRate & 0xff);
                header[29] = (byte) ((byteRate >> 8) & 0xff);
                header[30] = (byte) ((byteRate >> 16) & 0xff);
                header[31] = (byte) ((byteRate >> 24) & 0xff);
                header[32] = (byte) (2 * 16 / 8);  // block align
                header[33] = 0;
                header[34] = RECORDER_BPP;  // bits per sample
                header[35] = 0;
                header[36] = 'd';
                header[37] = 'a';
                header[38] = 't';
                header[39] = 'a';
                header[40] = (byte) (totalAudioLen & 0xff);
                header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
                header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
                header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

                out.write(header, 0, 44);
        }
        
        private View.OnClickListener btnClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        switch(v.getId()){
                                case R.id.btnrecordsoundui:{
                                        AppLog.logString("Start Recording");
                                        
                                        enableButtons(true);
                                        startRecording();
                                        /////////////////////
                                        MediaPlayer mp = MediaPlayer.create(SoundRecordingExample2.this, R.raw.sample1); 
                        				mp.getDuration();
                        				mp.start();
                                        /////////////////////
                                        Handler handler=new Handler();
                                        Runnable r=new Runnable() 
                                       {
                                        public void run() 
                                       {
                                         stopRecording();
                                          //recorder.release(); 
                                         } 

                                       };
                                       handler.postDelayed(r, 7000);
                                        /////////////////////
                                                        
                                        break;
                                }
                                case R.id.btnsubmitserverui:{
                                       // AppLog.logString("Stop Recording");
                                        
                                        //enableButtons(false);
                                        //stopRecording();
                                        
                                       // break;
                                	//Intent ii=new Intent("com.example.fyp.SUBMITTOSERVER");
                    				//startActivity(ii);
                                	//Intent i = new Intent(Intent.ACTION_VIEW, 
                 					//       Uri.parse("http://192.168.1.25:5656/JAXRS-FileUpload/form.html"));
                 					//startActivity(i);
                                	Intent i = new Intent(Intent.ACTION_VIEW, 
                 					       Uri.parse("http://192.168.1.104:5665/JAXRS-FileUpload/form.html"));
                 					startActivity(i);
                                }
                        }
                }
        }; 
}