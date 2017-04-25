package app.freelancer.syafiqq.courierselection.model.method.topsis.criterion;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.topsis.accumulator.ContinuousAccumulator;
import app.freelancer.syafiqq.courierselection.model.method.topsis.profit.ContinuousProfit;
import app.freelancer.syafiqq.courierselection.model.method.topsis.type.CriterionType;
import app.freelancer.syafiqq.courierselection.model.method.topsis.weight.ContinuousWeight;
import app.freelancer.syafiqq.madm.topsis.core.factory.Accumulator;
import app.freelancer.syafiqq.madm.topsis.core.factory.Criterion;
import app.freelancer.syafiqq.madm.topsis.core.factory.Profit;
import app.freelancer.syafiqq.madm.topsis.core.factory.Weight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 8:16 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class Coverage extends Criterion
{
    private final static CriterionType TYPE = CriterionType.BENEFIT;

    private int    value;
    private double normalized;
    private double profitDistance;
    private double lossDistance;

    public Coverage(int value)
    {
        this.value = value;
    }

    @Override
    public void collect(@NotNull Accumulator accumulator)
    {
        @NotNull ContinuousAccumulator _accumulator = (ContinuousAccumulator) accumulator;
        final double                   rootV        = FastMath.pow(this.getValue(), 2.0);
        _accumulator.setAccumulator(_accumulator.getAccumulator() + rootV);
    }

    @Override
    public void calculate(@NotNull Accumulator accumulator)
    {
        this.setNormalized(this.getValue() / ((ContinuousAccumulator) accumulator).getAccumulator());
    }

    @Override
    public void normalize(@NotNull Weight weight)
    {
        this.setNormalized(this.getNormalized() * ((ContinuousWeight) weight).getWeight());
    }

    @Override
    public void searchProfit(@NotNull Profit profit)
    {
        @NotNull
        final ContinuousProfit _profit = (ContinuousProfit) profit;
        switch(Coverage.TYPE)
        {
            case BENEFIT:
            {
                this.assignMax(_profit);
            }
            break;
            case COST:
            {
                this.assignMin(_profit);
            }
            break;
        }
    }

    @Override
    public void searchLoss(@NotNull Profit profit)
    {
        @NotNull
        final ContinuousProfit _profit = (ContinuousProfit) profit;
        switch(Coverage.TYPE)
        {
            case BENEFIT:
            {
                this.assignMin(_profit);
            }
            break;
            case COST:
            {
                this.assignMax(_profit);
            }
            break;
        }
    }

    @Override
    public void profitDistance(@NotNull Profit profit)
    {
        this.setProfitDistance(FastMath.pow(((ContinuousProfit) profit).getProfit() - this.getNormalized(), 2.0));
    }

    @Override
    public void lossDistance(@NotNull Profit profit)
    {
        this.setLossDistance(FastMath.pow(this.getNormalized() - ((ContinuousProfit) profit).getProfit(), 2.0));
    }

    private void assignMin(@NotNull ContinuousProfit profit)
    {
        profit.setProfit(FastMath.min(profit.getProfit(), this.getNormalized()));
    }

    private void assignMax(@NotNull ContinuousProfit profit)
    {
        profit.setProfit(FastMath.max(profit.getProfit(), this.getNormalized()));
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public double getNormalized()
    {
        return normalized;
    }

    public void setNormalized(double normalized)
    {
        this.normalized = normalized;
    }

    public double getProfitDistance()
    {
        return profitDistance;
    }

    public void setProfitDistance(double profitDistance)
    {
        this.profitDistance = profitDistance;
    }

    public double getLossDistance()
    {
        return lossDistance;
    }

    public void setLossDistance(double lossDistance)
    {
        this.lossDistance = lossDistance;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof Coverage))
        {
            return false;
        }

        final Coverage coverage = (Coverage) o;

        return new EqualsBuilder()
                .append(getValue(), coverage.getValue())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getValue())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("normalized", normalized)
                .append("profitDistance", profitDistance)
                .append("lossDistance", lossDistance)
                .toString();
    }

}
