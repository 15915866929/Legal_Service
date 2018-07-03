package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.LegalAidSituation;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.protocol.legalAid.*;
import com.module.service.LegalAidGuideService;
import com.module.service.LegalAidService;
import com.module.service.LegalMechanismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @author hsj
 * @date 2017/11/03
 */
@AccessRight(rightsCode = "1")
@Controller
@RequestMapping("/legalAid")
public class LegalAidController extends BaseController {

    @Autowired
    private LegalAidService legalAidService;
    @Autowired
    private LegalMechanismService legalMechanismService;
    @Autowired
    private LegalAidGuideService legalAidGuideService;

    /**
     * 查看法律援助申请列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalAidList")
    public BaseResponse findLegalAidList(@RequestBody FindLegalAidListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String fieldName = reqData.getFieldName();
            String value = reqData.getValue();
            resultBean.setResult(legalAidService.findLegalAidList(page, pageSize, sort, order, fieldName, value));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助详情
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalAidDetail")
    public BaseResponse findLegalAidDetail(@RequestBody FindLegalAidDetailReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalAid_Id = reqData.getLegalAid_Id();
            resultBean.setResult(legalAidService.findLegalAidDetail(legalAid_Id));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助机构列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalMechanism")
    public BaseResponse findLegalMechanism(@RequestBody FindLegalMechanismReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            resultBean.setResult(legalMechanismService.findLegalMechanism(page, pageSize, sort, order, name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 添加法律援助机构
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/addLegalMechanism")
    public BaseResponse addLegalMechanism(@RequestBody AddLegalMechanismReqData reqData, UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String name = reqData.getName();
            String street_Id = reqData.getStreet_Id();
            Double longitude = reqData.getLongitude();
            Double latitude = reqData.getLatitude();
            List<String> imageFileIds = reqData.getFileIds();
            String address = reqData.getAddress();
            String contact = reqData.getContact();
            List<String> phones = reqData.getPhones();
            String note = reqData.getNote();
            legalMechanismService.addLegalMechanism(userInfo.getId(), name, street_Id, longitude, latitude, imageFileIds, address, contact, phones, note);
            resultBean.setResult("添加成功");
    //        if (!ListUtils.isEmpty(imageFileIds)){
    //            resultBean.setFileIds(imageFileIds);
    //        }
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 删除法律援助机构
     * @author hsj
     * @date 2017-11-14
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteLegalMechanism")
    public BaseResponse deleteLegalMechanism(@RequestBody DeleteLegalMechanismReqData reqData, UserInfo userInfo, String params) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalMechanism_Id = reqData.getLegalMechanism_Id();
            legalMechanismService.deleteLegalMechanism(legalMechanism_Id);
            resultBean.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助机构列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalMechanismDetail")
    public BaseResponse findLegalMechanismDetail(@RequestBody FindLegalMechanismDetailReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalMechanism_Id = reqData.getLegalMechanism_Id();
            resultBean.setResult(legalMechanismService.findLegalMechanismDetail(legalMechanism_Id));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 修改法律援助信息
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLegalMechanism")
    public BaseResponse updateLegalMechanism(@RequestBody UpdateLegalMechanismReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalMechanism_Id = reqData.getLegalMechanism_Id();
            String name = reqData.getName();
            String street_Id = reqData.getStreet_Id();
            Double longitude = reqData.getLongitude();
            Double latitude = reqData.getLatitude();
            List<String> imageFileIds = reqData.getFileIds();
            String address = reqData.getAddress();
            String contact = reqData.getContact();
            List<String> phones = reqData.getPhones();
            String note = reqData.getNote();
            legalMechanismService.updateLegalMechanism(userInfo.getId(), legalMechanism_Id, name, street_Id, longitude, latitude, imageFileIds, address, contact, phones, note);
            resultBean.setResult("操作成功");
    //        if (!ListUtils.isEmpty(imageFileIds)){
    //            resultBean.setFileIds(imageFileIds);
    //        }
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 添加法律援助办理指南
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/addLegalAidGuide")
    public BaseResponse addLegalAidGuide(@RequestBody AddLegalAidGuideReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String materialHtml = reqData.getMaterialHtml();
            String processHtml = reqData.getProcessHtml();
            String chargeHtml = reqData.getChargeHtml();
            //富文本框文件Id
            List<String> keepFileIds = reqData.getKeepFileIds();
            List<String> fileIds = reqData.getFileIds();
            legalAidGuideService.addLegalAidGuide(userInfo.getId(), materialHtml, processHtml, chargeHtml, fileIds);
            resultBean.setResult("添加成功");
    //        List<String> saveIds = new ArrayList<>();
    //        if (!ListUtils.isEmpty(keepFileIds)){
    //            saveIds.addAll(keepFileIds);
    //        }
    //        if (!ListUtils.isEmpty(fileIds)){
    //            saveIds.addAll(fileIds);
    //        }
    //        if (ListUtils.size(saveIds) > 0){
    //            resultBean.setFileIds(saveIds);
    //        }
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助办理指南内容
     * @author hsj
     * @date 2017-11-08
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalAidGuide")
    public BaseResponse findLegalAidGuide(UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            resultBean.setResult(legalAidGuideService.findLegalAidGuide());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 修改法律援助办理指南内容
     * @author hsj
     * @date 2017-11-06
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLegalAidGuide")
    public BaseResponse updateLegalAidGuide(@RequestBody UpdateLegalAidGuideReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalAidGuide_Id = reqData.getLegalAidGuide_Id();
            String materialHtml = reqData.getMaterialHtml();
            String processHtml = reqData.getProcessHtml();
            String chargeHtml = reqData.getChargeHtml();
            String fileIds = reqData.getFileIds();
            legalAidGuideService.updateLegalAidGuide(userInfo.getId(), legalAidGuide_Id, materialHtml, processHtml, chargeHtml, fileIds);
            resultBean.setResult("操作成功");
    //        if (!ListUtils.isEmpty(fileIds)){
    //            resultBean.setFileIds(fileIds);
    //        }
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 操作员处理法律援助申请
     * @author hsj
     * @date  2017-11-16
     * @return
     */
    @ResponseBody
    @RequestMapping("/dealWithLegalAid")
    public BaseResponse dealWithLegalAid(@RequestBody DealWithLegalAidReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{

            legalAidService.dealWithLegalAid(userInfo.getId(),reqData);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助处理情况
     * @author hsj
     * @date  2017-11-16
     * @return
     */
    @ResponseBody
    @RequestMapping("/findLegalAidSituationList")
    public BaseResponse findLegalAidSituationList(@RequestBody FindLegalAidSituationReqData reqData,UserInfo userInfo) {
        BaseResponse resultBean = new BaseResponse();
        try{
            String legalAid_Id = reqData.getLegalAid_Id();
            List<LegalAidSituation> situations = legalAidService.findLegalAidSituationList(legalAid_Id);
            resultBean.setResult(situations);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 查看法律援助部门人员(除局长)列表
     * 用于配置短信可配置的人员
     * @author hsj
     * @date  2017-11-17
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOperatorList")
    public BaseResponse findOperatorList(@RequestBody FindOperatorListReqData reqData,UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            resultBean.setResult(legalAidService.findOperatorList(userInfo.getId(),sort,order,page,pageSize,name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/updateReceiveStatus")
    public BaseResponse updateReceiveStatus(@RequestBody UpdateReceiveStatusReqData reqData,UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            String userInfo_Id = reqData.getUserInfo_Id();
            Integer can_receive_SMS = reqData.getCan_receive_SMS();
            legalAidService.updateReceiveStatus(userInfo_Id, can_receive_SMS);
            resultBean.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }
}
