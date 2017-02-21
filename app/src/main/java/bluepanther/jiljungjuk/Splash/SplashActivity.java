package bluepanther.jiljungjuk.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import bluepanther.jiljungjuk.Sign_In;

/**
 * Created by Hariharsudan on 10/21/2016.
 */

public class SplashActivity extends AppCompatActivity  {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
            Intent intent = new Intent(this, Sign_In.class);
            startActivity(intent);
            finish();



    }







}
