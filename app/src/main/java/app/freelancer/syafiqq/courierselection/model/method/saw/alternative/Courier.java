package app.freelancer.syafiqq.courierselection.model.method.saw.alternative;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousProfitContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.saw.profit.ContinuousProfit;
import app.freelancer.syafiqq.madm.saw.core.factory.Alternative;
import app.freelancer.syafiqq.madm.saw.core.factory.ProfitContainer;
import app.freelancer.syafiqq.madm.saw.core.factory.Properties;
import app.freelancer.syafiqq.madm.saw.core.factory.WeightContainer;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 9:10 PM.
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
    public void calculateNormalization(@NotNull ProfitContainer alternative)
    {
        @NotNull
        final ContinuousProfitContainer _alternative = (ContinuousProfitContainer) alternative;
        this.getFleet().calculateNormalization(_alternative.getFleet());
        this.getCoverage().calculateNormalization(_alternative.getCoverage());
        this.getExperience().calculateNormalization(_alternative.getExperience());
        this.getCost().calculateNormalization(_alternative.getCost());
        this.getTime().calculateNormalization(_alternative.getTime());
        this.getPackaging().calculateNormalization(_alternative.getPackaging());
    }

    @Override
    public void calculatePreferences(@NotNull WeightContainer alternative)
    {
        @NotNull
        final ContinuousWeightContainer _alternative = (ContinuousWeightContainer) alternative;

        this.getFleet().calculateWeightedNormalization(_alternative.getFleet());
        this.getCoverage().calculateWeightedNormalization(_alternative.getCoverage());
        this.getExperience().calculateWeightedNormalization(_alternative.getExperience());
        this.getCost().calculateWeightedNormalization(_alternative.getCost());
        this.getTime().calculateWeightedNormalization(_alternative.getTime());
        this.getPackaging().calculateWeightedNormalization(_alternative.getPackaging());

        this.total = 0;
        this.total += this.getFleet().getNormalization();
        this.total += this.getCoverage().getNormalization();
        this.total += this.getExperience().getNormalization();
        this.total += this.getCost().getNormalization();
        this.total += this.getTime().getNormalization();
        this.total += this.getPackaging().getNormalization();
    }

    @Override
    public ProfitContainer adaptToProfit()
    {
        return new ContinuousProfitContainer(
                new ContinuousProfit(this.getFleet().getValue()),
                new ContinuousProfit(this.getCoverage().getValue()),
                new ContinuousProfit(this.getExperience().getValue()),
                new ContinuousProfit(this.getCost().getValue()),
                new ContinuousProfit(this.getTime().getValue()),
                new ContinuousProfit(this.getPackaging().getValue())
        );
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
