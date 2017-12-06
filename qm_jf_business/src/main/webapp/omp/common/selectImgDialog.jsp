<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ include file="../../taglibs.jsp"%>
<%
	//showModel:more,only
	//showTabs:lib,upload,url
	String dialogMode = request.getParameter("flg")== null ? "1" :request.getParameter("flg");
	String showModel 	= request.getParameter("showModel")== null ? "only" :request.getParameter("showModel");
	String showTabs 	= request.getParameter("showTabs")== null ? "lib,upload,url" :request.getParameter("showTabs");
	boolean blImgLib 	= false;
	boolean blImgUpload = false;
	boolean blImgUrl 	= false;
	String strTabId 	= "";
	showTabs =showTabs.toLowerCase();
	if(showTabs.indexOf("lib")>-1){
		blImgLib = true;
		strTabId = "ImgLib";
	}
	if(showTabs.indexOf("upload")>-1){
		blImgUpload = true;
		if("".equals(strTabId)){
			strTabId = "ImgUpload";
		}
	}
	if(showTabs.indexOf("url")>-1){
		blImgUrl = true;
		if("".equals(strTabId)){
			strTabId = "ImgUrl";
		}
	}
	blImgUrl = false;
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content=""/>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1" />
<link rel="icon" href="${res}/resource/images/favicon.ico?v=1" type="image/x-icon" />
<link rel="shortcut icon" href="${res}/resource/images/favicon.ico?v=1" type="image/x-icon" />
<link rel="dns-prefetch" href="//${dns}">
<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${contextPath}/resource/css/selectImg.css?v=1" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
</head>
<style>
</style>
<body class="easyui-layout">
<div data-options="region:'center',border:false" style="padding:10px;">
    <input type="hidden" name="dialogMode" id="dialogMode" value="<%=dialogMode %>" />
	<input type="hidden" name="showModel" id="showModel" value="<%=showModel %>" />
	<input type="hidden" name="showTabs" id="showTabs" value="<%=showTabs %>" />
    <div class="mask-box">
		<div id="tabId" class="tab-box">
			<div class="tab-nav" id="tabNavId">
				<%if(blImgLib){%><div class="tab-menu select" id="TabNavImgLib" onclick="onChangeTab(this,'ImgLib');">我的图片库</div><%} %>			
				<%if(blImgUpload){%><div class="tab-menu" id="TabNavImgUpload" onclick="onChangeTab(this,'ImgUpload');">本地上传</div><%} %>
				<%if(blImgUrl){%><div class="tab-menu" id="TabNavImgUrl" onclick="onChangeTab(this,'ImgUrl');">远程图片</div><%} %>
			</div>
			
			<div class="tab-content" id="tabContentId" style="height:365px;margin:0;padding:0;">
				<%if(blImgLib){%>
				<!-- 图片库 开始 -->
				<div class="tab-body" style="display:block;padding:0;" id="tabBodyImgLib">
					<div class="select-imglib">
						<div class="select-imglib-left">
							<div class="imglib-tree">
								<ul>
									<li class="tree-menu tree-menu-sub" style="padding-left:28px;"><a href="javascript:void(0);" onclick="imgComponent.queryImg('', 1);">我的图片</a></li>
									<li class="tree-menu tree-menu-close"><a href="javascript:void(0);" onclick="cartegoryComponent.clickCategory(this);imgComponent.queryImg('0', 1);">默认图片</a></li>
								</ul>
							</div>
						</div>
						<div class="select-imglib-right">
							<div class="select-imglib-list" id="libImgsId">
					   			<div></div>
						   	</div>
							<div class="pager"></div>
						</div>
					</div>
					<div class="select-imglib-box" style="height:90px;">
						<div id="selectImages">
						</div>
					</div>
				</div>
				<!-- 图片库 结束 -->
				<div class="Clear"></div>
				<%} %>
				<%if(blImgUpload){%>
				<!-- 本地 多图片上传 开始-->
				<div class="tab-body" style="display:none;padding:0;" id="tabBodyImgUpload">
					<div class="select-imgUpload" style="height:350px;">
						<div class="select-imgUpload-left" style="text-align:left;overflow: auto;height:320px;">
							<div id="imgUploadBoxId" style="padding:10px;margin-left:4px;height:300px;">
								<!--
								<label class="view-img-box" style="text-align:center;">
									<span class="view-img" style="font-size:14px;line-height: 24px;" id="addImgFileBtnId" onclick="addImgFileBtn(this);"><br/>点击添加<br/>图片选择框</span>
								</label>
								-->
								
							</div>			
						</div>
						<div class="select-imgUpload-right" style="height:320px;">
							<div class="img-tool" style="height:240px;">
								<div class="file-upload" style="font-size:24px;margin-top:6px;">
									批量上传图片
								</div>
								<div style="height:25px;width:100%;text-align: left;padding-top:10px;">
								  <span style="float:left;">上传到：
									<select name="categorySelect" id="categorySelect" style="height:22px;vertical-align:middle;">
										<option value="0" selected>默认分类</option>
									</select>
								  </span> 
								  <span class="add-img-sort" onclick="onShowAddSortImg(this)" style="padding-top:5px;height:21px;"><a>创建分类</a></span>
								</div>
								<div style="height:120px;width:100%;text-align: left;">
									<div class="upload-img-note" style="display:;">
										小提示:<br/>
										1、每张图片限制在<font color=red>3M</font>,超过处理后上传<br/>
										2、仅支持<font color=red>JPG、JPEG、GIF、PNG</font>格式<br/>
										<!-- 3、每次批量上传最多支持<font color=red>6</font>张图片<br/>  -->
										3、若图片预览功能不能显示，不影响<br/>
										4、选择后点击“上传图片”进行上传<br/>
									</div>
									<div class="add-img-sort-box" style="display:none;height:125px;">
										<div style="margin-top:15px;margin-left:15px;">
									  	 	<input class="text" style="width:100px;" type="text"  name="new_categoryName" id="new_categoryName"/><a href="javascript:void(0);" onclick="cartegoryComponent.addCategory();">添加</a>
									   	</div>
									   	<div style="padding-left:12px;padding-top:8px;line-height: 20px;color:#666">
									   		分类说明：<br/>
									   		1、字数限制在12字节(6个中文)之内<br/>
									   		2、添加成功后，请在列表框中选择。<br/>
									   	</div>
									   
									</div>
								</div>
							</div>
							<div class="img-view" style="padding-top:10px;height:50px;">
								<div class="b-btn"><input onclick="saveAllImgs();" name="ImgUploadBtn" id="ImgUploadBtn" type="button"  value=" 上传图片 " style="cursor:pointer;"/></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 本地 多图片上传 结束-->
				<div class="Clear"></div>
				<%} %>
			</div>
		</div>
    	<div class="Clear"></div>
	</div>
