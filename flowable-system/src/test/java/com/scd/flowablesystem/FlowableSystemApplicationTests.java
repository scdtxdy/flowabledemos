package com.scd.flowablesystem;

import org.junit.jupiter.api.Test;
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

  @Test
  void contextLoads() {
    System.out.println(annotationFontName);
  }

}
