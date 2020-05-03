package uw.log.es;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import uw.log.es.service.LogService;
import uw.log.es.vo.LogInterface;
import uw.log.es.vo.LogInterfaceOrder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liliang
 * @since 2018-04-25
 */
public class LogClientReadModeTest {

    private static LogClient logClient;

    @BeforeClass
    public static void setUp() {
        LogClientProperties logClientProperties = new LogClientProperties();
        LogClientProperties.EsConfig esConfig = new LogClientProperties.EsConfig();
        esConfig.setClusters("http://192.168.88.16:9200");
        esConfig.setMode(LogClientProperties.LogMode.READ_ONLY);
        esConfig.setAppInfoOverwrite(false);
        logClientProperties.setEs(esConfig);
        logClient = new LogClient(new LogService(logClientProperties,null,null));
        logClient.regLogObject(LogInterface.class);
        logClient.regLogObject(LogInterfaceOrder.class);
    }

    @Test
    public void testLogTestData() {
        int count = 0;
        Stopwatch stopwatch = Stopwatch.createStarted();
        while (count < 10000) {
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
            count++;
        }
        System.out.println("elapsed wall time " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void testLog() {
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

    @Test
    public void testWriteBulkLog() {

        List<LogInterface> dataList = Lists.newArrayList();
        LogInterface logInterface1 = new LogInterface();
        logInterface1.setInterfaceType(1);
        logInterface1.setInterfaceConfigId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface1.setSaasId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface1.setProductType(10);
        logInterface1.setProductId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface1.setInterfaceProductId(RandomStringUtils.randomNumeric(11));
        logInterface1.setInterfaceFunction("saas.common.log.client.logInterface");
        logInterface1.setRequestDate(new Date());
        logInterface1.setRequestBody("你吃饭了吗?");
        logInterface1.setResponseDate(new Date());
        logInterface1.setResponseBody("吃了");
        dataList.add(logInterface1);

        LogInterface logInterface2 = new LogInterface();
        logInterface2.setInterfaceType(1);
        logInterface2.setInterfaceConfigId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface2.setSaasId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface2.setProductType(10);
        logInterface2.setProductId(Long.parseLong(RandomStringUtils.randomNumeric(6)));
        logInterface2.setInterfaceProductId(RandomStringUtils.randomNumeric(11));
        logInterface2.setInterfaceFunction("saas.common.log.client.logInterface");
        logInterface2.setRequestDate(new Date());
        logInterface2.setRequestBody("你吃饭了吗?");
        logInterface2.setResponseDate(new Date());
        logInterface2.setResponseBody("吃了");
        dataList.add(logInterface2);

        logClient.bulkLog(dataList);
    }

    @Test
    public void testQueryLogBySimpleQuery() {
//        List<LogInterface> dataList = logClient.simpleQueryLog(LogInterface.class,"q=productId:926270");
    }

    @Test
    public void testQueryLogByDSLQuery() {
        String dslQuery = "{" +
                "\"query\": {" +
                "\"bool\": {" +
                "\"must\": [" +
                "{\n" +
                "\"term\": {" +
                "\"productType\": 10" +
                "}\n" +
                "}\n" +
                "],\n" +
                "\"must_not\": [ ]," +
                "\"should\": [ ]" +
                "}" +
                "}," +
                "\"from\": 0," +
                "\"size\": 10," +
                "\"sort\": [\"requestDate\"]," +
                "\"aggs\": { }" +
                "}";
    }


}
