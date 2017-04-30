package app.freelancer.syafiqq.courierselection.model.database.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 26 April 2017, 6:51 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class MProfile
{
    private          int    id;
    @NotNull private String name;

    public MProfile(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public MProfile(@NotNull final MProfile profile)
    {
        this(profile.getId(), profile.getName());
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @NotNull
    public String getName()
    {
        return name;
    }

    public void setName(@NotNull String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MProfile))
        {
            return false;
        }

        final MProfile mProfile = (MProfile) o;

        return new EqualsBuilder()
                .append(getId(), mProfile.getId())
                .append(getName(), mProfile.getName())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
