package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.CompanyService;
import com.sunlight.portal.accounts.service.UserService;
import com.sunlight.portal.accounts.vo.CompanyVO;
import com.sunlight.portal.accounts.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @Resource
    private UserService userService;

    @PostMapping("/getCompanyByUser")
    public HttpResult getCompanyByUser(Integer userId){
        try {
            UserVO uu = userService.get(userId);
            if (uu != null) {
                CompanyVO com = companyService.get(uu.getCompanyId());
                if (com != null) {
                    return HttpResult.ok("", com);
                }
            }
            return HttpResult.error("查询公司成功!");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("查询失败!");
    }

    @GetMapping("/getById")
    public HttpResult getById(Integer companyId){
        try {
            CompanyVO com = companyService.get(companyId);
            if (com != null) {
                return HttpResult.ok("", com);
            }
            return HttpResult.error("查询公司成功!");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("查询失败!");
    }

    @PostMapping("/add")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult add(@RequestBody CompanyVO companyVO){
        try {
            companyService.addCompany(companyVO);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("添加失败!");
    }

    @PostMapping("/update")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult update(@RequestBody CompanyVO companyVO){
        try {
            companyService.update(companyVO);
            return HttpResult.ok("操作成功!");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("添加失败!");
    }

    @DeleteMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer companyId){
        try {
            companyService.deleteCompany(companyId);
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @DeleteMapping("/batchDelete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult batchDelete(String ids){
        try {
            List<String> idList = StringUtils.toList(ids);
            for(String id: idList) {
                companyService.deleteCompany(Integer.parseInt(id));
            }
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @GetMapping("/getCompanyList")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult getCompanyList(Integer page, Integer pageSize, String companyName){
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 20;
            }
            List<CompanyVO> rets = companyService.searchCompanys(page, pageSize, companyName);
            return HttpResult.ok("获取成功!", rets);
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }
}
