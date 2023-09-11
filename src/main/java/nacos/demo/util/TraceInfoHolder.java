package nacos.demo.util;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TraceInfoHolder {

    private static final TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();

    public static void set(String info) {
        transmittableThreadLocal.set(info);
    }

    public static String get() {
        return transmittableThreadLocal.get();
    }

    public static void remove() {
        transmittableThreadLocal.remove();
    }
}
