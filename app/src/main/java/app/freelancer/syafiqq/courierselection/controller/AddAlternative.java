package app.freelancer.syafiqq.courierselection.controller;

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

import java.util.LinkedList;

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
import app.freelancer.syafiqq.courierselection.model.method.saw.property.Identity;
import app.freelancer.syafiqq.courierselection.model.util.SystemSetting;
import timber.log.Timber;

public class AddAlternative extends AppCompatActivity
{

    private FloatingActionButton save;
    private MaterialEditText     name;
    private MaterialSpinner      fleet;
    private MaterialSpinner      coverage;
    private MaterialSpinner      experience;
    private MaterialSpinner      cost;
    private MaterialSpinner      time;
    private MaterialSpinner      packaging;
    private MProfile             profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        setContentView(R.layout.activity_add_alternative);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.setProperties();
        this.populateField();
        this.setValue();
        this.setListener();
    }

    private void setProperties()
    {
        Timber.d("setProperties");

        this.name = (MaterialEditText) findViewById(R.id.content_add_alternative_materialedittext_name);
        this.save = (FloatingActionButton) findViewById(R.id.activity_add_alternative_fab_save);
        this.fleet = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_fleet);
        this.coverage = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_coverage);
        this.experience = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_experience);
        this.cost = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_cost);
        this.time = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_time);
        this.packaging = (MaterialSpinner) findViewById(R.id.content_add_alternative_materialspinner_packaging);
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
        final DAOProfile modelProfile = DAOProfile.getInstance(AddAlternative.this);
        this.profile = modelProfile.getByID(SystemSetting.getInstance().getProfileID());
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
                final AddAlternative that = AddAlternative.this;

                new AsyncTask<Void, Void, Boolean>()
                {
                    MAlternative alternative;

                    @Override
                    protected void onPreExecute()
                    {
                        Timber.d("Alternative = [%s, %d %d %d %d %d %d]", that.name.getText().toString(), that.fleet.getSelectedIndex(),
                                that.coverage.getSelectedIndex(),
                                that.experience.getSelectedIndex(),
                                that.cost.getSelectedIndex(),
                                that.time.getSelectedIndex(),
                                that.packaging.getSelectedIndex());

                        alternative = new MAlternative(MAlternative.NULLID,
                                new Identity(that.name.getText().toString()),
                                new Fleet(Fleet.normalize(that.fleet.getSelectedIndex())),
                                new Coverage(Coverage.normalize(that.coverage.getSelectedIndex())),
                                new Experience(Experience.normalize(that.experience.getSelectedIndex())),
                                new Cost(Cost.normalize(that.cost.getSelectedIndex())),
                                new Time(Time.normalize(that.time.getSelectedIndex())),
                                new Packaging(Packaging.normalize(that.packaging.getSelectedIndex())), that.profile, 1);
                        super.onPreExecute();
                    }

                    @Override
                    protected Boolean doInBackground(Void... params)
                    {
                        if(this.alternative != null)
                        {
                            @NotNull
                            final DAOAlternative modelData = DAOAlternative.getInstance(that);
                            modelData.insert(this.alternative);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean status)
                    {
                        if(status)
                        {
                            Toast.makeText(that, "Tambah Alternatif Berhasil", Toast.LENGTH_SHORT).show();
                            that.resetField();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Timber.d("onOptionsItemSelected");

        switch(item.getItemId())
        {
            case android.R.id.home:
                //perhaps use intent if needed but i'm sure there's a specific intent action for up you can use to handle
                AddAlternative.this.onBackPressed();
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
