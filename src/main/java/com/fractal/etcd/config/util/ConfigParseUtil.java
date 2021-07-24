package com.fractal.etcd.config.util;

import com.fractal.etcd.config.exception.EtcdConfigException;
import com.fractal.etcd.config.model.ConfigType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * 配置内容解析
 * @author : qinxm
 * @date : 2021/7/24 9:23 上午
 */
public class ConfigParseUtil {
    public static Properties toProperties(String content, String configType) {

        if (configType.equals(ConfigType.PROPERTIES.getType())) {
            return get(content);
        } else if (configType.equals(ConfigType.YAML.getType())) {
            return YamlUtil.toProperties(content);
        }

        throw new IllegalArgumentException("configType");
    }

    private static Properties get(String content) {
        Properties properties = new Properties();
        try {
            if (StringUtils.hasText(content)) {
                properties.load(new StringReader(content));
            }
        } catch (IOException e) {
            throw new EtcdConfigException("ConfigParse", e);
        }
        return properties;
    }
}
