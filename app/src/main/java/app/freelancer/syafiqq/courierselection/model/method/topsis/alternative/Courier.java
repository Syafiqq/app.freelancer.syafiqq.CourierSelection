package app.freelancer.syafiqq.courierselection.model.method.topsis.alternative;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousAccumulatorContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousProfitContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.topsis.profit.ContinuousProfit;
import app.freelancer.syafiqq.madm.topsis.core.factory.AccumulatorContainer;
import app.freelancer.syafiqq.madm.topsis.core.factory.Alternative;
import app.freelancer.syafiqq.madm.topsis.core.factory.ProfitContainer;
import app.freelancer.syafiqq.madm.topsis.core.factory.Properties;
import app.freelancer.syafiqq.madm.topsis.core.factory.WeightContainer;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 8:22 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class Courier extends Alternative
{
    @NotNull private Properties properties;
    @NotNull private Fleet      fleet;
    @NotNull private Coverage   coverage;
    @NotNull private Experience experience;
    @NotNull private Cost       cost;
    @NotNull private Time       time;
    @NotNull private Packaging  packaging;
    private          double     total;

    public Courier(@NotNull Properties properties, @NotNull Fleet fleet, @NotNull Coverage coverage, @NotNull Experience experience, @NotNull Cost cost, @NotNull Time time, @NotNull Packaging packaging)
    {
        this.properties = properties;
        this.fleet = fleet;
        this.coverage = coverage;
        this.experience = experience;
        this.cost = cost;
        this.time = time;
        this.packaging = packaging;
    }


    @Override
    public void collectData(@NotNull AccumulatorContainer container)
    {
        @NotNull
        final ContinuousAccumulatorContainer _container = (ContinuousAccumulatorContainer) container;
        this.getFleet().collect(_container.getFleet());
        this.getCoverage().collect(_container.getCoverage());
        this.getExperience().collect(_container.getExperience());
        this.getCost().collect(_container.getCost());
        this.getTime().collect(_container.getTime());
        this.getPackaging().collect(_container.getPackaging());
    }

    @Override
    public void calculateDecisionMatrix(@NotNull AccumulatorContainer container)
    {
        @NotNull
        final ContinuousAccumulatorContainer _container = (ContinuousAccumulatorContainer) container;
        this.getFleet().calculate(_container.getFleet());
        this.getCoverage().calculate(_container.getCoverage());
        this.getExperience().calculate(_container.getExperience());
        this.getCost().calculate(_container.getCost());
        this.getTime().calculate(_container.getTime());
        this.getPackaging().calculate(_container.getPackaging());
    }

    @Override
    public void calculateWeightedDecisionMatrix(@NotNull WeightContainer container)
    {
        @NotNull
        final ContinuousWeightContainer _container = (ContinuousWeightContainer) container;
        this.getFleet().normalize(_container.getFleet());
        this.getCoverage().normalize(_container.getCoverage());
        this.getExperience().normalize(_container.getExperience());
        this.getCost().normalize(_container.getCost());
        this.getTime().normalize(_container.getTime());
        this.getPackaging().normalize(_container.getPackaging());
    }

    @Override
    public ProfitContainer adaptWeightedDecisionMatrix()
    {
        return new ContinuousProfitContainer(
                new ContinuousProfit(this.getFleet().getNormalized()),
                new ContinuousProfit(this.getCoverage().getNormalized()),
                new ContinuousProfit(this.getExperience().getNormalized()),
                new ContinuousProfit(this.getCost().getNormalized()),
                new ContinuousProfit(this.getTime().getNormalized()),
                new ContinuousProfit(this.getPackaging().getNormalized())
        );
    }

    @Override
    public void getProfit(@NotNull ProfitContainer container)
    {
        @NotNull
        final ContinuousProfitContainer _container = (ContinuousProfitContainer) container;
        this.getFleet().searchProfit(_container.getFleet());
        this.getCoverage().searchProfit(_container.getCoverage());
        this.getExperience().searchProfit(_container.getExperience());
        this.getCost().searchProfit(_container.getCost());
        this.getTime().searchProfit(_container.getTime());
        this.getPackaging().searchProfit(_container.getPackaging());
    }

    @Override
    public void getLoss(@NotNull ProfitContainer container)
    {
        @NotNull
        final ContinuousProfitContainer _container = (ContinuousProfitContainer) container;
        this.getFleet().searchLoss(_container.getFleet());
        this.getCoverage().searchLoss(_container.getCoverage());
        this.getExperience().searchLoss(_container.getExperience());
        this.getCost().searchLoss(_container.getCost());
        this.getTime().searchLoss(_container.getTime());
        this.getPackaging().searchLoss(_container.getPackaging());
    }

    @Override
    public void calculateProfitDistance(@NotNull ProfitContainer container)
    {
        @NotNull
        final ContinuousProfitContainer _container = (ContinuousProfitContainer) container;
        this.getFleet().profitDistance(_container.getFleet());
        this.getCoverage().profitDistance(_container.getCoverage());
        this.getExperience().profitDistance(_container.getExperience());
        this.getCost().profitDistance(_container.getCost());
        this.getTime().profitDistance(_container.getTime());
        this.getPackaging().profitDistance(_container.getPackaging());
    }

    @Override
    public void calculateLossDistance(@NotNull ProfitContainer container)
    {
        @NotNull
        final ContinuousProfitContainer _container = (ContinuousProfitContainer) container;
        this.getFleet().lossDistance(_container.getFleet());
        this.getCoverage().lossDistance(_container.getCoverage());
        this.getExperience().lossDistance(_container.getExperience());
        this.getCost().lossDistance(_container.getCost());
        this.getTime().lossDistance(_container.getTime());
        this.getPackaging().lossDistance(_container.getPackaging());
    }

    @Override
    public void calculatePreferences()
    {
        final double profitDistance = FastMath.sqrt(this.getFleet().getProfitDistance() +
                this.getCoverage().getProfitDistance() +
                this.getExperience().getProfitDistance() +
                this.getCost().getProfitDistance() +
                this.getTime().getProfitDistance() +
                this.getPackaging().getProfitDistance()
        );

        final double lossDistance = FastMath.sqrt(this.getFleet().getLossDistance() +
                this.getCoverage().getLossDistance() +
                this.getExperience().getLossDistance() +
                this.getCost().getLossDistance() +
                this.getTime().getLossDistance() +
                this.getPackaging().getLossDistance()
        );

        this.setTotal(lossDistance / (lossDistance + profitDistance));
    }

    @Override
    public int compareTo(@NonNull Alternative o)
    {
        return -Double.compare(this.getTotal(), ((Courier) o).getTotal());
    }

    @NonNull
    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties(@NonNull Properties properties)
    {
        this.properties = properties;
    }

    @NonNull
    public Fleet getFleet()
    {
        return fleet;
    }

    public void setFleet(@NonNull Fleet fleet)
    {
        this.fleet = fleet;
    }

    @NonNull
    public Coverage getCoverage()
    {
        return coverage;
    }

    public void setCoverage(@NonNull Coverage coverage)
    {
        this.coverage = coverage;
    }

    @NonNull
    public Experience getExperience()
    {
        return experience;
    }

    public void setExperience(@NonNull Experience experience)
    {
        this.experience = experience;
    }

    @NonNull
    public Cost getCost()
    {
        return cost;
    }

    public void setCost(@NonNull Cost cost)
    {
        this.cost = cost;
    }

    @NonNull
    public Time getTime()
    {
        return time;
    }

    public void setTime(@NonNull Time time)
    {
        this.time = time;
    }

    @NonNull
    public Packaging getPackaging()
    {
        return packaging;
    }

    public void setPackaging(@NonNull Packaging packaging)
    {
        this.packaging = packaging;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof Courier))
        {
            return false;
        }

        Courier courier = (Courier) o;

        return new EqualsBuilder()
                .append(getProperties(), courier.getProperties())
                .append(getFleet(), courier.getFleet())
                .append(getCoverage(), courier.getCoverage())
                .append(getExperience(), courier.getExperience())
                .append(getCost(), courier.getCost())
                .append(getTime(), courier.getTime())
                .append(getPackaging(), courier.getPackaging())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getProperties())
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
                .append("properties", properties)
                .append("fleet", fleet)
                .append("coverage", coverage)
                .append("experience", experience)
                .append("cost", cost)
                .append("time", time)
                .append("packaging", packaging)
                .append("total", total)
                .toString();
    }
}
