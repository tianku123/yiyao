//StringBuffer
function StringBuffer() {
	this.content = new Array;
}
StringBuffer.prototype.append = function(str) {
	this.content.push(str);
}
StringBuffer.prototype.toString = function() {
	return this.content.join("");
}
StringBuffer.prototype.clear = function() {
	return this.content.length = 0;
}

// Map集合
var Map = function() {
	this.keys = new Array();
	this.data = new Object();
	this.put = function(key, value) {
		if (this.data[key] == null) {
			this.keys.push(key);
		}
		this.data[key] = value;
	};
	this.get = function(key) {
		return this.data[key];
	};
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};
	this.each = function(fn) {
		if (typeof fn != 'function') {
			return;
		}
		var len = this.keys.length;
		for (var i = 0; i < len; i++) {
			var k = this.keys[i];
			fn(k, this.data[k], i);
		}
	};
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};
	this.isEmpty = function() {
		return this.keys.length == 0;
	};
	this.size = function() {
		return this.keys.length;
	};
	this.toString = function() {
		var s = "{";
		for (var i = 0; i < this.keys.length; i++, s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
	this.clear = function() {
		this.keys.length = 0;
		this.data = new Object();
	};
};

// JSON格式化对象
var JSON = {
	$defined : function(obj) {
		return (obj != undefined);
	},

	encode : function(obj) {
		switch (typeof obj) {
		case 'string':
			return '"' + obj.replace(/[\x00-\x1f\\"]/g, JSON.$replaceChars)
					+ '"';
		case 'array':
			return '[' + String(obj.map(JSON.encode).filter(JSON.$defined))
					+ ']';
		case 'object':
			if (obj instanceof Array) {
				return '[' + String(obj.map(JSON.encode).filter(JSON.$defined))
						+ ']';
			} else {
				var string = [];
				for ( var key in obj) {
					var json = JSON.encode(obj[key]);
					if (json)
						string.push(JSON.encode(key) + ':' + json);
				}
				return '{' + String(string) + '}';
			}
		case 'number':
		case 'boolean':
			return String(obj);
		case false:
			return 'null';
		}
		return null;
	},

	$specialChars : {
		'\b' : '\\b',
		'\t' : '\\t',
		'\n' : '\\n',
		'\f' : '\\f',
		'\r' : '\\r',
		'"' : '\\"',
		'\\' : '\\\\'
	},

	$replaceChars : function(chr) {
		return JSON.$specialChars[chr] || '\\u00'
				+ Math.floor(chr.charCodeAt() / 16).toString(16)
				+ (chr.charCodeAt() % 16).toString(16);
	},

	decode : function(string, secure) {
		if (typeof string != 'string' || !string.length)
			return null;
		if (secure
				&& !(/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(string
						.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, '')))
			return null;
		return eval('(' + string + ')');
	}
};

Array.prototype.map = function(fn, bind) {
	var results = [];
	for (var i = 0, j = this.length; i < j; i++)
		results[i] = fn.call(bind, this[i], i, this);
	return results;
};

Array.prototype.filter = function(fn, bind) {
	var results = [];
	for (var i = 0, j = this.length; i < j; i++) {
		if (fn.call(bind, this[i], i, this))
			results.push(this[i]);
	}
	return results;
};

Array.prototype.insert = function(index, value) {
	if (index < 0 || index > this.length) {
		index = this.length;
	}
	var part1 = this.slice(0, index);
	var part2 = this.slice(index, this.length);
	part1.push(value);
	return part1.concat(part2);
};

Array.prototype.indexOf = function(item, from) {
	var len = this.length;
	for (var i = (from < 0) ? Math.max(0, len + from) : from || 0; i < len; i++) {
		if (this[i] === item)
			return i;
	}
	return -1;
};

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

Function.prototype.bind = function() {
	var __method = this;
	var args = Array.prototype.slice.call(arguments);
	var object = args.shift();
	return function() {
		return __method.apply(object, args.concat(Array.prototype.slice
				.call(arguments)));
	};
};

// 键盘事件
function getKeyCode(e) {
	var keyNum = 0;
	try {
		if (window.event) { // IE
			keyNum = e.keyCode;
		} else if (e.which) { // Netscape/Firefox/Opera
			keyNum = e.which;
		}

	} catch (ex) {
	}
	return keyNum;
}

// 回车事件
function enterEvent(e, fn) {
	if (getKeyCode(e) == 13) {
		eval(fn);
	}
}

// 屏蔽Form提交事件
function returnEvent(e) {
	var flag = true;
	try {
		if (window.event) { // IE
			e.returnValue = false;
		} else { // Netscape/Firefox/Opera
			if (e.which != 8) {
				e.preventDefault();
			}
		}
	} catch (ex) {
	}
}

// 去掉左右空格
function trimBlankFunc(str) {
	return str.replace(/^\s*|\s*$/g, "");
}

/**
 * 格式化日期 yyyy-MM-dd HH:mm:ss
 * 
 * @param dateStr
 * @returns {String}
 */
function formatDate14(dateStr) {
	var formatDate = "";
	if (dateStr == null || dateStr == "") {
		return "";
	}
	if (dateStr.length < 8) {
		return "";
	}
	formatDate += dateStr.substring(0, 4) + "-";
	formatDate += dateStr.substring(4, 6) + "-";
	formatDate += dateStr.substring(6, 8);
	if (dateStr.length >= 10) {
		formatDate += " " + dateStr.substring(8, 10);
	}
	if (dateStr.length >= 12) {
		formatDate += ":" + dateStr.substring(10, 12);
	}
	if (dateStr.length >= 14) {
		formatDate += ":" + dateStr.substring(12, 14);
	}

	return formatDate;
}

/**
 * 格式化日期 yyyy-MM-dd HH:mm:ss
 * 
 * @param dateStr
 * @returns {String}
 */
function formatDate8(dateStr) {
	var formatDate = "";
	if (dateStr == null || dateStr == "") {
		return "";
	}
	if (dateStr.length < 8) {
		return "";
	}
	formatDate += dateStr.substring(0, 4) + "-";
	formatDate += dateStr.substring(4, 6) + "-";
	formatDate += dateStr.substring(6, 8);

	return formatDate;
}

/**
 * 
 * 格式化金额成小数点后面2位
 * 
 */
function formatQMMoney(num) {
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num)) {
		num = "0";
	}
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10) {
		cents = "0" + cents;
	}
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	}
	return (((sign) ? '' : '-') + num + '.' + cents);
}

