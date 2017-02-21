package bluepanther.jiljungjuk.Tabs;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.luseen.spacenavigation.SpaceNavigationView;

import java.io.ByteArrayOutputStream;

import bluepanther.jiljungjuk.Category;
import bluepanther.jiljungjuk.CurrentUser;
import bluepanther.jiljungjuk.Filepath;
import bluepanther.jiljungjuk.ImageDesc;
import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.TempClass;
import bluepanther.jiljungjuk.Uploader;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class Image extends android.support.v4.app.Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    SpaceNavigationView spaceNavigationView;
    String date,desc;
    String fname;
   static EditText descr;
    ImageDesc imageDesc;
    Uri imageUri;
    Uri selectedImageUri = null;
    private Bitmap bitmap;
    ImageView iv;
    ImageView camera,gallery;
    Button upload;
    static TextView textView;
    ProgressDialog progressDialog;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image,container,false);

        textView = (TextView) v.findViewById(R.id.textView);
        String cat = TempClass.sharedValue;
        textView.setText(Category.subcat);
        Firebase.setAndroidContext(getActivity());
        fb_db=new Firebase(Base_url);
        camera = (ImageView) v.findViewById(R.id.camera);
        gallery = (ImageView)v.findViewById(R.id.gallery);
        //upload = (Button)v.findViewById(R.id.button_share);
        descr = (EditText)v.findViewById(R.id.desc);
        iv = (ImageView)v.findViewById(R.id.preview);




        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(
                            Intent.createChooser(gintent, "Select Picture"),
                            PICK_IMAGE);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                System.out.println("date is " + date);
//                desc = descr.getText().toString();
//
//                new MyTask().execute();
//            }
//        });



        return v;
    }
    public static String idesc()
    {
        String desc;
        desc=descr.getText().toString();
        return  desc;
    }
    public static void upmethod()
    {
//        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        System.out.println("date is " + date);
//        desc = descr.getText().toString();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                }else if(resultCode == getActivity().RESULT_CANCELED)
                {
                    Toast.makeText(getActivity(), "Picture was not selected", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getActivity(), "Picture was not selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == getActivity().RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);
                Filepath obj=new Filepath();
                String path=obj.getPaths(getContext(),selectedImageUri);
                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getActivity(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
                new MyTask().execute();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }



    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String image = Base64.encodeToString(b, Base64.DEFAULT);
        System.out.println(image);
        iv.setImageBitmap(bitmap);
        iv.setVisibility(View.VISIBLE);
        iv.setVisibility(View.VISIBLE);

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




            imageDesc = new ImageDesc();
            imageDesc.setUser(CurrentUser.user);
            imageDesc.setDate(date);
            imageDesc.setDesc(desc);
            imageDesc.setMaincat(Category.maincat);
            imageDesc.setSubcat(Category.subcat);



            fname = getFileName(selectedImageUri);

            fname = fname.substring(0, fname.lastIndexOf('.'));
            if(fname.equals(null))
            {
                fname = selectedImageUri.getLastPathSegment();
            }

            System.out.println("FILE IS :"+fname);
            System.out.println("Checks"+CurrentUser.sclass+"and "+CurrentUser.ssec);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(fname).setValue(imageDesc);

            Uploader iuploader = new Uploader();
            iuploader.setIfname(fname);
            iuploader.setImageDesc(imageDesc);
            iuploader.setIuri(selectedImageUri);



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

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }
    }
    public static void setLabel()
    {
        System.out.println("Label set");
        textView.setText(Category.subcat);
    }

}
