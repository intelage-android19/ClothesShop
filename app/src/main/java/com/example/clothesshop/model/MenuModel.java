package com.example.clothesshop.model;

public class MenuModel {
    String menuName;
    boolean isGroup,isChildren,isSubChild;
    int menuIcon=0;

    public MenuModel(String menuName,int menuIcon, boolean isGroup, boolean isChildren,boolean isSubChild) {
        this.menuName = menuName;
        this.isGroup = isGroup;
        this.isChildren = isChildren;
        this.menuIcon=menuIcon;
        this.isSubChild=isSubChild;
    }




    public boolean isSubChild() {
        return isSubChild;
    }

    public void setSubChild(boolean subChild) {
        isSubChild = subChild;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isChildren() {
        return isChildren;
    }

    public void setChildren(boolean children) {
        isChildren = children;
    }
}
