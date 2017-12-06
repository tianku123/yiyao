package com.qm.omp.business.service.impl.img;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qm.omp.business.dao.img.ImgCategoryMapper;
import com.qm.omp.business.dao.img.ImgInfoMapper;
import com.qm.omp.business.pojo.img.ImgCategoryBean;
import com.qm.omp.business.pojo.img.ImgInfoBean;
import com.qm.omp.business.service.iface.img.ImgInfoService;

/**
 * @ClassName: ImgInfoServiceImpl
 * @Description: 图片信息
 * @author jhr
 * @date 2013-11-6 下午4:34:34
 */
@Transactional
@Service("imgInfoService")
public class ImgInfoServiceImpl implements ImgInfoService
{
    @Autowired
    private ImgCategoryMapper imgCategoryMapper;

    @Autowired
    private ImgInfoMapper     imgInfoMapper;

    /*
     * <p>Title: queryImgCategoryLst</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see
     * com.itiyan.business.service.img.iface.IImgInfoService#queryImgCategoryLst
     * ()
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public List<ImgCategoryBean> queryImgCategoryLst(Map<String, Object> paramMap)
    {
        return imgCategoryMapper.selectList(paramMap);
    }

    /*
     * <p>Title: queryImgLst</p> <p>Description: </p>
     * 
     * @param paramMap
     * 
     * @return
     * 
     * @see
     * com.itiyan.business.service.img.iface.IImgInfoService#queryImgLst(java
     * .util.Map)
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public Map<String, Object> queryImgLst(Map<String, Object> paramMap)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();
        int count = imgInfoMapper.selectCount(paramMap);
        retMap.put("count", count);
        if (count > 0)
        {
            List<ImgInfoBean> imgLst = imgInfoMapper.selectList(paramMap);
            retMap.put("imgLst", imgLst);
        }
        else
        {
            retMap.put("imgLst", null);
        }

        return retMap;
    }

    /*
     * <p>Title: addCategory</p> <p>Description: </p>
     * 
     * @param bean
     * 
     * @see
     * com.itiyan.business.service.img.iface.IImgInfoService#addCategory(com
     * .itiyan.business.pojo.img.ImgCategoryBean)
     */
    @Override
    public void addCategory(ImgCategoryBean bean)
    {
        imgCategoryMapper.insertSelective(bean);
    }

    @Override
    public int addImg(ImgInfoBean bean)
    {
        return imgInfoMapper.insertSelective(bean);
    }

    /*
     * <p>Title: isCategoryNameExist</p> <p>Description: </p>
     * 
     * @param categoryName
     * 
     * @return
     * 
     * @see
     * com.itiyan.business.service.img.iface.IImgInfoService#isCategoryNameExist
     * (java.lang.String)
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public boolean isCategoryNameExist(String merchantId, String categoryName)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("categoryName", categoryName);
        paramMap.put("merchantId", merchantId);
        int count = imgCategoryMapper.selectCount(paramMap);
        return (count > 0);
    }

}
