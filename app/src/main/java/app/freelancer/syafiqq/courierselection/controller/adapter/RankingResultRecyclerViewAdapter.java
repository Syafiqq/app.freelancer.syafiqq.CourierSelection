package app.freelancer.syafiqq.courierselection.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import app.freelancer.syafiqq.courierselection.R;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.topsis.alternative.Courier;
import timber.log.Timber;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 01 May 2017, 4:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class RankingResultRecyclerViewAdapter extends RecyclerSwipeAdapter<RankingResultRecyclerViewAdapter.SimpleViewHolder>
{
    private final AppCompatActivity root;
    private final List<Courier>     dataset;


    public RankingResultRecyclerViewAdapter(AppCompatActivity root, List<Courier> objects)
    {
        Timber.d("RankingResultRecyclerViewAdapter");

        this.dataset = objects;
        this.root = root;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Timber.d("onCreateViewHolder");

        @NotNull
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_ranking_result_item, parent, false);
        return new SimpleViewHolder(view, parent.getContext());
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position)
    {
        Timber.d("onBindViewHolder");

        final Courier alternative = this.dataset.get(position);
        viewHolder.title.setText(alternative.getIdentity().getName());
        viewHolder.no.setText(this.root.getResources().getText(R.string.word_ranking) + " #" + (position + 1));
        viewHolder.value.setText(String.format(Locale.getDefault(), "%f", alternative.getTotal()));
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

        return R.id.content_ranking_result_item_swipelayout_swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        final Context context;

        Courier     alternative;
        SwipeLayout swipeLayout;
        TextView    title;
        TextView    no;
        TextView    value;
        ProgressBar pbFleet;
        ProgressBar pbCoverage;
        ProgressBar pbExperience;
        ProgressBar pbCost;
        ProgressBar pbTime;
        ProgressBar pbPackaging;
        TextView    tvFleet;
        TextView    tvCoverage;
        TextView    tvExperience;
        TextView    tvCost;
        TextView    tvTime;
        TextView    tvPackaging;

        public SimpleViewHolder(View itemView, final Context context)
        {
            super(itemView);
            Timber.d("SimpleViewHolder");

            this.context = context;
            this.registerView(itemView);
        }

        private void registerView(final View container)
        {
            Timber.d("registerView");

            this.swipeLayout = (SwipeLayout) container.findViewById(R.id.content_ranking_result_item_swipelayout_swipe);
            this.title = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_alternative_name);
            this.no = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_alternative_ranking_no);
            this.value = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_alternative_ranking_val);
            this.pbFleet = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_fleet);
            this.pbCoverage = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_coverage);
            this.pbExperience = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_experience);
            this.pbCost = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_cost);
            this.pbTime = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_time);
            this.pbPackaging = (ProgressBar) container.findViewById(R.id.content_ranking_result_item_progressbar_packaging);
            this.tvFleet = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_fleet);
            this.tvCoverage = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_coverage);
            this.tvExperience = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_experience);
            this.tvCost = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_cost);
            this.tvTime = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_time);
            this.tvPackaging = (TextView) container.findViewById(R.id.content_ranking_result_item_textview_packaging);
        }


        public Courier getAlternative()
        {
            return alternative;
        }

        public void setAlternative(Courier alternative)
        {
            this.alternative = alternative;
        }
    }
}

