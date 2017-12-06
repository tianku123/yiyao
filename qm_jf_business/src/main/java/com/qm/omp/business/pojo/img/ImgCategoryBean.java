package com.qm.omp.business.pojo.img;

/**
 * @ClassName: ImgCategoryBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jihr
 * @date 2014-3-8 上午11:45:25
 */
public class ImgCategoryBean
{
    private Integer categoryId;  // 分类编码
    private String  categoryName; // 分类名称
    private String  merchantId;  // 商家编码
    private String  belongType;  // 归属类型
    private String  updateUser;  // 修改人
    private String  updateTime;  // 修改时间

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getBelongType()
    {
        return belongType;
    }

    public void setBelongType(String belongType)
    {
        this.belongType = belongType;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

}
