package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.dao.InvestUserMapper;
import com.sunlight.invest.dao.ProfitMapper;
import com.sunlight.invest.model.InvestUser;
import com.sunlight.invest.model.Profit;
import com.sunlight.invest.vo.InvestUserVo;
import com.sunlight.invest.vo.ProfitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("investService")
public class InvestService {

    @Resource
    private ProfitMapper profitMapper;

    @Resource
    private InvestUserMapper investUserMapper;

    public InvestUserVo getInvestUser(Integer id){
        InvestUser uu = investUserMapper.selectByPrimaryKey(id);
        if (uu != null) {
            return new InvestUserVo(uu);
        }
        return null;
    }

    public void addOrUpdateProfit(ProfitVo vo) throws ParseException {
        Profit pf = new Profit();
        pf.setDate(DateUtils.getDate(vo.getDate()));
        pf.setUserId(vo.getUserId());
        pf.setStockName(vo.getStockName());
        pf.setProfit(vo.getProfit());
        pf.setCompanyId(vo.getCompanyId());
        if (vo.getId() != null) {
            pf.setId(vo.getId());
            pf.setSettled(vo.getSettled());
            profitMapper.updateByPrimaryKey(pf);
        } else {
            pf.setSettled(0);
            InvestUser uu = investUserMapper.selectByPrimaryKey(vo.getUserId());
            pf.setCompanyId(uu.getCompanyId());
            profitMapper.insert(pf);
        }
    }

    public int count(Integer userId, String startTime, String endTime, Integer companyId) {
        List<ProfitVo> ret = new ArrayList<>();
        Profit p = new Profit();
        p.setUserId(userId);
        p.setCompanyId(companyId);
        Date start = null;
        if (!StringUtils.isBlank(startTime)) {
            start = DateUtils.strToDate(startTime, "yyyy-MM-dd");
            p.setStartTime(start);
        }
        Date end = null;
        if (!StringUtils.isBlank(endTime)) {
            end = DateUtils.strToDate(endTime, "yyyy-MM-dd");
            p.setEndTime(end);
        }
        return profitMapper.count(p);
    }
    public List<ProfitVo> getProfits(Integer userId, Integer page, Integer pageSize,
                                     String startTime, String endTime, Integer companyId) {
        List<ProfitVo> ret = new ArrayList<>();
        Profit p = new Profit();
        p.setUserId(userId);
        p.setStart((page - 1) * pageSize);
        p.setPageSize(pageSize);
        p.setCompanyId(companyId);
        Date start = null;
        if (!StringUtils.isBlank(startTime)) {
            start = DateUtils.strToDate(startTime, "yyyy-MM-dd");
            p.setStartTime(start);
        }
        Date end = null;
        if (!StringUtils.isBlank(endTime)) {
            end = DateUtils.strToDate(endTime, "yyyy-MM-dd");
            p.setEndTime(end);
        }
        List<Profit> pts = profitMapper.selectMany(p);
        for (Profit pt : pts) {
            ret.add(new ProfitVo(pt));
        }
        return ret;
    }

    public List<InvestUserVo> getUsers(Integer page, Integer pageSize, Integer companyId) {

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

    public void deleteProfit(Integer profitId) {
        profitMapper.deleteByPrimaryKey(profitId);
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

    public void settleProfit(List<Integer> ids) {
        Profit p = new Profit();
        p.setIds(ids);
        profitMapper.updateSettled(p);
    }

    public void deleteInvestUser(Integer userId) {
        InvestUser u = new InvestUser();
        u.setId(userId);
        u.setDelstatus(DelStatusEnum.UnDelete.getValue());
        investUserMapper.updateByPrimaryKeySelective(u);
    }

    public Double getUnSettledProfits(Integer userId, Integer companyId) {
        Double ret = 0D;
        Profit p = new Profit();
        p.setCompanyId(companyId);
        if (userId != null) {
            p.setUserId(userId);
        }
        p.setSettled(DelStatusEnum.UnDelete.getValue());
        List<Profit> pts = profitMapper.selectMany(p);
        for (Profit pt : pts) {
            ret += pt.getProfit();
        }
        return ret;
    }
}
