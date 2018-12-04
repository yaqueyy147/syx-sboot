define(function (require, exports, module) {

	var $ = require('jquery');
	var callback = require('hlideal/jquery.hlideal.page.callback');

	//内部构造方法
	function YCombobox() {
		this.name = "YCombobox";
		this.version = '1.0.0';
	}


	//定义基本对象
	$.YCombobox = new YCombobox();

	// 插件的定义
	$.fn.YCombobox = function (options, param) {
		//如果为string，表示是方法调用
		if (typeof options == 'string') {
			var method = $.fn.YCombobox.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};

		var multiple = this.data('multiple'),
			valueField = this.data('valuefield'),
			textField = this.data('textfield'),
			defaultvalue = this.data('defaultvalue'),
			onSelect = this.data('callback'),
			textelementid = this.data('textelementid'),
			initValue = this.val(),
			opts = {};

		if (onSelect) {
			options.onSelect = callback[onSelect];
		}

		if (multiple && multiple === true) {
			options.multiple = true;
		}
		if (!options.valueField) {
			options.valueField = valueField;
			options.textField = textField;
		}
		if (!options.value) {
			// 修改获取初始值代码，如果value有值，优先取 value的值
			if (typeof initValue != "undefined" && initValue != "" && initValue != null) {
				//优先取value的值，原因是因为修改了下拉值，初始值不再是defaultvalue
				options.value = initValue;
			} else {
				options.value = defaultvalue;
			}
		}
		if(textelementid != null && textelementid !=''){
			options.textelementid = textelementid;
		}
		opts = $.extend({}, $.fn.YCombobox.defaults, options);
		opts.target = this;
		if (!opts.data) {  //不存在数据

			if (!opts.url) {//不存在url
				opts.url = this.data('url');
			}
			if (opts.url) {
				//URL存在则ajax 请求
				$.ajaxSettings.async = false;
				$.ajax({
					async: true,
					type: "get",
					dataType: "jsonp",
					url: opts.url,//要访问的后台地址
					data: opts.queryParams,
					crossDomain: true,
					complete: function () {
						//关闭遮挡层
						//Loading.hide(options.loading);
					},
					success: function (data) {
						opts.data = data;
						opts.target.data("options", opts);
						opts.target.hide();
						return opts.target.each(function () {
							$.fn.YCombobox.methods.init(opts);
						});
					}
				});
				$.ajaxSettings.async = true;

			} else {
				//抓取数据
				opts.data = eval(this.data('data'));
				this.data("options", opts);
				return this.each(function () {
					$(this).hide();
					$.fn.YCombobox.methods.init(opts);
				});
			}
		} else {
			this.data("options", opts);
			return this.each(function () {
				$(this).hide();
				$.fn.YCombobox.methods.init(opts);

			});
		}
	};

	$.fn.YCombobox.methods = {
		getValue: function (jq) {
			return $(jq).next().data("val");
		},
		getText: function (jq) {
			return $(jq).next().data("text");
		},
		setValue: function (jq, value) {
			var opts = $(jq).data("options");
			$(jq).next().find('dd').find('p').each(function () {
				var selfVal = $(this).data('name');
				if (selfVal == value) {
					var text = $(this).text();
					$(this).parent().hide().siblings().children('a').text(text);
					$(this).parent().parent().data({
						'val': selfVal,
						'text': text
					});
					$(this).parent().parent().prev().attr("value", selfVal);
					$(opts.target).attr("value", selfVal);
					$(this).addClass("yui-option-selected").siblings().removeClass("yui-option-selected");
					opts.onSelect(selfVal, text, $(this).index());
					try {
						$.YUI.cleanValidateText(opts.target.parent());
					} catch (e) {
					}
					return false;
				}
			});
		},
		setValues: function (jq, values) {
			var opts = $(jq).data("options");
			var _nowvalue = "", _nowtext = "";
			
			$(jq).next().find('dd').find('p').each(function () {
				var selfVal = $(this).data('name');
				$(this).removeClass("yui-option-selected");
				var t_values = values.split(",");
				for (var _i = 0; _i < t_values.length; _i++) {					
					if (selfVal == t_values[_i]) {//若value相同
						
						if (_nowvalue) {
							_nowvalue += ",";
						} else {
							_nowvalue = "";
						}
						if (_nowtext) {
							_nowtext += ",";
						} else {
							_nowtext = "";
						}
						_nowvalue = _nowvalue + selfVal;
						_nowtext = _nowtext + $(this).text();
						
						$(this).addClass("yui-option-selected");
						$(this).parent().parent().prev().attr("value", _nowvalue);
						$(this).parent().hide().siblings().children('a').text(_nowtext);
						opts.onSelect(selfVal, $(this).text(), $(this).index());
						$(opts.target).attr("value", _nowvalue);
						opts.dlTarget.data({
							'val': _nowvalue,
							'text': _nowtext
						});
						if(opts.textelementid != null && opts.textelementid != ''){
							$("#" + opts.textelementid).val(_nowtext);
						}
					}
				}
			});
		},
		disable: function (jq) {
			$(jq).data("choose", "disable");
		},
		enabled: function (jq) {
			$(jq).data("choose", "enabled");
		},
		init: function (opts) {
			var val = opts.valueField,
				text = opts.textField,
				data = opts.data,
				html = '<dl class="yui-select-panel">',
				dtHtml = ' <dt class="yui-mselect"><a href="javascript:void (0);">',
				ddHtml = '<dd class="yui-option">',
				dText = "",
				isDefaultValue = '',
				choose = opts.target.data("choose");

			if (choose && choose == 'disable') {
				dtHtml = ' <dt class="yui-mselect" style="background-color: #CCCCCC;box-shadow: none;"><a href="javascript:void (0);">';
			}

			$.each(data, function (i, item) {
				var _itemVal = item[val];
				//如果存在界面默认值 value与defaultvalue
				if (opts.value != null && typeof(clearMh(JSON.stringify(opts.value))) != "undefined") {
					var t_values = JSON.stringify(opts.value).split(",");
					var _flog = false;
					for (var _i = 0; _i < t_values.length; _i++) {
						if (clearMh(_itemVal) == clearMh(t_values[_i])) {
							_flog = true;
							break;
						}
					}
					if (_flog) {
						ddHtml += '<p href="javascript:void(0)" class="yui-option-selected" data-name="' + _itemVal + '">' + item[text] + '</p>';
						if (dText) {
							dText += ",";
						} else {
							dText = "";
						}
						dText += item[text];
					} else {
						ddHtml += '<p  data-name="' + _itemVal + '">' + item[text] + '</p>';
					}
				} else {
					if (item['isDefault']) {
						ddHtml += '<p  class="yui-option-selected" data-name="' + _itemVal + '">' + item[text] + '</p>';
						if (dText) {
							dText += ",";
						} else {
							dText = "";
						}
						dText += item[text];
						if (isDefaultValue) {
							isDefaultValue += ",";
						} else {
							isDefaultValue = "";
						}
						isDefaultValue += _itemVal;
					} else {
						ddHtml += '<p  data-name="' + _itemVal + '">' + item[text] + '</p>';
					}
				}
			});

			dtHtml += dText + '</a></dt>';
			ddHtml += '</dd>';
			html += dtHtml + ddHtml + '</dl>';

			if (!opts.value) {
				opts.value = isDefaultValue;
			}
			opts.target.after(html);

			opts.dlTarget = opts.target.next();
			opts.target.data("options", opts);
			opts.target.attr("value", opts.value);
			opts.dlTarget.data({
				'val': opts.value,
				'text': dText
			});


			opts.dlTarget.find("dt").click(function () {
				var choose = opts.target.data("choose");
				if (choose && choose == 'disable') {
					return;
				}
				var self = $(this);
				self.parent().css({
					zIndex: '100'
				});
				self.siblings('dd').slideDown();
			});


			opts.dlTarget.find("p").click(function () {
				var choose = opts.target.data("choose");
				if (choose && choose == 'disable') {
					return;
				}
				var self = $(this);
				var selfVal = self.data('name');
				var selfText = self.text();

				if (opts.multiple) {

					//获取之前的值
					var oldVal = opts.dlTarget.data('val');
					var oldText = opts.dlTarget.data('text');
					if (!oldText) {
						oldText = self.parent().hide().siblings().children('a').text();
					}
					//判断之前是否选中
					if (self.hasClass("yui-option-selected")) {
						//移除
						selfVal = removeItem(oldVal, selfVal);
						selfText = removeItem(oldText, selfText);

						self.removeClass("yui-option-selected");
					} else {

						//添加
						if (oldVal) {
							oldVal += ","
						} else {
							oldVal = "";
						}
						selfVal = oldVal + selfVal;
						if (oldText) {
							oldText += ",";
						} else {
							oldText = "";
						}
						selfText = oldText + selfText;
						self.addClass("yui-option-selected");
					}

				} else {
					self.addClass("yui-option-selected").siblings().removeClass("yui-option-selected");
				}
				self.parent().hide().siblings().children('a').text(selfText);
				opts.onSelect(selfVal, text, $(this).index());
				opts.dlTarget.data({
					'val': selfVal,
					'text': selfText
				});
				opts.target.attr("value", selfVal);
				if(opts.textelementid != null && opts.textelementid != ''){
					$("#" + opts.textelementid).val(selfText);
				}

				//清除验证失败提示信息
				if (selfVal || selfVal.length > 0) {
					try {
						$.YUI.cleanValidateText(opts.target.parent());
					} catch (e) {
					}
				}

			});

			opts.dlTarget.mouseleave(function () {
				var self;
				self = $(this);
				self.find('dd').hide();
				self.css({
					zIndex: '1'
				});
			});
		}
	};

	// 插件的defaults
	$.fn.YCombobox.defaults = {
		id: null,//唯一标识
		valueField: "value",//值字段
		textField: "text",//文本字段
		url: null,//远程数据地址
		data: null,//本地数据
		multiple: false,//是否多选
		width: 245,//下拉框宽度
		height: null,//下拉框高度
		value: null,//默认值
		target: null,
		textelementid: null, //对显示值进行赋值ID
		onSelect: function (value, text, index) {

		}
	};
	/**
	 * 移除oldVal中的val
	 * @param oldVal
	 * @param val
	 */
	function removeItem(oldVal, val) {
		if (oldVal) {
			oldVal = oldVal.replace(val, "");
			//最前面的,
			if (oldVal.length > 0) {
				if (oldVal.substr(0, 1) == ",") {
					oldVal = oldVal.substr(1, oldVal.length);
				}
				if (oldVal.substr(oldVal.length - 1, oldVal.length) == ",") {
					oldVal = oldVal.substr(0, oldVal.length - 1);
				}
			}
			//最后面的,
			return oldVal.replace(",,", ",");
		}
		return "";
	}

	/**
	 * 清除通过JOSN.stringify()解析出字符串中的引号
	 * @param str
	 */
	function clearMh(str) {
		if ((typeof str) != "string")return JSON.stringify(str);
		str = str.replace("\"", "");
		str = str.replace("\"", "");
		return str;
	}

});
