package com.fractal.etcd.config.exception;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:13 上午
 */
public class EtcdConfigException extends RuntimeException {

    public EtcdConfigException(String message) {
        super(message);
    }

    public EtcdConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}