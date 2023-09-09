package nacos.demo.util;

import com.alibaba.ttl.TransmittableThreadLocal;

public class HeaderHolder {

    private static final TransmittableThreadLocal<String> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String parkCode) {
        THREAD_LOCAL.set(parkCode);
    }

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
