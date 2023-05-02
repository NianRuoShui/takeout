package org.nian.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/*** 用于全局异常处理的类 */
//RestControllerAdvice捕获RestController
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    //异常处理
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
//        log.error(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry")) {
            //对字符串切片
            String[] split = exception.getMessage().split(" ");
            //字符串格式是固定的，所以这个位置必然是username
            String username = split[2];
            //拼串作为错误信息返回
            return Result.error("用户名" + username + "已存在");
        }
        //如果是别的错误那我也没招儿了
        return Result.error("未知错误");
    }
}
