define(function (require, exports, module) {
	var $ = require('jquery');
	//require('hlideal/myajax');
	var callBack = require('hlideal/jquery.hlideal.page.callback');
	require('hlideal/jquery.hlideal.message');
	require('hlideal/jquery.hlideal.validate');
	require('hlideal/jquery.hlideal.combobox');
	require('hlideal/jquery.hlideal.combotree');
	require('hlideal/jquery.hlideal.checkbox');
	require('hlideal/jquery.hlideal.table');
	require('datepicker');
	require('upload');

	//构造函数
	function YUI() {
		this.version = '1.0.0';
		this.name = 'yui-from';
		this.options = null;
	}



	String.prototype.replaceAll = function (s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	};
	//内部定义自身方法
	$.extend(YUI.prototype, {
		init: function (div) {
            if (!div) {
                div = "";
            }
            //初始化页面
            $.YUI.initForms(div);
            $.YUI.initGrid(div);
            $.YUI.strToDate();

        },
		/**
		 * 1、初始化一个或多个表单
		 * 2、绑定表单提交
		 * */
		initForms: function (div) {
			$(div + " form[class^=yui-form]").each(function () {
				// 绑定表单提交
				$(this).unbind();
				$(this).submit(function () {
					var optObj = $.YUI.bindOptions(this);
					if ($.YUI.validateForm(this)) {
						var params = $(this).serialize();
						try {
							$.ajax({
								url: optObj.url,
								type: "post",
								data: params,
								jsonp: "jsoncallBack",
								dataType: "jsonp",
								success: function (data) {
									callBack[optObj.callBack](data);
								}
							});
						} catch (e) {

						}
					} else {
						$.YMessage.error("错误", "数据验证失败", function () {
						});
					}
					return false;
				});
				$(this).find("[class*=yui-]").each(function () {
					var _class_names = $(this).attr("class").split(" ");
					var that = this;
					for (var _i = 0; _i < _class_names.length; _i++) {
						var _this_class = _class_names[_i];
						var flag = $(that).data('multiple');
						switch (_this_class) {
							case "yui-validatebox":
								$(that).YValidatebox('bindBox', null);
								break;
							case "yui-combobox":
								$(that).YCombobox({multiple: flag});
								break;
							case "yui-combotree":
								$(that).YComboTree({multiple: flag});
								break;
							case "yui-checkbox":
								$(that).YCheckbox({multiple: flag});
								break;
							case "yui-upload":
								$(that).uploadify({
									height: 30,
									width: 120,
									swf: '../assets/js/vendor-modules/uploadify//uploadify.swf',
									uploader: '../assets/js/vendor-modules/uploadify/uploadify.php'
								});
								break;
						}
					}
				});
			});
		},
		dialogInit: function () {
            $("form[class^=yui-]").each(function () {

                // 绑定表单提交
                $(this).unbind().bind("submit", function () {
                    var optObj = $.YUI.bindOptions(this);
                    if ($.YUI.validateForm(this)) {
                        var params = $(this).serialize();
                        $.ajax({
                            url: optObj.url,
                            type: "post",
                            data: params,
                            jsonp: "jsoncallBack",
                            dataType: "jsonp",
                            success: function (data) {
                                // 加载成功之前
                                try {
                                    callBack[optObj.callBack](data);
                                } catch (e) {
                                    
                                }
                            }
                        })
                    } else {
                        $.YMessage.error("错误", "数据验证失败", function () {
                        });
                    }
                    return false;
                });
                $(this).find("[class*=yui-]").each(function () {
                    var _class_names = $(this).attr("class").split(" ");
                    var that = this;
                    //TODO 这里可以写成target,className为参数进行绑定
                    for (var _i = 0; _i < _class_names.length; _i++) {
                        var _this_class = _class_names[_i];
                        switch (_this_class) {
                            case "yui-validatebox":
                                $(that).ZDSValidatebox('bindBox', null);
                                break;
                            case "yui-combobox":
                                var that = this;
                                var dialogInt = $(that).attr('dialogInit');
                                if (typeof dialogInt != 'undefined') {
                                    try {
                                        $(that).YCombobox();
                                    } catch (e) {
                                        
                                    }
                                    break;
                                }


                        }
                    }

                });
            });
        },
		/**
		 * 本地表单验证
		 * @return true|false
		 * */
		validateForm: function (target) {
			var checked = true;
			var finalResult = $(target).YValidatebox('validateAll', $(target));
			if (!finalResult) {
				checked = false;
			}
			return checked;
		},
		validateSubmit: function (target, status) {
            var checked = true;
            var comboChecked = true;
            var finalResult = $(target).ZDSValidatebox('validateForm', $(target), status);
            if (!finalResult) {
                checked = false;
            }
            return checked;
        },
        cleanValidateText: function (target) {
            $(target).parent().find('div[class*="hlideal-validation"]').remove();
        },
		/**
		 * 获取form上面zdata-options绑定的数据
		 * @param target 如：form
		 * @return dataObj JSON数据
		 * */
		bindOptions: function (target) {
			//绑定zdata-option
			var opts = $(target).attr("zdata-options");
			opts = opts.replaceAll("'", "\"");
			return $.parseJSON(opts);
		},
		/**
		 * 清除验证提示信息
		 * @param target  $('dd.detail')
		 * */
		cleanValidateText: function (target) {
			$(target).find('span[class*="hlideal-validation"]').remove();
		},
		bindcallBack: function (methodName, data) {//回调成功所调函数
            if (arguments.length > 2) {
                var args = [];
                for (var i = 2; i < arguments.length; i++) {
                    args.push(arguments[i]);
                }
            }
            if (methodName) {

                function zcallBackMethod(methodName, data, args) {
                    data = JSON.stringify(data);
                    for (var i = 0; args && i < args.length; i++) {
                        data += "," + JSON.stringify(args[i]);
                    }
                    if (data) {
                        this.func = new Function("return " + methodName + "(" + data + ");");
                    } else {
                        this.func = new Function("return " + methodName + "();");
                    }

                }

                return new zcallBackMethod(methodName, data, args);
            }
        },
        initGrid: function (div) {
            $(div + "table[class*=yui-datagrid]").each(function () {
                $(this).addClass('hlideal-table');
                var finalPoint = this;
                var opjObj = $.YUI.bindOptions(this);
                var outArray = [];
                var inArray = [];
                var toolbars = [];
                $(this).find('th').each(function () {
                    var that = this;
                    var test = $(that).attr('data-options');
                    var testArray = test.split(',');
                    var testObj = new Object();
                    for (var i = 0; i < testArray.length; i++) {
                        var lastThings = testArray[i].split(':');
                        var tempName = lastThings[0];
                        if(tempName =='opername'){
                        	testObj.opername = lastThings[1];
                        	testObj.opertemplate = $(that).html();
                        	testObj.opertrans=true;
                        	var operformater = testObj.opertemplate;
                        	 $(operformater).find("a").each(function () {
                        		 var oper = this;
                        		 var operhandler = $(that).attr('handler');
                                 if (typeof operhandler != 'undefined') {
                                     testObj.operhandler = function (arg1, arg2) {
                                         return callBack[operhandler](arg1, arg2);
                                     };
                                 }
                        	 });
                        }
                        testObj[tempName] = lastThings[1];
                    }
                    var formatter = $(that).attr('formatter');

                    if (typeof formatter != 'undefined') {

                        testObj.formatter = function (arg1, arg2) {
                            return callBack[formatter](arg1, arg2);
                            //return $.YUI.bindcallBack(formatter, arg1, arg2).func();
                        };
                    }

                    testObj.title = $(that).html();
                    inArray.push(testObj);
                });
                outArray.push(inArray);
                opjObj.columns = outArray;
                
                var toolbarId = opjObj.toolbar;
                $(toolbarId).find("a[class*=yui-toolbar]").each(function () {
                    var toolbar = new Object();
                    var that = this;
                    var id = $(that).attr('id');
                    var text = $(that).attr('text');
                    var iconCls = $(that).attr('iconCls');
                    var buttonCls = $(that).attr('buttonCls');
                    var handler = $(that).attr('handler');
                    toolbar.id = id;
                    toolbar.text = text;
                    toolbar.iconCls = iconCls;
                    toolbar.buttonCls = buttonCls;
                    toolbar.handler = function (event) {
                        try {
                            var _resultValue = callBack[handler]();

                            return false;
                        } catch (e) {
                            return false;
                        }

                        //  $.YUI.bindcallBack(handler).func();
                    };
                    toolbars.push(toolbar);
                });
                opjObj.toolbar = toolbars;
                var test = opjObj.columns;
                $(finalPoint).html('');
                $(toolbarId).html('');
                $(finalPoint).YTable(opjObj);

            });
        },
        /**
		 * 日期格式转换
		 * 20160426==>2016-04-26
		 * */
		dateToStr: function () {
	            $("dd[class*=dateToStr]").each(function () {
	                var that = this;
	                var _oldValue = $(that).text();
	                if (_oldValue.indexOf("-") == -1) {
	                    _oldValue = _oldValue.replace(/(^\s*)|(\s*$)/g, "");
	                    var _oldYear = _oldValue.substr(0, 4);
	                    var _oldMonth = _oldValue.substr(4, 2);
	                    var _oldDay = _oldValue.substr(6, 2);
	                    $(that).text(_oldYear + '-' + _oldMonth + '-' + _oldDay);
	                }
	
	            });
	    },
		strToDate: function () {
			$("input[class*=strToDate]").each(function () {
				var that = this;
				var _oldValue = $(that).val();
				if ('' != _oldValue && null != _oldValue && _oldValue.indexOf("-") == -1) {
					_oldValue = _oldValue.replace(/(^\s*)|(\s*$)/g, "");
					var _oldYear = _oldValue.substr(0, 4);
					var _oldMonth = _oldValue.substr(4, 2);
					var _oldDay = _oldValue.substr(6, 2);
					$(that).val(_oldYear + '-' + _oldMonth + '-' + _oldDay);
				}
			});
		}
	});
	
	//实例化对象，对外提供接口
	$.YUI = new YUI();
	module.exports = $.YUI;
});

