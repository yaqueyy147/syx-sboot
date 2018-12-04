var userMenu = [];
function Menus(config) {
    var all = {};
    var source = config.data;

    $.each(source, function (i, item) {

        var data = new MenuData();
        data.id = item.id;
        data.label = item.functionname;
        if(item.iconlink != null && item.iconlink != ''  && item.iconlink != 'undefined' ){
       	 	data.iconNm = item.iconlink;
        }
        if(item.linkurl != null && item.linkurl != ''  && item.linkurl != 'undefined'){
        	data.path = item.linkurl;
        }     
        
        if (item.parentid != null) {
        	data.parentId = item.parentid;
            if (data.parentId == '0') {
                data.parentId = "root";
            } 
            if (!all[data.parentId]) {
                all[data.parentId] = [];
            }
            userMenu[data.id] = data;
            all[data.parentId][all[data.parentId].length] = data;
        }       

    });

    var results = [];

    $.each(all['root'], function (i, item) {
        var functions = [];
        $.each(all[item.id], function (j, item2) {
            var three_menu = [];
            if (all[item2.id]) {
                $.each(all[item2.id], function (k, item3) {
                    var four_menu = [];
                    if (all[item3.id]) {
                        $.each(all[item3.id], function (l, item4) {
                            four_menu.push(item4);
                        });
                    }
                    item3.children = four_menu;
                    three_menu.push(item3);
                });
            }

            item2.children = three_menu;
            functions.push(item2);
        });

        item.children = functions;
        results.push(item);
    });
    config.data = results;
    this.config = $.extend({}, Menus.defaultConfig, config);
    this.init();
}

Menus.defaultConfig = {
    id: null,
    data: null
};
Menus.prototype = {
    init: function () {
        var that = this;
        $('#' + that.config.id).html('<ul class="first-menu"></ul>');
        //一级菜单
        $.each(that.config.data, function (i, item) {
            var html = [];
            html.push('<li>');
            if (item.children.length == 0 && item.path !='#') {
                // html.push('<a onclick="openMenu(\'zd' + item.id + '\')" href="' + item.path + '"  target="index">');
                html.push('<a onclick="openMenu(\'zd' + item.id + '\')" href="javascript:void 0;">');
            } else {
                html.push('<a href="javascript:void(0);" class="haschildren">');
            }
            html.push('<i class="icon-nav0' + item.iconNm + '"></i>');
            html.push(item.label);
            html.push('</a>');
            //二级菜单
            if (item.children && item.children.length > 0) {
                var icl = 0;
                var xg = "/";

                html.push('<ul class="second-menu" >');
                $.each(item.children, function (j, app) {
                    icl++;
                    html.push('<li>');
                    if (app.children.length == 0 && app.path !='#') {
                        // html.push('<a onclick="openMenu(\'zd' + app.id + '\')" href="' + app.path + '" target="index"><i class="i'+icl+'" style="background: url(\'/statics/admin/images/icon/00'+icl+'.png\') center top"></i>');
                        html.push('<a onclick="openMenu(\'zd' + app.id + '\')" href="javascript:void 0;">');
                        html.push("<i class=\"i"+icl+"\" style=\"background: url('/statics/admin/images/icon/00"+icl+".png') center top\"></i>");
                    } else {
                        html.push('<a href="javascript:void(0);" class="haschildren">');
                    }
                    html.push(app.label);
                    html.push('</a>');
                    //三级菜单
                    if (app.children && app.children.length > 0) {
                        html.push('<ul class="three-menu">');
                        $.each(app.children, function (k, _menu) {
                            html.push('<li>');
                            if (_menu.children.length == 0 && _menu.path !='#') {
                                // html.push('<a onclick="openMenu(\'zd' + _menu.id + '\')" href="' + _menu.path + '" target="index">');
                                html.push('<a onclick="openMenu(\'zd' + _menu.id + '\')" href="javascript:void 0;">');
                            } else {
                                html.push('<a href="javascript:void(0);" class="haschildren">');
                            }
                            html.push(_menu.label);
                            html.push('</a>');
                            //四级菜单
                            if (_menu.children && _menu.children.length > 0) {
                                html.push('<ul class="four-menu">');
                                $.each(_menu.children, function (l, _link) {
                                    html.push('<li>');
                                    if (_link.children.length == 0 && _link.path !='#') {
                                        // html.push('<a onclick="openMenu(\'zd' + _menu.id + '\')" href="' + _link.path + '" target="index">');
                                        html.push('<a onclick="openMenu(\'zd' + _menu.id + '\')" href="javascript:void 0;">');
                                    } else {
                                        html.push('<a href="javascript:void(0);" class="haschildren">');
                                    }
                                    html.push(_link.label);
                                    html.push('</a>');
                                    html.push('</li>');
                                });
                                html.push('</ul>');
                            }
                            html.push('</li>');
                        });
                        html.push('</ul>');
                    }
                    html.push('</li>');
                });
                html.push('</ul>');

            }
            html.push('</li>');

            $('#' + that.config.id + ' ul[class="first-menu"]').append(html.join(""));
        });


        $('#' + that.config.id).find('a').click(function () {
            $(this).parent('li').children('ul').slideToggle('normal', 'linear');
			var li = $(this).parent('li');
			li.toggleClass('active');
        });
    }
};
/***
 * 初始定义所有菜单的属性
 */
function MenuData() {
    this.id = '';
    this.label = '';
    this.iconNm = '1';
    this.path = '#';
    this.state = '';
    this.parentId = '';
    this.hashCode = '';
}

/**
 * 打开菜单
 */
function openMenu(id) {
    if ($(this).next().length > 0) {
        return;
    }
    var key = id.replace("zd", "");
    var data = userMenu[key];
    if(data.path !='#'){
    	createTab(data.id, data.label, data.path);
    }    
}
