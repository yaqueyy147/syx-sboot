//内部构造方法
var Table = function Table() {
    this.name = "Table";
    this.version = '1.0.0';
}

//定义基本对象
$.Table = new Table();

// 插件的定义
$.fn.Table = function (options, param) {
    //如果为string，表示是方法调用
    if (typeof options == 'string') {
        var method = $.fn.Table.methods[options];
        if (method) {
            return method(this, param);
        }
    }

    options = options || {};
    var opts = $.extend({}, $.fn.Table.defaults, options);
    opts.id = $(this).attr("id");
    this.data("ztable", {options: opts});

    return this.each(function () {

        //构建Table标题栏上面的按钮区域
        var tb = $("<div class=\"operate datagrid-toolbar\"></div>").insertBefore(this);
        if (opts.toolbar) {
            $.each(opts.toolbar, function (n, toolbar) {
                tb.YButon($.extend({}, toolbar, {groupDiv: tb}));
            });
        } else {
            tb.css("display", "none");
        }

        //构建Table区域
        if (opts.width) {
            $(this).css("width", opts.width);
        }
        if (opts.height) {
            $(this).css("height", opts.height);
        }
        if (opts.columns) {

            var table_start = '<div class="table-scroll"><table class="' + opts.tableCls + '">',
                table_end = '</table></div>',
                table_header = '<thead class="datagrid-header">',
                table_header_end = '</thead>',
                talbe_body = '<tbody class="datagrid-body">',
                table_body_end = '</tbody>',
                header_html = '<tr>',
                display = 'display:table-cell';

            //复选框
            if (options.singleSelect) {
                display = 'display:none';
            }
            header_html += '<th id="datagrid-header-check" style="width: 40px;' + display + '">' +
                '<input name="ck" type="checkbox" title="全选"></th>';

            //行号
            if (opts.isRowNum) {
                header_html += '<th id="datagrid-header-rownumber" style="width: 60px;">行号</th>';
            }

            //遍历列，生成列头
            $.each(opts.columns[0], function (n, column) {
                var _width = "";
                if(column.opertrans!= null && column.opertrans == true){
                    if (column.width) {
                        _width = 'width:' + column.width + 'px;';
                    }
                    header_html += '<th style="' + _width + 'text-align:'
                        + column.align + '">' + column.opername + '</th>';
                }else{
                    if (column.width) {
                        _width = 'width:' + column.width + 'px;';
                    }
                    header_html += '<th field="' + column.field + '" style="' + _width + 'text-align:'
                        + column.align + '">' + column.title + '</th>';
                }
            });
            header_html += '</tr>';

            //构建table最基本的html结构
            $(this).append(table_start
                + table_header
                + header_html
                + table_header_end
                + talbe_body
                + '' //rows内容，在load中构建
                + table_body_end
                + table_end
                + '<div class="page datagrid-pager"></div>'
            );

            //设置默认的分页参数和值
            opts.queryParams.pageSize = opts.rows;
            if (opts.thisQueryPage) {
                opts.queryParams.pageNo = opts.thisQueryPage;
                opts.currentPage = opts.thisQueryPage;
                opts.thisQueryPage = 0;
            } else {
                opts.queryParams.page = opts.currentPage;
            }

            //缓存数据
            $(this).data("ztable", {options: opts});

            //加载数据，初始化时，默认请求数据一次
            if(opts.startInit){
                if (opts.url) {
                    loadData(this, options);
                } else {
                    createTableBody(this, opts.data);
                }
            }

        }
    });
};
$.fn.Table.methods = {

    /**
     * 获取form上面zdata-options绑定的数据
     * @param target 如：form
     * @return dataObj JSON数据
     * */
    bindOptions: function (target) {
        //绑定zdata-option
        var opts = $(target).attr("zdata-options");
        opts = opts.replaceAll("'", "\"");
        return $.parseJSON(opts);
    },
    /**
     * 获取缓存options
     * @parma jq
     * @return options
     * */
    getOptions: function (jq) {
        return $(jq).data("ztable").options;
    },

    /**
     * 获取选中行数据
     * @param jq
     * @return data
     * */
    getSelections: function (jq) {
        var returnData = [],
            options = $(jq).data("ztable").options,
            selectRows = options.selectRows,
            dataRows = options.data.list;

        for (var index in selectRows) {
            returnData.push(dataRows[selectRows[index]]);
        }

        return returnData;
    },
    /**
     * 重新加载数据
     * @param jq
     * @param param 键值对对象{k1:v1,k2:v2}，k1为rows，k2为page
     * */
    reload: function (jq, param) {
        var options = $(jq).data("ztable").options;
        if (param) {
            var rows = options.queryParams.rows;
            var page = 1;
            options.thisQueryPage = page;
            //判断是否为字符串，如果是，需要转换
            if (typeof param == 'string') {
                options.queryParams = $.extend(parserQueryParameter(param), {rows: rows, page: page});
            } else {
                options.queryParams = $.extend(param, {rows: rows, page: page});
            }

            $(jq).data("ztable").options.queryParams = options.queryParams;
        }
        loadData(jq, options);
    },
    /**
     * 选择一行
     * @param jq
     * @param param 主键
     * */
    selectRow: function (jq, param) {
        var options = $(jq).data("ztable").options,
            index = -1;

        for (var i = 0; i < options.data.list.length; i++) {
            //如果值相等，表示就是当前行
            if (options.data.list[i][options.idField] == param) {
                index = i;
            }
        }

        if (index > -1) {
            var $tr = $('#' + options.id).find("tr").eq(index + 1);
            $tr.find('input').each(function () {
                if (!$(this).attr('checked')) {
                    $(this).parent().parent().click();
                }
            });
        }
    },
    /**
     * 取消选择行
     * @param jq
     * @param param 主键
     * */
    unselectRow: function (jq, param) {

        var options = $(jq).data("ztable").options,
            index = -1;

        for (var i = 0; i < options.data.list.length; i++) {
            //如果值相等，表示就是当前行
            if (options.data.list[i][options.idField] == param) {
                index = i;
            }
        }

        if (index > -1) {
            var $tr = $('#' + options.id).find("tr").eq(index + 1);
            $tr.find('input').each(function () {
                if ($(this).attr('checked')) {
                    $(this).parent().parent().click();
                }
            });
        }
    },
    /**
     * 添加一行数据
     * @param jq
     * */
    addRow: function (jq) {
        //定义变量
        var options = $(jq).data("ztable").options,
            $table = $('#' + options.id),
            $body = $table.find('.datagrid-body').eq(0),
            $saveColumn,
            rowId = getRandom(100),
            rowsHtml = '<tr id="' + rowId + '">',//行数据HTML结构字符串
            display = 'display:table-cell',//复选框属性值
            length = $table.find('.datagrid-body').eq(0).find("tr").length;//tr的length

        //分页
        if (options.pagination) {
            $.YMessage.warning("警告", "分页的表格不能直接添加行");
            return;
        }
        //复选框
        if (options.singleSelect) {
            display = 'display:none';
        }
        rowsHtml += '<td  field="datagrid-td-check" value="' + rowId + '"  style="width: 40px;'
            + display + '" ><input name="ck" type="checkbox"  value="' + rowId + '"></td>';

        //行号
        if (options.isRowNum) {
            if ((length + 1) != options.rownumber) {
                options.rownumber = (length + 1);
                resetRowIndex(options.id);
            }
            rowsHtml += '<td field="datagrid-td-rownumber"  value="'
                + options.rownumber + '"  >' + options.rownumber + '</td>';
            options.rownumber++;
        }

        //遍历列
        $.each(options.columns[0], function (c, column) {
            var field = column.field,//列标识
                type = options.data.type[0][field],//列编辑类型
                tdHtml = '<td field="' + field + '" style="text-align:' + column.align + '">',//列字符串
                width = options.data.type[1].inputWidth,//列编辑宽度
                height = options.data.type[1].inputHeight;//列编辑高度
            switch (type) {
                case 'text':
                    tdHtml += '<input type="text" class="sui-input"  style="width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;"/>';
                    break;
                case 'date':
                    tdHtml += '<input type="text" class="sui-date" onclick="WdatePicker()" readonly  style="width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;"/>';
                    break;
                case 'textarea':
                    width = options.data.type[1].areaWidth;
                    height = options.data.type[1].areaHeight;
                    tdHtml += '<textarea class="sui-area" style="width:' + width + 'px;height:' + height + 'px"></textarea>';
                    break;
                default:
                    tdHtml += '<input type="text" class="sui-input"  style="width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;"/>';
            }
            tdHtml += '</td>';
            rowsHtml += tdHtml;
        });

        rowsHtml += '</tr>';
        $body.append(rowsHtml);
        $saveColumn = $body.children().last().children().last();
        $saveColumn.append('<a class="datagrid-btn btn-sblue ml10" style="padding: 4px 8px;">保存</a>');


        //焦点定位
        $saveColumn.parent().find('input:eq(1)').first().focus();
        //删除行点击事件
        $table.find('tr').unbind();
        //禁用checkbox
        $table.find(':checkbox[name=ck]').attr('disabled', 'disabled');


        //保存事件
        $saveColumn.find('.datagrid-btn').on('click', function (e) {
            //禁止冒泡事件
            e.stopPropagation();
            //定义变量
            var $row = $(this).parent().parent(),//当前行
                rowIndex = $row.index(),//当前行索引
                $btns = $table.find('.datagrid-body').eq(0).find('.datagrid-btn'),
                rowsData = options.data.list,//所有行数据
                rowData = {};//从表单上获取的编辑行的新数据

            //遍历
            $.each(options.columns[0], function (c, column) {
                var field = column.field,//列标识
                    type = options.data.type[0][field],//存储的对应type类型
                    tdObj = $row.find("td[field='" + field + "']"),//列对象
                    tdValue = '';//列的值

                switch (type) {
                    case 'text':
                        tdValue = tdObj.find('input').val();
                        break;
                    case 'date':
                        tdValue = tdObj.find('input').val();
                        break;
                    case 'textarea':
                        tdValue = tdObj.find('textarea').val();
                        break;
                    default :
                        tdValue = tdObj.find('input').val();
                }

                rowData[field] = tdValue;
                tdObj.attr('value', tdValue);
                tdObj.empty();
                if (column.formatter) {
                    tdObj.html(column.formatter(rowData, tdValue));
                } else {
                    tdObj.html(tdValue);
                }
            });

            options.onSave.call(this, rowIndex, rowData);

            //清除选中行
            options.selectRows = [];
            //绑定行点击事件
            if ($btns.length < 2) {
                //启用checkbox
                $table.find(':checkbox[name=ck]').removeAttr('disabled');
                rowClick($table);
            }

            //保存缓存数据
            rowsData.push(rowData);
            options.data.list = rowsData;
            //更新行数
            options.rows = rowsData.length;
            $table.data("ztable", {options: options});

        });
        $table.data("ztable", {options: options});
    },
    /**
     * 删除选中的行数据
     * @param jq
     * @return true|false
     * */
    deleteRow: function (jq) {
        //定义变量
        var options = $(jq).data("ztable").options,
            selectRows = options.selectRows,
            len = selectRows.length,
            rowsData = [],//删除的数据
            $table = $('#' + options.id);

        //没有选中任何行
        if (len === 0) {
            $.YMessage.warning("警告", "请选择要删除的表格行");
            return;
        }

        //循环删除对应的选中行
        for (var i = 0; i < len; i++) {
            //存储删除的行数据
            rowsData.push(options.data.list[selectRows[i]]);
            //删除行数据
            options.data.list.splice(selectRows[i] - i, 1);
        }

        //清空选中行
        options.selectRows = [];
        //取消checkbox选中状态
        $table.find('.datagrid-header').find(':input[type=checkbox]').removeAttr('checked');
        //缓存数据
        $table.data("ztable", {options: options});


        if (options.pagination) {
            loadData(jq, options);
        } else {
            createTableBody($table, options.data);
            options.onDelete.call(this, selectRows, rowsData);
        }
    },
    /**
     * 得到行数据
     * @param jq
     * */
    getRows: function (jq) {
        return $(jq).data("ztable").options.data.list;
    },
    /**
     *编辑选中行
     * @param jq
     * */
    editRow: function (jq) {
        //定义变量
        var options = $(jq).data("ztable").options,
            rowsDate = options.data.list,//当前所有行数据
            thisIndex = -1,//找到需要修改的行
            selectRows = options.selectRows,
            len = selectRows.length,//选中行的length
            $table = $('#' + options.id),//表格
            trsObj = $table.find("tr"),//表格中的trs
            trObj;//操作行

        //没有选中编辑行
        if (len <= 0) {
            $.YMessage.warning("警告", "请选择要编辑的表格行！");
            return false;
        }

        //清除选中行数据
        options.selectRows = [];
        //清除checkbox的选中状态
        trsObj.find(':checkbox[name=ck]').removeAttr('checked');
        //禁用checkbox
        trsObj.find(':checkbox[name=ck]').attr('disabled', 'disabled');

        //删除行点击事件
        trsObj.unbind();

        //循环选中行
        for (var i = 0; i < len; i++) {
            thisIndex = selectRows[i];
            if (thisIndex < 0) {
                return;
            }
            //获取当前行对象
            trObj = trsObj.eq(thisIndex + 1);
            trObj.find('td').css('background-color', 'transparent');

            //遍历列
            $.each(options.columns[0], function (c, column) {
                var field = column.field,//列标识
                    type = options.data.type[0][field],//列编辑类型
                    tdObj = $(trObj).find("td[field='" + field + "']"),//列对象
                    tdText = $(tdObj).attr("value"),//列对象的值
                    width = options.data.type[1].inputWidth,//列编辑宽度
                    height = options.data.type[1].inputHeight;//列编辑高度
                if (type != 'readOnly') {
                    tdObj.empty();
                }
                switch (type) {
                    case 'text':
                        tdObj.append('<input type="text" class="sui-input" value="' + tdText
                            + '" style="width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;"/>');
                        break;
                    case 'date':
                        tdObj.append('<input type="text" class="sui-date" onclick="WdatePicker()" readonly  value="' + tdText + '" style="width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;"/>');
                        break;
                    case 'textarea':
                        width = options.data.type[1].areaWidth;
                        height = options.data.type[1].areaHeight;
                        tdObj.append('<textarea class="sui-area" style="width:' + width + 'px;height:' + height + ' px" >' + tdText + '</textarea>');
                        break;
                }
            });
            trObj.find('td').last().append('<a class="datagrid-btn btn-sblue ml10" style="padding: 4px 8px;">保存</a>');

            //保存事件
            trObj.find('.datagrid-btn').on('click', function () {
                //定义变量
                var newDate = {},//从表单上获取的编辑行的新数据
                    $that = $(this),
                    $btns = $table.find('.datagrid-body').eq(0).find('.datagrid-btn');

                //遍历
                $.each(options.columns[0], function (c, column) {
                    var field = column.field,
                        type = options.data.type[0][field],
                        tdObj = $that.parent().parent().find("td[field='" + field + "']"),
                        tdValue = '';
                    switch (type) {
                        case 'text':
                            tdValue = tdObj.find('input').val();
                            break;
                        case 'date':
                            tdValue = tdObj.find('input').val();
                            break;
                        case 'textarea':
                            tdValue = tdObj.find('textarea').val();
                            break;
                        default ://readOnly
                            tdValue = tdObj.attr('value');
                    }
                    newDate[field] = tdValue;
                    rowsDate[thisIndex][field] = tdValue;
                    tdObj.attr('value', tdValue);
                    tdObj.empty();
                    if (column.formatter) {
                        $(tdObj).html(column.formatter(newDate, tdValue));
                    } else {
                        $(tdObj).html(tdValue);
                    }
                });

                options.onSave.call(this, thisIndex, newDate);

                if ($btns.length < 2) {
                    //启用checkbox
                    trsObj.find(':checkbox[name=ck]').removeAttr('disabled');
                    $table.data("ztable", {options: options});
                    rowClick($table);
                }
            });

        }

        //更新保存数据
        options.data.list = rowsDate;
        $table.data("ztable", {options: options});
    }
};
$.fn.Table.defaults = {
    url: null,//分页路径
    data: null,//表格数据
    queryParams: {},//请求参数
    id: null,//table的Id
    idField: 'id',//表格行主键字段，没有默认为Id
    columns: null,//表格列集合定义
    toolbar: null,//按钮定义
    pagination: true, //是否分页
    total: 0,//总条数
    width: null,//table的宽度
    height: null,//table的高度
    isRowNum: true,//是否显示行号rownumbers
    rownumber: 0,//行号
    rows: 5,//每页条数
    selectRows: [],//选中的行数据
    currentPage: 1,//当前页
    totalPage: 0,//总页数
    rowcheck: true,//是否显示复选框
    singleSelect: false,//是否单选
    startInit:true,//是否进入页面是就初始化表格
    onUnselect: function (rowIndex, rowData) {
    },
    onSelect: function (rowIndex, rowData) {
    },
    onLoadSuccess: function () {
        $.YMessage.success("加载成功", "表格数据加载成功");
    },
    onSave: function (rowIndex, rowData) {
    },
    onDelete: function (rowIndex, rowData) {

    }
};
/**
 * 获取随机数
 * 1）获取0-100的随机数——getRandom(100);
 * 2）获取0-999的随机数——getRandom(999);
 * 3）以此类推…
 * */