</div>
	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
         <button class="orangeBtn" onclick="getCallBack();">提交</button>
         <span class="ml15"><button class="grayBtn" onclick="winClose();">取消</button> </span>
    </div>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery.pager.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js"></script>
<script type="text/javascript">
// 全局变量 上传文件类型
var imgType  =".gif,.jpg,.jpeg,.png";
// 全局变量 默认展示图片库
var tabId="<%=strTabId%>";
// 显示模式 more only
var showModel ="<%=showModel%>";
// Ajax
var ajaxTools = null;
$(document).ready(function(){
	ajaxTools = new QM.ajax();
	cartegoryComponent.init();
	//显示默认的 Tab
	$("#TabNav"+tabId).addClass("select");
	$("#tabBody"+tabId).show();
	initImgFileBtn();
});

function winClose()
{
    var dialogMode = $("#dialogMode").val();
    if(dialogMode == "1")
    {
        QM.dialog.closeWinOther();
    }
    else
    {
        QM.dialog.closeWin();
    }
}

//切换Tab
function onChangeTab(o,id){
	if(tabId!=id){
		var objs = $(o).parent().find("div");
		$(objs).removeClass("select");
		$(o).addClass("select");
		var objTabs=$("#tabContentId").find(".tab-body");
		$(objTabs).hide();
		$("#tabBody"+id).show();
		tabId=id;
	}
	if(id == "ImgLib"){
		cartegoryComponent.queryCategory("0");
	}else if(id == "ImgUpload"){
		cartegoryComponent.queryCategory("1");
	}
}

