package com.scd.flowablesystem.vo;

import liquibase.pro.packaged.T;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author
 * @since
 */
public class ReturnVo<T> implements Serializable {
  private static final long SERIAL_VERSION_UID = -5580228202640516960L;
  private String code;
  private String msg;
  private T data;
  private List<T> datas;

  public ReturnVo()
  {
  }

  public ReturnVo(String code, String msg)
  {
    this.code = code;
    this.msg = msg;
  }

  public ReturnVo(String code, String msg, T data, List<T> datas)
  {
    this.code = code;
    this.msg = msg;
    this.data = data;
    this.datas = datas;
  }

  public ReturnVo(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public List<T> getDatas() {
    return this.datas;
  }

  public void setDatas(List<T> datas) {
    this.datas = datas;
  }
}
