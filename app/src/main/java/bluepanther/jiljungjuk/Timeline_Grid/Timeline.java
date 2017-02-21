package bluepanther.jiljungjuk.Timeline_Grid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.ImgUri;
import bluepanther.jiljungjuk.InternalStorage.Internal_Audio;
import bluepanther.jiljungjuk.InternalStorage.Internal_File;
import bluepanther.jiljungjuk.InternalStorage.Internal_Image;
import bluepanther.jiljungjuk.InternalStorage.Internal_Text;
import bluepanther.jiljungjuk.InternalStorage.Internal_Video;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.Contacts_Grid.CustomAdapter;
import bluepanther.jiljungjuk.RowItem;
import bluepanther.jiljungjuk.TextDesc;
import bluepanther.jiljungjuk.imgdisp;
import bluepanther.jiljungjuk.txtdisp;
import bluepanther.jiljungjuk.widgets.MetaballMenu;

import static bluepanther.jiljungjuk.R.id.metaball_menu;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class Timeline extends AppCompatActivity implements AdapterView.OnItemClickListener, MetaballMenu.MetaballMenuClickListener {
    String result;
    String file1;

    ListView mylistview, mylistview2;
    public CustomAdapter adapter, adapter2;
    ProgressDialog progressDialog;
    public String member_names[];
    String seltab = "Text";
    List<RowItem> imgcontent = new ArrayList<>();
    List<RowItem> audcontent = new ArrayList<>();
    List<RowItem> vidcontent = new ArrayList<>();
    List<RowItem> filecontent = new ArrayList<>();
    List<RowItem> textcontent = new ArrayList<>();

    List<RowItem> imgcontent2 = new ArrayList<>();
    List<RowItem> audcontent2 = new ArrayList<>();
    List<RowItem> vidcontent2 = new ArrayList<>();
    List<RowItem> filecontent2 = new ArrayList<>();
    List<RowItem> textcontent2 = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();
    TapBarMenu tapBarMenu;
    ImageView save, sort, filter, share;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);
        mylistview = (ListView) findViewById(R.id.listView);
        mylistview2 = (ListView) findViewById(R.id.listView2);
        getSupportActionBar().setTitle("Timeline");
        ((MetaballMenu) findViewById(metaball_menu)).setMenuClickListener(Timeline.this);
        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);


        final Internal_Image imgobj;
        String filei = Environment.getExternalStorageDirectory() + "/img.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi = null;
        try {
            fisi = new FileInputStream(filei);
            ObjectInputStream ois = new ObjectInputStream(fisi);
            imgobj = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent = imgobj.imgcontent;
            Collections.reverse(imgcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj;
        String filea = Environment.getExternalStorageDirectory() + "/aud.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa = null;
        try {
            fisa = new FileInputStream(filea);
            ObjectInputStream ois = new ObjectInputStream(fisa);
            audobj = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent = audobj.audiocontent;
            Collections.reverse(audcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj;
        String filet = Environment.getExternalStorageDirectory() + "/text.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist = null;
        try {
            fist = new FileInputStream(filet);
            ObjectInputStream ois = new ObjectInputStream(fist);
            txtobj = (Internal_Text) ois.readObject();
            ois.close();
            textcontent = txtobj.textcontent;
            Collections.reverse(textcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj;
        String filev = Environment.getExternalStorageDirectory() + "/vid.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv = null;
        try {
            fisv = new FileInputStream(filev);
            ObjectInputStream ois = new ObjectInputStream(fisv);
            vidobj = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent = vidobj.videocontent;
            Collections.reverse(vidcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj;
        String filef = Environment.getExternalStorageDirectory() + "/file.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf = null;
        try {
            fisf = new FileInputStream(filef);
            ObjectInputStream ois = new ObjectInputStream(fisf);
            fileobj = (Internal_File) ois.readObject();
            ois.close();
            filecontent = fileobj.filecontent;
            Collections.reverse(filecontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Image imgobj2;
        String filei2 = Environment.getExternalStorageDirectory() + "/img1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi2 = null;
        try {
            fisi2 = new FileInputStream(filei2);
            ObjectInputStream ois = new ObjectInputStream(fisi2);
            imgobj2 = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent2 = imgobj2.imgcontent;
            Collections.reverse(imgcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj2;
        String filea2 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa2 = null;
        try {
            fisa2 = new FileInputStream(filea2);
            ObjectInputStream ois = new ObjectInputStream(fisa2);
            audobj2 = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent2 = audobj2.audiocontent;
            Collections.reverse(audcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj2;
        String filet2 = Environment.getExternalStorageDirectory() + "/text1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist2 = null;
        try {
            fist2 = new FileInputStream(filet2);
            ObjectInputStream ois = new ObjectInputStream(fist2);
            txtobj2 = (Internal_Text) ois.readObject();
            ois.close();
            textcontent2 = txtobj2.textcontent;
            Collections.reverse(textcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj2;
        String filev2 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv2 = null;
        try {
            fisv2 = new FileInputStream(filev2);
            ObjectInputStream ois = new ObjectInputStream(fisv2);
            vidobj2 = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent2 = vidobj2.videocontent;
            Collections.reverse(vidcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj2;
        String filef2 = Environment.getExternalStorageDirectory() + "/file1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf2 = null;
        try {
            fisf2 = new FileInputStream(filef2);
            ObjectInputStream ois = new ObjectInputStream(fisf2);
            fileobj2 = (Internal_File) ois.readObject();
            ois.close();
            filecontent2 = fileobj2.filecontent;
            Collections.reverse(filecontent2);


        } catch (Exception e) {
            e.printStackTrace();
        }


        adapter = new CustomAdapter(Timeline.this, textcontent);
        adapter2 = new CustomAdapter(Timeline.this, textcontent2);


        mylistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mylistview.setAdapter(adapter);
        mylistview.setLongClickable(true);
        mylistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mylistview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                                                  @Override
                                                  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                                      // TODO Auto-generated method stub
                                                      return false;
                                                  }

                                                  @Override
                                                  public void onDestroyActionMode(ActionMode mode) {


                                                  }

                                                  @Override
                                                  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                                      mode.getMenuInflater().inflate(R.menu.context_menu, menu);
                                                      return true;
                                                  }

                                                  @Override
                                                  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


                                                      switch (item.getItemId()) {
                                                          case R.id.menu_save:
                                                              Toast.makeText(Timeline.this, item.toString(), Toast.LENGTH_LONG).show();
                                                              String str = "File Saved to Internal Storage";
                                                              SparseBooleanArray selected = mylistview.getCheckedItemPositions();
                                                              System.out.println("Timelien size is" + selected.size() + "and" + selected.get(1));
                                                              switch (seltab) {
                                                                  case "Image":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selected.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Images");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = imgcontent.get(i).getAuthor() + imgcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = imgcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Images/" + ff + ".jpg";
                                                                                      File files = new File(file1);
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }
                                                                      }
                                                                      break;
                                                                  case "Audio":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selected.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Audios");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = audcontent.get(i).getAuthor() + audcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = audcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Audios/" + ff + ".mp3";
                                                                                      File files = new File(file1);
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }
                                                                      }

                                                                      break;
                                                                  case "Video":

                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selected.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Videos");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = vidcontent.get(i).getAuthor() + vidcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = vidcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Videos/" + ff + ".mp4";
                                                                                      File files = new File(file1);
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }
                                                                      }

                                                                      break;
                                                                  case "File":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selected.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Files");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = filecontent.get(i).getAuthor() + filecontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = filecontent.get(i).getMember_name();

                                                                                      final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                                          @Override
                                                                                          public void onSuccess(StorageMetadata storageMetadata) {
                                                                                              System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                                                                              String splitarr[] = storageMetadata.getContentType().split("/");
                                                                                              System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                                                                              final String ftype = storageMetadata.getContentType();


                                                                                              if (ftype.contains("pdf")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "pdf.pdf";
                                                                                              } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";

                                                                                              } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                                                                              } else if (ftype.equals("presentation")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "present.ppt";

                                                                                              } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "excel.xls";

                                                                                              }
                                                                                              final File files = new File(file1);


                                                                                              storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                                  @Override
                                                                                                  public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                                      Toast.makeText(Timeline.this,"File has been sevaed",Toast.LENGTH_SHORT).show();
                                                                                                  }
                                                                                              });



                                                                                          }
                                                                                      });



                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }
                                                                      }
                                                                      break;
                                                              }

                                                              Toast.makeText(Timeline.this, str, Toast.LENGTH_SHORT).show();
                                                              mode.finish();
                                                              return true;
                                                          case R.id.menu_share:
                                                              SparseBooleanArray selecteds = mylistview.getCheckedItemPositions();

                                                              Toast.makeText(Timeline.this,
                                                                      item.toString(), Toast.LENGTH_LONG).show();
                                                              switch (seltab) {

                                                                  case "Image":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selecteds.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = imgcontent.get(i).getAuthor() + imgcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = imgcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".jpg";
                                                                                      final File files = new File(file1);
                                                                                      // final File tf = File.createTempFile("sample","txt");
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                              Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                              Uri screenshotUri = Uri.fromFile(files);

                                                                                              sharingIntent.setType("image/png");
                                                                                              sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                                                                              startActivity(Intent.createChooser(sharingIntent, "Share image using"));

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }


                                                                      }
                                                                      break;
                                                                  case "Audio":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selecteds.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      final String res = audcontent.get(i).getAuthor() + audcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = audcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp3";
                                                                                      final File files = new File(file1);
                                                                                      // final File tf = File.createTempFile("sample","txt");
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                              Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                              Uri screenshotUri = Uri.fromFile(files);

                                                                                              sharingIntent.setType("audio/mp3");
                                                                                              sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                                                                              startActivity(Intent.createChooser(sharingIntent, "Share Audio using"));

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }


                                                                      }
                                                                      break;
                                                                  case "Video":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selecteds.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      progressDialog = new ProgressDialog(Timeline.this);
                                                                                      progressDialog.setTitle("Downloading");
                                                                                      progressDialog.setMessage("Downloading Video");
                                                                                      progressDialog.show();
                                                                                      final String res = vidcontent.get(i).getAuthor() + vidcontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = vidcontent.get(i).getMember_name();

                                                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp4";
                                                                                      final File files = new File(file1);
                                                                                      // final File tf = File.createTempFile("sample","txt");
                                                                                      storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                                                                              Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                              Uri screenshotUri = Uri.fromFile(files);

                                                                                              sharingIntent.setType("video/mp4");
                                                                                              sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

                                                                                              startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
                                                                                              progressDialog.dismiss();

                                                                                          }
                                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                                          @Override
                                                                                          public void onFailure(@NonNull Exception exception) {
                                                                                              // Handle any errors
                                                                                              System.out.println("sad" + exception);
                                                                                          }
                                                                                      });


                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }


                                                                      }
                                                                      break;
                                                                  case "File":
                                                                      for (int i = 0; i < mylistview.getCount(); i++) {
                                                                          if (selecteds.get(i)) {
                                                                              File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                              boolean success = true;
                                                                              if (!folder.exists()) {
                                                                                  success = folder.mkdirs();
                                                                              }
                                                                              if (success) {
                                                                                  // Do something on success
                                                                                  try {
                                                                                      progressDialog = new ProgressDialog(Timeline.this);
                                                                                      progressDialog.setTitle("Downloading");
                                                                                      progressDialog.setMessage("Downloading File");
                                                                                      progressDialog.show();
                                                                                      final String res = filecontent.get(i).getAuthor() + filecontent.get(i).getTime();
                                                                                      System.out.println("Downloading" + res);
                                                                                      final String ff = filecontent.get(i).getMember_name();

                                                                                      final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                                                      System.out.println("Storage refference : " + storageReference);

                                                                                      storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                                          @Override
                                                                                          public void onSuccess(StorageMetadata storageMetadata) {
                                                                                              System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                                                                              String splitarr[] = storageMetadata.getContentType().split("/");
                                                                                              System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                                                                              final String ftype = storageMetadata.getContentType();

                                                                                              if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword")){                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                                                                              } else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml")) {
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
                                                                                              } else if(ftype.contains("pdf")){
                                                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];

                                                                                              }
                                                                                              final File files = new File(file1);

                                                                                              storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                                  @Override
                                                                                                  public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                                      Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                                      if(ftype.contains("pdf")){
                                                                                                          sharingIntent.setDataAndType(Uri.fromFile(files), ftype);
                                                                                                      }else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml"))
                                                                                                      {
                                                                                                          sharingIntent.setDataAndType(Uri.fromFile(files), "text/plain");

                                                                                                      }else if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword"))
                                                                                                      {
                                                                                                          sharingIntent.setDataAndType(Uri.fromFile(files), "application/msword");

                                                                                                      }else if(ftype.equals("presentation"))
                                                                                                      {
                                                                                                          sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");

                                                                                                      }
                                                                                                      else if(ftype.equals("spreadsheet")||ftype.equals("sheet"))
                                                                                                      {
                                                                                                          sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");

                                                                                                      }
                                                                                                      startActivity(Intent.createChooser(sharingIntent, "Share File using"));
                                                                                                      progressDialog.dismiss();



                                                                                                  }
                                                                                              });

                                                                                          }
                                                                                      });



                                                                                  } catch (Exception e) {
                                                                                      System.out.println("Errror in file" + e);
                                                                                  }
                                                                              } else {
                                                                                  // Do something else on failure
                                                                              }
                                                                          }


                                                                      }
                                                              }


                                                              mode.finish();
                                                              return true;
                                                          default:
                                                              return false;
                                                      }
