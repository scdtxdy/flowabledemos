package com.scd.flowablesystem.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scd.flowablesystem.common.base.BaseService;
import com.scd.flowablesystem.entity.FlowableForm;

public interface FlowableFormService extends BaseService<FlowableForm> {

  IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm);
}
