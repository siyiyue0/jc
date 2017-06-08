/*
 * Copyright (C) 2014-2015 by ehngjen @ www.jfeat.com
 *
 *  The program may be used and/or copied only with the written permission
 *  from JFeat.com, or in accordance with the terms and
 *  conditions stipulated in the agreement/contract under which the program
 *  has been supplied.
 *
 *  All rights reserved.
 */

package com.jfeat.ui;

import com.jfeat.ui.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jacky on 12/21/14.
 */
public class MenuHolder implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(MenuHolder.class);
    private List<Menu> menus;
    private Map<String, IPrivilegeStrategy> privilegeStrategyMap;

    public MenuHolder() {
        privilegeStrategyMap = new LinkedHashMap<>();
    }

    public void setSelectedMenu(String controllerKey) {
        setSelection(menus, controllerKey);
    }

    /**
     * mark menu selected if it is selected or its submenu is selected.
     * @param menuList
     * @param controllerKey
     * @return
     */
    private boolean setSelection(List<Menu> menuList, String controllerKey) {
        boolean result = false;
        for (Menu menu : menuList) {
            menu.setSelected(false);
            String controllerKeySub = controllerKey.substring(1, controllerKey.length());
            if (menu.getUrl() != null) {
                if ((menu.getUrl().equals("") && controllerKeySub.equals("")) 
                    || (!menu.getUrl().equals("") && controllerKeySub.equals(menu.getUrl()))) {
                    menu.setSelected(true);
                    result = true;
                }
            }
            else {
                boolean res = setSelection(menu.getSubMenu(), controllerKey);
                menu.setSelected(res);
            }
        }
        return result;
    }

    public void fetchMenus() {
        if (menus == null) {
            List<Menu> menuList = Menu.dao.findByParentId(null);
            for (Menu menu : menuList) {
                menu.setAllowed(true);
                IPrivilegeStrategy privilegeStrategy = privilegeStrategyMap.get(menu.getName());
                if (privilegeStrategy != null) {
                    if (!privilegeStrategy.isAllowed(menu.getName())) {
                        menu.setAllowed(false);
                    }
                }
                retrieveSubMenu(privilegeStrategy, menu);
            }
            menus = menuList;
            if (logger.isDebugEnabled())
                displayMenu(0, menus);
        }
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Map<String, IPrivilegeStrategy> getPrivilegeStrategyMap() {
        return privilegeStrategyMap;
    }

    public void setPrivilegeStrategyMap(Map<String, IPrivilegeStrategy> privilegeStrategyMap) {
        this.privilegeStrategyMap = privilegeStrategyMap;
    }

    private void retrieveSubMenu(IPrivilegeStrategy privilegeStrategy, Menu menu) {
        List<Menu> menuList = Menu.dao.findByParentId(menu.getId());
        menu.setSubMenu(menuList);
        for (Menu subMenu : menuList) {
            logger.debug("menu=" + subMenu.getName());
            subMenu.setAllowed(true);
            if (privilegeStrategy != null) {
                if (!privilegeStrategy.isAllowed(subMenu.getName())) {
                    subMenu.setAllowed(false);
                }
            }
            // if subMenu is allowed, then its parent menu should be visible.
            if (subMenu.isAllowed() == true) {
                menu.setAllowed(true);
            }
            retrieveSubMenu(privilegeStrategy, subMenu);
        }
    }

    private void displayMenu(int indent, List<Menu> menuList) {
        for (Menu menu : menuList) {
            int tempIndent = indent;
            StringBuilder builder = new StringBuilder();
            while (tempIndent-- > 0) {
                builder.append("    ");
            }
            builder.append("menu=");
            builder.append(menu.getName());
            builder.append(", allowed=");
            builder.append(menu.isAllowed());
            logger.debug(builder.toString());
            displayMenu(indent + 1, menu.getSubMenu());
        }
    }
}
