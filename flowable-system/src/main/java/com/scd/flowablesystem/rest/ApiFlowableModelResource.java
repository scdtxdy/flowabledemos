package com.scd.flowablesystem.rest;

import com.scd.flowablesystem.common.ReturnCode;
import com.scd.flowablesystem.service.IFlowableModelService;
import com.scd.flowablesystem.vo.ModelVo;
import com.scd.flowablesystem.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author scd
 * @since
 */
@RestController
@RequestMapping("/vue-admin-template/rest/model")
public class ApiFlowableModelResource {

  @Autowired
  private IFlowableModelService flowableModelService;

  @PostMapping(value = "/addModel")
  @ResponseBody
  public ReturnVo<String> addModel(@RequestBody ModelVo params) throws UnsupportedEncodingException, XMLStreamException {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    return flowableModelService.addModel(params);
  }
}
