package com.qm.omp.business.pojo.img;

/**
 * @ClassName: ImgInfoBean
 * @Description:
 * @author jihr
 * @date 2014-3-8 上午11:51:42
 */
public class ImgInfoBean
{
    private Integer imgId;     // 图片编码
    private String  imgName;   // 图片名称
    private String  imgUrl;    // 图片路径
    private String  imgExtName; // 文件扩展名
    private Integer categoryId; // 分类
    private String  merchantId; // 商家编码
    private String  belongType; // 归属类型
    private String  updateUser; // 修改人
    private String  updateTime; // 修改时间

    public Integer getImgId()
    {
        return imgId;
    }

    public void setImgId(Integer imgId)
    {
        this.imgId = imgId;
    }

    public String getImgName()
    {
        return imgName;
    }

    public void setImgName(String imgName)
    {
        this.imgName = imgName;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getImgExtName()
    {
        return imgExtName;
    }

    public void setImgExtName(String imgExtName)
    {
        this.imgExtName = imgExtName;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
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
