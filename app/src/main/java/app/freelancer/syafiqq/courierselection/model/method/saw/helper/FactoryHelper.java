package app.freelancer.syafiqq.courierselection.model.method.saw.helper;

import app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousProfitContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.saw.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.saw.profit.ContinuousProfit;
import app.freelancer.syafiqq.courierselection.model.method.saw.property.Identity;
import app.freelancer.syafiqq.courierselection.model.method.saw.weight.ContinuousWeight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 24 April 2017, 9:23 PM.
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
}
