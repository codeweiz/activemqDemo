package microboat.activemq.embed;

import org.apache.activemq.broker.BrokerService;

/**
 * 便捷，占用资源少，方便多开模拟集群环境
 * 但功能太少，缺乏日志，数据库等支持
 * 实际工作中用到较少
 * @author zhouwei
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        // jmx 是 java 语言的新框架
        // 他允许你把所有资源（包括软硬件）封装成 java 对象，然后暴露在分布式环境中
        brokerService.setUseJmx(true);
//        brokerService.setPopulateJMSXUserID(true);
        // 本地并没有 activemq 的环境，但可通过 BrokerService 模拟出一个 activemq 的环境
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
