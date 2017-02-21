package bluepanther.jiljungjuk.Tabs;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.luseen.spacenavigation.SpaceNavigationView;

import java.io.File;
import java.io.IOException;

import bluepanther.jiljungjuk.Audiodesc;
import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.Filepath;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.Uploader;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class Audio extends android.support.v4.app.Fragment {

    Uri selectedAudiouri = null;

    ImageView fplay,frecord,fstop,audchose;
    protected static final int REQUEST_PICK_AUDIO = 1;
    private String outputFile = null;
    Button button_share;
    String path,date,desc,fname;
    Audiodesc audiodesc;
    static EditText desc_audio;
    Uri audioUri;
    static TextView textView;
    SpaceNavigationView spaceNavigationView;
   // static String cat;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;


    private MediaRecorder myAudioRecorder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.audio,container,false);

        textView = (TextView) v.findViewById(R.id.textView);

        String cat = TempClass.sharedValue;
        textView.setText(Category.subcat);
        Firebase.setAndroidContext(getActivity());
        fb_db=new Firebase(Base_url);
        audchose = (ImageView)v.findViewById(R.id.audchose);
        fplay = (ImageView) v.findViewById(R.id.fplay);
        frecord = (ImageView)v.findViewById(R.id.frecord);
        fstop = (ImageView)v.findViewById(R.id.fstop);
        //button_share = (Button)v.findViewById(R.id.button_share);
        desc_audio = (EditText)v.findViewById(R.id.desc_audio);



        fstop.setEnabled(false);
        fplay.setEnabled(false);
        File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        int cnt=f.listFiles().length;
        System.out.println("FIle cnt is "+cnt+"and they are"+f.listFiles());

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+Integer.toString(cnt+1)+".mp3";

        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);


        frecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                frecord.setEnabled(false);
                fstop.setEnabled(true);

                Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        fstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                fstop.setEnabled(false);
                fplay.setEnabled(true);
                path=outputFile;
                System.out.println("Adio path is "+path);
                audioUri=Uri.fromFile(new File(path));
                System.out.println("uri is"+audioUri);
                Toast.makeText(getActivity(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                new MyTask().execute();
            }
        });

        fplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        ImageView audchoose = (ImageView) v.findViewById(R.id.audchose);

        audchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Audio "), REQUEST_PICK_AUDIO);
            }
        });


//        button_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                System.out.println("date is "+date);
//                desc=desc_audio.getText().toString();
//                new MyTask().execute();
//            }
//        });


        return v;
    }


    public static String adesc()
    {
        String desc;
        desc=desc_audio.getText().toString();
        return  desc;
    }


    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        String filepath = null;
        switch(requestCode)
        {
            case REQUEST_PICK_AUDIO:
                if(resultCode == RESULT_OK)
                {
                    selectedAudiouri = data.getData();
                }else if(resultCode == getActivity().RESULT_CANCELED)
                {
                    Toast.makeText(getActivity(), "Audio was not selected", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(getActivity(), "Audio was not selected", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        try{



        if (requestCode == REQUEST_PICK_AUDIO) {
            audioUri = data.getData();
            //  String path = audioUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"

            Filepath obj=new Filepath();
            path=obj.getPaths(getContext(),audioUri);
            System.out.println("Path is "+ audioUri + " d " + obj.getPaths(getContext(),audioUri));

        }
        new MyTask().execute();
        }
        catch(Exception e)
        {

        }
    }



    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //     progressDialog = ProgressDialog.show(getActivity(), "Message", "Uploading Image...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {

             audiodesc = new Audiodesc();
            audiodesc.setUser(CurrentUser.user);
            audiodesc.setDate(date);
            audiodesc.setDesc(desc);
            audiodesc.setMaincat(Category.maincat);
            audiodesc.setSubcat(Category.subcat);


            fname = getFileName(audioUri);

            fname = fname.substring(0, fname.lastIndexOf('.'))+date;
            if(fname.equals(null))
            {
                fname = audioUri.getLastPathSegment();
            }

            System.out.println("FILE IS :"+fname);
            System.out.println("Checks"+CurrentUser.sclass+"and "+CurrentUser.ssec);


            Uploader auploader = new Uploader();
            auploader.setAfname(fname);
            auploader.setAudiodesc(audiodesc);
            auploader.setAuri(audioUri);




            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS"))
                System.out.println("SUCCESS");


            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }
    }
public static void setLabel()
{
    System.out.println("Label set");
    textView.setText(Category.subcat);
}
}
