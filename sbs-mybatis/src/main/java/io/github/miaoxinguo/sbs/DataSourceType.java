package io.github.miaoxinguo.sbs;

import java.util.ArrayList;
import java.util.List;

/**
 *  数据源统一命名，所有用到数据源名的地方必须取这里的
 */
public class DataSourceType {
    public static final String MASTER = "dataSourceMaster";
    public static final String SLAVE_1 = "dataSourceSlave1";

    public static final List<String> SLAVES = new ArrayList<>();
    static {
        SLAVES.add(SLAVE_1);
    }
}
