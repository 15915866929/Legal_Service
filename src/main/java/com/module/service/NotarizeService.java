package com.module.service;

import com.aliyuncs.exceptions.ClientException;
import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.module.entity.Mediate;
import com.module.entity.Notarize;
import com.module.entity.UserInfo;
import com.module.mapper.NotarizeMapper;
import com.module.protocol.notarize.DealNotarizeReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公证服务
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class NotarizeService extends BaseModelService<NotarizeMapper,Notarize> {

    @Autowired
    private SmsAliyunService smsAliyunService;

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @return
     */
    public HashMap findNotarizeList(Integer page, Integer pageSize){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<Notarize> notarizePage = this.selectPage(condition);
        hashMap.put("list", notarizePage.getEntities());
        hashMap.put("total", notarizePage.getEntityCount());
        return hashMap;
    }

    public void dealNotarize(DealNotarizeReqData reqData, UserInfo userInfo){
        Notarize notarize = this.select(reqData.getId());
        notarize.setStatus(reqData.getStatus());
        notarize.setDealTime(reqData.getDealTime());
        notarize.setAcceptNote(reqData.getAcceptNote());
        notarize.setAcceptTime(reqData.getAcceptTime());
        notarize.setDealNote(reqData.getDealNote());
        notarize.setDealTime(reqData.getDealTime());
        this.updateById(notarize);
        try {
            Map map = new HashMap();
            map.put("type","公证服务");
            if (notarize.getStatus() == 1) {
                //操作员已接手申请 正在处理中
                map.put("date","两");
                //map.put("product","abcdefg");
                smsAliyunService.sendSms("SMS_115035012", map, notarize.getPhone());
            }else if (notarize.getStatus() == 2){
                //操作员已处理申请
                smsAliyunService.sendSms("SMS_115035018", map, notarize.getPhone());
            }
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }

    }

}
