package com.module.controller;

import com.core.base.controller.BaseController;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.protocol.security.LoginReqData;
import com.module.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆Controller
 */
@AccessRight(rightsCode = "-1")
@RestController
@RequestMapping("/admin")
public class LoginController extends BaseController {


    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public BaseResponse login(@RequestBody LoginReqData reqData) {
        BaseResponse result = new BaseResponse();
        try{
            String userNo = reqData.getUSER_NO();
            String userPassword = reqData.getUSER_PASSWORD();
            result.setResult(loginService.login(userNo, userPassword));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 刷新token
     */
    @RequestMapping("/refreshToken")
    public BaseResponse refreshToken(UserInfo userInfo){
        BaseResponse respData = new BaseResponse();
        try {
            String s = loginService.refreshToken(userInfo);
            respData.setResult(s);
        }catch (Exception e){
            respData.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            respData.setMsg(e.getMessage());
        }
        return respData;
    }

}
