package com.ldz.sys.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * auther chenwei
 * create at 2018/5/5
 */
@Getter
@Setter
public class TreeNode {
    private String label;
    private String value;
    private String father;
    private List<TreeNode> children;
}
