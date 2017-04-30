package app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis;

import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.weight.ContinuousWeight;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 10:23 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class ContinuousWeightContainerAdapter extends ContinuousWeightContainer
{
    public ContinuousWeightContainerAdapter(@NotNull app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer container)
    {
        super(
                new ContinuousWeight(container.getFleet().getWeight()),
                new ContinuousWeight(container.getCoverage().getWeight()),
                new ContinuousWeight(container.getExperience().getWeight()),
                new ContinuousWeight(container.getCost().getWeight()),
                new ContinuousWeight(container.getTime().getWeight()),
                new ContinuousWeight(container.getPackaging().getWeight())
        );
    }
}
