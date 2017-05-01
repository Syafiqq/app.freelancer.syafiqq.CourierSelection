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
import java.util.LinkedList;
import java.util.List;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.controller.adapter.RankingResultRecyclerViewAdapter;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOWeight;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MWeight;
import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousAccumulatorContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.helper.FactoryHelper;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis.ContinuousWeightContainerAdapter;
import app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis.CourierAdapter;
import app.freelancer.syafiqq.madm.saw.core.factory.Alternative;
import app.freelancer.syafiqq.madm.saw.core.factory.SAW;
import app.freelancer.syafiqq.madm.topsis.core.factory.TOPSIS;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import timber.log.Timber;

public class RankingResult extends AppCompatActivity
{
    private List<Courier>        alternatives;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_ranking_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_ranking_result_toolbar_toolbar);
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
                RankingResult.this.onBackPressed();
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
        final RecyclerView recyclerView = (RecyclerView) super.findViewById(R.id.content_ranking_result_recycle_view_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(super.getApplicationContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(super.getApplicationContext(), R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        this.adapter = new RankingResultRecyclerViewAdapter(this, this.alternatives);
        ((RankingResultRecyclerViewAdapter) this.adapter).setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void onPostResume()
    {
        Timber.d("onPostResume");

        this.resetAlternative();
        this.calculateAlternative();
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

    private void calculateAlternative()
    {
        Timber.d("calculateAlternative");

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                @NotNull
                final DAOProfile modelProfile = DAOProfile.getInstance(RankingResult.this);
                @NotNull
                final DAOAlternative modelData = DAOAlternative.getInstance(RankingResult.this);
                @NotNull
                final DAOWeight weightData = DAOWeight.getInstance(RankingResult.this);
                @NotNull
                final MProfile profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
                @NotNull
                final MWeight weight = weightData.getByProfile(profile);
                @NotNull
                final List<MAlternative> alternatives = modelData.getByProfileAndActive(profile, true);

                final List<Courier> rankedAlternatives = this.calculate(alternatives, weight);
                RankingResult.this.alternatives.addAll(rankedAlternatives);
                return null;
            }

            private List<Courier> calculate(@NotNull List<MAlternative> alternatives, @NotNull MWeight weight)
            {
                @NotNull
                final SAW saw = new SAW();

                for(@NotNull final MAlternative alternative : alternatives)
                {
                    saw.addAlternative(alternative);
                }
                saw.setWeight(weight);

                saw.compile();
                saw.searchProfit();
                saw.calculate();

                @NotNull
                final TOPSIS topsis = new TOPSIS();
                ContinuousAccumulatorContainer a = FactoryHelper.createContinuousAccumulatorContainer(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                topsis.setDecisionMatrixAccumulator(a);
                topsis.setWeight(new ContinuousWeightContainerAdapter((ContinuousWeightContainer) saw.getWeight()));

                for(final Alternative alternative : saw.getAlternatives())
                {
                    topsis.addAlternative(new CourierAdapter((app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier) alternative));
                }

                topsis.calculateWeightedDecisionMatrix();
                topsis.collectProfitAndLoss();
                topsis.collectProfitAndLossDistance();
                topsis.ranking();
                topsis.sort();

                @NotNull
                final List<Courier> rankedAlternatives = new LinkedList<>();
                for(final app.freelancer.syafiqq.madm.topsis.core.factory.Alternative alternative : topsis.getAlternatives())
                {
                    rankedAlternatives.add((Courier) alternative);
                }
                return rankedAlternatives;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                RankingResult.this.adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
}

