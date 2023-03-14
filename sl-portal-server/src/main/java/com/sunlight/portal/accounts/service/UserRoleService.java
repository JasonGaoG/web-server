package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.portal.accounts.dao.UserRoleMapper;
import com.sunlight.portal.accounts.model.UserRole;
import com.sunlight.portal.accounts.vo.UserRoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userRoleService")
public class UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    public List<UserRoleVO> searchUserRoleList(Integer page, Integer pageSize) {
        List<UserRoleVO> ret = new ArrayList<>();
        UserRole role = new UserRole();
        role.setPageSize(pageSize);
        role.setStart((page-1) * pageSize);
        role.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<UserRole> list = userRoleMapper.selectMany(role);
        for(UserRole r : list) {
            ret.add(new UserRoleVO(r));
        }
        return ret;
    }

    public void deleteRoleById(Integer roleId) {
        UserRole role = new UserRole();
        role.setId(roleId);
        role.setDelstatus(DelStatusEnum.Delete.getValue());
        userRoleMapper.updateByPrimaryKeySelective(role);
    }

    public void update(UserRoleVO userRoleVO) {
        UserRole role = convert(userRoleVO);
        userRoleMapper.updateByPrimaryKeySelective(role);
    }

    public void addUserRole(UserRoleVO userRoleVO) {
        UserRole role = convert(userRoleVO);
        role.setDelstatus(DelStatusEnum.UnDelete.getValue());
        role.setRoleCode(StringUtils.generateUuid());
        userRoleMapper.insert(role);
    }

    private UserRole convert(UserRoleVO userRoleVO) {
        UserRole role = new UserRole();
        role.setId(userRoleVO.getId());
        role.setRoleCode(userRoleVO.getRoleCode());
        role.setRoleName(userRoleVO.getRoleName());
        role.setRemarks(userRoleVO.getRemarks());
        role.setAccessPoints(StringUtils.listToStr(userRoleVO.getAccessPoints()));
        return role;
    }

    public UserRoleVO getByRoleCode(String roleCode) {
        UserRole temp = new UserRole();
        temp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        temp.setRoleCode(roleCode);
        UserRole role = userRoleMapper.selectOne(temp);
        if (role == null) {
            return null;
        }
        return new UserRoleVO(role);
    }
}
