package com.yzx.common_utils.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzx
 * @create 2022-11-19 0:51
 */
@Data
public class R {
    private Result result;
    //失败的详细信息
    private String messageDeta;
    private Map<Object, Object> data = new HashMap<>();
    private Map<String, Object> data1 = new HashMap<>();
    private Integer code;

    private R() {

    }

    public static R ok() {
        R r = new R();
        r.result = Result.SUCCESS;
        r.setCode(200);
        return r;
    }

    public static R fail() {
        R r = new R();
        r.result = Result.FAIL;
        r.setCode(0);
        return r;
    }

    public static R payContinue() {
        R r = new R();
        r.setCode(250);
        return r;
    }

    public R setData(Object key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R setMessageDeta(String messageDeta) {
        this.messageDeta = messageDeta;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessageDeta() {
        return messageDeta;
    }

    public Map<Object, Object> getData(String item) {
        return data;
    }

    public R setData(Map<Object, Object> map) {
        this.data = map;
        return this;
    }

    public R setData1(Map<String, Object> map) {
        this.data1 = map;
        return this;
    }

    public Integer getCode() {
        return code;
    }


    public R setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "R{" +
                "result=" + result +
                ", messageDeta='" + messageDeta + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
