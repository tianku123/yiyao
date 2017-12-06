<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>医药管理项目</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="login_container">
  <div class="login_main">
     <div class="loginLeft"></div>
     <ul class="login_list">
       <li><input type="text" class="userId" name="loginId" id="loginId"  placeholder="用户编码" autofocus="autofocus" onkeydown="loginComponent.treatKeyEvent(event);"/></li>
       <li><input type="password" class="userPw" name="loginPassword" id="loginPassword"  placeholder="密码"  onkeydown="loginComponent.treatKeyEvent(event);"/></li>
       <li><button class="loginBtn" onclick="loginComponent.login();">登 录</button></li>
       <li><p class="errorBox" style="display:none;" id="resMsg">请正确输入用户编码和密码</p></li>
     </ul>
  </div><!--end login_main-->
</div><!--end login_container-->
</body>
<script src="${contextPath}/resource/scripts/jquery-1.7.1.min.js" language="javascript" type="text/javascript"></script>
<script src="${contextPath}/resource/scripts/qm_main.js" language="javascript" type="text/javascript"></script>
<script src="${contextPath}/resource/scripts/qm_util.js" language="javascript" type="text/javascript"></script>
<script src="${contextPath}/resource/scripts/md5.js" language="javascript" type="text/javascript"></script>
<script src="${contextPath}/resource/scripts/ie-placeholder.js" language="javascript" type="text/javascript"></script>
<script language="javascript">
var ajaxTools = new QM.ajax();

var loginComponent = 
{
    formCheck : function()
    {
        var flag=true;
        var loginName = trimBlankFunc($("#loginId").val());
        var password = trimBlankFunc($("#loginPassword").val());
        
    	if(loginName.length == 0)
        {
            $("#loginId").focus();
            $("#resMsg").html("请输入用户名!");
            flag = false;
        }
        else if(password.length == 0)
        {
            $("#loginPassword").focus();
            $("#resMsg").html("请输入密码!");
            flag = false;
        }
        
        //if(flag){
    	//	$("#loginPassword").val(hex_md5(password).toUpperCase());
    	//}
        return flag;
    },
    
    login : function()
    {
        $("#resMsg").html("");
        $("#resMsg").hide();
        if(!this.formCheck())
        {
        	$("#resMsg").show();
            return;
        }
        var loginName = trimBlankFunc($("#loginId").val());
        var password = trimBlankFunc($("#loginPassword").val());
       
        ajaxTools.singleReq({
            data : 
            {
                "reqUrl" : "login",
                "reqMethod" : "login",
                "loginId" : loginName,
                "loginPwd" : password
            },
            success : function(ret)
            {
                   
                if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
                {
                    location.href = "omp/main.jsp";
                }
                else
                {
                    $("#resMsg").html(ret.c);
                    $("#resMsg").show();
                   
                }
            }.bind(this)
        });
    },
    
    treatKeyEvent : function(event)
    {
        if(getKeyCode(event)==13){
            this.login();
        }
    }
};
$(function(){
	
});
</script>
</html>