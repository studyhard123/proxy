package simulationjdk;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;

public class SHProxy {

    private static final String DEFAULT_NEWLINE = "\r\n";

    private static final String DEFAULT_CLASSNAME = "$Proxy_0";

    public static Object newProxyInstance(SHClassLoader loader, Class<?>[] interfaces, SHInvocationHandler h) throws IllegalArgumentException{
        try {
            //生成代理类字符串
            String sourceCode = getSourceCodeByInterfaces(interfaces);
            //生成Java文件
            File file = new File(SHProxy.class.getResource("").getPath() +  File.separator + DEFAULT_CLASSNAME +".java");
            FileWriter fw = new FileWriter(file);
            fw.write(sourceCode);
            fw.flush();
            fw.close();
            //编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();
            //把生成的class文件j加载到JVM中去
            Class<?> proxyClass = loader.findClass(DEFAULT_CLASSNAME);
            Constructor constructor = proxyClass.getConstructor(SHInvocationHandler.class);
            file.delete();
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSourceCodeByInterfaces(Class<?>[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append("package simulationjdk;" + DEFAULT_NEWLINE);
        sb.append("import java.lang.reflect.*;" + DEFAULT_NEWLINE);
        sb.append("public class " + DEFAULT_CLASSNAME + " implements ");
        //遍历接口
        for(int i = 0  ; i < interfaces.length ; i++){
            if(i == interfaces.length - 1){
                sb.append(interfaces[i].getName());
            }else{
                sb.append(interfaces[i].getName() + ",");
            }
        }
        sb.append(" { " +  DEFAULT_NEWLINE );
        sb.append(" private static SHInvocationHandler h ; " + DEFAULT_NEWLINE);
        sb.append(" public " + DEFAULT_CLASSNAME + " ( SHInvocationHandler invocationHandler ) { " + DEFAULT_NEWLINE);
        sb.append(" this.h = invocationHandler ; " + DEFAULT_NEWLINE);
        sb.append(" } " + DEFAULT_NEWLINE);
        //循环全部接口的每一个方法
        for(Class<?> interfaceOnlyOne : interfaces){
            Method[] methods = interfaceOnlyOne.getDeclaredMethods();
            for(Method method : methods){
                int mod = method.getModifiers();
                String modires = Modifier.toString(mod);
                if(modires.contains("public")){
                    if(modires.contains("abstract")){
                        modires = modires.replaceAll("abstract", "");
                    }
                    sb.append(modires + " " + method.getReturnType().getName() + " " + method.getName() + " ( ");
                    //循环参数列表
                    Parameter[] params = method.getParameters();
                    for(int j = 0 ;  j < params.length ; j++){
                        if(j == params.length - 1){
                            sb.append(params[j].getType().getName() + " " + params[j].getName());
                        }else{
                            sb.append(params[j].getType().getName() + " " + params[j].getName() + " , ");
                        }
                    }
                    sb.append(" ) { " + DEFAULT_NEWLINE);
                    //处理方法体
                    sb.append(" try { " + DEFAULT_NEWLINE);
                    sb.append(" Method method = " + interfaceOnlyOne.getName() + ".class.getMethod ( ");
                    sb.append("\"" + method.getName() + "\"" + " , ");
                    if(params.length <= 0){
                        sb.append(" null ");
                    }else{
                        //继续遍历反射获取方法的参数列表
                        sb.append(" new Class[] { ");
                        for(int j = 0 ;  j < params.length ; j++){
                            if(j == params.length - 1){
                                sb.append(params[j].getType().getName() + ".class");
                            }else{
                                sb.append(params[j].getType().getName() + ".class , ");
                            }
                        }
                        sb.append(" } ");
                    }
                    sb.append(" ); " + DEFAULT_NEWLINE);
                    sb.append(" return (" + method.getReturnType().getName() +")this.h.invoke(this ,method ,new Object[]{ " );
                    //循环传参数
                    for(int j = 0 ;  j < params.length ; j++){
                        if(j == params.length - 1){
                            sb.append(params[j].getName());
                        }else{
                            sb.append(params[j].getName() + " , ");
                        }
                    }
                    sb.append(" } ); " + DEFAULT_NEWLINE);
                    sb.append(" } catch(Throwable e) { " + DEFAULT_NEWLINE);
                    sb.append(" e.printStackTrace(); " + DEFAULT_NEWLINE);
                    sb.append(" } " + DEFAULT_NEWLINE);
                    sb.append(" return null; " + DEFAULT_NEWLINE);
                    sb.append(" } " + DEFAULT_NEWLINE);
                }
            }
        }
        sb.append(" } " + DEFAULT_NEWLINE);
        return sb.toString();
    }
}
