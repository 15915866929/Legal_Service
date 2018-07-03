package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.protocol.mediate.AssignMediateReqData;
import com.module.protocol.mediate.DealMediateReqData;
import com.module.protocol.mediate.FindMediateDetailReqData;
import com.module.protocol.mediate.FindMediateListReqData;
import com.module.service.MediateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


/**
 * @author hsj
 * @date 2017/11/03
 */
@AccessRight(rightsCode = "1")
@Controller
@RequestMapping("/mediate")
public class MediateController extends BaseController {

    @Autowired
    private MediateService mediateService;

    /**
     * 查看人民调解申请列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMediateList")
    public BaseResponse findMediateList(@RequestBody FindMediateListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
//            String sort = reqData.getSort();
//            Integer order = reqData.getOrder();
//            String fieldName = reqData.getFieldName();
//            String value = reqData.getValue();
            resultBean.setResult(mediateService.findMediateList(page, pageSize,userInfo));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/findMediateDetail")
    public BaseResponse findMediateDetail(@RequestBody FindMediateDetailReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            resultBean.setResult(mediateService.findMediateDetail(reqData.getId()));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/dealMediate")
    public BaseResponse dealMediate(@RequestBody DealMediateReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            mediateService.dealMediate(reqData,userInfo);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/assignMediate")
    public BaseResponse assignMediate(@RequestBody AssignMediateReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            mediateService.assignMediate(reqData.getId(),reqData.getMediator());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
