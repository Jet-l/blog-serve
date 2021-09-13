package com.example.blogserve.constants;

public class JwtConstants {

    public static final String ROLE_CLAIMS = "role";

    //过期时间
    public static final long EXPIRATION = 60*60*24L;
    //私钥
    public static final String JWT_SECRET_KEY = "uED44b4NzOQlodczbRDMvbtjDMNCiS7BUpe39Mcq";
    //前缀
    public static final String TOKEN_PREFIX = "nefu_";
    //token类型
    public static final String TOKEN_TYPE = "JWT";
    //签发者
    public static final String TOKEN_ISSUER = "sakura";
    //加在响应头哪个字段
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 在 Redis 中存储的key
     */
    public static final String REDIS_KEY = "jwt_token";

}
