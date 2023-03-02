package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.portal.accounts.dao.CompanyMapper;
import com.sunlight.portal.accounts.model.Company;
import com.sunlight.portal.accounts.vo.CompanyVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("companyService")
public class CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    public void addCompany(CompanyVO vo){
        Company company = new Company();
        company.setName(vo.getName());
        company.setDelstatus(DelStatusEnum.UnDelete.getValue());
        Company old = companyMapper.selectOne(company);
        if (old != null) {
            throw new BusinessException("公司名称已存在");
        }
        company.setAddress(vo.getAddress());
        company.setRemarks(vo.getRemarks());
        company.setPushUrl(vo.getPushUrl());
        companyMapper.insert(company);
    }

    public void addOrUpdateCompany(CompanyVO companyVO) {
        if (companyVO.getId() == null) {
            addCompany(companyVO);
            return;
        }
        Company c = new Company();
        c.setId(companyVO.getId());
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        c.setRemarks(companyVO.getRemarks());
        c.setName(companyVO.getName());
        c.setAddress(companyVO.getAddress());
        c.setPushUrl(companyVO.getPushUrl());
        companyMapper.updateByPrimaryKey(c);

    }

    public void deleteCompany(Integer companyId) {
        Company c = new Company();
        c.setId(companyId);
        c.setDelstatus(DelStatusEnum.Delete.getValue());
        companyMapper.updateByPrimaryKeySelective(c);
    }

    public List<CompanyVO> searchCompanys(Integer page, Integer pageSize, String companyName) {
        List<CompanyVO> rets = new ArrayList<>();
        Company c = new Company();
        c.setPageSize(pageSize);
        c.setStart((page - 1) * pageSize);
        if (StringUtils.isNotBlank(companyName)) {
            c.setSearchKeyWords(companyName);
        }
        List<Company> clist = companyMapper.selectMany(c);
        if (clist.size() > 0) {
            clist.forEach(com -> {
                rets.add(new CompanyVO(com));
            });
        }
        return rets;
    }

    public CompanyVO get(Integer companyId) {
        Company c = new Company();
        c.setId(companyId);
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        c = companyMapper.selectOne(c);
        if (c != null) {
            return new CompanyVO(c);
        }
        return null;
    }
}
