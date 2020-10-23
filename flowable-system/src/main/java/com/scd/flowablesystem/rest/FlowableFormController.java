package com.scd.flowablesystem.rest;

import com.github.pagehelper.PageInfo;
import com.scd.flowablesystem.common.ReturnCode;
import com.scd.flowablesystem.entity.FlowableForm;
import com.scd.flowablesystem.service.FlowableFormService;
import com.scd.flowablesystem.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 21:50
 */
@RestController
@RequestMapping("/vue-admin-template/rest/form")
public class FlowableFormController {

  @Autowired
  private FlowableFormService flowableFormService;

  @GetMapping(value = "/list")
  public ReturnVo<PageInfo<FlowableForm>> list(){
    FlowableForm flowableForm = new FlowableForm();
    ReturnVo<PageInfo<FlowableForm>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    PageInfo<FlowableForm> pageInfo = flowableFormService.list(flowableForm);
    returnVo.setData(pageInfo);
    return returnVo;
  }



}
