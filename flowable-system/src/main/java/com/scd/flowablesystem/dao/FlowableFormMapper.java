package com.scd.flowablesystem.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * @param page
     * @author shang
     * @date 2020-10-21 23:27
     * @return list
     */
    List<FlowableForm> list(IPage<FlowableForm> page, @Param("entity")FlowableForm flowableForm);

}
