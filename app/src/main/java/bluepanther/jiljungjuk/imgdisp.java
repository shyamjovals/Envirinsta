package bluepanther.jiljungjuk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class imgdisp extends AppCompatActivity {
    TextView tv;
    String value;
    ImageView imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.imgview);
        System.out.println("in imgdisp");
        imgv=(ImageView)findViewById(R.id.imageView3);
        // Object docs=this.getIntent().getExtras().get("obj");
        //int pos=this.getIntent().getExtras().getInt("pos");
        // Accounts doc=(Accounts)docs;
        //value=doc.imgarr.get(pos);
        Picasso.with(imgdisp.this).load(ImgUri.uri).fit().centerCrop().into(imgv);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int) (width * .85), (int) (height * .7));

    }
}
