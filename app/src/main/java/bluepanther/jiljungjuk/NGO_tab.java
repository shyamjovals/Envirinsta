package bluepanther.jiljungjuk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class NGO_tab extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "NGOTab";
    public Firebase fb_db2;
    String tab,finalngo1,finalngo2;
    ArrayList <DataObject>results = new ArrayList<DataObject>();
    private String Base_url = "https://envirinsta.firebaseio.com/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ngo_tab, container, false);

        Firebase.setAndroidContext(getContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(null);
//        mRecyclerView.setAdapter(mAdapter);
        new MyTask5().execute();
        TempClass.sharedValue="NGO";
        tab=TempClass.sharedValue;
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(tab.equals("NGO")) {
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

                    try{AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Visit this NGO page?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        System.out.println("Ngo is"+results.get(position).getmText1());
                                        finalngo1=results.get(position).getmText1();
                                        finalngo2=results.get(position).getmText2();
                                        Intent intent = new Intent(getActivity(), NGO_user_page.class);
                                        intent.putExtra("NGOname",finalngo1);
                                        intent.putExtra("NGOinfo",finalngo2);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception "+e);
                    }


                }

        });}
    }

    public class MyTask5 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {


            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGO/");
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        newNGO ngoupdate = postSnapshot.getValue(newNGO.class);
                        String uname = ngoupdate.getNgouname();
                        String password = ngoupdate.getNgopass();
                        String ngoname=ngoupdate.getNgoname();
                        String ngodesc=ngoupdate.getNgoinfo();
                        String fols=Integer.toString(ngoupdate.getFollowers().size())+" following";


                            DataObject obj = new DataObject(ngoname,ngodesc,fols);
                            results.add(obj);
                    }
                    mAdapter = new MyRecyclerViewAdapter(results);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }
}