//                Toast.makeText(AndroidListViewActivity.this,item.toString(), Toast.LENGTH_LONG).show();
//                return true;
                                                  }

                                                  @Override
                                                  public void onItemCheckedStateChanged(ActionMode mode, int
                                                          position, long id,
                                                                                        boolean checked) {
                                                      int checkedCount = mylistview.getCheckedItemCount();

                                                      mode.setTitle(checkedCount + " selected");
                                                      //editListAdapter.toggleSelection(position);
                                                  }
                                              }

        );


        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                          {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  if (seltab.equals("Image")) {

                                                      progressDialog = ProgressDialog.show(Timeline.this, "Download", "Downloading Image");

//                    Toast toast =Toast.makeText(getApplicationContext(),"DOWNLOADING IMAGE",Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();


                                                      final String res = imgcontent.get(position).getAuthor() + imgcontent.get(position).getTime();
                                                      System.out.println("Downloading" + res);

                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                      System.out.println("Storage refference : " + storageReference);


                                                      storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                          @Override
                                                          public void onSuccess(Uri uri) {
                                                              System.out.println("NOOB");
                                                              Intent i = new Intent(Timeline.this, imgdisp.class);
                                                              ImgUri.uri = uri;
                                                              progressDialog.dismiss();
                                                              startActivity(i);
//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                          }
                                                      }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception exception) {
                                                              // Handle any errors
                                                              System.out.println("sad" + exception);
                                                          }
                                                      });
                                                  }
                                                  if (seltab.equals("Audio")) {

                                                      progressDialog = ProgressDialog.show(Timeline.this, "Download", "Downloading Audio");

                                                      final String res = audcontent.get(position).getAuthor() + audcontent.get(position).getTime();

                                                      System.out.println("Downloading" + res);

                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                      System.out.println("Storage refference : " + storageReference);


                                                      storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                          @Override
                                                          public void onSuccess(Uri uri) {
                                                              System.out.println("NOOB");
                                                              Intent i = new Intent();
                                                              i.setAction(Intent.ACTION_VIEW);
                                                              i.setDataAndType(uri, "audio/*");
                                                              progressDialog.dismiss();
                                                              startActivity(i);

//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                          }
                                                      }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception exception) {
                                                              // Handle any errors
                                                              System.out.println("sad" + exception);
                                                          }
                                                      });
                                                  }
                                                  if (seltab.equals("Video")) {

                                                      progressDialog = ProgressDialog.show(Timeline.this, "Download", "Downloading Video");

                                                      final String res = vidcontent.get(position).getAuthor() + vidcontent.get(position).getTime();

                                                      System.out.println("Downloading" + res);

                                                      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                      System.out.println("Storage refference : " + storageReference);


                                                      storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                          @Override
                                                          public void onSuccess(Uri uri) {
                                                              System.out.println("NOOB");
                                                              Intent i = new Intent();
                                                              i.setAction(Intent.ACTION_VIEW);
                                                              i.setDataAndType(uri, "video/*");
                                                              progressDialog.dismiss();
                                                              startActivity(i);

//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                          }
                                                      }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception exception) {
                                                              // Handle any errors
                                                              System.out.println("sad" + exception);
                                                          }
                                                      });
                                                  }
                                                  if (seltab.equals("File")) {

                                                      progressDialog = ProgressDialog.show(Timeline.this, "Download", "Downloading File");

                                                      final String res = filecontent.get(position).getAuthor() + filecontent.get(position).getTime();

                                                      System.out.println("Downloading" + res);

                                                      final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                      System.out.println("Storage refference : " + storageReference);


                                                      storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {

                                                          @Override
                                                          public void onSuccess(StorageMetadata storageMetadata) {


                                                              System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                                              String splitarr[] = storageMetadata.getContentType().split("/");
                                                              System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                                              final String ftype = storageMetadata.getContentType();

                                                              if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                                              } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
                                                              } else if (ftype.contains("pdf")) {
                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];

                                                              }
                                                              final File files = new File(file1);

