package org.nian.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Result<T> {
    private Integer code;  // 结果状态码：1 表示成功，0 和其他数字表示失败
    private String msg;  // 结果信息，用于存储错误信息或成功信息
    private T data; // 泛型数据字段，用于存储返回的数据
    private Map map = new HashMap();  // 动态数据字段，用于存储附加信息


    // 成功的静态工厂方法，用于构建一个成功的 Result 对象
    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.setCode(1); // 设置状态码为 1，表示成功
        r.setData(data); // 设置返回的数据
        return r; // 返回构建好的 Result 对象
    }

    // 错误的静态工厂方法，用于构建一个表示错误的 Result 对象
    public static <T> Result<T> error(String errMsg){
        Result<T> r = new Result<>();
        r.setMsg(errMsg); // 设置错误信息
        r.setCode(0); // 设置状态码为 0，表示失败
        return r; // 返回构建好的 Result 对象
    }

    // 向 Result 对象的动态数据字段中添加数据
    public Result<T> add(String msg, String value){
        this.map.put(msg, value); // 将数据添加到 map 中
        return this; // 返回当前 Result 对象，以支持链式调用
    }
}
