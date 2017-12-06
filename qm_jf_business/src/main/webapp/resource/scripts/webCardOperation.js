//0正确
// 
//1无卡
// 
//2CRC校验错
// 
//3值溢出
// 
//4未验证密码
// 
//5奇偶校验错
// 
//6通讯出错
// 
//8错误的序列号
// 
//10验证密码失败
// 
//11接收的数据位错误
// 
//12接收的数据字节错误
// 
//14Transfer错误
// 
//15写失败
// 
//16加值失败
// 
//17减值失败
// 
//18读失败
// 
//19已写卡
// 
//-0x10PC与读写器通讯错误
// 
//-0x11通讯超时
// 
//-0x20打开通信口失败
// 
//-0x24串口已被占用
// 
//-0x30地址格式错误
// 
//-0x31该块数据不是值格式
// 
//-0x32长度错误
// 
//-0x40值操作失败
// 
//-0x50卡中的值不够减
var CardOperation = {
	UID : "",			//卡片id
	DeviceHandle : null,	//设备句柄
	KeyType : 0, //默认密码类型 0：A密码  1：B密码
	Password : "FFFFFFFFFFFF", //读写卡密码
	BlockNum : 0, //需要操作的块号(绝对地址，0~255)
	ErrorCode : 0,
	DefValue : "0000000000000000",//块默认值
	//读卡器初始化
	InitDevice : function() {
		this.ErrorCode = 0;
		this.CloseDevice();
		var st = CActiveXCtrl.OpenDevice();
		this.ErrorCode = CActiveXCtrl.lErrorCode;
		if((st == 0 || st < 0) && CActiveXCtrl.lErrorCode != 0)
		{
			return 0; 
		}
		else
		{
			this.DeviceHandle = st; //获取句柄
			return 1;
		}
	},
	//读卡
	ReadCard : function(blocknum) {
		this.ErrorCode = 0;
		CActiveXCtrl.RfReset(this.DeviceHandle,10);//射频复位
		this.ErrorCode = CActiveXCtrl.lErrorCode;
		if(CActiveXCtrl.lErrorCode == 0) 
		{
			var ret = CActiveXCtrl.RfCard(this.DeviceHandle,1);//寻卡
			this.ErrorCode = CActiveXCtrl.lErrorCode;
			if(CActiveXCtrl.lErrorCode == 0)
			{
				//this.UID = ret;
				var ret = CActiveXCtrl.RfAuthenticationKey(this.DeviceHandle,this.KeyType,blocknum,this.Password);//校验密码
				this.ErrorCode = CActiveXCtrl.lErrorCode;
				if(CActiveXCtrl.lErrorCode == 0)
				{
						var ret = CActiveXCtrl.RfRead(this.DeviceHandle,blocknum);//读取数据
						this.ErrorCode = CActiveXCtrl.lErrorCode;
						if(CActiveXCtrl.lErrorCode == 0)
						{
							var length = ret.substring(0,2);
							length  = parseInt(length,10);
							return ret.substring(2,length + 2);
						}
						else
						{
							return "读卡出错";
						}
				}
				else
				{
					return "请使用密码正确的卡进行操作";
				}
			}
			else 
			{
				return "请放卡";
			}
		}
		else 
		{
			return "请确认读卡器已经连接并没有其他网页以及程序正在使用";
		}
	},
	//写卡
	WriteCard : function(blocknum,data) {
		this.ErrorCode = 0;
		CActiveXCtrl.RfReset(this.DeviceHandle,10);//射频复位
		this.ErrorCode = CActiveXCtrl.lErrorCode;
		if(CActiveXCtrl.lErrorCode == 0) {
			var ret = CActiveXCtrl.RfCard(this.DeviceHandle,1);//寻卡
			this.ErrorCode = CActiveXCtrl.lErrorCode;
			if(CActiveXCtrl.lErrorCode == 0)
			{
				
					this.UID = ret;
					var ret = CActiveXCtrl.RfAuthenticationKey(this.DeviceHandle,this.KeyType,blocknum,this.Password);//校验密码
					this.ErrorCode = CActiveXCtrl.lErrorCode;
					if(CActiveXCtrl.lErrorCode == 0)
					{
					
						var ret = CActiveXCtrl.RfRead(this.DeviceHandle,blocknum);//读取数据
						this.ErrorCode = CActiveXCtrl.lErrorCode;
						if(CActiveXCtrl.lErrorCode == 0)
						{
							var length = ret.substring(0,2);
							length  = parseInt(length,10);
							var darkCode =  ret.substring(2,length + 2);
							if(darkCode!=this.DefValue.substring(0, length)) 
							{
								this.ErrorCode = 19;
								return "已经写过卡了";
							}
							else 
							{
								var length = data.length;
								if(length<10) {
									data = "0" + length + data;
								}else {
									data = length + data;
								}
								data += "0";
								var ret = CActiveXCtrl.RfWrite(this.DeviceHandle,blocknum,data);//写数据
								this.ErrorCode = CActiveXCtrl.lErrorCode;
								if(CActiveXCtrl.lErrorCode == 0)
								{
									if(CActiveXCtrl.RfHalt(this.DeviceHandle)==0)
									{	
										return ret;
									}
									else
									{
										this.ErrorCode = CActiveXCtrl.lErrorCode;
										return "终止卡失败";
									}
								}
								else
								{
									return "写卡出错,请重新写卡";
								}
							}
						}
						else
						{
							return "写卡出错,请重新写卡";
						}
					}
					else
					{
						return "请使用密码正确的卡进行操作";
					}
			}
			else 
			{
				return "请放卡";
			}
		}
		else 
		{
			return "请确认读卡器已经连接并没有其他网页以及程序正在使用";
		}
	},
	CloseDevice : function() {
		CActiveXCtrl.CloseDevice(this.DeviceHandle);//关闭读卡器
	}
}