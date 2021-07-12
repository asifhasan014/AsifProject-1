package com.softron.common.transferobjects;

import java.util.ArrayList;
import java.util.List;

public class TreeTO {

    private Long id;

    private Long parentId;

    private String name;

    private List<TreeTO> children = new ArrayList<>();

    public TreeTO(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public TreeTO(Long id, Long parentId, String name, TreeTO child) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children.add(child);
    }

    public TreeTO(Long id, Long parentId, String name, List<TreeTO> children) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeTO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeTO> children) {
        this.children = children;
    }

    public void addChildren(List<TreeTO> children) {
        this.children.addAll(children);
    }

    public void addChild(TreeTO child) {
        this.children.add(child);
    }

}
