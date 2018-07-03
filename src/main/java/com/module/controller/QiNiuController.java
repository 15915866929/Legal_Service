package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.core.util.QiNiuUploadUtils;
import com.module.interceptor.annotation.AccessRight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 七牛Controller
 */
@AccessRight(rightsCode = "-1")
@RestController
@RequestMapping("/qiNiu")
public class QiNiuController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(QiNiuController.class);

    /**
     * 获取七牛云token
     */
    @RequestMapping("/getToken")
    public BaseResponse getToken(){
        BaseResponse respData = new BaseResponse();
        try {
            String token = QiNiuUploadUtils.getUpToken();
            respData.setResult(token);
        }catch (Exception e){
            logger.error("后台错误:",e);
            respData.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            respData.setMsg(e.getMessage());
        }
        return respData;
    }

}
