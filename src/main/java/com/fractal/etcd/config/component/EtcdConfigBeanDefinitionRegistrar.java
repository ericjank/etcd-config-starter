package com.fractal.etcd.config.component;

import com.fractal.etcd.config.processor.EtcdConfigListenerMethodProcessor;
import com.fractal.etcd.config.processor.EtcdConfigurationPropertiesBindingPostProcessor;
import com.fractal.etcd.config.processor.EtcdPropertySourcePostProcessor;
import com.fractal.etcd.config.processor.EtcdValueAnnotationBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author : qinxm
 * @date : 2021/7/24 9:03 上午
 */
public class EtcdConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    private static final Logger LOG = LoggerFactory.getLogger(EtcdConfigBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        registerCommon(registry);
        registerProcessor(registry);
    }

    private void registerCommon(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, EtcdConfigServiceImpl.BEAN_NAME, EtcdConfigServiceImpl.class);
        registerInfrastructureBeanIfAbsent(registry, EtcdComponent.BEAN_NAME, EtcdComponent.class);
    }

    private void registerProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, EtcdPropertySourcePostProcessor.BEAN_NAME, EtcdPropertySourcePostProcessor.class);
        registerInfrastructureBeanIfAbsent(registry, EtcdValueAnnotationBeanPostProcessor.BEAN_NAME, EtcdValueAnnotationBeanPostProcessor.class);
        registerInfrastructureBeanIfAbsent(registry, EtcdConfigurationPropertiesBindingPostProcessor.BEAN_NAME, EtcdConfigurationPropertiesBindingPostProcessor.class);
        registerInfrastructureBeanIfAbsent(registry, EtcdConfigListenerMethodProcessor.BEAN_NAME, EtcdConfigListenerMethodProcessor.class);

    }

    private static void registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass) {
        if (!registry.containsBeanDefinition(beanName)) {
            registerInfrastructureBean(registry, beanName, beanClass);
        } else {
            LOG.warn("register exists beanDefinition,beanName={}", beanName);
        }
    }


    private static void registerInfrastructureBean(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }
}
