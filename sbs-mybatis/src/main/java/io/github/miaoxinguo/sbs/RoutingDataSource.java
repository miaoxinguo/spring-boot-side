package io.github.miaoxinguo.sbs;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    // 数据源名称线程池
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void setDataSource(String datasource) {
        holder.set(datasource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return holder.get();
    }
}
