var CardOperation = {
	KeyType : 0, //默认密码类型 0：A密码  1：B密码
	Password : "737166323031", //读写卡密码
	BlockNumA : 0, //需要操作的块号(绝对地址，0~255)
	SectorA : 0,//默认扇区
	SectorB : 1,//默认扇区
	ErrorCode : 0,
	BaudRate : 9600,//通信波特率
	BeepTime : 25,//读卡成功时蜂鸣器鸣响时常
	//读卡器初始化
	InitDevice : function() {
		this.CloseDevice();
		this.ErrorCode = MWRFATL.OpenReader(0,9600);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "初始化读卡器失败";
		}else{
			return 1;
		}
	},
	//读卡
	ReadCard : function(blocknum) {
		var dataA = "";
		var dataB = "";
		this.ErrorCode = MWRFATL.OpenCard(1);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "请放卡";
		}
		//0扇区
		this.ErrorCode = MWRFATL.RF_LoadKey(this.KeyType,this.SectorA,this.Password);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡器装载密码错误";
		}
		this.ErrorCode = MWRFATL.RF_Authentication(this.KeyType,this.SectorA);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡密码不正确";
		}
		this.ErrorCode = MWRFATL.MF_Read(1);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读取指定块数据时发生错误，读卡失败";
		}else{
			var readData = this.ErrorCode;
			this.ErrorCode = 0;
			var length = readData.substring(0,2);
			length  = parseInt(length,10);
			dataA = readData.substring(2,length + 2);
		}
		//1扇区
		this.ErrorCode = MWRFATL.RF_LoadKey(this.KeyType,this.SectorB,this.Password);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡器装载密码错误";
		}
		this.ErrorCode = MWRFATL.RF_Authentication(this.KeyType,this.SectorB);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡密码不正确";
		}
		this.ErrorCode = MWRFATL.MF_Read(5);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读取指定块数据时发生错误，读卡失败";
		}else{
			var readData = this.ErrorCode;
			this.ErrorCode = 0;
			MWRFATL.RF_Beep(this.BeepTime);
			var length = readData.substring(0,2);
			length  = parseInt(length,10);
			dataB = readData.substring(2,length + 2);
		}
		var relData = dataA + dataB;
		return this.toRealCode(relData);
	},
	//写卡
	WriteCard : function(blocknum,data) {
		var dataA = "";
		var dataB = "";
		this.ErrorCode = MWRFATL.OpenCard(1);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "请放卡";
		}
		var length = data.length;
		if(length > 15) {
			dataA = this.toPassCode(data.substring(0,15));
			dataB = this.toPassCode(data.substring(15,length));
		}else {
			dataA = this.toPassCode(data.substring(0,length));
		}
		//0扇区
		this.ErrorCode = MWRFATL.RF_LoadKey(this.KeyType,this.SectorA,this.Password);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡器装载密码错误";
		}
		this.ErrorCode = MWRFATL.RF_Authentication(this.KeyType,this.SectorA);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读卡密码不正确";
		}
		this.ErrorCode = MWRFATL.MF_Read(1);
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "读取指定块数据时发生错误，读卡失败";
		}else{
			var blockData = this.ErrorCode;
			var length = blockData.substring(0,2);
			if(length != "00") 
			{
				this.ErrorCode = -1;
				return "已经写过卡了";
			}
			dataA += "0";
			try{
				MWRFATL.MF_Write(1, dataA);
			}catch(e){
				this.ErrorCode = -1;
				return "写卡出错";
			}
		}
		if(dataB != ""){
			//1扇区
			this.ErrorCode = MWRFATL.RF_LoadKey(this.KeyType,this.SectorB,this.Password);
			if(this.ErrorCode == '' || this.ErrorCode < 0){
				this.ErrorCode = -1;
				return "读卡器装载密码错误";
			}
			this.ErrorCode = MWRFATL.RF_Authentication(this.KeyType,this.SectorB);
			if(this.ErrorCode == '' || this.ErrorCode < 0){
				this.ErrorCode = -1;
				return "读卡密码不正确";
			}
			this.ErrorCode = MWRFATL.MF_Read(5);
			if(this.ErrorCode == '' || this.ErrorCode < 0){
				this.ErrorCode = -1;
				return "读取指定块数据时发生错误，读卡失败";
			}else{
				dataB += "0";
				try{
					MWRFATL.MF_Write(5, dataB);
					MWRFATL.RF_Beep(this.BeepTime);
					this.ErrorCode = 0;
					return "";
				}catch(e){
					this.ErrorCode = -1;
					return "写卡出错";
				}
			}
		}else{
			this.ErrorCode = 0;
			MWRFATL.RF_Beep(this.BeepTime);
			return "";
		}
	},
	//关闭设备
	CloseDevice : function() {
		this.ErrorCode = MWRFATL.CloseReader();
		if(this.ErrorCode == '' || this.ErrorCode < 0){
			this.ErrorCode = -1;
			return "关闭设备失败";
		}
	},
	//转换成卡内数据
	toPassCode : function(data) {
		var returnCode = "";
		var stringLength = "";
        var length = data.length;
		 if ((length * 2) < 9)
        {
            stringLength = "0" + length * 2;
        }
        else
        {
            stringLength = "" + length * 2;
        }
		returnCode += stringLength;
        for (var i = 0; i < length; i++)
        {
            var ascCode = data.substring(i,i+1).charCodeAt();
            if (ascCode <= 57)
            {
                ascCode = ascCode - 48;
            }
            else if (ascCode <= 90)
            {
                ascCode = ascCode - 55;
            }
            else
            {
                ascCode = ascCode - 60;
            }
            if (ascCode <= 9)
            {
                returnCode += "0" + ascCode;
            }
            else
            {
                returnCode += "" + ascCode;
            }
        }
        return returnCode;
	},
	//转换成卡外加密数据
	toRealCode : function(data) {
		var length = data.length;
		var returnCode = "";
		for(var i = 0;i < length;i = i + 2){
			var num = data.substring(i , i + 2);
			var ascCode = parseInt(num , 10);
			if (ascCode <= 9)
            {
                ascCode = ascCode + 48;
            }
            else if (ascCode <= 35)
            {
                ascCode = ascCode + 55;
            }
            else
            {
                ascCode = ascCode + 60;
            }
			returnCode += String.fromCharCode(ascCode);
		}
		return returnCode;
	}
}