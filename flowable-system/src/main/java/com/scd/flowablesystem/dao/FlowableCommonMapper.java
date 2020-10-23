package com.scd.flowablesystem.dao;

import com.scd.flowablesystem.common.base.CoreMapper;
import com.scd.flowablesystem.vo.ProcessDefinitionVo;
import com.scd.flowablesystem.vo.query.ProcessInstanceQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 17:45
 */
@Repository
@Mapper
public interface FlowableCommonMapper extends CoreMapper {
  /**
   * 查询用户信息
   *
   * @param query
   * @return
   */
  List<User> selectUserByQueryCriteria(UserQueryImpl query);

  /**
   * 查询用户信息
   *
   * @param query
   * @return
   */
  long selectUserCountByQueryCriteria(UserQueryImpl query);

  /**
   * 根据Flowable GroupQueryImpl查询岗位列表
   *
   * @param query
   * @return
   */
  List<Group> selectGroupByQueryCriteria(GroupQueryImpl query);

  /**
   * 根据Flowable GroupQueryImpl查询岗位数量
   *
   * @param query
   * @return
   */
  long selectGroupCountByQueryCriteria(GroupQueryImpl query);

  /**
   * 查询我的流程汇总信息
   * @param processInstanceQueryVo
   * @return
   */
  List<ProcessDefinitionVo> listMyInvolvedSummary(ProcessInstanceQueryVo processInstanceQueryVo);
}
