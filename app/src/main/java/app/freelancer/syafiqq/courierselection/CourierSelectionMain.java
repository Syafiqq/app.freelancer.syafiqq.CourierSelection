package app.freelancer.syafiqq.courierselection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

public class CourierSelectionMain extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_selection_main);

        this.initializeTimber();

        Timber.i("Best Alternative %s", "Test");
    }

    private void initializeTimber()
    {
        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
