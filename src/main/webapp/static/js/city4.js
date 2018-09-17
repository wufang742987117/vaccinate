
//$(function() {
	
//
//	$("#test").on("click", function() {
//		alert(prev.val());
//		alert(city.val());
//		alert(area.val());
//		alert(country.val());
//	});

//});

(function($) {
	$.fn.citySelect = function(options,formId,Provincename,Cityname,Areaname) {
		
		var form1 = $(formId), prev = $('input[name=Provincename]', form1), city = $(
				'input[name=Cityname]', form1), area = $('input[name=Areaname]',
				form1), country = $('input[name=country]', form1);
		
		
		var defaults = {
			setId : [ '#Province', '#City', '#Area', '#Insurer' ], // 默认id
			stval : [ '请选择省份', '请选择城市', '请选择地区', '请填写具体地址' ], // 默认值
			czemt : 'i', // 存值元素
			inpvt : 'input[name^="cho"]', // 存值文本框
			intva : true
		// 初始化所有下拉默认确定
		}, opts = $.extend(defaults, options), _setId = opts.setId, _stval = opts.stval, _czemt = opts.czemt, _inpnt = opts.inpvt, len = _setId.length;
		$.fn.removelist = function(options) { // 清空
			var removdefa = {
				removeAll : false,
				thisindex : 0
			}, optremove = $.extend(removdefa, options);
			var $_removebox = $(this), $_listall = $('ul li', $_removebox), $_listfirst = $(
					'ul li:first', $_removebox), $_listsib = $('ul li:gt(0)',
					$_removebox), $_vala = $(_czemt, $_removebox), $_valb = $(
					_inpnt, $_removebox);
			if (optremove.removeAll) {
				$_listall.remove();
			} else {
				$_listsib.remove();
			}
			$_vala.text(_stval[optremove.thisindex]);
			$_valb.val(_stval[optremove.thisindex]);
			return this;
		};
		$.fn.appendlist = function(options) { // 添加
			var appdefa = {
				theindex : '0'
			}, optapp = $.extend(appdefa, options);
			var $_appendbox = $(this), $_listbox = $('ul', $_appendbox), appendArray = dsy.Items[optapp.theindex], appList = '';
			if (typeof (appendArray) == "undefined")
				return false; // 如果不存在当前对象返回false
			for (var i = 0; i < appendArray.length; i++) {
				appList += '<li><a href="javascript:void(0)" alt="'
						+ appendArray[i] + '">' + appendArray[i] + '</a></li>';
			}
			$_listbox.append(appList);
			appList = '';
		};
		function slide(str) { // 下拉事件
			var oID = $(str), oClass = 'active', oList = $('ul', oID), closed;
			oID.click(function() {
				$(this).toggleClass(oClass);
				oList.stop(true, true).slideToggle();
			});
			closed = function() {
				oID.removeClass(oClass);
				oList.stop(true, true).slideUp();
			}
			$("body").click(function(e) {
				if (!$(e.target).is(str + " *")) {
					closed();
				}
			});
		}
		;
		$.fn.liClick = function() {
			var indA, indB, indC, indD;
			$(formId).on('click', _setId[0] + " a", function() { // 省点击事件
				console.log(this);
				var _valA = $(this).attr('alt');
				indA = $(this).parent().index() - 1;
				var _emeltA = $(_setId[0]).find(_czemt);
				var _inputA = $(_setId[0]).find(_inpnt);
				_emeltA.text(_valA);
				_inputA.val(_valA);
				$(_setId[1]).removelist({
					thisindex : 1
				});
				$(_setId[1]).appendlist({
					theindex : '0_' + indA
				});
				$(_setId[2]).removelist({
					thisindex : 2
				});
				$(_setId[3]).removelist({
					thisindex : 3
				});
			});
			$(formId).on('click', _setId[1] + " a", function() { // 省点击事件
				console.log(this);
				var _valB = $(this).attr('alt');
				indB = $(this).parent().index() - 1;
				var _emeltB = $(_setId[1]).find(_czemt);
				var _inputB = $(_setId[1]).find(_inpnt);
				_emeltB.text(_valB);
				_inputB.val(_valB);
				$(_setId[2]).removelist({
					thisindex : 2
				});
				$(_setId[2]).appendlist({
					theindex : '0_' + indA + '_' + indB
				});
				$(_setId[3]).removelist({
					thisindex : 3
				});
			});
			$(formId).on('click', _setId[2] + " a", function() { // 省点击事件
				console.log(this);
				var _valC = $(this).attr('alt');
				var _emeltC = $(_setId[2]).find(_czemt);
				var _inputC = $(_setId[2]).find(_inpnt);
				_emeltC.text(_valC);
				_inputC.val(_valC);
			});
		};
		function show(obj) { // 大按钮事件
			$(obj).toggleClass('active').find('ul').slideToggle();
		}
		if (opts.intva) {
			for (var i = 0; i < len; i++) { // 初始化默认值所有值
				$(_setId[i]).removelist({
					thisindex : i
				});
				slide(_setId[i]);
			}
			;
		}
		$(_setId[0]).appendlist({
			theindex : '0'
		}); // 默认添加省
		$.fn.liClick();
	};
})(jQuery);