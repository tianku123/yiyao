<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta name="description" content=""/>
<meta content="IE=8" http-equiv="X-UA-Compatible">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="dns-prefetch" href="//${dns}">
<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${contextPath}/resource/css/selectImg.css?v=1" />
<link rel="stylesheet" href="${contextPath}/resource/skins/jquery.Jcrop.min.css?v=1" />
</head>
<style>
</style>
<body class="mask-body" style="overflow:hidden;" height="100%">
    <div class="mask-box" style="height:450px;text-align:center;">
    	<table width="100%"  height="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<td width="70%" align="center" valign="middle" style="background: #FFFFF4;border:1px solid #ccc;overflow:hidden">
						<div  style="height:450px;width:530px;vertical-align:middle;overflow:auto;" >
							<table style="width:100%; height:100%;">
							   <tr><td id="imgBoxId" align="center" valign="middle">
							   		
							   </td></tr>
							</table>
						</div>
					</td>
					<td width="30%" style="margin:12px;border:0px solid #ccc;vertical-align:top;text-align:center;">
					
						<div class="img-tool" style="height:170px;margin-left:12px;margin-top:10px;">
							<div class="file-upload" style="height:55px;width:100%;border-bottom:1px dotted #ccc;padding-top:5px;margin-bottom:8px">
								<a href="javascript:;" class="file-upload-btn" style="width:156px; height:43px; margin:0 auto; display:block; background:url('${contextPath }/resource/images/upload_img_btn.jpg') no-repeat;">
									<input name="fileUploadImg" id="fileUploadImg" style="width:156px; height:43px;cursor:pointer;float:left;filter:alpha(opacity:0); opacity:0;font-size:30px"     type="file" size="5" onchange="imgComponent.onUploadImg();"/>
								</a>
							</div>
							<div style="height:112px;width:100%;text-align: left;">
								<div class="upload-img-note" style="padding-top:5px;line-height: 22px;color:#666">
									小提示:<br/>
									1、图片限制在<font color=red>3M</font>,超过处理后上传<br/>
									2、仅支持<font color=red>JPG、JPEG、GIF、PNG</font>格式<br/>
									3、若图片较大，可通过截图功能截取<br/>
								</div>
							</div>
						</div>
						<div class="img-view" style="height:185px;margin-left:12px;">
							<div style="border-top:1px dotted #ccc;">
								<div class="img-view-title" style="padding-left:10px;padding-top:8px;text-align: left;font-weight:bold;font-size:14px">图片预览</div>
								<div class="img-view-box" style="overflow:hidden;margin-left:18px;margin-top:10px;border:1px solid #ccc;height:180px;width:180px" id="viewImgId"></div>
							</div>
							<div style="padding-top:10px;padding-left:50px;">
								<label class="m-lbtn"> <input type="button" value="上传LOGO图" onclick="imgComponent.cropImg();" />  </label>
							</div>
							
							<input type="hidden" name="cropFlg" id="cropFlg" value="0" />
							<input type="hidden" name="logoImgPathId" id="logoImgPathId" value="" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
    	<div class="Clear"></div>
	</div>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js"></script>
