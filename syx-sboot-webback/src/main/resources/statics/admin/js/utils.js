//删除tab标签
function deleteTab(deleteid, tabcontentsid) {
    var currentTab = $(deleteid).parent();
    var index = currentTab.index() - 1;
    var tablist = currentTab.siblings('li');
    var ifm = $(tabcontentsid).find('iframe');
    var src = tablist.eq(index).children('a').attr('href');
    if (index > -1) {
        currentTab.remove();
        tablist.eq(index).addClass('tabs-on');
        ifm.attr('src', src);
    } else {
        tablist.eq(0).addClass('tabs-on');
        src = tablist.eq(0).children('a').attr('href');
        ifm.attr('src', src);
    }
    //箭头的状态
    var ul = window.parent.document.getElementById('dynamic-tabs');
    tabArrowStatus(ul);
}

function deleteTabNew(obj) {
    var id = $(obj).attr("data-id");
    var index = $(obj).parent().index();
    var lis = $("#dynamic-tabs li[name='tabli']");

    var openid;
    if(lis.length > 1){
        if(index > 1){
            openid = $("#li" + id).prev().attr("id").replace("li","");
        }else{
            openid = $("#li" + id).next().attr("id").replace("li","");
        }
        toggleTabPage(openid);
        $('#li' + openid).addClass('tabs-on').siblings().removeClass('tabs-on');
    }else{
        toggleTabPage("");
    }

    $("#dynamic-tabs li").remove("#li" + id);
    closeTabPage(id);
    //箭头的状态
    var ul = window.parent.document.getElementById('dynamic-tabs');
    tabArrowStatus(ul);
}

// 创建tab标签
function createTab(id, label, path) {
    var _a = $('#dynamic-tabs').find('a[id=zd' + id + ']');
    if (_a.length > 0) {
        //存在
        tablist = _a.parent('li').siblings('li');
        tablist.removeClass('tabs-on');
        _a.parent('li').addClass('tabs-on');
        //确定tab标签的位置
        hasTabPlace(_a.parent('li'));
        toggleTabPage(id);
    } else {
        //不存在
        // li_html = '<li><a href="' + path + '" id="zd' + id + '" target="index" style="width:50px;">' + label +
        li_html = '<li id="li' + id + '" name="tabli"><a href="javascript:void 0" id="zd' + id + '" style="width:50px;" onclick="toggleTabPage(\'' + id + '\')">' + label +
            '</a><span class="dynamic-close" data-id="' + id + '"> <i class="icon-dynaclose"></i></span></li>';
        $('#dynamic-tabs').append(li_html);
        $('#zd' + id).parent('li').addClass('tabs-on').siblings().removeClass('tabs-on');
        //确定tab标签的位置
        addTabPlace();
        addTabPage(id,path);
    }
}
//tab标签存在的情况下，tab标签的位置
function hasTabPlace(li) {
    var ul = window.parent.document.getElementById('dynamic-tabs');
    var ulWidth = $(ul).width();
    var liWidths = 0;
    $(li).prevAll('li').each(function (i) {
        liWidths += $(this).width();
    });
    //tab标签在可视区域(ul)的前面
    if (liWidths < ul.scrollLeft) {
        ul.scrollLeft = liWidths + 20;
    }
    //tab标签在可视区域(ul)的后面
    liWidths += $(li).width();
    if (liWidths > ul.scrollLeft + ulWidth) {
        ul.scrollLeft = liWidths - ulWidth + 200;
    }
    //箭头的状态
    tabArrowStatus(ul);
}
//tab标签不存在时，tab标签的位置
function addTabPlace() {
    var ul = window.parent.document.getElementById('dynamic-tabs');
    var ulWidth = $(ul).width();
    var liWidths = 0;
    $(ul).children('li').each(function (i) {
        liWidths += $(this).width();
    });
    ul.scrollLeft = liWidths - ulWidth + 200;
    //箭头的状态
    tabArrowStatus(ul);
}
//tab标签左右箭头的状态
function tabArrowStatus(ul) {
    var ulWidth = $(ul).width();
    var liWidths = 0;
    $(ul).children('li').each(function (i) {
        liWidths += $(this).width();
    });
    //控制左箭头的状态
    if (ul.scrollLeft === 0) {
        $('#arrowLeft').addClass('disable');
    }
    else {
        $('#arrowLeft').removeClass('disable');
    }
    //控制右箭头的状态
    if (liWidths <= ulWidth + ul.scrollLeft) {
        $('#arrowRight').addClass('disable');
    } else {
        $('#arrowRight').removeClass('disable');
    }
}

function moveRight() {
    var ul = window.parent.document.getElementById('dynamic-tabs');
    //ul的scrollLeft的旧值
    var oldLeftWidth = ul.scrollLeft;
    //ul的款的
    var ulWidth = $(ul).width();
    //初始化ul的lis的宽度为0
    var liWidths = 0;
    //计算lis的宽度
    $(ul).children('li').each(function (i) {
        liWidths += $(this).width();
    });
    //ul的scrollLeft的新值
    ul.scrollLeft += 200;
    //箭头的状态
    tabArrowStatus(ul);
}
function moveLeft() {
    var ul = window.parent.document.getElementById('dynamic-tabs');
    //ul的scrollLeft的旧值
    var oldLeftWidth = ul.scrollLeft;
    //ul的款的
    var ulWidth = $(ul).width();
    //初始化ul的lis的宽度为0
    var liWidths = 0;
    //计算lis的宽度
    $(ul).children('li').each(function (i) {
        liWidths += $(this).width();
    });
    //ul的scrollLeft的新值
    ul.scrollLeft -= 200;
    //箭头的状态
    tabArrowStatus(ul);
}
//普通tab标签切换
function tabChange(tabsid, tabcontentsid) {
    $(tabsid).children('li').click(function () {
        tablist = $(tabsid).children('li');
        contentlist = $(tabcontentsid).children('div');
        var i = $(this).index();
        tablist.removeClass();
        tablist.eq(i).addClass('tabs-on');
        contentlist.hide();
        contentlist.eq(i).show();
    });
}


//当浏览器窗口大小改变,执行操作
window.onresize = function () {
    //改变首页tab箭头的状态
    var ul = window.parent.document.getElementById('dynamic-tabs');
    tabArrowStatus(ul);
};

strTodate = function(time, format)
{
	var t = new Date(time);
	var tf = function(i){return (i < 10 ? '0' :'') + i};
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

function addTabPage(id,path) {
    var pagehtml = "<div class=\"tabcontents\" id=\"dynamic-tabcontents-" + id + "\" name=\"tabpage\">";
    pagehtml += "<iframe id=\"iframe" + id + "\" width=\"100%\" height=\"" + iframeHeight + "\" frameborder=\"0\" allowtransparency=\"true\" src=\"" + path + "\" class=\"yui-iframe\" name=\"index\">";
    pagehtml += "</iframe>";
    pagehtml += "</div>";
    $("#tabPageDiv").append(pagehtml);
    toggleTabPage(id);
    // $("#dynamic-tabcontents-" + id).height(iframeHeight);
    // $("#dynamic-tabcontents-" + id).width(iframeWidth);
}

function toggleTabPage(id) {
    var tabPages = $("#tabPageDiv").find("div[name='tabpage']");
    for(var i=0;i<tabPages.length;i++){
        var ii = tabPages[i];
        $(ii).hide();
    }
    $("#dynamic-tabcontents-" + id).show();
    if($.trim(id).length <= 0){
        $("#dynamic-tabcontents").show();
    }
}

function closeTabPage(id) {
    $("#tabPageDiv div").remove("#dynamic-tabcontents-" + id);
}