function getLocalImgPath(obj){
	var  imgPath= "";
	try{
		if($.browser.msie){
			if($.browser.version == 6.0){
				imgPath = obj.value;
			}else{
				obj.select();
				//必须设置 默认焦点 否则失败
				$("#ImgUploadBtn").focus();
				imgPath= document.selection.createRange().text; 
			}  
		}else if($.browser.mozilla){
			if($.browser.version<7){//firefox7以下版本
				imgPath = obj.files[0].getAsDataURL();
			}else{
				imgPath = window.URL.createObjectURL(obj.files.item(0));
			}
		}else{
			imgPath = window.URL.createObjectURL(obj.files.item(0));	 
		}
    }catch(e){} 
    //objectURL = (window.webkitURL ? webkitURL : URL).createObjectURL(file.files[0]);
    if(imgPath==''){imgPath = obj.value;}else{ return imgPath;}
}

//初始化 多图片上传点击按钮
function initImgFileBtn(){
	var initImg=1;
	var iHtml="<div class=\"view-img-div\" id=\"imgBoxId_"+ initImg +"\"><label class=\"view-img-box\">";
	if($.browser.msie){
		iHtml = iHtml + "<span class=\"view-img\" >";
		iHtml = iHtml + "<span class=\"img\" id=\"imgId_"+ initImg +"\"></span>";
	    iHtml = iHtml + "<input type=\"file\" class=\"fileUploadBtn\" id=\"fileUploadImgId_"+ initImg +"\" name=\"fileName\" onchange=\"javascript:onLocalViewImg(this,"+ initImg +");\">";	
	}else{
		iHtml = iHtml + "<span class=\"view-img\">";
		iHtml = iHtml + "<img id=\"imgId_"+ initImg +"\">";
		iHtml = iHtml + "<input type=\"file\" class=\"fileUploadBtn\" id=\"fileUploadImgId_"+ initImg +"\" name=\"fileName\"  onchange=\"javascript:onLocalViewImg(this,"+ initImg +");\">";
	}
	iHtml = iHtml + "<input type=\"hidden\"  class=\"fileUploadImgPath\" name=\"fileUploadImgPathId\" id=\"fileUploadImgPathId_"+ initImg +"\" ></span>";
	iHtml = iHtml + "</label><label class=\"view-img-info\" id=\"viewImgInfoId_"+ initImg +"\" style=\"display:none;\"><span class=\"info\" id=\"imgInfoId_"+ initImg +"\">未选取</span>";
	iHtml = iHtml + "<span class=\"del\" onclick=\"onDelViewImg(this,"+ initImg +");\"><a>取消</a></span></label></div>";
	$("#imgUploadBoxId").html(iHtml);
}

var intImgNum=2;
//添加图片选择框
function addImgFileBtn(id){
	//判断添加几个图片了
	//var arrLab = $("#imgUploadBoxId").find(".view-img-box");
	//var num = arrLab.length;
	//if(num>6){
		//alert("最多一次添加 6 张图片,请点击上传后再添加。");
		//return;
	//}
	var obj = $("#imgBoxId_"+id);
	var iHtml="<div class=\"view-img-div\" id=\"imgBoxId_"+ intImgNum +"\"><label class=\"view-img-box\">";
	if($.browser.msie){
		iHtml = iHtml + "<span class=\"view-img\" >";
		iHtml = iHtml + "<span class=\"img\" id=\"imgId_"+ intImgNum +"\"></span>";
	    iHtml = iHtml + "<input type=\"file\" class=\"fileUploadBtn\" id=\"fileUploadImgId_"+ intImgNum +"\" name=\"fileName\" onchange=\"javascript:onLocalViewImg(this,"+ intImgNum +");\">";	
	}else{
		iHtml = iHtml + "<span class=\"view-img\">";
		iHtml = iHtml + "<img id=\"imgId_"+ intImgNum +"\">";
		iHtml = iHtml + "<input type=\"file\" class=\"fileUploadBtn\" id=\"fileUploadImgId_"+ intImgNum +"\" name=\"fileName\"  onchange=\"javascript:onLocalViewImg(this,"+ intImgNum +");\">";
	}
	iHtml = iHtml + "<input type=\"hidden\"  class=\"fileUploadImgPath\" name=\"fileUploadImgPathId\" id=\"fileUploadImgPathId_"+ intImgNum +"\" ></span>";
	iHtml = iHtml + "</label><label class=\"view-img-info\" id=\"viewImgInfoId_"+ intImgNum +"\" style=\"display:none;\"><span class=\"info\" id=\"imgInfoId_"+ intImgNum +"\">未选取</span>";
	iHtml = iHtml + "<span class=\"del\" onclick=\"onDelViewImg(this,"+ intImgNum +");\"><a>取消</a></span></label></div>";
	obj.after(iHtml);
	obj.find(".view-img-box").removeAttr("onclick");
	intImgNum = intImgNum+1;
}

