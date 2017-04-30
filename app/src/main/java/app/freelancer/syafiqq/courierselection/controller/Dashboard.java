package app.freelancer.syafiqq.courierselection.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.daimajia.swipe.util.Attributes;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.controller.adapter.RecyclerViewAdapter;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import timber.log.Timber;

public class Dashboard extends AppCompatActivity
{
    private List<MAlternative>   alternatives;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setProperties();
    }

    private void setProperties()
    {
        Timber.d("setProperties");

        this.resetAlternative();
        this.initializeList();
    }

    private void initializeList()
    {
        Timber.d("initializeList");
        final RecyclerView recyclerView = (RecyclerView) super.findViewById(R.id.content_dashboard_recycle_view_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(super.getApplicationContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(super.getApplicationContext(), R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        this.adapter = new RecyclerViewAdapter(this, this.alternatives);
        ((RecyclerViewAdapter) this.adapter).setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void onPostResume()
    {
        Timber.d("onCreate");

        this.resetAlternative();
        this.populateAlternative();
        super.onPostResume();
    }

    private void resetAlternative()
    {
        Timber.d("resetAlternative");

        if(this.alternatives == null)
        {
            this.alternatives = new ArrayList<>();
        }
        this.alternatives.clear();
    }

    private void populateAlternative()
    {
        Timber.d("populateAlternative");

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
                Dashboard.this.alternatives.addAll(modelData.getByProfileAndActive(profile, true));
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
