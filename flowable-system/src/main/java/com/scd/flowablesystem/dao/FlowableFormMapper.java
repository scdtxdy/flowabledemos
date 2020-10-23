package com.scd.flowablesystem.dao;

import com.scd.flowablesystem.common.base.CoreMapper;
import com.scd.flowablesystem.entity.FlowableForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FlowableFormMapper extends CoreMapper<FlowableForm> {

    /**
     * 查询流程表单列表
     *
     * @param entity
     * @author shang
     * @date 2020-10-21 23:27
     * @return list
     */
    public List<FlowableForm>  list(@Param("entity") FlowableForm entity);
}
