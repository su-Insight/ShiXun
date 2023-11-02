package com.example.insight.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.insight.entity.User;
import com.example.insight.service.IUserService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/30 下午5:47
 * @Version: 1.0
 */
@Component
public class TokenUtils {

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    /*
     * @Author Insight
     * @Date 2023/10/30 下午5:48
     * @Description 生成token
     * @Param [userID, sign]
     * @Return java.lang.String
     * @Since version 1.0
     */
    public static String generateToken(String userID, String sign){
        return JWT.create().withAudience(userID)   // 将userId保存在Token里，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 6))  // 6小时后Token到期
                .sign(Algorithm.HMAC256(sign));
    }

    /*
     * @Author Insight
     * @Date 2023/10/30 下午9:46
     * @Description 获取当前登录信息
     * @Param []
     * @Return com.example.insight.entity.User
     * @Since version 1.0
     */
    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
