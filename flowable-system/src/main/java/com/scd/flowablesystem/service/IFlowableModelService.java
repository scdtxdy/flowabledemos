package com.scd.flowablesystem.service;

import com.scd.flowablesystem.vo.ModelVo;
import com.scd.flowablesystem.vo.ReturnVo;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;

public interface IFlowableModelService {


  /**
   * 添加模型
   * @param modelVo
   * @return
   */
  public ReturnVo<String> addModel(ModelVo modelVo) throws UnsupportedEncodingException, XMLStreamException;
}
