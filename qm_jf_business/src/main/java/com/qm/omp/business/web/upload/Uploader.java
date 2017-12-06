package com.qm.omp.business.web.upload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.qm.common.util.SequenceGenerator;
import com.qm.omp.api.web.BaseWebProperties;

/**
 * UEditor文件上传辅助类
 * 
 */
public class Uploader
{
    // 输出文件地址
    private String                  url          = "";
    // 上传文件名
    private String                  fileName     = "";
    // 状态
    private String                  state        = "";
    // 文件类型
    private String                  type         = "";
    // 原始文件名
    private String                  originalName = "";
    // 文件大小
    private long                    size         = 0;
    // 图宽
    private int                     width        = 0;
    // 图高
    private int                     height       = 0;

    private HttpServletRequest      request      = null;

    private String                  title        = "";
    // 图片分类
    private int                     categoryId   = 0;
    // 商客编码
    private String                  businessCode = "";

    // 保存路径
    private String                  savePath     = "upload";
    // 文件允许格式
    private String[]                allowFiles   = {".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg",
            ".bmp"                               };
    // 文件大小限制，单位mb
    private int                     maxSize      = 3;

    private String                  encoding     = "UTF-8";

    private HashMap<String, String> errorInfo    = new HashMap<String, String>();

    public Uploader(HttpServletRequest request)
    {
        this.request = request;
        HashMap<String, String> tmp = this.errorInfo;
        tmp.put("SUCCESS", "SUCCESS"); // 默认成功
        // tmp.put("NOFILE", "未包含文件上传域");
        tmp.put("NOFILE", "上传失败");
        // tmp.put("TYPE", "不允许的文件格式");
        tmp.put("TYPE", "格式错误");
        // tmp.put("SIZE", "文件大小超出限制");
        tmp.put("SIZE", "文件太大");
        // tmp.put("ENTYPE", "请求类型ENTYPE错误");
        tmp.put("ENTYPE", "上传失败");
        // tmp.put("REQUEST", "上传请求异常");
        tmp.put("REQUEST", "上传失败");
        // tmp.put("IO", "IO异常");
        tmp.put("IO", "上传失败");
        // tmp.put("DIR", "目录创建失败");
        tmp.put("DIR", "上传失败");
        // tmp.put("UNKNOWN", "未知错误");
        tmp.put("UNKNOWN", "上传失败");
    }

    public void delete(String fileName)
    {
        String savePath = this.getFolder(this.savePath);
        fileName = savePath + File.separator + fileName;
        File delFile = new File(this.getPhysicalPath(fileName));
        delFile.delete();
    }

    public void upload() throws Exception
    {
        boolean isMultipart = ServletFileUpload.isMultipartContent(this.request);
        if (!isMultipart)
        {
            this.state = this.errorInfo.get("NOFILE");
            return;
        }
        DiskFileItemFactory dff = new DiskFileItemFactory();
        String savePath = this.getFolder(this.savePath);
        dff.setRepository(new File(savePath));
        try
        {
            ServletFileUpload sfu = new ServletFileUpload(dff);
            sfu.setSizeMax(this.maxSize * 1024 * 1024);
            sfu.setHeaderEncoding(this.encoding);
            FileItemIterator fii = sfu.getItemIterator(this.request);
            while (fii.hasNext())
            {
                FileItemStream fis = fii.next();
                if (!fis.isFormField())
                {
                    this.originalName = fis.getName().substring(fis.getName().lastIndexOf(System.getProperty("file.separator")) + 1);
                    if (!this.checkFileType(this.originalName))
                    {
                        this.state = this.errorInfo.get("TYPE");
                        continue;
                    }
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    in.mark(this.maxSize * 1024 * 1024 + 1);
                    BufferedImage bi = ImageIO.read(in);
                    this.width = bi.getWidth();
                    this.height = bi.getHeight();
                    this.state = this.errorInfo.get("SUCCESS");
                    this.fileName = this.getName(this.originalName);
                    this.type = this.getFileExt(this.fileName);
                    this.url = savePath + File.separator + this.fileName;
                    in.reset();
                    File file = new File(this.getPhysicalPath(this.url));
                    FileOutputStream out = new FileOutputStream(file);
                    BufferedOutputStream output = new BufferedOutputStream(out);
                    Streams.copy(in, output, true);
                    this.state = this.errorInfo.get("SUCCESS");
                    this.size = file.length();
                    break;
                }
                else
                {
                    String fname = fis.getFieldName();
                    // 只处理title，其余表单请自行处理
                    if (!fname.equals("categoryId"))
                    {
                        continue;
                    }
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer result = new StringBuffer();
                    while (reader.ready())
                    {
                        result.append((char) reader.read());
                    }
                    this.categoryId = Integer.parseInt(result.toString());
                    reader.close();
                }
            }
        }
        catch (SizeLimitExceededException e)
        {
            this.state = this.errorInfo.get("SIZE");
        }
        catch (InvalidContentTypeException e)
        {
            this.state = this.errorInfo.get("ENTYPE");
        }
        catch (FileUploadException e)
        {
            this.state = this.errorInfo.get("REQUEST");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.state = this.errorInfo.get("UNKNOWN");
        }
    }

    /**
     * 文件类型判断
     * 
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName)
    {
        Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
        while (type.hasNext())
        {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     * 
     * @return string
     */
    private String getFileExt(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     * 
     * @return
     */
    private String getName(String fileName)
    {
        SequenceGenerator uploadFileNameSeqGen = (SequenceGenerator) BaseWebProperties.SPRING_CONTEXT.getBean("uploadFileNameSeqGen");
        if (uploadFileNameSeqGen != null)
        {
            return this.fileName = "" + uploadFileNameSeqGen.next() + this.getFileExt(fileName);
        }
        Random random = new Random();
        return this.fileName = "" + random.nextInt(10000) + System.currentTimeMillis() + this.getFileExt(fileName);
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     * 
     * @param path
     * @return
     */
    private String getFolder(String path)
    {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
//        path += File.separator + this.businessCode + File.separator + formater.format(new Date());
        path += File.separator + formater.format(new Date());
        File dir = new File(this.getPhysicalPath(path));
        if (!dir.exists())
        {
            try
            {
                dir.mkdirs();
            }
            catch (Exception e)
            {
                this.state = this.errorInfo.get("DIR");
                return "";
            }
        }
        return path;
    }

    /**
     * 根据传入的虚拟路径获取物理路径
     * 
     * @param path
     * @return
     */
    public String getPhysicalPath(String path)
    {
        // String servletPath = this.request.getServletPath();
        // String realPath =
        // this.request.getSession().getServletContext().getRealPath(servletPath);
        // return new File(realPath).getParent() + File.separator + path;
    	path = this.request.getSession().getServletContext().getRealPath(path);
        return path;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }

    public void setAllowFiles(String[] allowFiles)
    {
        this.allowFiles = allowFiles;
    }

    public void setMaxSize(int size)
    {
        this.maxSize = size;
    }

    public long getSize()
    {
        return this.size;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public String getState()
    {
        return this.state;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getType()
    {
        return this.type;
    }

    public String getOriginalName()
    {
        return this.originalName;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getBusinessCode()
    {
        return businessCode;
    }

    public void setBusinessCode(String businessCode)
    {
        this.businessCode = businessCode;
    }
}
