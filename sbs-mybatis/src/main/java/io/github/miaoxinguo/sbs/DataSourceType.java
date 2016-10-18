package io.github.miaoxinguo.sbs;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DataSourceType {
    public static final String MASTER = "dataSourceMaster";
    public static final String SLAVE_1 = "dataSourceSlave1";

    public static final List<String> SLAVES = new ArrayList<>();
    static {
        SLAVES.add(SLAVE_1);
    }
}
