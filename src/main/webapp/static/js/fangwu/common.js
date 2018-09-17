/**
 * [公共方法]
 * @author: 方武
 * @time: 2017年1月4日10:54:13
 */
(function() {

    //定义对象命名空间
	var QB = {};

    // 公共基础方法模块
	QB.util = {}; 

	/**
     * 链式调用所用对象
     * @class AjaxObj
     * @namespace Util
     */
    QB.util.AjaxObj = function() {
        var obj = {};

        obj.done = function(callback) {
            if (callback) this._done = callback;
            return this;
        };

        obj.fail = function(callback) {
            if (callback) this._fail = callback;
            return this;
        };

        obj.empty = function(callback) {
            if (callback) this._empty = callback;
            return this;
        };

        return obj;
    };

    /**
     * [post description]
     * @param  {[type]} url  [请求]
     * @param  {[type]} data [接口数据]
     * @param  {[type]} type [数据类型]
     * @return {[type]}      [description]
     */
    QB.util.post = function(url, data, type) {
        type = type || 'json';
        return $.ajax({
            url: url,
            data: data || {},
            dataType: type || 'json',
            type: 'POST'
        });
    };

    /**
     * 公用的post请求
     * @param  {[type]} url请求
     * @return {[type]}
     */
    QB.util.ajaxPost = function(url, param) {
        var obj = new QB.util.AjaxObj();

        QB.util.post(url, param)
            .done(function(data) {
                setTimeout(function() {
                    if (data) {
                        if (data.success === true) {
                            obj._done && obj._done(data);
                        } else {
                            // obj._fail && obj._fail(data.message);
                            // layer.open({
                            //     content: data
                            //     ,skin: 'msg',
                            //     style:'background:#FF4351'
                            //     ,time: 2 //2秒后自动关闭
                            // });
                        }
                    } else {
                        obj._done && obj._done(data);
                    }
                }, 0);
            })
            .fail(function(data) {
                setTimeout(function() {
                    obj._fail && obj._fail(data);
                }, 0);
            });
        return obj;
    };

    /**
     * 获取URL中的参数值
     * @param  {String} name 参数名
     * @return {String} 参数值，若没有该参数，则返回''
     */
    QB.util.getQueryString = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"),
            r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURI(r[2]);
        }
        return '';
    };

    window.QB = QB;
})(window)