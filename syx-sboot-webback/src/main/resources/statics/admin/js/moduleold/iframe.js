//初始化首页iframe的高度
function reinitIframe(iframeId) {
    var iframe = window.parent.document.getElementById(iframeId);
    var clientHeight;
    if (window.innerWidth)
        clientHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
        clientHeight = document.body.clientHeight;
    iframe.style.height = clientHeight - 105 + "px";
}
//初始化左侧菜单div的高度
function reinitMenu(menuId) {
    var menu = window.parent.document.getElementById(menuId);
    var clientHeight;
    if (window.innerWidth)
        clientHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
        clientHeight = document.body.clientHeight;
    menu.style.height = clientHeight - 145 + "px";
}
//开始初始化
function startInit(iframeId, menuId) {
    window.setInterval("reinitIframe('" + iframeId + "')", 500);
    window.setInterval("reinitMenu('" + menuId + "')",500);
}


/**
 *流程中iframe中有弹出层的情况，打开弹出层之前，
 *需要判断iframe的高度，如果高度低了，需要动态修改
 *参数一表示 obj 弹出层的div
 *参数二表示 需要调整的高度
 *参数三表示 iframe的id
 *@author hlf
 *@data 2016.4.29
 */
var pHeight = 0;
function openLayerBefore(obj, LHeight, iframeId) {
    try {
        var id = "processContentDiv";//默认iframe的id
        if (!isEmpty(iframeId)) {
            id = iframeId;
        }
        var iframe = window.parent.document.getElementById(id);
        var bHeight = 0;
        if (isChrome == false && isSafari == false)
            bHeight = iframe.contentWindow.document.documentElement.scrollHeight;

        var dHeight = 0;
        if (isFireFox == true)
            dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
        else if (isIE == false && isOpera == false)
            dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        else if (isIE == true && !-[1,] == false) {
        } //ie9+
        else
            bHeight += 3;
        pHeight = Math.max(bHeight, dHeight);
        if (pHeight < obj.height + LHeight) {//如果iframe的高度还没有弹出层的高
            $(iframe.contentWindow.document.body).height($(iframe.contentWindow.document.body).height() + LHeight);
            iframe.style.height = (pHeight + LHeight) + "px";
            //设置弹出层 top的高度
            var top = (pHeight + LHeight - obj.height) / 2;
            $("#" + obj.windowId).css("top", top + "px");
        }
    } catch (ex) {

    }
}
/**
 *流程中iframe中有弹出层的情况，关闭弹出层之前，
 *需要判断iframe的高度，如果高度低了，需要动态修改
 *参数一表示 obj 弹出层的div
 *参数二表示 需要调整的高度
 *参数三表示 iframe的id
 *@author hlf
 *@data 2016.4.29
 */

function closeLayerBefore(obj, LHeight, iframeId) {
    try {
        var id = "processContentDiv";//默认iframe的id
        if (!isEmpty(iframeId)) {
            id = iframeId;
        }
        var iframe = window.parent.document.getElementById(id);
        //取消之前先把滚动条置顶
        $(iframe).closest("html").scrollTop(0);
        //滚动条置顶之后，悬浮置顶应初始化
        $(".qiehuan").css("position", "relative");
        $(".qiehuan").css("margin-top", "0px");
        $("#empty").hide();

        var bHeight = 0;
        if (isChrome == false && isSafari == false)
            bHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        var dHeight = 0;
        if (isFireFox == true)
            dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
        else if (isIE == false && isOpera == false)
            dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        else if (isIE == true && !-[1,] == false) {
        } //ie9+
        else
            bHeight += 3;
        var newHeight = Math.max(bHeight, dHeight);
        if (pHeight + LHeight == newHeight) {//如果 iframe的高度更改过，则，还原
            if ($(iframe).parents("body").height() == 700) {
                iframe.style.height = (newHeight - LHeight + 30) + "px";
                $(iframe.contentWindow.document.body).height($(iframe.contentWindow.document.body).height() - LHeight + 30);
            } else {
                iframe.style.height = (newHeight - LHeight) + "px";
                $(iframe.contentWindow.document.body).height($(iframe.contentWindow.document.body).height() - LHeight);
            }
        }

    } catch (ex) {
    }
}