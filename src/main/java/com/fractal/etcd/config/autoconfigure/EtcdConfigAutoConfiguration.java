package com.fractal.etcd.config.autoconfigure;

import com.fractal.etcd.config.annotation.EnableEtcdConfig;
import com.fractal.etcd.config.model.EtcdConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:00 上午
 */
@Configuration
@EnableConfigurationProperties(value = EtcdConfigProperties.class)
@ConditionalOnProperty(name = EtcdConfigConstants.CONFIG_PROPERTIES_ENABLED, havingValue = "true")
@EnableEtcdConfig
public class EtcdConfigAutoConfiguration {
}
