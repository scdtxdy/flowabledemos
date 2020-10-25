package com.scd.flowablesystem.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scd.flowablesystem.common.ReturnCode;
import com.scd.flowablesystem.entity.FlowableForm;
import com.scd.flowablesystem.service.FlowableFormService;
import com.scd.flowablesystem.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

  /**
   * 表单列表
   *
   * @param flowableForm
   * @param current
   * @param size
   * @author shang
   * @date 2020-10-25 18:49
   * @return list
   */
  @GetMapping(value = "/list")
  public ReturnVo<IPage<FlowableForm>> list(FlowableForm flowableForm, @RequestParam Integer current, @RequestParam Integer size){
    ReturnVo<IPage<FlowableForm>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    IPage<FlowableForm> formPage = flowableFormService.list(new Page<FlowableForm>(current, size), flowableForm);
    returnVo.setData(formPage);
    return returnVo;
  }

  /**
   * 根据id获取表单
   *
   * @param id
   * @author shang
   * @date 2020-10-25 18:49
   * @return queryById
   */
  @GetMapping(value = "/queryById")
  public ReturnVo<FlowableForm> queryById(@RequestParam String id) {
    ReturnVo<FlowableForm> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    FlowableForm flowableForm = flowableFormService.getById(id);
    returnVo.setData(flowableForm);
    return returnVo;
  }

  /**
   * 保存表单
   *
   * @param flowableForm
   * @author shang
   * @date 2020-10-25 18:49
   * @return save
   */
  @PostMapping(value = "/save")
  public ReturnVo<String> save(@RequestBody FlowableForm flowableForm) {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    flowableFormService.save(flowableForm);
    return returnVo;
  }

  /**
   * 编辑表单
   *
   * @param flowableForm
   * @author shang
   * @date 2020-10-25 18:49
   * @return update
   */
  @PutMapping(value = "/update")
  public ReturnVo<String> update(@RequestBody FlowableForm flowableForm) {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    flowableFormService.updateById(flowableForm);
    return returnVo;
  }

  /**
   * 批量删表单
   *
   * @param ids
   * @author shang
   * @date 2020-10-25 19:01
   * @return delete
   */
  @DeleteMapping(value = "/delete")
  public ReturnVo<String> delete(@RequestParam String ids) {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    if (ids == null || ids.trim().length() == 0) {
      returnVo.setCode(ReturnCode.FAIL);
      returnVo.setMsg("ids can't be empty");
      return returnVo;
    }
    String[] idsArr = ids.split(",");
    if (idsArr.length > 1) {
      flowableFormService.removeByIds(Arrays.asList(idsArr));
    } else {
      flowableFormService.removeById(idsArr[0]);
    }
    return returnVo;
  }
}
