package pri.zxx.learndemo.designmodels.decoratorModel.coffee;


import pri.zxx.learndemo.designmodels.decoratorModel.abstractClass.NowBeverage;

/**
 * @author 一杯咖啡
 */
public class Espresso extends NowBeverage {

    public Espresso() {
        this.setDescription("【浓缩咖啡-Espresso】1.99");
    }

    @Override
    public double cost() {
        return 1.99;
    }

}
