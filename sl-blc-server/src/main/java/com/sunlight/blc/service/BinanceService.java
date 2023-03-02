package com.sunlight.blc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.impl.SpotClientImpl;
import com.sunlight.blc.binance.BinanceConstant;
import com.sunlight.blc.binance.BinanceContext;
import com.sunlight.blc.binance.model.BinanceBalance;
import com.sunlight.blc.dao.DailyProfitMapper;
import com.sunlight.blc.dao.DepositMapper;
import com.sunlight.blc.dao.ExchangeMapper;
import com.sunlight.blc.model.DailyCurrency;
import com.sunlight.blc.model.DailyProfit;
import com.sunlight.blc.model.Deposit;
import com.sunlight.blc.model.Exchange;
import com.sunlight.blc.vo.DailyProfitVO;
import com.sunlight.blc.vo.DepositVO;
import com.sunlight.blc.vo.ExchangeVO;
import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.MathUtils;
import com.sunlight.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 币安接口
 *
 * @author jason
 * @date 2019-12-09
 */
@Slf4j
@Service("binanceService")
public class BinanceService {

    @Resource
    private DailyProfitMapper dailyProfitMapper;

    @Resource
    private ExchangeMapper exchangeMapper;

    @Resource
    private DepositMapper depositMapper;

    public Double getCurrencyAmount(String currency) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        double ret = 0D;
        SpotClientImpl client = new SpotClientImpl(BinanceConstant.getKey(), BinanceConstant.getSecret());
        String result = client.createTrade().account(parameters);
        log.info(result);
        JSONObject obj = JSONObject.parseObject(result);
        List<BinanceBalance> balances = JSON.parseArray(obj.getString("balances"), BinanceBalance.class);
        for(BinanceBalance binanceBalance : balances) {
            if (binanceBalance.getAsset().equals(currency)) {
                ret += Double.parseDouble(binanceBalance.getFree());
            }
        }
        return ret;
    }

    public void sellCurrency(String from, String to, String amount) {
        log.info("sell binance currency, from:{}, to: {}; amount:{}",
                from, to, amount);
        String symbol = (from + to).toUpperCase(Locale.ROOT);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client2 = new SpotClientImpl(BinanceConstant.getKey(),
                BinanceConstant.getSecret());

        parameters.put("symbol", symbol);
        parameters.put("side","SELL");
        parameters.put("type","MARKET");
        parameters.put("quantity", amount);
        String result = client2.createTrade().newOrder(parameters);
        log.info("sell currency, result:{}", result);
    }

    /**
     * 市价直接卖出某币成btc
     *
     * @param currency 币种
     * @param amount   数量
     */
    public void sellCurrencyToBtc(String currency, String amount) {
        log.info("sell binance currency to btc, from:{}, to: btc; amount:{}",
                currency, amount);
        String symbol = (currency + "BTC").toUpperCase(Locale.ROOT);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client2 = new SpotClientImpl(BinanceConstant.getKey(),
                BinanceConstant.getSecret());

        parameters.put("symbol", symbol);
        parameters.put("side","BUY");
        parameters.put("type","MARKET");
        parameters.put("quoteOrderQty", amount);
        String result = client2.createTrade().newOrder(parameters);
        log.info("sell currency, result:{}", result);
    }

    /**
     * 查询币安的btc币的总数
     *
     * @return double
     */
    public double getAccountBtcBalances() {
        return getCurrencyAmount("BTC");
    }

    /**
     * 订阅btc价格
     */
    public void subscribePrice(){
        BinanceContext.getInstance().subPrice();
    }

    public void saveDailyProfit(DailyProfit dp) {
        dp.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (StringUtils.isBlank(dp.getDay())) {
            dp.setDay(DateUtils.getDateString(new Date(), "yyyy-MM-dd"));
        }
        dailyProfitMapper.insert(dp);
    }

    /**
     * 查询货币兑换信息
     */
    public List<ExchangeVO> searchExchanges(String account, String symbol, String fromTime, String toTime,
                                            Integer page, Integer pageSize) {
        List<ExchangeVO> ret = new ArrayList<>();

        Exchange exchange = new Exchange();
        if (StringUtils.isNotBlank(fromTime)) {
            exchange.setFromTime(DateUtils.strToDate(fromTime));
        }
        if (StringUtils.isNotBlank(toTime)) {
            exchange.setToTime(DateUtils.strToDate(toTime));
        }
        if (StringUtils.isNotBlank(account)) {
            exchange.setAccount(account);
        }
        if (StringUtils.isNotBlank(symbol)) {
            exchange.setSymbol(symbol);
        }
        exchange.setOrderBy("desc");
        exchange.setOrderColumn("finished_time");
        exchange.setPageSize(pageSize);
        exchange.setStart((page - 1) * pageSize);
        exchange.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Exchange> list = exchangeMapper.selectMany(exchange);
        if (list != null && list.size() > 0) {
            for (Exchange ex : list) {
                ret.add(convertToVO(ex));
            }
        }
        return ret;
    }
    private ExchangeVO convertToVO(Exchange ex) {
        if (ex == null) {
            return null;
        }
        ExchangeVO vo = new ExchangeVO();
        vo.setId(ex.getId());
        vo.setAccount(ex.getAccount());
        vo.setAmount(ex.getAmount());
        vo.setFees(ex.getFees());
        vo.setSymbol(ex.getSymbol());
        vo.setFilledBtcAmount(ex.getFilledBtcAmount());
        vo.setFinishedTime(DateUtils.getDateString(ex.getFinishedTime()));
        vo.setCreateTime(DateUtils.getDateString(ex.getCreateTime()));
        vo.setOrderId(ex.getOrderId());
        vo.setRemarks(ex.getRemarks());
        return vo;
    }
    public Exchange getExchangeByRefId(String refId) {
        Exchange exchange = new Exchange();
        exchange.setOrderId(refId);
        exchange.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Exchange> list = exchangeMapper.selectMany(exchange);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public int searchExchangesTotal(String account, String keywords, String fromTime, String toTime) {
        Exchange exchange = new Exchange();
        if (StringUtils.isNotBlank(fromTime)) {
            exchange.setFromTime(DateUtils.strToDate(fromTime));
        }
        if (StringUtils.isNotBlank(toTime)) {
            exchange.setToTime(DateUtils.strToDate(toTime));
        }
        if (StringUtils.isNotBlank(account)) {
            exchange.setAccount(account);
        }
        exchange.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return exchangeMapper.count(exchange);
    }

    public String searchExchangesTotalFilled(String account, String keywords, String fromTime, String toTime) {
        Exchange exchange = new Exchange();
        if (StringUtils.isNotBlank(fromTime)) {
            exchange.setFromTime(DateUtils.strToDate(fromTime));
        }
        if (StringUtils.isNotBlank(toTime)) {
            exchange.setToTime(DateUtils.strToDate(toTime));
        }
        if (StringUtils.isNotBlank(account)) {
            exchange.setAccount(account);
        }
        exchange.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Exchange> list = exchangeMapper.selectMany(exchange);
        if (list != null && list.size() > 0) {
            BigDecimal ret = new BigDecimal(0);
            for (Exchange ex : list) {
                ret = ret.add(new BigDecimal(ex.getFilledBtcAmount()));
            }
            return ret.toString();
        }
        return "0";
    }

    /**
     * 查询挖币记录信息
     *
     * @return
     */
    public List<DepositVO> searchDeposits(String account, String symbol, String fromTime, String toTime,
                                          Integer page, Integer pageSize) {
        List<DepositVO> ret = new ArrayList<>();

        Deposit deposit = new Deposit();
        deposit.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (StringUtils.isNotBlank(account)) {
            deposit.setAccount(account);
        }

        if (StringUtils.isNotBlank(symbol)) {
            deposit.setSymbol(symbol);
        }
        if (StringUtils.isNotBlank(fromTime)) {
            deposit.setFromTime(DateUtils.strToDate(fromTime));
        }

        if (StringUtils.isNotBlank(toTime)) {
            deposit.setToTime(DateUtils.strToDate(toTime));
        }

        deposit.setOrderBy("desc");
        deposit.setOrderColumn("create_time");
        deposit.setStart((page - 1) * pageSize);
        deposit.setPageSize(pageSize);

        List<Deposit> list = depositMapper.selectMany(deposit);
        if (list != null && list.size() > 0) {
            for (Deposit ex : list) {
                ret.add(convertToDeptVO(ex));
            }
        }
        return ret;
    }
    private DepositVO convertToDeptVO(Deposit ex) {
        DepositVO vo = new DepositVO();
        vo.setId(ex.getId());
        vo.setAccount(ex.getAccount());
        vo.setAmount(ex.getAmount());
        vo.setCreateTime(DateUtils.getDateString(ex.getCreateTime()));
        vo.setRefId(ex.getRefId());
        vo.setRemarks(ex.getRemarks());
        vo.setStatus(ex.getStatus());
        vo.setSymbol(ex.getSymbol());
        vo.setUpdateTime(DateUtils.getDateString(ex.getUpdateTime()));
        return vo;
    }

    public int searchDepositsTotal(String account, String symbol, String fromTime, String toTime) {
        Deposit deposit = new Deposit();
        deposit.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (StringUtils.isNotBlank(account)) {
            deposit.setAccount(account);
        }

        if (StringUtils.isNotBlank(symbol)) {
            deposit.setSymbol(symbol);
        }
        if (StringUtils.isNotBlank(fromTime)) {
            deposit.setFromTime(DateUtils.strToDate(fromTime));
        }

        if (StringUtils.isNotBlank(toTime)) {
            deposit.setToTime(DateUtils.strToDate(toTime));
        }
        return depositMapper.count(deposit);
    }

    public Deposit getDepositByRefId(Long refId) {
        Deposit deposit = new Deposit();
        deposit.setRefId(refId);
        deposit.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Deposit> list = depositMapper.selectMany(deposit);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public Long getLastUnFinishedId(String account, String symbol) {
        Deposit d = new Deposit();
        d.setDelstatus(DelStatusEnum.UnDelete.getValue());
        d.setAccount(account);
        d.setSymbol(symbol);
        String refId = depositMapper.getLastUnfinishedId(d);
        if (StringUtils.isBlank(refId)) {
            return null;
        }
        return Long.valueOf(refId);
    }

    public List<DailyProfitVO> getDailyProfits(Integer page, Integer pageSize) {
        DailyProfit p = new DailyProfit();

        p.setOrderBy("desc");
        p.setOrderColumn("id");
        p.setStart((page - 1) * pageSize);
        // 多查询一个，用于计算币数的增长
        p.setPageSize(pageSize + 1);
        p.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<DailyProfitVO> ret = new ArrayList<>();
        List<DailyProfit> list = dailyProfitMapper.selectMany(p);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DailyProfit dp = list.get(i);
                DailyProfitVO vo = convertToDailyProfitVO(dp);
                // 最后一个为多查询的，不做处理
                if (i != list.size() - 1) {
                    DailyProfit dpNext = list.get(i + 1);
                    vo.setAccountDcrBtcIncrease(MathUtils.subDouble(dp.getAccountDcrBtc(), dpNext.getAccountDcrBtc()));
                    vo.setAccountXvgBtcIncrease(MathUtils.subDouble(dp.getAccountXvgBtc(), dpNext.getAccountXvgBtc()));
                    vo.setTotalBtcIncrease(MathUtils.subDouble(dp.getTotalBtc(), dpNext.getTotalBtc()));
                    ret.add(vo);
                }
            }
        }
        return ret;
    }

    public int getDailyProfitsTotal() {
        DailyProfit p = new DailyProfit();
        p.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return dailyProfitMapper.count(p);
    }

    private DailyProfitVO convertToDailyProfitVO(DailyProfit ex) {
        if (ex == null) {
            return null;
        }
        DailyProfitVO vo = new DailyProfitVO();
        vo.setId(ex.getId());
        vo.setAccountDcrBtc(ex.getAccountDcrBtc());
        vo.setAccountXvgBtc(ex.getAccountXvgBtc());
        vo.setDay(ex.getDay());
        vo.setRemarks(ex.getRemarks());
        vo.setTotalBtc(ex.getTotalBtc());
        vo.setBtcPrice(ex.getBtcPrice());
        DailyCurrency dc = new DailyCurrency();
        dc.setDailyProfitId(ex.getId());
        dc.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return vo;
    }

    public void updateDailyProfitRemarks(Integer id, String remarks) {
        DailyProfit dp = new DailyProfit();
        dp.setId(id);
        dp.setRemarks(remarks);
        dailyProfitMapper.updateByPrimaryKeySelective(dp);
    }
}
