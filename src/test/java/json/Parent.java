package json;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Json 转换用的父类实体
 */
public class Parent {
    private Integer pid;
    private String parentName;

    private List<Child> children = new ArrayList<>();

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "pid=" + pid +
                ", parentName='" + parentName + '\'' +
                ", children=" + children +
                '}';
    }

    /**
     * add
     */
    public void addChild(Child child) {
        this.children.add(child);
    }
}
