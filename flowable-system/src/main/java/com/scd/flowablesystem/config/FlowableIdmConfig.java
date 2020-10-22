package com.scd.flowablesystem.config;

import com.scd.flowablesystem.identity.CustomGroupEntityManager;
import com.scd.flowablesystem.identity.CustomUserEntityManager;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 17:37
 */
@Configuration
public class FlowableIdmConfig implements EngineConfigurationConfigurer<SpringIdmEngineConfiguration> {
  @Override
  public void configure(SpringIdmEngineConfiguration configuration) {
    configuration.setGroupEntityManager(customGroupEntityManager(configuration));
    configuration.setUserEntityManager(customUserEntityManager(configuration));
  }

  @Bean
  public CustomGroupEntityManager customGroupEntityManager(IdmEngineConfiguration configuration) {
    return new CustomGroupEntityManager(configuration, configuration.getGroupDataManager());
  }

  @Bean
  public CustomUserEntityManager customUserEntityManager(IdmEngineConfiguration configuration) {
    return new CustomUserEntityManager(configuration, configuration.getUserDataManager());
  }
}
