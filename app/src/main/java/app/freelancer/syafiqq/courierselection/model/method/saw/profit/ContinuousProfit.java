package app.freelancer.syafiqq.courierselection.model.method.saw.profit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import app.freelancer.syafiqq.madm.saw.core.factory.Profit;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 8:41 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousProfit extends Profit
{
    private double profit;

    public ContinuousProfit(double profit)
    {
        this.profit = profit;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof ContinuousProfit))
        {
            return false;
        }

        final ContinuousProfit that = (ContinuousProfit) o;

        return new EqualsBuilder()
                .append(getProfit(), that.getProfit())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getProfit())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("profit", profit)
                .toString();
    }
}
