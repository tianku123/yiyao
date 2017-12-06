   /**
    * <p>Title: 系统日历</p>
    * <p>Description: 从服务器获取时间并显示公历、农历、星期的功能 （防止客户端修改时间显示错误）</p>
    * <p>Copyright: Copyright (c) 2005</p>
    * <p>Company: </p>
    * @authory tongzy
    * @version 1.0
	* @notice 
    */

	var lunarInfo=new Array(
		0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2, 
		0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
		0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
		0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
		0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,
		0x06ca0,0x0b550,0x15355,0x04da0,0x0a5d0,0x14573,0x052d0,0x0a9a8,0x0e950,0x06aa0,
		0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,
		0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b5a0,0x195a6,
		0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,
		0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,
		0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,
		0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,
		0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
		0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,
		0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0)

	var Animals=new Array("鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪");
	var Gan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸");
	var Zhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥");
	var now = new Date();

	var SY = 1900
	var SM = 0
	var SD = 31
	var SW = 0


	//==== 传入 offset 传回干支, 0=甲子
	function cyclical(num) { 
		return(Gan[num%10]+Zhi[num%12])
	}

	//==== 传回农历 y年的总天数
	function lYearDays(y) {
		var i, sum = 348
		for(i=0x8000; i>0x8; i>>=1) sum += (lunarInfo[y-1900] & i)? 1: 0
		return(sum+leapDays(y))
	}


	//==== 传回农历 y年闰月的天数
	function leapDays(y) {
		if(leapMonth(y))  return((lunarInfo[y-1900] & 0x10000)? 30: 29)
		else return(0)
	}


	//==== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	function leapMonth(y) { 
		return(lunarInfo[y-1900] & 0xf)
	}


	//====传回农历 y年m月的总天数
	function monthDays(y,m) { 
		return( (lunarInfo[y-1900] & (0x10000>>m))? 30: 29 )
	}


	//==== 算出农历, 传入日期物件, 传回农历日期物件
	//
	function Lunar(objDate) {
		var i, leap=0, temp=0
		var baseDate = new Date(1900,0,31)
		var offset = (objDate - baseDate)/86400000

		this.dayCyl = offset + 40
		this.monCyl = 14

		for(i=1900; i<2050 && offset>0; i++) {
			temp = lYearDays(i)
			offset -= temp
			this.monCyl += 12
		}

		if(offset<0) {
			offset += temp;
			i--;
			this.monCyl -= 12
		}

		this.year = i
		this.yearCyl = i-1864

         //闰哪个月
		leap = leapMonth(i) 
		this.isLeap = false

		for(i=1; i<13 && offset>0; i++) {
			//闰月
			if(leap>0 && i==(leap+1) && this.isLeap==false)
			{ --i; this.isLeap = true; temp = leapDays(this.year); }
			else
			{ temp = monthDays(this.year, i); }

			 //解除闰月
			if(this.isLeap==true && i==(leap+1)) this.isLeap = false
			offset -= temp
			if(this.isLeap == false) this.monCyl ++
		}

		if(offset==0 && leap>0 && i==leap+1)
			if(this.isLeap)
				{ this.isLeap = false; }
			else
				{ this.isLeap = true; --i; --this.monCyl;}

	   if(offset<0){ offset += temp; --i; --this.monCyl; }

	   this.month = i
       this.day = offset + 1
	}



	function YYMMDD(){
        var StrSM="";
        var StrSD="";
		if((SM+1)<10){
		   StrSM="0" + (SM+1);
		}else{
		   StrSM=SM+1;
		}

		if(SD<10){
		   StrSD="0" + SD;
		}else{
		   StrSD=SD;
		}


		return(SY+'年'+StrSM+'月'+StrSD+'日'); 
	}

   function weekday(){ 
		var StrSW="";
		var day = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        if((SW==0)||(SW==6)){

			if(SW==0){
               StrSW="<font COLOR=#FF0000>" + day[SW] + "</font>"
			}else{
               StrSW="<font COLOR=#FF0000>" + day[SW] + "</font>"
			}

		}else{
		   StrSW =day[SW]
		}
		return(StrSW); 
   }


	//==== 中文日期
	function cDay(m,d){
		var nStr1 = new Array('日','一','二','三','四','五','六','七','八','九','十');
		var nStr2 = new Array('初','十','廿','卅','　');
		var s;
		if (m>10){s = '十'+nStr1[m-10]} else {s = nStr1[m]} s += '月'
		switch (d) {
			case 10:s += '初十'; break;
			case 20:s += '二十'; break;
			case 30:s += '三十'; break;
			default:s += nStr2[Math.floor(d/10)]; s += nStr1[d%10];
		}
 	 return(s);
	}


	function GLCalendar(){
		return(YYMMDD()+'&nbsp;'+weekday());
	}

	function YLCalendar(){
		var sDObj = new Date(SY,SM,SD);
		var lDObj = new Lunar(sDObj);
		var nSY=SY;
		var nSM=lDObj.month;
		//if(SM<nSM){nSY=nSY-1;}
		//if(SM<=1){nSY=nSY+1;}

		var tt = cyclical(nSY-1900+36)+'年'+' ('+Animals[(nSY-4)%12]+') '+""+cDay(lDObj.month,lDObj.day);
		return(tt);
	}

	function getCalendar(setSY,setSM,setSD,setSW){
		SY=setSY;
		SM=setSM;
		SD=setSD;
		SW=setSW;
		return('今日：'+GLCalendar()+' 农历：'+YLCalendar());
	}

    //返回阳历日期
	function getYLCalendar(setSY,setSM,setSD,setSW){
	    var myDate=new Date();
	    var curyear=myDate.getFullYear();
	    var curmonth=myDate.getMonth()+1;
	    var curday=myDate.getDate();
	    var curweek=myDate.getDay();
	    
		if(setSY==undefined || setSY=='undefined'|| setSY==''){
			SY=curyear;
			SM=curmonth;
			SD=curday;
			SW=curweek;	
		}else{
			SY=setSY;
			SM=setSM;
			SD=setSD;
			SW=setSW;	
		}
		return('&nbsp;&nbsp;今日：'+ GLCalendar());
	}

	//返回农历日期
	function getNLCalendar(setSY,setSM,setSD,setSW){
	    var myDate=new Date();
	    var curyear=myDate.getFullYear();
	    var curmonth=myDate.getMonth();
	    var curday=myDate.getDate();
	    var curweek=myDate.getDay();
	    
	    if(setSY==undefined || setSY=='undefined'|| setSY==''){
			SY=curyear;
			SM=curmonth;
			SD=curday;
			SW=curweek;	
		}else{
			SY=setSY;
			SM=setSM;
			SD=setSD;
			SW=setSW;	
		}
		return('农历：'+YLCalendar());
	}


	function getUserCalendar(setSY,setSM,setSD,setSW){
		SY=setSY;
		SM=setSM;
		SD=setSD;
		SW=setSW;
		document.write(GLCalendar());
		document.write('<br>');
		document.write(YLCalendar());
	}