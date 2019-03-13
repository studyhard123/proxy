package loggerproxy;

import java.util.Map;

public class ServiceOperation implements Service {
    @Override
    public void createBusiness(Map<String,Object> mes) {
        System.out.println("执行业务操作");
    }
}
