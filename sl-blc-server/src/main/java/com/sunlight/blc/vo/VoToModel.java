package com.sunlight.blc.vo;


import com.sunlight.blc.model.Cost;
import com.sunlight.common.utils.DateUtils;

/**
 * @author Administrator
 * @package com.kedacom.blockchain.utils
 * @description vo 转 model
 * @date 2019/3/21
 */
public class VoToModel {


    public static Cost toCost(CostVO vo) {
        if (vo == null){
            return  null;
        }
        Cost c = new Cost();
        c.setId(vo.getId());
        c.setAmount(vo.getAmount());
        c.setDatatime(DateUtils.strToDate(vo.getDateTime()));
        c.setDetails(vo.getDetails());
        c.setRemarks(vo.getRemarks());
        c.setBalance(vo.getBalance());
        return c;
    }
}
