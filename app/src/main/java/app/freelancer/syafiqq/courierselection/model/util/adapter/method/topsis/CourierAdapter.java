package app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis;

import org.jetbrains.annotations.NotNull;

import app.freelancer.syafiqq.courierselection.model.method.topsis.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Cost;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Coverage;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Experience;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Fleet;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Packaging;
import app.freelancer.syafiqq.courierselection.model.method.topsis.criterion.Time;
import app.freelancer.syafiqq.courierselection.model.method.topsis.property.Identity;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 10:14 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class CourierAdapter extends Courier
{
    public CourierAdapter(@NotNull app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier courier)
    {
        super(
                new Identity(courier.getIdentity().getName()),
                new Fleet(courier.getFleet().getValue()),
                new Coverage(courier.getCoverage().getValue()),
                new Experience(courier.getExperience().getValue()),
                new Cost(courier.getCost().getValue()),
                new Time(courier.getTime().getValue()),
                new Packaging(courier.getTime().getValue())
        );

        super.getFleet().setNormalized(courier.getFleet().getNormalization());
        super.getCoverage().setNormalized(courier.getCoverage().getNormalization());
        super.getExperience().setNormalized(courier.getExperience().getNormalization());
        super.getCost().setNormalized(courier.getCost().getNormalization());
        super.getTime().setNormalized(courier.getTime().getNormalization());
        super.getPackaging().setNormalized(courier.getPackaging().getNormalization());
    }
}
