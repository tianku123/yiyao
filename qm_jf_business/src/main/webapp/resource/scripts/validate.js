$.extend($.fn.validatebox.defaults.rules, {
	number: {
        validator: function(value){
            return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
        },
        message: '只能输入数字.'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少{0}个字符.'
    },
    length: { validator: function (value, param) {
        var len = $.trim(value).length;
        return len >= param[0] && len <= param[1];
    },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    phoneOrMobile : {
    	validator :function(value) {
    		return ((/^(13|15|18)\d{9}$/i.test(value))||(/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)));
    	},
    	message : "请输入正确格式的手机号或者如下格式的号码:020-88888888"
    },
    intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d{1,2})?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数 
        validator: function (value) {
            // return /^[+]?[1-9]+\d*$/i.test(value);
            return /^[1-9][\d]*$/.test(value);
        },
        message: '请输入大于0的正整数'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },
    chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
            // return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[
			// ]){1,12})+$/i.test(value);
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    num: {// 验证编码，只能是数字和字母
    	validator: function (value) {
            return /^[\da-zA-Z]+$/i.test(value);
        },
        message: '请输入数字或字母'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    date: {// 验证日期格式
        validator: function (value) {
            // 格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message: '请输入合适的日期格式'
    },
    yyyyMMdd: {// 验证日期格式
    	validator: function (value) {
    		// 格式yyyy-MM-dd
    		return /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))$/i.test(value);
    	},
    	message: '请输入合适的日期格式'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    }, 
    positive: {
        validator: function (value) {
            return /^[0-9]*[1-9][0-9]*$/i.test(value);
        },
        message: '请输入正整数！'
    },
    specialCharacter :{
    	  validator: function (value) {
    		  return /^[^`~!@#$^&*()=|{}\'\:\;\',\[\].<>\/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]+$/i.test(value);
    	  },
    	message :　'请勿输入特殊字符'
    },
    positiveIntOrFloat: {
    	// 验证正数
        validator: function (value) {
        	return /^\d+(\.{0,1}\d+){0,1}$/i.test(value);
        },
        message: '请输入正数'
    },
    positiveAndZero:{
    	// 验证正整数+0
        validator: function (value) {
        	return /^(0|[1-9]\d*)$/.test(value);
        },
        message: '请输入正数'
    },
    ten:{
    	// 验证正整数+0
    	validator: function (value) {
    		return /^[1-9]\d*0$/.test(value);
    	},
    	message: '请输入10的倍数'
    },
    checkEndTime:{
    	// 验证时间段，结束时间大于等于开始时间
        validator: function (endVal, param) {
        	var startVal = $("#" + param[0]).datebox("getValue");
        	return endVal >= startVal;
        },
        message: '结束时间必须大于等于开始时间'
    },
    checkEndDate:{
    	// 验证日期段，结束日期大于等于开始日期
        validator: function (endVal, param) {
        	var startVal = $("#" + param[0]).datebox("getValue");
        	return endVal >= startVal;
        },
        message: '结束日期必须大于等于开始日期'
    },
    url:{
    	// 验证url
        validator: function (value) {
        	var strRegex = "^(https|http|ftp|rtsp|mms)://"
        		+ "(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        		+ "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        		+ "|" // 允许IP和DOMAIN（域名）
        		+ "([0-9a-z_!~*'()-]+.)*" // 域名- www.
        		+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." // 二级域名
        		+ "[a-z]{2,6})" // first level domain- .com or .museum
        		+ "(:[0-9]{1,4})?" // 端口- :80
        		+ "((/?)|" // a slash isn't required if there is no file name
        		+ "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        	var re=new RegExp(strRegex);
        	//re.test()
	        if (re.test(value)){
	            return (true);
	        }else{
	            return (false);
	        }
        },
        message: '请输入合适的url格式'
    },
	integerZ: {// 验证大于等于50的整数 
		validator: function (value) {
			// return /^[+]?[1-9]+\d*$/i.test(value);
			if(/^[1-9][\d]*$/.test(value)){
				var a = parseInt(value);
				if(a>=50){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}

			
		},
		message: '请输入大于等于50的整数'
    }
});




$.extend($.fn.validatebox.methods, {  
	remove: function(jq, newposition){  
		return jq.each(function(){  
			$(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
		});  
	},
	reduce: function(jq, newposition){  
		return jq.each(function(){  
		   var opt = $(this).data().validatebox.options;
		   $(this).addClass("validatebox-text").validatebox(opt);
		});  
	}	
}); 







