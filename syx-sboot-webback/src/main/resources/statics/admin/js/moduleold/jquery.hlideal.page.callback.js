define(function (require, exports, module) {
	var $ = require('jquery');

	//初始化对象
    function callBack() {
        this.version = "1.0.0";
        this.name = "callBack";
    }
    //定义基本对象
    var _c=new callBack();
    module.exports=_c;

});