//                                System.out.println("NOOB");
//                                Intent intent = new Intent();
//                                intent.setAction(Intent.ACTION_VIEW);
//                                intent.setDataAndType(uri,"file/*");
//                                startActivity(intent);
                                                              storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                  @Override
                                                                  public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                      // Metadata now contains the metadata for 'images/forest.jpg'
                                                                      progressDialog.dismiss();

                                                                      Intent intent = new Intent(Intent.ACTION_VIEW);
                                                                      if (ftype.contains("pdf")) {
                                                                          intent.setDataAndType(Uri.fromFile(files), ftype);
                                                                      } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                                                          intent.setDataAndType(Uri.fromFile(files), "text/plain");

                                                                      } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                                                          intent.setDataAndType(Uri.fromFile(files), "application/msword");

                                                                      } else if (ftype.equals("presentation")) {
                                                                          intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");

                                                                      } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
                                                                          intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");

                                                                      }

                                                                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                                                      try {
                                                                          startActivity(intent);
                                                                      } catch (Exception e) {
                                                                          System.out.println("EXCEPTION IS " + e);
                                                                          Toast.makeText(Timeline.this, "Invalid File type", Toast.LENGTH_LONG).show();
                                                                          // Instruct the user to install a PDF reader here, or something
                                                                      }

                                                                  }
                                                              }).addOnFailureListener(new OnFailureListener() {
                                                                  @Override
                                                                  public void onFailure(@NonNull Exception exception) {
                                                                      // Uh-oh, an error occurred!
                                                                  }
                                                              });


