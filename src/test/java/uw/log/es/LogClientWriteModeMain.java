package uw.log.es;

import org.apache.commons.lang3.RandomStringUtils;
import uw.log.es.service.LogService;
import uw.log.es.vo.LogInterface;

import java.util.Date;

/**
 * @author liliang
 * @since 2018-07-27
 */
public class LogClientWriteModeMain {

    private static LogClient logClient;


    public static void main(String[] args) {
        LogClientProperties logClientProperties = new LogClientProperties();
        LogClientProperties.EsConfig esConfig = new LogClientProperties.EsConfig();
        esConfig.setClusters("http://192.168.88.16:9200");
        esConfig.setMode(LogClientProperties.LogMode.READ_WRITE);
        esConfig.setAppInfoOverwrite(false);
        esConfig.setMaxFlushInMilliseconds(10000);
        esConfig.setMaxBytesOfBatch(5 * 1024 * 1024);
        esConfig.setMaxBatchThreads(3);
        esConfig.setMaxBatchQueueSize(10);
        logClientProperties.setEs(esConfig);
        logClient = new LogClient(new LogService(logClientProperties, null, null));
        logClient.regLogObjectWithIndexPattern(LogInterface.class, "_yyyy-MM");

        for (int i = 0; i < 1000000000; i++) {
            LogInterface logInterface = new LogInterface();
            logInterface.setInterfaceType(1);
            logInterface.setInterfaceConfigId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
            logInterface.setSaasId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
            logInterface.setProductType(10);
            logInterface.setProductId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
            logInterface.setInterfaceProductId(RandomStringUtils.randomNumeric(11));
            logInterface.setInterfaceFunction("saas.common.log.client.logInterface");
            logInterface.setRequestDate(new Date());
            logInterface.setRequestBody("你吃饭了吗?");
            logInterface.setResponseDate(new Date());
            logInterface.setResponseBody("吃了");
            logClient.log(logInterface);
        }
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
