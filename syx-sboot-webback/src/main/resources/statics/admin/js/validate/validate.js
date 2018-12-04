$.validator.addMethod("tel", function(value, element) {
	var score = /^1[0-9]{10}$/;
	return this.optional(element) || (score.test(value));
}, "手机号码格式错误");
$.validator.addMethod("password", function(value, element) {
	var score = /^.{1,16}$/;
	return (this.optional(element) || (score.test(value)));
}, "1-16位数字，英文和特殊字符");
$.validator.addMethod("seniorPwd", function(value, element) {
	var score = /^.{6,16}$/;
	var number = /^\d+$/;
	var Dletter = /^[A-Z]+$/;
	var Xletter = /^[a-z]+$/;
	return (this.optional(element) || (score.test(value)))
			&& !number.test(value) && !Dletter.test(value)
			&& !Xletter.test(value);
}, "6-16位数字，英文和特殊字符，至少包含2种");
$.validator.addMethod("userName", function(value, element) {
	var userName = /^[0-9a-zA-Z_]{4,30}$/;
	var email = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	return this.optional(element) || (email.test(value))
			|| (userName.test(value));
}, "4-16位数字英文下划线组成，或手机号或邮箱");

$.validator.addMethod("idCard", function(value, element) {
	var score = /^\d{14}\d{3}[Xx0-9]$/;
	return (this.optional(element) || (score.test(value)))
			&& IdentityCodeValid(value);
}, "身份证号码格式错误");

$.validator.addMethod("qq", function(value, element) {
	var score = /^[1-9]*[1-9][0-9]*$/;
	return (this.optional(element) || (score.test(value)));
}, "腾讯QQ号码格式错误");

$.validator.addMethod("cqrcb", function(value, element) {
	var captcha = /^[0-9a-zA-Z]{6}$/;
	return this.optional(element) || (captcha.test(value));
}, "6位英文和数字");
$.validator.addMethod("captcha", function(value, element) {
	var captcha = /^[0-9]{4}$/;
	return this.optional(element) || (captcha.test(value));
}, "4位数字");

$.validator.addMethod("bankCard", function(value, element) {
	return this.optional(element) || (luhmCheck(value));
}, "银行卡号格式错误");
$.validator.addMethod("chinese", function(value, element) {
	var captcha = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
	return this.optional(element) || (captcha.test(value));
}, "中文格式错误");
// 下拉框验证
$.validator.addMethod("selectNone", function(value, element) {
	return value == "请选择";
}, "必须选择一项");

$.validator.setDefaults({
	debug : false,
	errorPlacement : function(error, element) {
		var _parent = element.parent().find(".validate-msg");
		_parent.show();
		_parent.html("");
		error.attr("class", "auth-error");
		error.appendTo(_parent);
	},
	success : function(label) {
		var _parent = label.parent();
		_parent.html("");
		_parent.hide();
	},
	ignore : "",
	errorClass : "auth-input-error",
	errorElement : "sup",
	validClass : "auth-success",
	submitHandler : function(editform) {
		return false;
	}
});
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		$(this.serializeArray()).each(function() {
			serializeObj[this.name] = this.value;
		});
		return serializeObj;
	};
})(jQuery);
// Create Time: 07/28/2011
// Operator: 刘政伟
// Description: 银行卡号Luhm校验

// Luhm校验规则：16位银行卡号（19位通用）:

// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
// 3.将加法和加上校验位能被 10 整除。

// 方法步骤很清晰，易理解，需要在页面引用Jquery.js

// bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
function luhmCheck(bankno) {
	var lastNum = bankno.substr(bankno.length - 1, 1);// 取出最后一位（与luhm进行比较）

	var first15Num = bankno.substr(0, bankno.length - 1);// 前15或18位
	var newArr = new Array();
	for (var i = first15Num.length - 1; i > -1; i--) { // 前15或18位倒序存进数组
		newArr.push(first15Num.substr(i, 1));
	}
	var arrJiShu = new Array(); // 奇数位*2的积 <9
	var arrJiShu2 = new Array(); // 奇数位*2的积 >9

	var arrOuShu = new Array(); // 偶数位数组
	for (var j = 0; j < newArr.length; j++) {
		if ((j + 1) % 2 == 1) {// 奇数位
			if (parseInt(newArr[j]) * 2 < 9)
				arrJiShu.push(parseInt(newArr[j]) * 2);
			else
				arrJiShu2.push(parseInt(newArr[j]) * 2);
		} else
			// 偶数位
			arrOuShu.push(newArr[j]);
	}

	var jishu_child1 = new Array();// 奇数位*2 >9 的分割之后的数组个位数
	var jishu_child2 = new Array();// 奇数位*2 >9 的分割之后的数组十位数
	for (var h = 0; h < arrJiShu2.length; h++) {
		jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
		jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
	}

	var sumJiShu = 0; // 奇数位*2 < 9 的数组之和
	var sumOuShu = 0; // 偶数位数组之和
	var sumJiShuChild1 = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
	var sumJiShuChild2 = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
	var sumTotal = 0;
	for (var m = 0; m < arrJiShu.length; m++) {
		sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
	}

	for (var n = 0; n < arrOuShu.length; n++) {
		sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
	}

	for (var p = 0; p < jishu_child1.length; p++) {
		sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
		sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
	}
	// 计算总和
	sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu)
			+ parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

	// 计算Luhm值
	var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
	var luhm = 10 - k;

	if (lastNum == luhm) {
		// $("#banknoInfo").html("Luhm验证通过");
		return true;
	} else {
		// $("#banknoInfo").html("银行卡号必须符合Luhm校验");
		return false;
	}
}

/*
 * 根据〖中华人民共和国国家标准 GB
 * 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。 出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
 * 顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
 * 校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
 * 
 * 出生日期计算方法。 15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
 * 2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗... 下面是正则表达式:
 * 出生日期1800-2099 (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01]) 身份证正则表达式
 * /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
 * 15位校验规则 6位地址编码+6位出生日期+3位顺序号 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位
 * 
 * 校验位规则 公式:∑(ai×Wi)(mod 11)……………………………………(1) 公式(1)中：
 * i----表示号码字符从由至左包括校验码在内的位置序号； ai----表示第i位置上的号码字符值；
 * Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。 i 18 17 16 15 14 13 12 11 10
 * 9 8 7 6 5 4 3 2 1 Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
 * 
 */
// 身份证号合法性验证
// 支持15位和18位身份证号
// 支持地址编码、出生日期、校验位验证
function IdentityCodeValid(code) {
	var city = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江 ",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北 ",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏 ",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外 "
	};
	var tip = "";
	var pass = true;

	if (!code
			|| !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X|x)$/i
					.test(code)) {
		tip = "身份证号格式错误";
		pass = false;
	}

	else if (!city[code.substr(0, 2)]) {
		tip = "地址编码错误";
		pass = false;
	} else {
		// 18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			// ∑(ai×Wi)(mod 11)
			// 加权因子
			var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			// 校验位
			var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				tip = "校验位错误";
				pass = false;
			}
		}
	}
	return pass;
}
//