package simulationjdk;

import java.lang.reflect.Method;

public interface SHInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
