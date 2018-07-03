package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.protocol.notarize.DealNotarizeReqData;
import com.module.protocol.notarize.FindNotarizeDetailReqData;
import com.module.protocol.notarize.FindNotarizeListReqData;
import com.module.service.NotarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author hsj
 * @date 2017/11/03
 */
@AccessRight(rightsCode = "1")
@Controller
@RequestMapping("/notarize")
public class NotarizeController extends BaseController {

    @Autowired
    private NotarizeService notarizeService;

    /**
     * 查看公证服务申请列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findNotarizeList")
    public BaseResponse findNotarizeList(@RequestBody FindNotarizeListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
//            String sort = reqData.getSort();
//            Integer order = reqData.getOrder();
//            String fieldName = reqData.getFieldName();
//            String value = reqData.getValue();
            resultBean.setResult(notarizeService.findNotarizeList(page, pageSize));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/findNotarizeDetail")
    public BaseResponse findNotarizeDetail(@RequestBody FindNotarizeDetailReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            resultBean.setResult(notarizeService.select(reqData.getId()));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/dealNotarize")
    public BaseResponse dealNotarize(@RequestBody DealNotarizeReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            notarizeService.dealNotarize(reqData,userInfo);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
