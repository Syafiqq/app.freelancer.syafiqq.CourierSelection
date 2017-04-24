package app.freelancer.syafiqq.courierselection.model.method.saw.container;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.saw.profit.ContinuousProfit;
import app.freelancer.syafiqq.madm.saw.core.factory.Alternative;
import app.freelancer.syafiqq.madm.saw.core.factory.ProfitContainer;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 8:47 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousProfitContainer extends ProfitContainer
{
    @NotNull private ContinuousProfit fleet;
    @NotNull private ContinuousProfit coverage;
    @NotNull private ContinuousProfit experience;
    @NotNull private ContinuousProfit cost;
    @NotNull private ContinuousProfit time;
    @NotNull private ContinuousProfit packaging;

    public ContinuousProfitContainer(@NotNull ContinuousProfit fleet, @NotNull ContinuousProfit coverage, @NotNull ContinuousProfit experience, @NotNull ContinuousProfit cost, @NotNull ContinuousProfit time, @NotNull ContinuousProfit packaging)
    {
        this.fleet = fleet;
        this.coverage = coverage;
        this.experience = experience;
        this.cost = cost;
        this.time = time;
        this.packaging = packaging;
    }

    @Override
    public void searchProfits(@NotNull Alternative alternative)
    {
        @NotNull
        final Courier _alternative = (Courier) alternative;
        _alternative.getFleet().searchProfit(this.getFleet());
        _alternative.getCoverage().searchProfit(this.getCoverage());
        _alternative.getExperience().searchProfit(this.getExperience());
        _alternative.getCost().searchProfit(this.getCost());
        _alternative.getTime().searchProfit(this.getTime());
        _alternative.getPackaging().searchProfit(this.getPackaging());
    }

    @NonNull
    public ContinuousProfit getFleet()
    {
        return fleet;
    }

    public void setFleet(@NonNull ContinuousProfit fleet)
    {
        this.fleet = fleet;
    }

    @NonNull
    public ContinuousProfit getCoverage()
    {
        return coverage;
    }

    public void setCoverage(@NonNull ContinuousProfit coverage)
    {
        this.coverage = coverage;
    }

    @NonNull
    public ContinuousProfit getExperience()
    {
        return experience;
    }

    public void setExperience(@NonNull ContinuousProfit experience)
    {
        this.experience = experience;
    }

    @NonNull
    public ContinuousProfit getCost()
    {
        return cost;
    }

    public void setCost(@NonNull ContinuousProfit cost)
    {
        this.cost = cost;
    }

    @NonNull
    public ContinuousProfit getTime()
    {
        return time;
    }

    public void setTime(@NonNull ContinuousProfit time)
    {
        this.time = time;
    }

    @NonNull
    public ContinuousProfit getPackaging()
    {
        return packaging;
    }

    public void setPackaging(@NonNull ContinuousProfit packaging)
    {
        this.packaging = packaging;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof ContinuousProfitContainer))
        {
            return false;
        }

        ContinuousProfitContainer that = (ContinuousProfitContainer) o;

        return new EqualsBuilder()
                .append(getFleet(), that.getFleet())
                .append(getCoverage(), that.getCoverage())
                .append(getExperience(), that.getExperience())
                .append(getCost(), that.getCost())
                .append(getTime(), that.getTime())
                .append(getPackaging(), that.getPackaging())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getFleet())
                .append(getCoverage())
                .append(getExperience())
                .append(getCost())
                .append(getTime())
                .append(getPackaging())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("fleet", fleet)
                .append("coverage", coverage)
                .append("experience", experience)
                .append("cost", cost)
                .append("time", time)
                .append("packaging", packaging)
                .toString();
    }
}
