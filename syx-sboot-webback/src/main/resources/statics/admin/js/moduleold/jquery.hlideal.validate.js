define(function (require, exports, module) {
	var $ = require('jquery');

	//构造函数
	function YValidatebox() {
		this.name = "YValidatebox";
		this.version = '1.0.0';
		this.text = "";
	}

	//初始化对象
	$.YValidatebox = new YValidatebox();

	var errorMap = {};
	errorMap['Require'] = '相关信息';
	errorMap['CHS'] = '中文';
	errorMap['Mobile'] = '手机号';
	errorMap['Phone'] = '电话';
	errorMap['PhoneOrMobile'] = '联系电话';
	errorMap['ZipCode'] = '邮政编码';
	errorMap['Number'] = '数字';
	errorMap['IDCard'] = '身份证';
	errorMap['IsUrl'] = '网址';
	errorMap['IntOrFloat'] = '数字';
	errorMap['QQ'] = 'QQ';
	errorMap['Age'] = '年龄';
	errorMap['Length'] = '长度';
	errorMap['Email'] = '邮箱';
	errorMap['Remote'] = '值';
	errorMap['Size'] = '数值大小';
	errorMap['MinSize'] = '最小值';
	errorMap['Amount'] = '为正的值';
	var methods = {
		/**
		 * 1、keyup事件
		 * 2、blur事件
		 * */
		bindBox: function () {
			//这里的this是input
			var that = this;
			that.unbind();
			var validateType = that.attr('validate-type');
			if (that.is("textarea") && validateType && validateType.indexOf('Length') != -1) {
				that.keyup(function () {
					checkObj(this);
				});
			} else {
				that.change(function () {
					checkObj(this);
				});
				that.blur(function () {
					if (that.hasClass('yui-date')) {
						var selfVal = $(this).val();
						//清除验证失败提示信息
						if (selfVal || selfVal.length > 0) {
							$.YUI.cleanValidateText($(this).parent().parent());
						}
					}
				});
			}
		},
		/**
		 * 验证单个表单项
		 * @param jq 如：input
		 * */
		validate: function (jq) {
			return checkObj(jq);
		},
		/**
		 * 验证所有表单项
		 * @param obj 如：form
		 * */
		validateAll: function (obj) {
			var checked = true;
			$(obj).find('[class*="yui-validatebox"]').each(function () {
				if (!checkObj(this)) {
					checked = false;
				}
			});
			return checked;
		},

		/**
		 * 初始化
		 * @param opts
		 * */
		init: function (opts) {
			var that = opts.target;
			$(that).unbind();
			$(that).change(function () {
				checkObj(this);
			});
		}
	};


	//定义插件
	$.fn.YValidatebox = function (options, param) {
		if (typeof options == 'string') {
			if (methods[options]) {
				return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
			} else {
				$.error('Method ' + options + ' does not exist on jQuery.pluginName');
				return this;
			}
		}

		options = options || {};
		var opts = $.extend({}, $.fn.YValidatebox.defaults, options);
		var validateType;
		var validataMsg;
		if (null == opts.type) {
			validateType = $(this).attr('validate-type');
			opts.type = validateType;
		}
		if (null == opts.errorMsg) {
			validataMsg = $(this).attr('validate-false');
			opts.errorMsg = validataMsg;
		}
		opts.target = $(this);
		return this.each(function () {
			methods.init(opts);
		});
	};
	$.fn.YValidatebox.defaults = {
		type: null,
		errorMsg: null,
		target: null
	};

	/**
	 * 验证表单项
	 * @param obj 如：input
	 * @return true|false
	 * */
	function checkObj(obj) {
		//获取验证类型和错误提示信息
		var validateType = $(obj).attr('validate-type');
		var validataMsg = $(obj).attr('validate-false');

		//如果验证类型为空，不需要验证return true
		if (typeof validateType == 'undefined' || '' == validateType || null == validateType) {
			return true;
		} else {
			var this_class = $(obj).attr('class');
			//获得input的value值
			var inputValue = $(obj).val();
			if (this_class.indexOf("combobox") > 0) {
				inputValue = $(obj).YCombobox("getValue");
			} else if (this_class.indexOf("combotree") > 0) {
				inputValue = $(obj).YComboTree("getValue");
			}
			//如果验证类型不是Require，且obj的标签名不是textarea
			if (validateType.indexOf("Require") < 0 && $(obj)[0].tagName != "TEXTAREA") {
				//inputValue的值为空，return true
				if (null == inputValue || "" == inputValue) {
					return true;
				}
			}
			//获取所有验证类型type，返回数组
			var validateTypeArray = getType(validateType);
			var flag = true;
			//对每个验证类型type进行验证
			for (var i = 0; i < validateTypeArray.length; i++) {
				var option = validateTypeArray[i];
				var endValue;
				var caseOption;
				//如果为Length[2-12]等验证类型
				//获得当前类型Length与endvalue[2-12]
				if (option.indexOf('[') != -1) {
					var strOption = option;
					endValue = strOption.substring(strOption.indexOf('['), strOption.indexOf(']') + 1);
					caseOption = strOption.substring(0, option.indexOf('['));
				} else {
					caseOption = option;
				}
				var returnFlag;
				//如果验证类型不为Romote，进行正则表达验证
				//反之，提交验证 不在进行二次验证 而是读取之前check的结果
				if ("Remote" != caseOption) {
					returnFlag = doValidate(inputValue, caseOption, endValue, obj);
				} else {
					returnFlag = $(obj).data("check");
					try {
						returnFlag.length;
					} catch (e) {
						returnFlag = true;
					}
				}
				if (!returnFlag) {
					flag = false;
					showErrorMsg(obj, caseOption, validataMsg);
					break;
				} else {
					showSuccessMsg(obj);
				}
			}
			return flag;
		}
	}

	/**
	 * 验证表单项
	 * @param obj 如：input
	 * @param status
	 * @return true|false
	 * */
	function checkValidate(obj, status) {
		var validateType = $(obj).attr('validate-type');
		var validataMsg = $(obj).attr('validate-false');
		if (typeof validateType == 'undefined' || validateType == '' || validateType == null) {
			return true;
		} else {
			var this_class = $(obj).attr('class');

			var inputValue = $(obj).val();
			if (this_class.indexOf("combobox") > 0) {
				inputValue = $(obj).YCombobox("getValue");
			} else if (this_class.indexOf("combotree") > 0) {
				inputValue = $(obj).YComboTree("getValue");
			}
			if ((!status && this_class.indexOf("saveCheck") > 0) || (status)) {
				//如果没有必填  且值为空则直接返回
				if (validateType.indexOf("Require") < 0) {
					if (null == inputValue || "" == inputValue) {
						return true;
					}
				}
				var validateTypeArray = getType(validateType);
				var flag = true;
				for (var i = 0; i < validateTypeArray.length; i++) {
					var option = validateTypeArray[i];

					var endValue;
					var caseOption;
					if (option.indexOf('[') != -1) {
						var strOption = option;
						endValue = strOption.substring(strOption.indexOf('['), strOption.indexOf(']') + 1);
						caseOption = strOption.substring(0, option.indexOf('['));
					} else {
						caseOption = option;
					}
					var returnFlag;
					//提交验证 不在进行二次验证 而是读取之前check的结果
					if ("Remote" != caseOption) {
						returnFlag = doValidate(inputValue, caseOption, endValue);
					} else {
						returnFlag = $(obj).data("check");
						try {
							returnFlag.length;
						} catch (e) {
							returnFlag = true;
						}

					}
					if (!returnFlag) {
						flag = false;
						showErrorMsg(obj, caseOption, validataMsg);
						break;
					} else {
						showSuccessMsg(obj);
					}
				}
			} else {
				clearMsg(obj);
				return true;
			}
			return flag;
		}
	}

	/**
	 * 对各种验证类型进行正则验证
	 * @param inputValue 当前输入值
	 * @param caseOption 如：Require|Length|Mobile等
	 * @param endValue  如：Length[2-3]中的[2-3]
	 * @param obj 如：input
	 * */
	function doValidate(inputValue, caseOption, endValue, obj) {
		if ((inputValue == null || inputValue.length == 0) && caseOption != "Require") {
			if (caseOption != "Length") {
				return true;
			}
		}
		var isPass = true;
		switch (caseOption) {
			case 'Require':
				var req = true;
				try {
					req = inputValue.length < 1;
				} catch (e) {
				}
				if (('' == inputValue || null == inputValue) && req) {
					isPass = false;
				}
				break;
			case 'CHS':
				var reg = /^[\u0391-\uFFE5]+$/;
				isPass = reg.test(inputValue);
				break;
			case 'Mobile':
				var reg = /^(13|15|18|14|17)\d{9}$/;
				isPass = reg.test(inputValue);
				break;
			case 'Phone':
				isPass = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(inputValue);
				break;
			case 'PhoneOrMobile':
				isPass = /^(13|15|18|14|17)\d{9}$/i.test(inputValue) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(inputValue);
				break;
			case 'ZipCode':
				isPass = /^[0-9]\d{5}$/.test(inputValue);
				break;
			case 'Number':
				isPass = /^[0-9]*$/.test(inputValue);
				break;
			case 'IsOverNumber':
				isPass = (/^(\d+\.\d{1,2}|\d+)$/).test(inputValue);
				break;
			case 'IsDecimal':
				isPass = (/^(([0-9]\d*)|\d)(\.\d{1,10})?$/).test(inputValue);
				break;
			case 'IDCard':
				isPass = isIdCardNo(inputValue);
				//isPass = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(inputValue);
				break;
			case 'IDCard18':
				isPass = '';
				break;
			case 'Letter':
				isPass = /^[a-z-A-Z\.\s]+$/i.test(inputValue);
				break;
			case 'IsUrl':
				isPass = /^(http:\/\/)?(www.)?(\w+\.)+\w{2,4}(\/)?$/i.test(inputValue);
				break;
			case 'IntOrFloat':
				isPass = /^[+-]?\d+(\.\d+)?$/.test(inputValue);
				break;
			case 'Currency':
				isPass = /^\d+(\.\d+)?$/i.test(inputValue);
				break;
			case 'QQ':
				isPass = /^[1-9]\d{4,9}$/i.test(inputValue);
				break;
			case 'Integer':
				isPass = /^[+]?[1-9]+\d*$/i.test(inputValue);
				break;
			case 'Age':
				isPass = /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(inputValue);
				break;
			case 'Length':
				endValue = endValue.replace("[", "");
				endValue = endValue.replace("]", "");
				var finalArray = endValue.split('-');
				if (inputValue.length < finalArray[0] || inputValue.length > finalArray[1]) {
					isPass = false;
				}
				break;
			case 'Size':
				endValue = endValue.replace("[", "");
				endValue = endValue.replace("]", "");
				var finalArray = endValue.split('-');
				if (parseFloat(inputValue) < parseFloat(finalArray[0]) || parseFloat(inputValue) > parseFloat(finalArray[1])) {
					isPass = false;
				}
				break;
			case 'Amount':
				isPass = /^\d+(\.\d+)?$/.test(inputValue);
				break;
			case 'MinSize' :
				endValue = endValue.replace("[", "");
				endValue = endValue.replace("]", "");
				isPass = inputValue >= endValue;
				break;
			case 'Email':
				isPass = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(inputValue);
				break;
			case 'Remote':
				endValue = endValue.replace("[", "");
				endValue = endValue.replace("]", "");
				endValue = endValue.replace(new RegExp("'", "gm"), "");
				var args = endValue.split(",");
				var url = args[0];
				var date = "";
				if (!args[1]) {
					isPass = false;
				} else {
					date += args[1] + "=" + inputValue;
					for (var i = 2; i < args.length; i++) {
						var _t = args[i];
						var prm = "";
						if (_t.indexOf("#") > 0) {
							var _a = _t.split("=");
							prm = _a[0] + $(_a[1]).val();
						} else {
							prm = args[i];
						}
						date += "&" + prm;
					}
					$.ajax({
						type: "GET",
						url: url,
						data: date,
						async: false,
						success: function (data) {
							if (data || data == "true") {
								isPass = true;
								showSuccessMsg(obj);
							} else {
								isPass = false;
								showErrorMsg(obj, caseOption, $(obj).attr('validate-false'));
							}
							$(obj).data("check", isPass);
						}
					});
				}
				break;
		}
		return isPass;
	}

	/**
	 * 对身份号码进行正则验证
	 * 在doValidate()中被调用
	 * @param num
	 * @return true|false
	 * */
	function isIdCardNo(num) {
		num = num.toUpperCase();
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			return false;
		}
		//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。

		//下面分别分析出生日期和校验位
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);
			//检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return false;
			} else {
				//将15位身份证转成18位

				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return num;
			}
		}

		if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);
			//检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return false;
			} else {
				//检验18位身份证的校验码是否正确。
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1)) {
					return false;
				}
				return num;
			}
		}
		return false;
	}

	/**
	 * 获取表单验证类型
	 * @param str 如："Require,Length[8-16]"
	 * @return
	 * */
	function getType(str) {
		var array = [];
		if (str.indexOf('[') != -1) {
			var reg = new RegExp(",[A-Z]", "g");
			var _list = str.match(reg);
			if (_list) {
				for (var i = 0; i < _list.length; i++) {
					var _v = str.substr(0, str.indexOf(_list[i]));
					array.push(_v);
					str = str.replace(_v, "");
					str = str.substr(1, str.length);
				}
				array.push(str);
			} else {
				array.push(str);
			}
		} else {
			array = str.split(',');
		}
		return array;
	}

	/**
	 * 显示失败信息
	 * @param obj 如：input
	 * @param caseOption 如：Require|Length|Size等
	 * @param str 错误提示信息
	 * */
	function showErrorMsg(obj, caseOption, str) {
		var this_class = $(obj).attr('class');
		var appendHtml = '<span class="form-error hlideal-validation" pop="reqFalse">';
		if (!str) {
			if ('Length' == caseOption) {
				appendHtml += showLengthErrorMsg(obj, caseOption);
			} else if ('Size' == caseOption) {
				appendHtml += showSizeErrorMsg(obj, caseOption);
			} else {
				appendHtml += '请输入正确的' + errorMap[caseOption];
			}
		} else {
			appendHtml += str;
		}
		appendHtml += '</div>';
		if (this_class.indexOf("combobox") > 0 || this_class.indexOf("combotree") > 0 || this_class.indexOf("checkbox") > 0) {
			//obj的父亲是dd.detail
			$(obj).parent().find('.hlideal-validation').remove();
			$(obj).parent().append(appendHtml);
		} else {
			//obj的父亲是lable，爷爷是dd.detail
			$(obj).parent().parent().find('.hlideal-validation').remove();
			$(obj).parent().after(appendHtml);
		}
	}

	/**
	 * 显示成功信息
	 * @param obj 如：input
	 * */
	function showSuccessMsg(obj) {
		var this_class = $(obj).attr('class');
		//var appendHtml='<span class="hlideal-validation form-success">验证成功</span>';
		if (this_class.indexOf("combobox") > 0 || this_class.indexOf("combotree") > 0) {
			$(obj).parent().find('.hlideal-validation').remove();
			//$(obj).parent().append(appendHtml);
		} else {
			$(obj).parent().parent().find('.hlideal-validation').remove();
			//$(obj).parent().after(appendHtml);
		}
	}

	/**
	 * 清除提示信息
	 * @param obj 如：input
	 * */
	function clearMsg(obj) {
		var this_class = $(obj).attr('class');
		if (this_class.indexOf("combobox") > 0 || this_class.indexOf("combotree") > 0) {
			$(obj).parent().find('.hlideal-validation').remove();
		} else {
			$(obj).parent().parent().find('.hlideal-validation').remove();
		}
	}


	/**
	 * 显示验证类型为Length的错误信息
	 * @param obj 如：input
	 * */
	function showLengthErrorMsg(obj) {
		var validateType = $(obj).attr('validate-type');
		var validateTypeArray = getType(validateType);
		for (var i = 0; i < validateTypeArray.length; i++) {
			var option = validateTypeArray[i];
			var endValue;
			if (option.indexOf('[') != -1) {
				var strOption = option;
				endValue = strOption.substring(option.indexOf('['), strOption.indexOf(']') + 1);
				return '长度应在：' + endValue + '之间';
			}
		}
	}

	/**
	 * 显示验证类型为Size的错误信息
	 * @param obj 如：input
	 * */
	function showSizeErrorMsg(obj) {
		var validateType = $(obj).attr('validate-type');
		var validateTypeArray = getType(validateType);
		for (var i = 0; i < validateTypeArray.length; i++) {
			var option = validateTypeArray[i];
			var endValue;
			if (option.indexOf('[') != -1) {
				var strOption = option;
				endValue = strOption.substring(option.indexOf('['), strOption.indexOf(']') + 1);
				return '大小应在：' + endValue + '之间';
			}
		}
	}
});