 		
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
	    	console.log("memberPageLoad Event!");
			if(!nickname) {
				var currentMusicUploader = document.getElementById('currentUploader');
				var nickname = currentMusicUploader.getAttribute('data-music-play-nickname');
			}
	    	 $.ajax({
		            type: 'GET',
		            url: '/mypage/' + nickname,
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

			event.preventDefault(); // 폼의 기본 동작 막기
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

		function genreFilterLoad(genre, genreImg) {
		    let totalPlayHour; // 전역 변수로 선언
		    let totalPlayMinutes;
		
		    fetch('/getMusicDuration/' + genre)
		        .then(response => response.json())
		        .then(musicUrls => {
		            console.log('음악 파일 경로 목록:', musicUrls);
		
		            // 각 음악 파일에 대한 재생 시간을 계산
		            const promises = musicUrls.map(url => {
		                const serverUrl = url.replace("C:\\capstone", "");
		
		                return fetch('/getMusic' + serverUrl)
		                    .then(response => response.blob())
		                    .then(blob => {
		                        const audioUrl = URL.createObjectURL(blob);
		                        const audio = new Audio(audioUrl);
		
		                        return new Promise((resolve) => {
		                            audio.addEventListener('loadedmetadata', () => {
		                                resolve(audio.duration);
		                            });
		                        });
		                    });
		            });
		
		            // 모든 비동기 작업이 완료된 후에 결과를 처리
		            return Promise.all(promises)
		                .then(durations => {
		                    // durations 배열에는 각 음악 파일의 재생 시간이 들어 있음
		                    console.log('음악 파일 재생 시간 목록:', durations);
		
		                    // 모든 재생 시간을 합친 변수 생성
		                    const totalDuration = durations.reduce((acc, duration) => acc + duration, 0);
		                    totalPlayHour = Math.floor(totalDuration / 3600);
		                    totalPlayMinutes = Math.floor((totalDuration % 3600) / 60);
		                    console.log('전체 재생 시간:', totalDuration, '초');
		
		                    // 이제 totalDuration을 이용하여 필요한 작업 수행
		
		                    // totalPlayHour와 totalPlayMinutes 값을 사용하여 다음 작업 수행
		                    return {
		                        totalPlayHour: totalPlayHour,
		                        totalPlayMinutes: totalPlayMinutes
		                    };
		                });
		        })
		        .then(totalPlayTime => {
		            // totalPlayHour와 totalPlayMinutes 값을 사용하여 Ajax 요청 수행
		            return $.ajax({
		                type: 'GET',
		                url: '/genre',
		                data: {
		                    genre: genre,
		                    genreImg: genreImg,
		                    totalPlayHour: parseInt(totalPlayTime.totalPlayHour),
		                    totalPlayMinutes: parseInt(totalPlayTime.totalPlayMinutes)
		                },
		                success: function(data) {
		                    // Ajax 요청 성공 시 처리
		                    console.log('Server response:', data);
		
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
		                            url: '/getLikedMusicIds',
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
		                error: function(xhr, status, error) {
		                    console.error("Error loading content:", error);
		                }
		            });
		        })
		        .catch(error => console.error('Error:', error));
		}


		function CommunityLoad(page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/requestnoticeboard',
					data: { 
						page: page
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
		                }
				
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function CommunitySearchLoad() {
	    	
			var formValues = $('#request-title-form').serialize();
			
			console.log("formValues", formValues);
	    	 $.ajax({
		            type: 'POST',
		            url: '/requestnoticeboard',
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

		function TipCommunityLoad(page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/tipnoticeboard',
					data: { page: page },
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

		function TipCommunitySearchLoad() {
	    	
			var formValues = $('#tip-search-form').serialize();
			
			console.log("formValues", formValues);
	    	 $.ajax({
		            type: 'POST',
		            url: '/tipnoticeboard',
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
	    
		function VoiceCommunityLoad(page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/voicenoticeboard',
					data: { page: page },
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
	    
	    function VoiceCommunitySearchLoad() {
	    	
			var formValues = $('#voice-search-form').serialize();
			
			console.log("formValues", formValues);
	    	 $.ajax({
		            type: 'POST',
		            url: '/voicenoticeboard',
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

		function CommunityWriteLoad() {
	    	loginCheck();
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

		function TipCommunityWriteLoad() {
	    	loginCheck();
	    	 $.ajax({
		            type: 'GET',
		            url: '/private/tippostwrite',
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

		function VoiceCommunityWriteLoad() {
			 loginCheck();
	    	 $.ajax({
		            type: 'GET',
		            url: '/private/voicepostwrite',
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
				
						voiceFileEvent();
		    			voiceImageEvent();
		            },
		            error: function (xhr, status, error) {
		            	   console.error("Error loading content:", error);
		            	}
		        });
	    }

		function loginCheck() {
			
			return fetch('/api/auth/check')
                .then(response => response.json())
                .then(data => {
                    // 응답에서 isAuthenticated 값을 확인하여 alert 띄우기
                    return data.isAuthenticated;
                })
                .catch(error => {
                    console.error("Error checking authentication:", error);
                });
		}
		
		function CommunityWriteEndLoad() {
	    	
			var formValues = $("form[name=request-post-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/requestpostwrite',
					data: formValues,
		            success: function (data) {
						console.log("data", data);
						if(data.trim() === 'titleEmpty') {
							console.log("titleEmpty");
							alert("제목을 입력해주세요");
							return;
						}
						
						
						if(data.trim() === 'contentEmpty') {
							console.log("contentEmpty");
							alert("내용을 입력해주세요");
							return;
						}
						
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

		function TipCommunityWriteEndLoad() {
	    	
			var formValues = $("form[name=tip-post-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/tippostwrite',
					data: formValues,
		            success: function (data) {
						console.log("data", data);
						if(data.trim() === 'titleEmpty') {
							console.log("titleEmpty");
							alert("제목을 입력해주세요");
							return;
						}
						
						if(data.trim() === 'contentEmpty') {
							console.log("contentEmpty");
							alert("내용을 입력해주세요");
							return;
						}
						
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

		function VoiceCommunityWriteEndLoad() {
	    	
			var formValues = new FormData($("#voice-post-form")[0]);
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/voicepostwrite',
					data: formValues,
					processData: false,
   				    contentType: false,
		            success: function (data) {
						console.log("data", data);
						if(data.trim() === 'titleEmpty') {
							console.log("titleEmpty");
							alert("제목을 입력해주세요");
							return;
						}
						
						if(data.trim() === 'contentEmpty') {
							console.log("contentEmpty");
							alert("내용을 입력해주세요");
							return;
						}
						
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

		function CommunityPostLoad(postId, page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/requestpost/' + postId,
					data: { page: page },
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

		function TipCommunityPostLoad(postId, page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/tippost/' + postId,
					data: { page: page },
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

		function VoiceCommunityPostLoad(postId, page) {
	    	
	    	 $.ajax({
		            type: 'GET',
		            url: '/voicepost/' + postId,
					data: { page: page },
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

		function CommunityPostCommentWrite() {
	    	
			var formValues = $("form[name=community-post-comment-write-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/requestcommentwrite',
					data: formValues,
		            success: function (data) {
			
						if(data.trim() === 'commentEmpty') {
							console.log("commentEmpty");
							alert("댓글	을 입력해주세요");
							return;
						}
						
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

		function TipCommunityCommentWrite() {
	    	
			var formValues = $("form[name=community-post-comment-write-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/tipcommentwrite',
					data: formValues,
		            success: function (data) {
			
						if(data.trim() === 'commentEmpty') {
							console.log("commentEmpty");
							alert("댓글	을 입력해주세요");
							return;
						}
						
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

		function VoiceCommunityCommentWrite() {
	    	
			var formValues = $("form[name=community-post-comment-write-form]").serialize() ;
			
	    	 $.ajax({
		            type: 'POST',
		            url: '/private/tipcommentwrite',
					data: formValues,
		            success: function (data) {
			
						if(data.trim() === 'commentEmpty') {
							console.log("commentEmpty");
							alert("댓글	을 입력해주세요");
							return;
						}
						
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

		function VoiceFileDownload(button) {
			
			var filePath = button.getAttribute('data-voice-url');
    		var voiceName = button.getAttribute('data-ori-voice-name');

			window.open('/voiceFileDownload?filePath=' + encodeURIComponent(filePath) + '&voiceName=' + encodeURIComponent(voiceName));
		}
		
		function ReportList() {
		
	    	 $.ajax({
		            type: 'GET',
		            url: '/reportlist',
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
	    
	    function ReportSearchLoad() {
	    	
			var formValues = $('#report-form').serialize();
			
			console.log("formValues", formValues);
	    	 $.ajax({
		            type: 'POST',
		            url: '/reportlist',
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
	    