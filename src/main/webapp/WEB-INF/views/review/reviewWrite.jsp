<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<style>
	.ck-editor__editable_inline{
		min-height:250px;
	}
.page-main{
	padding:120px;
}
h2{
text-align:center;
}
ul{
   list-style:none;
}
.button{
display:flex;
justify-content:center;
width:100%;
}
.upload{
display:flex;
justify-content:right;
width:100%;
}
</style>
<<script type="text/javascript">
	var locked = 0;
	
	function show(review_rating){
		if(locked)
			return;
		var i;
		var image;
		var el;
		var e = document.getElementById('review_ratingtext');
		var stateMsg;
		
		for(i = 1; i <= star; i++){
			image = 'image' + i;
			el = document.getElementById(image);
			el.src = "image/star/star1.png";
		}
		
		switch (star){
		case 1:
			stateMsg = "별로에요"
			break;
		}
		case 2:
			stateMsg = "보통이에요"
			break;
		}
		case 3:
			stateMsg = "괜찮아요"
			break;
		}
		case 4:
			stateMsg = "좋아요"
			break;
		}
		case 5:
			stateMsg = "아주 좋아요"
			break;
		}
		default:
			stateMsg = "";
	}
	e.innerHTML = stateMsg;
	}
	
	function noshow(review_rating){
		if(locked)
			return;
		var i;
		var image;
		var el;
		
		for(i = 1; i<= review_rating; i++){
			image = 'image' + i;
			el = document.getElementById(image);
			el.src = "image/star/star0.png";
		}
	}
	
	function lock(review_rating){
		show(review_rating);
		locked = 1;
	}
	
	function mark(review_rating){
		lock(review_rating);
		alert("선택2"+review_rating);
		document.reviewform.review_rating.value=review_rating;
	}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>
<div class="page-main">
	<h2>상품 후기 작성하기</h2>
	<form:form action="usr_MovDetailPro.do" method="post" name="reviewform">
		<table width="700">
			<tr>
				<td width="100" rowspan="2">${sessionScope.memId }</td>
				<td width="500" height="50" colspan="2">
					<div id="rating" align="center">
						<span>
							<img id=image1 onmouseover=show(1) onclick=mark(1) onmouseout=noshow(1) src="image/star/star0.png">
							<img id=image1 onmouseover=show(2) onclick=mark(2) onmouseout=noshow(2) src="image/star/star0.png">
							<img id=image1 onmouseover=show(3) onclick=mark(3) onmouseout=noshow(3) src="image/star/star0.png">
							<img id=image1 onmouseover=show(4) onclick=mark(4) onmouseout=noshow(4) src="image/star/star0.png">
							<img id=image1 onmouseover=show(5) onclick=mark(5) onmouseout=noshow(5) src="image/star/star0.png">
						</span>
					</div>
					<input type="hidden" name="review_rating"/>
				</td>
		</table>
	</form:form>
	
	
	
	<form:form id="register_form" action="write.do" modelAttribute="reviewVO"
	           enctype="multipart/form-data">
	    <form:errors element="div" cssClass="error-color"/>       
		<ul>
			<li>
				<form:textarea path="review_content" class="reviewform"/>
				<form:errors path="review_content" cssClass="error-color"/>
				<script>
					function MyCustomUploadAdapterPlugin(editor){
						editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							return new UploadAdapter(loader);
						}
					}
					
					ClassicEditor.create(document.querySelector('#review_content'),{
						extraPlugins:[MyCustomUploadAdapterPlugin]
					}).then(editor => {
						window.editor = editor;
					}).catch(error => {
						console.error(error);
					});
				</script>
			</li>
			
			<br><li class="upload">
				<label for="upload">이미지 파일 : </label>&nbsp;&nbsp;
				<input type="file" name="upload" id="upload" class="btn btn-primary btn-sm" accept="image/gif,image/png,image/jpeg">
			</li>
		</ul>
		<div class="button">
			<form:button class="btn btn-primary">상품 후기 등록하기</form:button>
		</div>	
	</form:form>
</div>