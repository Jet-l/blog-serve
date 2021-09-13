package com.example.blogserve.model.enums;

public enum ErrorInfoEnum implements IErrorInfo {
    SUCCESS(0, "操作成功"),
    TOKEN_EXPIRED(4001, "token已过期"),
    NOT_ADMIN_USER(4002, "非管理员用户操作"),
    RESOURCES_NOT_FOUND(4003, "找不到相应资源"),
    MISSING_PARAMETERS(4004, "参数缺失"),
    ARTICLE_NOT_FOUND(4005, "找不到对应文章"),
    INCOMPLETE_PARAMETERS(4006, "请求字段不完整"),
    UN_INIT(4007, "博客尚未初始化"),
    INVALID_LOGIN_TYPE(4008, "非法登录类型"),
    UNKNOWN_ERROR(5000, "出现未知错误"),
    IMAGE_UPLOAD_ERROR(5001, "图片上传失败"),
    INVALID_ID(4008,"id不存在"),
    USERNAME_PASSWORD_ERROR(4009,"用户名或密码错误"),
    NOT_LOGIN(4002,"请先登录再进行访问"),
    NO_AUTHORITY(4005,"没有权限访问该接口"),
    TOKEN_INVALID(4004,"无效的token");

    private int code;
    private String msg;

    @Override
    public String getMsg() {
        return msg;
    }

    ErrorInfoEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
