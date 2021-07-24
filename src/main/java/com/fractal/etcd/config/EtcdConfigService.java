package com.fractal.etcd.config;

import com.fractal.etcd.config.event.Listener;
import com.fractal.etcd.config.exception.EtcdConfigException;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:22 上午
 */
public interface EtcdConfigService {
    /**
     * 从配置中心获取配置
     *
     * @param dataId
     * @return
     * @throws EtcdConfigException
     */
    String getConfig(String dataId) throws EtcdConfigException;

    /**
     * 监听dataId的配置变化
     *
     * @param dataId
     * @param listener
     */
    void addListener(String dataId, Listener listener);

    /**
     * 监听所有dataId的配置变化
     *
     * @param listener
     */
    void addAllListener(Listener listener);
}
