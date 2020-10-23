package com.scd.flowablesystem.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.scd.flowablesystem.common.methods.DeleteAll;
import com.scd.flowablesystem.common.methods.FindOne;

import java.util.List;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-23 16:10
 */
public class MySqlInjector extends DefaultSqlInjector {
  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);
    //增加自定义方法
    methodList.add(new FindOne());
    methodList.add(new DeleteAll());
    /**
     * 以下 3 个为内置选装件
     * 头 2 个支持字段筛选函数
     */
    // 例: 不要指定了 update 填充的字段
    methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
    methodList.add(new AlwaysUpdateSomeColumnById());
    methodList.add(new LogicDeleteByIdWithFill());
    return methodList;
  }
}
