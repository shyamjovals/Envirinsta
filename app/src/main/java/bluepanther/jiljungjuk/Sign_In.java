package bluepanther.jiljungjuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import bluepanther.jiljungjuk.InternalStorage.Loginext;

public class Sign_In extends AppCompatActivity {
    EditText username,password;
    Button signinbut;
    TextView signupbut;
    String usn,pass;
    ProgressDialog progressDialog;
    private static final int CAMERA = 110 , WRITE_EXTERNAL_STORAGE = 111, INTERNET = 112, RECORD_AUDIO = 113;


    private String Base_url = "https://envirinsta.firebaseio.com/Accounts/";
    private Firebase fb_db;
    public Loginext obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        requestPermission();//oncreate


        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        signinbut = (Button)findViewById(R.id.signinbut);
        signupbut = (TextView)findViewById(R.id.signupbut);
        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);

        signupbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_In.this,Sign_Up.class);
                startActivity(intent);
            }
        });

        signinbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usn = username.getText().toString();
                pass = password.getText().toString();
                new MyTask().execute();
            }
        });
        String filei = Environment.getExternalStorageDirectory()+"/Soul/Login/logs.tmp";
        File f = new File(filei);
        if(f.exists()) {
            System.out.println("FILE READING");
            FileInputStream fisi = null;

            try {
                fisi = new FileInputStream(filei);
                ObjectInputStream ois = new ObjectInputStream(fisi);
                obj = (Loginext) ois.readObject();
                ois.close();
                if(obj.islog)
                {


                    new CurrentUser(obj.user.user2,obj.user.pass2,obj.user.sclass2,obj.user.ssec2,obj.user.sidate2,obj.user.sadate2,obj.user.svdate2,obj.user.sfdate2,obj.user.stdate2);
                    Intent intent = new Intent(Sign_In.this, ResideActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    public void requestPermission()
    {
        //request permission
        boolean hasPermissioncamera= (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == MockPackageManager.PERMISSION_GRANTED);
        System.out.println("RES IS "+hasPermissioncamera);
        if (!hasPermissioncamera) {
            System.out.println("NOOB CAMERA");
            ActivityCompat.requestPermissions(Sign_In.this,
                    new String[]{android.Manifest.permission.CAMERA},CAMERA);
        }

        boolean hasPermissionexternal = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == MockPackageManager.PERMISSION_GRANTED);
        System.out.println("RES IS "+hasPermissionexternal);

        if (!hasPermissionexternal) {
            System.out.println("NOOB EXTERNAL");
            ActivityCompat.requestPermissions(Sign_In.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE);
        }

        boolean hasPermissionInternet = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.INTERNET) == MockPackageManager.PERMISSION_GRANTED);
        System.out.println("RES IS "+hasPermissionInternet);

        if (!hasPermissionInternet) {
            System.out.println("NOOB INTERNET");
            ActivityCompat.requestPermissions(Sign_In.this,
                    new String[]{android.Manifest.permission.INTERNET},INTERNET);
        }

        boolean hasPermissionaudio = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.RECORD_AUDIO) == MockPackageManager.PERMISSION_GRANTED);
        System.out.println("RES IS "+hasPermissionaudio);

        if (!hasPermissionaudio) {
            System.out.println("NOOB AUDIO");
            ActivityCompat.requestPermissions(Sign_In.this,
                    new String[]{android.Manifest.permission.RECORD_AUDIO},RECORD_AUDIO);
        }
    }
    
    
    


    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Sign_In.this, "Message", "Logging in...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array

            System.out.println("BGPROCESS");
            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                        String uname = cred_update.getUsn();
                        String password = cred_update.getPass();

                        if((uname.equals(usn))&&(password.equals(pass)))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+ "/Soul/Login");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                // Do something on success
                                try {
                                    String file1 = Environment.getExternalStorageDirectory() + "/Soul/Login/logs.tmp";
                                    new PrintWriter(file1).close();
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new Loginext(new CurrentUser(uname,password,cred_update.cls,cred_update.sec,cred_update.idate,cred_update.adate,cred_update.vdate,cred_update.fdate,cred_update.tdate),true));
                                    oos.close();
                                }
                                catch(Exception e)
                                {
System.out.println("Errror in file"+e);
                                }
                            } else {
                                // Do something else on failure

                            }

                            new CurrentUser(uname,password,cred_update.cls,cred_update.sec,cred_update.idate,cred_update.adate,cred_update.vdate,cred_update.fdate,cred_update.tdate);


                            System.out.println("SUCCESS");
                            Toast.makeText(Sign_In.this,"Login Successful",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(Sign_In.this, ResideActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Sign_In.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }



                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return "SUCCESS";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result)
        {


            progressDialog.dismiss();

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case CAMERA: {
                System.out.println("inside camera");
                if (grantResults.length > 0 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
//                    finish();
                    startActivity(getIntent());
                } else
                {
//                    Toast.makeText(Sign_In.this, "The app was not allowed to get your phone Camera. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case WRITE_EXTERNAL_STORAGE: {
                System.out.println("inside storage");

                if (grantResults.length > 0 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
//                    finish();
                    startActivity(getIntent());
                } else
                {
//                    Toast.makeText(Sign_In.this, "The app was not allowed to get your Internal Storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                break;

            }

            case INTERNET: {
                System.out.println("inside internet");

                if (grantResults.length > 0 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
//                    finish();
                    startActivity(getIntent());
                } else
                {
//                    Toast.makeText(Sign_In.this, "The app was not allowed to access internet. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                break;

            }

            case RECORD_AUDIO: {
                System.out.println("inside audio");

                if (grantResults.length > 0 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED)
                {
//                    Toast.makeText(Sign_In.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
//                    finish();
                    startActivity(getIntent());
                } else
                {
//                    Toast.makeText(Sign_In.this, "The app was not allowed to record audio. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
    
}
