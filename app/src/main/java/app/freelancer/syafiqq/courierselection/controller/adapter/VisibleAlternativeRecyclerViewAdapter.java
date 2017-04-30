package app.freelancer.syafiqq.courierselection.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.controller.Dashboard;
import app.freelancer.syafiqq.courierselection.model.database.dao.DAOAlternative;
import app.freelancer.syafiqq.courierselection.model.database.model.MAlternative;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 30 April 2017, 4:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class VisibleAlternativeRecyclerViewAdapter extends RecyclerSwipeAdapter<VisibleAlternativeRecyclerViewAdapter.SimpleViewHolder>
{
    private final AppCompatActivity  root;
    private final List<MAlternative> dataset;
    private final Observer           onAlternativeDeletion;
    private final Observer           onAlternativeUpdate;

    public VisibleAlternativeRecyclerViewAdapter(AppCompatActivity root, List<MAlternative> objects)
    {
        Timber.d("VisibleAlternativeRecyclerViewAdapter");

        this.dataset = objects;
        this.root = root;
        this.onAlternativeDeletion = new Observer()
        {
            @Override
            public void update(Observable o, final Object arg)
            {
                if(arg instanceof MAlternative)
                {
                    @NotNull
                    final MAlternative alternative = (MAlternative) arg;
                    new AsyncTask<Void, Void, Void>()
                    {
                        @Override
                        protected Void doInBackground(Void... params)
                        {
                            @NotNull
                            final DAOAlternative modelData = DAOAlternative.getInstance(VisibleAlternativeRecyclerViewAdapter.this.root.getApplicationContext());
                            modelData.deleteByID(alternative);
                            return null;
                        }

                        @Override
                        protected void onPreExecute()
                        {
                            super.onPreExecute();
                            VisibleAlternativeRecyclerViewAdapter.this.dataset.remove(alternative);
                            ((Dashboard) VisibleAlternativeRecyclerViewAdapter.this.root).getAdapter().notifyDataSetChanged();
                        }
                    }.execute();
                }
            }
        };

        this.onAlternativeUpdate = new Observer()
        {
            @Override
            public void update(Observable o, Object arg)
            {
                if(arg instanceof MAlternative)
                {
                    @NotNull
                    final MAlternative alternative = (MAlternative) arg;
                    new AsyncTask<Void, Void, Void>()
                    {
                        @Override
                        protected Void doInBackground(Void... params)
                        {
                            alternative.setActive(0);
                            @NotNull
                            final DAOAlternative modelData = DAOAlternative.getInstance(VisibleAlternativeRecyclerViewAdapter.this.root.getApplicationContext());
                            modelData.update(alternative);
                            return null;
                        }

                        @Override
                        protected void onPreExecute()
                        {
                            super.onPreExecute();
                            VisibleAlternativeRecyclerViewAdapter.this.dataset.remove(alternative);
                            ((Dashboard) VisibleAlternativeRecyclerViewAdapter.this.root).getAdapter().notifyDataSetChanged();
                        }
                    }.execute();
                }
            }
        };
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Timber.d("onCreateViewHolder");

        @NotNull
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_dashboard_item, parent, false);
        return new SimpleViewHolder(view, parent.getContext());
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position)
    {
        Timber.d("onBindViewHolder");

        final MAlternative medicalRecord = this.dataset.get(position);
        viewHolder.title.setText(medicalRecord.getIdentity().getName());
        viewHolder.pbFleet.setProgress(this.getProgress(medicalRecord.getFleet().getValue(), Fleet.min, Fleet.max));
        viewHolder.pbCoverage.setProgress(this.getProgress(medicalRecord.getCoverage().getValue(), Coverage.min, Coverage.max));
        viewHolder.pbExperience.setProgress(this.getProgress(medicalRecord.getExperience().getValue(), Experience.min, Experience.max));
        viewHolder.pbCost.setProgress(this.getProgress(medicalRecord.getCost().getValue(), Cost.min, Cost.max));
        viewHolder.pbTime.setProgress(this.getProgress(medicalRecord.getTime().getValue(), Time.min, Time.max));
        viewHolder.pbPackaging.setProgress(this.getProgress(medicalRecord.getPackaging().getValue(), Packaging.min, Packaging.max));
        viewHolder.tvFleet.setText(String.valueOf(medicalRecord.getFleet().getValue()));
        viewHolder.tvCoverage.setText(String.valueOf(medicalRecord.getCoverage().getValue()));
        viewHolder.tvExperience.setText(String.valueOf(medicalRecord.getExperience().getValue()));
        viewHolder.tvCost.setText(String.valueOf(medicalRecord.getCost().getValue()));
        viewHolder.tvTime.setText(String.valueOf(medicalRecord.getTime().getValue()));
        viewHolder.tvPackaging.setText(String.valueOf(medicalRecord.getPackaging().getValue()));
        viewHolder.setAlternative(medicalRecord);
        viewHolder.setDeleteAlternativeListener(this.onAlternativeDeletion);
        viewHolder.setUpdateAlternativeListener(this.onAlternativeUpdate);

        mItemManger.bindView(viewHolder.itemView, position);
    }

    private int getProgress(int value, int min, int max)
    {
        return (int) Math.ceil(((value - min) * 100) / (max - min));
    }

    @Override
    public int getItemCount()
    {
        Timber.d("getItemCount");

        return dataset.size();
    }


    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        Timber.d("getSwipeLayoutResourceId");

        return R.id.content_dashboard_item_swipelayout_swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        final Context context;

        MAlternative alternative;
        SwipeLayout  swipeLayout;
        TextView     title;
        ImageButton  edit;
        ImageButton  hide;
        ImageButton  delete;
        ProgressBar  pbFleet;
        ProgressBar  pbCoverage;
        ProgressBar  pbExperience;
        ProgressBar  pbCost;
        ProgressBar  pbTime;
        ProgressBar  pbPackaging;
        TextView     tvFleet;
        TextView     tvCoverage;
        TextView     tvExperience;
        TextView     tvCost;
        TextView     tvTime;
        TextView     tvPackaging;
        private Observer onAlternativeDeletion;
        private Observer onAlternativeUpdate;

        public SimpleViewHolder(View itemView, final Context context)
        {
            super(itemView);
            Timber.d("SimpleViewHolder");

            this.context = context;
            this.registerView(itemView);
            this.registerListener();
        }

        private void registerView(final View container)
        {
            Timber.d("registerView");

            this.swipeLayout = (SwipeLayout) container.findViewById(R.id.content_dashboard_item_swipelayout_swipe);
            this.title = (TextView) container.findViewById(R.id.content_dashboard_item_textview_alternative_name);
            this.edit = (ImageButton) container.findViewById(R.id.content_dashboard_item_imagebutton_edit);
            this.hide = (ImageButton) container.findViewById(R.id.content_dashboard_item_imagebutton_hide);
            this.delete = (ImageButton) container.findViewById(R.id.content_dashboard_item_imagebutton_delete);
            this.pbFleet = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_fleet);
            this.pbCoverage = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_coverage);
            this.pbExperience = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_knowledge);
            this.pbCost = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_cost);
            this.pbTime = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_time);
            this.pbPackaging = (ProgressBar) container.findViewById(R.id.content_dashboard_item_progressbar_packaging);
            this.tvFleet = (TextView) container.findViewById(R.id.content_dashboard_item_textview_fleet);
            this.tvCoverage = (TextView) container.findViewById(R.id.content_dashboard_item_textview_coverage);
            this.tvExperience = (TextView) container.findViewById(R.id.content_dashboard_item_textview_knowledge);
            this.tvCost = (TextView) container.findViewById(R.id.content_dashboard_item_textview_cost);
            this.tvTime = (TextView) container.findViewById(R.id.content_dashboard_item_textview_time);
            this.tvPackaging = (TextView) container.findViewById(R.id.content_dashboard_item_textview_packaging);
        }

        private void registerListener()
        {
            Timber.d("registerListener");

            this.edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SimpleViewHolder.this.onEditClick();
                }
            });
            this.hide.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SimpleViewHolder.this.onHideClick();
                }
            });
            this.delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SimpleViewHolder.this.onDeleteClick();
                }
            });
        }

        public MAlternative getAlternative()
        {
            return alternative;
        }

        public void setAlternative(MAlternative alternative)
        {
            this.alternative = alternative;
        }

        private void onDeleteClick()
        {
            Timber.d("onDeleteClick");

            if(this.onAlternativeDeletion != null)
            {
                this.onAlternativeDeletion.update(null, this.alternative);
            }
        }

        private void onHideClick()
        {
            Timber.d("onHideClick");

            if(this.onAlternativeUpdate != null)
            {
                this.onAlternativeUpdate.update(null, this.alternative);
            }
        }

        private void onEditClick()
        {
            Timber.d("onEditClick");
        }

        public void setDeleteAlternativeListener(Observer onAlternativeDeletion)
        {
            this.onAlternativeDeletion = onAlternativeDeletion;
        }

        public void setUpdateAlternativeListener(Observer updateAlternativeListener)
        {
            this.onAlternativeUpdate = updateAlternativeListener;
        }
    }
}

