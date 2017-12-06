package com.qm.omp.business.pojo.admin;

import java.io.Serializable;

import com.qm.omp.api.pojo.OrderInfoBean;

/**
 * @ClassName: MenuInfoBean
 * @Description: 模块信息
 * @author rh
 * @date 2014-12-1 18:53:21
 */
public class MenuInfoBean extends OrderInfoBean implements Serializable
{
    private static final long serialVersionUID = 717464608572605704L;


    /**
     * 模块ID
     */
    private String funcId;

    /**
     * 模块父节点ID
     */
    private String funcParentId;

    /**
     * 模块名称
     */
    private String funcName;

    /**
     * 模块类型
     */
    private Integer funcType;

    /**
     * URI
     */
    private String funcURI;

    /**
     * 菜单排序
     */
    private Integer funcOrder;

    /**
     * 一级模块加载的样式
     */
    private String funcClass;

    /**
     * 是否选中
     */
    private boolean checked;
    
    
	public boolean isChecked() {
		return checked;
	}


	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getFuncId() {
		return funcId;
	}



	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}



	public String getFuncParentId() {
		return funcParentId;
	}



	public void setFuncParentId(String funcParentId) {
		this.funcParentId = funcParentId;
	}



	public String getFuncName() {
		return funcName;
	}



	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}



	public Integer getFuncType() {
		return funcType;
	}



	public void setFuncType(Integer funcType) {
		this.funcType = funcType;
	}



	public String getFuncURI() {
		return funcURI;
	}



	public void setFuncURI(String funcURI) {
		this.funcURI = funcURI;
	}



	public Integer getFuncOrder() {
		return funcOrder;
	}



	public void setFuncOrder(Integer funcOrder) {
		this.funcOrder = funcOrder;
	}




	public String getFuncClass() {
		return funcClass;
	}



	public void setFuncClass(String funcClass) {
		this.funcClass = funcClass;
	}



	/**
     * 重写toString方法，用于权限判断比较
     */
    @Override
    public String toString()
    {
        return this.funcURI.toString();
    }
}
