package com.scd.flowablesystem.dao;

import com.scd.flowablesystem.entity.FlowableForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FlowableFormMapper{

    /**
     * 查询流程表单列表
     *
     * @param entity
     * @author shang
     * @date 2020-10-21 23:27
     * @return list
     */
    public List<FlowableForm> list(@Param("entity") FlowableForm entity);
}
