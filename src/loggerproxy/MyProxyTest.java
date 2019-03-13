package loggerproxy;

import java.util.HashMap;
import java.util.Map;

public class MyProxyTest {

    public static void main(String[] args) {
        MyProxy proxy = new MyProxy(new ServiceOperation());
        Service service = (Service)proxy.getInstance();
        Map<String,Object> mes = new HashMap<String,Object>();
        mes.put("mes1","用户信息");
        mes.put("mes2","数据库需要的信息");
        service.createBusiness(mes);
    }
}
