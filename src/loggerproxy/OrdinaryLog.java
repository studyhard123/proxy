package loggerproxy;

import java.util.Map;

public class OrdinaryLog {

    private Map<String,Object> mes;

    public void writeLog(){
        System.out.println("可以操作的数据如下");
        System.out.println("mes1=" + mes.get("mes1"));
        System.out.println("mes2=" + mes.get("mes2"));
        System.out.println("ServiceOperation的操作日志");
    }
}
