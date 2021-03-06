package pri.zxx.learndemo.designmodels.commandModel;

import pri.zxx.learndemo.designmodels.commandModel.core.Invoker;
import pri.zxx.learndemo.designmodels.commandModel.core.MyReciver;
import pri.zxx.learndemo.designmodels.commandModel.core.OnCommand;

/**
 * @author 一杯咖啡
 * @desc 命令模式 测试类
 * @createTime 2018-12-23-3:04
 */
public class CommandTest {
    public static void main(String[] args) {
        Invoker invoker = new Invoker(new OnCommand(new MyReciver()));
        invoker.call();
        invoker.undo();
    }
}
