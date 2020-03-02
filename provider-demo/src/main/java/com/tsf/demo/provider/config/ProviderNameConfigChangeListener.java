package com.tsf.demo.provider.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.consul.config.watch.ConfigChangeCallback;
import org.springframework.cloud.consul.config.watch.ConfigChangeListener;
import org.springframework.cloud.consul.config.watch.ConfigProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigChangeListener(prefix = "provider.config", value = { "name" })
public class ProviderNameConfigChangeListener implements ConfigChangeCallback {

    private static final Logger log = LoggerFactory.getLogger(ProviderNameConfigChangeListener.class);

    @Override
    public void callback(ConfigProperty lastConfigProperty, ConfigProperty newConfigProperty) {
        log.info("[TSF SDK] Configuration Change Listener: key: {}, old value: {}, new value: {}",
                lastConfigProperty.getKey(), lastConfigProperty.getValue(), newConfigProperty.getValue());
    }

}
