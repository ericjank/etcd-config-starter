package com.fractal.etcd.config.processor;

import com.fractal.etcd.config.annotation.EtcdConfigListener;
import com.fractal.etcd.config.util.ConfigParseUtil;
import com.fractal.etcd.config.util.ConfigTypeUtil;
import com.fractal.etcd.config.EtcdConfigService;
import com.fractal.etcd.config.component.EtcdConfigServiceImpl;
import com.fractal.etcd.config.event.AbstractNotifyUserListener;
import com.fractal.etcd.config.exception.EtcdConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Properties;

import static java.lang.reflect.Modifier.*;

/**
 * ε€η{@link EtcdConfigListener}
 * @author : qinxm
 * @date : 2021/7/24 9:16 δΈε
 */
public class EtcdConfigListenerMethodProcessor implements BeanPostProcessor, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(EtcdConfigListenerMethodProcessor.class);

    public static final String BEAN_NAME = "etcdConfigListenerMethodProcessor";

    private final Class<EtcdConfigListener> annotationType = EtcdConfigListener.class;
    private EtcdConfigService etcdConfigService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        processBean(beanName, bean, bean.getClass());
        return bean;
    }

    private void processBean(String beanName, Object bean, Class<?> beanClass) {

        ReflectionUtils.doWithMethods(beanClass, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method)
                    throws IllegalArgumentException {
                EtcdConfigListener annotation = AnnotationUtils.getAnnotation(method, annotationType);
                if (annotation != null) {

                    checkCandidateMethod(method, annotation);
                    processListenerMethod(bean, method, annotation);
                }
            }

        }, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                return isListenerMethod(method);
            }
        });

    }

    private void checkCandidateMethod(Method method, EtcdConfigListener annotation) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        //ιͺθ―εζ°δΈͺζ°
        if (parameterTypes.length != 1) {
            throw new EtcdConfigException("@EtcdConfigListener method " + method + " parameters count must be one");
        }

        Class<?> targetType = parameterTypes[0];

        //εζ°η±»εεΏι‘»ζ―StringζProperties
        if (!targetType.equals(String.class) && !targetType.equals(Properties.class)) {
            throw new EtcdConfigException("@EtcdConfigListener method " + method + " parameter type must be String or Properties");
        }

        String dataId = annotation.dataId();
        if (StringUtils.isEmpty(dataId)) {
            throw new EtcdConfigException("@EtcdConfigListener method " + method + " dataId is null");
        }
    }


    /**
     * ζ―ε¦η¬¦εlistenerζΉζ³ηζ‘δ»Ά
     *
     * @param method
     * @return θΏεtrueοΌζδΌθΏθ‘εη»­ηMethodCallback
     */
    private boolean isListenerMethod(Method method) {

        int modifiers = method.getModifiers();

        Class<?> returnType = method.getReturnType();

        return isPublic(modifiers) && !isStatic(modifiers) && !isNative(modifiers)
                && !isAbstract(modifiers) && void.class.equals(returnType);
    }

    private void processListenerMethod(Object bean, Method method, EtcdConfigListener listener) {

        String dataId = listener.dataId();
        //ζ³¨εη¨ζ·θͺε?δΉηε¬
        etcdConfigService.addListener(dataId, new AbstractNotifyUserListener(dataId, listener.timeout()) {
            @Override
            protected void onReceived(String configInfo) {
                Object parameterValue = configInfo;
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> targetType = parameterTypes[0];

                if (targetType.equals(Properties.class)) {
                    String type = ConfigTypeUtil.getTypeWithDataId(listener.type(), dataId);
                    parameterValue = ConfigParseUtil.toProperties(configInfo, type);
                }

                ReflectionUtils.invokeMethod(method, bean, parameterValue);
                LOG.info("notify user dataId={},beanName={},method={}", dataId, bean.getClass().getSimpleName(), method.getName());
            }
        });
        LOG.info("add notifyUserListener success dataId={},beanName={},method={}", dataId, bean.getClass().getSimpleName(), method.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.etcdConfigService = applicationContext.getBean(EtcdConfigServiceImpl.BEAN_NAME, EtcdConfigServiceImpl.class);
    }
}
