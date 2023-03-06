package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.MD5Util;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.utils.TokenUtils;
import com.sunlight.portal.accounts.dao.UserMapper;
import com.sunlight.portal.accounts.model.User;
import com.sunlight.portal.accounts.vo.UserVO;
import com.sunlight.portal.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.sunlight.common.constant.CommonConstant.USER_LOGIN_REDIS;

@Service("userService")
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisService redisService;

    public void addUser (String userName, String password, String userRoleCode, Integer companyId) throws Exception{
        User u = new User();
        u.setUserName(userName);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        User ret = userMapper.selectOne(u);
        if (ret != null) {
            throw new BusinessException("用户已经存在");
        }
        u.setCompanyId(companyId);
        u.setPassword(MD5Util.md5Encode(password));
        u.setUserRoleCode(userRoleCode);
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
            claims.put("userId", ret.getId().toString());
            claims.put("userName", ret.getUserName());
            claims.put("userRoleCode", ret.getUserRoleCode());
            String token = TokenUtils.generateToken(claims);
            if (token == null) {
                throw new BusinessException("token 生成失败!");
            }
            redisService.setStringValue(USER_LOGIN_REDIS + ret.getId(), token);
            return token;
        }
        throw new BusinessException("用户不存在!");
    }

    public List<UserVO> searchUsers(Integer page, Integer pageSize, String userRoleCode) {
        List<UserVO> rets = new ArrayList<>();
        User u = new User();
        u.setPageSize(pageSize);
        if (StringUtils.isNotBlank(userRoleCode)) {
            u.setUserRoleCode(userRoleCode);
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
            addUser(userVo.getUserName(), userVo.getPassword(), userVo.getUserRoleCode(), userVo.getCompanyId());
            return;
        }
        User uu = new User();
        uu.setId(userVo.getId());
        uu.setCompanyId(userVo.getCompanyId());
        uu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        uu.setUserRoleCode(userVo.getUserRoleCode());
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
        User uu = userMapper.selectByPrimaryKey(userId);
        if (uu != null) {
            return new UserVO(uu);
        }
        return null;
    }
}
