 		
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
						            if(response.logoutStatus) {
										return;
									}
									console.log("responseLike:", response.likedMusicIds);
									initializeLikeMusic(response.likedMusicIds);
									
									console.log("responseAdd:", response.addMusicIds);
									initializeAddMusic(response.addMusicIds);
									
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
			/*$('.main-top-music-function-like').click(function() {
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
		        });*/

		        likedMusicIds.forEach(function(musicId) {
		            $('img[data-like-music-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
		        });
	
		}
		
		function initializeAddMusic(addMusicIds){
			
			/*$('.main-top-music-function-addsong').click(function() {
		            var musicId = $(this).data('music-add-id');
		            
		            console.log("musicId:", musicId);
					// 보관함에 추가되있는 경우 삭제
		            if (addMusicIds.includes(musicId)) {
			
						$.ajax({
						        type: 'POST',
						        url: '/addMusicCancel/' + musicId, // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
									$(this).attr('src', '/static/images/add_song.png');
		                			alert('보관함에서 삭제되었습니다.');
						            
						        },
						        error: function(error, xhr) {
						            if (xhr.status === 401) {
							            alert('로그인 후 이용해주십시오');
							        }
						        }
						    });
		                return;

		            }

						$.ajax({
						        type: 'POST',
						        url: '/addMusic/' + musicId, // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						        success: function(response) {
						            // 서버로부터 받은 데이터를 변수에 할당
									$(this).attr('src', '/static/images/addSong_on.png');
		                			alert('보관함에서 추가되었습니다.');
						            
						        },
						        error: function(error, xhr) {
						            if (xhr.status === 401) {
							            alert('로그인 후 이용해주십시오');
							        }
						        }
						    });
		

		            $(this).attr('src', '/static/images/addSong_on.png');
		            // likedMusicIds에 추가
		            addMusicIds.push(musicId);
		        });*/

		        addMusicIds.forEach(function(musicId) {
		            $('img[data-add-music-id="' + musicId + '"]').attr('src', '/static/images/addSong_on.png');
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
			if(!nickname) {
				var currentMusicUploader = document.getElementById('currentUploader');
				var nickname = currentMusicUploader.getAttribute('data-music-play-nickname');
			}
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
									
									console.log("responseLike:", response.likedMusicIds);
									initializeLikeMusic(response.likedMusicIds);
									
									console.log("responseAdd:", response.addMusicIds);
									initializeAddMusic(response.addMusicIds);
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
					
			  $('#addMusics *').hide();
	
		        console.log("likedMusicIds:",likedMusicIds);


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
						            
						            console.log("responseLike:", response.likedMusicIds);
									initializeLikeMusic(response.likedMusicIds);
									
									console.log("responseAdd:", response.addMusicIds);
									initializeAddMusic(response.addMusicIds);
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
									
						            console.log("responseLike:", response.likedMusicIds);
									initializeLikeMusic(response.likedMusicIds);
									
									console.log("responseAdd:", response.addMusicIds);
									initializeAddMusic(response.addMusicIds);
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

		function CommunityLoad() {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/requestpost',
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
				
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function CommunityWriteLoad() {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/private/requestpostwrite',
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
				
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function CommunityWriteEndLoad() {
	    	
			var formValues = $("form[name=request-post-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/requestpostwrite',
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
		                }
				
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
			event.preventDefault(); // 이벤트의 기본 동작 막기
    		return false;
	    }
