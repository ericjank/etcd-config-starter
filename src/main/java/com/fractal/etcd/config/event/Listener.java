package com.fractal.etcd.config.event;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:06 上午
 */
public interface Listener {
    /**
     * 配置内容变更通知
     *
     * @param configInfo
     */
    void receiveConfigInfo(String configInfo);
}
