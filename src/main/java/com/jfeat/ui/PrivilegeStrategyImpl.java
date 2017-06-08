package com.jfeat.ui;

import com.jfinal.ext.plugin.shiro.ShiroMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ehngjen on 1/18/2016.
 */
public class PrivilegeStrategyImpl implements IPrivilegeStrategy, Serializable {
    private static Logger logger = LoggerFactory.getLogger(PrivilegeStrategyImpl.class);

    /**
     * 对于只有根菜单的，使用permissionList 保存权限。
     */
    private List<String> permissionList = new LinkedList<>();

    /**
     * 对于有子菜单的，使用用permissionMap针对每个子菜单分别保存权限。
     */
    private Map<String, List<String>> permissionMap = new HashMap<>();

    /**
     *
     * 两种使用方式：
     *   1. 根菜单下有子菜单的
     *   2. 只有根菜单的
     *
     * @param permissions menu.key1:permission1|permission2|permission3, menu.key2:permission2|permission3
     *                    or permission1, permission2
     */
    public PrivilegeStrategyImpl(String... permissions) {
        for (String permission : permissions) {
            String[] values = permission.split(":");
            if (values.length == 2) {
                List<String> permissionList = new LinkedList<>();
                for (String p : values[1].split("\\|")) {
                    permissionList.add(p);
                }
                permissionMap.put(values[0], permissionList);
            }
            else {
                permissionList.add(permission);
            }
        }
    }

    @Override
    public boolean isAllowed(String key) {
        logger.debug("checking privilege for key=" + key);

        if (permissionMap.containsKey(key)) {
            for (String permission : permissionMap.get(key)) {
                if (ShiroMethod.hasPermission(permission)) {
                    logger.debug("Allow to access " + key);
                    return true;
                }
            }
        }
        else {
            for (String permission : permissionList) {
                if (ShiroMethod.hasPermission(permission)) {
                    logger.debug("Allow to access " + key);
                    return true;
                }
            }
        }

        logger.debug("NOT ALLOWED. access " + key + " but lack of permissions.");
        return false;
    }
}
