package bluepanther.jiljungjuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bluepanther.jiljungjuk.Contacts_Grid.CustomAdapter2;

/**
 * Created by SUBASH.M on 11/17/2016.
 */

public class Out_Share extends AppCompatActivity {

    ListView listView;
    Uri imageUri;
    ProgressDialog progressDialog;
    ArrayList<String> sendlist;
    CustomAdapter2 adapter;
    List<RowItem2> shareList = new ArrayList<>();
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db,fb_db2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_share);

        getSupportActionBar().setTitle("Send to");
        Firebase.setAndroidContext(this);
        sendlist = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter2(Out_Share.this, shareList);
        listView.setAdapter(adapter);

        //progressDialog=new ProgressDialog(Out_Share.this);
      //  progressDialog.show(this,"Finding Contacts","Loading");

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        listView.setLongClickable(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int checkedCount = listView.getCheckedItemCount();

                mode.setTitle(checkedCount + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.context_menu2, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                SparseBooleanArray selected = listView.getCheckedItemPositions();
                System.out.println("Share size is" + selected.size());
                if(selected.get(0))
                {
                    final ImageDesc imageDesc;
                    imageDesc = new ImageDesc();
                    imageDesc.setUser(CurrentUser.user);
                    imageDesc.setDate(date);
                    imageDesc.setDesc("Shared Image");
                    imageDesc.setMaincat("Shared");
                    imageDesc.setSubcat("Images");
                    imageDesc.setTarget("all");
                    imageDesc.setTargetmems(new ArrayList<String>());
                    final String imagenode = CurrentUser.user + date;
                    System.out.println("  imagenode  " + imagenode);
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);

                    System.out.println("Storage refference : " + storageReference);

                    UploadTask up = storageReference.putFile(imageUri);
                    up.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fb_db2=new Firebase(Base_url);
                            fb_db2.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(imageDesc);


                            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                else {
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (selected.get(i)) {
                            sendlist.add(shareList.get(i).getMember_name());
                            Toast.makeText(Out_Share.this, shareList.get(i).getMember_name(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        new MyTask().execute();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/".equals(type)) {

                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {

                handleSendImage(intent); // Handle single image being sent
            }
            else if (type.startsWith("audio/")) {

                handleSendAudio(intent); // Handle single audio being sent
            }
            else if (type.startsWith("video")) {

                handleSendVideo(intent); // Handle single video being sent
            }
            else if (type.startsWith("file/")) {

                handleSendFiles(intent); // Handle single file being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            shareList.add(new RowItem2("Group",R.drawable.picture));
            fb_db = new Firebase(Base_url+"Accounts/");
            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                        String uname = cred_update.getUsn();
                        RowItem2 item = new RowItem2(uname, R.drawable.picture);
                        shareList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                  //  progressDialog.dismiss();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
         imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

    }


    void handleSendAudio(Intent intent) {
        Uri audioUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (audioUri != null) {
            // Update UI to reflect audio being shared
        }
    }


    void handleSendVideo(Intent intent) {
        Uri videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (videoUri != null) {
            // Update UI to reflect video being shared
        }
    }


    void handleSendFiles(Intent intent) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
            // Update UI to reflect files being shared
        }
    }
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }

}
