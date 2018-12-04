define(function (require, exports, module) {

	var $ = require('jquery');
	//var YUI = require('hlideal/jquery.hlideal.form');
	require('ztree');
	//内部构造方法
	function YComboTree() {
		this.name = "YComboTree";
		this.version = '1.0.0';
	}

	//定义基本对象
	$.YComboTree = new YComboTree();

	// 插件的定义
	$.fn.YComboTree = function (options, param) {
		//如果为string，表示是方法调用
		if (typeof options == 'string') {
			var method = $.fn.YComboTree.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};

		var opts,
			valueField,
			textField,
			idFiled,
			parentIdField,
			defaultvalue,
			expandall,
			textelementid;

		if (!options.valueField) {
			valueField = $(this).data('valuefield');
			textField = $(this).data('textfield');
			idFiled = $(this).data('idFiled');
			parentIdField = $(this).data('parentIdField');
			textelementid = $(this).data('textelementid');
			options.valueField = valueField;
			options.textField = textField;
			options.parentIdField = parentIdField;
			options.idFiled = idFiled;
			options.textelementid = textelementid;
		}
		if (!options.value) {
			defaultvalue = $(this).data('defaultvalue');
			options.value = defaultvalue;
		}
		if (!options.expandAll) {
			expandall = $(this).data('expandall');
			if (expandall) {
				options.expandAll = expandall;
			}
		}

		opts = $.extend({}, $.fn.YComboTree.defaults, options);
		opts.target = this;
		$(opts.target).data("options", opts);
		if (!opts.data) {   //不存在数据
			if (!opts.url) {//不存在url
				opts.url = $(this).data('url');
			}
			if (opts.url) {
				$.ajax({
					async: false,//默认为true，异步
					type: "get",
					dataType: "jsonp",
					url: opts.url,//要访问的后台地址
					data: options.queryParams,  //每页条数，当前请求页数
					crossDomain: true,
					success: function (data) {//msg为返回的数据，在这里做数据绑定
						opts.data = data;
						opts.target.each(function () {
							var method = $.fn.YComboTree.methods["init"];
							method(this);
						});
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						alert("请求出错：XMLHttpRequest.status=" + XMLHttpRequest.status + "\n XMLHttpRequest.readyState=" + XMLHttpRequest.readyState + "\n textStatus=" + textStatus);
					}
				});
				return;
			} else {
				//抓取数据
				opts.data = eval($(this).data('data'));
			}
		}
		return this.each(function () {
			var method = $.fn.YComboTree.methods["init"];
			method(this);
		});


	};
	$.fn.YComboTree.methods = {
		getValue: function (jq) {
			var _value = $(jq).val();
			if (!_value) {
				_value = $(jq).data('val');
			}
			return _value;
		},
		getText: function (jq) {
			return $(jq).data('text');
		},
		setValue: function (jq, value) {
			var opts = $(jq).next().find("ul").data("options");
			if (!opts) {
				return;
			}
			var treeObj = opts.treeObj;
			var nodes = treeObj.transformToArray(treeObj.getNodes());
			var saveData = [];
			var values = "";
			var texts = "";
			var _i = 0;
			$.each(nodes, function (i, item) {
				var val = item[opts.valueField];
				treeObj.checkNode(item, false, true);
				treeObj.cancelSelectedNode(item);
				if (val == value) {
					//如果是父节点并且不获取父节点的值 则跳过
					if (item.isParent && (!opts.parentValue)) {
						return true;
					}
					if (_i > 0) {
						values += ",";
						texts += ",";
					}
					if (opts.multiple) {
						treeObj.checkNode(item, true, true);
						saveData.push(item);
					} else {
						treeObj.selectNode(item, true);
						saveData = item;
					}
					values += val;
					texts += item[opts.textField];
					_i++;
				}
			});
			$(opts.treeTarget).data("treeData", opts.treeData);
			$(opts.target).attr("value", values);
			$(opts.treeTarget).parent().prev().children('a').text(texts);
		},
		setValues: function (jq, paremsvalues) {
			var opts = $(jq).next().find("ul").data("options");
			if (!opts) {
				return;
			}
			var treeObj = opts.treeObj;
			var nodes = treeObj.transformToArray(treeObj.getNodes());
			var saveData = [];
			var values = "";
			var texts = "";
			var _j = 0;
			$.each(nodes, function (i, item) {
				var val = item[opts.valueField];
				treeObj.checkNode(item, false, true);
				treeObj.cancelSelectedNode(item);
				
				var t_values = paremsvalues.split(",");
				for (var _i = 0; _i < t_values.length; _i++) {	
					if (val == t_values[_i]) {//若value相同
						//如果是父节点并且不获取父节点的值 则跳过
						if (item.isParent && (!opts.parentValue)) {
							return true;
						}
						if (_j > 0) {
							values += ",";
							texts += ",";
						}
						
						treeObj.checkNode(item, true, true);
						saveData.push(item);
						
						values += val;
						texts += item[opts.textField];
						_j++;
					}
				}
			});
			$(opts.treeTarget).data("treeData", opts.treeData);
			$(opts.target).attr("value", values);
			$(opts.treeTarget).parent().prev().children('a').text(texts);
		},
		init: function (jq) {
			var opts = $(jq).data('options'),
				text = opts.textField,
				idFiled = opts.idFiled
				parentIdField = opts.parentIdField,
				dtHtml = '<dt class="yui-mselect"><a href="javascript:void (0);"></a></dt>',
				ulHtml = '<dd class="yui-option"><ul id="zd' + getRandom(999) + '" class="ztree" style=" overflow:auto;overflow-x:hidden"></ul>',
				html = '<dl class="yui-select-panel">' + dtHtml + ulHtml + '</dd></dl>';

			$(jq).hide();
			$(jq).after(html);
			opts.dlTarget = $(jq).next();
			opts.treeTarget = $(jq).next().find("ul");


			//创建ztree
			var checkStatus = false;
			if (opts.multiple) {
				checkStatus = true;
			}
			var setting = {
				check: {
					enable: checkStatus
				},
				data: {
					simpleData: {
						enable: true,
						pIdKey: parentIdField,
						idKey: idFiled
					},
					key: {
						name: text
					}
				},
				callback: {
					onCheck: treeNodesCheck,
					onClick: treeNodeClick
				}
			};

			opts.setting = setting;
			opts.treeObj = $.fn.zTree.init(opts.treeTarget, setting, opts.data);

			//缓存options
			opts.target.data("options", opts);
			opts.treeTarget.data("options", opts);

			//展开ztree
			if (opts.expandAll) {
				opts.treeObj.expandAll(true);
			}

			//展开ztree
			$(jq).next().find("dt").click(function () {
				var self = $(this);
				self.parent().css({
					zIndex: '100'
				});
				self.siblings('dd').slideToggle();
			});

			//收起ztree
			$(jq).next().mouseleave(function () {
				var self;
				self = $(this);
				self.find('dd').hide();
				self.css({
					zIndex: '1'
				});
			});

			//设置默认的值
			var method = $.fn.YComboTree.methods["setValue"];
			method(opts.target, opts.value);
		}
	};
	// 插件的defaults
	$.fn.YComboTree.defaults = {
		id:'',//唯一标识
		valueField: "id",//值字段
		textField: "text",//文本值
		textelementid: null,
		idFiled: "id", //ID
		parentIdField: "pId",//父节点字段
		url: null,//远程数据地址
		data: null,//本地数据
		multiple: false,//是否多选
		parentValue: true,//是否获取父节点的值
		expandAll: false,//是否展开全部
		value: null,//默认值
		treeData: {},//选中的节点集合
		treeObj: null,//构建ztree返回的对象
		target: null,//$('input[type=hidden]')
		dlTarget: null,//$('dl')
		treeTarget: null,//$('ul')
		setting: null//配置
	};
	/**
	 * ztree节点点击事件
	 * */
	function treeNodeClick(event, treeId, treeNode) {
		var opts = $(event.currentTarget).data('options');
		if (!opts.multiple) {
			//如果是父节点并且不获取父节点的值 则跳过
			if (treeNode.isParent && (!opts.parentValue)) {
				opts.treeData = null;
				$(opts.target).attr("value", "");
				$(opts.treeTraget).parent().prev().children('a').text("");
				return true;
			}
			var val = treeNode[opts.valueField];
			var text = treeNode[opts.textField];
			opts.treeData = treeNode;
			$(opts.treeTarget).data("treeData", opts.treeData);
			$(opts.target).attr("value", val);
			$(opts.treeTarget).parent().prev().children('a').text(text);
			$(opts.treeTarget).parent().hide();

			if(opts.textelementid != null && opts.textelementid != ''){
				$("#" + opts.textelementid).val(text);
			}
			//清除验证失败提示信息
			if (val || val.length > 0) {
				try {
					//YUI.cleanValidateText(opts.target.parent());
				} catch (e) {
				}
			}

		}
	}

	/**
	 * ztree节点check事件
	 * */
	function treeNodesCheck(event, treeId, treeNode) {
		var opts = $(event.currentTarget).data('options');
		opts.treeData = opts.treeObj.getCheckedNodes(true);
		var values = "";
		var texts = "";
		var _i = 0;
		var saveData = [];
		$.each(opts.treeData, function (i, item) {
			//如果是父节点并且不获取父节点的值 则跳过
			if (item.isParent && (!opts.parentValue)) {
				return true;
			}
			if (_i > 0) {
				values += ",";
				texts += ",";
			}
			values += item[opts.valueField];
			texts += item[opts.textField];
			saveData.push(item);
			_i++;
		});
		opts.treeData = saveData;
		$(opts.treeTarget).data("options", opts);

		$(opts.target).attr("value", values);
		
		if(opts.textelementid != null && opts.textelementid != ''){
			$("#" + opts.textelementid).val(texts);
		}

		$(opts.treeTarget).parent().prev().children('a').text(texts);
		//清除验证失败提示信息
		if (values || values.length > 0) {
			//YUI.cleanValidateText(opts.target.parent());
		}
	}

	/**
	 * 获取随机数
	 * 1）获取0-100的随机数——getRandom(100);
	 * 2）获取0-999的随机数——getRandom(999);
	 * 3）以此类推…
	 * */
	function getRandom(n) {
		return Math.floor(Math.random() * n + 1)
	}

});


