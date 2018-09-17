var shineNo="";     
	function newMsg(no){
		if(no){
			shineNo = no;
		}
		setTimeout('flash_title()', 2000); //2秒之后调用一次
	}
        function flash_title(no) {
            //当窗口效果为最小化，或者没焦点状态下才闪动
            if (isMinStatus() || !window.focus) {
                newMsgCount(shineNo);
            } else {
                document.title = '智慧接种';//窗口没有消息的时候默认的title内容
                /*window.clearInterval();*/
            }
        }
        //消息提示
        var flag = false;
        function newMsgCount(no) {
        	shineNo = no;
            if (flag) {
                flag = false;
                if(shineNo){
                	document.title = '【    ' + shineNo + '    】';
                }else{
                	document.title = '【新消息】';
                }
            } else {
                flag = true;
                document.title = '【　　　】';
            }
            window.setTimeout('flash_title(' + no + ')', 380);
        }
        //判断窗口是否最小化
        //在Opera中还不能显示
        var isMin = false;
        function isMinStatus() {
            //除了Internet Explorer浏览器，其他主流浏览器均支持Window outerHeight 和outerWidth 属性
            if (window.outerWidth != undefined && window.outerHeight != undefined) {
                isMin = window.outerWidth <= 160 && window.outerHeight <= 27;
            } else {
                isMin = window.outerWidth <= 160 && window.outerHeight <= 27;
            }
            //除了Internet Explorer浏览器，其他主流浏览器均支持Window screenY 和screenX 属性
            if (window.screenY != undefined && window.screenX != undefined) {
                isMin = window.screenY < -30000 && window.screenX < -30000;//FF Chrome       
            } else {
                isMin = window.screenTop < -30000 && window.screenLeft < -30000;//IE
            }
            return isMin;
        }
 