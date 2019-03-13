package loggerproxy;

import sun.misc.ProxyGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理类，这里主要使用它完成日志信息的代理
 */
public class MyProxy implements InvocationHandler {

    private Object target;

    public MyProxy(Object proxyObj){
        this.target = proxyObj;
    }

    public Object getInstance(){
        Class<?> objClass = this.target.getClass();
        return Proxy.newProxyInstance(objClass.getClassLoader(),objClass.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        OrdinaryLog ol = new OrdinaryLog();
        Class<OrdinaryLog> OrdinaryLogClass = (Class<OrdinaryLog>) ol.getClass();
        setLogClassArgs(ol,OrdinaryLogClass,args);
        OrdinaryLogClass.getDeclaredMethod("writeLog",null).invoke(ol,null);
        return method.invoke(target,args);
    }

    private void setLogClassArgs(OrdinaryLog ol , Class<OrdinaryLog> clazz , Object[] args) throws Exception {
        Field field = clazz.getDeclaredField("mes");
        field.setAccessible(true);
        field.set(ol,args[0]);
    }
}
