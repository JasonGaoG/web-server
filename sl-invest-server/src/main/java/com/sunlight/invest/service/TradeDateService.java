package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.dao.TradeDateMapper;
import com.sunlight.invest.model.TradeDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("tradeDateService")
public class TradeDateService {

    @Resource
    private TradeDateMapper tradeDateMapper;

    /**
     * 判断日期是否是交易日
     * @return boolean
     */
    public boolean isTradeDate(Date date){
        if (date == null) {
            date = new Date();
        }
        String checkD = DateUtils.getDateString(date, "yyyy-MM-dd");
        TradeDate td = new TradeDate();
        td.settDate(checkD);
        td.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<TradeDate> rets = tradeDateMapper.selectMany(td);
        return rets != null && rets.size() > 0;
    }

    public List<TradeDate> getTradeDate(Integer count, String toTime){
        if (count == null) {
            count = 30;
        }
        if (StringUtils.isBlank(toTime)) {
            toTime = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        }
        TradeDate td = new TradeDate();
        td.setPageSize(count);
        td.setStart(0);
        td.setOrderColumn("t_date");
        td.setOrderBy("desc");
        td.setDateStr(toTime);
        td.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return tradeDateMapper.selectMany(td);
    }
}
