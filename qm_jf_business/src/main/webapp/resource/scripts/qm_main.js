//全局公共变量
GLOBAL_INFO = 
{
    COMMON_REQ_URI   : '/qm_jf_business/actionDispatcher.do',
    CONTEXTPATH      : '/qm_jf_business',
    RESOURCE_PREFIX : 'http://localhost:9000/qm_jf_business/',
    SUCCESS_CODE     :  "0",
    EORROR_CODE      :  "1"
};

var QM = window.QM || {};

//页面请求对象
(function($){
	QM.PageDynRequestInfo = function(){};
	QM.PageDynRequestInfo.prototype = 
	{
		dynamicDataNodeName     : '',
	    dynamicURI              : '',
	    dynamicParameter        : [],
	    dynamicRequestCallback  : null
	};
})(jQuery);

//封装AJAX请求
(function($){
	QM.ajax = function()
	{
		this.default_options = {
            "type"        :    "post",
            "timeout"     :    "40000",
            "contentType" :    "application/x-www-form-urlencoded; charset=UTF-8",
			"url"         :    GLOBAL_INFO.COMMON_REQ_URI,
            "success"     :    function(data){
                alert("Ajax Success!");
            },
            "error"      :    function(request, textStatus, errorThrown){
            },
            "complete" : function(){
            }
        };
	};
	
	QM.ajax.prototype = {
		//多个请求
		multiReqs : (function(){
	        return function(dynamicReqs){
	        	if(dynamicReqs && dynamicReqs.length)
	        	{
	        		var jsonRequestStr = JSON.encode(dynamicReqs);
	        		var user_options = {
	        			data : {
	        				"jsonParam" : jsonRequestStr
	        			}
	        		};
	        		var options = {};
	        		$.extend(options, this.default_options, user_options);
	        		options["success"] = function(data)
		            {
		            	var obj = JSON.decode(data);
		            	for (var i = 0; i < dynamicReqs.length; i++) 
		                {
		                    if (dynamicReqs[i].dynamicRequestCallback != null) 
		                    {
		                    	if(obj[dynamicReqs[i].dynamicDataNodeName])
		                        {
		                        	dynamicReqs[i].dynamicRequestCallback(obj[dynamicReqs[i].dynamicDataNodeName]);
		                        }
		                    }
		                }
		            };
		            //发送请求
		            $.ajax(options);
	        	}
	        };
	    })(),
	    //单个请求
        singleReq : (function(){
	        return function(user_options){
	        	var options = {"dataType" : "json"};
	            
	            //统一处理弹出层
	            var new_options = {};
	            for(var key in user_options)
	            {
	            	if(key != 'success' && key != 'confirmMsg')
	            	{
	            		new_options[key] = user_options[key];
	            	}
	            }
	            new_options['success'] = function(result)
	            {
	            	if(result){
	            		if(result && result.a == GLOBAL_INFO.EORROR_CODE)
	                	{
	            			//权限校验，用户未登陆
	            			if(result.b == '-100002' && result.e == "-10000204")
	                		{
	            				QM.dialog.showFailedDialog(result.c,function(){location.href = GLOBAL_INFO.CONTEXTPATH + "/login.jsp"});
	                		}
	            			else if(result.b == '-100002')
	                		{
	            				if(ret.c){
	    							QM.dialog.showFailedDialog(result.c);
	    						}
	                		}
	                		else
	                		{
	                			user_options['success'](result);
	                		}
	                	}
	                	else
	                	{
	                		
	                		user_options['success'](result);
	                	}
	            	}
	            };
	            $.extend(options, this.default_options, new_options);
	            $.ajax(options);
	        };
	    })()
	};
})(jQuery);

