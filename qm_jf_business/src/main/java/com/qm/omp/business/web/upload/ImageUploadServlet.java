package com.qm.omp.business.web.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.omp.api.web.BaseWebProperties;
import com.qm.omp.business.pojo.img.ImgInfoBean;
import com.qm.omp.business.service.iface.img.ImgInfoService;

public class ImageUploadServlet extends HttpServlet
{
    private Logger logger = LoggerFactory.getLogger(ImageUploadServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String oper = request.getParameter("oper");
        Uploader up = new Uploader(request);
        if ("DELETE".equals(oper))
        {
            String fileName = request.getParameter("fileName");
            up.delete(fileName);
        }
        else
        {
            response.setContentType("text/html");
//            MerchantUserInfoBean userInfo = (MerchantUserInfoBean) SessionUtil.getObjectAttribute(request, RequestConstants.ADMIN_SESSION_KEY);
//            up.setSavePath(BaseWebProperties.BUSINESS_FILE_UPDIR);
            up.setSavePath("upload\\img");
            String[] fileType = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
            up.setAllowFiles(fileType);
            up.setMaxSize(3); // 单位MB
//            up.setBusinessCode(userInfo.getfUserMerRel());

            try
            {
                up.upload();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                Map<String, String> retMap = new HashMap<String, String>();
                if (up.getState().equals("SUCCESS"))
                {
                    // 保存图片到数据库
                    // int imgId = saveImgToDB(request, up.getFileName(),
                    // up.getUrl(), up.getType());
                    // retMap.put("imgId", String.valueOf(imgId));
                    saveImg2DB(request, up.getFileName(), up.getUrl().replace("\\", "/"), up.getType());
                }
                retMap.put("state", up.getState());
                retMap.put("url", up.getUrl());
                retMap.put("width", String.valueOf(up.getWidth()));
                retMap.put("height", String.valueOf(up.getHeight()));
                PrintWriter pw = response.getWriter();
                pw.print(JSON.toJSONString(retMap));
                pw.close();
            }
        }
    }

    /**
     * @Title: saveImgToDB
     * @Description: 保存图片到数据库
     * @param request
     * @param fileName
     * @param categoryId
     * @param url
     * @return void 返回类型
     * @throws
     */
    private int saveImg2DB(HttpServletRequest request, String fileName, String url, String imgExtName)
    {
        int imgId = 0;
        try
        {
            int categoryId = RequestUtil.getIntParamterAsDef(request, "categoryId", 0);
            ImgInfoService imgInfoService = (ImgInfoService) BaseWebProperties.SPRING_CONTEXT.getBean("imgInfoService");
//            MerchantUserInfoBean userInfo = (MerchantUserInfoBean) SessionUtil.getObjectAttribute(request, RequestConstants.ADMIN_SESSION_KEY);
            ImgInfoBean bean = new ImgInfoBean();
//            bean.setMerchantId(userInfo.getfUserMerRel());
//            bean.setBelongType("");
            bean.setImgName(fileName);//图片名
            bean.setImgExtName(imgExtName);//图片后缀名
            bean.setCategoryId(categoryId);//图片所属目录
            bean.setImgUrl(url);//图片web服务下相对地址
//            bean.setUpdateUser(userInfo.getfUserCode());
            bean.setUpdateTime(DateTimeUtil.getTodayChar17());
            imgId = imgInfoService.addImg(bean);
        }
        catch (Exception ex)
        {
            imgId = -1;
            logger.error("发生异常:", ex);
        }

        return imgId;
    }
}
