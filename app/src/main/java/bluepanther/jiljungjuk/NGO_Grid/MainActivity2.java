package bluepanther.jiljungjuk.NGO_Grid;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.luseen.spacenavigation.SpaceNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import bluepanther.jiljungjuk.Audiodesc;
import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.FileDesc;
import bluepanther.jiljungjuk.ImageDesc;
import bluepanther.jiljungjuk.NGO;
import bluepanther.jiljungjuk.NGO_tab;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.TextDesc;
import bluepanther.jiljungjuk.VideoDesc;
import bluepanther.jiljungjuk.newNGO;

public class MainActivity2 extends AppCompatActivity{

    private static final int PICK_IMAGE = 1;
    Notification notification,notification2,notification3,notification4;
    EditText ngoname,ngoadmin,ngopurpose,ngoinfo,ngouname,ngopass;
    ImageView imageView10;
    String txtNGO,txtAdmin,txtPurpose,txtInfo,txtUsername,txtPassword;
    double progress = 0.0,progress2=0.0,progress3=0.0,progress4 = 0.0;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Dialog login;
    String selectcat,date;
    String ngoimage;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    private Firebase fb_db2;
    private SpaceNavigationView spaceNavigationView;
    ImageDesc img;
    Dialog newngo;
    ArrayList<String> targetmems;
    TextDesc txt;
    String tar,ntxt,npass;
    Audiodesc aud;
    ArrayList<String>tempmems;
    VideoDesc vid;
    FileDesc fil;
    ProgressBar progressBar;
    List<CharSequence> list = new ArrayList<CharSequence>();


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_club,
            R.drawable.ic_action_esch,

    };
    public String str[]={"NGO","Events"};
    public static int int_items = 2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo);
        Firebase.setAndroidContext(this);
        fb_db=new Firebase(Base_url);
        //progressBar = new ProgressBar(this);
        tar="GROUP";
        if(this.getIntent().getExtras().getBoolean("bool2")) {
            Category.maincat = "Administration";
            Category.subcat = "Registration";
    }

        getSupportActionBar().setTitle("NGO");
        getSupportActionBar().show();

        Button signinbut = (Button)findViewById(R.id.signinbut);
        Button signupbut = (Button)findViewById(R.id.signupbut);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        signinbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = new Dialog(MainActivity2.this);
                // Set GUI of login screen
                login.setContentView(R.layout.login_dialog);
                login.setTitle("Login to NGO");

                // Init button of login GUI
                Button btnLogin = (Button) login.findViewById(R.id.login);
                Button btnCancel = (Button) login.findViewById(R.id.cancel);
                final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
                final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);


                // Attached listener for login GUI button
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                        {
                            ntxt = txtUsername.getText().toString();
                            npass = txtPassword.getText().toString();
                            login.dismiss();
                            new MyTask4().execute();
                        }
                        else
                        {
                            Toast.makeText(MainActivity2.this, "Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login.dismiss();
                    }
                });

                // Make dialog box visible.
                login.show();
            }
        });

        signupbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newngo = new Dialog(MainActivity2.this);
                // Set GUI of login screen
                newngo.setContentView(R.layout.new_ngo_dialog);
                newngo.setTitle("Start a new NGO");
                ngoname = (EditText)newngo.findViewById(R.id.txtNGO);
                System.out.println("NGONAME "+ngoname);
                ngoadmin = (EditText)newngo.findViewById(R.id.txtAdmin);
                ngopurpose = (EditText)newngo.findViewById(R.id.txtPurpose);
                ngoinfo = (EditText)newngo.findViewById(R.id.txtInfo);
                ngouname = (EditText)newngo.findViewById(R.id.txtUsername);
                ngopass = (EditText)newngo.findViewById(R.id.txtPassword);
                imageView10 = (ImageView)newngo.findViewById(R.id.imageView10);


                imageView10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_IMAGE);
                    }
                });


                Button btnLogin = (Button) newngo.findViewById(R.id.login);
                Button btnCancel = (Button) newngo.findViewById(R.id.cancel);
//                final EditText txtUsername = (EditText)newngo.findViewById(R.id.txtUsername);
//                final EditText txtPassword = (EditText)newngo.findViewById(R.id.txtPassword);

                // Attached listener for login GUI button
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("NGONAME "+ngoname);
                        txtNGO = ngoname.getText().toString();
                        txtAdmin = ngoadmin.getText().toString();
                        txtPurpose = ngopurpose.getText().toString();
                        txtInfo = ngoinfo.getText().toString();
                        txtUsername = ngouname.getText().toString();
                        txtPassword = ngopass.getText().toString();

                        newngo.dismiss();
                        new MyTask3().execute();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newngo.dismiss();
                    }
                });

                // Make dialog box visible.
                newngo.show();
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("Selected tab is"+tab.getText());
                TempClass.sharedValue=tab.getText().toString();
                System.out.println("After tab select shyam"+TempClass.sharedValue);
                super.onTabSelected(tab);
            }
        });


            }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
       // tabLayout.getTabAt(1).setIcon(tabIcons[1]);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NGO_tab(), "NGO");
       //\ adapter.addFrag(new Events_tab(), "Events");
        viewPager.setAdapter(adapter);


    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView10.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            ngoimage= Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }
    public class MyTask3 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
ArrayList<String>fols=new ArrayList<>();
            fols.add("Dummy Follower");
            newNGO ngoobj = new newNGO();
            ngoobj.setNgoname(txtNGO);
            ngoobj.setNgoadmin(txtAdmin);
            ngoobj.setNgoinfo(txtInfo);
            ngoobj.setNgopurpose(txtPurpose);
            ngoobj.setNgouname(txtUsername);
            ngoobj.setNgopass(txtPassword);

            ngoobj.setFollowers(fols);
            System.out.println("Lol"+CurrentUser.sclass+" "+CurrentUser.ssec);

            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("NGO").child(txtNGO).setValue(ngoobj);
          //  newngo.dismiss();
            return null;
        }
    }
    public class MyTask4 extends AsyncTask<String, Integer, String>
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

                        if((uname.equals(ntxt))&&(password.equals(npass)))
                        {
                            Intent intent = new Intent(MainActivity2.this, NGO.class);
                            intent.putExtra("NGOclass",ngoupdate);
                            startActivity(intent);
                            // Redirect to dashboard / home screen.

                        }
                        else
                        {
                            Toast.makeText(MainActivity2.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }



                    }
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





