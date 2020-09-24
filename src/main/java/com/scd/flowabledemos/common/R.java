package com.scd.flowabledemos.common;

import lombok.Data;

@Data
public class R {

   private String msg;

   private Integer code;

   private static R r = new R();

   public static R ok(String msg){
       r.msg = msg;
       r.code = 200;
       return r;
   }

   public static R error(String msg){
       r.setMsg(msg);
       r.setCode(500);
       return r;
   }

   public static R ok(){
       r.setCode(200);
       r.setMsg("操作成功");
       return r;
   }

}
