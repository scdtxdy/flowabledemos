package com.scd.flowablesystem.config;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.scd.flowablesystem.common.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-23 14:55
 */
@Slf4j
@Component
public class MetaHandler implements MetaObjectHandler {

  private static final String CREATE_BY = "createBy";
  private static final String CREATE_DATE = "createDate";
  private static final String CREATE_TIME = "createTime";
  private static final String UPDATE_BY = "updateBy";
  private static final String UPDATE_DATE = "updateDate";
  private static final String UPDATE_TIME = "updateTime";

  @Override
  public void insertFill(MetaObject metaObject) {

  }

  @Override
  public void updateFill(MetaObject metaObject) {

  }

  private boolean isBaseEntity(MetaObject metaObject) {
    return metaObject.getOriginalObject() instanceof BaseEntity || (metaObject.hasGetter(Constants.ENTITY) && metaObject.getValue(Constants.ENTITY) != null && metaObject.getValue(Constants.ENTITY) instanceof BaseEntity);
  }

  private void setBaseEntityFill(MetaObject metaObject, String createBy2, String createDate2, String createTime2) {
    Object createBy = getFieldValByName(createBy2, metaObject);
    if (createBy == null) {
//      String currUser = SecurityUtils.getUserId();
      String currUser = "admin";
      setFieldValByName(createBy2, currUser, metaObject);
    }
    DateTime now = DateUtil.date();
    Object createDate = getFieldValByName(createDate2, metaObject);
    if (createDate == null) {
      setFieldValByName(createDate2, now, metaObject);
    }
    Object createTime = getFieldValByName(createTime2, metaObject);
    if (createTime == null) {
      setFieldValByName(createTime2, now, metaObject);
    }
  }
}
