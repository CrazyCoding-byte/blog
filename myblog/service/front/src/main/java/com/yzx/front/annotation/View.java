package com.yzx.front.annotation;

import java.lang.annotation.*;

/**
 * @author yzx
 * @create 2023-01-13 1:03
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
  public String defaultValue();
}
