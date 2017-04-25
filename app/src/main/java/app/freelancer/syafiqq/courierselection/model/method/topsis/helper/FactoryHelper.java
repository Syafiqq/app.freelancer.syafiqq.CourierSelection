package app.freelancer.syafiqq.courierselection.model.method.topsis.helper;

import app.freelancer.syafiqq.courierselection.model.method.topsis.accumulator.ContinuousAccumulator;
import app.freelancer.syafiqq.courierselection.model.method.topsis.alternative.Courier;
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
import app.freelancer.syafiqq.courierselection.model.method.topsis.property.Identity;
import app.freelancer.syafiqq.courierselection.model.method.topsis.weight.ContinuousWeight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 8:33 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class FactoryHelper
{
    public static Courier createCourier(String name, int fleet, int coverage, int experience, int cost, int time, int packaging)
    {
        return new Courier(
                new Identity(name)
                , new Fleet(fleet)
                , new Coverage(coverage)
                , new Experience(experience)
                , new Cost(cost)
                , new Time(time)
                , new Packaging(packaging)
        );
    }

    public static ContinuousProfitContainer createContinuousProfitContainer(double fleet, double coverage, double experience, double cost, double time, double packaging)
    {
        return new ContinuousProfitContainer(
                new ContinuousProfit(fleet)
                , new ContinuousProfit(coverage)
                , new ContinuousProfit(experience)
                , new ContinuousProfit(cost)
                , new ContinuousProfit(time)
                , new ContinuousProfit(packaging)
        );
    }

    public static ContinuousWeightContainer createContinuousWeightContainer(double fleet, double coverage, double experience, double cost, double time, double packaging)
    {
        return new ContinuousWeightContainer(
                new ContinuousWeight(fleet)
                , new ContinuousWeight(coverage)
                , new ContinuousWeight(experience)
                , new ContinuousWeight(cost)
                , new ContinuousWeight(time)
                , new ContinuousWeight(packaging)
        );
    }

    public static ContinuousAccumulatorContainer createContinuousAccumulatorContainer(double fleet, double coverage, double experience, double cost, double time, double packaging)
    {
        return new ContinuousAccumulatorContainer(
                new ContinuousAccumulator(fleet)
                , new ContinuousAccumulator(coverage)
                , new ContinuousAccumulator(experience)
                , new ContinuousAccumulator(cost)
                , new ContinuousAccumulator(time)
                , new ContinuousAccumulator(packaging)
        );
    }
}
