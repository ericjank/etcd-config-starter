package com.fractal.etcd.config.util;

import com.fractal.etcd.config.model.ConfigType;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:24 上午
 */
public class ConfigTypeUtil {
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String YAML_SUFFIX = ".yml";

    /**
     * 获取配置文件类型
     *
     * @param configType
     * @param dataId
     * @return
     */
    public static String getTypeWithDataId(ConfigType configType, String dataId) {
        String type = configType.getType();
        if (dataId.endsWith(PROPERTIES_SUFFIX)) {
            type = ConfigType.PROPERTIES.getType();
        } else if (dataId.endsWith(YAML_SUFFIX)) {
            type = ConfigType.YAML.getType();
        }
        return type;
    }
}