</html>
<script type="text/javascript">
	var ajaxTools = null;
	$(document).ready(function(){
		ajaxTools = new iTools.ajax();
	});

	var imgComponent = {
		init : function(){},
		onUploadImg : function(){
			var ret = checkImageFile('fileUploadImg', '.gif,.png,.jpg,.jpeg,.bmp', 1024);
        	if (ret != true) {
            	alert(ret);
            	return false;
        	}
        	var categoryId = "0";
  
			$("#logoImgPathId").val("");
			$("#imgBoxId").html("<img src=\"${res}/resource/images/load_icon.gif\">正在加载图片中，请稍后...");
			
       	 	$.ajaxFileUpload({
				url: GLOBAL_INFO.CTX + "/imgUpload.do?categoryId=" + categoryId,
				secureuri: false,
				fileElementId: "fileUploadImg",
				dataType: "json",
				success: function(data) {
					var ret = eval("(" + data + ")");
					if(ret){
						if(ret.state == "SUCCESS"){
							var preview = $("#viewImgId");
							var xsize = preview.width();
        					var ysize = preview.height();
        					imgComponent.selUrl = ret.url;
        					var imgSrc= "${ctx}" + ret.url +"?r="+ Math.random();
							$upImg = $("<img src='" + imgSrc + "' width='" + ret.width + "' height='" + ret.height + "' />");
							$upImg.Jcrop({
								onChange: imgComponent.updatePreview,
					      		onSelect: imgComponent.updatePreview,
					      		minSize : [100, 100],
					      		maxSize : [400, 400],
					      		aspectRatio: xsize / ysize,
					      		bgColor:"#ccc", //裁剪时背景颜色设为灰色 
								bgOpacity:0.1, //透明度设为0.1 
					      		setSelect:[0, 0, 100, 100] 
					    	});
					    	
							preview.html("<img src='" + imgSrc + "' width='" + ret.width + "' height='" + ret.height + "' />");
							$("#imgBoxId").html($upImg);
							$("#logoImgPathId").val("${ctx}" + ret.url);
						}else{
							alert(ret.state);
							$("#imgBoxId").html("");
						}
					}else{
						alert("加载图片失败！");
						$("#imgBoxId").html("");
					}
				}
			});
		},

		selX : 0,
		selY : 0,
		selW : 0,
		selH : 0,
		selUrl : "",
	
		updatePreview : function(c){
			if (parseInt(c.w) > 0){
				imgComponent.selX = c.x;
      			imgComponent.selY = c.y;
      			imgComponent.selW = c.w;
      			imgComponent.selH = c.h;
	      		var preview = $("#viewImgId");
				var xsize = preview.width();
	     		var ysize = preview.height();
	        	var rx = xsize / c.w;
	        	var ry = ysize / c.h;
	        	var boundx = $("#imgBoxId img").width();
	        	var boundy = $("#imgBoxId img").height();
	        	preview.find("img").css({
	          		width: Math.round(rx * boundx) + 'px',
	          		height: Math.round(ry * boundy) + 'px',
	          		marginLeft: '-' + Math.round(rx * c.x) + 'px',
	          		marginTop: '-' + Math.round(ry * c.y) + 'px'
	        	});
      		}
		},
		
		cropImg : function()
		{
			if(this.selW <= 0 || this.selH <= 0)
			{
				alert("请先添加照片，正确的设计企业LOGO的尺寸！");
				return;
			}
			if(confirm("是否确认上传企业LOGO图尺寸？"))
			{
				ajaxTools.singleReq({
					data : 
					{
						"reqUrl" : "imgInfo",
						"reqMethod" : "cropImg",
						"imgUrl" : this.selUrl,
						"selX"   : this.selX,
						"selY"   : this.selY,
						"selW"   : this.selW,
						"selH"   : this.selH
					},
					success : function(ret)
					{
						if(ret.a == GLOBAL_INFO.SUCCESS_CODE){
							alert("企业LOGO图标上传成功,返回企业信息编辑页面！");
							$("#cropFlg").val("1");
							window.parent.onAddLogoImg();
						}
					}.bind(this)
				});
			}
		}
	};
	
	//调用返回函数
	function getCallBack(){
		var cropFlg = $("#cropFlg").val();
		if(cropFlg != "1")
		{
			alert("请先上传LOGO图，再点击确定按钮！")
			return null;
		}
		var oResult ="";
		var imgPath= $("#logoImgPathId").val();
		if(imgPath==''){
			alert("尚未选择企业LOGO图片，上传企业LOGO图片！");
			return null;
		}else{
			oResult="{src:'"+ imgPath +"'}";
		}
	   	//返回JSON数据格式
		//oResult = "[" + oResult +"]";
		return oResult;
	}
</script>