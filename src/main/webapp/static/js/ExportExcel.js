	var LODOP; //声明为全局变量   
	function SaveAsFile(filename){ 
		LODOP=getLodop();   
		LODOP.PRINT_INIT(""); 
		LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
		LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
		LODOP.ADD_PRINT_TABLE(100,20,document.getElementById("exportTable").offsetWidth,document.getElementById("exportTable").offsetHeight,document.documentElement.innerHTML); 
		LODOP.SET_SAVE_MODE("Orientation",2); //Excel文件的页面设置：横向打印   1-纵向,2-横向;
		LODOP.SET_SAVE_MODE("PaperSize",9);  //Excel文件的页面设置：纸张大小   9-对应A4
		LODOP.SET_SAVE_MODE("Zoom",90);       //Excel文件的页面设置：缩放比例
		LODOP.SET_SAVE_MODE("CenterHorizontally",true);//Excel文件的页面设置：页面水平居中
		LODOP.SET_SAVE_MODE("CenterVertically",true); //Excel文件的页面设置：页面垂直居中
//		LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到） 
		LODOP.SAVE_TO_FILE(filename + ".xlsx"); 
	};	 
	function OutToFileOneSheet(){ 
		LODOP=getLodop();   
		LODOP.PRINT_INIT(""); 
		LODOP.ADD_PRINT_TABLE(100,20,500,60,document.getElementById("div1").innerHTML); 
		LODOP.SET_SAVE_MODE("FILE_PROMPT",false); 
		if (LODOP.CVERSION) {
			LODOP.On_Return=function(TaskID,Value){if(Value) alert("导出成功！");};
			LODOP.SAVE_TO_FILE(document.getElementById("T1").value);
		} else if (LODOP.SAVE_TO_FILE(document.getElementById("T1").value)) alert("导出成功！");		 
	}; 
	function OutToFileMoreSheet(){ 
		LODOP=getLodop();   
		LODOP.PRINT_INIT(""); 
		LODOP.ADD_PRINT_TABLE(100,20,500,60,document.documentElement.innerHTML); 
		LODOP.SET_SAVE_MODE("PAGE_TYPE",2); 
		LODOP.SET_SAVE_MODE("CenterHeader","页眉"); //Excel文件的页面设置
		LODOP.SET_SAVE_MODE("CenterFooter","第&P页"); //Excel文件的页面设置
		LODOP.SET_SAVE_MODE("Caption","我的标题栏");//Excel文件的页面设置					 
		LODOP.SET_SAVE_MODE("RETURN_FILE_NAME",1); 
		if (LODOP.CVERSION) {
			LODOP.On_Return=function(TaskID,Value){document.getElementById("T2").value=Value;};
			LODOP.SAVE_TO_FILE("多个Sheet的文件.xls");
			document.getElementById("T2").value="请等待结果...";
		} else document.getElementById("T2").value=LODOP.SAVE_TO_FILE("多个Sheet的文件.xls");		 
	};	
	function SaveAsEmfFile(){ 
		LODOP=getLodop();   
		LODOP.PRINT_INIT(""); 
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.documentElement.innerHTML); 
		LODOP.SET_SAVE_MODE("SAVEAS_IMGFILE_EXENAME",".emf");
		LODOP.SAVE_TO_FILE("新的矢量图片文件.emf"); 
	};	
