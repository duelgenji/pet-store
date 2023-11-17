package com.knight.petstore.model;

import lombok.Data;

@Data
public class Result<T> {
    private String status;
    private String msg;
    private T result;
    public Result(String status, T result) {
        this.status = "";
        this.msg = "";
        this.result = result;
    }

    public static Result<Void> success() {
        return new Result<>("1", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>("1", data);
    }

    public static <T> Result<T> failure() {
        return new Result<>("0", null);
    }


}