QM.dialog = {
	winCallback : null,
	winOtherCallback : null,
	paramMap : null,
	
	addData : function(key, value)
	{
		if(top != window)
		{
			top.QM.dialog.addData(key, value);
		}
		else
		{
			if(this.paramMap == null) this.paramMap = new Map();
			this.paramMap.put(key, value);
		}
	},
	
	getData : function(key)
	{
		if(top != window)
		{
			return top.QM.dialog.getData(key);
		}
		else
		{
			if(this.paramMap == null) this.paramMap = new Map();
			return this.paramMap.get(key);
		}
		
	},
	
	clearData : function()
	{
		if(top != window)
		{
			top.QM.dialog.clearData();
		}
		else
		{
			if(this.paramMap == null) this.paramMap = new Map();
			return this.paramMap.clear();
		}
		
	},
	
	openWin : function(options, pageUrl, callback)
	{
		if(top != window)
		{
			top.QM.dialog.openWin(options, pageUrl, callback);
		}
		else
		{
			if(callback)
			{
				this.winCallback = callback;
			}
			$('#winFrame').attr("src", pageUrl);
			//$('#win').window("refresh", pageUrl);
			var width = options.width ? options.width : 800 ;
			var height = options.height ? options.height : 500 ;
			//var width = 800;
			//var height = 500;
			$('#win').window({'title' : options.title, 'width' : width, 'height' : height, 'minimizable' : false, "collapsible" : false, 
				'onBeforeClose' : function(){
					$('#winFrame').attr("src","about:blank");
					QM.dialog.clearData();
				}
			});
			$('#win').window('open');
		}
	},
	
	openWinOther : function(options, pageUrl, callback)
	{
		if(top != window)
		{
			top.QM.dialog.openWinOther(options, pageUrl, callback);
		}
		else
		{
			if(callback)
			{
				this.winOtherCallback = callback;
			}
			$('#winOtherFrame').attr("src", pageUrl);
			//$('#winOtherFrame').window("refresh", pageUrl);
			//var width = options.width ? options.width : 800 ;
			//var height = options.height ? options.height : 500 ;
			var width = 800;
			var height = 500;
			$('#winOther').window({'title' : options.title, 'width' : width, 'height' : height, 'minimizable' : false, "collapsible" : false, 
				'onBeforeClose' : function(){
					$('#winOtherFrame').attr("src","about:blank");
				}
			});
			$('#winOther').window('open');
		}
	},
	
	closeWin : function()
	{
		if(top != window)
		{
			top.QM.dialog.closeWin();
		}
		else
		{
			$('#win').window('close');
		}
	},
	
	closeWinOther : function()
	{
		if(top != window)
		{
			top.QM.dialog.closeWinOther();
		}
		else
		{
			$('#winOther').window('close');
		}
	},
	
	doWinCallback : function(isClose, params)
	{
		if(top != window)
		{
			top.QM.dialog.doWinCallback(isClose, params);
		}
		else
		{
			if(isClose)
			{
				this.closeWin();
			}
			this.winCallback && this.winCallback(params);
		}
	},
	
	doWinOtherCallback : function(isClose, params)
	{
		if(top != window)
		{
			top.QM.dialog.doWinOtherCallback(isClose, params);
		}
		else
		{
			if(isClose)
			{
				this.closeWinOther();
			}
			this.winOtherCallback && this.winOtherCallback(params);
		}
	},
	
	showSuccessDialog : function(msg, callback)
	{
		if(top != window)
		{
			top.QM.dialog.showSuccessDialog(msg, callback);
		}
		else
		{
			$.messager.alert("成功", msg, "info", callback);
		}
	},
	
	showFailedDialog : function(msg, callback)
	{
		if(top != window)
		{
			top.QM.dialog.showFailedDialog(msg, callback);
		}
		else
		{	
			$.messager.alert("失败", msg, "error", callback);
		}
	},
	
	showConfirmDialog : function(msg, callback)
	{
		if(top != window)
		{
			top.QM.dialog.showConfirmDialog(msg, callback);
		}
		else
		{
			$.messager.confirm("确认", msg, callback);
		}
	}
};

//封装Form
(function($){
	QM.Form = function(id)
	{
		this.id = id;
	}

	QM.Form.prototype = {
		submitForm : function(fn, btnId)
		{
			var fun = null;
			if(btnId)
			{
				fun = document.getElementById(btnId).onclick;
				document.getElementById(btnId).onclick = function(){return false;};
			}
			$('#' + this.id).form("submit", {
				url : GLOBAL_INFO.COMMON_REQ_URI,
				onSubmit : function() {
					if(fn) {
						if(!fn()) {
							return false;
						}
					}
					return $(this).form('validate');
				},
				success : function(result) {
					var retObj = $.parseJSON(result);
					if(retObj.b == '-100002' && retObj.e == "-10000204")
            		{
        				QM.dialog.showFailedDialog(retObj.c,function(){location.href = GLOBAL_INFO.CONTEXTPATH + "/login.jsp"});
            		}
					else
					{
						if(btnId)
						{
							document.getElementById(btnId).onclick = fun;
						}
						if(retObj && retObj.a == GLOBAL_INFO.SUCCESS_CODE)
						{
							top.QM.dialog.doWinCallback(true, retObj);
						}
						else
						{
							top.QM.dialog.doWinCallback(false, retObj);
						}
					}
				}
			});
		}
	};
})(jQuery);	
	

