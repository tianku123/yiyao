package com.qm.omp.api.util;

import java.util.ArrayList;
import java.util.List;

import com.qm.omp.api.pojo.OrderInfoBean;
import com.qm.omp.api.pojo.TreeNode;

/**
 * @ClassName: TreeUtils
 * @Description: Tree工具类
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
@SuppressWarnings("unchecked")
public class TreeUtils
{
    /**
     * @Title: obj2tree
     * @Description: 转换成easyUI需要的数据格式
     * @param funInfoLst
     * @return
     * @return List<TreeNode> 返回类型
     * @throws
     */
    public static List<TreeNode> obj2tree(List<?> objLst)
    {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        for(int i = 0; i < objLst.size(); i ++)
        {
            OrderInfoBean order = (OrderInfoBean)objLst.get(i);
            if(order.getJb() == 1)
            {
                TreeNode node = order.toTreeNode();
                if(order.mj != 1)
                {
                    findChildren(node, order, objLst);
                }
                nodeList.add(node);
            }
        }
        return nodeList;
    }
    
    /**
     * @Title: findChildren
     * @Description: 
     * @param parent
     * @param objLst
     * @return void 返回类型
     * @throws
     */
    public static void findChildren(TreeNode parentNode, OrderInfoBean parentOrder, List<?> objLst)
    {
        for(int i = 0; i < objLst.size(); i ++)
        {
            OrderInfoBean order = (OrderInfoBean)objLst.get(i);
            String parentId = order.getJbNum().substring(0, 2 * (order.getJb() - 1));
            if(parentOrder.getJbNum().equals(parentId))
            {
                TreeNode node = order.toTreeNode();
                node.setParentNode(parentNode);
                if(!node.isChecked())
                {
                    parentNode.unChecked();
                }
                parentNode.getChildren().add(node);
                if(order.getMj() != 1)
                {
                    findChildren(node, order, objLst);
                }
            }
        }
    }  
}
