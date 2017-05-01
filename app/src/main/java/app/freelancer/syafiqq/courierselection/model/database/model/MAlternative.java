package app.freelancer.syafiqq.courierselection.model.database.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.saw.property.Identity;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 7:14 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class MAlternative extends Courier
{
    public static final int NULLID = -1;
    @NotNull private final MProfile profile;
    private                int      id;
    private                int      active;

    public MAlternative(int id, @NotNull Identity identity, @NotNull Fleet fleet, @NotNull Coverage coverage, @NotNull Experience experience, @NotNull Cost cost, @NotNull Time time, @NotNull Packaging packaging, @NotNull MProfile profile, int active)
    {
        super(identity, fleet, coverage, experience, cost, time, packaging);
        this.id = id;
        this.profile = profile;
        this.active = active;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    @NonNull
    public MProfile getProfile()
    {
        return this.profile;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MAlternative))
        {
            return false;
        }

        final MAlternative that = (MAlternative) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), that.getId())
                .append(getActive(), that.getActive())
                .append(getProfile(), that.getProfile())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getProfile())
                .append(getId())
                .append(getActive())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("profile", profile)
                .append("active", active)
                .toString();
    }
}
