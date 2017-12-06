package com.qm.omp.business.dao.img;

import java.util.List;
import java.util.Map;

import com.qm.omp.business.pojo.img.ImgCategoryBean;

/**
 * @ClassName: IImgCategoryMapper
 * @Description: 图片目录
 * @author jhr
 * @date 2013-11-6 下午4:28:01
 */
public interface ImgCategoryMapper
{
    /**
     * @Title: selectCount
     * @Description: 查询记录数
     * @param paramMap
     * @return
     * @return int 返回类型
     * @throws
     */
    public int selectCount(Map<String, Object> paramMap);
    
    /**
     * @Title: selectList
     * @Description: 查询分类列表
     * @param paramMap
     * @return
     * @return List<ImgCategoryBean> 返回类型
     * @throws
     */
    public List<ImgCategoryBean> selectList(Map<String, Object> paramMap);
    
    public void insertSelective(ImgCategoryBean bean);
}
 