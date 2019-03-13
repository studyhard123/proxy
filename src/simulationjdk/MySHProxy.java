package simulationjdk;

import java.lang.reflect.Method;

public class MySHProxy implements SHInvocationHandler {

    private Object target;

    public MySHProxy(Object obj){
        this.target = obj;
    }

    public Object getInstance(){
        return SHProxy.newProxyInstance(new SHClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置增强，当前代理的方法名是：" + method.getName() );
        return method.invoke(this.target,args);
    }
}
