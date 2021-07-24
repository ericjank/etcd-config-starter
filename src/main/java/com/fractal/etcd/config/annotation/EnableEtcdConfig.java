package com.fractal.etcd.config.annotation;

import com.fractal.etcd.config.component.EtcdConfigBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author : qinxm
 * @date : 2021/7/24 8:49 上午
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EtcdConfigBeanDefinitionRegistrar.class)
public @interface EnableEtcdConfig {
}
