package com.scd.flowablesystem.common.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-23 14:59
 */
public abstract class BaseEntity {

  private static final long serialVersionUID = 1L;

  @TableField(fill = FieldFill.INSERT)
  @ExcelIgnore
  private String createBy;

  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelIgnore
  private Date createDate;

  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ExcelIgnore
  private Date createTime;

  @TableField(fill = FieldFill.UPDATE)
  @ExcelIgnore
  private String updateBy;

  @TableField(fill = FieldFill.UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelIgnore
  private Date updateDate;

  @TableField(fill = FieldFill.UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ExcelIgnore
  private Date updateTime;
}
