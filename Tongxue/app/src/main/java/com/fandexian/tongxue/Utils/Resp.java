package com.fandexian.tongxue.Utils;

import com.fandexian.tongxue.Bean.UserInfo;

/**
 * Created by fandexian on 16/4/15.
 */
public  class Resp <T>{
    private T data;
    private String message;
    private String status;

    public Resp() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
