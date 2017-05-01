package app.freelancer.syafiqq.courierselection.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.daimajia.swipe.util.Attributes;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.controller.adapter.InvisibleAlternativeRecyclerViewAdapter;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import timber.log.Timber;

public class AlternativeHider extends AppCompatActivity
{
    private List<MAlternative>   alternatives;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_alternative_hider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_alternative_hider_toolbar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setProperties();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item)
    {
        Timber.d("onOptionsItemSelected");

        final int cItem = item.getItemId();
        switch(cItem)
        {
            case android.R.id.home:
                //perhaps use intent if needed but i'm sure there's a specific intent action for up you can use to handle
                AlternativeHider.this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Timber.d("onBackPressed");

        super.finish();
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
        final RecyclerView recyclerView = (RecyclerView) super.findViewById(R.id.content_alternative_hider_recycle_view_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(super.getApplicationContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(super.getApplicationContext(), R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        this.adapter = new InvisibleAlternativeRecyclerViewAdapter(this, this.alternatives);
        ((InvisibleAlternativeRecyclerViewAdapter) this.adapter).setMode(Attributes.Mode.Multiple);
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
                final DAOProfile modelProfile = DAOProfile.getInstance(AlternativeHider.this);
                @NotNull
                final DAOAlternative modelData = DAOAlternative.getInstance(AlternativeHider.this);
                @NotNull
                final MProfile profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
                AlternativeHider.this.resetAlternative();
                AlternativeHider.this.alternatives.addAll(modelData.getByProfileAndActive(profile, false));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                AlternativeHider.this.adapter.notifyDataSetChanged();
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