//删除预览的图片
function onDelViewImg(o, id){
	var obj = $("#imgBoxId_" + id);
	obj.remove();
	//obj.clear();
}

//本地图片预览功能
function onLocalViewImg(o,id){	
	var imgName = $(o).val();
	imgName = imgName.replace(/\\/g,"/"); 
	imgName = imgName.substring(imgName.lastIndexOf("/")+1);
	//判读文件类型
	//var imgType  =".gif .jpg .jpeg .png";
    var imgExt = imgName.match(/.[^.]*$/);
    var imgSufName= imgExt[0].toLowerCase();
    if (imgType.indexOf(imgSufName)<0){
    	alert("上传的图片只允许"+ imgType +"文件；现在上传是："+imgSufName+"类型文件，请重新选择文件。");
		$("#imgInfoId_"+id).html("未选取");
		$("#fileUploadImgPathId_"+id).val("");
    	return;
    }else{
		var imgPath = getLocalImgPath(o);
		if($.browser.msie){
			var obj = document.getElementById("imgId_"+id);
			obj.title=imgName;
			obj.style.background ="#fff";
			//使用滤镜效果 显示预览
			//使用滤镜效果 显示预览
			try{
				
				if($.browser.version>=10){
				    obj.innerHTML="读取图片中";
					var freader = new FileReader();
					freader.readAsDataURL(o.files[0]);
					freader.onload=function(e){
						obj.innerHTML="<img src=\""+this.result+"\" />";
					};
					freader.onprogress=function(e){
						obj.innerHTML="读取图片中";
					};
				}else if($.browser.version>=7){
				    obj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + imgPath + "\")"; 
				}else{
					obj.innerHTML="<img src=\""+imgPath+"\" title=\""+ imgName +"\"/>";
				}
			}catch(e){
				obj.innerHTML=imgName;
			}
		}else{
			var obj = $("#imgId_"+id);
			obj.attr("title",imgName);
		    obj.attr("src",imgPath);
		}
		$("#imgInfoId_"+id).html("<font color=blue>未上传</font>");
		$("#fileUploadImgPathId_"+id).val("");
	 }
	 $("#viewImgInfoId_"+id).show();
	 if((id+1)==intImgNum){addImgFileBtn(id);}
}

//保存上传图片
var selectAllFile = null;
var saveIndex = 0;
function saveAllImgs(){
	$("#ImgUploadBtn").attr("disabled", "disabled");
	saveIndex = 0;
	selectAllFile = $("#imgUploadBoxId").find(".fileUploadBtn");
	onSaveUploadImg(saveIndex);
}

function onSaveUploadImg(saveIndex){
	var categoryId = $("#categorySelect").val();
    if(categoryId == null)
    {
    	categoryId = "";
    }
    var currentFile = selectAllFile.eq(saveIndex);
    var fileId = currentFile.attr("id");
    var imgUrl = currentFile.val();
    if(imgUrl == null || imgUrl == "")
    {
    	saveIndex ++;
    	if(saveIndex >= selectAllFile.length)
		{
			$("#ImgUploadBtn").removeAttr("disabled");
			return;
		}else{
			onSaveUploadImg(saveIndex);
		}
    }
    else
    {
    	var uploadId = fileId.split("_")[1];
    	$("#imgInfoId_" + uploadId).html("正在上传");
		$.ajaxFileUpload({
			url: GLOBAL_INFO.CONTEXTPATH + "/imgUpload?categoryId=" + categoryId,
			secureuri: false,
			fileElementId: fileId,
			dataType: "json",
			complete : function()
			{
				saveIndex ++;	
				if(saveIndex >= selectAllFile.length)
				{
					$("#ImgUploadBtn").removeAttr("disabled");
				}
				else
				{
					onSaveUploadImg(saveIndex);
				}
			},
			success: function(data) {
				var ret = eval("(" + data + ")");
				if(ret)
				{
					if(ret.state == "SUCCESS")
					{
						$("#fileUploadImgId_" + uploadId).remove();
						$("#imgInfoId_" + uploadId).html("<font color='green'>上传成功</font>");
						$("#imgInfoId_" + uploadId).parent().find(".del").hide();
						var imgPath = "${res}/"+ret.url;
						//var imgPath = ret.url;
						$("#fileUploadImgPathId_" + uploadId).val(imgPath);
					}
					else
					{
						$("#imgInfoId_" + uploadId).html("<font color='red'>" + ret.state + "</font>");
					}
				}
				else
				{
					$("#imgInfoId_" + uploadId).html("<font color='red'>上传失败</font>");
				}
			}
		});
	}
}