//封装DataGrid
(function($){
	QM.dataGrid = function(id, attrs, toolbar, qFn, pageTemplate)
	{
		this.id = id;
		this.attrs= {};
		this.qFn = qFn;
		this.queryParam = qFn ? qFn() : {};
		this.toolbar = toolbar;
		var _attrs = {
			url : GLOBAL_INFO.COMMON_REQ_URI,
			nowrap : false,
			striped : true,
			fit : true,
			fitColumns : true,
			singleSelect : true,
			selectOnCheck : false,
			checkOnSelect : false,
			pagination : true,
			pageSize : 50,
			pageList : [50, 100, 200, 500, 1000],
			rownumbers : true,
			sortOrder : "desc",
			queryParams : this.queryParam,
			loadFilter : function(data)
			{
				if(data && data.a == GLOBAL_INFO.EORROR_CODE)
            	{
        			//权限校验，用户未登陆
        			if(data.b == '-100002' && data.e == "-10000204")
            		{
        				QM.dialog.showFailedDialog(data.c,function(){location.href = GLOBAL_INFO.CONTEXTPATH + "/login.jsp"});
            		}
        			else if(data.b == '-100002')
            		{
        				if(data.c){
							QM.dialog.showFailedDialog(data.c);
						}
            		}
        			data.rows = 0;
            	}
				if(data.d)
				{
					return data.d;
				}
				return data;
			}
		};
		$.extend(this.attrs, _attrs, attrs);
		this.qFn = qFn;
		//分页标准配置
		this.PAGE_TEMPLATE = {  
		     pageSize: 50,//每页显示的记录条数，默认为100  
		     pageList: [50, 100, 200, 500, 1000],//可以设置每页记录条数的列表  
		     beforePageText: '第',//页数文本框前显示的汉字  
		     afterPageText: '页    共 {pages} 页',  
		     displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		};
		$.extend(this.PAGE_TEMPLATE, this.PAGE_TEMPLATE, pageTemplate);
	};
	
	QM.dataGrid.prototype = {
		init : function()
		{
			this.initToolbar();
			$("#" + this.id).datagrid(this.attrs);
			if(this.attrs.pagination)
			{
				var p = $('#' + this.id).datagrid('getPager');
			    $(p).pagination(this.PAGE_TEMPLATE);
			}
		},
		
		initToolbar : function()
		{
			var _toolbar = [];
			if(this.toolbar && this.toolbar.btns)
			{
				var btnsLen = this.toolbar.btns.length;
				for(var i = 0; i < btnsLen; i ++)
				{
					_toolbar.push(this.createToolbarBtn(this.toolbar.btns[i]));
				}
			}
			
			if(this.toolbar && this.toolbar.newBtns )
			{
				var newBtnsLen = this.toolbar.newBtns.length;
				var order = 0;
				for(var i = 0; i < newBtnsLen; i ++)
				{
					order = this.toolbar.newBtns[i].order ? (parseInt(this.toolbar.newBtns[i].order) - 1) : -1;
					_toolbar = _toolbar.insert(order, this.toolbar.newBtns[i]);
				}
			}

			if(_toolbar.length > 0)
			{
				this.attrs.toolbar = _toolbar;
			}
		},
		
		formQry : function()
		{
			this.queryParam = this.qFn ? this.qFn() : {};
			$('#' + this.id).datagrid('load', this.queryParam);
		},
		
		reload : function()
		{
			$('#' + this.id).datagrid('reload');
			$('#' + this.id).datagrid('clearSelections');
		},
		
		createToolbarBtn : function(btn)
		{
			if(btn == "ADD")
			{
				var options = {"title":"新增"};
				if(this.toolbar.btns.options && this.toolbar.btns.options.ADD)
				{
					$.extend(options, options, this.toolbar.btns.options.ADD ? this.toolbar.btns.options.ADD : {});
				}
				return {
					id : "btnAdd",
					text : options.title,
					iconCls : 'icon-add',
					handler : function() {
						top.QM.dialog.winCallback = null;
						if(this.toolbar.btnCallbacks && this.toolbar.btnCallbacks["ADD"])
						{
							top.QM.dialog.winCallback = this.toolbar.btnCallbacks["ADD"];
						}
						else
						{
							top.QM.dialog.winCallback = function(result)
							{
								if(result && result.a == GLOBAL_INFO.SUCCESS_CODE)
								{
									top.QM.dialog.showSuccessDialog("添加成功.");
									this.formQry();
								}
								else
								{
									top.QM.dialog.showFailedDialog("添加失败.");
								}
							}.bind(this);
						}
						top.QM.dialog.openWin(options, this.toolbar.urls["ADD"]);
					}.bind(this)
				};
			}
			else if(btn == "EDIT")
			{
				var options = {"title":"编辑"};
				if(this.toolbar.btns.options && this.toolbar.btns.options.EDIT)
				{
					$.extend(options, options, this.toolbar.btns.options.EDIT ? this.toolbar.btns.options.EDIT : {});
				}
				return {
					id : "btnEdit",
					text : options.title,
					iconCls : 'icon-edit',
					handler : function() {
						var editUrl = this.toolbar.urls["EDIT"];
						var selRows = $('#' + this.id).datagrid("getSelections");
							
						if(!selRows || selRows.length != 1)
						{	
							top.QM.dialog.showFailedDialog("请选择要修改的记录，只能选取单行修改！");
							return ;
						}
						var param = this.toolbar.getPKConds(selRows[0]).queryStr;
						if(editUrl){
							if(editUrl.indexOf('?') != -1){
								editUrl = editUrl + "&" + param;
							}else{
								editUrl = editUrl + "?" + param;
							}
						}
						top.QM.dialog.winCallback = null;
						if(this.toolbar.btnCallbacks && this.toolbar.btnCallbacks["EDIT"])
						{
							top.QM.dialog.winCallback = this.toolbar.btnCallbacks["EDIT"];
						}
						else
						{
							top.QM.dialog.winCallback = function(result)
							{
								if(result && result.a == GLOBAL_INFO.SUCCESS_CODE)
								{
									top.QM.dialog.showSuccessDialog("编辑成功.");
									this.reload();
								}
								else
								{
									top.QM.dialog.showFailedDialog("编辑失败.");
								}
							}.bind(this);
						}
						top.QM.dialog.openWin(options, editUrl);
					}.bind(this)
				};
			}
			else if(btn == "VIEW")
			{
				return {
					id : "btnView",
					text : '详情',
					iconCls : 'icon-search',
					handler : function(){alert('VIEW')}
				};
			}
			else if(btn == "DELETE")
			{
				return {
					id : "btnDel",
					text : '删除',
					iconCls : 'icon-no',
					handler : function(){
						var deleteParam = this.toolbar.urls["DELETE"];
						var selRows = $('#' + this.id).datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							top.QM.dialog.showFailedDialog("请选择要删除的记录，只能选取单行删除！");
							return ;
						}
						top.QM.dialog.showConfirmDialog("是否确定删除?", function(flg) {
							if(flg)
							{
								var param = this.toolbar.getPKConds(selRows[0]).queryJson;
								$.extend(param, param, deleteParam);
								new QM.ajax().singleReq({
						            data : param,
						            success : function(ret)
						            {
						                if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
						                {
						                	top.QM.dialog.showSuccessDialog("删除成功!");
						                	this.reload();
						                }
						                else
						                {
						                	if(ret.b && ret.c != null && ret.c != ""){
						                		top.QM.dialog.showFailedDialog(ret.c);
						                	}else{
						                		top.QM.dialog.showFailedDialog("删除失败!");
						                	}
						                }
						            }.bind(this)
						        });
							}
						}.bind(this));
					}.bind(this)
				};
			}
		}
	};
})(jQuery);

