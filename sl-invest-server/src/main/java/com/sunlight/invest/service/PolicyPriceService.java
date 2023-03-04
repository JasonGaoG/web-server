package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.invest.dao.PolicyPriceMapper;
import com.sunlight.invest.model.PolicyPrice;
import com.sunlight.invest.vo.PolicyPriceVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("policyPriceService")
public class PolicyPriceService {

    @Resource
    private PolicyPriceMapper policyPriceMapper;

    public void add(PolicyPriceVo ppvo){
        PolicyPrice pp = converToModel(ppvo);
        pp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        policyPriceMapper.insert(pp);
    }

    private PolicyPrice converToModel(PolicyPriceVo ppvo) {
        PolicyPrice pp = new PolicyPrice();
        pp.setId(ppvo.getId());
        pp.setCode(ppvo.getCode());
        pp.setName(ppvo.getName());
        pp.setRemarks(ppvo.getRemarks());
        pp.setPrices(ppvo.getPrices());
        pp.setNewHigh20(ppvo.getNewHigh20());
        pp.setNewHigh20Date(ppvo.getNewHigh20Date());
        pp.setNewHigh60(ppvo.getNewHigh60());
        pp.setNewHigh60Date(ppvo.getNewHigh60Date());
        pp.setNewHigh90(ppvo.getNewHigh90());
        pp.setNewHigh90Date(ppvo.getNewHigh90Date());
        pp.setNewHighEst(ppvo.getNewHighEst());
        pp.setNewHighEstDate(ppvo.getNewHighEstDate());
        pp.setNewHigh120(ppvo.getNewHigh120());
        pp.setNewHigh120Date(ppvo.getNewHigh120Date());
        pp.setNewLow20(ppvo.getNewLow20());
        pp.setNewLow20Date(ppvo.getNewLow20Date());
        pp.setNewLow60(ppvo.getNewLow60());
        pp.setNewLow60Date(ppvo.getNewLow60Date());
        pp.setNewLow90(ppvo.getNewLow90());
        pp.setNewLow90Date(ppvo.getNewLow90Date());
        pp.setNewLow120(ppvo.getNewLow120());
        pp.setNewLow120Date(ppvo.getNewLow120Date());
        pp.setNewLowEst(ppvo.getNewLowEst());
        pp.setNewLowEstDate(ppvo.getNewLowEstDate());
        return pp;
    }

    public List<PolicyPriceVo> getAllPolicyPrices(){
        List<PolicyPriceVo> ret = new ArrayList<>();
        PolicyPrice pp = new PolicyPrice();
        pp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<PolicyPrice> pps = policyPriceMapper.selectMany(pp);
        for (PolicyPrice p: pps) {
            ret.add(new PolicyPriceVo(p));
        }
        return ret;
    }

    /**
     *
     * @param type 20 60 90 120 0
     * @param date yyyy-MM-dd
     */
    public List<PolicyPriceVo> searchPolicyPrices(Integer type, String date, Integer page, Integer pageSize){
        PolicyPrice pp = new PolicyPrice();
        pp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        switch (type) {
            case 1:
                pp.setNewHighEstDate(date);
                break;
            case 20:
                pp.setNewHigh20Date(date);
                break;
            case 60:
                pp.setNewHigh60Date(date);
                break;
            case 90:
                pp.setNewHigh90Date(date);
                break;
            case 120:
                pp.setNewHigh120Date(date);
                break;
            case -1:
                pp.setNewLowEstDate(date);
                break;
            case -20:
                pp.setNewLow20Date(date);
                break;
            case -60:
                pp.setNewLow60Date(date);
                break;
            case -90:
                pp.setNewLow90Date(date);
                break;
            case -120:
                pp.setNewLow120Date(date);
                break;
        }
        List<PolicyPriceVo> ret = new ArrayList<>();
        pp.setPageSize(pageSize);
        pp.setStart((page-1)*pageSize);
        List<PolicyPrice> pps = policyPriceMapper.selectMany(pp);
        for (PolicyPrice p: pps) {
            ret.add(new PolicyPriceVo(p));
        }
        return ret;
    }

    public void update(PolicyPriceVo old) {
        PolicyPrice pp = converToModel(old);
        policyPriceMapper.updateByPrimaryKeySelective(pp);
    }

    public int count(Integer type, String date) {
        PolicyPrice pp = new PolicyPrice();
        pp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        switch (type) {
            case 1:
                pp.setNewHighEstDate(date);
                break;
            case 20:
                pp.setNewHigh20Date(date);
                break;
            case 60:
                pp.setNewHigh60Date(date);
                break;
            case 90:
                pp.setNewHigh90Date(date);
                break;
            case 120:
                pp.setNewHigh120Date(date);
                break;
            case -1:
                pp.setNewLowEstDate(date);
                break;
            case -20:
                pp.setNewLow20Date(date);
                break;
            case -60:
                pp.setNewLow60Date(date);
                break;
            case -90:
                pp.setNewLow90Date(date);
                break;
            case -120:
                pp.setNewLow120Date(date);
                break;
        }
        return policyPriceMapper.count(pp);
    }
}