var cartegoryComponent = {
	init : function()
	{
		this.queryCategory();
		imgComponent.queryImg("", 1);
	},

	queryCategory : function(flg, isAdd)
	{
		ajaxTools.singleReq({
			data : 
			{
				"reqUrl" : "imgInfo",
				"reqMethod" : "queryImgCategory"
			},
			success : function(ret)
			{
				if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
				{
					var categoryLst = ret.d;
					var categotyHtml = "";
					if(categoryLst && categoryLst.length > 0)
					{
						for(var i = 0; i < categoryLst.length; i ++)
						{
							if(flg == "1")
							{
								categotyHtml += "<option value='" + categoryLst[i].categoryId + "' " + ((i == (categoryLst.length - 1) && isAdd) ? "selected" : "") + " >" + categoryLst[i].categoryName + "</option>";
							}
							else
							{
								categotyHtml += "<li class=\"tree-menu tree-menu-close\"><a href=\"javascript:void(0);\" onclick=\"cartegoryComponent.clickCategory(this);imgComponent.queryImg('" + categoryLst[i].categoryId + "', 1);\">" + categoryLst[i].categoryName + "</a></li>";
							}
						}
					}
					if(flg == "1")
					{
						$("#categorySelect option:gt(0)").remove();
						$("#categorySelect").append(categotyHtml);
					}
					else
					{
						$(".imglib-tree ul li:gt(1)").remove();
						$(".imglib-tree ul").append(categotyHtml);
					}
				}
			}.bind(this)
		});
	},
	
	clickCategory : function(obj)
	{
		$(".tree-menu").removeClass("tree-menu-open");
		$(obj).parent().addClass("tree-menu-open");
	},
	
	addCategory : function()
	{
		var categoryName = $("#new_categoryName").val();
		if(categoryName == null || categoryName == "")
		{
			alert("请输入分类名！");
			return;
		}
		ajaxTools.singleReq({
			data : 
			{
				"reqUrl" : "imgInfo",
				"reqMethod" : "addImgCategory",
				"categoryName" : categoryName
			},
			success : function(ret)
			{
				if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
				{
					$("#new_categoryName").val("");
					$(".add-img-sort").removeClass("add-img-sort-select");
	   				$(".upload-img-note").show();
	   				$(".add-img-sort-box").hide();
					this.queryCategory("1", true);
				}
				else
				{
					alert(ret.c);
				}
			}.bind(this)
		});		
	}
};

var imgComponent = {
	pageSize : 10,
	cuurCategoryId : "",
	init : function()
	{
	},
	
	queryImg : function(categoryId, page)
	{
		if(categoryId == null || categoryId == "")
		{
			categoryId = "";
			$(".tree-menu").removeClass("tree-menu-open");
		}
		this.cuurCategoryId = categoryId;
		ajaxTools.singleReq({
			data : 
			{
				"reqUrl" : "imgInfo",
				"reqMethod" : "queryImgLst",
				"categoryId" : categoryId,
				"page" : page,
				"pageSize" : this.pageSize
			},
			success : function(ret)
			{
				if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
				{
					var count = ret.d.count;
					var imgLst = ret.d.imgLst;
					var imgLstHtml = "";
					if(imgLst && imgLst.length > 0)
					{
						for(var i = 0; i < imgLst.length; i ++)
						{
						
							imgLstHtml += "<label><span><img ondblclick=\"imgComponent.select(this);\" src=\"${ctx}" +  imgLst[i].imgUrl + "\" title=\"选择请双击\"></span></label>";
						}
						imgLstHtml +="<br class=\"cb\">";
					}
					$("#libImgsId div").html(imgLstHtml);
					this.showPager(count, this.pageSize, page);
				}
			}.bind(this)
		});
	},
	
	select : function(obj)
	{
		var allSelect = $("#selectImages img");
		var selectSrc = $(obj).attr("src");
		var isSelect = false;
		$(allSelect).each(function(){
			if(selectSrc == $(this).attr("src"))
			{
				isSelect = true;
				return false;
			}
		});
		if(!isSelect)
		{
			var selectImg = "<label><span ondblclick='onDelLibImg(this);'><img src='" + selectSrc + "' title=\"删除，请双击\"></span></label>";
			$("#selectImages").append(selectImg);	
		}
		else
		{
			alert("该图片已选择");
		}
	},
	
	showPager : function (totalRecords, pageSize, currentPage)
	{
		var t = totalRecords / pageSize;
		totalPages = (totalRecords % pageSize == 0) ? (t) : (parseInt(t) + 1);
		$(".pager").pager({ pagenumber: currentPage, pagecount: totalPages, buttonClickCallback: imgComponent.PageClick });
	},

	PageClick : function(pageclickednumber) {
        imgComponent.queryImg(imgComponent.cuurCategoryId, pageclickednumber);
	}
};

