package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.invest.dao.InvestUserMapper;
import com.sunlight.invest.model.InvestUser;
import com.sunlight.invest.vo.InvestUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("investUserService")
public class InvestUserUService {

    @Resource
    private InvestUserMapper investUserMapper;

    public InvestUserVo getInvestUser(Integer id){
        InvestUser uu = investUserMapper.selectByPrimaryKey(id);
        if (uu != null) {
            return new InvestUserVo(uu);
        }
        return null;
    }

    public List<InvestUserVo> getUsers(Integer page, Integer pageSize, Integer companyId) {

        if (companyId == -1) {
            companyId = null;
        }
        List<InvestUserVo> ret = new ArrayList<>();
        InvestUser u = new InvestUser();
        u.setStart((page - 1) * pageSize + 1);
        u.setCompanyId(companyId);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<InvestUser> uus = investUserMapper.selectMany(u);
        for (InvestUser uu : uus) {
            ret.add(new InvestUserVo(uu));
        }
        return ret;
    }

    public void addOrUpdateUser(InvestUserVo userVo) {
        InvestUser u = new InvestUser();
        u.setRemarks(userVo.getRemarks());
        u.setUserName(userVo.getUserName());
        u.setCompanyId(userVo.getCompanyId());
        if (userVo.getId() != null) {
            u.setId(userVo.getId());
            u.setDelstatus(DelStatusEnum.UnDelete.getValue());
            investUserMapper.updateByPrimaryKey(u);
        } else {
            u.setDelstatus(DelStatusEnum.UnDelete.getValue());
            investUserMapper.insert(u);
        }
    }

    public void deleteInvestUser(Integer userId) {
        InvestUser u = new InvestUser();
        u.setId(userId);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        investUserMapper.updateByPrimaryKeySelective(u);
    }
}
