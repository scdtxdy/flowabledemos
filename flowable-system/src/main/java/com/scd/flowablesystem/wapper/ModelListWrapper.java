package com.scd.flowablesystem.wapper;

import com.scd.flowablesystem.common.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-21 13:37
 */
@Component
public class ModelListWrapper implements IListWrapper{

  @Autowired
  private ResponseFactory responseFactory;

  /**
   * @param list
   * @return execute
   * @author shang
   * @date 2020-10-21 12:00
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public List execute(List list) {
    return responseFactory.createModelResponseList(list);
  }
}
