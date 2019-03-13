package simulationjdk;

public class SHProxyTest {
    public static void main(String[] args) {
        MySHProxy shp = new MySHProxy(new Service());
        Ser ser = (Ser)shp.getInstance();
        ser.methodName("", 1);
        ser.methodName2(1, 'a');
    }
}
