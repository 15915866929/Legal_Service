package com.module.service;

import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.core.cache.CacheService;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.*;
import com.module.protocol.security.AddAccountReqData;
import com.module.protocol.security.UpdateAccountReqData;
import com.module.returnModel.*;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.core.util.StringUtils;

import java.util.*;


/**
 * @author hsj
 * @date 2017/11/01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecurityService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FirstMenuService firstMenuService;
    @Autowired
    private SecondMenuService secondMenuService;


    public void addDepartment(String operatorId, String name, String uaccount, List<String> menus, String note) {
        String adminName = name+"管理员";
        List<Department> departmentList = this.departmentService.selectAll();
        int count = departmentList.size();
        Department department = new Department();
        department.setDepartment_Id(IdGenerator.generate());
        department.setId(department.getDepartment_Id());
        department.setName(name);
        department.setStatus(1);
        department.setNote(note);
        department.setCreater(operatorId);
        department.setLastUpdateOperator(operatorId);
        department.setCtime(new Date());
        department.setLastUpdateTime(new Date());
        department.setType(count+1);

        Role role = addRole(operatorId, adminName, null, menus,department.getDepartment_Id(), 2);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserInfo_Id(IdGenerator.generate());
        userInfo.setId(userInfo.getUserInfo_Id());
        userInfo.setRole_Id(role.getId());
        userInfo.setDepartment_Id(department.getId());
        userInfo.setDepartment_type(department.getType());
        userInfo.setUname(adminName);
        userInfo.setCreater(operatorId);
        userInfo.setCtime(new Date());
        userInfo.setLastUpdateOperator(operatorId);
        userInfo.setLastUpdateTime(new Date());
        userInfo.setAdmin(1);
        userInfo.setType(2);

        Account account = new Account();
        account.setId(IdGenerator.generate());
        account.setUaccount(uaccount);
        account.setUpassword("96e79218965eb72c92a549dd5a330112");
        account.setCtime(new Date());
        account.setGeneralAccount(1);
        account.setUserInfo_Id(userInfo.getId());
        department.setUserInfo_Id(userInfo.getUserInfo_Id());

        this.userInfoService.insert(userInfo);
        this.accountService.insert(account);
        this.departmentService.insert(department);
    }

    public HashMap findDepartmentList(Integer page, Integer pageSize){
        HashMap hashMap = new HashMap();
        Condition condition = Condition.create();
        condition.setPageSize(pageSize);
        condition.setPageNo(page);
        condition.addExpression(Expression.eq("status",1));
        Page<Department> departmentPage = this.departmentService.selectPage(condition);
        List<ReturnDepartment> returnDepartmentList = new ArrayList<>();
        for (Department department : departmentPage.getEntities()) {
            ReturnDepartment returnDepartment = new ReturnDepartment();
            BeanUtils.copyProperties(department,returnDepartment);
            returnDepartment.setRemarks(department.getNote());
            Account account = this.accountService.selectByUniqueColumn("userInfo_Id",department.getUserInfo_Id());
            returnDepartment.setAccount(account.getUaccount());
            UserInfo userInfo = this.userInfoService.select(department.getUserInfo_Id());
            returnDepartment.setUname(userInfo.getUname());
            returnDepartmentList.add(returnDepartment);
        }
        hashMap.put("list", returnDepartmentList);
        hashMap.put("total", departmentPage.getEntityCount());
        return hashMap;
    }

    public ReturnDepartment findDepartmentDetail(String departmentId){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("department_Id",departmentId));
        List<Department> departmentList = this.departmentService.selectAll(condition);
        if(departmentList==null || departmentList.size()==0){
            return null;
        }else {
            Department department = departmentList.get(0);
            ReturnDepartment returnDepartment = new ReturnDepartment();
            BeanUtils.copyProperties(department,returnDepartment);
            returnDepartment.setRemarks(department.getNote());
            Account account = this.accountService.selectByUniqueColumn("userInfo_Id",department.getUserInfo_Id());
            returnDepartment.setAccount(account.getUaccount());
            UserInfo userInfo = this.userInfoService.select(department.getUserInfo_Id());
            returnDepartment.setUname(userInfo.getUname());
            return returnDepartment;
        }
    }

    public void updateDepartment(String operatorId, String departmentId, String name, String note){
        String adminName = name+"管理员";
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("department_Id",departmentId));
        condition.addExpression(Expression.eq("admin",1));
        List<UserInfo> userInfoList = this.userInfoService.selectAll(condition);
        for (UserInfo userInfo : userInfoList) {
            userInfo.setUname(adminName);
            this.userInfoService.updateById(userInfo);
        }
        List<Role> roleList = this.roleService.selectAll(condition);
        for (Role role : roleList) {
            role.setRoleName(adminName);
            this.roleService.updateById(role);
        }
        Department department = this.departmentService.select(departmentId);
        department.setName(name);
        department.setNote(note);
        department.setLastUpdateOperator(operatorId);
        department.setLastUpdateTime(new Date());
        this.departmentService.updateById(department);
    }

    public void deleteDepartment(String departmentId){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("department_Id",departmentId));
        List<UserInfo> userInfoList = this.userInfoService.selectAll(condition);
        for (UserInfo userInfo : userInfoList) {
            userInfo.setStatus(-1);
            this.userInfoService.updateById(userInfo);
        }
        List<Role> roleList = this.roleService.selectAll(condition);
        for (Role role : roleList) {
            role.setStatus(-1);
            this.roleService.updateById(role);
        }
        Department department = this.departmentService.select(departmentId);
        department.setStatus(-1);
        this.departmentService.updateById(department);
    }

    public void addAccount(AddAccountReqData reqData,String operatorId) {
        UserInfo operator = this.userInfoService.select(operatorId);
        if (StringUtils.isEmpty(reqData.getAccount())) {
            throw new ResultException("账号为空");
        }
//        if (operator.getType() == 2) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserInfo_Id(IdGenerator.generate());
        userInfo.setId(userInfo.getUserInfo_Id());
        userInfo.setRole_Id(reqData.getRole_Id());
        userInfo.setUname(reqData.getUname());
        userInfo.setCreater(operatorId);
        userInfo.setCtime(new Date());
        userInfo.setLastUpdateOperator(operatorId);
        userInfo.setLastUpdateTime(new Date());
        userInfo.setContactPhone(reqData.getContactPhone());
        userInfo.setNote(reqData.getNote());
        userInfo.setTelephone(reqData.getTelephone());
        userInfo.setAbbreviation(reqData.getAbbreviation());
        Department department = this.departmentService.select(operator.getDepartment_Id());
        //法律援助部门
        if (department!=null ){
            if(department.getType() == 1){
                userInfo.setCan_receive_SMS(1);
            }
            userInfo.setDepartment_type(department.getType());
        }
        Role role = this.roleService.select(reqData.getRole_Id());
        userInfo.setType(role.getType());
        userInfo.setDepartment_Id(operator.getDepartment_Id());
        Account account = new Account();
        account.setUaccount(reqData.getAccount());
        account.setUpassword("96e79218965eb72c92a549dd5a330112");
        account.setCtime(new Date());
        account.setGeneralAccount(1);
        account.setUserInfo_Id(userInfo.getId());
        account.setId(IdGenerator.generate());

        this.userInfoService.insert(userInfo);
        this.accountService.insert(account);
//        }
    }

    public void updateAccount(UpdateAccountReqData reqData,String operatorId){
        UserInfo userInfo = this.userInfoService.select(reqData.getId());
        userInfo.setRole_Id(reqData.getRole_Id());
        userInfo.setUname(reqData.getUname());
        userInfo.setContactPhone(reqData.getContactPhone());
        userInfo.setTelephone(reqData.getTelephone());
        userInfo.setAbbreviation(reqData.getAbbreviation());
        userInfo.setNote(reqData.getNote());
        userInfo.setLastUpdateOperator(operatorId);
        userInfo.setLastUpdateTime(new Date());
        this.userInfoService.updateById(userInfo);
    }

    public void deleteAccount(String id){
        UserInfo userInfo = this.userInfoService.select(id);
        userInfo.setStatus(-1);
        this.userInfoService.updateById(userInfo);
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
        account.setStatus(-1);
        this.accountService.updateById(account);
    }

    public HashMap findAccountList(String operatorId, Integer page, Integer pageSize,String phone){
        UserInfo operator = this.userInfoService.select(operatorId);
        HashMap hashMap = new HashMap(2);
        ArrayList<ReturnAccount> returnList = new ArrayList<>();
        Condition condition = Condition.create();
        condition.addExpression(Expression.ge("status",0));
        condition.addOrder(Order.desc("ctime"));
        if (operator.getType() == 1) {
            condition.addExpression(Expression.ne("type",1));
        }else {
            condition.addExpression(Expression.ne("department_Id",operator.getDepartment_Id()));
            condition.addExpression(Expression.ge("type",operator.getType()));
        }
        if(StringUtils.isNotEmpty(phone)){
            condition.addExpression(Expression.like("telephone",phone));
        }
        List<UserInfo> userInfoList;
        if (page != null && pageSize != null) {
            condition.setPageNo(page);
            condition.setPageSize(pageSize);
            Page<UserInfo> userInfoPage = this.userInfoService.selectPage(condition);
            hashMap.put("total",userInfoPage.getEntityCount());
            userInfoList = userInfoPage.getEntities();
        }else {
            userInfoList = this.userInfoService.selectAll(condition);
        }
        for (UserInfo userInfo : userInfoList) {
            Role role = this.roleService.select(userInfo.getRole_Id());
            Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
            ReturnAccount ra = new ReturnAccount();
            ra.setAccount(account.getUaccount());
            ra.setUserInfo_Id(userInfo.getId());
            ra.setUname(userInfo.getUname());
            ra.setRoleName(role.getRoleName());
            ra.setStatus(userInfo.getStatus());
            ra.setNote(userInfo.getNote());
            ra.setLastLoginTime(userInfo.getLastLoginTime());
            ra.setCtime(userInfo.getCtime());
            String upassword = account.getUpassword();
            if ("96e79218965eb72c92a549dd5a330112".equals(upassword)){
                ra.setPwd_status(1);
            }else {
                ra.setPwd_status(2);
            }
            returnList.add(ra);
        }
        hashMap.put("list", returnList);
        return hashMap;
    }

    public Map findMediatorList(Integer page, Integer pageSize){
        Map map = new HashMap();
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("type",5));
        condition.addExpression(Expression.eq("status",1));
        condition.addOrder(Order.desc("ctime"));
        List<UserInfo> userInfoList;
        if (page != null && pageSize != null) {
            condition.setPageNo(page);
            condition.setPageSize(pageSize);
            Page<UserInfo> userInfoPage = this.userInfoService.selectPage(condition);
            map.put("total",userInfoPage.getEntityCount());
            userInfoList = userInfoPage.getEntities();
        }else {
            userInfoList = this.userInfoService.selectAll(condition);
        }
        for (UserInfo userInfo : userInfoList) {
            Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
            userInfo.setUaccount(account.getUaccount());
        }
        map.put("list", userInfoList);
        return map;
    }

    public UserInfo findMediator(String id){
        UserInfo userInfo = this.userInfoService.select(id);
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
        userInfo.setUaccount(account.getUaccount());
        return userInfo;
    }



    public void resetPwd(String operatorId, String userInfo_Id) {
        UserInfo userInfo = this.userInfoService.select(operatorId);
        if (userInfo.getType() != 2){
            throw new ResultException("非管理员不能操作");
        }
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo_Id);
        account.setUpassword("96e79218965eb72c92a549dd5a330112");
        this.accountService.updateById(account);
    }

    public void updateAccountRole(String operatorId, String userInfo_Id, String role_Id) {
        UserInfo userInfo = this.userInfoService.select(userInfo_Id);
        if (userInfo == null) {
            throw new ResultException("异常，选择的用户有误");
        }
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("role_Id",role_Id));
        condition.addExpression(Expression.eq("status",1));
        List<Role> roleList = this.roleService.selectAll(condition);
        if(roleList==null || roleList.size()==0){
            throw new ResultException("异常，角色权限有误");
        }
        Role role = roleList.get(0);
//        serviceFactory.getRoleLogService().updateAccountRole(operatorId,"更改账号的角色",userInfo_Id, userInfo.getRole_Id(), role_Id);
        userInfo.setRole_Id(role_Id);
        userInfo.setType(role.getType());
        this.userInfoService.updateById(userInfo);
    }

    public String banAccount(String userId) {
        UserInfo userInfo = this.userInfoService.select(userId);
        if (userInfo == null) {
            throw new ResultException("没有此用户！");
        }
        userInfo.setStatus(0);
        this.userInfoService.updateById(userInfo);
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userId);
        account.setStatus(0);
        this.accountService.updateById(account);
        return "禁用账号成功";
    }

    public String enableAccount(String userId) {
        UserInfo userInfo = this.userInfoService.select(userId);
        if (userInfo == null) {
            throw new ResultException("没有此用户！");
        }
        userInfo.setStatus(1);
        this.userInfoService.updateById(userInfo);
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userId);
        account.setStatus(1);
        this.accountService.updateById(account);
        return "启用账号成功";
    }

    public Boolean checkAccountExist(String account){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("uaccount",account));
        condition.addExpression(Expression.eq("status",1));
        List<Account> accountList = this.accountService.selectAll(condition);
        if (accountList != null && accountList.size() > 0){
            return true;
        }
        return false;
    }

    public HashMap getOperator(String userId){
        HashMap<String, Object> hashMap = new HashMap<>(16);
        UserInfo userInfo = this.userInfoService.select(userId);
        ReturnUserInfo returnUserInfo = new ReturnUserInfo(userInfo);
        hashMap.put("userInfo", returnUserInfo);
        return hashMap;
    }

    /**
     * 新建角色
     * @param operatorId 操作人ID
     * @param roleName 角色名字
     * @param note 备注
     * @param menus 菜单(菜单ID数组)
     * @return
     */
    public Role addRole(String operatorId, String roleName, String note, List<String> menus, String department_Id, Integer type){
        Role role = new Role();
        role.setRole_Id(IdGenerator.generate());
        role.setId(role.getRole_Id());
        role.setRoleName(roleName);
        role.setCreater(operatorId);
        role.setCtime(new Date());
        role.setLastUpdateOperator(operatorId);
        role.setNote(note);
        role.setType(type);
        if (type == 2) {
            role.setAdmin(1);
        }
        role.setDepartment_Id(department_Id);
        role.setLastUpdateTime(new Date());
        setRoleMenus(role,menus);
        this.roleService.insert(role);
//        serviceFactory.getRoleLogService().saveRoleLog(operatorId,role.getRole_Id(),"添加角色:"+roleName);
        return role;
    }

    public HashMap getRoleList(String operatorId, Integer page, Integer pageSize){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addOrder(Order.desc("ctime"));
        condition.addExpression(Expression.ne("type",1));
        condition.addExpression(Expression.eq("status",1));
        List<Role> roleList;
        if (page != null && pageSize != null) {
            condition.setPageSize(pageSize);
            condition.setPageNo(page);
            Page<Role> rolePage = this.roleService.selectPage(condition);
            roleList = rolePage.getEntities();
            hashMap.put("total", rolePage.getEntityCount());
        }else {
            roleList = this.roleService.selectAll(condition);
            hashMap.put("total", roleList.size());
        }
        ArrayList<ReturnRole> returnRoles = new ArrayList<>();
        for (Role role : roleList) {
            ReturnRole r = new ReturnRole();
            r.setRole_Id(role.getRole_Id());
            r.setRoleName(role.getRoleName());
            r.setStatus(role.getStatus());
            UserInfo userInfo = this.userInfoService.select(role.getLastUpdateOperator());
            r.setLastOperator(userInfo.getUname());
            r.setLastUpdateTime(role.getLastUpdateTime());
            r.setNote(role.getNote());
            r.setType(role.getType());
            if(StringUtils.isNotEmpty(role.getDepartment_Id())){
                Department department = this.departmentService.select(role.getDepartment_Id());
                r.setDepartmentName(department.getName());
            }
            returnRoles.add(r);
        }
        hashMap.put("list", returnRoles);
        return hashMap;

    }

    public ReturnRoleDetail getRoleDetail(String role_Id){
        ReturnRoleDetail rrd = new ReturnRoleDetail();
        Role role = this.roleService.select(role_Id);
        ArrayList<ReturnFirstMenu> menus = getMenusByRole(role);
        //排序一级菜单
        SortMenus1 sortList = new SortMenus1();
        Collections.sort(menus, sortList);
        rrd.setRoleName(role.getRoleName());
        rrd.setNote(role.getNote());
        rrd.setType(role.getType());
        rrd.setDepartment_Id(role.getDepartment_Id());
        rrd.setMenus(menus);
        return rrd;
    }

    private ArrayList<ReturnFirstMenu> getMenusByRole(Role role){
        ArrayList<ReturnFirstMenu> menus = new ArrayList<>();
        List<SecondMenu> secondMenuList = new ArrayList<>();
        if(StringUtils.isNotEmpty(role.getSecondMenu_Ids())){
            String[] secondMenuIds = role.getSecondMenu_Ids().split(",");
            Condition condition = Condition.create();
            condition.addExpression(Expression.in("id",secondMenuIds));
            condition.addExpression(Expression.eq("status",1));
            condition.addOrder(Order.asc("idx"));
            secondMenuList = this.secondMenuService.selectAll(condition);
        }

        List<FirstMenu> firstMenuList = new ArrayList<>();
        List<String> firstMenuIds = new ArrayList<>();
        for (SecondMenu secondMenu : secondMenuList) {
            if(!firstMenuIds.contains(secondMenu.getFirstMenu_Id())){
                firstMenuIds.add(secondMenu.getFirstMenu_Id());
            }
        }
        if(StringUtils.isNotEmpty(role.getFirstMenu_Ids())){
            String[] tempFirstMenuIds = role.getFirstMenu_Ids().split(",");
            for (String tempFirstMenuId : tempFirstMenuIds) {
                if(!firstMenuIds.contains(tempFirstMenuId)){
                    firstMenuIds.add(tempFirstMenuId);
                }
            }
        }
        if(firstMenuIds.size()>0){
            Condition condition = Condition.create();
            condition.addExpression(Expression.in("id",firstMenuIds.toArray(new String[firstMenuIds.size()])));
            condition.addExpression(Expression.eq("status",1));
            condition.addExpression(Expression.eq("independence",1));
            firstMenuList = this.firstMenuService.selectAll(condition);
        }
        for (FirstMenu firstMenu : firstMenuList) {
            ReturnFirstMenu returnFirstMenu = new ReturnFirstMenu(firstMenu);
            List<ReturnSecondMenu> childMenus = returnFirstMenu.getChildMenus();
            for (SecondMenu secondMenu : secondMenuList) {
                if(firstMenu.getId().equals(secondMenu.getFirstMenu_Id())){
                    ReturnSecondMenu returnSecondMenu = new ReturnSecondMenu(secondMenu);
                    childMenus.add(returnSecondMenu);
                }
            }
            menus.add(returnFirstMenu);
        }
        return menus;
    }

    private void setRoleMenus(Role role,List<String> menus){
        String firMenuIds = "";
        String secMenuIds = "";
        if (menus != null && menus.size() > 0) {
            for (int i = 0; i < menus.size(); i++) {
                String mid = menus.get(i);
                Condition condition = Condition.create();
                condition.addExpression(Expression.eq("status",1));
                condition.addExpression(Expression.eq("secondMenu_Id",mid));
                List<SecondMenu> secondMenuList = this.secondMenuService.selectAll(condition);
                if(secondMenuList!=null && secondMenuList.size()>0){
                    secMenuIds += (mid+",");
                }else {
                    condition = Condition.create();
                    condition.addExpression(Expression.eq("status",1));
                    condition.addExpression(Expression.eq("firstMenu_Id",mid));
                    List<FirstMenu> firstMenuList = this.firstMenuService.selectAll(condition);
                    if(firstMenuList!=null && firstMenuList.size()>0){
                        firMenuIds += (mid+",");
                    }
                }
            }
        }
        role.setFirstMenu_Ids(firMenuIds);
        role.setSecondMenu_Ids(secMenuIds);
    }

    public void updateRole(String operatorId, String role_Id, String roleName,String department_Id,Integer type, String note, List<String> menus){
        Role role = this.roleService.select(role_Id);
        role.setLastUpdateOperator(operatorId);
        role.setLastUpdateTime(new Date());
        role.setRoleName(roleName);
        role.setNote(note);
        role.setDepartment_Id(department_Id);
        role.setType(type);
        setRoleMenus(role,menus);
        this.roleService.updateById(role);
//        //记录日志
//        ArrayList<String> updateFieldNames = new ArrayList<>();
//        //修改前的值List
//        ArrayList beforeValueList = new ArrayList();
//        //修改前的值List
//        ArrayList afteValueList = new ArrayList();
//        /*if (!StringUtils.isEmpty(roleName) && !roleName.equals(role.getRoleName())) {
//            serviceFactory.getRoleLogService().installFieldList("roleName", role.getRoleName(), roleName, updateFieldNames, beforeValueList, afteValueList);
//        }*/
//        /*if (!StringUtils.isEmpty(note) && !roleName.equals(role.getNote())) {
//            serviceFactory.getRoleLogService().installFieldList("note", role.getNote(), note, updateFieldNames, beforeValueList, afteValueList);
//        }*/
//        /*if (updateFieldNames.size() > 0){
//            serviceFactory.getRoleLogService().updateRoleLog(operatorId,role_Id,updateFieldNames,beforeValueList,afteValueList,operatorId+"修改角色基本信息");
//        }*/
//        serviceFactory.getRoleLogService().updateRoleMenus(operatorId,role_Id,"修改角色:"+role.getRoleName()+"菜单权限",addFirstMenus,addSecondMenus,deleteFirstMenus,deleteSecondMenus);
//        daoFactory.getRoleDao().updateMenu(new Document("role_Id", role_Id), addFirstMenus, deleteFirstMenus, addSecondMenus, deleteSecondMenus);
//        daoFactory.getRoleDao().updateManyToSet(new Document("role_Id", role_Id), new Document("lastUpdateOperator", operatorId).append("lastUpdateTime", DateUtils.currentDatetime())
//                                                    .append("roleName", roleName).append("note", note).append("department_Id",department_Id).append("type",type));

    }

    public void deleteRole(String role_Id){
        Role role = this.roleService.select(role_Id);
        role.setStatus(-1);
        this.roleService.updateById(role);
    }

    public HashMap getOperatorMenus(String userId){
        UserInfo userInfo = this.userInfoService.select(userId);
        Role role = this.roleService.select(userInfo.getRole_Id());
        ArrayList<ReturnFirstMenu> menus = getMenusByRole(role);
        //排序一级菜单
        SortMenus1 sortList = new SortMenus1();
        Collections.sort(menus, sortList);
        HashMap<String, Object> hashMap = new HashMap<>(16);
        hashMap.put("menus", menus);
        return hashMap;
    }

    public Boolean updatePWD(String userId, String oldPWD, String newPWD) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("userInfo_Id",userId));
        List<Account> accountList = this.accountService.selectAll(condition);
        if(accountList==null || accountList.size()==0){
            throw new ResultException("异常！没有该账号！");
        }
        Account account = accountList.get(0);
        if (!oldPWD.equals(account.getUpassword())){
            return false;
        }else {
            account.setUpassword(newPWD);
            this.accountService.updateById(account);
            return true;
        }
    }

    public List<Department> getDepartmentAgg(Condition condition, Integer page, Integer pageSize){
        List<Department> departmentsList;
        if (page != null && pageSize != null) {
            condition.setPageNo(page);
            condition.setPageSize(pageSize);
            Page<Department> departmentPage = this.departmentService.selectPage(condition);
            departmentsList = departmentPage.getEntities();
        }else {
            departmentsList = this.departmentService.selectAll(condition);
        }
        return departmentsList;
    }

}

class SortMenus1 implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ReturnFirstMenu e1 = (ReturnFirstMenu) o1;
        ReturnFirstMenu e2 = (ReturnFirstMenu) o2;
        return e1.getIdx().compareTo(e2.getIdx());
    }

}

class SortMenus2 implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ReturnSecondMenu e1 = (ReturnSecondMenu) o1;
        ReturnSecondMenu e2 = (ReturnSecondMenu) o2;
        return e1.getIdx().compareTo(e2.getIdx());
    }
}
