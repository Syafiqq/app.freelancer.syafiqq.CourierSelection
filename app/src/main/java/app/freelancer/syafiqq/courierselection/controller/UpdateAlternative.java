package app.freelancer.syafiqq.courierselection.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOProfile;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MProfile;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import timber.log.Timber;

public class UpdateAlternative extends AppCompatActivity
{

    public static String ALTERNATIVE_ID = "alternative_id";
    private FloatingActionButton save;
    private MaterialEditText     name;
    private MaterialSpinner      fleet;
    private MaterialSpinner      coverage;
    private MaterialSpinner      experience;
    private MaterialSpinner      cost;
    private MaterialSpinner      time;
    private MaterialSpinner      packaging;
    private MAlternative         alternative;
    private int                  id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_update_alternative);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_update_alternative_toolbar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = super.getIntent();
        this.id = intent.getIntExtra(ALTERNATIVE_ID, 1);

        this.setProperties();
        this.populateField();
        this.setValue();
        this.setListener();
    }

    private void setProperties()
    {
        Timber.d("setProperties");

        this.name = (MaterialEditText) findViewById(R.id.content_update_alternative_materialedittext_name);
        this.save = (FloatingActionButton) findViewById(R.id.activity_update_alternative_fab_save);
        this.fleet = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_fleet);
        this.coverage = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_coverage);
        this.experience = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_experience);
        this.cost = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_cost);
        this.time = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_time);
        this.packaging = (MaterialSpinner) findViewById(R.id.content_update_alternative_materialspinner_packaging);
    }

    private void populateField()
    {
        Timber.d("populateField");

        this.fleet.setItems(new LinkedList<>(Fleet.spinnerVal.values()));
        this.coverage.setItems(new LinkedList<>(Coverage.spinnerVal.values()));
        this.experience.setItems(new LinkedList<>(Experience.spinnerVal.values()));
        this.cost.setItems(new LinkedList<>(Cost.spinnerVal.values()));
        this.time.setItems(new LinkedList<>(Time.spinnerVal.values()));
        this.packaging.setItems(new LinkedList<>(Packaging.spinnerVal.values()));
        this.resetField();
    }

    private void setValue()
    {
        @NotNull
        final UpdateAlternative that = UpdateAlternative.this;
        new AsyncTask<Void, Void, Boolean>()
        {
            @Override
            protected Boolean doInBackground(Void... params)
            {
                @NotNull
                final DAOProfile modelProfile = DAOProfile.getInstance(that);
                @NotNull
                final MProfile profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
                @NotNull
                final Map<Integer, MProfile> profiles = new LinkedHashMap<>(1);
                profiles.put(profile.getId(), profile);
                @NotNull
                final DAOAlternative modelAlternative = DAOAlternative.getInstance(that);
                that.alternative = modelAlternative.getByID(that.id, profiles);
                return that.alternative != null;
            }

            @Override
            protected void onPostExecute(Boolean success)
            {
                if(success)
                {
                    that.setField(that.alternative);
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
                final UpdateAlternative that = UpdateAlternative.this;

                new AsyncTask<Void, Void, Boolean>()
                {

                    @Override
                    protected void onPreExecute()
                    {
                        Timber.d("Alternative = [%s, %d %d %d %d %d %d]", that.name.getText().toString(), that.fleet.getSelectedIndex(),
                                that.coverage.getSelectedIndex(),
                                that.experience.getSelectedIndex(),
                                that.cost.getSelectedIndex(),
                                that.time.getSelectedIndex(),
                                that.packaging.getSelectedIndex());
                        if(that.alternative != null)
                        {
                            that.alternative.getIdentity().setName(that.name.getText().toString());
                            that.alternative.getFleet().setValue(Fleet.normalize(that.fleet.getSelectedIndex()));
                            that.alternative.getCoverage().setValue(Coverage.normalize(that.coverage.getSelectedIndex()));
                            that.alternative.getExperience().setValue(Experience.normalize(that.experience.getSelectedIndex()));
                            that.alternative.getCost().setValue(Cost.normalize(that.cost.getSelectedIndex()));
                            that.alternative.getTime().setValue(Time.normalize(that.time.getSelectedIndex()));
                            that.alternative.getPackaging().setValue(Packaging.normalize(that.packaging.getSelectedIndex()));
                        }

                        super.onPreExecute();
                    }

                    @Override
                    protected Boolean doInBackground(Void... params)
                    {
                        if(that.alternative != null)
                        {
                            @NotNull
                            final DAOAlternative modelData = DAOAlternative.getInstance(that);
                            modelData.update(that.alternative);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean status)
                    {
                        if(status)
                        {
                            Toast.makeText(that, "Update Alternatif Berhasil", Toast.LENGTH_SHORT).show();
                            that.onBackPressed();
                        }
                        super.onPostExecute(status);
                    }
                }.execute();
            }
        });
    }

    private void resetField()
    {
        Timber.d("resetField");

        this.name.setText("");
        this.fleet.setSelectedIndex(0);
        this.coverage.setSelectedIndex(0);
        this.experience.setSelectedIndex(0);
        this.cost.setSelectedIndex(0);
        this.time.setSelectedIndex(0);
        this.packaging.setSelectedIndex(0);
    }


    private void setField(MAlternative alternative)
    {
        Timber.d("setField");

        this.name.setText(alternative.getIdentity().getName());
        this.fleet.setSelectedIndex(alternative.getFleet().getValue() - Fleet.min);
        this.coverage.setSelectedIndex(alternative.getCoverage().getValue() - Coverage.min);
        this.experience.setSelectedIndex(alternative.getExperience().getValue() - Experience.min);
        this.cost.setSelectedIndex(alternative.getCost().getValue() - Cost.min);
        this.time.setSelectedIndex(alternative.getTime().getValue() - Time.min);
        this.packaging.setSelectedIndex(alternative.getPackaging().getValue() - Packaging.min);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Timber.d("onOptionsItemSelected");

        switch(item.getItemId())
        {
            case android.R.id.home:
                //perhaps use intent if needed but i'm sure there's a specific intent action for up you can use to handle
                UpdateAlternative.this.onBackPressed();
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
