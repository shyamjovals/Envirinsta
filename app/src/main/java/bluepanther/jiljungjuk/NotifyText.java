package bluepanther.jiljungjuk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import bluepanther.jiljungjuk.Notifications.Textnoti;
import bluepanther.jiljungjuk.Notifications.Textnoti2;
import bluepanther.jiljungjuk.Contacts_Grid.CustomAdapter;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class NotifyText extends Fragment {
    ListView mylistview, mylistview2;
    String date;
    public CustomAdapter adapter, adapter2;
    public String member_names[], member_names2[];
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("IN TEXT");
        View v = inflater.inflate(R.layout.noti_view, container, false);

        Firebase.setAndroidContext(getActivity());
        fb_db = new Firebase(Base_url);

        mylistview = (ListView) v.findViewById(R.id.listView);
        mylistview2 = (ListView) v.findViewById(R.id.listView2);
        try {
            if(Textnoti.state) {
                member_names = Arrays.copyOf(Textnoti.members, Textnoti.members.length);
                adapter = new CustomAdapter(getActivity(), Textnoti.row);
                mylistview.setAdapter(adapter);
            }
            if(Textnoti2.state) {
                member_names2 = Arrays.copyOf(Textnoti2.members, Textnoti2.members.length);
                adapter2 = new CustomAdapter(getActivity(), Textnoti2.row);
                mylistview2.setAdapter(adapter2);
            }
        } catch (Exception e) {
    System.out.println("Group problm");
        }
        //   mylistview.setOnItemClickListener(Timeline.this);
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String res = member_names[position];
                Intent i = new Intent(getActivity(), txtdisp.class);
                i.putExtra("value", Textnoti.contentt.get(position));
                startActivity(i);
                System.out.println("Downloading Text" + res);

            }
        });

        mylistview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String res = member_names2[position];
                Intent i = new Intent(getActivity(), txtdisp.class);
                i.putExtra("value", Textnoti2.contentt.get(position));
                startActivity(i);
                System.out.println("Downloading Text" + res);

            }
        });

        new MyTask().execute();

        return v;
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setIdate(CurrentUser.sidate);
            cred_update.setVdate(CurrentUser.svdate);
            cred_update.setAdate(CurrentUser.sadate);
            cred_update.setFdate(CurrentUser.sfdate);
            cred_update.setTdate(date);
            CurrentUser.stdate = date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

}








