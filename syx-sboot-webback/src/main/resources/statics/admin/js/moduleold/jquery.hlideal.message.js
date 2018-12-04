define(function (require, exports, module) {

	var $ = require('jquery');
	require('hlideal/jquery.hlideal.window');

	//构造函数
	function YMessage() {
		this.version = "1.0.0";
		this.name = "YMessage";
	}

	//实例化对象
	var yMessage = new YMessage();
	$.YMessage = yMessage;

	//定义内部自身方法
	$.extend(YMessage.prototype, {

		/**
		 * 初始化消息框
		 * */
		init: function (title, html) {

			$("body").append('<div class="hlideal-message">' + html + '</div>');

			var target = $(".hlideal-message").last(),
				content_width = 350,
				content_height,
				w_opts = {};

			target.find('.alert-content').css('width', content_width);
			content_height = target.find('.alert-content').height();
			target.find('.alert-content').css('width', '');

			//标题的高度大约50px
			target.YWindow({width: content_width, height: content_height + 50, closed: true, title: title});
			w_opts = target.data("options");

			//关闭图标
			w_opts.target.find(".dialog-title .alert-close").click(function () {
				target.YWindow("close");
			});

			//拖拽效果
			if (w_opts.isDrag) {
				w_opts.target.find(".dialog-title").css('cursor', 'move');
				var maxWidth = $(window).width() - content_width - 10,
					maxHeight = $(window).height() - content_height - 80;
				iDrag({
					target: w_opts.target.find(".dialog-title"),
					root: w_opts.target,
					min: {x: 10, y: 10},
					max: {x: maxWidth, y: maxHeight}
				});
			}

			//打开提示框
			target.YWindow("open");
			return target;
		},

		/**
		 * 警告提示框
		 * */
		warning: function (title, body, fn) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-awarn"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			html.push('<div class="tc alert-button"> <a href="javascript:void(0);" class="btn-sblue">确认</a></div>');
			html.push('</div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			//确认按钮
			z_window.target.find(".alert-button a").YButon({
				id: 'message-btn',
				text: '确定',
				buttonCls: 'btn-sblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
					if (typeof fn == "function") {
						fn();
					}
				}
			});

		},

		/**
		 * 普通消息提示框
		 * */
		info: function (title, body, fn) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-awarn"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			html.push('<div class="tc alert-button"><a href="javascript:void(0);" class="btn-sblue">确认</a></div>');
			html.push('</div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			//确认按钮
			z_window.target.find(".alert-button a").YButon({
				id: 'message-btn',
				text: '确定',
				buttonCls: 'btn-sblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
					if (typeof fn == "function") {
						fn();
					}
				}
			});
		},

		/**
		 * 成功提示框
		 * */
		success: function (title, body, fn) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-asuccess"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			html.push('<div class="tc alert-button"> <a href="javascript:void(0);" class="btn-sblue">确认</a></div>');
			html.push('</div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			//确认按钮
			z_window.target.find(".alert-button a").YButon({
				id: 'message-btn',
				text: '确定',
				buttonCls: 'btn-sblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
					if (typeof fn == "function") {
						fn();
					}
				}
			});
		},

		/**
		 * 失败提示框
		 * */
		error: function (title, body, fn) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-aerror"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			html.push('<div class="tc alert-button"> <a href="javascript:void(0);" class="btn-sblue">确认</a></div>');
			html.push('</div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			//确认按钮
			z_window.target.find(".alert-button a").YButon({
				id: 'message-btn',
				text: '确定',
				buttonCls: 'btn-sblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
					if (typeof fn == "function") {
						fn();
					}
				}
			});
		},

		/**
		 * 问题提示框
		 * */
		question: function (title, body, fn) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-awarn"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			html.push('<div class="tc alert-button"> <a href="javascript:void(0);" class="btn-sblue">确认</a><a href="javascript:void(0);" class="btn-cblue">取消</a></div>');
			html.push('</div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			//确认按钮
			z_window.target.find(".alert-button a.btn-sblue").YButon({
				id: 'message-btn',
				text: '确定',
				buttonCls: 'btn-sblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
					if (typeof fn == "function") {
						fn();
					}
				}
			});
			//取消按钮
			z_window.target.find(".alert-button a.btn-cblue").YButon({
				id: 'message-btn',
				text: '取消',
				buttonCls: 'btn-cblue',
				handler: function () {
					target.YWindow("close");
					z_window.target.parent().remove();
					target.remove();
				}
			});
		},

		/**
		 * 确认提示框
		 * */
		confirm: function (title, body, fn, buttons) {
			var html = [];
			//标题
			html.push('<div class="dialog-title">' + title + ' <a class="alert-close"><i class="icon-dclose"></i></a> </div>');
			//内容
			html.push(' <div class="alert-content">');
			html.push('<div class="alert-img"> <i class="icon-awarn"></i> </div>');
			html.push('<div class="alert-word">');
			html.push('<p>' + title + '</p><p>' + body + '</p>');
			html.push('</div>');
			//按钮
			var button_html = '';
			html.push('<div class="tc alert-button">');
			if (!buttons) {
				html.push('<a href="javascript:void(0);" class="btn-sblue">确认</a><a href="javascript:void(0);" class="btn-cblue">取消</a>');
			} else {
				for (var key in buttons) {
					var item = buttons[key];
					button_html += '<a href="javascript:void(0);" id="' + item.id + '" >' + item.text + '</a>';
				}
				html.push(button_html);
			}
			html.push('</div></div>');
			//初始化新建消息提示框
			var target = this.init(title, html.join(""));
			var z_window = target.data("options");
			if (!buttons) {
				//确认按钮
				z_window.target.find(".alert-button a.btn-sblue").YButon({
					id: 'message-btn',
					text: '确定',
					buttonCls: 'btn-sblue',
					handler: function () {
						target.YWindow("close");
						z_window.target.parent().remove();
						target.remove();
						if (typeof fn == "function") {
							fn(true);
						}
					}
				});
				//取消按钮
				z_window.target.find(".alert-button a.btn-cblue").YButon({
					id: 'message-btn',
					text: '取消',
					buttonCls: 'btn-cblue',
					handler: function () {
						target.YWindow("close");
						z_window.target.parent().remove();
						target.remove();
						if (typeof fn == "function") {
							fn(false);
						}
					}
				});
			} else {
				var callback = function (r, target, z_window) {
					this.func = function () {
						$(target).YWindow("close");
						$(z_window.target).parent().remove();
						$(target).remove();
						if (typeof fn == "function") {
							fn(r);
						}
					}
				};
				for (var key in buttons) {
					var _fn = new callback(key, target, z_window);
					var item = buttons[key];
					$(z_window.target).find(".alert-button a#" + item.id).YButon({
						id: item.id,
						text: item.text,
						buttonCls: item.buttonCls,
						handler: _fn.func
					});
				}
			}
		}
	});

});