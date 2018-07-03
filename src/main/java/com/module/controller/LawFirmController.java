package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.protocol.lawFirm.*;
import com.module.service.LawFirmService;
import com.module.service.LawMajorService;
import com.module.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Controller
@RequestMapping("/lawFirm")
public class LawFirmController extends BaseController {

    @Autowired
    private LawFirmService lawFirmService;
    @Autowired
    private LawMajorService lawMajorService;
    @Autowired
    private LawyerService lawyerService;

    /**
     * 查看律师事务所列表
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLawFirmList")
    public BaseResponse findLawFirmList(@RequestBody FindLawFirmListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            resultBean.setResult(lawFirmService.findLawFirmList(page, pageSize, sort, order, name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 添加律师事务所
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/addLawFirm")
    public BaseResponse addLawFirm(@RequestBody AddLawFirmReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String name = reqData.getName();
            String street_Id = reqData.getStreet_Id();
            String address = reqData.getAddress();
            String email = reqData.getEmail();
            String phone = reqData.getPhone();
            Double longitude = reqData.getLongitude();
            Double latitude = reqData.getLatitude();
            //事务所概况
            String profile = reqData.getProfile();
            //律师团队
            String team = reqData.getTeam();
            //专业领域
            String field = reqData.getField();
            lawFirmService.addLawFirm(userInfo.getId(), name, street_Id, longitude, latitude, address, email, phone, profile, team, field);
            resultBean.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看律师事务所信息
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLawFirmDetail")
    public BaseResponse findLawFirmDetail(@RequestBody FindLawFirmDetailReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String lawFirm_Id = reqData.getLawFirm_Id();
            resultBean.setResult(lawFirmService.findLawFirmDetail(lawFirm_Id));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }


    /**
     * 修改律师事务所基本信息
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLawFirm")
    public BaseResponse updateLawFirm(@RequestBody UpdateLawFirmReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String lawFirm_Id = reqData.getLawFirm_Id();
            String name = reqData.getName();
            String street_Id = reqData.getStreet_Id();
            String address = reqData.getAddress();
            String email = reqData.getEmail();
            String phone = reqData.getPhone();
            Double longitude = reqData.getLongitude();
            Double latitude = reqData.getLatitude();
            //事务所概况
            String profile = reqData.getProfile();
            //律师团队
            String team = reqData.getTeam();
            //专业领域
            String field = reqData.getField();
            this.lawFirmService.updateLawFirm(userInfo.getId(), lawFirm_Id, name, street_Id, longitude, latitude, address, email, phone, profile, team, field);
            resultBean.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 删除律师事务所接口
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteLawFirm")
    public BaseResponse deleteLawFirm(@RequestBody DeleteLawFirmReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            this.lawFirmService.deleteLawFirm(reqData.getId());
            resultBean.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看律师专业列表
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLawMajorList")
    public BaseResponse findLawMajorList(@RequestBody FindLawMajorListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            resultBean.setResult(this.lawMajorService.findLawMajorList(page, pageSize, sort, order, name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 添加律师专业
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/addLawMajor")
    public BaseResponse addLawMajor(@RequestBody AddLawMajorReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            List<String> lawMajors = reqData.getLawMajors();
            this.lawMajorService.addLawMajor(userInfo.getId(), lawMajors);
            resultBean.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 检查律师专业是否已存在
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkLawMajorExist")
    public BaseResponse checkLawMajorExist(@RequestBody CheckLawMajorExistReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String name = reqData.getName();
            resultBean.setResult(this.lawMajorService.checkLawMajorExist(name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 修改律师专业
     * @author hsj
     * @date 2017-11-07
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLawMajor")
    public BaseResponse updateLawMajor(@RequestBody UpdateLawMajorReqData reqData,UserInfo userInfo, String params) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String lawMajor_Id = reqData.getLawMajor_Id();
            String name = reqData.getName();
            this.lawMajorService.updateLawMajor(userInfo.getId(), lawMajor_Id, name);
            resultBean.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }




}
