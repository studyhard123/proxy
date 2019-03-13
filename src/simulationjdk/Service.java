package simulationjdk;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Service implements Ser {

    @Override
    public String methodName(String name , int mod){
        System.out.println("执行业务操作1");
        return null;
    }

    @Override
    public String methodName2(int arg0, char arg1) {
        System.out.println("执行业务操作2");
        return null;
    }
}
