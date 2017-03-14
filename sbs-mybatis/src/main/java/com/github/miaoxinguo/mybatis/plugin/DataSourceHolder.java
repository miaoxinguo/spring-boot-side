package com.github.miaoxinguo.mybatis.plugin;

public class DataSourceHolder {

    // 数据源名称
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    /**
     * 设置当前线程的数据源
     */
    public static void set(String datasource) {
        holder.set(datasource);
    }

    /**
     * 获取数据源名
     */
    public static String get() {
        return holder.get();
    }
}
