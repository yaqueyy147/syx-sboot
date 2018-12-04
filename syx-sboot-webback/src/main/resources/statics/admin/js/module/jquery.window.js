//内部构造方法
function Window() {
    this.name = "Window";
    this.version = "1.0.0";
}

//定义基本对象
$.Window = new Window();

// 插件的定义
$.fn.Window = function (options) {
    //options如果为string，表示是方法调用
    if (typeof options == 'string') {
        var method = $.fn.Window.methods[options];
        if (method) {
            return method(this);
        }
    }
    //获取合并options
    options = options || {};
    var _opts = this.data("options");
    if (_opts) {
        options = $.extend({}, _opts, options);
    }
    var opts = $.extend({}, $.fn.Window.defaults, options);
    this.each(function () {
        var html = [],//弹出框的html
            width = $(window).width(),//可视区域宽度
            height = $(window).height(),//可视区域高度
            left = (width - opts.width) / 2,//弹出框的left值
            top = (height - opts.height) / 2,//弹出框的top值
            window_width = opts.width,//弹出框的宽度
            window_height = opts.height,//弹出框的高度
            window_id = "YUIWIN" + Math.floor(Math.random() * 99999 + 1),//弹出框的id属性值
            display = 'none', //弹出框的display属性值
            zindex = 0, //弹出框与遮罩层的z-index属性值
            body = $('body');//body对象

        if (left < 0) {
            left = 10;
        }
        if (top < 0) {
            top = 10;
        }
        if (opts.width > width) {
            window_width = width - 20;
        }
        if (opts.height > height) {
            window_height = height - 20;
        }

        //closed的值为false，打开弹出框
        if (!opts.closed) {
            display = 'block';
            //设置遮罩层的z-index的值
            //z-index的值，要比已打开的弹出框的值高，让它显示在最前面
            $("div[class='hlideal-window']:visible").each(function () {
                if (zindex < $(this).css("z-index")) {
                    zindex = $(this).css("z-index");
                }
            });
            //创建遮罩层
            opts.loading = Loading.show(null, false, ++zindex);
            //设置弹出框的z-index值
            if (zindex == 0) {
                zindex = 9000;
            } else {
                zindex += 2;
            }
            html.push('<div  tabindex="-1"  id="' + window_id
                + '" class="hlideal-window" style="width: ' + window_width + 'px;height: ' + window_height
                + 'px;top:' + top + 'px;left:' + left + 'px;display:' + display
                + ' z-index:' + (zindex) + ';" >');
        } else {
            html.push('<div  tabindex="-1"   id="' + window_id
                + '" class="hlideal-window"  style="width: ' + window_width + 'px;height: ' + window_height
                + 'px;top:' + top + 'px;left:' + left + 'px;display:' + display + '" >');
        }

        html.push($(this).html());
        html.push('</div>');
        body.append('<div>' + html.join("") + '</div>');

        $(this).html("");
        opts.divTarget = $(this);
        opts.target = body.children("div:last-child").find(".hlideal-window");
        opts.isInit = true;
        opts.windowId = window_id;
        $(this).data("options", opts);
    });
};
$.fn.Window.defaults = {
    width: 400,//宽度
    height: 150,//高度
    title: "",//标题
    isDrag: true,//是否可以拖拽移动
    closed: false,//是否关闭
    isInit: false,//是否初始化
    bodyHtml: null,//内容
    createNew: false,//是否新建
    target: null,
    fullcontent: false,
    divTarget: null,
    onClose: function () {

    },
    onOpen: function () {

    }

};
$.fn.Window.methods = {
    /**
     * 打开弹出框
     * @param jq
     * */
    open: function (jq) {
        var opts = $(jq).data("options");
        if (opts) {
            var width = $(window).width(),//可视区域宽度
                height = $(window).height(),//可视区域高度
                left = (width - opts.width) / 2,//弹出框的left属性值
                top = (height - opts.height) / 2,//弹出框的top属性值
                zindex = 0;//弹出框的z-index属性值

            //设置弹出框的宽高以及主体内容的高度
            if (opts.width > width) {

                opts.width = width - 20;
            }
            if (opts.height > height) {
                opts.height = height - 20;
            }
            opts.target.css("width", opts.width);
            opts.target.css("height", opts.height);

            if(opts.fullcontent == true){
                opts.target.find(".dialog-content").css("height", opts.height - 70);
            }else{
                opts.target.find(".dialog-content").css("height", opts.height - 120);
            }
            //设置弹出框的偏移量

            if (left < 0) {
                left = 10;
            }
            if (top < 0) {
                top = 10;
            }
            opts.target.css("top", top);
            opts.target.css("left", left);

            //添加 try 以防没有该方法
            try {
                opts.onOpen();
            } catch (e) {
            }

            //设置弹出框遮罩层的z-index
            $("div[class='hlideal-window']:visible").each(function () {
                if (zindex < $(this).css("z-index")) {
                    zindex = $(this).css("z-index");
                }
            });
            // if(!opts.loading){//有遮罩则不再增加
            opts.loading = Loading.show(null, false, ++zindex);
            // }
            if (zindex > 8888) {
                opts.target.css("z-index", zindex + 2);
            }
            //绑定数据
            $(jq).data("options", opts);
            //显示弹出框
            opts.target.show();
        }
    },
    /**
     * 关闭弹出框
     * @param jq
     * */
    close: function (jq) {
        var opts = $(jq).data("options");
        if (opts) {
            try {
                opts.onClose();
            } catch (e) {
            }
            Loading.hide(opts.loading);
            $("body").attr("scoll", null);
            $(opts.target).hide();
        }
    }
};
