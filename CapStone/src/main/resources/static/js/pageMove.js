 		
		function homePageLoad() {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/',
		            success: function (data) {
		            	// data를 DOM 객체로 변환
		                var parser = new DOMParser();
		                var doc = parser.parseFromString(data, 'text/html');

		                // 특정 템플릿에서 content-container를 찾음
		                var contentContainerInTemplate = doc.querySelector("#content-container");

		                // 만약 템플릿에서 content-container를 찾았다면 현재 페이지의 content-container를 업데이트
		                if (contentContainerInTemplate) {
		                    var currentContent = document.querySelector("#content-container");
		                    currentContent.innerHTML = contentContainerInTemplate.innerHTML;

							$.ajax({
						        type: 'GET',
						        url: '/getLikedMusicIds', // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
						            
									console.log("responseLike:", response);
									initializeLikeMusic(response);
						            // 이후에 할 작업들...
						        },
						        error: function(error) {
						            console.error(error);
						        }
						    });
							//bindDomEvent();
							
		                }
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function initializeLikeMusic(likedMusicIds){
			$('.main-top-music-function-like').click(function() {
		            var musicId = $(this).data('music-id');
		            
		            console.log("musicId:", musicId);
					// 이미 추천한 경우 이미지 변경하지 않음
		            if (likedMusicIds.includes(musicId)) {
		                alert('이미 추천한 음악입니다.');
		                return;
		            }

		            $(this).attr('src', '/static/images/like_on.png');
		            // likedMusicIds에 추가
		            likedMusicIds.push(musicId);
		        });

		        likedMusicIds.forEach(function(musicId) {
		            $('img[data-music-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
		        });

			
		}
		function fileUploadLoad() {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/private/musicUpload',
		            success: function (data) {
		            	// data를 DOM 객체로 변환
		                var parser = new DOMParser();
		                var doc = parser.parseFromString(data, 'text/html');

		                // 특정 템플릿에서 content-container를 찾음
		                var contentContainerInTemplate = doc.querySelector("#content-container");

		                // 만약 템플릿에서 content-container를 찾았다면 현재 페이지의 content-container를 업데이트
		                if (contentContainerInTemplate) {
		                    var currentContent = document.querySelector("#content-container");
		                    currentContent.innerHTML = contentContainerInTemplate.innerHTML;
		                }
				
						musicFileEvent();
		    			musicImageEvent();
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

	    function memberPageLoad(nickname) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/private/mypage/' + nickname,
		            success: function (data) {
		            	// data를 DOM 객체로 변환
		                var parser = new DOMParser();
		                var doc = parser.parseFromString(data, 'text/html');

		                // 특정 템플릿에서 content-container를 찾음
		                var contentContainerInTemplate = doc.querySelector("#content-container");

		                // 만약 템플릿에서 content-container를 찾았다면 현재 페이지의 content-container를 업데이트
		                if (contentContainerInTemplate) {
		                    var currentContent = document.querySelector("#content-container");
		                    currentContent.innerHTML = contentContainerInTemplate.innerHTML;
				
							$.ajax({
						        type: 'GET',
						        url: '/getLikedMusicIds', // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
						            
									console.log("responseLike:", response);
									initializeMemberPage(response);
						            // 이후에 할 작업들...
						        },
						        error: function(error) {
						            console.error(error);
						        }
						    });
							bindDomEvent();	
		                }
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function initializeMemberPage(likedMusicIds) {
			
			$(document).ready(function () {
		      // tracks-column �겢由� �떆
		      $('.tracks-column').click(function () {
		          $(this).toggleClass('active'); // �겢�옒�뒪 �넗湲�
		
		          // playlist-column 珥덇린�솕
		          $('.playlist-column').removeClass('active');
		      });
		
		      // playlist-column �겢由� �떆
		      $('.playlist-column').click(function () {
		          $(this).toggleClass('active'); // �겢�옒�뒪 �넗湲�
		
		          // tracks-column 珥덇린�솕
		          $('.tracks-column').removeClass('active');
		      });
		      
		        //var likedMusicIds = /*[[${likedMusicIds}]]*/ [];

		        console.log("likedMusicIds:",likedMusicIds);

		        $('.main-top-music-function-like').click(function() {
		            var musicId = $(this).data('music-id');
		            
		            console.log("musicId:", musicId);
		            // �씠誘� 異붿쿇�븳 寃쎌슦 �씠誘몄� 蹂�寃쏀븯吏� �븡�쓬
		            if (likedMusicIds.includes(musicId)) {
		                alert('이미 추천한 음악입니다.');
		                return;
		            }

		            $(this).attr('src', '/static/images/like_on.png');
		            // likedMusicIds�뿉 異붽�
		            likedMusicIds.push(musicId);
		        });

		        likedMusicIds.forEach(function(musicId) {
		            $('img[data-music-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
		        });

				    // 모달 열기
				    document.getElementById('followerModalBtn').addEventListener('click', function() {
				    	console.log('Open button clicked');
				        document.getElementById('followModalContainer').style.display = 'block';
				    });
			
				    // 모달 닫기
				    document.getElementById('closeFollowModalBtn').addEventListener('click', function() {
				        document.getElementById('followModalContainer').style.display = 'none';
				    });
			
				    // 모달 외부 클릭 시 닫기
				    window.addEventListener('click', function(event) {
				        var modal = document.getElementById('followModalContainer');
				        if (event.target == modal) {
				            modal.style.display = 'none';
				        }
				    });
				
				    // 모달 열기
				    document.getElementById('followingModalBtn').addEventListener('click', function() {
				    	console.log('Open button clicked');
				        document.getElementById('followingModalContainer').style.display = 'block';
				    });
			
				    // 모달 닫기
				    document.getElementById('closeFollowingModalBtn').addEventListener('click', function() {
				        document.getElementById('followingModalContainer').style.display = 'none';
				    });
			
				    // 모달 외부 클릭 시 닫기
				    window.addEventListener('click', function(event) {
				        var modal = document.getElementById('followingModalContainer');
				        if (event.target == modal) {
				            modal.style.display = 'none';
				        }
				    });
				});
		        
		}
		
		function searchLoad() {
	    	
			var formValues = $("form[name=music-search-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/search',
					data: formValues,
		            success: function (data) {
		            	// data를 DOM 객체로 변환
		                var parser = new DOMParser();
		                var doc = parser.parseFromString(data, 'text/html');

		                // 특정 템플릿에서 content-container를 찾음
		                var contentContainerInTemplate = doc.querySelector("#content-container");

		                // 만약 템플릿에서 content-container를 찾았다면 현재 페이지의 content-container를 업데이트
		                if (contentContainerInTemplate) {
		                    var currentContent = document.querySelector("#content-container");
		                    currentContent.innerHTML = contentContainerInTemplate.innerHTML;

							$.ajax({
						        type: 'GET',
						        url: '/getLikedMusicIds', // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
						            
									console.log("responseLike:", response);
									initializeLikeMusic(response);
						            // 이후에 할 작업들...
						        },
						        error: function(error) {
						            console.error(error);
						        }
						    });
		                }
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });

			event.preventDefault(); // 이벤트의 기본 동작 막기
    		return false;
	    }

		function searchFilterLoad(element) {
	    	
			var filter = element.getAttribute('data-filter');
    		var keyword = element.getAttribute('data-keyword');

	    	 $.ajax({
		            type: 'GET',
		            url: '/filter',
					data: {
			            filter: filter,
			            keyword: keyword
			        },
		            success: function (data) {
		            	// data를 DOM 객체로 변환
		                var parser = new DOMParser();
		                var doc = parser.parseFromString(data, 'text/html');

		                // 특정 템플릿에서 content-container를 찾음
		                var contentContainerInTemplate = doc.querySelector("#content-container");

		                // 만약 템플릿에서 content-container를 찾았다면 현재 페이지의 content-container를 업데이트
		                if (contentContainerInTemplate) {
		                    var currentContent = document.querySelector("#content-container");
		                    currentContent.innerHTML = contentContainerInTemplate.innerHTML;

							$.ajax({
						        type: 'GET',
						        url: '/getLikedMusicIds', // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
						            
									console.log("responseLike:", response);
									initializeLikeMusic(response);
						            // 이후에 할 작업들...
						        },
						        error: function(error) {
						            console.error(error);
						        }
						    });
		                }
				
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });

	    }
