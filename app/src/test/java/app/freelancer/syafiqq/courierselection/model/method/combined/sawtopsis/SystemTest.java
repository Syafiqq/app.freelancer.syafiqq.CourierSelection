package app.freelancer.syafiqq.courierselection.model.method.combined.sawtopsis;

import org.junit.Test;

import app.freelancer.syafiqq.courierselection.model.method.saw.alternative.Courier;
import app.freelancer.syafiqq.courierselection.model.method.saw.container.ContinuousWeightContainer;
import app.freelancer.syafiqq.courierselection.model.method.saw.helper.FactoryHelper;
import app.freelancer.syafiqq.courierselection.model.method.topsis.container.ContinuousAccumulatorContainer;
import app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis.ContinuousWeightContainerAdapter;
import app.freelancer.syafiqq.courierselection.model.util.adapter.method.topsis.CourierAdapter;
import app.freelancer.syafiqq.madm.saw.core.factory.Alternative;
import app.freelancer.syafiqq.madm.saw.core.factory.SAW;
import app.freelancer.syafiqq.madm.topsis.core.factory.TOPSIS;

/**
 * This <CourierSelection> project created by :
 * Name         : syafiq
 * Date / Time  : 25 April 2017, 9:52 AM.
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

        ContinuousWeightContainer w = FactoryHelper.createContinuousWeightContainer(0.15, 0.15, 0.10, 0.10, 0.20, 0.30);

        SAW saw = new SAW();

        saw.addAlternative(a1);
        saw.addAlternative(a2);
        saw.addAlternative(a3);
        saw.addAlternative(a4);
        saw.addAlternative(a5);
        saw.addAlternative(a6);
        saw.addAlternative(a7);

        saw.setWeight(w);

        saw.compile();
        saw.searchProfit();
        saw.calculate();

        TOPSIS                         topsis = new TOPSIS();
        ContinuousAccumulatorContainer a      = app.freelancer.syafiqq.courierselection.model.method.topsis.helper.FactoryHelper.createContinuousAccumulatorContainer(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        topsis.setDecisionMatrixAccumulator(a);
        topsis.setWeight(new ContinuousWeightContainerAdapter((ContinuousWeightContainer) saw.getWeight()));

        for(final Alternative alternative : saw.getAlternatives())
        {
            topsis.addAlternative(new CourierAdapter((Courier) alternative));
        }

        topsis.calculateWeightedDecisionMatrix();
        topsis.collectProfitAndLoss();
        topsis.collectProfitAndLossDistance();
        topsis.ranking();
        topsis.sort();

        System.out.println(topsis.getBestAlternative());
    }
}
