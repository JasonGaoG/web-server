package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.portal.accounts.dao.MenuMapper;
import com.sunlight.portal.accounts.model.Menu;
import com.sunlight.portal.accounts.vo.MenuVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置服务
 */
@Service("menuService")
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    public void add(MenuVO menuVO) {
        Menu menu = convert(menuVO);
        if (StringUtils.isBlank(menu.getParentCode())) {
            menu.setParentCode("");
            menu.setTop(0);
        } else {
            menu.setTop(1);
        }
        menu.setMenuCode(getMenuCode(menu.getParentCode()));
        menuMapper.insert(menu);
    }

    /**
     * 根据parent Code 生成menuCode
     * 顶级菜单 10 - 99
     * 每往下一级增加两位数
     */
    private String getMenuCode(String parentCode) {
        Menu menu = new Menu();
        menu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Menu> ret = menuMapper.selectMany(menu);
        int length = parentCode.length() + 2;
        int current = 0;
        for (Menu m : ret) {
            if (m.getMenuCode().length() == length) {
                if (current < Integer.parseInt(m.getMenuCode())) {
                    current = Integer.parseInt(m.getMenuCode());
                }
            }
        }
        String code = (current + 1) + "";
        System.out.println("get menu code: " + parentCode + "; ret:" + code);
        return code;
    }

    public void update(MenuVO menuVO) {
        Menu menu = convert(menuVO);
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    public void delete(Integer menuId) {
        // TODO 子菜单删除逻辑
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setDelstatus(DelStatusEnum.Delete.getValue());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    private Menu convert(MenuVO menuVO) {
        Menu m = new Menu();
        m.setId(menuVO.getId());
        m.setMenuCode(menuVO.getMenuCode());
        m.setDelstatus(DelStatusEnum.UnDelete.getValue());
        m.setTop(menuVO.getTop());
        m.setMenuUrl(menuVO.getMenuUrl());
        m.setRemarks(menuVO.getRemarks());
        m.setParentCode(menuVO.getParentCode());
        return m;
    }

    public List<MenuVO> getMenuList(Integer page, Integer pageSize) {
        List<MenuVO> ret = new ArrayList<>();
        Menu menu = new Menu();
        menu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        menu.setPageSize(pageSize);
        menu.setStart((page-1) * pageSize);
        List<Menu> rets = menuMapper.selectMany(menu);
        for(Menu m: rets) {
            ret.add(new MenuVO(m));
        }
        return ret;
    }

    public List<MenuVO> getMenuTree(String menuCode) {
        List<MenuVO> ret = new ArrayList<>();
        Menu menu = new Menu();
        menu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        menu.setParentCode(menuCode);

        List<Menu> rets = menuMapper.selectMany(menu);
        for(Menu m: rets) {
            MenuVO vo = new MenuVO(m);
            vo.setChildren(getMenuTree(m.getMenuCode()));
            ret.add(vo);
        }
        return ret;
    }
}
