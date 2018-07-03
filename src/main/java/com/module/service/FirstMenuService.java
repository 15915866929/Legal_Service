package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.FirstMenu;
import com.module.entity.Role;
import com.module.entity.SecondMenu;
import com.module.mapper.FirstMenuMapper;
import com.module.returnModel.ReturnFirstMenu;
import com.module.returnModel.ReturnSecondMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FirstMenuService extends BaseModelService<FirstMenuMapper,FirstMenu> {

    @Autowired
    private SecondMenuService secondMenuService;
    @Autowired
    private RoleService roleService;

    public List<ReturnFirstMenu> findMenuList(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        ArrayList<ReturnFirstMenu> menus = new ArrayList<>();
        List<FirstMenu> FMenus = this.selectAll(condition);
        List<SecondMenu> SMenus = this.secondMenuService.selectAll(condition);

        for (FirstMenu fMenu : FMenus) {
            ReturnFirstMenu rfm = new ReturnFirstMenu(fMenu);
            for (SecondMenu sMenu : SMenus) {
                if(fMenu.getId().equals(sMenu.getFirstMenu_Id())){
                    ReturnSecondMenu rsm = new ReturnSecondMenu(sMenu);
                    rfm.getChildMenus().add(rsm);
                }
            }
            //排序二级菜单
            SortMenus2 sortList = new SortMenus2();
            Collections.sort(rfm.getChildMenus(), sortList);
            menus.add(rfm);
        }
        //排序一级菜单
        SortMenus1 sortList = new SortMenus1();
        Collections.sort(menus, sortList);
        return menus;
    }

    public void addMenus(String firstMenu_Id, String name, String url, List<String> role_Ids){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("firstMenu_Id",firstMenu_Id));
        condition.addExpression(Expression.eq("status",1));
        List<SecondMenu> secondMenuList = this.secondMenuService.selectAll(condition);

        SecondMenu secondMenu = new SecondMenu();
        secondMenu.setSecondMenu_Id(IdGenerator.generate());
        secondMenu.setId(secondMenu.getSecondMenu_Id());
        secondMenu.setFirstMenu_Id(firstMenu_Id);
        secondMenu.setSecondMname(name);
        secondMenu.setURL(url);
        secondMenu.setIdx(secondMenuList.size() + 1);
        secondMenu.setStatus(1);
        this.secondMenuService.insert(secondMenu);

        FirstMenu firstMenu = this.select(firstMenu_Id);
        String secondMids = firstMenu.getSecondMids();
        if(StringUtils.isNotEmpty(secondMids)){
            if(",".equals(secondMids.substring(secondMids.length()-1,secondMids.length()))){
                secondMids += secondMenu.getId();
            }else {
                secondMids += (","+secondMenu.getId());
            }
        }else {
            secondMids = secondMenu.getId();
        }
        firstMenu.setSecondMids(secondMids);
        this.updateById(firstMenu);

        if (role_Ids == null){
            role_Ids = new ArrayList<>();
        }
        role_Ids.add("-1");//超级管理员角色id为-1
        for (String role_id : role_Ids) {
            Role role = this.roleService.select(role_id);
            String secondMenuIds = role.getSecondMenu_Ids();
            if(StringUtils.isNotEmpty(secondMenuIds)){
                if(",".equals(secondMenuIds.substring(secondMenuIds.length()-1,secondMenuIds.length()))){
                    secondMenuIds += secondMenu.getId();
                }else {
                    secondMenuIds += (","+secondMenu.getId());
                }
            }else {
                secondMenuIds = secondMenu.getId();
            }
            role.setSecondMenu_Ids(secondMenuIds);
            this.roleService.updateById(role);
        }
    }

    public ReturnSecondMenu findSecondMenuById(String secMenuId){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("id",secMenuId));
        List<SecondMenu> secondMenuList = this.secondMenuService.selectAll(condition);
        if(secondMenuList!=null && secondMenuList.size()>0){
            ReturnSecondMenu returnSecondMenu = new ReturnSecondMenu(secondMenuList.get(0));
            return returnSecondMenu;
        }
        return null;
    }

    public void updateSecondMenuMsg(String secondMenu_Id, String name, String url){
        SecondMenu secondMenu = this.secondMenuService.select(secondMenu_Id);
        secondMenu.setSecondMname(name);
        secondMenu.setURL(url);
        this.secondMenuService.updateById(secondMenu);
    }

    public void updateMenusOrder(List<String> list){
        for (int i = 0; i < list.size(); i++){
            String secMenuId = list.get(i);
            SecondMenu secondMenu = this.secondMenuService.select(secMenuId);
            secondMenu.setIdx(i + 1);
            this.secondMenuService.updateById(secondMenu);
        }
    }

    public void dd(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        List<FirstMenu> allFirstMenu = this.selectAll(condition);
        for (FirstMenu ff : allFirstMenu){
            if(StringUtils.isNotEmpty(ff.getSecondMids())){
                String[] secondMids = ff.getSecondMids().split(",");
                if (secondMids.length > 0) {
                    for (String secondMid : secondMids) {
                        SecondMenu secondMenu = this.secondMenuService.select(secondMid);
                        if(secondMenu!=null){
                            secondMenu.setFirstMenu_Id(ff.getFirstMenu_Id());
                            this.secondMenuService.updateById(secondMenu);
                        }
                    }
                }
            }
        }
    }

}
