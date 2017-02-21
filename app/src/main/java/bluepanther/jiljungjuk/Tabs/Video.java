package bluepanther.jiljungjuk.Tabs;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.Filepath;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.Uploader;
import bluepanther.jiljungjuk.VideoDesc;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class Video extends android.support.v4.app.Fragment {

    String fname;
    protected static final int REQUEST_PICK_VIDEO = 2;
    VideoDesc videoDesc;
    String date;
    ImageView camera,gallery;
    static EditText desc;
    String descr,path;
    Button button_share;
    Uri VideoUri;
    private Bitmap bitmap;
    Uri selectedVideoUri = null;
    static TextView textView;
    SpaceNavigationView spaceNavigationView;

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video,container,false);


        Firebase.setAndroidContext(getActivity());
        fb_db=new Firebase(Base_url);
        camera = (ImageView)v.findViewById(R.id.camera);
        gallery = (ImageView)v.findViewById(R.id.gallery);
        desc = (EditText)v.findViewById(R.id.desc);
       // button_share =(Button)v.findViewById(R.id.button_share);
        textView = (TextView) v.findViewById(R.id.textView);
        String cat = TempClass.sharedValue;
        textView.setText(Category.subcat);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                int cnt=f.listFiles().length;
                System.out.println("FIle cnt is "+cnt+"and they are"+f.listFiles());



                File mediaFile = new
                        File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/myvideo"+Integer.toString(cnt+1)+".mp4");
                path=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/myvideo"+Integer.toString(cnt+1)+".mp4";
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                VideoUri = Uri.fromFile(mediaFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, VideoUri);
                startActivityForResult(intent, VIDEO_CAPTURE);

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent gintent = new Intent();
                gintent.setType("video/*");
                gintent.setAction(Intent.ACTION_PICK);
                startActivityForResult(
                        Intent.createChooser(gintent, "Select Picture"),
                        REQUEST_PICK_VIDEO);


//                Intent intent = new Intent();
//                intent.setType("video/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Video "), REQUEST_PICK_VIDEO);
            }
        });
//        button_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                System.out.println("date is "+date);
//                descr=desc.getText().toString();
//                new MyTask().execute();
//            }
//        });

        return v;
        

        
    }

    public static String vdesc()
    {
        String descr;
        descr=desc.getText().toString();

        return  descr;
    }
    private boolean hasCamera() {
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }
    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) {
                Toast.makeText(getContext(), "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getContext(), "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
        try {
            if (requestCode == REQUEST_PICK_VIDEO) {


                if (data.equals(null)) {
                    Toast.makeText(getContext(), "Video was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    VideoUri = data.getData();
                    Toast.makeText(getContext(), VideoUri.toString(), Toast.LENGTH_SHORT).show();
                    Filepath obj = new Filepath();
                    path = obj.getPaths(getContext(), VideoUri);
                    System.out.println("VIDEOO" + VideoUri + " d " + VideoUri.getPath());
                }
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




            videoDesc = new VideoDesc();
            videoDesc.setUser(CurrentUser.user);
            videoDesc.setDate(date);
            videoDesc.setDesc(descr);
            videoDesc.setMaincat(Category.maincat);
            videoDesc.setSubcat(Category.subcat);


            fname = getFileName(VideoUri);

            fname = fname.substring(0, fname.lastIndexOf('.'));
            if(fname.equals(null))
            {
                fname = VideoUri.getLastPathSegment();
            }

            System.out.println("FILE IS :"+fname);
            System.out.println("Checks"+CurrentUser.sclass+"and "+CurrentUser.ssec);

            Uploader vuploader = new Uploader();
            vuploader.setVfname(fname);
            vuploader.setVideoDesc(videoDesc);
            vuploader.setVuri(VideoUri);



            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");

            }

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
