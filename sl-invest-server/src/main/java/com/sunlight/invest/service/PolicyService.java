package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.MathUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.dao.CompositeIndexMapper;
import com.sunlight.invest.dao.PolicySelHighLowNotifyMapper;
import com.sunlight.invest.model.CompositeIndex;
import com.sunlight.invest.model.PolicySelHighLowNotify;
import com.sunlight.invest.utils.StockUtils;
import com.sunlight.invest.vo.PolicySelHighLowNotifyVo;
import com.sunlight.invest.vo.PolicySelHighLowStatisVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("policyService")
public class PolicyService {

    @Resource
    private PolicySelHighLowNotifyMapper policySelHighLowNotifyMapper;

    @Resource
    private CompositeIndexMapper compositeIndexMapper;

    public void insertSelHighLowNotify(PolicySelHighLowNotify pn){
        pn.setDelstatus(DelStatusEnum.UnDelete.getValue());
        policySelHighLowNotifyMapper.insert(pn);
    }

    public List<PolicySelHighLowNotifyVo> searchSelHighLowNotify(String date, Integer type){
        List<PolicySelHighLowNotifyVo> rets = new ArrayList<>();
        PolicySelHighLowNotify pn = new PolicySelHighLowNotify();
        pn.setDelstatus(DelStatusEnum.UnDelete.getValue());
        pn.setNotifyDate(date);
        pn.setNotifyType(type);
        pn.setNotifyDays(29);
        List<PolicySelHighLowNotify> list = policySelHighLowNotifyMapper.selectMany(pn);
        for(PolicySelHighLowNotify p : list) {
            rets.add(new PolicySelHighLowNotifyVo(p));
        }
        return rets;
    }

    public void updatePolicySelHighLowInfo(Integer id, Double price, Integer type) {
        PolicySelHighLowNotify pn = policySelHighLowNotifyMapper.selectByPrimaryKey(id);
        if (pn == null) {
            throw new BusinessException("id 不正确");
        }
        String date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        if (type == 0) {
            pn.setBuyPrice(price);
            pn.setBuyDate(date);
            pn.setHasBuy(0);
        } else {
            if (StringUtils.isBlank(pn.getBuyDate())){
                pn.setBuyDate(pn.getNotifyDate());
                pn.setBuyPrice(pn.getNotifyPrice());
                pn.setHasBuy(0);
            }
            pn.setSellPrice(price);
            pn.setHasSell(0);
            pn.setProfit(MathUtils.subDouble(price, pn.getBuyPrice()));
            pn.setSellDate(date);
        }
        policySelHighLowNotifyMapper.updateByPrimaryKeySelective(pn);
    }

    public void updateSelHighLowNotifyInfos() {
        log.info("updateSelHighLowNotifyInfo.. start ");
        PolicySelHighLowNotify pn = new PolicySelHighLowNotify();
        pn.setNotifyDays(29); // 更新30天以内的
        pn.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<PolicySelHighLowNotify> rets = policySelHighLowNotifyMapper.selectMany(pn);
        StringBuilder sb = new StringBuilder();
        for(PolicySelHighLowNotify p: rets) {
            sb.append(StockUtils.getCodePrefix(p.getCode())).append(p.getCode()).append(",");
        }
        String details = StockUtils.getStockDetail(sb.toString());
        List<StockInfoVo> stockInfoVos = StockUtils.paresTXStock(details);
        String date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        for (StockInfoVo vo : stockInfoVos) {
            for (PolicySelHighLowNotify pp : rets) {
                if (!pp.getCode().equals(vo.getCode())) {
                    continue;
                }
                pp.setPrices(pp.getPrices() + "," + vo.getCurrentPrice());
                pp.setDays(pp.getDays() + 1);
                if (pp.getHighEstPrice() < vo.getHighestPrice()) {
                    pp.setHighEstDate(date);
                    pp.setHighEstPrice(vo.getHighestPrice());
                    if (pp.getHasBuy().equals(0) && pp.getBuyPrice() != null) {
                        pp.setBestProfit(MathUtils.subDouble(vo.getHighestPrice(),pp.getBuyPrice()));
                    } else {
                        pp.setBestProfit(MathUtils.subDouble(vo.getHighestPrice(),pp.getNotifyPrice()));
                    }
                }
                if (pp.getLowEstPrice() < vo.getLowestPrice()) {
                    pp.setLowEstDate(date);
                    pp.setLowEstPrice(vo.getHighestPrice());
                    if (pp.getHasBuy().equals(0) && pp.getBuyPrice() != null) {
                        pp.setBadProfit(MathUtils.subDouble(vo.getLowestPrice(),pp.getBuyPrice()));
                    } else {
                        pp.setBadProfit(MathUtils.subDouble(vo.getLowestPrice(),pp.getNotifyPrice()));
                    }
                }
                policySelHighLowNotifyMapper.updateByPrimaryKeySelective(pp);
            }
        }
    }