seajs.use(['jquery', 'hlideal/jquery.hlideal.page.callback'], function ($, CALLBACK) {
    CALLBACK.dateFmt = function (index, val) {
        if (val) {
            var _oldValue = val + '';
            _oldValue = _oldValue.replace(/(^\s*)|(\s*$)/g, "");
            var _oldYear = _oldValue.substr(0, 4);
            var _oldMonth = _oldValue.substr(4, 2);
            var _oldDay = _oldValue.substr(6, 2);
            return _oldYear + '-' + _oldMonth + '-' + _oldDay;
        }
    }
    CALLBACK.strTodate = function (index, val) {
        if (val) {
        	var t = new Date(val);
        	var tf = function(i){return (i < 10 ? '0' :'') + i};
        	var format = "yyyy-MM-dd";
        	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        		switch(a){
        			case 'yyyy':
        			return tf(t.getFullYear());
        			break;
        			case 'MM':
        			return tf(t.getMonth() + 1);
        			break;
        			case 'mm':
        			return tf(t.getMinutes());
        			break;
        			case 'dd':
        			return tf(t.getDate());
        			break;
        			case 'HH':
        			return tf(t.getHours());
        			break;
        			case 'ss':
        			return tf(t.getSeconds());
        			break;
        		}
        	});
        }
    }
});

