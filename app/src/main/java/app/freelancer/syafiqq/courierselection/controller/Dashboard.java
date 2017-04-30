package app.freelancer.syafiqq.courierselection.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import timber.log.Timber;

public class Dashboard extends AppCompatActivity
{
    private List<MAlternative> alternatives;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setProperties();
        this.populateAlternative();
    }

    private void setProperties()
    {
        this.resetAlternative();
    }

    @Override
    protected void onPostResume()
    {
        this.resetAlternative();
        super.onPostResume();
    }

    private void resetAlternative()
    {
        if(this.alternatives == null)
        {
            this.alternatives = new ArrayList<>();
        }
        this.alternatives.clear();
    }

    private void populateAlternative()
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                @NotNull
                final DAOProfile modelProfile = DAOProfile.getInstance(Dashboard.this);
                @NotNull
                final DAOAlternative modelData = DAOAlternative.getInstance(Dashboard.this);
                @NotNull
                final MProfile profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
                Dashboard.this.resetAlternative();
                Dashboard.this.alternatives.addAll(modelData.getByProfile(profile));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                for(@NotNull final MAlternative alternative : Dashboard.this.alternatives)
                {
                    Timber.d(alternative.toString());
                }
            }
        }.execute();
    }
}
