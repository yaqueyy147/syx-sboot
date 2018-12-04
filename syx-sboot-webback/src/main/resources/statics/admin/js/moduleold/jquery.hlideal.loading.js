define(function (require, exports, module) {
	var $ = require('jquery');

	function Loading() {
		this.name = "Loading";
		this.version = "1.0.0";
		this.zindex = 8888;
	}

	Loading.zindex = 8888;
	Loading.html = '<div class="hlideal_loading" style="display: none"><div class="loading-pic"><img src="/statics/admin/images/spinner.gif"><p>loading…</p></div> </div>';
	Loading.divHtml = '<div class="hlideal_div_loading" style="display: none"><div class="loading-pic"> <img src="/statics/admin/images/spinner.gif"><p>loading…</p></div> </div>';

	/**
	 *显示遮罩层
	 * @param obj
	 * @param showImage
	 * @param zindex
	 */
	Loading.show = function (obj, showImage, zindex) {
		//存在的遮罩层
		if (!obj) {
			$(window).unbind('resize');
			$(window).bind('resize', function () {
				$(".hlideal_loading").each(function () {
					$(this).css("width", $(document).width());
					$(this).css("height", $(document).height());
				});
			});
		}
		//新建遮罩层
		var loadObj = Loading.create(obj, showImage, zindex || Loading.zindex);
		//显示遮罩层
		$(loadObj).show();
		return loadObj;
	};

	/**
	 * 创建遮罩层
	 * @param obj
	 * @param showImage 加载图标
	 * @param zindex  层级
	 * @returns {*} 新遮罩层
	 */
	Loading.create = function (obj, showImage, zindex) {
		if (zindex < 8888) {
			zindex = 8888;
		}
		//定义loading对象
		var loadingObj = null;
		//判断是否存在obj
		if (obj) {
			if (!($("#" + obj + " .hlideal_div_loading").length && $("#" + obj + " .hlideal_div_loading").length > 0)) {
				$("#" + obj).prepend(Loading.divHtml);
			}
			loadingObj = $("#" + obj + " .hlideal_div_loading").first();
			var _temp_object = $("#" + obj + " table");
			var _heigt = $("#" + obj).height();
			if (_temp_object.css("margin-top")) {
				_heigt = $("#" + obj).height() + _temp_object.css("margin-top").replace("px", "") * 1;
			}

			$(loadingObj).css("height", _heigt);
			$(loadingObj).css("width", $("#" + obj).width());
			return loadingObj;
		} else {
			//如果存在则需要修改zindex
			if ($(".hlideal_loading").length && $(".hlideal_loading").length > 0) {
				var z_index = $(".hlideal_loading").last().css("z-index");
				if (zindex < (z_index++)) {
					zindex = z_index++;
				}
			}
			$("body").prepend(Loading.html);
			loadingObj = $(".hlideal_loading").first();
			if (showImage === false) {
				$(loadingObj).find(".loading-pic").remove();
			}
			$(loadingObj).css("width", $(document).width());
			$(loadingObj).css("height", $(document).height());
			$(loadingObj).css("z-index", zindex);
			return loadingObj;
		}
	};

	/**
	 *删除遮罩层
	 *@param obj
	 */
	Loading.hide = function (obj) {
		if (obj && $(obj).length > 0) {
			$(obj).hide();
			$(obj).remove();
		}

	};

	module.exports = Loading;
	new Loading();
});