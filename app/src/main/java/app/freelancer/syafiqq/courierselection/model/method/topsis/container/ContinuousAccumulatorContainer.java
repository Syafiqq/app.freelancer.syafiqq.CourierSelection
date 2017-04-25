package app.freelancer.syafiqq.courierselection.model.method.topsis.container;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.topsis.accumulator.ContinuousAccumulator;
import app.freelancer.syafiqq.madm.topsis.core.factory.AccumulatorContainer;
import app.freelancer.syafiqq.madm.topsis.core.factory.interfaces.Compressable;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 7:53 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousAccumulatorContainer extends AccumulatorContainer implements Compressable
{
    @NotNull private ContinuousAccumulator fleet;
    @NotNull private ContinuousAccumulator coverage;
    @NotNull private ContinuousAccumulator experience;
    @NotNull private ContinuousAccumulator cost;
    @NotNull private ContinuousAccumulator time;
    @NotNull private ContinuousAccumulator packaging;

    public ContinuousAccumulatorContainer(@NotNull ContinuousAccumulator fleet, @NotNull ContinuousAccumulator coverage, @NotNull ContinuousAccumulator experience, @NotNull ContinuousAccumulator cost, @NotNull ContinuousAccumulator time, @NotNull ContinuousAccumulator packaging)
    {
        this.fleet = fleet;
        this.coverage = coverage;
        this.experience = experience;
        this.cost = cost;
        this.time = time;
        this.packaging = packaging;
    }

    @Override
    public void compress()
    {
        this.getFleet().compress();
        this.getCoverage().compress();
        this.getExperience().compress();
        this.getCost().compress();
        this.getTime().compress();
        this.getPackaging().compress();
    }

    @NonNull
    public ContinuousAccumulator getFleet()
    {
        return fleet;
    }

    public void setFleet(@NonNull ContinuousAccumulator fleet)
    {
        this.fleet = fleet;
    }

    @NonNull
    public ContinuousAccumulator getCoverage()
    {
        return coverage;
    }

    public void setCoverage(@NonNull ContinuousAccumulator coverage)
    {
        this.coverage = coverage;
    }

    @NonNull
    public ContinuousAccumulator getExperience()
    {
        return experience;
    }

    public void setExperience(@NonNull ContinuousAccumulator experience)
    {
        this.experience = experience;
    }

    @NonNull
    public ContinuousAccumulator getCost()
    {
        return cost;
    }

    public void setCost(@NonNull ContinuousAccumulator cost)
    {
        this.cost = cost;
    }

    @NonNull
    public ContinuousAccumulator getTime()
    {
        return time;
    }

    public void setTime(@NonNull ContinuousAccumulator time)
    {
        this.time = time;
    }

    @NonNull
    public ContinuousAccumulator getPackaging()
    {
        return packaging;
    }

    public void setPackaging(@NonNull ContinuousAccumulator packaging)
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

        if(!(o instanceof ContinuousAccumulatorContainer))
        {
            return false;
        }

        ContinuousAccumulatorContainer that = (ContinuousAccumulatorContainer) o;

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
