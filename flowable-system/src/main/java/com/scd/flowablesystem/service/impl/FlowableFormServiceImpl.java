package com.scd.flowablesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scd.flowablesystem.common.base.BaseServiceImpl;
import com.scd.flowablesystem.dao.FlowableFormMapper;
import com.scd.flowablesystem.entity.FlowableForm;
import com.scd.flowablesystem.service.FlowableFormService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FlowableFormServiceImpl extends BaseServiceImpl<FlowableFormMapper, FlowableForm> implements FlowableFormService {

  @Resource
  private FlowableFormMapper flowableFormMapper;

  @Override
  public PageInfo<FlowableForm> list(FlowableForm flowableForm) {
    PageHelper.startPage(1, 10);
    List<FlowableForm> list = flowableFormMapper.list(flowableForm);
    PageInfo<FlowableForm> pageInfo = new PageInfo<>(list);
    return pageInfo;
  }
}
