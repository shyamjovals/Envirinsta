package bluepanther.jiljungjuk.Tabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.luseen.spacenavigation.SpaceNavigationView;

import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.TextDesc;
import bluepanther.jiljungjuk.Uploader;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class Text extends android.support.v4.app.Fragment {
    TextDesc textDesc;
   static EditText editText, desc;
    Button button_share;
    String descr, date,textcontent;
    static TextView textView;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    SpaceNavigationView spaceNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text, container, false);

        editText = (EditText) v.findViewById(R.id.editText);
        //button_share = (Button) v.findViewById(R.id.button_share);
        desc = (EditText) v.findViewById(R.id.desc);
        Firebase.setAndroidContext(getActivity());
        fb_db = new Firebase(Base_url);
        textView = (TextView) v.findViewById(R.id.textView);
        String cat = TempClass.sharedValue;
        textView.setText(Category.subcat);



//        button_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                System.out.println("date is " + date);
//                descr = desc.getText().toString();
//                textcontent = editText.getText().toString();
//                new MyTask().execute();
//
//            }
//        });
//
new MyTask().execute();
        return v;
    }
    public static String tdesc()
    {
        String descr;
        descr=desc.getText().toString();

        return  descr;
    }
    public static String tcont()
    {
        String textcontent = editText.getText().toString();
        return  textcontent;
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

            textDesc = new TextDesc();
            textDesc.setUser(CurrentUser.user);
            textDesc.setDate(date);
            textDesc.setDesc(descr);
            textDesc.setMaincat(Category.maincat);
            textDesc.setSubcat(Category.subcat);
            textDesc.setText(textcontent);

            Uploader tuploader = new Uploader();
            tuploader.setTextDesc(textDesc);



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
    }
    public static void setLabel()
    {
        System.out.println("Label set");
        textView.setText(Category.subcat);
    }
}