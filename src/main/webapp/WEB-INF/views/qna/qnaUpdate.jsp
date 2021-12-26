<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/qnaList.css"> 
<script>
	$(document).ready(function(){
		$('#update_form').submit(function(){
			if($('#board_content').val().trim()==''){
				alert('내용을 입력해주세요!');
				$('#board_content').focus();
				$('#board_content').val();
				return false;
			}
		});
	});
</script>
<style>
	.ck-editor__editable_inline{
		min-height:250px;
	}
</style>
<div class="top_menu_info">
	<div>
	홈 > 문의게시판
	</div>
</div>
<div id="main-width">
	<div id="categorySide">
		<div id="showmenu">문의게시판</div>
		<div class="menu mt-2">
		<ul>
			<c:if test="${qnaVO.board_kind == 0}">
				<li class="order-current"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
			</c:if>
			<c:if test="${qnaVO.board_kind == 1}">
				<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
				<li class="order-current"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
			</c:if>
			<c:if test="${qnaVO.board_kind == 2}">
				<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
				<li class="order-current"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
			</c:if>
			<c:if test="${qnaVO.board_kind == 3}">
				<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
				<li class="order-current"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
			</c:if>
		</ul>
		</div>
	</div>
	<div class="top-small-menu">
		<div class="top-small-menu-title mt-3">
			<c:if test="${qnaVO.board_kind == 0}">
				상품 문의게시판
			</c:if>
			<c:if test="${qnaVO.board_kind == 1}">
				배송 문의게시판
			</c:if>
			<c:if test="${qnaVO.board_kind == 2}">
				교환/환불 문의게시판
			</c:if>
			<c:if test="${qnaVO.board_kind == 3}">
				기타 문의게시판
			</c:if>
		</div>
		
		<div class="form-div mt-3">
			<form:form id="update_form" action="qnaUpdate.do" modelAttribute="qnaVO" enctype="multipart/form-data">
				<input type="hidden" id="board_no" name="board_no" value="${qnaVO.board_no}">
				<input type="hidden" id="board_kind" name="board_kind" value="${qnaVO.board_kind}">
				<div class="form-group row">
					<label for="board_title" class="col-sm-1 col-form-label">제목</label>
					<div class="col-sm-7">
						<c:if test="${qnaVO.board_kind == 0}">
							<input type="text" class="form-control" id="board_title" name="board_title" value="상품관련 문의입니다:D" readonly="readonly">
						</c:if>
						<c:if test="${qnaVO.board_kind == 1}">
							<input type="text" class="form-control" id="board_title" name="board_title" value="배송관련 문의입니다:D" readonly="readonly">
						</c:if>
						<c:if test="${qnaVO.board_kind == 2}">
							<input type="text" class="form-control" id="board_title" name="board_title" value="교환/환불관련 문의입니다:D" readonly="readonly">
						</c:if>
						<c:if test="${qnaVO.board_kind == 3}">
							<input type="text" class="form-control" id="board_title" name="board_title" value="기타관련 문의입니다:D" readonly="readonly">
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<textarea id="board_content" name="board_content">${qnaVO.board_content}</textarea>
					<script>
					function MyCustomUploadAdapterPlugin(editor){
						editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							return new UploadAdapter(loader);
						}
					}
					
					ClassicEditor.create(document.querySelector('#board_content'),{
						extraPlugins:[MyCustomUploadAdapterPlugin]
					}).then(editor => {
						window.editor = editor;
					}).catch(error => {
						console.error(error);
					});
				</script>
				</div>
				<form:button class="btn btn-primary write-detail-btn">글수정</form:button>
			</form:form>
		</div>
		
	</div>
</div>