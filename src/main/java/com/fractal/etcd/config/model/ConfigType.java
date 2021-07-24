package com.fractal.etcd.config.model;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:14 上午
 */
public enum ConfigType {
    PROPERTIES("properties"),

    YAML("yaml");

    String type;

    ConfigType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
