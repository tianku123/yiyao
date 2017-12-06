package com.qm.omp.business.service.iface.img;

import java.util.List;
import java.util.Map;

import com.qm.omp.business.pojo.img.ImgCategoryBean;
import com.qm.omp.business.pojo.img.ImgInfoBean;

/**
 * @ClassName: IImgInfoService
 * @Description: 图片信息
 * @author jhr
 * @date 2013-11-6 下午4:33:56
 */
public interface ImgInfoService
{
    /**
     * @Title: queryImgCategoryLst
     * @Description: 查询图片分类列表
     * @param paramMap
     * @return
     * @return List<ImgCategoryBean> 返回类型
     * @throws
     */
    public List<ImgCategoryBean> queryImgCategoryLst(Map<String, Object> paramMap);

    /**
     * @Title: queryImgLst
     * @Description: 查询图片列表
     * @return
     * @return Map<ImgInfoBean> 返回类型
     * @throws
     */
    public Map<String, Object> queryImgLst(Map<String, Object> paramMap);

    /**
     * @Title: addCategory
     * @Description: 添加分类
     * @param bean
     * @return void 返回类型
     * @throws
     */
    public void addCategory(ImgCategoryBean bean);

    /**
     * @Title: addImg
     * @Description: 添加图片
     * @param bean
     * @return void 返回类型
     * @throws
     */
    public int addImg(ImgInfoBean bean);

    /**
     * @Title: isCategoryNameExist
     * @Description: 检查分类是否已经存在
     * @param merchantId
     * @param categoryName
     * @return
     * @return boolean 返回类型
     * @throws
     */
    public boolean isCategoryNameExist(String merchantId, String categoryName);
}
