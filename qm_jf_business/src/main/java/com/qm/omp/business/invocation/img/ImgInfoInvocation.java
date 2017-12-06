package com.qm.omp.business.invocation.img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.constants.SystemCodeConstants;
import com.qm.omp.business.pojo.img.ImgCategoryBean;
import com.qm.omp.business.service.iface.img.ImgInfoService;

/**
 * @ClassName: ImgInfoInvocation
 * @Description: 图片信息处理类
 * @author jhr
 * @date 2013-11-6 下午4:33:06
 */
@Component("INVO_imgInfo")
public class ImgInfoInvocation implements BaseInvocation
{
    private Logger         logger = LoggerFactory.getLogger(ImgInfoInvocation.class);

    @Autowired
    private ImgInfoService imgInfoService;

    /**
     * @Title: queryImgCategory
     * @Description: 查询图片分类
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult queryImgCategory(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        try
        {
//            MerchantUserInfoBean userInfo = (MerchantUserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(),
//                    RequestConstants.ADMIN_SESSION_KEY);
            Map<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("merchantId", userInfo.getfUserMerRel());
            List<ImgCategoryBean> categoryLst = imgInfoService.queryImgCategoryLst(paramMap);
            result.setRetObj(categoryLst);
        }
        catch (Exception ex)
        {
            logger.error("发生异常:", ex);
            result.setSysCode(SystemCodeConstants.OPERATE_FAILED);
        }

        return result;
    }

    /**
     * @Title: addImgCategory
     * @Description: 增加图片分类
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult addImgCategory(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        try
        {
            String categoryName = RequestUtil.getStrParamterAsDef(context.getRequest(), "categoryName", "");
//            MerchantUserInfoBean userInfo = (MerchantUserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(),
//                    RequestConstants.ADMIN_SESSION_KEY);
//            if (!imgInfoService.isCategoryNameExist(userInfo.getfUserMerRel(), categoryName))
//            {

                ImgCategoryBean bean = new ImgCategoryBean();
//                bean.setMerchantId(userInfo.getfUserMerRel());
                bean.setBelongType("2");
                bean.setCategoryName(categoryName);
//                bean.setUpdateUser(userInfo.getfUserCode());
                bean.setUpdateTime(DateTimeUtil.getTodayChar17());
                imgInfoService.addCategory(bean);
//            }
//            else
//            {
//                result.setLogicCode(LogicCodeConstants.IMG_CATEGORY_ADD_ISEXIST);
//            }
        }
        catch (Exception ex)
        {
            logger.error("发生异常:", ex);
//            result.setLogicCode(LogicCodeConstants.IMG_CATEGORY_ADD_FAILED);
        }

        return result;
    }

    /**
     * @Title: queryImgCategory
     * @Description: 查询图片分类
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult queryImgLst(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        try
        {
            String categoryId = RequestUtil.getStrParamterAsDef(context.getRequest(), "categoryId", "");
            int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
            int pageSize = RequestUtil.getIntParamterAsDef(context.getRequest(), "pageSize", 50);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (!StringUtils.isBlank(categoryId))
            {
                paramMap.put("categoryId", categoryId);
            }
//            paramMap.put("orderBy", " MODI_TIME DESC ");
//            MerchantUserInfoBean userInfo = (MerchantUserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(),
//                    RequestConstants.ADMIN_SESSION_KEY);
//            paramMap.put("merchantId", userInfo.getfUserMerRel());
            paramMap.put("pageStart", (page - 1) * pageSize);
            paramMap.put("pageSize", pageSize);
            Map<String, Object> retMap = imgInfoService.queryImgLst(paramMap);
            result.setRetObj(retMap);
        }
        catch (Exception ex)
        {
            logger.error("发生异常:", ex);
            result.setSysCode(SystemCodeConstants.OPERATE_FAILED);
        }

        return result;
    }

    /**
     * @Title: cropImg
     * @Description:
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult cropImg(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        InputStream in = null;
        FileOutputStream fos = null;
        try
        {
            String imgUrl = RequestUtil.getStrParamterAsDef(context.getRequest(), "imgUrl", "");
            int selX = RequestUtil.getIntParamterAsDef(context.getRequest(), "selX", 0);
            int selY = RequestUtil.getIntParamterAsDef(context.getRequest(), "selY", 0);
            int selW = RequestUtil.getIntParamterAsDef(context.getRequest(), "selW", 400);
            int selH = RequestUtil.getIntParamterAsDef(context.getRequest(), "selH", 400);

            String servletPath = context.getRequest().getServletPath();
            String realPath = context.getRequest().getSession().getServletContext().getRealPath(servletPath);
            String absPath = new File(realPath).getParent() + "/" + imgUrl;

            byte[] imageBytes = null;
            File file = new File(absPath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            in = new FileInputStream(file);
            Streams.copy(in, baos, true);
            imageBytes = baos.toByteArray();
            BufferedImage img = javax.imageio.ImageIO.read(new ByteArrayInputStream(imageBytes));
            img = img.getSubimage(selX, selY, selW, selH);
            fos = new FileOutputStream(absPath);
            javax.imageio.ImageIO.write(img, "jpg", fos);
        }
        catch (Exception ex)
        {
            logger.error("发生异常:", ex);
            result.setSysCode(SystemCodeConstants.OPERATE_FAILED);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
