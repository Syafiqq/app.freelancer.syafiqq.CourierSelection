package app.freelancer.syafiqq.courierselection.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.swipe.util.Attributes;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.controller.adapter.VisibleAlternativeRecyclerViewAdapter;
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

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu)
    {
        Timber.d("onCreateOptionsMenu");

        super.getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item)
    {
        Timber.d("onOptionsItemSelected");

        final int cItem = item.getItemId();
        switch(cItem)
        {
            case R.id.dashboard_menu_add:
            {
                this.onToolbarAddMenuPressed();
            }
            break;
            case R.id.dashboard_menu_hide:
            {
                this.onToolbarHideMenuPressed();
            }
            break;
            case R.id.dashboard_menu_weight:
            {
                this.onToolbarWeightMenuPressed();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onToolbarAddMenuPressed()
    {
        Timber.d("onToolbarAddMenuPressed");

        @NotNull
        final Intent intent = new Intent(this, AddAlternative.class);
        this.startActivity(intent);
    }

    private void onToolbarHideMenuPressed()
    {
        Timber.d("onToolbarHideMenuPressed");

    }

    private void onToolbarWeightMenuPressed()
    {
        Timber.d("onToolbarWeightMenuPressed");

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

        this.adapter = new VisibleAlternativeRecyclerViewAdapter(this, this.alternatives);
        ((VisibleAlternativeRecyclerViewAdapter) this.adapter).setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void onPostResume()
    {
        Timber.d("onPostResume");

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
                Dashboard.this.adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public RecyclerView.Adapter getAdapter()
    {
        Timber.d("getAdapter");

        return adapter;
    }
}
