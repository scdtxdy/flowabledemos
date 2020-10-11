package com.scd.flowablesystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.compatibility.spring.SpringFlowable5CompatibilityHandlerFactory;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.common.engine.impl.de.odysseus.el.misc.TypeConverter;
import org.flowable.common.engine.impl.de.odysseus.el.misc.TypeConverterImpl;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.job.service.SpringAsyncExecutor;
import org.flowable.ui.idm.properties.FlowableIdmAppProperties;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @Description: flowable配置
 * @Author: scd
 * @Since:18:44 2018/9/7
 */
@Configuration
@EnableConfigurationProperties({FlowableIdmAppProperties.class, FlowableModelerAppProperties.class})
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

  @Value("${flowable.activityFontName}")
  private String activityFontName;
  @Value("${flowable.annotationFontName}")
  private String annotationFontName;
  @Value("${flowable.labelFontName}")
  private String labelFontName;
  @Value("${flowable.xml.encoding}")
  private String xmlEncoding;


  /**
   * @author scd
   * @deprecated
   * 在配置文件中如果没有字段，使用@Value的时候就会忽略掉，不会报错
   * @return
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    configurer.setIgnoreUnresolvablePlaceholders(true);
    return configurer;
  }

  @Override
  public void configure(SpringProcessEngineConfiguration configure) {
    //配置中文
    configure.setActivityFontName(activityFontName);
    configure.setLabelFontName(labelFontName);
    configure.setAnnotationFontName(annotationFontName);
    //设置是否升级 false不升级  true升级
    configure.setDatabaseSchemaUpdate("true");
  }


  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public TypeConverter typeConverter() {
    return new TypeConverterImpl();
  }

  @Bean
  public SpringAsyncExecutor springAsyncExecutor() {
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();
    springAsyncExecutor.setTaskExecutor(processTaskExecutor());
    springAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1000);
    springAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1000);
    return springAsyncExecutor;
  }

  @Bean
  public TaskExecutor processTaskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }

  /**
   * BpmnXMLConverter
   *
   * @return BpmnXMLConverter
   */
  @Bean
  public BpmnXMLConverter createBpmnXMLConverter() {
    return new BpmnXMLConverter();
  }

  /**
   * BpmnJsonConverter
   *
   * @return BpmnJsonConverter
   */
  @Bean
  public BpmnJsonConverter createBpmnJsonConverter() {
    return new BpmnJsonConverter();
  }


  /**
   * 兼容V5
   *
   * @return
   */
  @Bean
  public SpringFlowable5CompatibilityHandlerFactory createSpringFlowable5CompatibilityHandlerFactory() {
    return new SpringFlowable5CompatibilityHandlerFactory();
  }

}
