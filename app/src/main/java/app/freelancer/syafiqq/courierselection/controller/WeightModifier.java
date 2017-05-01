package app.freelancer.syafiqq.courierselection.controller;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOWeight;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MWeight;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import timber.log.Timber;

class WeightObservable extends Observable
{
    private MWeight weight;

    public WeightObservable(MWeight weight)
    {
        super();
        this.weight = weight;
    }

    public void update(double fleet, double coverage, double experience, double cost, double time, double packaging)
    {
        double accumulator = fleet + coverage + experience + cost + time + packaging;
        fleet = fleet / accumulator;
        coverage = coverage / accumulator;
        experience = experience / accumulator;
        cost = cost / accumulator;
        time = time / accumulator;
        packaging = packaging / accumulator;
        this.weight.getFleet().setWeight(fleet);
        this.weight.getCoverage().setWeight(coverage);
        this.weight.getExperience().setWeight(experience);
        this.weight.getCost().setWeight(cost);
        this.weight.getTime().setWeight(time);
        this.weight.getPackaging().setWeight(packaging);
        super.setChanged();
        notifyObservers(this.weight);
    }

    public MWeight getWeight()
    {
        return this.weight;
    }
}

public class WeightModifier extends AppCompatActivity
{

    private FloatingActionButton save;
    private WeightObservable     observable;
    private PieChart             chart;
    private MaterialEditText     fleet;
    private MaterialEditText     coverage;
    private MaterialEditText     experience;
    private MaterialEditText     cost;
    private MaterialEditText     time;
    private MaterialEditText     packaging;
    private Observer             fieldListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_weight_modifier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_weight_modifier_toolbar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.fieldListener = new Observer()
        {
            @Override
            public void update(Observable o, Object arg)
            {
                if(arg instanceof MWeight)
                {
                    WeightModifier.this.setData((MWeight) arg);
                }
            }
        };

