package com.qm.omp.api.invocation;

import org.apache.commons.lang.StringUtils;

import com.qm.omp.api.constants.ISystemConstants;

/**
 * @ClassName: InvocationResult
 * @Description: 请求处理结果对象
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class InvocationResult
{
    // 处理结果(成功/失败)
    private String a;
    // 系统返回码
    private String b;
    // 返回消息
    private String c;
    // 返回结果集
    private Object d;
    // 逻辑处理返回码(失败原因)
    private String e = "0";

    public String getE()
    {
        return e;
    }

    public void setE(String e)
    {
        this.e = e;
    }

    public String getA()
    {
        return a;
    }

    public void setA(String a)
    {
        this.a = a;
    }

    public String getB()
    {
        return b;
    }

    public void setB(String b)
    {
        this.b = b;
    }

    public String getC()
    {
        return c;
    }

    public void setC(String c)
    {
        this.c = c;
    }

    public Object getD()
    {
        return d;
    }

    public void setD(Object d)
    {
        this.d = d;
    }

    public static InvocationResult newInstance()
    {
        InvocationResult result = new InvocationResult();
        result.setA(ISystemConstants.SYS_SUCCESS);
        result.setE("0");
        return result;
    }

    public static InvocationResult newInstance(String retCode)
    {
        InvocationResult result = new InvocationResult();
        result.setA(retCode);
        return result;
    }

    public boolean isSuccess()
    {
        return ISystemConstants.SYS_SUCCESS.equals(this.a);
    }

    public void setRetCode(String retCode)
    {
        this.a = retCode;
    }

    public void setSysCode(String sysCode)
    {
        if (!StringUtils.isBlank(sysCode) && sysCode.startsWith("-"))
        {
            this.a = ISystemConstants.SYS_FAILED;
        }
        this.b = sysCode;
    }

    public void setResMsg(String resMsg)
    {
        this.c = resMsg;
    }

    public void setRetObj(Object retObject)
    {
        this.d = retObject;
    }

    public void setLogicCode(String logicCode)
    {
        this.e = logicCode;
        if (!StringUtils.isBlank(logicCode))
        {
            if (logicCode.startsWith("-"))
            {
                this.setSysCode(logicCode.substring(0, 7));
            }
            else
            {
                this.setSysCode(logicCode.substring(0, 6));
            }
        }
    }
}
