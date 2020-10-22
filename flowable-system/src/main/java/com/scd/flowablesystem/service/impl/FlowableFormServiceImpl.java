package com.scd.flowablesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scd.flowablesystem.dao.FlowableFormMapper;
import com.scd.flowablesystem.entity.FlowableForm;
import com.scd.flowablesystem.service.FlowableFormService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlowableFormServiceImpl implements FlowableFormService {

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