        this.setProperties();
        this.setValue();
        this.setListener();
    }

    private void setData(MWeight weight)
    {
        ArrayList<PieEntry> entries = new ArrayList<>(6);

        entries.add(new PieEntry((float) weight.getFleet().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_fleet)));
        entries.add(new PieEntry((float) weight.getCoverage().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_coverage)));
        entries.add(new PieEntry((float) weight.getExperience().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_experience)));
        entries.add(new PieEntry((float) weight.getCost().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_cost)));
        entries.add(new PieEntry((float) weight.getTime().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_time)));
        entries.add(new PieEntry((float) weight.getPackaging().getWeight(), this.getResources().getString(R.string.content_weight_modifier_chart_label_packaging)));
        PieDataSet dataSet = new PieDataSet(entries, "Bobot");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS)
        {
            colors.add(c);
        }

        for(int c : ColorTemplate.JOYFUL_COLORS)
        {
            colors.add(c);
        }

        for(int c : ColorTemplate.COLORFUL_COLORS)
        {
            colors.add(c);
        }

        for(int c : ColorTemplate.LIBERTY_COLORS)
        {
            colors.add(c);
        }

        for(int c : ColorTemplate.PASTEL_COLORS)
        {
            colors.add(c);
        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        this.chart.setData(data);

        // undo all highlights
        this.chart.highlightValues(null);

        this.chart.invalidate();
    }

    private void setProperties()
    {
        Timber.d("setProperties");

        this.chart = (PieChart) findViewById(R.id.content_weight_modifier_piechart_visual);
        this.save = (FloatingActionButton) findViewById(R.id.activity_weight_modifier_fab_save);
        this.fleet = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_fleet);
        this.coverage = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_coverage);
        this.experience = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_experience);
        this.cost = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_cost);
        this.time = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_time);
        this.packaging = (MaterialEditText) findViewById(R.id.content_weight_modifier_materialedittext_packaging);

        this.chart.setUsePercentValues(true);
        this.chart.getDescription().setEnabled(false);
        this.chart.setExtraOffsets(5, 10, 5, 5);

        this.chart.setDragDecelerationFrictionCoef(0.95f);

        this.chart.setTransparentCircleColor(Color.WHITE);
        this.chart.setTransparentCircleAlpha(110);

        this.chart.setHoleRadius(58f);
        this.chart.setTransparentCircleRadius(61f);

        this.chart.setDrawCenterText(false);
        this.chart.setDrawHoleEnabled(false);

        this.chart.setRotationAngle(0);
        this.chart.setRotationEnabled(true);
        this.chart.setHighlightPerTapEnabled(true);

        this.chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        @NotNull
        final Legend l = this.chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(2f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        this.chart.setEntryLabelColor(Color.WHITE);
        this.chart.setEntryLabelTextSize(12f);

        @NotNull
        final TextWatcher watcher = new TextWatcher()
        {
            private final long DELAY = 500;
            private Timer timer = new Timer();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(final Editable s)
            {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                @NotNull
                                final WeightModifier that = WeightModifier.this;
                                that.runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        that.observable.update(
                                                Double.parseDouble(that.fleet.getText().toString()),
                                                Double.parseDouble(that.coverage.getText().toString()),
                                                Double.parseDouble(that.experience.getText().toString()),
                                                Double.parseDouble(that.cost.getText().toString()),
                                                Double.parseDouble(that.time.getText().toString()),
                                                Double.parseDouble(that.packaging.getText().toString()));
                                    }
                                });
                            }
                        },
                        DELAY
                );
            }
        };

        this.fleet.addTextChangedListener(watcher);
        this.coverage.addTextChangedListener(watcher);
        this.experience.addTextChangedListener(watcher);
        this.cost.addTextChangedListener(watcher);
        this.time.addTextChangedListener(watcher);
        this.packaging.addTextChangedListener(watcher);
    }

    private void setValue()
    {
        @NotNull
        final WeightModifier that = WeightModifier.this;
        new AsyncTask<Void, Void, Boolean>()
        {
            @Override
            protected Boolean doInBackground(Void... params)
            {
                @NotNull
                final DAOProfile modelProfile = DAOProfile.getInstance(that);
                @NotNull
                final DAOWeight modelWeight = DAOWeight.getInstance(that);
                @NotNull
                final MProfile profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
                @Nullable
                final MWeight weight = modelWeight.getByProfile(profile);
                if(weight != null)
                {
                    that.observable = new WeightObservable(weight);
                    that.observable.addObserver(that.fieldListener);
                }
                return that.observable != null;
            }

            @Override
            protected void onPostExecute(Boolean success)
            {
                if(success)
                {
                    @NotNull
                    final MWeight weight = that.observable.getWeight();
                    that.setField(that.observable.getWeight());
                    that.observable.update(weight.getFleet().getWeight(), weight.getCoverage().getWeight(), weight.getExperience().getWeight(), weight.getCost().getWeight(), weight.getTime().getWeight(), weight.getPackaging().getWeight());
                }
                super.onPostExecute(success);
            }
        }.execute();

    }

    private void setListener()
    {
        Timber.d("setListener");

        this.save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                @NotNull
                final WeightModifier that = WeightModifier.this;

                new AsyncTask<Void, Void, Boolean>()
                {

                    @Override
                    protected void onPreExecute()
                    {
                        super.onPreExecute();
                    }

                    @Override
                    protected Boolean doInBackground(Void... params)
                    {
                        if(that.observable != null)
                        {
                            @NotNull
                            final DAOWeight modelData = DAOWeight.getInstance(that);
                            modelData.update(that.observable.getWeight());
                            return true;
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean status)
                    {
                        if(status)
                        {
                            Toast.makeText(that, "Update Bobot Berhasil", Toast.LENGTH_SHORT).show();
                        }
                        super.onPostExecute(status);
                    }
                }.execute();
            }
        });
    }

    private void setField(MWeight weight)
    {
        Timber.d("setField");

        this.fleet.setText(String.format(Locale.getDefault(), "%.3f", weight.getFleet().getWeight()));
        this.coverage.setText(String.format(Locale.getDefault(), "%.3f", weight.getCoverage().getWeight()));
        this.experience.setText(String.format(Locale.getDefault(), "%.3f", weight.getExperience().getWeight()));
        this.cost.setText(String.format(Locale.getDefault(), "%.3f", weight.getCost().getWeight()));
        this.time.setText(String.format(Locale.getDefault(), "%.3f", weight.getTime().getWeight()));
        this.packaging.setText(String.format(Locale.getDefault(), "%.3f", weight.getPackaging().getWeight()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Timber.d("onOptionsItemSelected");

        switch(item.getItemId())
        {
            case android.R.id.home:
                //perhaps use intent if needed but i'm sure there's a specific intent action for up you can use to handle
                WeightModifier.this.onBackPressed();
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
}
