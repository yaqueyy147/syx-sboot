//初始化对象
var Dialog = function Dialog() {
    this.version = "1.0.0";
    this.name = "Dialog";
}

//定义基本对象
$.Dialog = new Dialog();
// var _z = new Dialog();
// $.Dialog = _z;
//定义插件
$.fn.Dialog = function (options) {
    //如果为string，表示是方法调用
    if (typeof options == 'string') {
        var method = $.fn.Dialog.methods[options];
        if (method) {
            return method(this);
        } else {
            method = $.fn.Window.methods[options];
            if (method) {
                return method(this);
            }
        }
    }
    options = options || {};

    var _opts = this.data("options");

    //判断是否已渲染
    if (_opts && _opts.isInit) {
        options = $.extend({}, _opts, options);
    }

    var opts = $.extend({}, $.fn.Dialog.defaults, options);
    opts = $.extend({}, $.fn.Window.defaults, opts);

    if (_opts && (!_opts.createNew)) {
        $(this).Window(_opts);
        return;
    }

    $(this).each(function () {

        var html = [],
            bodyHtml = '',
            dialog_btn = '';

        //判断是否已渲染
        if (_opts && _opts.isInit) {
            bodyHtml = $(opts.target).find(".hlideal-window").html();
        } else {
            bodyHtml = $(this).html();
        }

        //标题
        html.push('<div class="dialog-title"> ' + opts.title + '<a class="dialog-close"><i class="icon-dclose"></i></a> </div>');

        //内容
        html.push('<div class="dialog-content">');
        html.push(bodyHtml);
        html.push('</div>');

        //按钮
        if (opts.buttons) {
            dialog_btn += ('<div class="dialog-bottom">');
            $.each(opts.buttons, function (i, item) {
                dialog_btn += ('<a href="javascript:void(0);">' + item.text + '</a>');
            });
            dialog_btn += ('</div>');
            html.push(dialog_btn);
        }
        $(this).html(html.join(""));

        //创建弹出框
        $(this).Window({
            width: opts.width,
            height: opts.height,
            title: opts.title,
            closed: opts.closed,
            fullcontent: opts.fullcontent
        });

        //创建按钮
        var w_opts = $(this).data("options");
        w_opts.target.find(".dialog-bottom a").each(function (i) {
            $(this).YButon(opts.buttons[i]);
        });

        //关闭图标
        w_opts.target.find(".dialog-title .dialog-close").click(function () {
            opts.target.Dialog("close");
        });

        //拖拽效果
        if (opts.isDrag) {
            w_opts.target.find(".dialog-title").css('cursor', 'move');
            var maxWidth = $(window).width() - w_opts.width - 10,
                maxHeight = $(window).height() - w_opts.height - 10;
            if (maxWidth < 0) {
                maxWidth = 10;
            }
            if (maxHeight < 0) {
                maxHeight = 10;
            }
            iDrag({
                target: w_opts.target.find(".dialog-title"),
                root: w_opts.target,
                min: {x: 10, y: 10},
                max: {x: maxWidth, y: maxHeight}
            });
        }
        opts.target = $(this);
    });
};
$.fn.Dialog.methods = {
    /**
     * 打开弹出框
     * @param jq
     * */
    open: function (jq) {
        $.fn.Window.methods["open"](jq);
    },
    /**
     * 关闭弹出框
     * @param jq
     * */
    close: function (jq) {
        $.fn.Window.methods["close"](jq);
    }
};
$.fn.Dialog.defaults = {
    width: 500,
    height: 250,
    title: 'new Dialog',
    isDrag: true,//是否可以拖拽移动
    target: null,
    createNew: false,
    fullcontent: false
};