package com.qm.omp.business.dao.img;

import java.util.List;
import java.util.Map;

import com.qm.omp.business.pojo.img.ImgInfoBean;

/**
 * @ClassName: IImgInfoMapper
 * @Description: 图片信息
 * @author jhr
 * @date 2013-11-6 下午4:32:24
 */
public interface ImgInfoMapper
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
     * @Description: 查询图片列表
     * @param paramMap
     * @return
     * @return List<ImgInfoBean> 返回类型
     * @throws
     */
    public List<ImgInfoBean> selectList(Map<String, Object> paramMap);
    
    /**
     * @Title: insertSelective
     * @Description: 新增图片
     * @param bean
     * @return void 返回类型
     * @throws
     */
    public int insertSelective(ImgInfoBean bean);
}