function getRandom(n) {
    return Math.floor(Math.random() * n + 1)
}

/**
 * 解析URL查询参数，并转换为json形式
 * @param queryParameter
 * @returns {*}
 */
function parserQueryParameter(queryParameter) {
    if (!queryParameter) return;
    var paramsArr = queryParameter.split('&');
    var args = {}, param, name, value;
    //遍历数组
    for (var i = 0; i < paramsArr.length; i++) {
        //获取参数
        param = paramsArr[i].split('=');
        //获取参数名和值
        name = param[0];
        value = param[1];
        if (name == "") {
            name = "unkown";
        }
        if (typeof args[name] == "undefined") {
            //参数尚不存在
            args[name] = value;
        } else if (typeof args[name] == "string") {
            //参数已经存在则保存为数组
            args[name] = [args[name]];
            args[name].push(value);
        } else {
            //已经是数组的
            args[name].push(value);
        }
    }
    //以json格式返回获取的所有参数
    return args;
}

/**
 * 添加行点击事件
 * @param obj
 */
function rowClick(obj) {
    var options = $(obj).data("ztable").options,
        nowIndex,
        _index,
        $table = $('#' + options.id);
    if (options.singleSelect) {
        //单选行点击事件
        $table.find("tr").unbind("click").bind("click", function () {
            // 保存tr对象索引
            var that = this;
            $(this).find(':checkbox[name=ck]').each(function () {
                // 第一次点击
                if (!nowIndex) {
                    $(this).attr('checked', 'checked');
                    nowIndex = $(this);
                    // 保存第一次点击时的选中行的下标
                    _index = $(that).index();
                    options.selectRows.length = 0;

                    // 将当前选中行的下标push进数组中
                    var myIndex = $(that).index();
                    options.selectRows.push(myIndex);

                    // 为当前行的td添加选中色
                    $(this).parent().parent().find('td').each(function () {
                        $(this).css('background-color', '#D5C8C8');
                    });
                    options.onSelect.call(this, myIndex, options.data.list[myIndex]);
                } else { // 第二次点击
                    var inIndex = $(that).index();
                    // 不管是否选中的是自己，先重置样式
                    nowIndex.parent().parent().find('td').each(function () {
                        $(this).css('background-color', 'white');
                    });
                    // 判断是否选中自己
                    if (_index == inIndex) {
                        // 如果选中的为自己，那么去掉隐藏checkbox的选中，并将选中数组清空
                        nowIndex.removeAttr('checked');
                        $(nowIndex).prop("checked",false);
                        nowIndex = null;
                        options.selectRows.length = 0;
                        options.onUnselect.call(this, inIndex, options.data.list[inIndex]);
                    } else {
                        // 将以前选中的去掉，选中现在行
                        nowIndex.removeAttr('checked');
                        $(nowIndex).prop("checked",false);
                        $(this).attr('checked', 'checked');
                        // 保存当前选中行的下标
                        _index = $(that).index();
                        nowIndex = $(this);

                        // 清空上次选中数据
                        options.selectRows.length = 0;
                        // 将这次选中行的下标保存起来
                        var myIndex = $(that).index();
                        options.selectRows.push(myIndex);

                        // 添加选中行修改样式
                        $(this).parent().parent().find('td').each(function () {
                            $(this).css('background-color', '#D5C8C8');
                        });

                        options.onSelect.call(this, myIndex, options.data.list[myIndex]);
                    }
                }
            });
        });
    } else {
        //多选行点击事件
        $table.find('.datagrid-body').find("tr").unbind("click").bind("click", function () {
            //计算实际有多少行
            var realRows = $table.find("tbody").find("tr").length;

            var _index = $(this).index();
            var header_input = $table.find('.datagrid-header').find(':input');
            $(this).find(':input').each(function () {
                //如果取消选择，从selectRows中删除选中项
                //否则，向selectRows中添加选中项
                if ($(this).attr('trSelect')) {
                    $(this).removeAttr('checked');
                    $(this).prop("checked",false);
                    $(this).removeAttr('trSelect');
                    var _selectIndex = -1;
                    for (var i = 0; i < options.selectRows.length; i++) {
                        if (_index == options.selectRows[i]) {
                            _selectIndex = i;
                        }
                    }
                    options.selectRows.splice(_selectIndex, 1);
                    $(this).parent().parent().find('td').each(function () {
                        $(this).css('background-color', 'white');
                    });
                    //控制全选按钮的状态
                    if (realRows == options.selectRows.length) {
                        header_input.attr('checked', 'checked');
                        header_input.attr('trSelect', true);
                    } else {
                        header_input.removeAttr('checked');
                        $(header_input).prop("checked",false);
                        header_input.removeAttr('trSelect');
                    }
                    options.onUnselect.call(this, _index, options.data.list[_index]);
                } else {
                    if ($(this).attr('checked')) {
                        $(this).attr('trSelect', true);
                        options.selectRows.push(_index);
                        $(this).parent().parent().find('td').each(function () {
                            $(this).css('background-color', '#D5C8C8');
                        });
                        options.onSelect.call(this, _index, options.data.list[_index]);
                    } else {
                        $(this).attr('checked', 'checked');
                        $(this).attr('trSelect', true);
                        options.selectRows.push(_index);
                        $(this).parent().parent().find('td').each(function () {
                            $(this).css('background-color', '#D5C8C8');
                        });
                        options.onSelect.call(this, _index, options.data.list[_index]);
                    }
                    //控制全选的状态
                    if (realRows == options.selectRows.length) {
                        header_input.attr('checked', 'checked');
                        header_input.attr('trSelect', true);
                    } else {
                        header_input.removeAttr('checked');
                        $(header_input).prop("checked",false);
                        header_input.removeAttr('trSelect');
                    }
                }
            });
            $('#' + options.id).data("ztable", {options: options});

        });

        //全选与反选行点击事件
        $table.find('.datagrid-header').find('tr').unbind("click").bind("click", function () {
            var rows = options.rows,
                i = 0,
                selectRows = [],
                ck = $table.find(':checkbox[name=ck]');


            if (!$(this).find(':input').attr("trSelect")) { //全选

                //计算实际有多少行
                var realRows = $(this).parent().parent().find("tbody").find("tr");
                var realRowN = realRows.length;

                //如果table设置的行数大于实际显示的行数，则设置全选的行数为实际显示的行
                if(rows > realRowN){
                    rows = realRowN;
                }

                for (i = 0; i < rows; i++) {
                    selectRows.push(i);
                }
                $(this).find(':input').attr('checked', 'checked');
                $(this).find(':input').attr('trSelect', true);
                ck.attr('checked', 'checked');
                ck.attr('trSelect', true);
                ck.parent().parent().find('td').each(function () {
                    $(this).css('background-color', '#D5C8C8');
                });
            } else {//反选
                $(this).find(':input').removeAttr('checked');
                $(this).find(':input').prop("checked",false);
                $(this).find(':input').removeAttr('trSelect');
                ck.removeAttr('checked');
                $(ck).prop("checked",false);
                ck.removeAttr('trSelect');
                ck.parent().parent().find('td').each(function () {
                    $(this).css('background-color', 'white');
                });
            }

            options.selectRows = selectRows;
            $('#' + options.id).data("ztable", {options: options});

        });
    }


}

