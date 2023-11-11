	const addMusicBtn = document.querySelector(".main-top-music-function-addsong");
	
	function addMusic(musicId) {
		
		 $.ajax({
	            type: 'POST',
	            url: '/addMusic/' + musicId,
	            dataType: 'json',
	            success: function () {
	                // 성공적으로 업데이트된 좋아요 수를 받아와 화면 갱신
	                
	            },
	            error: function (xhr) {
	            }
	        });
	}