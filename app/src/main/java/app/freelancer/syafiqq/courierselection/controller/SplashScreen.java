package app.freelancer.syafiqq.courierselection.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;

import app.freelancer.syafiqq.courierselection.BuildConfig;
import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import timber.log.Timber;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.initializeTimber();

        Timber.i("Best Alternative %s", "Test");

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                final DAOProfile model_data = DAOProfile.getInstance(SplashScreen.this);
                try
                {
                    model_data.openWrite();
                    model_data.close();
                }
                catch(SQLException e)
                {
                    Timber.d("SQLException");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                int secondsDelayed = 2;
                new Handler().postDelayed(new Runnable()
                {
                    public void run()
                    {
                        SplashScreen.super.startActivity(new Intent(SplashScreen.this, Dashboard.class));
                        SplashScreen.super.finish();
                    }
                }, secondsDelayed * 1000);
            }
        }.execute();
    }

    private void initializeTimber()
    {
        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
