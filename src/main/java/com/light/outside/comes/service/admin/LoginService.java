package com.light.outside.comes.service.admin;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.mybatis.mapper.UserDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by b3st9u on 16/10/16.
 */
@Service
public class LoginService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private ReadDao readDao;

    public boolean login(String userName, String pwd, HttpServletRequest request) {
        boolean isSuccess = false;
        UsersModel usersModel = userDao.queryUserByPwd(userName, pwd);
        if (usersModel != null) {
            isSuccess = true;
            HttpSession session = request.getSession();
            session.setAttribute(LoginController.SESSION_KEY_USERINFO, usersModel);
        }
        return isSuccess;
    }

    /**
     * 客户端登录
     * @param phone
     * @param pwd
     * @param request
     * @return
     */
    public boolean clientLogin(String phone,String pwd,HttpServletRequest request){
        boolean isSuccess = false;
        UserModel userModel = readDao.getUserByPassword(phone, pwd);
        if (userModel != null) {
            isSuccess = true;
            HttpSession session = request.getSession();
            session.setAttribute(LoginController.SESSION_KEY_APP_USERINFO, userModel);
        }
        return isSuccess;
    }

    /**
     * 微信登录
     * @param openid
     * @param nickname
     * @param headimg
     * @param sex
     * @return
     */
    public UserModel wechatLogin(String openid,String nickname,String headimg,String sex){
        return new UserModel();
    }


    public void addUsers(UsersModel usersModel) {
        Preconditions.checkNotNull(usersModel);
        UsersModel oldUsersModel = this.userDao.getUserByName(usersModel.getUser_name());

        if (oldUsersModel == null) {
            this.userDao.addUser(usersModel);
        }
    }


    public void editUsers(UsersModel usersModel) {
        Preconditions.checkNotNull(usersModel);
        UsersModel editUser = this.getUsersById(usersModel.getId());
        if (editUser != null) {
            if (!Strings.isNullOrEmpty(usersModel.getPassword())) {
                editUser.setPassword(usersModel.getPassword());
            }
            editUser.setReal_name(usersModel.getReal_name());
            editUser.setUpdate_time(new Date());
            this.userDao.updateUser(editUser);
        }
    }

    public UsersModel getUsersById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.userDao.getUserById(id);
    }


    public void deleteUsers(long id) {
        Preconditions.checkArgument(id > 0);

        this.userDao.deletUser(id);
    }

    /**
     * 分页获取数据
     *
     * @param pageModel
     * @return
     */
    public PageResult<UsersModel> getUsers(PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.userDao.userTotal();

        List<UsersModel> usersModels = this.userDao.getUsers(pageModel.getStart(), pageModel.getSize());

        PageResult<UsersModel> usersModelPageResult = new PageResult<UsersModel>();
        usersModelPageResult.setData(usersModels);
        usersModelPageResult.setPageModel(pageModel);
        usersModelPageResult.setTotal(total);
        return usersModelPageResult;
    }

}
