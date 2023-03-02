package com.sunlight.blc.web;

import com.sunlight.blc.annotation.Authorization;
import com.sunlight.blc.constant.GlobalData;
import com.sunlight.blc.constant.PermissionClassEnum;
import com.sunlight.blc.model.Config;
import com.sunlight.blc.model.Cost;
import com.sunlight.blc.service.ConfigService;
import com.sunlight.blc.service.CostService;
import com.sunlight.blc.vo.CostVO;
import com.sunlight.blc.vo.HttpResult;
import com.sunlight.blc.vo.VoToModel;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @package com.sunlight.blc.web
 * @description 花费接口
 * @date 2019/3/19
 */
@Slf4j
@RequestMapping("/api/cost")
@RestController
public class CostController {

    @Resource
    private CostService costService;

    @Resource
    private ConfigService configService;

    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @RequestMapping(value = "/v1/delete", method = RequestMethod.DELETE)
    public HttpResult delete(@RequestBody String ids) {
        try {
            List<Integer> idList = StringUtils.toIntegerIds(ids);
            for(Integer id:idList){
                costService.delete(id);
            }
            return HttpResult.ok("删除成功");
        } catch (Exception e) {
            log.error("删除消费失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("删除消费失败！");
        }
    }

    /**
     * 获取初始化资金
     */
    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @RequestMapping(value = "/v1/getOriMoney", method = RequestMethod.GET)
    public HttpResult getOriMoney() {
        try {
            Config config = configService.getByKey(GlobalData.ORI_MONEY);
            if(config == null){
                config = new Config();
                config.setValue("0");
                config.setKeyItem(GlobalData.ORI_MONEY);
                configService.save(config);
            }
            return HttpResult.ok("记录成功",config);
        } catch (Exception e) {
            log.error("记录消费失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("记录消费失败！");
        }
    }

    /**
     * 更新初始化资金
     */
    @RequestMapping(value = "/v1/updateOriMoney", method = RequestMethod.POST)
    public HttpResult updateOriMoney(String money) {
        try {
            Config config = configService.getByKey(GlobalData.ORI_MONEY);
            config.setValue(money);
            configService.update(config);
            return HttpResult.ok("修改成功");
        } catch (Exception e) {
            log.error("修改失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("修改失败！");
        }
    }

    @RequestMapping(value = "/v1/add", method = RequestMethod.POST)
    public HttpResult delete(@RequestBody CostVO costVO) {
        try {
            Cost c = VoToModel.toCost(costVO);
            costService.save(c);
            return HttpResult.ok("记录成功");
        } catch (Exception e) {
            log.error("记录消费失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("记录消费失败！");
        }
    }

    @RequestMapping(value = "/v1/update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody CostVO costVO) {
        try {
            Cost c = VoToModel.toCost(costVO);
            costService.update(c);
            return HttpResult.ok("修改成功");
        } catch (Exception e) {
            log.error("修改消费失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("修改消费失败！");
        }
    }

    /**
     * @author Administrator
     * @description 分页查询收益交易
     * @date 2019/3/22
     */
    @RequestMapping(value = "/v1/page", method = RequestMethod.POST)
    public HttpResult pageCost(String fromTime, String toTime, Integer page, Integer pageSize) {
        try {
            if (page == null || page <= 0) {
                page = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            List<CostVO> ret = costService.searchCosts(fromTime, toTime, page, pageSize);
            int total = costService.searchCostsTotal(fromTime, toTime);
            ModelMap map = new ModelMap();
            map.put("list", ret);
            map.put("total", total);
            return HttpResult.ok("查询成功!", map);
        } catch (Exception e) {
            log.error("获取收益列表失败!", e);
            return HttpResult.error("获取收益列表失败!");
        }
    }
}
