package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
   public String name;
   @Value("小红")
   public void setName(String name) {
      this.name = name;
   }
}
