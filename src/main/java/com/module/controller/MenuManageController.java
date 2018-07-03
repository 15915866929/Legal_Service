package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.protocol.menu.AddMenusReqData;
import com.module.protocol.menu.FindMenuOneReqData;
import com.module.protocol.menu.UpdateMenuOneMsgReqData;
import com.module.protocol.menu.UpdateMenusOrderReqData;
import com.module.service.FirstMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/01
 */
@AccessRight(rightsCode = "1")
@Controller
@RequestMapping(value = "/menuManage")
public class MenuManageController extends BaseController {

    @Autowired
    private FirstMenuService firstMenuService;

    /**
     * 查找所有菜单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMenuList")
    public BaseResponse findMenuList(UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            result.setResult(firstMenuService.findMenuList());
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 增加二级菜单
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMenus")
    public BaseResponse addMenus(AddMenusReqData reqData,UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String firMenuId = reqData.getFatherId();
            String name = reqData.getName();
            String url = reqData.getUrl();
            List<String> role_Ids = reqData.getRole_Ids();
            firstMenuService.addMenus(firMenuId, name, url, role_Ids);
            result.setResult("添加菜单成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查找具体的一个二级菜单
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMenuOne")
    public BaseResponse findMenuOne(FindMenuOneReqData reqData, UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String secMenuId = reqData.getId();
            result.setResult(firstMenuService.findSecondMenuById(secMenuId));
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 更改一个二级菜单的内容
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMenuOneMsg")
    public BaseResponse updateMenuOneMsg(UpdateMenuOneMsgReqData reqData, UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String secondMenu_Id = reqData.getId();
            String name = reqData.getName();
            String url = reqData.getUrl();
            firstMenuService.updateSecondMenuMsg(secondMenu_Id, name, url);
            result.setResult("修改菜单成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 更改一个一级菜单下的所有二级菜单的排列顺序
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMenusOrder")
    public BaseResponse updateMenusOrder(UpdateMenusOrderReqData reqData, UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            List<String> list = reqData.getChildren();
            firstMenuService.updateMenusOrder(list);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/dd")
    public void dd(){
        firstMenuService.dd();
    }
}
