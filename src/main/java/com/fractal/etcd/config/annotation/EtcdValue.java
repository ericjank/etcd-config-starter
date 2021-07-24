package com.fractal.etcd.config.annotation;

import org.springframework.beans.factory.annotation.Value;
import java.lang.annotation.*;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:00 上午
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EtcdValue {
    /**
     * The actual value expression: for example {@code #{systemProperties.myProp}}.
     */
    String value();

    /**
     * 当etcd配置更新时，是否从environment刷新到bean属性
     *
     * @return
     */
    boolean autoRefreshed() default true;
}
