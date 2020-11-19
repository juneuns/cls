$(document).ready(function(){
	$('.brow').click(function(){
		var sno = $(this).attr('id');
		$('#bno').val(sno);
		$('#bfrm').attr('action', '/cls/board/boardDetail.cls');
		$('#bfrm').submit();
	});
	
	$('.pagebtn').click(function(){
		// 버튼 내용 알아내고
		var strPage = $(this).attr('id');
		
		if(!strPage){
			strPage = $(this).html();
		}
		
		$('#nowPage').val(strPage);
		// 전송주소 설정하고
		$('#bfrm').attr('action', '/cls/board/boardList.cls');
		// 폼태그 전송하고
		$('#bfrm').submit();
	});
	
	$('#rbtn').click(function(){
		$(location).attr('href', '/cls/board/boardWrite.cls');
	});
	
	$('#lbtn').click(function(){
		$(location).attr('href', '/cls/member/login.cls');
	});
	
	$('#jbtn').click(function(){
		$(location).attr('href', '/cls/member/join.cls');
	});
	
	$('#hbtn').click(function(){
		$(location).attr('href', '/cls/main.cls');
	});
	
/* 게시글 작성 페이지 이벤트 처리*/
	$('#wbtn').click(function(){
		var shead = $('#title').val();
		var sbody = $('#body').val();
		
		// 데이터가 입력되었는지 확인하고...
		if((!shead) || (!sbody)){
			return;
		};
		
		// 이곳을 실행하는 경우는 모든 입력태그에(파일태그제외) 데이터가 입력된 경우..
		$('#wfrm').submit();
	});
	
	$('#ebtn').click(function(){
		$('#frm').submit();
	});
	
	// 문서가 완성이 되면 태그에 입력된 값을 기억해 놓는다.
	var stitle = $('#title').val();
	var body = $('#body').val();
	
	$('#edit').click(function(){
		var tTitle = $('#title').val();
		var tBody = $('#body').val();
		
		if(stitle == tTitle && tBody == body){
			return;
		}
		if(stitle == tTitle){
			$('#title').prop('readonly', true);
		}
		if(tBody == body){
			$('#body').prop('readonly', true);
		}
		
		$('#efrm').submit();
	});
});

















