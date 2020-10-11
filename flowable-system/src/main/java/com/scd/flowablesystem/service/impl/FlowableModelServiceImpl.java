package com.scd.flowablesystem.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scd.flowablesystem.common.ReturnCode;
import com.scd.flowablesystem.service.IFlowableModelService;
import com.scd.flowablesystem.vo.ModelVo;
import com.scd.flowablesystem.vo.ReturnVo;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author
 * @since
 */
@Service
public class FlowableModelServiceImpl implements IFlowableModelService {

  @Autowired
  private ModelRepository modelRepository;


  @Autowired
  protected ModelService modelService;

  protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();

  protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();


  /**
   * 添加模型
   *
   * @param modelVo
   * @return
   * @deprecated add
   */
  @Override
  public ReturnVo<String> addModel(ModelVo modelVo) throws UnsupportedEncodingException, XMLStreamException {
    ReturnVo<String> returnVo = new ReturnVo(ReturnCode.SUCCESS,"创建模板成功");
    InputStream inputStream = new ByteArrayInputStream(modelVo.getXml().getBytes());

    //获取 XMLInputFactory
    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();

    // inputStream 装进输入流 InputStreamReader
    InputStreamReader xmlIn = new InputStreamReader(inputStream, "UTF-8");

    //创建xml解析器
    XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);

    // xml json 转换bpmnModel
    BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);

    //模板验证
    ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
    List<ValidationError> errors = validator.validate(bpmnModel);
    if (CollectionUtils.isNotEmpty(errors)) {
      StringBuffer es = new StringBuffer();
      errors.forEach(ve -> es.append(ve.toString()).append("/n"));
      return new ReturnVo(ReturnCode.SUCCESS,"模板验证失败，原因: " + es.toString());
    }

    // 查看模板中是否有流程
    String fileName = bpmnModel.getMainProcess().getName();
    if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
      return new ReturnVo(ReturnCode.SUCCESS,"No process found in definition " + fileName);
    }

    // 自动布局
    if (bpmnModel.getLocationMap().size() == 0) {
      BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
      bpmnLayout.execute();
    }
    // 获取节点
    ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
    Process process = bpmnModel.getMainProcess();

    // 如果流程名不为空,获取流程名反之以id代替
    String name = process.getId();
    if (StringUtils.isNotEmpty(process.getName())) {
      name = process.getName();
    }
    String description = process.getDocumentation();

    // flowable的当前登陆人
    User createdBy = SecurityUtils.getCurrentUserObject();

    //查询是否已经存在流程模板
    Model newModel = new Model();
    List<Model> models = modelRepository.findByKeyAndType(process.getId(), AbstractModel.MODEL_TYPE_BPMN);
    if (CollectionUtils.isNotEmpty(models)) {
      Model updateModel = models.get(0);
      newModel.setId(updateModel.getId());
    }

    //组装模型
    newModel.setName(name);
    newModel.setKey(process.getId());
    newModel.setModelType(AbstractModel.MODEL_TYPE_BPMN);
    newModel.setCreated(Calendar.getInstance().getTime());
//    newModel.setCreatedBy(createdBy.getId());
    newModel.setCreatedBy("2");
    newModel.setDescription(description);
    newModel.setModelEditorJson(modelNode.toString());
    newModel.setLastUpdated(Calendar.getInstance().getTime());
//    newModel.setLastUpdatedBy(createdBy.getId());
    newModel.setLastUpdatedBy("2");
//    modelService.createModel(newModel, SecurityUtils.getCurrentUserObject());
    User user = new RemoteUser();
    user.setId("5");
    user.setFirstName("scd");
    user.setTenantId("5");
    modelService.createModel(newModel, user);
    return returnVo;
  }
}
