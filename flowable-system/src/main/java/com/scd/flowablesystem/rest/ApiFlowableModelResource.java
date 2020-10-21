package com.scd.flowablesystem.rest;

import com.scd.flowablesystem.common.*;
import com.scd.flowablesystem.service.IFlowableModelService;
import com.scd.flowablesystem.util.SpringContextUtils;
import com.scd.flowablesystem.vo.ModelVo;
import com.scd.flowablesystem.vo.ReturnVo;
import com.scd.flowablesystem.wapper.IListWrapper;
import com.scd.flowablesystem.wapper.ModelListWrapper;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author scd
 * @since
 */
@RestController
@RequestMapping("/vue-admin-template/rest/model")
public class ApiFlowableModelResource {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private ResponseFactory responseFactory;

  @Autowired
  private ManagementService managementService;

  @Autowired
  private IListWrapper listWrapper;

  /**
   * 模板列表
   *
   * @author shang
   * @date 2020-10-20 16:31
   * @return getModelsByPage
   */
  @GetMapping("getMldelsByPage")
  @ResponseBody
  public ReturnVo<org.flowable.engine.repository.Model> getModelsByPage(){
    ReturnVo<org.flowable.engine.repository.Model> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    ModelQuery modelQuery = repositoryService.createModelQuery();
    List<Model> list = modelQuery.list();
    returnVo.setDatas(listWrapper.execute(list));
    return returnVo;
  }

  /**
   * 创建流程模板
   *
   * @param modelRequest
   * @author shang
   * @date 2020-10-20 16:12
   * @return createModel
   */
  @PostMapping(value = "/saveModel")
  @Transactional(rollbackFor = Exception.class)
  public ReturnVo<String> saveModel(@RequestBody ModelRequest modelRequest){
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    long countNum = repositoryService.createModelQuery().modelKey(modelRequest.getKey()).count();
    if (countNum > 0) {
      throw new FlowableObjectNotFoundException("ModelKey already exists with id " + modelRequest.getKey());
    }
    org.flowable.engine.repository.Model model = repositoryService.newModel();
    model.setKey(modelRequest.getKey());
    model.setName(modelRequest.getName());
    model.setVersion(1);
    model.setMetaInfo(modelRequest.getMetaInfo());
    model.setTenantId(modelRequest.getTenantId());
    model.setCategory(modelRequest.getCategory());
    repositoryService.saveModel(model);
    return returnVo;
  }

  /**
   * 根据id获取流程模板
   *
   * @param modelId
   * @author shang
   * @date 2020-10-20 16:13
   * @return getModelById
   */
  @GetMapping(value = "/getModelsById")
  @ResponseBody
  public ReturnVo<ModelResponse> getModelById(@RequestParam String modelId) throws UnsupportedEncodingException {
    ReturnVo<ModelResponse> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "ok");
    org.flowable.engine.repository.Model model = repositoryService.getModel(modelId);
    if (model == null) {
      throw new FlowableObjectNotFoundException("No model found with id " + modelId);
    }
    ModelResponse modelResponse = responseFactory.createModelResponse(model);
    if (model.hasEditorSource()) {
      byte[] editorBytes = repositoryService.getModelEditorSource(model.getId());
      String editor = new String(editorBytes, "UTF-8");
      modelResponse.setEditor(editor);
    }
    returnVo.setData(modelResponse);
    return returnVo;
  }

  /**
   * 修改流程模板
   *
   * @param modelRequest
   * @author shang
   * @date 2020-10-20 16:13
   * @return update
   */
  @PutMapping(value = "/updateModel")
  @Transactional(rollbackFor = Exception.class)
  @ResponseBody
  public ReturnVo<String> update(@RequestBody ModelRequest modelRequest) {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "ok");
    managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getKey(),
        modelRequest.getName(), modelRequest.getCategory(), modelRequest.getDescription()));
    return returnVo;
  }

  /**
   * 保存流程设计
   *
   * @param modelRequest
   * @author shang
   * @date 2020-10-21 17:15
   * @return saveModelEditor
   */
  @PutMapping(value = "/saveModelEditor")
  @Transactional(rollbackFor = Exception.class)
  public ReturnVo<String> saveModelEditor(@RequestBody ModelRequest modelRequest) {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "ok");
    managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getEditor()));
    return returnVo;
  }


}
