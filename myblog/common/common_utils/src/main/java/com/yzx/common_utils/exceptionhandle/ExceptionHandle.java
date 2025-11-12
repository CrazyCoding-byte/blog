package com.yzx.common_utils.exceptionhandle;

import com.yzx.common_utils.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yzx
 * @create 2022-11-19 19:58
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandle {


    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R myExceptionHandle(MyException e) {
        log.error(e.getMessage(), e);
        return R.fail().setMessageDeta(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exceptionHandle(Exception e) {
        log.error(e.getMessage(), e);
        e.printStackTrace();
        return R.fail().setMessageDeta(e.getMessage());
    }

}
