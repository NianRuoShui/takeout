package org.nian.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Result<T> {
    private Integer code;  // 编码：1成功。0和其他数字失败
    private String msg;  // 错误信息
    private T data; // 数据
    private Map map = new HashMap();  // 动态数据

    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.setCode(1);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> error(String errMsg){
        Result<T> r = new Result<>();
        r.setMsg(errMsg);
        r.setCode(0);
        return r;
    }

    public Result<T> add(String msg, String value){
        this.map.put(msg, value);
        return this;
    }
}
