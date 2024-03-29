package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.MD5Util;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.utils.TokenUtils;
import com.sunlight.portal.accounts.dao.UserMapper;
import com.sunlight.portal.accounts.model.User;
import com.sunlight.portal.accounts.vo.CompanyVO;
import com.sunlight.portal.accounts.vo.UserRoleVO;
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
    private CompanyService companyService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RedisService redisService;

    public void addUser (UserVO userVO) throws Exception{
        User u = new User();
        u.setUserName(userVO.getUserName());
        u.setUserRoleCode(userVO.getUserRoleCode());
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        User ret = userMapper.selectOne(u);
        if (ret != null) {
            throw new BusinessException("用户已经存在");
        }
        u.setCompanyId(userVO.getCompanyId());
        u.setPassword(MD5Util.md5Encode(userVO.getPassword()));
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
            claims.put("companyId", ret.getCompanyId()+"");
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
        if (!users.isEmpty()) {
            users.forEach(uu -> {
                rets.add(convert(uu));
            });
        }
        return rets;
    }

    private UserVO convert(User uu) {
        UserVO vo = new UserVO();
        vo.setId(uu.getId());
        vo.setCompanyId(uu.getCompanyId());
        vo.setUserName(uu.getUserName());
        vo.setUserRoleCode(uu.getUserRoleCode());
        CompanyVO c = companyService.get(uu.getCompanyId());
        if (c != null) {
            vo.setCompanyName(c.getName());
        } else {
            vo.setCompanyName("未知");
        }

        UserRoleVO role = userRoleService.getByRoleCode(uu.getUserRoleCode());
        if (role != null) {
            vo.setUserRoleName(role.getRoleName());
        } else {
            vo.setUserRoleName("未知");
        }
        return vo;
    }

    public void deleteUser(Integer userId) {
        User uu = new User();
        uu.setId(userId);
        uu.setDelstatus(DelStatusEnum.Delete.getValue());
        userMapper.updateByPrimaryKeySelective(uu);
    }

    public void update(UserVO userVo) throws Exception {
        User uu = new User();
        uu.setId(userVo.getId());
        uu.setCompanyId(userVo.getCompanyId());
        uu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        uu.setUserRoleCode(userVo.getUserRoleCode());
        // 更新没有密码，密码单独重置接口
//        if (StringUtils.isNotBlank(userVo.getPassword())) {
//            uu.setPassword(MD5Util.md5Encode(userVo.getPassword()));
//        }
        uu.setUserName(userVo.getUserName());
        userMapper.updateByPrimaryKeySelective(uu);
    }

    public void updatePwd(Integer userId, String newPwd, String oldPwd) throws Exception {
        User u = userMapper.selectByPrimaryKey(userId);
        if (!MD5Util.md5Encode(oldPwd).equals(u.getPassword())) {
            throw new BusinessException("旧密码不正确!");
        }
        u.setPassword(MD5Util.md5Encode(newPwd));
        userMapper.updateByPrimaryKey(u);
    }

    public UserVO get(Integer userId) {
        User uu = userMapper.selectByPrimaryKey(userId);
        if (uu != null) {
            return convert(uu);
        }
        return null;
    }
}