//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                          }
                                                      }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception exception) {
                                                              // Handle any errors
                                                              System.out.println("sad" + exception);
                                                          }
                                                      });
                                                  }
                                                  if (seltab.equals("Text")) {

                                                      progressDialog = ProgressDialog.show(Timeline.this, "Download", "Downloading Text");

                                                      final String res = textcontent.get(position).getAuthor() + textcontent.get(position).getTime();

                                                      System.out.println("Downloading" + res);
                                                      String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
                                                      fb_db = new Firebase(tmp5);
                                                      fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                                                          @Override
                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                              TextDesc obj = dataSnapshot.getValue(TextDesc.class);
                                                              result = obj.text;
                                                              System.out.println("TXT IS " + result);
                                                              Intent i = new Intent(Timeline.this, txtdisp.class);
                                                              i.putExtra("value", result);
                                                              progressDialog.dismiss();
                                                              startActivity(i);
                                                          }

                                                          @Override
                                                          public void onCancelled(FirebaseError firebaseError) {

                                                          }

                                                      });


                                                  }
                                              }

                                          }

        );


        mylistview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mylistview2.setAdapter(adapter2);
        mylistview2.setLongClickable(true);
        mylistview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mylistview2.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()

                                               {

                                                   @Override
                                                   public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                                       // TODO Auto-generated method stub
                                                       return false;
                                                   }

                                                   @Override
                                                   public void onDestroyActionMode(ActionMode mode) {


                                                   }

                                                   @Override
                                                   public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                                       mode.getMenuInflater().inflate(R.menu.context_menu, menu);
                                                       return true;
                                                   }

                                                   @Override
                                                   public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


                                                       switch (item.getItemId()) {
                                                           case R.id.menu_save:
                                                               Toast.makeText(Timeline.this,
                                                                       item.toString(), Toast.LENGTH_LONG).show();
                                                               String str = "File Saved to Internal Storage";
                                                               SparseBooleanArray selected = mylistview2.getCheckedItemPositions();
                                                               System.out.println("Timelien size is" + selected.size() + "and" + selected.get(1));
                                                               switch (seltab) {
                                                                   case "Image":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selected.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Images/personal/");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = imgcontent2.get(i).getAuthor() + imgcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading2" + res);
                                                                                       final String ff = imgcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Images/personal/" + ff + ".jpg";
                                                                                       File files = new File(file1);
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }
                                                                       }
                                                                       break;
                                                                   case "Audio":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selected.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Audios/personal/");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = audcontent2.get(i).getAuthor() + audcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading2" + res);
                                                                                       final String ff = audcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Audios/personal/" + ff + ".mp3";
                                                                                       File files = new File(file1);
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }
                                                                       }

                                                                       break;
                                                                   case "Video":

                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selected.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Videos/personal/");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = vidcontent2.get(i).getAuthor() + vidcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading2" + res);
                                                                                       final String ff = vidcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Videos/personal/" + ff + ".mp4";
                                                                                       File files = new File(file1);
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }
                                                                       }

                                                                       break;
                                                                   case "File":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selected.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Files");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = filecontent2.get(i).getAuthor() + filecontent2.get(i).getTime();
                                                                                       System.out.println("Downloading" + res);
                                                                                       final String ff = filecontent2.get(i).getMember_name();

                                                                                       final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                                           @Override
                                                                                           public void onSuccess(StorageMetadata storageMetadata) {
                                                                                               System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                                                                               String splitarr[] = storageMetadata.getContentType().split("/");
                                                                                               System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                                                                               final String ftype = storageMetadata.getContentType();


                                                                                               if (ftype.contains("pdf")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "pdf.pdf";
                                                                                               } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";

                                                                                               } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                                                                               } else if (ftype.equals("presentation")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "present.ppt";

                                                                                               } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "excel.xls";

                                                                                               }
                                                                                               final File files = new File(file1);


                                                                                               storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                                   @Override
                                                                                                   public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                                       Toast.makeText(Timeline.this,"File has been sevaed",Toast.LENGTH_SHORT).show();
                                                                                                   }
                                                                                               });



                                                                                           }
                                                                                       });



                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }
                                                                       }
                                                                       break;
                                                               }

                                                               Toast.makeText(Timeline.this, str, Toast.LENGTH_SHORT).show();
                                                               mode.finish();
                                                               return true;
                                                           case R.id.menu_share:


                                                               SparseBooleanArray selecteds = mylistview2.getCheckedItemPositions();

                                                               Toast.makeText(Timeline.this,
                                                                       item.toString(), Toast.LENGTH_LONG).show();
                                                               switch (seltab) {

                                                                   case "Image":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selecteds.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = imgcontent2.get(i).getAuthor() + imgcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading" + res);
                                                                                       final String ff = imgcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".jpg";
                                                                                       final File files = new File(file1);
                                                                                       // final File tf = File.createTempFile("sample","txt");
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                               Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                               Uri screenshotUri = Uri.fromFile(files);

                                                                                               sharingIntent.setType("image/png");
                                                                                               sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                                                                               startActivity(Intent.createChooser(sharingIntent, "Share image using"));

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }


                                                                       }
                                                                       break;
                                                                   case "Audio":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selecteds.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       final String res = audcontent2.get(i).getAuthor() + audcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading" + res);
                                                                                       final String ff = audcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp3";
                                                                                       final File files = new File(file1);
                                                                                       // final File tf = File.createTempFile("sample","txt");
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                                                               Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                               Uri screenshotUri = Uri.fromFile(files);

                                                                                               sharingIntent.setType("audio/mp3");
                                                                                               sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                                                                               startActivity(Intent.createChooser(sharingIntent, "Share Audio using"));

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }


                                                                       }
                                                                       break;
                                                                   case "Video":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selecteds.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       progressDialog = new ProgressDialog(Timeline.this);
                                                                                       progressDialog.setTitle("Downloading");
                                                                                       progressDialog.setMessage("Downloading Video");
                                                                                       progressDialog.show();
                                                                                       final String res = vidcontent2.get(i).getAuthor() + vidcontent2.get(i).getTime();
                                                                                       System.out.println("Downloading" + res);
                                                                                       final String ff = vidcontent2.get(i).getMember_name();

                                                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       String file1 = Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files/" + ff + ".mp4";
                                                                                       final File files = new File(file1);
                                                                                       // final File tf = File.createTempFile("sample","txt");
                                                                                       storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                                                                               Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                               Uri screenshotUri = Uri.fromFile(files);

                                                                                               sharingIntent.setType("video/mp4");
                                                                                               sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

                                                                                               startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
                                                                                               progressDialog.dismiss();

                                                                                           }
                                                                                       }).addOnFailureListener(new OnFailureListener() {
                                                                                           @Override
                                                                                           public void onFailure(@NonNull Exception exception) {
                                                                                               // Handle any errors
                                                                                               System.out.println("sad" + exception);
                                                                                           }
                                                                                       });


                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }


                                                                       }
                                                                       break;
                                                                   case "File":
                                                                       for (int i = 0; i < mylistview2.getCount(); i++) {
                                                                           if (selecteds.get(i)) {
                                                                               File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/Soulshare/Files");
                                                                               boolean success = true;
                                                                               if (!folder.exists()) {
                                                                                   success = folder.mkdirs();
                                                                               }
                                                                               if (success) {
                                                                                   // Do something on success
                                                                                   try {
                                                                                       progressDialog = new ProgressDialog(Timeline.this);
                                                                                       progressDialog.setTitle("Downloading");
                                                                                       progressDialog.setMessage("Downloading File");
                                                                                       progressDialog.show();
                                                                                       final String res = filecontent2.get(i).getAuthor() + filecontent2.get(i).getTime();
                                                                                       System.out.println("Downloading" + res);
                                                                                       final String ff = filecontent2.get(i).getMember_name();

                                                                                       final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                                                       System.out.println("Storage refference : " + storageReference);

                                                                                       storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                                           @Override
                                                                                           public void onSuccess(StorageMetadata storageMetadata) {
                                                                                               System.out.println("Type is" + storageMetadata.getContentType() + "end");
                                                                                               String splitarr[] = storageMetadata.getContentType().split("/");
                                                                                               System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                                                                                               final String ftype = storageMetadata.getContentType();

                                                                                               if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword")){                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                                                                                               } else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml")) {
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
                                                                                               } else if(ftype.contains("pdf")){
                                                                                                   file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];

                                                                                               }
                                                                                               final File files = new File(file1);

                                                                                               storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                                   @Override
                                                                                                   public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                                       Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                                                                       if(ftype.contains("pdf")){
                                                                                                           sharingIntent.setDataAndType(Uri.fromFile(files), ftype);
                                                                                                       }else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml"))
                                                                                                       {
                                                                                                           sharingIntent.setDataAndType(Uri.fromFile(files), "text/plain");

                                                                                                       }else if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword"))
                                                                                                       {
                                                                                                           sharingIntent.setDataAndType(Uri.fromFile(files), "application/msword");

                                                                                                       }else if(ftype.equals("presentation"))
                                                                                                       {
                                                                                                           sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");

                                                                                                       }
                                                                                                       else if(ftype.equals("spreadsheet")||ftype.equals("sheet"))
                                                                                                       {
                                                                                                           sharingIntent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");

                                                                                                       }
                                                                                                       startActivity(Intent.createChooser(sharingIntent, "Share File using"));
                                                                                                       progressDialog.dismiss();



                                                                                                   }
                                                                                               });

                                                                                           }
                                                                                       });



                                                                                   } catch (Exception e) {
                                                                                       System.out.println("Errror in file" + e);
                                                                                   }
                                                                               } else {
                                                                                   // Do something else on failure
                                                                               }
                                                                           }


                                                                       }
                                                               }


                                                               mode.finish();
                                                               return true;
                                                           default:
                                                               return false;
                                                       }

                                                   }

                                                   @Override
                                                   public void onItemCheckedStateChanged(ActionMode mode, int
                                                           position, long id,
                                                                                         boolean checked) {
                                                       int checkedCount = mylistview2.getCheckedItemCount();

                                                       mode.setTitle(checkedCount + " selected");
                                                       //editListAdapter.toggleSelection(position);
                                                   }
                                               }

        );


        Toast.makeText(Timeline.this, "Downloaded", Toast.LENGTH_LONG).

                show();


        mylistview2.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                           {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                   if (seltab.equals("Image")) {

//                    Toast toast =Toast.makeText(getApplicationContext(),"DOWNLOADING IMAGE",Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();


                                                       final String res = imgcontent2.get(position).getAuthor() + imgcontent2.get(position).getTime();
                                                       System.out.println("Downloading" + res);

                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                                                       System.out.println("Storage refference : " + storageReference);


                                                       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri uri) {
                                                               System.out.println("NOOB");
                                                               Intent i = new Intent(Timeline.this, imgdisp.class);
                                                               ImgUri.uri = uri;
                                                               startActivity(i);
//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception exception) {
                                                               // Handle any errors
                                                               System.out.println("sad" + exception);
                                                           }
                                                       });
                                                   }
                                                   if (seltab.equals("Audio")) {

                                                       final String res = audcontent2.get(position).getAuthor() + audcontent2.get(position).getTime();

                                                       System.out.println("Downloading" + res);

                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                                                       System.out.println("Storage refference : " + storageReference);


                                                       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri uri) {
                                                               System.out.println("NOOB");
                                                               Intent i = new Intent();
                                                               i.setAction(Intent.ACTION_VIEW);
                                                               i.setDataAndType(uri, "audio/*");
                                                               startActivity(i);

//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception exception) {
                                                               // Handle any errors
                                                               System.out.println("sad" + exception);
                                                           }
                                                       });
                                                   }
                                                   if (seltab.equals("Video")) {

                                                       final String res = vidcontent2.get(position).getAuthor() + vidcontent2.get(position).getTime();

                                                       System.out.println("Downloading" + res);

                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                                                       System.out.println("Storage refference : " + storageReference);


                                                       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri uri) {
                                                               System.out.println("NOOB");
                                                               Intent i = new Intent();
                                                               i.setAction(Intent.ACTION_VIEW);
                                                               i.setDataAndType(uri, "video/*");
                                                               startActivity(i);

//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception exception) {
                                                               // Handle any errors
                                                               System.out.println("sad" + exception);
                                                           }
                                                       });
                                                   }
                                                   if (seltab.equals("File")) {

                                                       final String res = filecontent2.get(position).getAuthor() + filecontent2.get(position).getTime();

                                                       System.out.println("Downloading" + res);

                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                                                       System.out.println("Storage refference : " + storageReference);


                                                       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri uri) {
                                                               System.out.println("NOOB");
                                                               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
                                                               startActivity(browserIntent);


//                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception exception) {
                                                               // Handle any errors
                                                               System.out.println("sad" + exception);
                                                           }
                                                       });
                                                   }
                                                   if (seltab.equals("Text")) {

                                                       final String res = textcontent2.get(position).getAuthor() + textcontent2.get(position).getTime();

                                                       System.out.println("Downloading" + res);
                                                       String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
                                                       fb_db = new Firebase(tmp5);
                                                       fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                                               TextDesc obj = dataSnapshot.getValue(TextDesc.class);
                                                               result = obj.text;
                                                               System.out.println("TXT IS " + result);
                                                               Intent i = new Intent(Timeline.this, txtdisp.class);
                                                               i.putExtra("value", result);
                                                               startActivity(i);
                                                           }

                                                           @Override
                                                           public void onCancelled(FirebaseError firebaseError) {

                                                           }

                                                       });


                                                   }
                                               }

                                           }

        );


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onClick(View view) {
        System.out.println("Menu clicked");
        switch (view.getId()) {


            case R.id.menuitem1:
                seltab = "Text";
                adapter = new CustomAdapter(Timeline.this, textcontent);
                mylistview.setAdapter(adapter);
                adapter2 = new CustomAdapter(Timeline.this, textcontent2);
                mylistview2.setAdapter(adapter2);
                break;
            case R.id.menuitem2:
                seltab = "Image";
                adapter = new CustomAdapter(Timeline.this, imgcontent);
                mylistview.setAdapter(adapter);
                adapter2 = new CustomAdapter(Timeline.this, imgcontent2);
                mylistview2.setAdapter(adapter2);
                break;
            case R.id.menuitem3:
                seltab = "Audio";
                System.out.println("Audio content:");
                for (int i = 0; i < audcontent.size(); i++) {
                    System.out.println(audcontent.get(i).getMember_name());
                }
                adapter = new CustomAdapter(Timeline.this, audcontent);
                mylistview.setAdapter(adapter);
                adapter2 = new CustomAdapter(Timeline.this, audcontent2);
                mylistview2.setAdapter(adapter2);
                break;
            case R.id.menuitem4:
                seltab = "Video";
                adapter = new CustomAdapter(Timeline.this, vidcontent);
                mylistview.setAdapter(adapter);
                adapter2 = new CustomAdapter(Timeline.this, vidcontent2);
                mylistview2.setAdapter(adapter2);
                break;
            case R.id.menuitem5:
                seltab = "File";
                adapter = new CustomAdapter(Timeline.this, filecontent);
                mylistview.setAdapter(adapter);
                adapter2 = new CustomAdapter(Timeline.this, filecontent2);
                mylistview2.setAdapter(adapter2);
                break;
        }
    }


}
