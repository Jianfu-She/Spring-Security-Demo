package com.sjf.dto;

/**
 * Created by SJF on 2017/2/17.
 */
public class ErrorInfo {

    private int status;
    private String info;

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(int status, String info) {
        this.status = status;
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
