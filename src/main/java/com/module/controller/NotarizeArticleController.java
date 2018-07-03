package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.NotarizeArticle;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.service.NotarizeArticleService;
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
@RequestMapping("/notarizeArticle")
public class NotarizeArticleController extends BaseController {

    @Autowired
    private NotarizeArticleService notarizeArticleService;

    /**
     * 查看公证服务文章列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAll")
    public BaseResponse queryAll(UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            resultBean.setResult(notarizeArticleService.queryAll());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/add")
    public BaseResponse findNotarizeDetail(@RequestBody NotarizeArticle reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            notarizeArticleService.add(reqData);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/update")
    public BaseResponse update(@RequestBody NotarizeArticle reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            notarizeArticleService.update(reqData);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public BaseResponse delete(@RequestBody NotarizeArticle reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            notarizeArticleService.deleteById(reqData.getId());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
