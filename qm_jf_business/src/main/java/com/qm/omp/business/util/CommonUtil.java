/**
 * @Title: CommonUtil.java
 * @Package: com.qm.omp.business.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: Admin
 * @date: 2014-9-19 下午4:57:40
 */
package com.qm.omp.business.util;

/**
 * @ClassName: CommonUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Admin
 * @date 2014-9-19 下午4:57:40
 */
public class CommonUtil
{
    public final static int ROWS = 10; // 每页显示10条数据

    public static int getPageSize(int total)
    {
        int pageSize = 0;
        pageSize = total / ROWS;
        if (total % ROWS != 0)
            pageSize++;
        return pageSize;
    }
}
