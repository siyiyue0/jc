/*
 *   Copyright (C) 2014-2016 www.kequandian.net
 *
 *    The program may be used and/or copied only with the written permission
 *    from www.kequandian.net or in accordance with the terms and
 *    conditions stipulated in the agreement/contract under which the program
 *    has been supplied.
 *
 *    All rights reserved.
 *
 */

/*
 * This file is automatically generated by tools.
 * It defines fields related to the database table.
 *
 * DON'T EDIT IT. OTHERWIDE IT WILL BE OVERRIDE WHEN RE-GENERATING IF TABLE CHANGE.
 */
package com.jfeat.ui.model.base;

import com.jfeat.core.BaseModel;
import com.jfinal.plugin.activerecord.IBean;

public abstract class NotificationBase<M extends NotificationBase<?>> extends BaseModel<M> implements IBean {

    /**
     * Table fields 
     */
    public enum Fields {
        ID("id"),
        IDENTIFIER("identifier"),
        NAME("name"),
        TITLE("title"),
        ICON("icon"),
        DISPLAY_MODE("display_mode"),
        BADGE_URL("badge_url"),
        URL("url"),
        TIMEOUT("timeout");
        
        private String name;
        Fields(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
        public String like(Object obj) {
            return new StringBuilder(this.toString()).append(" LIKE ").append(obj).toString();
        }
        public String eq(Object obj) {
            return new StringBuilder(this.toString()).append("=").append(obj).toString();
        }
        public String ge(Object obj) {
            return new StringBuilder(this.toString()).append(">=").append(obj).toString();
        }
        public String lt(Object obj) {
            return new StringBuilder(this.toString()).append("<").append(obj).toString();
        }
        public String le(Object obj) {
            return new StringBuilder(this.toString()).append("<=").append(obj).toString();
        }
        public String isNull() {
            return new StringBuilder(this.toString()).append(" IS NULL").toString();
        }
        public String notNull() {
            return new StringBuilder(this.toString()).append(" NOT NULL").toString();
        }
        public String notEquals(Object obj) {
            return new StringBuilder(this.toString()).append("<>").append(obj).toString();
        }
    }

    public void setId(Integer var) {
        set(Fields.ID.toString(), var);
    }

    public Integer getId() {
        return (Integer) get(Fields.ID.toString());
    }

    public void setIdentifier(String var) {
        set(Fields.IDENTIFIER.toString(), var);
    }

    public String getIdentifier() {
        return (String) get(Fields.IDENTIFIER.toString());
    }

    public void setName(String var) {
        set(Fields.NAME.toString(), var);
    }

    public String getName() {
        return (String) get(Fields.NAME.toString());
    }

    public void setTitle(String var) {
        set(Fields.TITLE.toString(), var);
    }

    public String getTitle() {
        return (String) get(Fields.TITLE.toString());
    }

    public void setIcon(String var) {
        set(Fields.ICON.toString(), var);
    }

    public String getIcon() {
        return (String) get(Fields.ICON.toString());
    }

    public void setDisplayMode(Integer var) {
        set(Fields.DISPLAY_MODE.toString(), var);
    }

    public Integer getDisplayMode() {
        return (Integer) get(Fields.DISPLAY_MODE.toString());
    }

    public void setBadgeUrl(String var) {
        set(Fields.BADGE_URL.toString(), var);
    }

    public String getBadgeUrl() {
        return (String) get(Fields.BADGE_URL.toString());
    }

    public void setUrl(String var) {
        set(Fields.URL.toString(), var);
    }

    public String getUrl() {
        return (String) get(Fields.URL.toString());
    }

    public void setTimeout(Integer var) {
        set(Fields.TIMEOUT.toString(), var);
    }

    public Integer getTimeout() {
        return (Integer) get(Fields.TIMEOUT.toString());
    }

    ///////////////////////////////////////////////////////////////////////////
    
}
