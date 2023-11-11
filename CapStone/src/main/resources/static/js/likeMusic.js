		function likeMusic(musicId) {
	        // AJAX를 사용하여 서버에 좋아요 수 업데이트 요청
	        $.ajax({
	            type: 'POST',
	            url: '/like/' + musicId,
	            dataType: 'json',
	            success: function (response) {
		
					// 로그인이 안되었다면..
					if(response.sessionError) {
						alert("로그인 후 이용해주십시오");
            			return; // Ajax 종료
					}
					
	                // 성공적으로 업데이트된 좋아요 수를 받아와 화면 갱신
	                $('#likeCount').text(response.updatedLikeCount);
	                
	                // 로컬 스토리지에 좋아요 눌렀음을 표시
	                localStorage.setItem(`like_${musicId}`, 'true');
					var playMusicId = musicLike.getAttribute('data-music-play-id');
					
					if(musicId == playMusicId) {
						musicLike.src = '/static/images/like_on.png';
					} else {
						
					}
	            },
	            error: function (xhr) {
	            }
	        });
	    }