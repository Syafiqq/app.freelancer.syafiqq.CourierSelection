package app.freelancer.syafiqq.courierselection.model.method.topsis.weight;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import app.freelancer.syafiqq.madm.topsis.core.factory.Weight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 7:47 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousWeight extends Weight
{
    private double weight;

    public ContinuousWeight(double weight)
    {
        this.setWeight(weight);
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof ContinuousWeight))
        {
            return false;
        }

        final ContinuousWeight that = (ContinuousWeight) o;

        return new EqualsBuilder()
                .append(getWeight(), that.getWeight())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getWeight())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("weight", weight)
                .toString();
    }
}
