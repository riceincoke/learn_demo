package pri.zxx.learndemo.designmodels.factorymodel.PowerImp;

import pri.zxx.learndemo.designmodels.factorymodel.interfaces.Power;

public class DellPower implements Power {

    @Override
    public void ShowPower() {
        // TODO Auto-generated method stub
        System.out.println("戴尔电源");
    }

}
