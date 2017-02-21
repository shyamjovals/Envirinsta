package bluepanther.jiljungjuk.OtherActivities_Grid;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bluepanther.jiljungjuk.NGO_Grid.MainActivity2;
import bluepanther.jiljungjuk.Audiodesc;
import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.Cred_Update;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.FileDesc;
import bluepanther.jiljungjuk.FindTab;
import bluepanther.jiljungjuk.ImageDesc;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TabFragment;
import bluepanther.jiljungjuk.Tabs.Audio;
import bluepanther.jiljungjuk.Tabs.Files;
import bluepanther.jiljungjuk.Tabs.Image;
import bluepanther.jiljungjuk.Tabs.Text;
import bluepanther.jiljungjuk.Tabs.Video;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.TextDesc;
import bluepanther.jiljungjuk.Uploader;
import bluepanther.jiljungjuk.VideoDesc;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class MainActivity3 extends AppCompatActivity{
    Notification notification,notification2,notification3,notification4;

    double progress = 0.0,progress2=0.0,progress3=0.0,progress4 = 0.0;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String selectcat,date;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    private Firebase fb_db2;
    private SpaceNavigationView spaceNavigationView;
    ImageDesc img;
    ArrayList<String> targetmems;
    TextDesc txt;
    String tar;
    Audiodesc aud;
    ArrayList<String>tempmems;
    VideoDesc vid;
    FileDesc fil;
    ProgressBar progressBar;
    List<CharSequence> list = new ArrayList<CharSequence>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otheractivities_main);
        Firebase.setAndroidContext(this);
        fb_db=new Firebase(Base_url);
        progressBar = new ProgressBar(this);
        tar="GROUP";
        if(this.getIntent().getExtras().getBoolean("bool3")) {
            Category.maincat = "Extra-Curricular Activities";
            Category.subcat = "NCC  ";
        }

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.nav_nss) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new Audio()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_ncc) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new Text()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_clubactivities) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new Audio()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_competitions) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new Text()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_prizes) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new Audio()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_donations) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new Text()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_donations) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new Audio()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_fieldtrips) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new Text()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_tours) {
                    System.out.println("PETTA RAP = " + menuItem.toString());
                    selectcat = menuItem.toString();
                    Toast.makeText(MainActivity3.this,""+menuItem,Toast.LENGTH_SHORT).show();
                    TempClass.sharedValue = menuItem.toString();
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new Audio()).commit();

                }
                Category.subcat = selectcat;
                System.out.println("Setting label");
                Intent i = new Intent(MainActivity3.this, MainActivity3.class);
                i.putExtra("bool2", false);
                startActivity(i);
                finish();

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("GROUP", R.drawable.ic_action_group_sv));
        spaceNavigationView.addSpaceItem(new SpaceItem("INDIVIDUAL", R.drawable.ic_action_indi_sv));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                switch (FindTab.curTab)
                {
                    case "Text":
                        System.out.println("Text share");
                        txt= Uploader.getTextDesc();
                        txt.setDesc(Text.tdesc());
                        txt.setText(Text.tcont());
                        txt.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        if(txt.getText().isEmpty())
                        {
                            Toast.makeText(getApplication(),"Enter the Text",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else if( txt.getDesc().isEmpty())
                        {
                            Toast.makeText(getApplication(),"Enter The description", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if(tar.equals("GROUP"))
                        {
                            txt.setTarget("all");
                            txt.setTargetmems(new ArrayList<String>());
                        }
                        else if(tar.equals("INDIVIDUAL"))
                        {
                            txt.setTarget("indi");
                            targetmems=new ArrayList(tempmems);
                            txt.setTargetmems(targetmems);

                        }
                        fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Texts").child(CurrentUser.user+txt.getDate()).setValue(txt);
                        Intent i = new Intent(MainActivity3.this, MainActivity2.class);
                        i.putExtra("bool1", false);
                        startActivity(i);
                        finish();
                        break;
                    case "Image":
                        if(Uploader.istate) {
                            System.out.println("Image share");

                            img = Uploader.getImageDesc();
                            img.setDesc(Image.idesc());
                            img.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                            if (tar.equals("GROUP")) {
                                img.setTarget("all");
                                img.setTargetmems(new ArrayList<String>());
                            } else if (tar.equals("INDIVIDUAL")) {
                                img.setTarget("indi");
                                ArrayList targetmems = new ArrayList(tempmems);
                                img.setTargetmems(targetmems);

                            }
                            final String imagenode = CurrentUser.user + img.getDate();
                            System.out.println("  imagenode  " + imagenode);
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);

                            System.out.println("Storage refference : " + storageReference);

                            final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            final Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext());
                            notificationBuilder.setOngoing(true)
                                    .setContentTitle("Uploading Image")
                                    .setContentText("Progressed (" + (int) progress + " %)")
                                    .setProgress(100, (int) progress, false)
                                    .setSmallIcon(R.drawable.soul_logo);
                            notification = notificationBuilder.build();
                            notificationManager.notify(100, notification);

                            UploadTask up = storageReference.putFile(Uploader.getIuri());
                            up.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    System.out.println("Progress is " + progress);
                                    notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%...)");
                                    notification = notificationBuilder.build();
                                    notificationManager.notify(100, notification);

                                }
                            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(img);

                                    notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                                    notification = notificationBuilder.build();
                                    notificationManager.notify(100, notification);
                                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
                                    Uploader.istate=false;
                                    Intent i2 = new Intent(MainActivity3.this, MainActivity2.class);
                                    i2.putExtra("bool1", false);
                                    startActivity(i2);
                                    finish();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(MainActivity3.this,"No image selected",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "Audio":
                        if(Uploader.astate) {
                            System.out.println("Audio share");
                            aud = Uploader.getAudiodesc();
                            aud.setDesc(Audio.adesc());

                            aud.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                            if (aud.getDesc().isEmpty()) {
                                Toast.makeText(getApplication(), "Enter the Description", Toast.LENGTH_SHORT).show();
                                break;
                            }


                            if (tar.equals("GROUP")) {
                                aud.setTarget("all");
                                aud.setTargetmems(new ArrayList<String>());
                            } else if (tar.equals("INDIVIDUAL")) {
                                aud.setTarget("indi");
                                ArrayList targetmems = new ArrayList(tempmems);
                                aud.setTargetmems(targetmems);

                            }
                            final String audnode = CurrentUser.user + aud.getDate();
                            System.out.println("  audionode  " + audnode);
                            StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode);

                            System.out.println("Storage refference : " + storageReference2);

                            final NotificationManager notificationManager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            final Notification.Builder notificationBuilder2 = new Notification.Builder(getApplicationContext());
                            notificationBuilder2.setOngoing(true)
                                    .setContentTitle("Uploading Audio")
                                    .setContentText("Progressed (" + (int) progress2 + " %)")
                                    .setProgress(100, (int) progress2, false)
                                    .setSmallIcon(R.drawable.soul_logo);
                            notification2 = notificationBuilder2.build();
                            notificationManager2.notify(101, notification2);

                            UploadTask up1 = storageReference2.putFile(Uploader.getAuri());
                            up1.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progress2 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    System.out.println("Progress is " + progress2);
                                    notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%...)");
                                    notification2 = notificationBuilder2.build();
                                    notificationManager2.notify(101, notification2);


                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode).setValue(aud);
                                    notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                                    notification2 = notificationBuilder2.build();
                                    notificationManager2.notify(101, notification2);
                                    Uploader.astate=false;
                                    Toast.makeText(getApplicationContext(), "Audio Uploaded", Toast.LENGTH_LONG).show();
                                    Intent i3 = new Intent(MainActivity3.this, MainActivity2.class);
                                    i3.putExtra("bool1", false);
                                    startActivity(i3);
                                    finish();

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(MainActivity3.this,"No audio selected",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "Video":
                        if(Uploader.vstate) {
                            System.out.println("Video share");
                            vid = Uploader.getVideoDesc();
                            vid.setDesc(Video.vdesc());
                            vid.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                            if (tar.equals("GROUP")) {
                                vid.setTarget("all");
                                vid.setTargetmems(new ArrayList<String>());
                            } else if (tar.equals("INDIVIDUAL")) {
                                vid.setTarget("indi");
                                ArrayList targetmems = new ArrayList(tempmems);
                                vid.setTargetmems(targetmems);

                            }
                            final String vidnode = CurrentUser.user + vid.getDate();
                            System.out.println("  videonode  " + vidnode);
                            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode);

                            System.out.println("Storage refference : " + storageReference3);

                            final NotificationManager notificationManager3 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            final Notification.Builder notificationBuilder3 = new Notification.Builder(getApplicationContext());
                            notificationBuilder3.setOngoing(true)
                                    .setContentTitle("Uploading Video")
                                    .setContentText("Progressed (" + (int) progress3 + " %)")
                                    .setProgress(100, (int) progress3, false)
                                    .setSmallIcon(R.drawable.soul_logo);
                            notification3 = notificationBuilder3.build();
                            notificationManager3.notify(102, notification3);

                            UploadTask up2 = storageReference3.putFile(Uploader.getVuri());
                            up2.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                    progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    System.out.println("Progress is " + progress3);
                                    notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)");
                                    notification3 = notificationBuilder3.build();
                                    notificationManager3.notify(102, notification3);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode).setValue(vid);

                                    progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    System.out.println("Progress is " + progress3);
                                    notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                                    notification3 = notificationBuilder3.build();
                                    notificationManager3.notify(102, notification3);
                                    Uploader.vstate=false;
                                    Toast.makeText(getApplicationContext(), "Video Uploaded", Toast.LENGTH_LONG).show();
                                    Intent i4 = new Intent(MainActivity3.this, MainActivity2.class);
                                    i4.putExtra("bool1", false);
                                    startActivity(i4);
                                    finish();

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(MainActivity3.this,"No video selected",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "File":
                        if(Uploader.fstate) {
                            System.out.println("File share");
                            fil = Uploader.getFileDesc();
                            System.out.println("FDESC " + fil.toString());

                            fil.setDesc(Files.fdesc());

                            System.out.println("FDESC2" + fil.getDesc());

                            fil.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                            if (tar.equals("GROUP")) {
                                fil.setTarget("all");
                                fil.setTargetmems(new ArrayList<String>());
                            } else if (tar.equals("INDIVIDUAL")) {
                                fil.setTarget("indi");
                                ArrayList targetmems = new ArrayList(tempmems);
                                fil.setTargetmems(targetmems);

                            }


                            final String filenode = CurrentUser.user + fil.getDate();
                            System.out.println("  filenode  " + filenode);
                            StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode);

                            System.out.println("Storage refference : " + storageReference4);

                            final NotificationManager notificationManager4 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            final Notification.Builder notificationBuilder4 = new Notification.Builder(getApplicationContext());
                            notificationBuilder4.setOngoing(true)
                                    .setContentTitle("Uploading File")
                                    .setContentText("Progressed (" + (int) progress4 + " %)")
                                    .setProgress(100, (int) progress4, false)
                                    .setSmallIcon(R.drawable.soul_logo);
                            notification4 = notificationBuilder4.build();
                            notificationManager4.notify(104, notification4);

                            UploadTask up3 = storageReference4.putFile(Uploader.getFuri());
                            up3.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progress4 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    System.out.println("Progress is " + progress4);
                                    notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%...)");
                                    notification4 = notificationBuilder4.build();
                                    notificationManager4.notify(104, notification4);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode).setValue(fil);

                                    notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                                    notification4 = notificationBuilder4.build();
                                    notificationManager4.notify(104, notification4);
                                    Uploader.fstate=false;
                                    Toast.makeText(MainActivity3.this, "File Uploaded", Toast.LENGTH_LONG).show();
                                    Intent i5 = new Intent(MainActivity3.this, MainActivity2.class);
                                    i5.putExtra("bool1", false);
                                    startActivity(i5);
                                    finish();

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(MainActivity3.this,"No file selected",Toast.LENGTH_SHORT).show();
                        }

                        break;

                }
                spaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                tar=itemName;
                System.out.println("Inside indi");
                if(tar.equals("INDIVIDUAL")) {
                    list=new ArrayList<CharSequence>();
                    System.out.println("Inside indi");
                    //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                    fb_db2 = new Firebase(Base_url+"Accounts/");
                    fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                                String uname = cred_update.getUsn();
                                list.add(uname);
                            }
                            final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                            final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity3.this);
                            builderDialog.setTitle("Select Contact");
                            int count = dialogList.length;
                            boolean[] is_checked = new boolean[count];

                            // Creating multiple selection by using setMutliChoiceItem method
                            builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton, boolean isChecked) {
                                        }
                                    });

                            builderDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ListView list = ((AlertDialog) dialog).getListView();
                                            tempmems=new ArrayList<String>();
                                            // make selected item in the comma seprated string
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < list.getCount(); i++) {
                                                boolean checked = list.isItemChecked(i);

                                                if (checked) {
                                                    tempmems.add(list.getItemAtPosition(i).toString());
                                                    if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                    stringBuilder.append(list.getItemAtPosition(i));
                                                }
                                            }

                                            if (stringBuilder.toString().trim().equals("")) {
                                                Toast.makeText(MainActivity3.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(MainActivity3.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            builderDialog.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity3.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builderDialog.create();
                            alert.show();

                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }

            }


            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
                tar=itemName;
                if(tar.equals("INDIVIDUAL"))
                {
                    list=new ArrayList<CharSequence>();
                    System.out.println("Inside indi");
                    //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values

                    fb_db2 = new Firebase(Base_url+"Accounts/");
                    fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                            {
                                Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                                String uname = cred_update.getUsn();
                                list.add(uname);
                            }

                            final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                            final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity3.this);
                            builderDialog.setTitle("Select Contact");
                            int count = dialogList.length;
                            boolean[] is_checked = new boolean[count];

                            // Creating multiple selection by using setMutliChoiceItem method
                            builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton, boolean isChecked) {
                                        }
                                    });

                            builderDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ListView list = ((AlertDialog) dialog).getListView();
                                            tempmems=new ArrayList<String>();
                                            // make selected item in the comma seprated string
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < list.getCount(); i++) {
                                                boolean checked = list.isItemChecked(i);

                                                if (checked) {
                                                    tempmems.add(list.getItemAtPosition(i).toString());
                                                    if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                    stringBuilder.append(list.getItemAtPosition(i));
                                                }
                                            }

                                            if (stringBuilder.toString().trim().equals("")) {
                                                Toast.makeText(MainActivity3.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(MainActivity3.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            builderDialog.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity3.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builderDialog.create();
                            alert.show();

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("FIREBASE ERROR OCCOURED");
                        }
                    });

                }
            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(MainActivity3.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity3.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
