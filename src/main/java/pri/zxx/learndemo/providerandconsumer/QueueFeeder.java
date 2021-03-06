package pri.zxx.learndemo.providerandconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pri.zxx.learndemo.providerandconsumer.utils.IGenerator;

import java.util.concurrent.TimeUnit;

/**
 * @author 一杯咖啡
 * @desc 任务生产者
 * @createTime
 */
//@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueueFeeder<T> implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(QueueFeeder.class);

    @Autowired
    private FetchQueue queue;
    @Autowired
    private IGenerator<T> iGenerator;
    @Value(value = "${my.queue.maxSize}")
    private int queueMaxSize;

    @Autowired
    private FetcherState fetcherState;

    /**
     * desc:关闭管道添加工具
     **/
    public void stopFeeder() {
        //停止数据库提取工具
        try {
            LOG.info("【正在关闭数据库提取工具】");
        } catch (Exception e) {
            LOG.error("【关闭数据库提取工具出错】");
        }
        fetcherState.setFeederRunnning(false);
    }

    /**
     * desc: feeder 线程运行
     **/
    @Override
    public void run() {
        if (iGenerator == null) {
            LOG.error("未提供数据库提取工具，关闭提取线程");
            fetcherState.setFeederRunnning(false);
            return;
        }
        LOG.info(this.toString());
        boolean hasMore = true;
        fetcherState.setFeederRunnning(true);
        //任务生产者依赖 数据库后续任务，自身状态，调度器状态
        while (hasMore && fetcherState.isFeederRunnning() && fetcherState.isFetcherRunning()) {
            //监听queue中数量，当queue中数量为1000时，线程等待，
            int feed = queueMaxSize - queue.getSize();
            if (feed <= 0) {
                pause(1, 0);
                continue;
            }
            //如果queue中小于1000，往queue中添加新任务，未提取到任务 count 等待时间
            int count = 0;
            while (feed > 0 && hasMore && fetcherState.isFeederRunnning()) {
                //任务生成器 如果下一个任务为空，返回空。判断dbmananger中是否有后续任务
                T t = iGenerator.getData();
                if (t != null) {
                    // pause(0, 100);
                    LOG.info("feed Size = " + feed);
                    LOG.info("queue size == " + queue.getSize());
                    queue.addResponseData(t);
                    // pause(0, 500);
                    feed--;
                } else {
                    pause(1, 0);
                    count++;
                    if (queue.getSize() == 0 && count >= 6) {
                        LOG.info("等待超时，关闭程序");
                        fetcherState.setFeederRunnning(false);
                        hasMore = false;
                    } else {
                        LOG.info("获取数据超时【" + count + "】秒");
                    }
                }
            }
        }
    }

    public void pause(Integer seconds, Integer mills) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            LOG.error("生产者休眠异常");
        }
    }

    @Override
    public String toString() {
        return " 提取线程 ：QueueFeeder [" +
                "\nqueue=" + queue +
                "\niGenerator=" + iGenerator +
                "\nqueueMaxSize=" + queueMaxSize +
                "\nfetcherState=" + fetcherState +
                ']';
    }
}
