//package bluepanther.jiljungjuk;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
///**
// * Created by Hariharsudan on 11/2/2016.
// */
//
//public class Events_tab extends android.support.v4.app.Fragment {
//
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private static String LOG_TAG = "EventsTab";
//    String tab;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.events_tab, container, false);
//
//
//        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerViewAdapter(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);
//        tab=TempClass.sharedValue;
//        return v;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(tab.equals("Events")){
//        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
//                .MyClickListener() {
//            @Override
//            public void onItemClick(final int position, View v) {
//                Log.i(LOG_TAG, " Clicked on Item " + position);
////                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////                builder.setMessage("Participate in this Event?")
////                        .setCancelable(false)
////                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int id) {
////                                Toast.makeText(getActivity(),"You are participating in Event "+position,Toast.LENGTH_SHORT).show();
////                            }
////                        })
////                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int id) {
////                                dialog.cancel();
////                            }
////                        });
////                AlertDialog alert = builder.create();
////                alert.show();
//            }
//        });}
//    }
//
//    private ArrayList<DataObject> getDataSet() {
//        ArrayList results = new ArrayList<DataObject>();
//        for (int index = 0; index < 20; index++) {
//            DataObject obj = new DataObject("Event " + index,
//                    "Info about Event " + index);
//            results.add(index, obj);
//        }
//        return results;
//    }
//}