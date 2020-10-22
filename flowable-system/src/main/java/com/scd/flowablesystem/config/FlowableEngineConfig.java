package com.scd.flowablesystem.config;

import com.google.common.collect.Maps;
import com.scd.flowablesystem.common.listener.TaskBeforeListener;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 17:06
 */
@Configuration
public class FlowableEngineConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

  @Autowired
  private TaskBeforeListener taskBeforeListener;

  @Override
  public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {

  }

  @Bean
  public ProcessDiagramGenerator processDiagramGenerator() {
    return new CustomProcessDiagramGenerator();
  }

  private Map<String, List<FlowableEventListener>> customFlowableListeners () {
    Map<String, List<FlowableEventListener>> listenerMap = Maps.newHashMap();
    listenerMap.put(FlowableEngineEventType.TASK_CREATED.name(),
        new ArrayList<>(Collections.singletonList(taskBeforeListener)));
    return listenerMap;
  }
}
