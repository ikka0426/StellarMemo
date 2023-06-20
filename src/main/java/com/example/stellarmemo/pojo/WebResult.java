package com.example.stellarmemo.pojo;

public class WebResult<T> {
    private T data;

    //    状态码
    //        ERROR(-1, "失败"),
    //        SUCCESS(0, "成功");
    private int code;

    //    返回消息
    private String message;

    public WebResult() {
    }

    public WebResult(T data) {
        this.data = data;
    }

    public void success() {
        this.code = 1;
        message = "成功";

    }
    public void error() {
        this.code =-1;
        message = "出错";

    }

    public void success(String message) {
        this.code = 1;
        this.message = message;

    }
    public void error(String message) {
        this.code =-1;
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
