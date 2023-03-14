package com.sunlight.portal.accounts.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.portal.accounts.dao.MenuMapper;
import com.sunlight.portal.accounts.model.Menu;
import com.sunlight.portal.accounts.vo.MenuVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单配置服务
 */
@Service("menuService")
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    public void add(MenuVO menuVO) {
        Menu menu = convert(menuVO);
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
        String code = "";
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
        if (current == 0) {
            code =  parentCode + "10";
        } else {
            code = (current + 1) + "";
        }
        System.out.println("get menu code: " + parentCode + "; ret:" + code);
        return code;
    }

    public void update(MenuVO menuVO) {
        Menu menu = convert(menuVO);
        Menu old = menuMapper.selectByPrimaryKey(menuVO.getId());
        if (!old.getParentCode().equals(menuVO.getParentCode())) {
            menu.setMenuCode(getMenuCode(menu.getParentCode()));
        }
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
        m.setMenuName(menuVO.getMenuName());
        m.setDelstatus(DelStatusEnum.UnDelete.getValue());
        m.setIcon(menuVO.getIcon());
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

    public Integer count() {
        Menu menu = new Menu();
        menu.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return menuMapper.count(menu);
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
