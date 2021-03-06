package pri.zxx.learndemo.designmodels.decoratorModel.abstractClass;


import java.util.logging.Logger;

/**
 * desc: 饮料抽象类
 **/
public abstract class NowBeverage {
    private static final Logger log = Logger.getLogger(NowBeverage.class.getName());
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract double cost();

    public void showDetail() {
        log.info(this.getDescription() + " -- 价格::￥" + this.cost());
    }
}
