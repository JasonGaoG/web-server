package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.MD5Util;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.portal.accounts.dao.UserMapper;
import com.sunlight.portal.accounts.model.User;
import com.sunlight.portal.accounts.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("userService")
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void addUser (String userName, String password, String userRole, Integer companyId) throws Exception{
        User u = new User();
        u.setUserName(userName);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        User ret = userMapper.selectOne(u);
        if (ret != null) {
            throw new BusinessException("用户已经存在");
        }
        u.setCompanyId(companyId);
        u.setPassword(MD5Util.md5Encode(password));
        u.setUserRole(userRole);
        userMapper.insert(u);
    }

    public String login(String username, String password) throws Exception {
        User u = new User();
        u.setUserName(username);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        User ret = userMapper.selectOne(u);
        if (ret != null) {
            String pwd = MD5Util.md5Encode(password);
            if (!pwd.equals(ret.getPassword())) {
                throw new BusinessException("密码不正确!");
            }

            Map<String, String> claims = new HashMap<>();
            claims.put("id", ret.getId().toString());
            claims.put("userName", ret.getUserName());
            claims.put("userRole", ret.getUserRole());
            claims.put("companyId", ret.getCompanyId().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); //需要将date数据转移到Calender对象中操作
            calendar.add(Calendar.DATE, 30);//把日期往后增加n天.正数往后推,负数往前移动
            Date date = calendar.getTime();
            String token = StringUtils.generateUuid();
            return token;
        }
        throw new BusinessException("用户不存在!");
    }

    public List<UserVO> searchUsers(Integer page, Integer pageSize, String userRole) {
        List<UserVO> rets = new ArrayList<>();
        User u = new User();
        u.setPageSize(pageSize);
        if (StringUtils.isNotBlank(userRole)) {
            u.setUserRole(userRole);
        }
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        u.setStart((page - 1) * pageSize);
        List<User> users = userMapper.selectMany(u);
        System.out.println(users.size());
        if (!users.isEmpty()) {
            users.forEach(uu -> {
                rets.add(new UserVO(uu));
            });
        }
        return rets;
    }

    public void deleteUser(Integer userId) {
        User uu = new User();
        uu.setId(userId);
        uu.setDelstatus(DelStatusEnum.Delete.getValue());
        userMapper.updateByPrimaryKey(uu);
    }

    public void addOrUpdateUser(UserVO userVo) throws Exception {
        if (userVo.getId() == null) {
            addUser(userVo.getUserName(), userVo.getPassword(), userVo.getUserRole(), userVo.getCompanyId());
            return;
        }
        User uu = new User();
        uu.setId(userVo.getId());
        uu.setCompanyId(userVo.getCompanyId());
        uu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        uu.setUserRole(userVo.getUserRole());
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            uu.setPassword(MD5Util.md5Encode(userVo.getPassword()));
        }
        uu.setUserName(userVo.getUserName());
        userMapper.updateByPrimaryKeySelective(uu);
    }

    public void updatePwd(User u, String oldPwd, String newPwd) throws Exception {
        if (!MD5Util.md5Encode(oldPwd).equals(u.getPassword())) {
            throw new BusinessException("旧密码不正确!");
        }
        u.setPassword(MD5Util.md5Encode(newPwd));
        userMapper.updateByPrimaryKey(u);
    }

    public UserVO get(Integer userId) {
        User uu = new User();
        uu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        uu.setId(userId);
        uu = userMapper.selectOne(uu);
        if (uu != null) {
            return new UserVO(uu);
        }
        return null;
    }
}
