package com.scd.flowablesystem.service;


import com.github.pagehelper.PageInfo;
import com.scd.flowablesystem.entity.FlowableForm;
import org.flowable.engine.repository.Model;

import java.util.List;

public interface FlowableFormService {

  PageInfo<FlowableForm> list(FlowableForm flowableForm);
}
