package app.freelancer.syafiqq.courierselection.model.method.topsis.accumulator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

import app.freelancer.syafiqq.madm.topsis.core.factory.Accumulator;
import app.freelancer.syafiqq.madm.topsis.core.factory.interfaces.Compressable;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 7:52 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousAccumulator extends Accumulator implements Compressable
{
    private double accumulator;

    public ContinuousAccumulator(double accumulator)
    {
        this.accumulator = accumulator;
    }

    @Override
    public void compress()
    {
        this.setAccumulator(FastMath.sqrt(this.getAccumulator()));
    }

    public double getAccumulator()
    {
        return accumulator;
    }

    public void setAccumulator(double accumulator)
    {
        this.accumulator = accumulator;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof ContinuousAccumulator))
        {
            return false;
        }

        final ContinuousAccumulator that = (ContinuousAccumulator) o;

        return new EqualsBuilder()
                .append(getAccumulator(), that.getAccumulator())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getAccumulator())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("accumulator", accumulator)
                .toString();
    }
}