    public void updateCompositeIndex() {
        // 上证A
        String sb = "sh" + Constant.SH_A + "," + "sh" + Constant.SH_K + "," +
                "sz" + Constant.SZ_A + "," + "sz" + Constant.SZ_C;
        String str = StockUtils.getStockDetail(sb);
        List<StockInfoVo> vos = StockUtils.paresTXStock(str);
        for (StockInfoVo vo: vos) {
            CompositeIndex index =new CompositeIndex();
            index.setDelstatus(DelStatusEnum.UnDelete.getValue());
            index.setUpPercent(vo.getUpPercent());
            index.setMarket(vo.getCode());
            index.setMarketName(vo.getName());
            index.setPrice(vo.getCurrentPrice());
            index.setRecordDate(DateUtils.getDateString(new Date(), "yyyy-MM-dd"));
            index.setTurnOver(vo.getTurnOver());
            compositeIndexMapper.insert(index);
        }
    }

    public List<CompositeIndex> getCompositeIndex(String date) {
        CompositeIndex index = new CompositeIndex();
        index.setDelstatus(DelStatusEnum.UnDelete.getValue());
        index.setRecordDate(date);
        return compositeIndexMapper.selectMany(index);
    }

    public void deletePolicySelHighLowInfo(Integer id) {
        PolicySelHighLowNotify pn = new PolicySelHighLowNotify();
        pn.setId(id);
        pn.setDelstatus(DelStatusEnum.Delete.getValue());
        policySelHighLowNotifyMapper.updateByPrimaryKeySelective(pn);
    }

    /**
     * 统计最近30天内的打板收益情况
      * @param date yyyy-MM-dd
     */
    public PolicySelHighLowStatisVo statisPolicySelHighLow(String date) {
        PolicySelHighLowStatisVo ret = new PolicySelHighLowStatisVo();
        PolicySelHighLowNotify pn = new PolicySelHighLowNotify();
        pn.setDelstatus(DelStatusEnum.UnDelete.getValue());
        pn.setNotifyDays(29);
        List<PolicySelHighLowNotify> rets = policySelHighLowNotifyMapper.selectMany(pn);

        Integer money = 10000; // 10000块钱模拟买入收益
        double allMoney = 0D; // 所有股票都按通知价格买入的花费
        for (PolicySelHighLowNotify pp: rets) {
            Integer stockCount = Math.toIntExact(Math.round((money / pp.getNotifyPrice() / 100))) * 100;
            allMoney += stockCount * pp.getNotifyPrice();
            if (pp.getProfit() == null) {
                pp.setProfit(pp.getHighEstPrice() - pp.getNotifyPrice());
            }
            Double profit = stockCount * pp.getProfit();
            if (pp.getNotifyType().equals(Constant.NOTIFY_HIGH_UP)) {
                ret.setUpCount(ret.getUpCount() + 1);
                // 最高价日期不是通知日期的才能有收益。
                if (pp.getProfit() > 0 && !pp.getNotifyDate().equals(pp.getHighEstDate())) {
                    ret.setUpProfitCount(ret.getUpProfitCount() + 1);
                    ret.setUpProfit(MathUtils.addDouble(ret.getUpProfit(), profit));
                }
            } else {
                ret.setDownCount(ret.getDownCount() + 1);
                // 最高价日期不是通知日期的才能有收益。
                if (pp.getProfit() > 0 && !pp.getNotifyDate().equals(pp.getHighEstDate())) {
                    ret.setDownProfitCount(ret.getDownProfitCount() + 1);
                    ret.setDownProfit(MathUtils.addDouble(ret.getDownProfit() , profit));
                }
            }
        }
        ret.setDownProfitCountRate(MathUtils.div(ret.getDownProfitCount(),ret.getDownCount(), 2));
        ret.setUpProfitCountRate(MathUtils.div(ret.getUpProfitCount(),ret.getUpCount(), 2));
        ret.setUpProfitRate(MathUtils.div(ret.getUpProfit(),allMoney, 2));
        ret.setDownProfitRate(MathUtils.div(ret.getDownProfit(),allMoney, 2));
        return ret;
    }
}
