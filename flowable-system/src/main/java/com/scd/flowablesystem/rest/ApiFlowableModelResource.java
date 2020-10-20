package com.scd.flowablesystem.rest;

import com.scd.flowablesystem.common.ModelRequest;
import com.scd.flowablesystem.common.ModelResponse;
import com.scd.flowablesystem.common.ResponseFactory;
import com.scd.flowablesystem.common.ReturnCode;
import com.scd.flowablesystem.service.IFlowableModelService;
import com.scd.flowablesystem.vo.ModelVo;
import com.scd.flowablesystem.vo.ReturnVo;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
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
  private IFlowableModelService flowableModelService;
  @Autowired
  private ModelService modelService;
  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private ResponseFactory responseFactory;

  @GetMapping("getMldelsByPage")
  @ResponseBody
  public ReturnVo<org.flowable.engine.repository.Model> getModelsByPage(){
    ReturnVo<org.flowable.engine.repository.Model> returnVo = new ReturnVo<>();
    ModelQuery modelQuery = repositoryService.createModelQuery();
    List<org.flowable.engine.repository.Model> list = modelQuery.list();
    returnVo.setDatas(list);
    return returnVo;
  }

  @PostMapping(value = "/saveModel")
  @Transactional(rollbackFor = Exception.class)
  public ReturnVo<String> createModel(@RequestBody ModelRequest modelRequest){
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

  @PostMapping(value = "/addModel")
  @ResponseBody
  public ReturnVo<String> addModel(@RequestBody ModelVo params) throws UnsupportedEncodingException, XMLStreamException {
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
    return flowableModelService.addModel(params);
  }

  @GetMapping(value = "/getModels")
  @ResponseBody
  public ReturnVo<org.flowable.engine.repository.Model> getModels(){
    ReturnVo<org.flowable.engine.repository.Model>returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "ok");
    ModelQuery modelQuery = repositoryService.createModelQuery();
    List<org.flowable.engine.repository.Model> list = modelQuery.list();
    returnVo.setDatas(list);
    return returnVo;
  }

  @GetMapping(value = "/getModelsById")
  @ResponseBody
  public ReturnVo<ModelVo> getModelById(@RequestParam String modelId) throws UnsupportedEncodingException {
    ReturnVo<ModelVo> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "ok");
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
    return returnVo;
  }

  @PostMapping("/deploy")
  @ResponseBody
  public ReturnVo<String> deploy(String modelId){
    ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.FAIL, "部署流程失败！");
    if (StringUtils.isBlank(modelId)) {
      returnVo.setMsg("模板ID不能为空！");
      return returnVo;
    }
    Model model = modelService.getModel(modelId);
    if (model == null) {
      returnVo.setMsg("模板ID不存在！");
      return returnVo;
    }
    BpmnModel bpmnModel = modelService.getBpmnModel(model);
    //到时候需要添加分类
    String categoryCode = "1000";
    //添加隔离信息
    String tenantId = "flow";
    Deployment deploy = repositoryService
        .createDeployment()
        .name(model.getName())
        .category(categoryCode)
        .key(model.getKey())
        .addBpmnModel(model.getKey() + ".bpmn", bpmnModel)
        .tenantId(tenantId)
        .deploy();
    returnVo.setData(deploy.getId());
    returnVo.setMsg("部署流程成功！");
    returnVo.setCode(ReturnCode.SUCCESS);
    return returnVo;
  }

}
