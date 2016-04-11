//计算json长度
function getJsonObjLength(data) {
    var test = data;
    return test.length;
}

$(document).ready(function(){
    $.ajax({
    	type:"POST",
    	  //返回类型
    	  dataType: "json",
    	  //请求地址
    	  url: "http://localhost:8080/protein02/FileListAction",
    	  //参数
    	  data: "page=1",
    	  success:function addFileToTable(data){
    	    	//alert(data);
    	    	var dom="<tr><td align='center'>";
    	    	var size=1;
    	    	var length=getJsonObjLength(data);
    	    	for(size; size<length+1; size++){
    	    		if(size==1){
    	    			dom+=size+"</td><td align='center'>"+data[size-1].name+"</td><td align='center'>"+data[size-1].time+"</td><td align='center'>"+data[size-1].publicTime+"</td><td align='center' ><a href='javascript:publishFile("+data[size-1].name+")'>publish</a>&nbsp&nbsp<a href='javascript:deleteFile("+data[size-1].name+")'> delete</a></td></tr>";
    	    		}
    	    		else{
    	    			dom+="<tr><td align='center'>"+size+"</td><td align='center'>"+data[size-1].name+"</td><td align='center'>"+data[size-1].time+"</td><td align='center'>"+data[size-1].publicTime+"</td><td align='center'><a href='javascript:publishFile("+data[size-1].name+")'>publish</a>&nbsp&nbsp<a href='javascript:deleteFile("+data[size-1].name+")'> delete</a></td></tr>";
    	    		}
    	    	}
    	    	$("#fileTable tbody").prepend(dom);
    	    }
    });
    
    /**
	$.post( "http://localhost:8080/protein02/FileListAction",function(success) {
		  alert(success[0].name);
		} );
    alert("end");
    **/
});

function search(){
	var fileName=$("#fileName").val();
	var inputTime=$("#inputTime").val();
	$.ajax({
    	type:"POST",
    	  //返回类型
    	  dataType: "json",
    	  //请求地址
    	  url: "http://localhost:8080/protein02/FileListAction",
    	  //参数
    	  data: "fileName="+fileName+"&inputTime+"+inputTime+"&page=1",
    	  success:function addFileToTable(data){
    	    	//alert(data);
    	    	var dom="<tr><td align='center'>";
    	    	var size=1;
    	    	var length=getJsonObjLength(data);
    	    	for(size; size<length+1; size++){
    	    		if(size==1){
    	    			dom+=size+"</td><td align='center'>"+data[size-1].name+"</td><td align='center'>"+data[size-1].time+"</td><td align='center'>"+data[size-1].publicTime+"</td><td align='center' ><a href='javascript:publishFile("+data[size-1].name+")'>publish</a>&nbsp&nbsp<a href onclick='javascript:deleteFile("+data[size-1].name+")'> delete</a></td></tr>";
    	    		}
    	    		else{
    	    			dom+="<tr><td align='center'>"+size+"</td><td align='center'>"+data[size-1].name+"</td><td align='center'>"+data[size-1].time+"</td><td align='center'>"+data[size-1].publicTime+"</td><td align='center'><a href='javascript:publishFile("+data[size-1].name+")'>publish</a>&nbsp&nbsp<a href onclick='javascript:deleteFile("+data[size-1].name+")'> delete</a></td></tr>";
    	    		}
    	    	}
    	    	$("#fileTable tbody").empty();
    	    	alert(dom);
    	    	$("#fileTable tbody").prepend(dom);
    	    }
    });
}

function publishFile(fileName){
	alert("11111111");
	alert(fileName);
	if(fileName.indexOf("xml")>0){
		$.ajax({
	    	type:"GET",
	    	  //请求地址
	    	  url: "http://localhost:8080/protein02/PrideXMLParse.action",
	    	  //参数
	    	  data: "fileName="+fileName,
	    	  success:function(data){
	    		  alert(data);
	    	  }
	    });
	}else{
		$.ajax({
	    	type:"GET",
	    	  //请求地址
	    	  url: "http://localhost:8080/protein02/MzidentmlParse.action",
	    	  //参数
	    	  data: "fileName="+fileName,
	    	  success:function(data){
	    		  alert(data);
	    	  }
	    });
	}
}


