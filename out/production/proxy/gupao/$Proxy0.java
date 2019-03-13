package com.gupaoedu.vip.pattern.proxy.dynamicproxy.gpproxy;
import com.gupaoedu.vip.pattern.proxy.Person;
import java.lang.reflect.*;
public class $Proxy0 implements gupao.Person{
GPInvocationHandler h;
public $Proxy0(GPInvocationHandler h) { 
this.h = h;}
public int findLove() {
try{
Method m = gupao.Person.class.getMethod("findLove",new Class[]{});
return ((java.lang.Integer)this.h.invoke(this,m,new Object[]{})).intValue();
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}return 0;}}