//删除图片--从图片库中选择
function onDelLibImg(o){
	var obj = $(o).parent();
	obj.remove();
}

function onShowAddSortImg(o){
   var obj = $(".add-img-sort-box");
   if(obj.is(":hidden")){
	   $(o).addClass("add-img-sort-select");
	   $(".upload-img-note").hide();
	   obj.show();
	}else{
	   $(o).removeClass("add-img-sort-select");
	   $(".upload-img-note").show();
	   obj.hide();
	}
} 

//调用返回函数
function getCallBack(){
	var oResult ="";
	var num = 0;
    if(tabId =='ImgLib' || tabId =='ImgUpload'){
		var arrImg=$("#selectImages").find("img");
		$.each(arrImg, function(i) {
			var imgPath = arrImg.eq(i).attr("src");
   			if(oResult==''){
   				oResult="{src:'"+ imgPath +"'}";
   			}else{
   				oResult=oResult+",{src:'"+ imgPath +"'}";
   			}
   			num=num+1;
		});
		if(oResult==''){QM.dialog.showFailedDialog("尚未选择图片，请从图片库选择图片！");return;}
		if(showModel=='only' && num>1){QM.dialog.showFailedDialog("要求只允许选择 1 张图片,现在已经选择 "+num+" 张图片！");return;}
	}
	/**else if(tabId =='ImgUpload'){//ImgUpload
		var arrImg = $("#imgUploadBoxId").find(".fileUploadImgPath");
		$.each(arrImg, function(i) {
			var imgPath= arrImg.eq(i).val();
			if(imgPath!=''){
				if(oResult==''){
	   				oResult="{src:'"+ imgPath +"'}";
	   			}else{
	   				oResult=oResult+",{src:'"+ imgPath +"'}";
	   			}
	   			num= num+1;
   			}
		});
		if(oResult==''){QM.dialog.showFailedDialog("尚未选择本地图片或未上传图片，请选择图片或上传图片完成后再操作！");return null;}
		if(showModel=='only' && num>1){QM.dialog.showFailedDialog("要求只允许选择 1 张图片,现在已经选择 "+num+" 张图片！");return null;}		
	}**/
	else if(tabId =='ImgUrl'){
		var src =$("#urlImgSrcId").val();
		src = $.trim(src);
		if(src!=''){
			var width = $("#urlImgWidthId").val();
			var height = $("#urlImgHeightId").val();
			var border = $("#urlImgBorderId").val();
			var hvspace = $("#urlImgHVspaceId").val();
			var title = $("#urlImgTitleId").val();
			var float = $("#urlImgFloatId").val();
			if(float==''){float="left";}
			oResult = "src:'"+src+"'";
			oResult = oResult + ",width:'"+width+"'";
			oResult = oResult + ",height:'"+height+"'";
			oResult = oResult + ",border:'"+border+"'";
			oResult = oResult + ",hspace:'"+hvspace+"'";
			oResult = oResult + ",vspace:'"+hvspace+"'";
			oResult = oResult + ",title:'"+title+"'";
			oResult = oResult + ",float:'"+float+"'";
			oResult="{"+ oResult +"}";
		}
		if(oResult==''){QM.dialog.showFailedDialog("尚未设置远程图片，请设置图片！");return;}
	}
   	//返回JSON数据格式
	oResult = "[" + oResult +"]";
	var dialogMode = $("#dialogMode").val();
	if(dialogMode == "1")
    {
        QM.dialog.doWinOtherCallback(true, oResult);
    }
    else
    {
        QM.dialog.doWinCallback(true, oResult);
    }
}
</script>
