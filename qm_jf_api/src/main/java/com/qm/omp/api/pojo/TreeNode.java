package com.qm.omp.api.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: TreeNode
 * @Description:
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class TreeNode
{
    private String              text;
    private Map<String, String> attributes;
    private boolean             checked;
    private List<TreeNode>      children;
    private TreeNode            parentNode;

    public TreeNode getParentNode()
    {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode)
    {
        this.parentNode = parentNode;
    }

    public boolean isChecked()
    {
        return checked;
    }
    
    public void unChecked()
    {
        this.checked = false;
        if(this.getParentNode() != null)
        {
            this.getParentNode().unChecked();
        }
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Map getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeNode> children)
    {
        this.children = children;
    }

    public static void main(String[] args)
    {
        TreeNode root = new TreeNode();
        root.setText("root");
        Map attrMap = new HashMap();
        attrMap.put("a", "aaaa");
        attrMap.put("b", "bbbb");
        attrMap.put("c", "cccc");
        root.setAttributes(attrMap);
        TreeNode leaf1 = new TreeNode();
        leaf1.setText("leaf1");

        List children = new ArrayList();
        children.add(leaf1);
        root.setChildren(children);

        System.out.println(JSON.toJSONString(root));
    }
}
