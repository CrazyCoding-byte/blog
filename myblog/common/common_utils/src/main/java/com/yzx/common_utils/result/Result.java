package com.yzx.common_utils.result;

/**
 * @author yzx
 * @create 2022-11-19 0:50
 */
public enum Result {
    SUCCESS(200, "成功"),
    FAIL(0, "失败");

    private Integer code;
    private String message;

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
