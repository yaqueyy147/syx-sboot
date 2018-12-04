/**
 * Created by suyx on 2017/1/6 0006.
 */

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$.fn.populateForm = function(data){
    return this.each(function(){
        var formElem, name;
        if(data == null){this.reset(); return; }
        for(var i = 0; i < this.length; i++){
            formElem = this.elements[i];
            //checkbox的name可能是name[]数组形式
            name = (formElem.type == "checkbox")? formElem.name.replace(/(.+)\[\]$/, "$1") : formElem.name;
            if(data[name] == undefined) continue;
            switch(formElem.type){
                case "checkbox":
                    if(data[name] == ""){
                        formElem.checked = false;
                    }else{
                        //数组查找元素
                        if(data[name].indexOf(formElem.value) > -1){
                            formElem.checked = true;
                        }else{
                            formElem.checked = false;
                        }
                    }
                    break;
                case "radio":
                    if($.trim(data[name]).length <= 0){
                        formElem.checked = false;
                    }else if(formElem.value == data[name]){
                        formElem.checked = true;
                    }
                    break;
                case "button": break;
                default: formElem.value = data[name];
            }
        }
    });
};

function toFillForm(formObj,data) {
    for(var key in data){
        var forElement1 = $(formObj).find("input[name='" + key + "']");
        if(forElement1 != undefined && forElement1 != 'undefined'){
            var elementType = $(forElement1).attr("type");
            if(elementType == "checkbox" || elementType == "radio"){
                for(var i=0;i<forElement1.length;i++){
                    var ii = forElement1[i];
                    if(data[key].indexOf($(ii).val()) > -1){
                        $(ii).prop("checked",true);
                    }else{
                        $(ii).prop("checked",false);
                    }
                }
            }else{
                $(forElement1).val(data[key]);
            }
        }
        var forElement2 = $(formObj).find("select[name='" + key + "']");
        if(forElement2 != undefined && forElement2 != 'undefined') {
            $(forElement2).val(data[key]);
            $(forElement2).change();
        }
        
        var forElement3 = $(formObj).find("td[name='" + key + "']");
        if(forElement3 != undefined && forElement3 != 'undefined') {
        	var labelclass = $(forElement3).attr("class");
        	if(labelclass=='hllabel'){
        		$(forElement3).html(data[key]);
        	}
        }
    }
}

function formDataToJson(formObj) {
    var formData = $(formObj).serializeArray();
    var postData = {};
    for (var item in formData) {
        postData["" + formData[item].name + ""] = formData[item].value;
    }
    return postData;
};

String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}