package bluepanther.jiljungjuk.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.util.Log;
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

import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.FileDesc;
import bluepanther.jiljungjuk.Filepath;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.Uploader;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class Files extends android.support.v4.app.Fragment {
    ImageView manager;
    Button button_share;
    static EditText desc;
    String descr,date,path;
   Uri fileUri = null;
    String fname;
    byte[] bytearr;
    static TextView textView;
    private static final int PICK_FILE = 1;
    SpaceNavigationView spaceNavigationView;
    FileDesc fileDesc;

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.files,container,false);

        Firebase.setAndroidContext(getActivity());
        fb_db=new Firebase(Base_url);
        manager = (ImageView)v.findViewById(R.id.manager);
        desc = (EditText)v.findViewById(R.id.desc);
        //button_share = (Button)v.findViewById(R.id.button_share);
        textView = (TextView) v.findViewById(R.id.textView);
        String cat = TempClass.sharedValue;
        textView.setText(cat);
//        button_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                System.out.println("date is "+date);
//                descr=desc.getText().toString();
//                new MyTask().execute();
//            }
//        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    Intent gintent = new Intent();
                    gintent.setType("file/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(gintent, "Select File"),
                            PICK_FILE);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

        return v;
    }
    public static String fdesc()
    {
        String descr;
        descr=desc.getText().toString();

        return  descr;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String filePath = null;
        switch (requestCode) {
            case PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    fileUri = data.getData();
                    Filepath obj=new Filepath();
                    path=obj.getPaths(getActivity(),fileUri);
                    System.out.println(fileUri+" d "+fileUri.getPath());
                }
                break;
        }
new MyTask2().execute();
    }


    private class MyTask2 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Files_PU.this, "Message", "Uploading File...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            try {
                fileDesc = new FileDesc();
                fileDesc.setUser(CurrentUser.user);
                fileDesc.setDate(date);
                fileDesc.setDesc(descr);
                fileDesc.setMaincat(Category.maincat);
                fileDesc.setSubcat(Category.subcat);
                System.out.println("Mangatha dawww");
                fname = getFileName(fileUri);

                fname = fname.substring(0, fname.lastIndexOf('.'));
                if(fname.equals(null))
                {
                    fname = fileUri.getLastPathSegment();
                }


                System.out.println("FILE IS :"+fname);
                System.out.println("Checks"+CurrentUser.sclass+"and "+CurrentUser.ssec);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(fname).setValue(imageDesc);


                Uploader fuploader = new Uploader();
                fuploader.setFfname(fname);
                fuploader.setFileDesc(fileDesc);
                fuploader.setFuri(fileUri);




                return "SUCCESS";

            } catch (Exception e) {
//                Toast.makeText(getActivity(), "Network Busy!!", Toast.LENGTH_LONG).show();
                //System.out.println("Error SMS "+e);

            }

            return "FAILED";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");

            }


            //   progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
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
    public static void setLabel()
    {
        System.out.println("Label set");
        textView.setText(Category.subcat);
    }
}
