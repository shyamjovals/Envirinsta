package bluepanther.jiljungjuk;

/**
 * Created by SUBASH.M on 11/8/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import bluepanther.jiljungjuk.InternalStorage.Internal_Audio;
import bluepanther.jiljungjuk.InternalStorage.Internal_File;
import bluepanther.jiljungjuk.InternalStorage.Internal_Image;
import bluepanther.jiljungjuk.InternalStorage.Internal_Text;
import bluepanther.jiljungjuk.InternalStorage.Internal_Video;
import bluepanther.jiljungjuk.Notifications.Audionoti;
import bluepanther.jiljungjuk.Notifications.Audionoti2;
import bluepanther.jiljungjuk.Notifications.Filenoti;
import bluepanther.jiljungjuk.Notifications.Filenoti2;
import bluepanther.jiljungjuk.Notifications.Imgnoti;
import bluepanther.jiljungjuk.Notifications.Imgnoti2;
import bluepanther.jiljungjuk.Notifications.Textnoti;
import bluepanther.jiljungjuk.Notifications.Textnoti2;
import bluepanther.jiljungjuk.Notifications.Videonoti;
import bluepanther.jiljungjuk.Notifications.Videonoti2;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service {

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    Boolean flagz;

    String lastlogin;
    String tmp1, tmp2, tmp3, tmp4, tmp5;
    ArrayList<String> namei, categi, datesi, contenti, authi, nodenamei;
    ArrayList<String> namea, catega, datesa, contenta, autha, nodenamea;
    ArrayList<String> namev, categv, datesv, contentv, authv, nodenamev;
    ArrayList<String> namef, categf, datesf, contentf, authf, nodenamef;
    ArrayList<String> namet, categt, datest, contentt, autht, nodenamet;

    ArrayList<String> namei2, categi2, datesi2, contenti2, authi2, nodenamei2;
    ArrayList<String> namea2, catega2, datesa2, contenta2, autha2, nodenamea2;
    ArrayList<String> namev2, categv2, datesv2, contentv2, authv2, nodenamev2;
    ArrayList<String> namef2, categf2, datesf2, contentf2, authf2, nodenamef2;
    ArrayList<String> namet2, categt2, datest2, contentt2, autht2, nodenamet2;

    Boolean flag = true;

    String[] member_namesi;
    String[] member_namesa;
    String[] member_namesv;
    String[] member_namesf;
    String[] member_namest;

    String[] member_namesi2;
    String[] member_namesa2;
    String[] member_namesv2;
    String[] member_namesf2;
    String[] member_namest2;

    String[] contenttarr;
    String[] statuesi2;
    String[] timei2;
    String[] authori2;


    String[] statuesa2;
    String[] timea2;
    String[] authora2;

    String[] statuesv2;
    String[] timev2;
    String[] authorv2;

    String[] statuesf2;
    String[] timef2;
    String[] authorf2;

    String[] statuest2;
    String[] timet2;
    String[] authort2;

    String[] nodei2;
    String[] nodea2;
    String[] nodev2;
    String[] nodef2;
    String[] nodet2;



    String[] statuesi;
    String[] timei;
    String[] authori;


    String[] statuesa;
    String[] timea;
    String[] authora;

    String[] statuesv;
    String[] timev;
    String[] authorv;

    String[] statuesf;
    String[] timef;
    String[] authorf;

    String[] statuest;
    String[] timet;
    String[] authort;

    String[] nodei;
    String[] nodea;
    String[] nodev;
    String[] nodef;
    String[] nodet;

    List<RowItem> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    List<RowItem> rowItemsi2, rowItemsa2, rowItemsv2, rowItemsf2, rowItemst2;





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String xxx = Base_url + "Accounts/";
        System.out.println("User is" + xxx);
        Firebase.setAndroidContext(this);
        fb_db = new Firebase(xxx);
        fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                    String uname = cred_update.getUsn();
                    String password = cred_update.getPass();
                    System.out.println("Checking" + uname + "and" + CurrentUser.user);
                    if ((uname.equals(CurrentUser.user))) {
                        System.out.println("subashh " + cred_update.idate);

                        new CurrentUser(uname, password, cred_update.cls, cred_update.sec, cred_update.idate, cred_update.adate, cred_update.vdate, cred_update.fdate, cred_update.tdate);

                        new MyTask2().execute();
                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("FIREBASE ERROR OCCOURED");
            }
        });

        System.out.println("Assigned values");


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
    @Override public void onTaskRemoved(Intent rootIntent){
       System.out.println("Service stopped now");
//        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
//
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(
//                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
//                restartServicePendingIntent);

        System.out.println("Service restarted");
        super.onTaskRemoved(rootIntent);

    }
    public boolean icheckdate(String date)
    {
        System.out.println("Checking my"+lastlogin+"with"+date);
        if (CurrentUser.sidate.compareTo(date)<0)
        {
            return true;
        }
        return false;
    }
    public boolean acheckdate(String date)
    {
        System.out.println("Checking my"+lastlogin+"with"+date);
        if (CurrentUser.sadate.compareTo(date)<0)
        {
            return true;
        }
        return false;
    }
    public boolean vcheckdate(String date)
    {
        System.out.println("Checking my"+lastlogin+"with"+date);
        if (CurrentUser.svdate.compareTo(date)<0)
        {
            return true;
        }
        return false;
    }
    public boolean fcheckdate(String date)
    {
        System.out.println("Checking my"+lastlogin+"with"+date);
        if (CurrentUser.sfdate.compareTo(date)<0)
        {
            return true;
        }
        return false;
    }
    public boolean tcheckdate(String date)
    {
        System.out.println("Checking my"+lastlogin+"with"+date);
        if (CurrentUser.stdate.compareTo(date)<0)
        {
            return true;
        }
        return false;
    }
    public class MyTask2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            rowItemsi = new ArrayList<RowItem>();
            rowItemsa = new ArrayList<RowItem>();
            rowItemsv = new ArrayList<RowItem>();
            rowItemsf = new ArrayList<RowItem>();
            rowItemst = new ArrayList<RowItem>();

            rowItemsi2 = new ArrayList<RowItem>();
            rowItemsa2 = new ArrayList<RowItem>();
            rowItemsv2 = new ArrayList<RowItem>();
            rowItemsf2 = new ArrayList<RowItem>();
            rowItemst2 = new ArrayList<RowItem>();

            // Let it continue running until it is stopped.
            //images
            tmp1 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Images/";
            fb_db = new Firebase(tmp1);
            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contenti = new ArrayList<>();
                    namei = new ArrayList<>();
                    categi = new ArrayList<>();
                    datesi = new ArrayList<>();
                    authi = new ArrayList<String>();
                    nodenamei = new ArrayList<>();

                    contenti2 = new ArrayList<>();
                    namei2 = new ArrayList<>();
                    categi2 = new ArrayList<>();
                    datesi2 = new ArrayList<>();
                    authi2 = new ArrayList<String>();
                    nodenamei2 = new ArrayList<>();

                    rowItemsi.clear();
                    rowItemsi2.clear();

                    //        adapter.notifyDataSetChanged();

                    System.out.println("Entering text");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ImageDesc images = postSnapshot.getValue(ImageDesc.class);
                        String date = images.date;
                        String desc = images.desc;
                        System.out.println("cheking ");

                        System.out.println("Checking" + postSnapshot.getKey());
                        if (icheckdate(date)) {

                            if (images.target.equals("all")) {
                                System.out.println("Image date passed:" + postSnapshot.getKey());
                                nodenamei.add(postSnapshot.getKey());
                                namei.add(images.desc);
                                categi.add(images.maincat + " : " + images.subcat);
                                datesi.add(images.date);
                                authi.add(images.user);
                                System.out.println("Added" + namei.size());
                                member_namesi = new String[namei.size()];
                                statuesi = new String[namei.size()];
                                timei = new String[namei.size()];
                                authori = new String[authi.size()];
                                nodei = new String[nodenamei.size()];
                                for (int i = 0; i < namei.size(); i++) {


                                    System.out.println("title is " + namei.get(i));
                                    member_namesi[i] = namei.get(i);
                                    statuesi[i] = categi.get(i);
                                    timei[i] = datesi.get(i);
                                    authori[i] = authi.get(i);
                                    nodei[i] = nodenamei.get(i);
                                }

                            } else {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz=false;
                                    System.out.println("Image date passed:" + postSnapshot.getKey());
                                    nodenamei2.add(postSnapshot.getKey());
                                    namei2.add(images.desc);
                                    categi2.add(images.maincat + " : " + images.subcat);
                                    datesi2.add(images.date);
                                    authi2.add(images.user);
                                    System.out.println("Added" + namei2.size());
                                    member_namesi2 = new String[namei2.size()];
                                    statuesi2 = new String[namei2.size()];
                                    timei2 = new String[namei2.size()];
                                    authori2 = new String[authi2.size()];
                                    nodei2 = new String[nodenamei2.size()];
                                    for (int i = 0; i < namei2.size(); i++) {


                                        System.out.println("title is " + namei2.get(i));
                                        member_namesi2[i] = namei2.get(i);
                                        statuesi2[i] = categi2.get(i);
                                        timei2[i] = datesi2.get(i);
                                        authori2[i] = authi2.get(i);
                                        nodei2[i] = nodenamei2.get(i);
                                    }
                                }
                            }


                        }
                    }
                    if (namei.size() > 0) {
                        System.out.println("Row cnt is" + member_namesi.length);
                        for (int i = 0; i < member_namesi.length; i++) {
                            RowItem item = new RowItem(member_namesi[i],
                                    R.drawable.picture, statuesi[i],
                                    timei[i], authori[i]);
                            rowItemsi.add(item);
                            System.out.println("mangatha added" + rowItemsi);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();

                        Internal_Image imgobj = new Internal_Image(rowItemsi);
                        try {

                            List<RowItem> imgcontent;
                            List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/img.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                imgobj = (Internal_Image) ois.readObject();
                                ois.close();
                                imgcontent = imgobj.imgcontent;
                                for (int i = 0; i < rowItemsi.size(); i++) {
                                    for (int j = 0; j < imgcontent.size(); j++) {
                                        if (imgcontent.get(j).getMember_name().equals(rowItemsi.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        imgcontent2.add(rowItemsi.get(i));
                                    }
                                    flag = true;
                                }
                                imgcontent.addAll(imgcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < imgcontent.size(); k++) {
                                    System.out.println("List iteams are" + imgcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/img.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Image(imgcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/img.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(imgobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }
                        for(int i=0;i<rowItemsi.size();i++)
                        {
                            if(rowItemsi.get(i).getAuthor().equals(CurrentUser.user))
                            {
                                rowItemsi.remove(i);
                            }
                        }
                        if(rowItemsi.size()>0) {

                            new Imgnoti(rowItemsi, nodei);
                            Intent intent = new Intent(getBaseContext(), bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Images")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);

                        }
                    }

                    if (namei2.size() > 0) {
                        System.out.println("Row cnt is" + member_namesi2.length);
                        for (int i = 0; i < member_namesi2.length; i++) {
                            RowItem item2 = new RowItem(member_namesi2[i],
                                    R.drawable.picture, statuesi2[i],
                                    timei2[i], authori2[i]);
                            rowItemsi2.add(item2);
                            System.out.println("mangatha added" + rowItemsi2);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();

                        Internal_Image imgobj = new Internal_Image(rowItemsi2);
                        try {

                            List<RowItem> imgcontent;
                            List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/img1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                imgobj = (Internal_Image) ois.readObject();
                                ois.close();
                                imgcontent = imgobj.imgcontent;
                                for (int i = 0; i < rowItemsi2.size(); i++) {
                                    for (int j = 0; j < imgcontent.size(); j++) {
                                        if (imgcontent.get(j).getMember_name().equals(rowItemsi2.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        imgcontent2.add(rowItemsi2.get(i));
                                    }
                                    flag = true;
                                }
                                imgcontent.addAll(imgcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < imgcontent.size(); k++) {
                                    System.out.println("List iteams are" + imgcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Image(imgcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(imgobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }

                        for (int i = 0; i < rowItemsi2.size(); i++) {
                            if (rowItemsi2.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsi2.remove(i);
                            }
                        }
                        if (rowItemsi2.size() > 0) {
                            new Imgnoti2(rowItemsi2, nodei2);
                            Intent intent = new Intent(getBaseContext(), bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Images")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);


                        }

                    }




                }

                @Override
                public void onCancelled (FirebaseError firebaseError){
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });
//audio
            tmp2 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Audios/";
            fb_db = new Firebase(tmp2);
            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contenta = new ArrayList<>();
                    namea = new ArrayList<>();
                    catega = new ArrayList<>();
                    datesa = new ArrayList<>();
                    autha = new ArrayList<String>();
                    nodenamea = new ArrayList<String>();

                    contenta2 = new ArrayList<>();
                    namea2 = new ArrayList<>();
                    catega2 = new ArrayList<>();
                    datesa2 = new ArrayList<>();
                    autha2 = new ArrayList<String>();
                    nodenamea2 = new ArrayList<String>();

                    rowItemsa.clear();
                    rowItemsa2.clear();
                    //        adapter.notifyDataSetChanged();

                    System.out.println("Entering");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Audiodesc audiodesc = postSnapshot.getValue(Audiodesc.class);
                        String date = audiodesc.date;
                        String desc = audiodesc.desc;
                        System.out.println("Checking" + postSnapshot.getKey());
                        if (acheckdate(date)) {
                            if (audiodesc.target.equals("all")) {

                                nodenamea.add(postSnapshot.getKey());

                                System.out.println("Audio date passed:" + postSnapshot.getKey());
                                namea.add(audiodesc.desc);
                                catega.add(audiodesc.maincat + " : " + audiodesc.subcat);
                                datesa.add(audiodesc.date);
                                autha.add(audiodesc.user);

                                System.out.println("Added" + namea.size());
                                member_namesa = new String[namea.size()];
                                statuesa = new String[namea.size()];
                                timea = new String[namea.size()];
                                authora = new String[autha.size()];
                                nodea = new String[nodenamea.size()];
                                for (int i = 0; i < namea.size(); i++) {


                                    System.out.println("title is " + namea.get(i));
                                    member_namesa[i] = namea.get(i);
                                    statuesa[i] = catega.get(i);
                                    timea[i] = datesa.get(i);
                                    authora[i] = autha.get(i);
                                    nodea[i] = nodenamea.get(i);

                                }
                            }else
                            {
                                flagz = false;
                                for (int i = 0; i < audiodesc.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(audiodesc.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz=false;
                                    System.out.println("Image date passed:" + postSnapshot.getKey());
                                    nodenamea2.add(postSnapshot.getKey());
                                    namea2.add(audiodesc.desc);
                                    catega2.add(audiodesc.maincat + " : " + audiodesc.subcat);
                                    datesa2.add(audiodesc.date);
                                    autha2.add(audiodesc.user);
                                    System.out.println("Added" + namea2.size());
                                    member_namesa2 = new String[namea2.size()];
                                    statuesa2 = new String[namea2.size()];
                                    timea2 = new String[namea2.size()];
                                    authora2 = new String[autha2.size()];
                                    nodea2 = new String[nodenamea2.size()];
                                    for (int i = 0; i < namea2.size(); i++) {


                                        System.out.println("title is " + namea2.get(i));
                                        member_namesa2[i] = namea2.get(i);
                                        statuesa2[i] = catega2.get(i);
                                        timea2[i] = datesa2.get(i);
                                        authora2[i] = autha2.get(i);
                                        nodea2[i] = nodenamea2.get(i);
                                    }
                                }
                            }
                        }
                    }

                    if (namea.size() > 0) {
                        System.out.println("Row cnt is" + member_namesa.length);
                        for (int i = 0; i < member_namesa.length; i++) {
                            RowItem item = new RowItem(member_namesa[i],
                                    R.drawable.music, statuesa[i],
                                    timea[i], authora[i]);
                            rowItemsa.add(item);
                            System.out.println("mangatha added" + rowItemsa);
                        }


                        Internal_Audio audioobj = new Internal_Audio(rowItemsa);
                        try {

                            List<RowItem> audiocontent;
                            List<RowItem> audiocontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/aud.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                audioobj = (Internal_Audio) ois.readObject();
                                ois.close();
                                audiocontent = audioobj.audiocontent;
                                for (int i = 0; i < rowItemsa.size(); i++) {
                                    for (int j = 0; j < audiocontent.size(); j++) {
                                        if (audiocontent.get(j).getMember_name().equals(rowItemsa.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        audiocontent2.add(rowItemsa.get(i));
                                    }
                                    flag = true;
                                }
                                audiocontent.addAll(audiocontent2);
                                //      imgcontent.addAll(rowItemsi);

                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/aud.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Audio(audiocontent));
                                oos.close();


                            } else {
                                System.out.println("FILE audio CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/aud.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(audioobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();
                        for (int i = 0; i < rowItemsa.size(); i++) {
                            if (rowItemsa.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsa.remove(i);
                            }
                        }
                        if (rowItemsa.size() > 0) {
                            new Audionoti(rowItemsa, nodea);
                            Intent intent = new Intent(MyService.this, bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Audios")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(2, notification);


                        }
                    }


                    if (namea2.size() > 0) {
                        System.out.println("Row cnt is" + member_namesa2.length);
                        for (int i = 0; i < member_namesa2.length; i++) {
                            RowItem item2 = new RowItem(member_namesa2[i],
                                    R.drawable.picture, statuesa2[i],
                                    timea2[i], authora2[i]);
                            rowItemsa2.add(item2);
                            System.out.println("mangatha added" + rowItemsa2);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();

                        Internal_Audio audobj = new Internal_Audio(rowItemsa2);
                        try {

                            List<RowItem> audcontent;
                            List<RowItem> audcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                audobj = (Internal_Audio) ois.readObject();
                                ois.close();
                                audcontent = audobj.audiocontent;
                                for (int i = 0; i < rowItemsa2.size(); i++) {
                                    for (int j = 0; j < audcontent.size(); j++) {
                                        if (audcontent.get(j).getMember_name().equals(rowItemsa2.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        audcontent2.add(rowItemsa2.get(i));
                                    }
                                    flag = true;
                                }
                                audcontent.addAll(audcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < audcontent.size(); k++) {
                                    System.out.println("List iteams are" + audcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Audio(audcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(audobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }

                        for (int i = 0; i < rowItemsa2.size(); i++) {
                            if (rowItemsa2.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsa2.remove(i);
                            }
                        }
                        if (rowItemsa2.size() > 0) {
                            new Audionoti2(rowItemsa2, nodea2);
                            Intent intent = new Intent(getBaseContext(), bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Audios")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);


                        }
                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });
//video
            tmp3 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Videos/";
            fb_db = new Firebase(tmp3);
            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contentv = new ArrayList<>();
                    namev = new ArrayList<>();
                    categv = new ArrayList<>();
                    datesv = new ArrayList<>();
                    authv = new ArrayList<String>();
                    nodenamev = new ArrayList<String>();

                    contentv2 = new ArrayList<>();
                    namev2 = new ArrayList<>();
                    categv2 = new ArrayList<>();
                    datesv2 = new ArrayList<>();
                    authv2 = new ArrayList<String>();
                    nodenamev2 = new ArrayList<String>();

                    rowItemsv.clear();
                    rowItemsv2.clear();
                    //        adapter.notifyDataSetChanged();

                    System.out.println("Entering");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        VideoDesc images = postSnapshot.getValue(VideoDesc.class);
                        String date = images.date;
                        String desc = images.desc;

                        System.out.println("Checking" + postSnapshot.getKey());
                        if (vcheckdate(date)) {
                            if (images.target.equals("all")) {

                                nodenamev.add(postSnapshot.getKey());

                                System.out.println("Videos date passed:" + postSnapshot.getKey());
                                namev.add(images.desc);
                                categv.add(images.maincat + " : " + images.subcat);
                                datesv.add(images.date);
                                authv.add(images.user);

                                System.out.println("Added" + namev.size());
                                member_namesv = new String[namev.size()];
                                statuesv = new String[namev.size()];
                                timev = new String[namev.size()];
                                nodev = new String[nodenamev.size()];
                                authorv = new String[authv.size()];
                                for (int i = 0; i < namev.size(); i++) {


                                    System.out.println("title is " + namev.get(i));
                                    member_namesv[i] = namev.get(i);
                                    statuesv[i] = categv.get(i);
                                    timev[i] = datesv.get(i);
                                    authorv[i] = authv.get(i);
                                    nodev[i] = nodenamev.get(i);
                                }
                            }else
                            {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz=false;
                                    System.out.println("Image date passed:" + postSnapshot.getKey());
                                    nodenamev2.add(postSnapshot.getKey());
                                    namev2.add(images.desc);
                                    categv2.add(images.maincat + " : " + images.subcat);
                                    datesv2.add(images.date);
                                    authv2.add(images.user);
                                    System.out.println("Added" + namev2.size());
                                    member_namesv2 = new String[namev2.size()];
                                    statuesv2 = new String[namev2.size()];
                                    timev2 = new String[namev2.size()];
                                    authorv2 = new String[authv2.size()];
                                    nodev2 = new String[nodenamev2.size()];
                                    for (int i = 0; i < namev2.size(); i++) {


                                        System.out.println("title is " + namev2.get(i));
                                        member_namesv2[i] = namev2.get(i);
                                        statuesv2[i] = categv2.get(i);
                                        timev2[i] = datesv2.get(i);
                                        authorv2[i] = authv2.get(i);
                                        nodev2[i] = nodenamev2.get(i);
                                    }
                                }
                            }

                        }
                    }

                    if (namev.size() > 0) {
                        System.out.println("Row cnt is" + member_namesv.length);
                        for (int i = 0; i < member_namesv.length; i++) {
                            RowItem item = new RowItem(member_namesv[i],
                                    R.drawable.clip, statuesv[i],
                                    timev[i], authorv[i]);
                            rowItemsv.add(item);
                            System.out.println("mangatha added" + rowItemsv);
                        }


                        Internal_Video videoobj = new Internal_Video(rowItemsv);
                        try {

                            List<RowItem> videocontent;
                            List<RowItem> videocontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/vid.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                videoobj = (Internal_Video) ois.readObject();
                                ois.close();
                                videocontent = videoobj.videocontent;
                                for (int i = 0; i < rowItemsv.size(); i++) {
                                    for (int j = 0; j < videocontent.size(); j++) {
                                        if (videocontent.get(j).getMember_name().equals(rowItemsv.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        videocontent2.add(rowItemsv.get(i));
                                    }
                                    flag = true;
                                }
                                videocontent.addAll(videocontent2);
                                //      imgcontent.addAll(rowItemsi);

                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/vid.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Video(videocontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/vid.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(videoobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();
                        for (int i = 0; i < rowItemsv.size(); i++) {
                            if (rowItemsv.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsv.remove(i);
                            }
                        }
                        if (rowItemsv.size() > 0) {
                            new Videonoti(rowItemsv, nodev);
                            Intent intent = new Intent(MyService.this, bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Videos")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(3, notification);


                        }
                    }

                    if (namev2.size() > 0) {
                        System.out.println("Row cnt is" + member_namesv2.length);
                        for (int i = 0; i < member_namesv2.length; i++) {
                            RowItem item2 = new RowItem(member_namesv2[i],
                                    R.drawable.picture, statuesv2[i],
                                    timev2[i], authorv2[i]);
                            rowItemsv2.add(item2);
                            System.out.println("mangatha added" + rowItemsv2);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();

                        Internal_Video vidobj = new Internal_Video(rowItemsv2);
                        try {

                            List<RowItem> vidcontent;
                            List<RowItem> vidcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                vidobj = (Internal_Video) ois.readObject();
                                ois.close();
                                vidcontent = vidobj.videocontent;
                                for (int i = 0; i < rowItemsv2.size(); i++) {
                                    for (int j = 0; j < vidcontent.size(); j++) {
                                        if (vidcontent.get(j).getMember_name().equals(rowItemsv2.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        vidcontent2.add(vidcontent2.get(i));
                                    }
                                    flag = true;
                                }
                                vidcontent.addAll(vidcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < vidcontent.size(); k++) {
                                    System.out.println("List iteams are" + vidcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Video(vidcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(vidobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }
                        for (int i = 0; i < rowItemsv2.size(); i++) {
                            if (rowItemsv2.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsv2.remove(i);
                            }
                        }
                        if (rowItemsv2.size() > 0) {
                            new Videonoti2(rowItemsv2, nodev2);
                            Intent intent = new Intent(getBaseContext(), bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Videos")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);


                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });
//file
            tmp4 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Files/";
            fb_db = new Firebase(tmp4);
            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contentf = new ArrayList<>();
                    namef = new ArrayList<>();
                    categf = new ArrayList<>();
                    datesf = new ArrayList<>();
                    authf = new ArrayList<String>();
                    nodenamef = new ArrayList<String>();

                    contentf2 = new ArrayList<>();
                    namef2 = new ArrayList<>();
                    categf2 = new ArrayList<>();
                    datesf2 = new ArrayList<>();
                    authf2 = new ArrayList<String>();
                    nodenamef2 = new ArrayList<String>();

                    rowItemsf.clear();
                    rowItemsf2.clear();
                    //        adapter.notifyDataSetChanged();

                    System.out.println("Entering");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        FileDesc fileDesc = postSnapshot.getValue(FileDesc.class);
                        String date = fileDesc.date;
                        String desc = fileDesc.desc;

                        System.out.println("Checking" + postSnapshot.getKey());
                        if (fcheckdate(date)) {

                            if (fileDesc.target.equals("all")) {

                                nodenamef.add(postSnapshot.getKey());

                                System.out.println("Files date passed:" + postSnapshot.getKey());
                                namef.add(fileDesc.desc);
                                categf.add(fileDesc.maincat + " : " + fileDesc.subcat);
                                datesf.add(fileDesc.date);
                                authf.add(fileDesc.user);
                                System.out.println("Added" + namef.size());
                                member_namesf = new String[namef.size()];
                                statuesf = new String[namef.size()];
                                timef = new String[namef.size()];
                                authorf = new String[authf.size()];
                                nodef = new String[nodenamef.size()];
                                for (int i = 0; i < namef.size(); i++) {


                                    System.out.println("title is " + namef.get(i));
                                    member_namesf[i] = namef.get(i);
                                    statuesf[i] = categf.get(i);
                                    timef[i] = datesf.get(i);
                                    authorf[i] = authf.get(i);
                                    nodef[i] = nodenamef.get(i);
                                }

                            }else
                            {
                                flagz = false;
                                for (int i = 0; i < fileDesc.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(fileDesc.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz=false;
                                    System.out.println("Image date passed:" + postSnapshot.getKey());
                                    nodenamef2.add(postSnapshot.getKey());
                                    namef2.add(fileDesc.desc);
                                    categf2.add(fileDesc.maincat + " : " + fileDesc.subcat);
                                    datesf2.add(fileDesc.date);
                                    authf2.add(fileDesc.user);
                                    System.out.println("Added" + namef2.size());
                                    member_namesf2 = new String[namef2.size()];
                                    statuesf2 = new String[namef2.size()];
                                    timef2 = new String[namef2.size()];
                                    authorf2 = new String[authf2.size()];
                                    nodef2 = new String[nodenamef2.size()];
                                    for (int i = 0; i < namef2.size(); i++) {


                                        System.out.println("title is " + namef2.get(i));
                                        member_namesf2[i] = namef2.get(i);
                                        statuesf2[i] = categf2.get(i);
                                        timef2[i] = datesf2.get(i);
                                        authorf2[i] = authf2.get(i);
                                        nodef2[i] = nodenamef2.get(i);
                                    }
                                }
                            }
                        }
                    }

                    if (namef.size() > 0) {
                        System.out.println("Row cnt is" + member_namesf.length);
                        for (int i = 0; i < member_namesf.length; i++) {
                            RowItem item = new RowItem(member_namesf[i],
                                    R.drawable.files, statuesf[i],
                                    timef[i], authorf[i]);
                            rowItemsf.add(item);
                            System.out.println("mangatha added" + rowItemsf);
                        }


                        Internal_File fileobj = new Internal_File(rowItemsf);
                        try {

                            List<RowItem> filecontent;
                            List<RowItem> filecontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/file.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                fileobj = (Internal_File) ois.readObject();
                                ois.close();
                                filecontent = fileobj.filecontent;
                                for (int i = 0; i < rowItemsf.size(); i++) {
                                    for (int j = 0; j < filecontent.size(); j++) {
                                        if (filecontent.get(j).getMember_name().equals(rowItemsf.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        filecontent2.add(rowItemsf.get(i));
                                    }
                                    flag = true;
                                }
                                filecontent.addAll(filecontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < filecontent.size(); k++) {
                                    System.out.println("List iteams are" + filecontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/file.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_File(filecontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/file.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(fileobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();
                        for(int i=0;i<rowItemsf.size();i++)
                        {
                            if(rowItemsf.get(i).getAuthor().equals(CurrentUser.user))
                            {
                                rowItemsf.remove(i);
                            }
                        }
                        if(rowItemsf.size()>0) {
                            new Filenoti(rowItemsf, nodef);
                            Intent intent = new Intent(MyService.this, bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Files")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(4, notification);

                        }
                    }



                    if (namef2.size() > 0) {
                        System.out.println("Row cnt is" + member_namesf2.length);
                        for (int i = 0; i < member_namesf2.length; i++) {
                            RowItem item2 = new RowItem(member_namesf2[i],
                                    R.drawable.picture, statuesf2[i],
                                    timef2[i], authorf2[i]);
                            rowItemsf2.add(item2);
                            System.out.println("mangatha added" + rowItemsf2);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();

                        Internal_File fileobj = new Internal_File(rowItemsf2);
                        try {

                            List<RowItem> filecontent;
                            List<RowItem> filecontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/file1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                fileobj = (Internal_File) ois.readObject();
                                ois.close();
                                filecontent = fileobj.filecontent;
                                for (int i = 0; i < rowItemsf2.size(); i++) {
                                    for (int j = 0; j < filecontent.size(); j++) {
                                        if (filecontent.get(j).getMember_name().equals(rowItemsf2.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        filecontent2.add(rowItemsf2.get(i));
                                    }
                                    flag = true;
                                }
                                filecontent.addAll(filecontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < filecontent.size(); k++) {
                                    System.out.println("List iteams are" + filecontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_File(filecontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(fileobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }

                        for (int i = 0; i < rowItemsf2.size(); i++) {
                            if (rowItemsf2.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemsf2.remove(i);
                            }
                        }
                        if (rowItemsf2.size() > 0) {
                            new Filenoti2(rowItemsf2, nodef2);
                            Intent intent = new Intent(getBaseContext(), bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Files")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);


                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });
//text


            tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/";
            fb_db = new Firebase(tmp5);

            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contentt = new ArrayList<>();
                    namet = new ArrayList<>();
                    categt = new ArrayList<>();
                    datest = new ArrayList<>();
                    autht = new ArrayList<String>();
                    nodenamet = new ArrayList<String>();
                    contentt2 = new ArrayList<>();

                    namet2 = new ArrayList<>();
                    categt2 = new ArrayList<>();
                    datest2 = new ArrayList<>();
                    autht2 = new ArrayList<String>();
                    nodenamet2 = new ArrayList<String>();

                    rowItemst.clear();
                    rowItemst2.clear();
                    //        adapter.notifyDataSetChanged();

                    System.out.println("Entering");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        TextDesc textDesc = postSnapshot.getValue(TextDesc.class);
                        String date = textDesc.date;
                        String desc = textDesc.desc;
                        System.out.println("Checking" + postSnapshot.getKey());
                        if (tcheckdate(date)) {
                            if (textDesc.target.equals("all")) {

                                nodenamet.add(dataSnapshot.getKey());

                                System.out.println("Files date passed:" + postSnapshot.getKey());
                                namet.add(textDesc.desc);
                                categt.add(textDesc.maincat + " : " + textDesc.subcat);
                                datest.add(textDesc.date);
                                autht.add(textDesc.user);
                                contentt.add(textDesc.text);
                                System.out.println("Added" + namet.size());
                                member_namest = new String[namet.size()];
                                statuest = new String[namet.size()];
                                timet = new String[namet.size()];
                                authort = new String[autht.size()];
                                nodet = new String[nodenamet.size()];

                                for (int i = 0; i < namet.size(); i++) {


                                    System.out.println("title is " + namet.get(i));
                                    member_namest[i] = namet.get(i);
                                    statuest[i] = categt.get(i);
                                    timet[i] = datest.get(i);
                                    authort[i] = autht.get(i);
                                    nodet[i] = nodenamet.get(i);

                                }

                            } else {
                                flagz = false;
                                for (int i = 0; i < textDesc.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(textDesc.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz = false;
                                    System.out.println("Image date passed:" + postSnapshot.getKey());
                                    nodenamet2.add(postSnapshot.getKey());
                                    namet2.add(textDesc.desc);
                                    categt2.add(textDesc.maincat + " : " + textDesc.subcat);
                                    datest2.add(textDesc.date);
                                    autht2.add(textDesc.user);
                                    contentt2.add(textDesc.text);
                                    System.out.println("Added" + namet2.size());
                                    member_namest2 = new String[namet2.size()];
                                    statuest2 = new String[namet2.size()];
                                    timet2 = new String[namet2.size()];
                                    authort2 = new String[autht2.size()];
                                    nodet2 = new String[nodenamet2.size()];
                                    for (int i = 0; i < namet2.size(); i++) {


                                        System.out.println("title is " + namet2.get(i));
                                        member_namest2[i] = namet2.get(i);
                                        statuest2[i] = categt2.get(i);
                                        timet2[i] = datest2.get(i);
                                        authort2[i] = autht2.get(i);
                                        nodet2[i] = nodenamet2.get(i);
                                    }
                                }
                            }
                        }
                    }

                    if (namet.size() > 0) {
                        System.out.println("Row cnt is" + member_namest.length);
                        for (int i = 0; i < member_namest.length; i++) {
                            RowItem item = new RowItem(member_namest[i],
                                    R.drawable.doc, statuest[i],
                                    timet[i], authort[i]);
                            rowItemst.add(item);
                            System.out.println("mangatha added" + rowItemst);
                        }


                        Internal_Text textobj = new Internal_Text(rowItemst);
                        try {

                            List<RowItem> textcontent;
                            List<RowItem> textcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/text.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                textobj = (Internal_Text) ois.readObject();
                                ois.close();
                                textcontent = textobj.textcontent;
                                for (int i = 0; i < rowItemst.size(); i++) {
                                    for (int j = 0; j < textcontent.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(rowItemst.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        textcontent2.add(rowItemst.get(i));
                                    }
                                    flag = true;
                                }
                                textcontent.addAll(textcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < textcontent.size(); k++) {
                                    System.out.println("List iteams are" + textcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/text.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Text(textcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/text.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(textobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();
                        for(int i=0;i<rowItemst.size();i++)
                        {
                            if(rowItemst.get(i).getAuthor().equals(CurrentUser.user))
                            {
                                rowItemst.remove(i);
                            }
                        }
                        if(rowItemst.size()>0) {
                            new Textnoti(rowItemst, nodet, contentt);
                            Intent intent = new Intent(MyService.this, bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Texts")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(5, notification);

                        }
                    }

                    if (namet2.size() > 0) {
                        System.out.println("Row cnt is" + member_namest2.length);
                        for (int i = 0; i < member_namest2.length; i++) {
                            RowItem item = new RowItem(member_namest2[i],
                                    R.drawable.doc, statuest2[i],
                                    timet2[i], authort2[i]);
                            rowItemst2.add(item);
                            System.out.println("mangatha added" + rowItemst2);
                        }


                        Internal_Text textobj = new Internal_Text(rowItemst2);
                        try {

                            List<RowItem> textcontent;
                            List<RowItem> textcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/text1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                textobj = (Internal_Text) ois.readObject();
                                ois.close();
                                textcontent = textobj.textcontent;
                                for (int i = 0; i < rowItemst2.size(); i++) {
                                    for (int j = 0; j < textcontent2.size(); j++) {
                                        if (textcontent.get(j).getMember_name().equals(rowItemst2.get(i).getMember_name())) {
                                            flag = false;

                                        }
                                    }
                                    if (flag) {
                                        textcontent2.add(rowItemst2.get(i));
                                    }
                                    flag = true;
                                }
                                textcontent.addAll(textcontent2);
                                //      imgcontent.addAll(rowItemsi);
                                for (int k = 0; k < textcontent.size(); k++) {
                                    System.out.println("List iteams are" + textcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Text(textcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(textobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }


                        //        adapter.notifyDataSetChanged();
                        //  profile_pics.recycle();
                        for (int i = 0; i < rowItemst2.size(); i++) {
                            if (rowItemst2.get(i).getAuthor().equals(CurrentUser.user)) {
                                rowItemst2.remove(i);
                            }
                        }
                        if (rowItemst2.size() > 0) {
                            new Textnoti2(rowItemst2, nodet2, contentt2);
                            Intent intent = new Intent(MyService.this, bluepanther.jiljungjuk.Notification.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent
                                    .getActivity(getApplicationContext(),
                                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            android.app.Notification notification = new android.app.Notification.Builder(MyService.this)
                                    .setTicker("Title")
                                    .setContentTitle("SOUL")
                                    .setContentText("You have new Texts")
                                    .setSmallIcon(R.drawable.soul_logo)
                                    .setContentIntent(pendingIntent).getNotification();

                            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(5, notification);


                        }
                    }


                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }


            });

            return null;
        }
    }
}