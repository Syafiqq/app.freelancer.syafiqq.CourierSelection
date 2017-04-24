package app.freelancer.syafiqq.courierselection.model.method.saw.container;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.weight.ContinuousWeight;
import app.freelancer.syafiqq.madm.saw.core.factory.WeightContainer;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 8:55 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousWeightContainer extends WeightContainer
{
    @NotNull private ContinuousWeight fleet;
    @NotNull private ContinuousWeight coverage;
    @NotNull private ContinuousWeight experience;
    @NotNull private ContinuousWeight cost;
    @NotNull private ContinuousWeight time;
    @NotNull private ContinuousWeight packaging;

    public ContinuousWeightContainer(@NotNull ContinuousWeight fleet, @NotNull ContinuousWeight coverage, @NotNull ContinuousWeight experience, @NotNull ContinuousWeight cost, @NotNull ContinuousWeight time, @NotNull ContinuousWeight packaging)
    {
        this.fleet = fleet;
        this.coverage = coverage;
        this.experience = experience;
        this.cost = cost;
        this.time = time;
        this.packaging = packaging;
    }

    @NonNull
    public ContinuousWeight getFleet()
    {
        return fleet;
    }

    public void setFleet(@NonNull ContinuousWeight fleet)
    {
        this.fleet = fleet;
    }

    @NonNull
    public ContinuousWeight getCoverage()
    {
        return coverage;
    }

    public void setCoverage(@NonNull ContinuousWeight coverage)
    {
        this.coverage = coverage;
    }

    @NonNull
    public ContinuousWeight getExperience()
    {
        return experience;
    }

    public void setExperience(@NonNull ContinuousWeight experience)
    {
        this.experience = experience;
    }

    @NonNull
    public ContinuousWeight getCost()
    {
        return cost;
    }

    public void setCost(@NonNull ContinuousWeight cost)
    {
        this.cost = cost;
    }

    @NonNull
    public ContinuousWeight getTime()
    {
        return time;
    }

    public void setTime(@NonNull ContinuousWeight time)
    {
        this.time = time;
    }

    @NonNull
    public ContinuousWeight getPackaging()
    {
        return packaging;
    }

    public void setPackaging(@NonNull ContinuousWeight packaging)
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

        if(!(o instanceof ContinuousWeightContainer))
        {
            return false;
        }

        ContinuousWeightContainer that = (ContinuousWeightContainer) o;

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
