package app.freelancer.syafiqq.courierselection.model.method.saw.criterion;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.profit.ContinuousProfit;
import app.freelancer.syafiqq.courierselection.model.method.saw.type.CriterionType;
import app.freelancer.syafiqq.courierselection.model.method.saw.weight.ContinuousWeight;
import app.freelancer.syafiqq.madm.saw.core.factory.Criterion;
import app.freelancer.syafiqq.madm.saw.core.factory.Profit;
import app.freelancer.syafiqq.madm.saw.core.factory.Weight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 9:08 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class Packaging extends Criterion
{
    public static final CriterionType TYPE = CriterionType.BENEFIT;

    private int    value;
    private double normalization;

    public Packaging(int value)
    {
        this.value = value;
        this.normalization = 0.0;
    }

    @Override
    public void searchProfit(@NotNull Profit criterion)
    {
        @NotNull
        final ContinuousProfit _criterion = (ContinuousProfit) criterion;
        switch(Packaging.TYPE)
        {
            case BENEFIT:
            {
                _criterion.setProfit(FastMath.max(_criterion.getProfit(), this.getValue()));
            }
            break;
            case COST:
            {
                _criterion.setProfit(FastMath.min(_criterion.getProfit(), this.getValue()));
            }
            break;
        }
    }

    @Override
    public void calculateNormalization(@NotNull Profit criterion)
    {
        @NotNull
        final ContinuousProfit _criterion = (ContinuousProfit) criterion;
        switch(Packaging.TYPE)
        {
            case BENEFIT:
            {
                this.setNormalization(this.getValue() / _criterion.getProfit());
            }
            break;
            case COST:
            {
                this.setNormalization(_criterion.getProfit() / this.getValue());
            }
            break;
        }
    }

    @Override
    public void calculateWeightedNormalization(@NotNull Weight criterion)
    {
        this.setNormalization(this.getNormalization() * ((ContinuousWeight) criterion).getWeight());
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public double getNormalization()
    {
        return normalization;
    }

    public void setNormalization(double normalization)
    {
        this.normalization = normalization;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof Packaging))
        {
            return false;
        }

        @NotNull Packaging packaging = (Packaging) o;

        return new EqualsBuilder()
                .append(getValue(), packaging.getValue())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getValue())
                .append(getNormalization())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("normalization", normalization)
                .toString();
    }
}