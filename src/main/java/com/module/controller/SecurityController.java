package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.protocol.security.*;
import com.module.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hsj
 * @date 2017/11/01
 */
@AccessRight(rightsCode = "1")
@Controller
@RequestMapping(value = "/security")
public class SecurityController extends BaseController {

    @Autowired
    private SecurityService securityService;

    /**
     * 添加部门
     * @author hsj
     * @date 2017-11-13
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDepartment")
    public BaseResponse addDepartment(@RequestBody AddDepartmentReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String name = reqData.getName();
            String note = reqData.getNote();
            String uaccount = reqData.getAccount();
            List<String> menus = reqData.getMenus();
            securityService.addDepartment(userInfo.getId(), name, uaccount, menus, note);
            result.setResult("添加账号成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 部门列表
     * @author hsj
     * @date 2017-11-13
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDepartmentList")
    public BaseResponse findDepartmentList(@RequestBody FindDepartmentListReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            HashMap hashMap = securityService.findDepartmentList(page, pageSize);
            result.setResult(hashMap);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看单个部门
     * @author hsj
     * @date 2017-11-13
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDepartmentDetail")
    public BaseResponse findDepartmentDetail(@RequestBody FindDepartmentDetailReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String department_Id = reqData.getDepartment_Id();
            result.setResult(securityService.findDepartmentDetail(department_Id));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 修改部门名称
     * @author hsj
     * @date 2017-11-13
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDepartment")
    public BaseResponse updateDepartment(@RequestBody UpdateDepartmentReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String department_Id = reqData.getDepartment_Id();
            String name = reqData.getName();
            String note = reqData.getNote();
            securityService.updateDepartment(userInfo.getId(),department_Id,name,note);
            result.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增账号
     */
    @ResponseBody
    @RequestMapping(value = "/addAccount")
    public BaseResponse addAccount(@RequestBody AddAccountReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            securityService.addAccount(reqData,userInfo.getId());
            result.setResult("添加账号成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 修改账号
     * @author hsj
     * @date 2017-11-1
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAccount")
    public BaseResponse updateAccount(@RequestBody UpdateAccountReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            securityService.updateAccount(reqData,userInfo.getId());
            result.setResult("添加账号成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 删除账号
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAccount")
    public BaseResponse deleteAccount(@RequestBody DeleteAccountReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            securityService.deleteAccount(reqData.getId());
            result.setResult("删除账号成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看账号列表
     * @author hsj
     * @date 2017-11-1
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAccountList")
    public BaseResponse findAccountList(@RequestBody FindAccountListReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            HashMap hashMap = securityService.findAccountList(userInfo.getId(), page, pageSize);
            result.setResult(hashMap);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看所有调解员列表
     * @author hsj
     * @date 2017-11-1
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMediatorList")
    public BaseResponse findMediatorList(@RequestBody FindMediatorListReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            Map map = securityService.findMediatorList(page, pageSize);
            result.setResult(map);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看调解员
     * @author hsj
     * @date 2017-11-1
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMediator")
    public BaseResponse findMediator(@RequestBody FindMediatorReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            result.setResult(securityService.findMediator(reqData.getId()));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 部门管理员重置账号密码
     * @author hsj
     * @date 2017-11-18
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public BaseResponse resetPwd(@RequestBody ResetPwdReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String userInfo_Id = reqData.getUserInfo_Id();
            securityService.resetPwd(userInfo.getId(), userInfo_Id);
            result.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 更改账号的角色权限(把账号的这个角色 换成 另一个角色)
     * @author hsj
     * @date 2017-11-1
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAccountRole")
    public BaseResponse updateAccountRole(@RequestBody UpdateAccountRoleReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String userInfo_Id = reqData.getUserInfo_Id();
            String role_Id = reqData.getRole_Id();
            securityService.updateAccountRole(userInfo.getId(), userInfo_Id, role_Id);
            result.setResult("修改成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 检测账号是否已有
     * @author hsj
     * @date 2017-08-18
     * @param reqData(账号)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkAccountExist")
    public BaseResponse checkAccountExist(@RequestBody CheckAccountExistReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String account = reqData.getAccount();
            result.setResult(securityService.checkAccountExist(account));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/banAccount")
    public BaseResponse banAccount(@RequestBody BanAccountReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String userInfo_Id = reqData.getUserInfo_Id();
            result.setResult(securityService.banAccount(userInfo_Id));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/enableAccount")
    public BaseResponse enableAccount(@RequestBody EnableAccountReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String userInfo_Id = reqData.getUserInfo_Id();
            result.setResult(securityService.enableAccount(userInfo_Id));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getOperator")
    public BaseResponse getOperator(UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            result.setResult(securityService.getOperator(userInfo.getId()));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getOperatorMenus")
    public BaseResponse getOperatorMenus(UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            result.setResult(securityService.getOperatorMenus(userInfo.getId()));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updatePWD")
    public BaseResponse updatePWD(@RequestBody UpdatePwdReqData reqData,UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String oldPWD = reqData.getOldPWD();
            String newPWD = reqData.getNewPWD();
            Boolean flag = securityService.updatePWD(userInfo.getId(), oldPWD, newPWD);
            if (!flag) {
                result.setResult(flag);
            }else {
                result.setResult("修改密码成功");
            }
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addRole")
    public BaseResponse addRole(@RequestBody AddRoleReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String department_Id = reqData.getDepartment_Id();
            String roleName = reqData.getRoleName();
            String note = reqData.getNote();
            List<String> menus = reqData.getMenus();
            Integer type = reqData.getType();
            securityService.addRole(userInfo.getId(), roleName, note, menus, department_Id, type);
            result.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleList")
    public BaseResponse getRoleList(@RequestBody GetRoleListReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            result.setResult(securityService.getRoleList(userInfo.getId(), page, pageSize));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleDetail")
    public BaseResponse getRoleDetail(@RequestBody GetRoleDetailReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String role_Id = reqData.getRole_Id();
            result.setResult(securityService.getRoleDetail(role_Id));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateRole")
    public BaseResponse updateRole(@RequestBody UpdateRoleReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String role_Id = reqData.getRole_Id();
            String roleName = reqData.getRoleName();
            String note = reqData.getNote();
            String department_Id = reqData.getDepartment_Id();
            Integer type = reqData.getType();
            List<String> menus = reqData.getMenus();
    //        List<String> addFirstMenus = document.get(("addFirstMenus"), List.class);
    //        List<String> deleteFirstMenus = document.get(("deleteFirstMenus"), List.class);
    //        List<String> addSecondMenus = document.get(("addSecondMenus"), List.class);
    //        List<String> deleteSecondMenus = document.get(("deleteSecondMenus"), List.class);
            securityService.updateRole(userInfo.getId(),role_Id,roleName,department_Id,type,note,menus);
            result.setResult("修改角色成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
