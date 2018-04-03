<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EasyUI Tree Menu Test</title>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<ul id="treeTest" class="easyui-tree"></ul>
<script type="text/javascript">
$(document).ready(function(){
	alert(1);
	$("#treeTest").tree({
		url:'/item/cat/list',
		animate:true,
		onClick : function(node){
			if($(this).tree("isLeaf",node.target)){
				// 填写到cid中
				//_ele.parent().find("[name=cid]").val(node.id);
				//_ele.next().text(node.text).attr("cid",node.id);
				//$(_win).window('close');
				//if(data && data.fun){
					//data.fun.call(this,node);
				//}
				alert(1111);
			}else{
				alert(2222);
			}
		}
	});
});
</script>
</body>
</html>