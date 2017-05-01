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
import app.freelancer.syafiqq.courierselection.controller.AlternativeHider;
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
 * Date / Time  : 01 May 2017, 10:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class InvisibleAlternativeRecyclerViewAdapter extends RecyclerSwipeAdapter<InvisibleAlternativeRecyclerViewAdapter.SimpleViewHolder>
{
    private final AppCompatActivity  root;
    private final List<MAlternative> dataset;
    private final Observer           onAlternativeDeletion;
    private final Observer           onAlternativeHide;


    public InvisibleAlternativeRecyclerViewAdapter(AppCompatActivity root, List<MAlternative> objects)
    {
        Timber.d("InvisibleAlternativeRecyclerViewAdapter");

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
                            final DAOAlternative modelData = DAOAlternative.getInstance(InvisibleAlternativeRecyclerViewAdapter.this.root.getApplicationContext());
                            modelData.deleteByID(alternative);
                            return null;
                        }

                        @Override
                        protected void onPreExecute()
                        {
                            super.onPreExecute();
                            InvisibleAlternativeRecyclerViewAdapter.this.dataset.remove(alternative);
                            ((AlternativeHider) InvisibleAlternativeRecyclerViewAdapter.this.root).getAdapter().notifyDataSetChanged();
                        }
                    }.execute();
                }
            }
        };

        this.onAlternativeHide = new Observer()
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
                            alternative.setActive(1);
                            @NotNull
                            final DAOAlternative modelData = DAOAlternative.getInstance(InvisibleAlternativeRecyclerViewAdapter.this.root.getApplicationContext());
                            modelData.update(alternative);
                            return null;
                        }

                        @Override
                        protected void onPreExecute()
                        {
                            super.onPreExecute();
                            InvisibleAlternativeRecyclerViewAdapter.this.dataset.remove(alternative);
                            ((AlternativeHider) InvisibleAlternativeRecyclerViewAdapter.this.root).getAdapter().notifyDataSetChanged();
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_alternative_hider_item, parent, false);
        return new SimpleViewHolder(view, parent.getContext());
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position)
    {
        Timber.d("onBindViewHolder");

        final MAlternative alternative = this.dataset.get(position);
        viewHolder.title.setText(alternative.getIdentity().getName());
        viewHolder.pbFleet.setProgress(this.getProgress(alternative.getFleet().getValue(), Fleet.min, Fleet.max));
        viewHolder.pbCoverage.setProgress(this.getProgress(alternative.getCoverage().getValue(), Coverage.min, Coverage.max));
        viewHolder.pbExperience.setProgress(this.getProgress(alternative.getExperience().getValue(), Experience.min, Experience.max));
        viewHolder.pbCost.setProgress(this.getProgress(alternative.getCost().getValue(), Cost.min, Cost.max));
        viewHolder.pbTime.setProgress(this.getProgress(alternative.getTime().getValue(), Time.min, Time.max));
        viewHolder.pbPackaging.setProgress(this.getProgress(alternative.getPackaging().getValue(), Packaging.min, Packaging.max));
        viewHolder.tvFleet.setText(String.valueOf(alternative.getFleet().getValue()));
        viewHolder.tvCoverage.setText(String.valueOf(alternative.getCoverage().getValue()));
        viewHolder.tvExperience.setText(String.valueOf(alternative.getExperience().getValue()));
        viewHolder.tvCost.setText(String.valueOf(alternative.getCost().getValue()));
        viewHolder.tvTime.setText(String.valueOf(alternative.getTime().getValue()));
        viewHolder.tvPackaging.setText(String.valueOf(alternative.getPackaging().getValue()));
        viewHolder.setAlternative(alternative);
        viewHolder.setDeleteAlternativeListener(this.onAlternativeDeletion);
        viewHolder.setUnhideAlternativeListener(this.onAlternativeHide);

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

        return R.id.content_alternative_hider_item_swipelayout_swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        final Context context;

        MAlternative alternative;
        SwipeLayout  swipeLayout;
        TextView     title;
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
        private Observer onAlternativeUnhide;

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

            this.swipeLayout = (SwipeLayout) container.findViewById(R.id.content_alternative_hider_item_swipelayout_swipe);
            this.title = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_alternative_name);
            this.hide = (ImageButton) container.findViewById(R.id.content_alternative_hider_item_imagebutton_hide);
            this.delete = (ImageButton) container.findViewById(R.id.content_alternative_hider_item_imagebutton_delete);
            this.pbFleet = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_fleet);
            this.pbCoverage = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_coverage);
            this.pbExperience = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_experience);
            this.pbCost = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_cost);
            this.pbTime = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_time);
            this.pbPackaging = (ProgressBar) container.findViewById(R.id.content_alternative_hider_item_progressbar_packaging);
            this.tvFleet = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_fleet);
            this.tvCoverage = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_coverage);
            this.tvExperience = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_experience);
            this.tvCost = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_cost);
            this.tvTime = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_time);
            this.tvPackaging = (TextView) container.findViewById(R.id.content_alternative_hider_item_textview_packaging);
        }

        private void registerListener()
        {
            Timber.d("registerListener");

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

            if(this.onAlternativeUnhide != null)
            {
                this.onAlternativeUnhide.update(null, this.alternative);
            }
        }


        public void setDeleteAlternativeListener(Observer deleteAlternativeListener)
        {
            Timber.d("setDeleteAlternativeListener");

            this.onAlternativeDeletion = deleteAlternativeListener;
        }

        public void setUnhideAlternativeListener(Observer unhideAlternativeListener)
        {
            Timber.d("setUnhideAlternativeListener");

            this.onAlternativeUnhide = unhideAlternativeListener;
        }
    }
}


