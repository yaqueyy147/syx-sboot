(function($) {
	$(document).ready(function($) {
		//左侧tab标签切换
		
        //tabChange('#index-tabs', '#index-tabcontents');
        //顶部tab标签切换
        $("#dynamic-tabs").delegate("li", "click", function () {
            tablist = $(this).siblings('li');
            tablist.removeClass('tabs-on');
            $(this).addClass('tabs-on');
        });
        //顶部tab标签删除
        $("#dynamic-tabs").delegate(".dynamic-close", "click", function () {
            // deleteTab(this, '#dynamic-tabcontents');
			deleteTabNew(this);
        });
        //顶部tab标签箭头的状态
        var ul = window.parent.document.getElementById('dynamic-tabs');
        tabArrowStatus(ul);

        //初始化iframe高度和左侧菜单的高度
        // startInit("iframe1", "sidebar");

        //展开收起侧边栏
        $('#menuFolder').click(function () {
            $('#main').toggleClass('ml0');
            $('#sidebar').toggleClass('hide');
            $('#menuFolder').children('i').toggleClass('icon-unfold');
        });
        
        //初始化菜单
        getUserMenu();
	});
})(jQuery);

/**
 * 取得所有的用户菜单
 */
function getUserMenu(){
	$.ajax({
		type:"post",
		url: "/admin/backstage/getUserFunctions.htm",
		dataType : 'json',
		success:function(data){
            new Menus({id:"menu",data:data.result});
			if(data.result.length==0){
				$("#menu div span").html("您没有菜单...");
			}
		},error:function(XmlHttpRequest,textStatus, errorThrown){
    
		}
	});
	$(window).resize(function() {
		//menu
		$('#menu').height(($(document).height()-136));
		//所有的iframe
		var ul= $(".index-content >ul[class*='dynamic-nav']");
		$(".dynamic-nav-content").height($(window).height()-50-ul.height());
	});
}
