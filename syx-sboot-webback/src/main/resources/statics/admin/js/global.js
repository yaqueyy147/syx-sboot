/**
 *
 * @authors younger
 * @date    2016-10-13
 * @description
 */
~ function(window) {
  	seajs.config({
	    base: '/statics/admin/js',
	    alias: {
	    	'module':'module',
	        'project': 'project',	        
	        'orijquery': 'vendor-modules/jquery',
	        'jquery': 'module/myajax',
	        'datepicker': 'vendor-modules/datepicker/WdatePicker',
	        'ztree': 'vendor-modules/ztree/jquery.ztree.all-3.5',
	        'upload': 'vendor-modules/uploadify/jquery.uploadify.js',
	        'validate':'vendor-modules/jquery.validate.min',
	        'plugin': 'plugin',
			'ajaxUpload':'vendor-modules/ajaxfileupload4seajs'
	        	
	    },
	    charset: 'utf-8',
	    timeout: 20000,
	    debug: false
	});
}(window)