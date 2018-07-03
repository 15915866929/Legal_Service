package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.protocol.lawFirm.*;
import com.module.protocol.lawyer.*;
import com.module.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Controller
@RequestMapping("/lawyer")
public class LawyerController extends BaseController {

    @Autowired
    private LawyerService lawyerService;

    /**
     * 查看律师列表
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLawyerList")
    public BaseResponse findLawyerList(@RequestBody FindLawyerListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            String lawFirm_Id = reqData.getLawFirm_Id();
            String street_Id = reqData.getStreet_Id();
            resultBean.setResult(this.lawyerService.findLawyerList(page, pageSize, sort, order, name, lawFirm_Id, street_Id));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看律师详细信息
     * @author hsj
     * @date 2017-11-08
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLawyerDetail")
    public BaseResponse findLawyerDetail(@RequestBody FindLawyerDetailReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String lawyer_Id = reqData.getLawyer_Id();
            resultBean.setResult(this.lawyerService.findLawyerDetail(lawyer_Id));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 添加律师
     * @author hsj
     * @date 2017-11-08
     * @return
     */
    @ResponseBody
    @RequestMapping("/addLawyer")
    public BaseResponse addLawyer(@RequestBody AddLawyerReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            this.lawyerService.addLawyer(userInfo.getId(),reqData);
            resultBean.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 更新律师详细信息
     * @author hsj
     * @date 2017-11-08
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLawyer")
    public BaseResponse updateLawyer(@RequestBody UpdateLawyerReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            this.lawyerService.updateLawyer(userInfo.getId(), reqData);
            resultBean.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 删除律师详细信息
     * @author hsj
     * @date 2017-11-08
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteLawyer")
    public BaseResponse deleteLawyer(@RequestBody DeleteLawyerReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            this.lawyerService.deleteById(reqData.getLawyer_Id());
            resultBean.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
