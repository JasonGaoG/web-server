package com.sunlight.blc.service;

import com.sunlight.blc.constant.GlobalData;
import com.sunlight.blc.dao.CostMapper;
import com.sunlight.blc.model.Config;
import com.sunlight.blc.model.Cost;
import com.sunlight.blc.vo.CostVO;
import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.MathUtils;
import com.sunlight.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @package com.sunlight.blc.service
 * @description 花费操作
 * @date 2019/4/16
 */
@Slf4j
@Service("costService")
public class CostService {

    @Resource
    private CostMapper costMapper;

    @Resource
    private ConfigService configService;

    public void save(Cost cost) {
        cost.setDelstatus(DelStatusEnum.UnDelete.getValue());
        Cost last = costMapper.getLastOne(cost);
        if (last != null) {
            cost.setBalance(MathUtils.subDouble(last.getBalance(), cost.getAmount()));
        } else {
            Config c = configService.getByKey(GlobalData.ORI_MONEY);
            if (c == null || c.getValue().equals("0")) {
                throw new BusinessException("请先设置初始资金!");
            }
            Double ori = Double.valueOf(c.getValue());
            cost.setBalance(MathUtils.subDouble(ori, cost.getAmount()));
        }

        costMapper.insertSelective(cost);
    }

    public void update(Cost cost) {
        Cost old = costMapper.selectByPrimaryKey(cost.getId());
        if (old.getAmount().equals(cost.getAmount())) {
            costMapper.updateByPrimaryKeySelective(cost);
            return;
        }
        cost.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Cost> costs = costMapper.getFromId(cost);
        double edit = 0;
        for (int i = 0; i < costs.size(); i++) {
            Cost c = costs.get(i);
            if (i == 0) {
                // 要更新的对象
                edit = MathUtils.subDouble(cost.getAmount(), c.getAmount());
                c.setAmount(cost.getAmount());
                c.setRemarks(cost.getRemarks());
                c.setDetails(cost.getDetails());
                c.setBalance(MathUtils.subDouble(c.getBalance(), edit));
            } else {
                c.setBalance(MathUtils.subDouble(costs.get(i - 1).getBalance(), c.getAmount()));
            }
            costMapper.updateByPrimaryKeySelective(c);
        }
    }

    public void delete(Integer id) {
        Cost cost = new Cost();
        cost.setId(id);
        cost.setDelstatus(DelStatusEnum.Delete.getValue());
        costMapper.updateByPrimaryKeySelective(cost);
    }

    private CostVO convertToVO(Cost cost) {
        if (cost == null) {
            return null;
        }
        CostVO vo = new CostVO();
        vo.setId(cost.getId());
        vo.setBalance(cost.getBalance());
        vo.setAmount(cost.getAmount());
        vo.setDateTime(DateUtils.getDateString(cost.getDatatime(), "yyyy-MM-dd"));
        vo.setDetails(cost.getDetails());
        vo.setRemarks(cost.getRemarks());
        return vo;
    }

    public List<CostVO> searchCosts(String fromTime, String toTime, Integer page, Integer pageSize) {
        List<CostVO> ret = new ArrayList<>();

        Cost c = new Cost();
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (StringUtils.isNotBlank(fromTime)) {
            c.setFromTime(DateUtils.strToDate(fromTime, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(toTime)) {
            c.setToTime(DateUtils.strToDate(toTime, "yyyy-MM-dd"));
        }
        c.setStart((page - 1) * pageSize);
        c.setPageSize(pageSize);
        c.setOrderBy("desc");
        c.setOrderColumn("datatime desc,id");
        List<Cost> list = costMapper.selectMany(c);
        if (list != null && list.size() > 0) {
            for (Cost cost : list) {
                ret.add(convertToVO(cost));
            }
        }
        return ret;
    }

    public int searchCostsTotal(String fromTime, String toTime) {
        Cost c = new Cost();
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (StringUtils.isNotBlank(fromTime)) {
            c.setFromTime(DateUtils.strToDate(fromTime, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(toTime)) {
            c.setToTime(DateUtils.strToDate(toTime, "yyyy-MM-dd"));
        }
        return costMapper.count(c);
    }
}
