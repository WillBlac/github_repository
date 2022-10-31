package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.SMSUtils;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)){
            //生成随机4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}", code);
            //调用阿里云短信api发送短信
            SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            //生成的验证码保存起来
            session.setAttribute(phone, code);

            return R.success("手机验证码发送成功");
        }
        return R.error("验证码发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从session中获得验证码
        String codeInSession = (String) session.getAttribute(phone);


        //比对
        if (codeInSession != null && codeInSession.equals(code)){
            //比对成功

            //判断手机号是否为新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);

            if (user == null){
                //判断为新用户
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }
}
