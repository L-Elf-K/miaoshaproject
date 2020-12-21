package com.miaoshaproject.error;

/**
 * Created by kk on 2020/12/19
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
