package app.freelancer.syafiqq.courierselection.model.database.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.weight.ContinuousWeight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 7:07 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class MWeight extends ContinuousWeightContainer
{
    @NotNull private final MProfile profile;
    private                int      id;

    public MWeight(int id, @NotNull ContinuousWeight fleet, @NotNull ContinuousWeight coverage, @NotNull ContinuousWeight experience, @NotNull ContinuousWeight cost, @NotNull ContinuousWeight time, @NotNull ContinuousWeight packaging, @NotNull MProfile profile)
    {
        super(fleet, coverage, experience, cost, time, packaging);
        this.id = id;
        this.profile = profile;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @NonNull
    public MProfile getProfile()
    {
        return profile;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MWeight))
        {
            return false;
        }

        final MWeight mWeight = (MWeight) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), mWeight.getId())
                .append(getProfile(), mWeight.getProfile())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(getProfile())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("profile", profile)
                .toString();
    }
}
