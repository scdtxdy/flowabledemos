package com.scd.flowablesystem;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author
 * @since
 */
@SpringBootTest
class FlowableSystemApplicationTests {

  @Value("${flowable.activityFontName}")
  private String activityFontName;

  @Value("${flowable.annotationFontName}")
  private String annotationFontName;

  @Autowired
  private RepositoryService repositoryService;

  @Test
  void contextLoads() {
    Model model = repositoryService.newModel();
    model.setKey("111111111111");
    model.setName("scdtest");
    model.setVersion(1);
    model.setMetaInfo("111111");
    model.setTenantId("1");
    model.setCategory("1");
    repositoryService.saveModel(model);
  }

}
