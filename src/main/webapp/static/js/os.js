
/**
* @author hechen
*/
var gs = {
  /**获得屏幕宽度**/
  ScreenWidth: function () {
    return window.screen.width;
  },
  /***获得屏幕高度**/
  ScreenHeight: function () {
    return window.screen.height;
  },
  /**获得浏览器***/
  Browse: function () {
    var browser = {};
    var userAgent = navigator.userAgent.toLowerCase();
    var s;
    (s = userAgent.match(/msie ([\d.]+)/)) ? browser.ie = s[1] : (s = userAgent.match(/firefox\/([\d.]+)/)) ? browser.firefox = s[1] : (s = userAgent.match(/chrome\/([\d.]+)/)) ? browser.chrome = s[1] : (s = userAgent.match(/opera.([\d.]+)/)) ? browser.opera = s[1] : (s = userAgent.match(/version\/([\d.]+).*safari/)) ? browser.safari = s[1] : 0;
    var version = "";
    if (browser.ie) {
      version = 'IE ' + browser.ie;
    }
    else {
      if (browser.firefox) {
        version = 'firefox ' + browser.firefox;
      }
      else {
        if (browser.chrome) {
          version = 'chrome ' + browser.chrome;
        }
        else {
          if (browser.opera) {
            version = 'opera ' + browser.opera;
          }
          else {
            if (browser.safari) {
              version = 'safari ' + browser.safari;
            }
            else {
              version = '未知浏览器';
            }
          }
        }
      }
    }
    return version;
  },
  /**获得操作系统***/
  ClientOs: function () {
    var sUserAgent = navigator.userAgent;
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
    var isiPhone = (navigator.platform == "iPhone");
    if (isMac)
      return "Mac";
    if (isiPhone)
    	return "iPhone";
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
    if (isUnix)
      return "Unix";
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
    if (isLinux)
      return "Linux";
    if (isWin) {
      var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
      if (isWin2K)
        return "Win2000";
      var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
      if (isWinXP)
        return "WinXP";
      var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
      if (isWin2003)
        return "Win2003";
      var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
      if (isWinVista)
        return "WinVista";
      var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
      if (isWin7)
        return "Win7";
      return "Win"
    }
    return "other";
  },
  /****获得客户端ID****/
  ClientID: function (name) {
    var cookies = document.cookie.split(";");
    var value = "";
    for (var i = 0; i < cookies.length; i++) {
      var temp = cookies[i].split("=");
      var tempValue = temp[0].replace(/(^\s*)|(\s*$)/g, "");
      if (tempValue == name) {
        value = unescape(temp[1]);
        break;
      }
    }
    return value;
  },
  /****获得商品ID****/
  SearchGid: function () {
    var url = window.location.href;
    var value = "";
    if (url != undefined && url != "") {
      url.replace(/-\d+-/, function (a, b, c) {
        if (a != undefined && a != "" && a.length > 2) {
          value = a.substring(1, a.length - 1);
        }
      });
    }
    return value;
  },
  /****获得站内搜索关键字****/
  SearchKey: function () {
    var url = window.location.href;
    var value = "";
    if (url != undefined && url != "") {
      url.replace(/[=].*/, function (a, b, c) {
        if (a != undefined && a != "" && a.length > 1) {
          value = a.substring(1);
        }
      });
    }
    return value;
  },
  /**获得商品分类搜索编号**/
  SearchCategory: function () {
    var url = window.location.href;
    var value = "";
    if (url != undefined && url != "") {
      url.replace(/-\d+/, function (a, b, c) {
        if (a != undefined && a != "" && a.length > 1) {
          value = a.substring(1);
        }
      });
    }
    return value;
  },
  /****站外搜索 Google 百度*****/
  SearchSite: function (type) {
    var referrer = document.referrer;
    if (referrer != undefined && referrer != "") {
      if ("google" == type) {
        url.replace(new RegExp("(?:^|/?|&)q=([^&]*)(?:&|$)"), function (a, b, c) {
          if (this._ch != undefined && a != undefined && a.length > 0) {
            a = a.substring(a.indexOf("=") + 1, a.length - 1);
            return a;
          }
        });
      } else if ("baidu" == type) {
        url.replace(new RegExp("(?:^|/?|&)wd=([^&]*)(?:&|$)"), function (a, b, c) {
          if (this._ch != undefined && a != undefined && a.length > 0) {
            a = a.substring(a.indexOf("=") + 1, a.length - 1);
            return a;
          }
        });
      } else if ("sogou" == type) {
        url.replace(new RegExp("(?:^|/?|&)query=([^&]*)(?:&|$)"), function (a, b, c) {
          if (this._ch != undefined && a != undefined && a.length > 0) {
            a = a.substring(a.indexOf("=") + 1, a.length - 1);
            return a;
          }
        });
      } else if ("bing" == type) {
        url.replace(new RegExp("(?:^|/?|&)q=([^&]*)(?:&|$)"), function (a, b, c) {
          if (this._ch != undefined && a != undefined && a.length > 0) {
            a = a.substring(a.indexOf("=") + 1, a.length - 1);
            return a;
          }
        });
      }
    }
    return "";
  },
  /******将值插入到数组中*********/
  PushValue: function (key, value) {
    if (this._ch != undefined) {
      this._ch[key] = value;
    }
  },
  /***自定义参数**/
  Param: function (value) {
    if (this._ch != undefined) {
      this._ch["_ps"] = value;
    }
  },
  /******提交数据到后台**********/
  PostData: function () {
    $.ajax({
      url: "http://localhost:28482/Ajax/StatActions.aspx?t=" + Math.random(),
      type: "post",
      data: this._ch,
      success: function (data) { }
    });
  },
  /***初始化**/
  Init: function () {
    if (this._ch != undefined) {
      this._ch = undefined;
    }
    this._ch = {};
    if (this._Reg != undefined) {
      this._Reg = undefined;
    }
    //初始化正则表达式 匹配URL

    this._Reg = new Array();
    this._Reg[0] = "http://www.google.com.hk(.*)";
    this._Reg[1] = "http://www.baidu.com(.*)";
    this._Reg[2] = "http://www.sogou.com(.*)";
    this._Reg[3] = "http://cn.bing.com(.*)";

    //分析URL参数
    var hostURL = window.location.href;
    if (hostURL != undefined && hostURL != "") {
      if ((new RegExp(this._Reg[4])).test(hostURL)) {
        this._ch["_gid"] = this.SearchGid();
      } else if ((new RegExp(this._Reg[7])).test(hostURL)) {

      } else if ((new RegExp(this._Reg[5])).test(hostURL)) {
        this._ch["_cid"] = this.SearchCategory();
      } else if ((new RegExp(this._Reg[6])).test(hostURL)) {
        this._ch["_key"] = this.SearchKey();
      }
      this._ch["_tu"] = hostURL;
    }
    var referrer = document.referrer;
    if (referrer != undefined && referrer != "") {
      if ((new RegExp(this._Reg[0])).test(referrer)) {
        this._ch["_key"] = this.SearchSite("google");
      } else if ((new RegExp(this._Reg[1])).test(referrer)) {
        this._ch["_key"] = this.SearchSite("baidu");
      } else if ((new RegExp(this._Reg[2])).test(referrer)) {
        this._ch["_key"] = this.SearchSite("sogou");
      } else if ((new RegExp(this._Reg[3])).test(referrer)) {
        this._ch["_key"] = this.SearchSite("bing");
      }
      this._ch["_su"] = referrer;
    }

    //获取客户端相关信息
    this._ch["_sw"] = this.ScreenWidth();
    this._ch["_sh"] = this.ScreenHeight();
    this._ch["_bw"] = this.Browse();
    this._ch["_cs"] = this.ClientOs();
    this._ch["_mid"] = this.ClientID("from_channelid");
    this._ch["_ctid"] = this.ClientID("ClientKey");

  },
  _ch: undefined,
  _Reg: undefined
}