package com.fractal.etcd.config.annotation;

import java.lang.annotation.*;

/**
 * @author : qinxm
 * @date : 2021/7/24 8:59 上午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EtcdPropertySources {
    EtcdPropertySource[] value();
}