/**
 * EasyUI DataGrid根据字段动态合并单元格 这个是根据比对字段一列的值进行合并单元格，存在的问题是纵向合并单元格可能不一致
 * 
 * @param tableID
 *            要合并table的id
 * @param fldList
 *            要合并的列,用逗号分隔(例如："name,department,office");
 */
function MergeCells(tableID, fldList) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 0; i--) {
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			} else {
				CurValue = dg.datagrid("getRows")[row][fldName];
			}
			if (row == 0) {
				PerValue = CurValue;
				continue;
			}
			if (PerValue == CurValue) {
				span += 1;
			} else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index : index,
					field : fldName,
					rowspan : span,
					colspan : null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}

/**
 * EasyUI DataGrid根据字段动态合并单元格 这个是根据某个字段的规则进行合并单元，后续多少列也是根据此规则合并
 * 
 * @param tableID
 *            要合并table的id
 * @param fieldName
 *            指定合并规则的列字段
 * @param fldList
 *            要合并的列（应该包括规则字段fieldName）,用逗号分隔(例如："name,department,office");
 */
function MergeCells_RuleField(tableID, fieldName, fldList) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span = 1;
	var PerValue = "";
	var CurValue = "";
	for (row = 0; row <= RowCount; row++) {
		if (row == RowCount) {
			CurValue = "";
		} else {
			CurValue = dg.datagrid("getRows")[row][fieldName];
		}
		if (row == 0) {
			PerValue = CurValue;
			continue;
		}
		if (PerValue == CurValue) {
			span += 1;
		} else {
			var index = row - span;
			var length = Arr.length - 1;
			for (i = length; i >= 0; i--) {
				fldName = Arr[i];
				dg.datagrid('mergeCells', {
					index : index,
					field : fldName,
					rowspan : span,
					colspan : null
				});
			}
			span = 1;
			PerValue = CurValue;
		}
	}
}

/**
 * 订单类型转换函数
 * @param value
 * @returns {String}
 */
function fTax2Zh(value) {
	if (value == '0') {
		return "普通发票";
	} else if (value == '1') {
		return "高开(增值税)";
	} else if (value == '2') {
		return "高开(普通)";
	} else if (value == '3') {
		return "专用发票";
	}
	return "";
}

/**
 * 订单类型转换函数
 * @param value
 * @returns {String}
 */
function fState2Zh(value) {
	if(value=='0'){
		return "业务员未提交";
	}else if(value=='1'){
		return "<span style='color:red;'>财务未审核</span>";
	}else if(value=='2'){
		return "<span style='color:green;'>财务审核通过</span>";
	}else if(value=='3'){
		return "已发货";
	}else if(value=='4'){
		return "手动退单";
	}else if(value=='8'){
		return "超时自动退单";
	}else if(value=='9'){
		return "30分钟内未提交自动退单";
	}else if(value=='10'){
		return "政策报单未审核";
	}
	return "";
}

/**
 * 付款情况
 * @param value
 * @returns {String}
 */
function fPaymentState2Zh(value) {
	if(value=='0'){
		return "<span style='color:red;'>借款</span>";
	}else if(value=='1'){
		return "<span style='color:green;'>已付款</span>";
	}else if(value=='2'){
		return "<span style='color:green;'>已还款</span>";
	}else if(value=='3'){
		return "<span style='color:green;'>扫码付(信)</span>";
	}else if(value=='4'){
		return "<span style='color:green;'>扫码付(邮)</span>";
	}else if(value=='5'){
		return "<span style='color:green;'>直营借款</span>";
	}
	return "";
}

/**
 * 订单类型
 * @param value
 * @returns {String}
 */
function isPolicy2Zh(value) {
	if(value=='0'){
		return "否";
	}else if(value=='1'){
		return "是";
	}else if(value=='3'){
		return "直营";
	}
	return "";
}