/**
 * 加载数据
 * @param obj
 * @param opts
 */
function loadData(obj, opts) {
    //获取缓存数据
    var options = $(obj).data("ztable").options;
    //设置默认的分页参数和值
    options.queryParams.pageSize = options.rows;
    if (opts.thisQueryPage) {
        options.queryParams.pageNo = opts.thisQueryPage;
        options.currentPage = opts.thisQueryPage;
        opts.thisQueryPage = 0;
    } else {
        options.queryParams.pageNo = options.currentPage;
    }
    //loading遮挡层
    options.loading = Loading.show(options.id);
    $.ajax({
        async: true,
        type: "get",
        dataType: "jsonp",
        url: options.url,//要访问的后台地址
        data: options.queryParams,//每页条数queryParams.rows，当前请求页数queryParams.page
        crossDomain: true,
        complete: function () {
            //关闭遮挡层
            Loading.hide(options.loading);
        },
        success: function (data) {
            createTableBody(obj, data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.YMessage.warning("提示", "网络请求出错,请联系管理员");
        }
    });
}

/**
 * 渲染body
 * @param obj
 * @param json ajax请求的数据或options.data
 */
function createTableBody(obj, json) {
    //获取缓存数据
    var options = $(obj).data('ztable').options;
    options.data = json.page;
    options.total = options.data.count;

    //判断是否分页，如果不分页，显示全部
    if (!options.pagination) {
        options.rows = options.data.list.length;
        options.currentPage = 1;
    }

    //缓存数据
    $(obj).data("ztable", {options: options});

    //总页数 = 总条数 % 每页显示条数
    if (options.total % options.rows == 0) {
        options.totalPage = parseInt(options.total / options.rows);
    } else {
        options.totalPage = parseInt(options.total / options.rows + 1);
    }
    //行号
    options.rownumber = (options.currentPage - 1) * options.rows;

    if (options.currentPage == 0) {
        options.currentPage = 1;
    }
    if (options.rownumber == 0) {
        options.rownumber = 1;
    } else {
        options.rownumber += 1;
    }
    //翻页后清空选择中的数据
    options.selectRows = [];
    //取消全选状态
    $('#' + options.id).find('.datagrid-header').find(':input[type=checkbox]').removeAttr('checked').removeAttr('trselect');
    //缓存数据
    $(obj).data("ztable", {options: options});
    //构建表格行
    var rows_html = "";

    //遍历行记录
    var index = options.rows;
    var dataList = options.data.list;
    // if(options.data.count > 0){
    if(dataList){
        // if(options.data.count > 0){
        $.each(options.data.list, function (r, row) {
            if (index > 0) {
                //主键
                if (row[options.idField]) {
                    rows_html += '<tr id="' + row[options.idField] + '">';
                } else {
                    rows_html += '<tr>';
                }
                //复选是否可见
                var _display = 'display:table-cell';
                if (options.singleSelect) {
                    _display = 'display:none';
                }
                rows_html += '<td  field="datagrid-header-check"   value="' + row[options.idField]
                    + '"  style="width: 40px;' + _display + '" ><input name="ck" type="checkbox"  value="'
                    + row[options.idField] + '"></td>';

                //行号
                if (options.isRowNum) {
                    rows_html += '<td field="datagrid-td-rownumber"  class="datagrid-row-selected" value="'
                        + options.rownumber + '" >' + options.rownumber + '</td>';
                    options.rownumber++;
                }

                //遍历列,添加行数据
                $.each(options.columns[0], function (c, column) {
                    //判断是否有自定义函数绑定
                    if (column.formatter) {
                        rows_html += '<td  field="' + column.field + '" value="' + row[column.field]
                            + '" style="text-align:' + column.align + '">'
                            + undefinedToStr(column.formatter(row, row[column.field])) + '</td>';
                    } else if(column.opertrans){
                        rows_html += '<td  field="' + column.field + '" value="' + row[column.field]
                            + '" style="text-align:' + column.align + '">'
                            + column.opertemplate + '</td>';
                    }else {
                        rows_html += '<td  field="' + column.field + '" value="' + row[column.field]
                            + '"style="text-align:' + column.align + '">'
                            + undefinedToStr(row[column.field]) + '</td>';
                    }
                });
                rows_html += '</tr>';
                index = index - 1;
            }
        });
        // }
    }else{
        var columns = $('#' + options.id).find("thead").find("tr").find("th").length;
        rows_html = "<tr><td colspan='" + columns + "'>暂无数据！</td></tr>";
    }
    //表格行内容添加
    $('#' + options.id).find('.datagrid-body').eq(0).html(rows_html);

    //绑定事件,行点击事件
    rowClick(obj);

    //禁止事件冒泡
    $('#' + options.id).find('.datagrid-body').find('tr td a').each(function () {
        var rowIndex = $(this).parent().parent().index();
        var rowData = options.data.list[rowIndex];
        var click = $(this).attr("onclick");
        $(this).removeAttr("onclick");
        if (click) {
            $(this).click(function (event) {
                CALLBACK[click](rowIndex, rowData);
                event.stopPropagation();
            });
        }
    });

    $('#' + options.id).find('.datagrid-body').find('tr td a').each(function () {
        var rowIndex = $(this).parent().parent().index();
        var rowData = options.data.list[rowIndex];
        var handler = $(this).attr("handler");
        $(this).removeAttr("handler");
        if (handler) {
            $(this).click(function (event) {
                CALLBACK[handler](rowIndex, rowData);
                event.stopPropagation();
            });
        }
    });

    //显示分页
    if (options.pagination) {
        showPage(obj);
    }
}

/**
 * 构建分页区域
 * @param obj
 */
function showPage(obj) {
    var options = $(obj).data("ztable").options;
    //总记录数
    var html = ' <span>共有' + options.total + '条记录</span>';
    // var html = '';
    //当前页
    html += '<span>当前第&nbsp;(<span style="color: #0000ff;margin-right: 0px">' + options.currentPage + '</span>)&nbsp;页</span>';
    //上一页
    if (options.currentPage > 1 && options.currentPage <= options.totalPage) {
        html += '<a href="javascript:void(0);" class="page-pre" title="'
            + parseInt(options.currentPage - 1) + '">上一页</a>'
    }
    // //当前页的前2页
    // for (var i = 2; i > 0; i--) {
    // 	if (parseInt(options.currentPage) > i) {
    // 		html += '<a href="javascript:void(0);" title="' + (parseInt(options.currentPage) - i) + '">'
    // 			+ (parseInt(options.currentPage) - i) + '</a>';
    // 	}
    // }
    // //当前页
    // html += '<a href="javascript:void(0);" class="page-on" title="'
    //    + options.currentPage + '">' + options.currentPage + '</a>';
    // //当前页的后2页
    // for (var i = 1; i < 3; i++) {
    // 	if (parseInt(options.currentPage) + i < parseInt(options.totalPage)) {
    // 		html += '<a href="javascript:void(0);" title="' + (parseInt(options.currentPage) + i) + '">'
    // 			+ (parseInt(options.currentPage) + i) + '</a>';
    // 	}
    // }
    // //总页数
    // if (options.totalPage > parseInt(options.currentPage)) {
    // 	html += '<span style="margin:0;">...</span>';
    // 	html += '<a href="javascript:void(0);" title="' + options.totalPage + '">'
    // 		+ (parseInt(options.totalPage)) + '</a>';
    // }
    //下一页
    if (parseInt(options.currentPage) < parseInt(options.totalPage) && parseInt(options.totalPage) > 1) {
        html += '<a href="javascript:void(0);" class="page-next" title="'
            + parseInt(options.currentPage + 1) + '">下一页</a>'
    }
    //跳转
    html += '<span style="margin-left: 10px;margin-right: 0;">到第</span>';
    html += '<input id="datagrid_currentPage_input" name="currentPage" type="text" class="hlideal-input" ' +
        'style="width:25px; text-align:center;" value="' + options.currentPage + '" >';
    html += '<span>页</span>';
    html += ' <button type="button" class="btn-blue" style="padding:0 10px;">跳转</button>';
    //插入html
    //$(obj).append(html);
    $(obj).find('.datagrid-pager').eq(0).html(html);
    //点击链接时
    $(obj).find("div[class*='datagrid-pager'] a").on({
        click: function (e) {
            options.currentPage = parseInt($(this).attr("title"));
            // var curPage = parseInt($(this).attr("title"));
            // if(parseInt(curPage) < 1){
            //     curPage = 1;
            // }
            // options.currentPage = curPage;

            //缓存当前页
            $(obj).data("ztable").options.currentPage = parseInt($(this).attr("title"));
            loadData(obj, options);
        }
    });

    //点击跳转时
    $(obj).find("div[class*='datagrid-pager'] :button").on({
        click: function (e) {
            var page = $(obj).find('#datagrid_currentPage_input').val();
            if (isNaN(page)) {
                page = 1;
            }
            var n = parseInt(page);
            if (n < 1) {
                n = 1;
            }
            if (n > parseInt(options.totalPage)) {
                n = options.totalPage;
            }
            $(obj).find('#datagrid_currentPage_input').val(n);
            options.currentPage = n;
            //缓存当前页currentPage
            $(obj).data("ztable").options.currentPage = options.currentPage;
            loadData(obj, options);
        }
    });
}

/**
 * 将undefined转换成""
 * @param str
 * @returns
 */
function undefinedToStr(str) {
    if (typeof str == "string") {
        if (str == "undefined") {
            str = "";
        }
    }
    if (typeof(str) == "undefined") {
        str = "";
    }
    return str;
}

/**
 * 设置行号
 * @param id
 * */
function resetRowIndex(id) {
    $('#' + id).find('.datagrid-body').eq(0).find("tr").each(function (i, row) {
        $(this).find('td[field="datagrid-td-rownumber"]').html((i + 1));
    });
}

var table = new Table();