  
  document.addEventListener('DOMContentLoaded', function() {
	    // 모달 열기
	    document.getElementById('openModalBtn').addEventListener('click', function() {
	    	console.log('Open button clicked');
	        document.getElementById('loginModalContainer').style.display = 'block';
	    });

	    // 모달 닫기
	    document.getElementById('closeModalBtn').addEventListener('click', function() {
	        document.getElementById('loginModalContainer').style.display = 'none';
	    });

	    // 모달 외부 클릭 시 닫기
	    window.addEventListener('click', function(event) {
	        var modal = document.getElementById('loginModalContainer');
	        if (event.target == modal) {
	            modal.style.display = 'none';
	        }
	    });
	});

 	document.addEventListener('DOMContentLoaded', function() {
	    // 모달 열기
	    document.getElementById('openModalBtn2').addEventListener('click', function() {
	    	console.log('Open button clicked');
	        document.getElementById('loginModalContainer').style.display = 'block';
	    });

	    // 모달 닫기
	    document.getElementById('closeModalBtn').addEventListener('click', function() {
	        document.getElementById('loginModalContainer').style.display = 'none';
	    });

	    // 모달 외부 클릭 시 닫기
	    window.addEventListener('click', function(event) {
	        var modal = document.getElementById('loginModalContainer');
	        if (event.target == modal) {
	            modal.style.display = 'none';
	        }
	    });
	});
	
	// 구글 로그인 버튼 클릭
	document.getElementById('googleLogin').addEventListener('click', function() {
        document.getElementById('googleLoginForm').submit();
    });
	
	// 카카오 로그인 버튼 클릭
	document.getElementById('kakaoLogin').addEventListener('click', function() {
        document.getElementById('kakaoLoginForm').submit();
    });
    
    // 네이버 로그인 버튼 클릭
    document.getElementById('naverLogin').addEventListener('click', function() {
        document.getElementById('naverLoginForm').submit();
    });
	
	function openReportModal(id, aiSinger, title, oriSinger, nickname) {
	    // 모달 열기
	    console.log('Open button clicked');
	    document.getElementById('reportModalContainer').style.display = 'block';
		document.getElementById('reportMusicTitle').textContent = '노래: ' + aiSinger + ' - ' + title + '(' + oriSinger + ')';
		document.getElementById('reportMusicUploader').textContent = '업로더: ' + nickname;
		
	    // 모달 닫기
	    document.getElementById('closeReportModalBtn').addEventListener('click', function() {
	        document.getElementById('reportModalContainer').style.display = 'none';
	    });

	    // 모달 외부 클릭 시 닫기
	    window.addEventListener('click', function(event) {
	        var modal = document.getElementById('reportModalContainer');
	        if (event.target == modal) {
	            modal.style.display = 'none';
	        }
	    });
	    
	    document.getElementById('reportMusicBtn').addEventListener('click', function() {
	    	
		    event.preventDefault(); // 폼의 기본 동작 막기
		    loginCheck().then(function(isAuthenticated) {
	        if (!isAuthenticated) {
	            alert("로그인이 필요합니다.");
	            return;
	        }
	        
	    	var formValues = new FormData($("#report-music-form")[0]);
	    	
	    	formValues.append('id', id);
	    	formValues.append('nickname', nickname);
	    	console.log("신고 버튼 클릭");
	    	$.ajax({
		            type: 'POST',
		            url: '/reportMusic',
		            data: formValues,
		            processData: false,
   				    contentType: false,
		            success: function (data) {
							alert('신고 처리 되었습니다.');
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    });
	    });
    		
	}