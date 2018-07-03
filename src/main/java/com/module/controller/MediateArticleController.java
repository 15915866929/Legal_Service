package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.MediateArticle;
import com.module.entity.NotarizeArticle;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.module.service.MediateArticleService;
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
@RequestMapping("/mediateArticle")
public class MediateArticleController extends BaseController {

    @Autowired
    private MediateArticleService mediateArticleService;

    /**
     * 查看人民调解文章列表
     * @author hsj
     * @date 2017-11-03
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAll")
    public BaseResponse queryAll(UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            resultBean.setResult(mediateArticleService.queryAll());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/add")
    public BaseResponse findNotarizeDetail(@RequestBody MediateArticle reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            mediateArticleService.add(reqData);
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @ResponseBody
    @RequestMapping("/update")
    public BaseResponse update(@RequestBody MediateArticle reqData, UserInfo userInfo){
        BaseResponse resultBean = new BaseResponse();
        try{
            mediateArticleService.update(reqData);
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
            mediateArticleService.deleteById(reqData.getId());
        }catch(Exception e){
            e.printStackTrace();
            resultBean.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
