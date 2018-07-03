package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.UserInfo;
import com.module.protocol.street.AddStreetReqData;
import com.module.protocol.street.CheckStreetExistReqData;
import com.module.protocol.street.FindStreetListReqData;
import com.module.protocol.street.UpdateStreetReqData;
import com.module.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hsj
 * @date 2017/11/06
 * 镇街Controller.
 */

@Controller
@RequestMapping(value = "/street")
public class StreetController extends BaseController {

    @Autowired
    private StreetService streetService;

    /**
     * 增加镇街
     * @author hsj
     * @date 2017-11-04
     * @param reqData(数组镇街名字)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStreet")
    public BaseResponse addStreet(@RequestBody AddStreetReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String name = reqData.getName();
            streetService.addStreet(userInfo.getId(), name);
            result.setResult("添加成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 检查镇街是否存在
     * @author hsj
     * @date 2017-11-04
     * @param reqData(镇街名字)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkStreetExist")
    public BaseResponse checkStreetExist(@RequestBody CheckStreetExistReqData reqData, UserInfo userInfo){
        BaseResponse result = new BaseResponse();
        try{
            String name = reqData.getName();
            boolean exist = streetService.checkStreetExist(name);
            result.setResult(exist);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看法律援助机构列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/findStreetList")
    public BaseResponse findStreetList(@RequestBody FindStreetListReqData reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            Integer page = reqData.getPage();
            Integer pageSize = reqData.getPageSize();
            String sort = reqData.getSort();
            Integer order = reqData.getOrder();
            String name = reqData.getName();
            resultBean.setResult(streetService.findStreetList(page, pageSize, sort, order, name));
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    /**
     * 修改镇街信息
     * @author hsj
     * @date 2017-11-06
     * @param reqData(镇街ID)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateStreet")
    public BaseResponse updateStreet(@RequestBody UpdateStreetReqData reqData, UserInfo userInfo) {
        BaseResponse result = new BaseResponse();
        try{
            String street_Id = reqData.getStreet_Id();
            String name = reqData.getName();
            streetService.updateStreet(userInfo.getId(), street_Id, name);
            result.setResult("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 禁用/启用镇街
     * @author hsj
     * @date 2017-11-06
     * @param params(镇街ID)
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/banOrEnableStreet")
    public ResultBean banOrEnableStreet(String userId, String params) throws BaseException {
        ResultBean result = new ResultBean();
        Document doc = getDocument(params);
        String street_Id = doc.getString("street_Id");
        Integer status = doc.getInteger("status");
        serviceFactory.getStreetService().banOrEnableStreet(userId, street_Id, status);
        result.setResult("操作成功");
        return result;
    }*/
}
