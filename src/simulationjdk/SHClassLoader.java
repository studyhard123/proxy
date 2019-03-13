package simulationjdk;

import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;

import java.io.*;

public class SHClassLoader extends ClassLoader {

    private File classDirectoryFile;

    public SHClassLoader(){
        String classPath = SHClassLoader.class.getResource("").getPath();
        this.classDirectoryFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classFullyQualifiedName = SHClassLoader.class.getPackage().getName() + "." + name;//获取代理类的完全限定名
        File classFile = new File(classDirectoryFile,name.replaceAll("\\.","/") + ".class");
        if(classFile.exists()){
            ByteArrayOutputStream byteOut = null;
            InputStream in = null;
            try {
                byteOut = new ByteArrayOutputStream();
                in = new FileInputStream(classFile);
                byte[] buff = new byte[1024];
                int len = 0;
                while((len = in.read(buff)) != -1){
                    byteOut.write(buff,0 , len);
                }
                return defineClass(classFullyQualifiedName,byteOut.toByteArray(),0,byteOut.size());
            }catch(IOException e){
                e.printStackTrace();
            }finally {
               try {
                   if(in != null){
                       in.close();
                   }
                   if(byteOut != null){
                       byteOut.close();
                   }
               } catch(IOException e){
                    e.printStackTrace();
               }
            }
        }
        return null;
    }
}
