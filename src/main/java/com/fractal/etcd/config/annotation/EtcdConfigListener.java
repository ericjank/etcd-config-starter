package com.fractal.etcd.config.annotation;

import com.fractal.etcd.config.model.ConfigType;
import java.lang.annotation.*;

/**
 * 当etcd中的dataId的配置发生变化时，触发回调
 * @author : qinxm
 * @date : 2021/7/24 8:55 上午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface EtcdConfigListener {
    /**
     * 配置集
     *
     * @return
     */
    String dataId();

    /**
     * 配置的内容类型
     * 如果dataId以.properties结尾或.yml结尾，则忽略这个type值
     *
     * @return
     */
    ConfigType type() default ConfigType.PROPERTIES;

    /**
     * 最大执行时间，单位毫秒
     *
     * @return
     */
    long timeout() default 5000L;
}
