package app.freelancer.syafiqq.courierselection.model.method.topsis;

import org.junit.Test;

import app.freelancer.syafiqq.courierselection.model.method.topsis.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousAccumulatorContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.topsis.helper.FactoryHelper;
import app.freelancer.syafiqq.madm.topsis.core.factory.TOPSIS;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 8:36 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class SystemTest
{
    @Test
    public void it_success()
    {
        Courier a1 = FactoryHelper.createCourier("JNE", 5, 2, 4, 2, 4, 3);
        Courier a2 = FactoryHelper.createCourier("Kantor Pos Indonesia", 4, 1, 3, 2, 2, 4);
        Courier a3 = FactoryHelper.createCourier("JET Express", 3, 2, 5, 3, 3, 2);
        Courier a4 = FactoryHelper.createCourier("Lia Jaya Sentosa", 5, 1, 4, 1, 4, 1);
        Courier a5 = FactoryHelper.createCourier("J & T Express", 1, 2, 1, 3, 1, 3);
        Courier a6 = FactoryHelper.createCourier("TIKI", 4, 1, 2, 1, 1, 1);
        Courier a7 = FactoryHelper.createCourier("SiCepat", 2, 2, 5, 2, 3, 2);

        ContinuousAccumulatorContainer a = FactoryHelper.createContinuousAccumulatorContainer(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ContinuousWeightContainer      w = FactoryHelper.createContinuousWeightContainer(0.15, 0.15, 0.10, 0.10, 0.20, 0.30);

        TOPSIS topsis = new TOPSIS();

        topsis.addAlternative(a1);
        topsis.addAlternative(a2);
        topsis.addAlternative(a3);
        topsis.addAlternative(a4);
        topsis.addAlternative(a5);
        topsis.addAlternative(a6);
        topsis.addAlternative(a7);

        topsis.setDecisionMatrixAccumulator(a);
        topsis.setWeight(w);

        topsis.process();

        System.out.println(topsis.getBestAlternative());
    }
}
