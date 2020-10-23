package com.scd.flowablesystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scd.flowablesystem.common.base.BaseEntity;
import com.sun.istack.internal.NotNull;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("T_FLOWABLE_FORM")
public class FlowableForm extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @TableId
  @NotNull
  private String formKey;

  @NotNull
  private String formName;

  private String formJson;
}
