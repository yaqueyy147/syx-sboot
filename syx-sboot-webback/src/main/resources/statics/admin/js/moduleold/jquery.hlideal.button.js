define(function (require, exports, module) {

	var $ = require('jquery');
	//内部构造方法
	function YButon() {
		this.name = "YButon";
		this.version = '1.0.0';
	}

	//定义基本对象
	$.YButon = new YButon();

	// 插件的定义
	$.fn.YButon = function (options, param) {
		if (typeof options == 'string') {
			var method = $.fn.YButon.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};
		var opts = $.extend({}, $.fn.YButon.defaults, options);

		return this.each(function () {
			var oldOptions = null,
				Num = "",//随机数，设置id
				i = 0;//for循环变量

			//根据群组按钮还是普通按钮
			//获取oldOptions的值
			if (opts.groupDiv) {
				//如果群组二次操作无id 则可能出现问题，
				//设置群组的id
				if (!opts.id) {
					for (i = 0; i < 6; i++) {
						Num += Math.floor(Math.random() * 10);
					}
					opts.id = "zbt" + Num;
				}
				oldOptions = $(opts.groupDiv).data(opts.id);
			} else {
				//如果按钮没有id，设置按钮的id
				if (!opts.id) {
					for (i = 0; i < 6; i++) {
						Num += Math.floor(Math.random() * 10);
					}
					opts.id = "zbt" + Num;
				}
				oldOptions = $(this).data("zbutton");
			}
			if (oldOptions != null) {
				return;
			}
			//如果是群组则 将数据存在群组
			//否则，保存在zbutton
			if (opts.groupDiv) {
				$(opts.groupDiv).data(opts.id, opts);
			} else {
				opts.target = $(this);
				$(this).data("zbutton", opts);
			}
			//创建按钮
			createButton(opts);
		});
	};
	$.fn.YButon.methods = {
		/**
		 * 获取zbutton存放的数据
		 * */
		options: function (jq) {
			return $.data(jq[0], "zbutton");
		},
		/**
		 * 启动按钮
		 * */
		enable: function (jq) {
			return jq.each(function () {
				var opts = $.data(jq, "zbutton");
				opts.disabled = false;
				setDisabled(opts);
			});
		},
		/**
		 * 禁用按钮
		 * */
		disable: function (jq) {
			return jq.each(function () {
				var opts = $.data(jq, "zbutton");
				opts.disabled = true;
				setDisabled(opts);
			});
		}
	};

	$.fn.YButon.defaults = {
		id: null,//id
		text: "",//按钮内容
		buttonCls:null,//按钮样式
		iconCls: null,//按钮图标
		disabled: false,//是否可用
		target: null,
		groupDiv: null,//群组所在的DIV
		isInit: false,//是否初始化
		handler: function () {
		}
	};

	/**
	 * 创建按钮
	 * @param options
	 */
	function createButton(options) {
		var target = null,
			html = '';//按钮代码片段
		if (options.groupDiv) {
			target = options.groupDiv;
			html += ('<button id="hlideal_btn_' + options.id + '" class="mr10 mb15 ' + options.buttonCls + '">');
			//按钮图标
			if (options.iconCls) {
				html += ('<i  class="' + options.iconCls + '"></i>');
			}
			html += (options.text + '</button');
			$(target).append(html);
		} else {
			target = options.target;
			html += ('<button id="hlideal_btn_' + options.id + '" class="mr10 ' + options.buttonCls + '">');
			//按钮图标
			if (options.iconCls) {
				html += ('<i  class="' + options.iconCls + '"></i>');
			}
			html += (options.text + '</button>');
			$(target).after(html);
			$(target).hide();
		}
		//设置按钮状态
		setDisabled(options);
	}

	/**
	 * 设置按钮状态（disabled：true|false）
	 * @param options
	 */
	function setDisabled(options) {
		var target = null;
		if (options.groupDiv) {
			target = options.groupDiv;
			if (options.disabled) {
				$(target).find("#hlideal_btn_" + options.id).unbind("click");
				$(target).find("#hlideal_btn_" + options.id).addClass("btn-disabled");
			} else {
				$(target).find("#hlideal_btn_" + options.id).unbind("click");
				$(target).find("#hlideal_btn_" + options.id).click(function (e) {
					options.handler();
					e.preventDefault();
				});
				$(target).find("#hlideal_btn_" + options.id).removeClass("btn-disabled");
			}
		} else {
			target = options.target;
			if (options.disabled) {
				$(target).next("#hlideal_btn_" + options.id).unbind("click");
				$(target).next("#hlideal_btn_" + options.id).addClass("btn-disabled");
			} else {
				$(target).next("#hlideal_btn_" + options.id).unbind("click");
				$(target).next("#hlideal_btn_" + options.id).click(function (e) {
					options.handler();
					e.preventDefault();
				});
				$(target).next("#hlideal_btn_" + options.id).removeClass("btn-disabled");
			}
		}
	}
});