//让window居中
if($.fn.window && $.fn.window.defaults)
{
	var easyuiPanelOnOpen = function (left, top) {
	    var iframeWidth = $(this).parent().parent().width();
	   
	    var iframeHeight = $(this).parent().parent().height();

	    var windowWidth = $(this).parent().width();
	    var windowHeight = $(this).parent().height();

	    var setWidth = (iframeWidth - windowWidth) / 2;
	    var setHeight = (iframeHeight - windowHeight) / 2;
	    $(this).parent().css({/* 修正面板位置 */
	        left: setWidth,
	        top: setHeight
	    });

	    if (iframeHeight < windowHeight)
	    {
	        $(this).parent().css({/* 修正面板位置 */
	            left: setWidth,
	            top: 0
	        });
	    }
	    $(".window-shadow").hide();
	};
	$.fn.window.defaults.onOpen = easyuiPanelOnOpen;
}

/*查询条件 点击收缩展开效果*/
var flag = true;
function resizeNorth(){
	if (flag){
		$(".keySearch").hide();
		$(".shrink").css('background-position','center -168px');
		$('#filter').layout('panel', 'north').panel('resize',{height:23});
		$('#filter').layout('resize');
		flag = false;
	}else{
		$(".keySearch").show();
		$(".shrink").css('background-position','center -142px');
		$('#filter').layout('panel', 'north').panel('resize',{height:$(".searchColumn").height()+24});
		$('#filter').layout('resize');
		flag = true;	
	}
}

/*IE 鼠标点击链接时，取消虚线框*/
$(document).ready(function() {
	$("a,button").focus(function(){this.blur()});
});
