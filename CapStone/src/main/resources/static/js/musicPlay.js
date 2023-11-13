    const musicPlay = document.querySelector(".control-play");
	const musicProgress = document.querySelector(".music-progress");
	const musicProgressBar = document.querySelector(".music-progress-bar");
	const musicProgressCurrent = document.querySelector(".current");
    const musicProgressDuration = document.querySelector(".duration");
	const musicAudio = document.getElementById('main-audio');
	const musicListPlayBtn = document.querySelector(".music-list-play-button");
	const musicPlayBtn = document.querySelector('.control-play');
	const musicPrevBtn = document.querySelector(".control-previous");
    const musicNextBtn = document.querySelector(".control-next");
	const musicVol = document.querySelector("#vol");
	const musicLike = document.querySelector(".control-music-like");
	const musicAdd = document.querySelector(".control-music-add");
	const muteBtn = document.querySelector(".control-sound")
	const currentUploaderFollow = document.querySelector('.control-music-follow');
	const currentMusicImg = document.querySelector('.current-music-img');
	const currentMusicUploader = document.getElementById('currentUploader');
	const currentMusicTitle = document.getElementById('currentTitle');
	var musicList = document.querySelector('.music-list-column-div-block');
	let allMusic = JSON.parse(localStorage.getItem('allMusic')) || [];
	let musicIndex = 1; // 현재 재생되고 있는 음악 구분
   	var likedMusicIdsStr = document.getElementById('likedMusicIds').value;
	var likedMusicIds = JSON.parse(likedMusicIdsStr);
	var addMusicIdsStr = document.getElementById('addMusicIds').value;
	var addMusicIds = JSON.parse(addMusicIdsStr);
	var loginNickname = document.getElementById('loginNickname').value;
	var showFooterExecuted = false;
	let lastMusic = []; // 사용자가 마지막으로 재생한 음악 

	// jQuery를 사용하여 페이지 로드 시 실행합니다.
	$(document).ready(function () {
		console.log("allMusic:", allMusic);
		var storedStatus = localStorage.getItem('footerStatus');
	    var storedMusicStatus = localStorage.getItem('musicStatus');
	    var storedMusicInfo = localStorage.getItem('musicInfo');
	
	    var footer = document.querySelector('.music-play-footer');
	    if (storedStatus === 'visible') {
	        footer.style.display = '';
	    } else {
	        footer.style.display = 'none';
	    }
		
		musicVol.value = localStorage.getItem("musicVolume") || 50;
		musicAudio.volume = musicVol.value / 100;
		
		if (storedMusicStatus === 'playing' && storedMusicInfo) {
	        // 저장된 음악 정보가 있을 경우 플레이어 초기화
	        var musicInfo = JSON.parse(storedMusicInfo);
	        playMusicLoad(
				musicInfo.id,
	            musicInfo.musicUrl,
	            musicInfo.imgUrl,
	            musicInfo.aiSinger,
	            musicInfo.title,
	            musicInfo.oriSinger,
	            musicInfo.nickname
	        );
	    }

	    displayLoadMusicList();
	});


	function playMusic(id, musicUrl, imgUrl, aiSinger, title, oriSinger, nickname) {
        //var audio = document.getElementById('main-audio');
        //var playButton = document.querySelector('.control-play');

        musicAudio.src = musicUrl;  // 음악의 URL을 설정
        musicAudio.play();  // 음악을 재생


		musicPlayBtn.src = '/static/images/pause_button.png';
		
        // 나머지 이미지 및 음악 정보 업데이트
        //var currentMusicImg = document.querySelector('.current-music-img');
       // var uploaderElement = document.getElementById('currentUploader');
        var titleElement = document.getElementById('currentTitle');
        currentMusicImg.src = imgUrl;
        currentMusicUploader.textContent = nickname;
        currentMusicTitle.textContent = aiSinger + ' - ' + title + '(' + oriSinger + ')';
		
		console.log("likedMusicIds:",likedMusicIds);
		musicLike.setAttribute('data-music-play-id', id);
		musicAdd.setAttribute('data-music-play-add-id', id);
		currentMusicUploader.setAttribute('data-music-play-nickname', nickname);
		if(nickname == loginNickname) {
			currentUploaderFollow.style.display = 'none';
		} else {
			currentUploaderFollow.style.display = 'block';
			currentUploaderFollow.setAttribute('data-music-play-nickname', nickname);
		}
		
		$.ajax({
	            type: 'GET',
	            url: '/getExistLikedAndAddMusic',
				data: {
			            musicId: id,
			        },
	            dataType: 'json',
	            success: function (response) {
					if (response.likedExist) {
						musicLike.src = '/static/images/like_on.png';
			        } else {
						musicLike.src = '/static/images/black_like.png';
			        }

					if(response.addExist) {
						musicAdd.src = '/static/images/addSong_on.png';
					} else {
						musicAdd.src = '/static/images/black_add_song.png';
					}
	            },
	            error: function (xhr) {
	            }
	        });


		musicLike.addEventListener('click', function () {
			var musicId = parseInt(musicLike.getAttribute('data-music-play-id'));
			
			if (!likedMusicIds) {
				alert("로그인 후 이용 가능합니다.");
				return;
			}
			
			if (likedMusicIds.includes(musicId)) {
                alert('이미 추천한 음악입니다.');
                return;
            }

			musicLike.setAttribute('src', '/static/images/like_on.png');
			likedMusicIds.push(musicId);
			
				$.ajax({
	            type: 'POST',
	            url: '/like/' + musicId,
	            dataType: 'json',
	            success: function (response) {
	                // 성공적으로 업데이트된 좋아요 수를 받아와 화면 갱신
	                //$('#likeCount').text(updatedLikeCount);
	                console.log("updatedLikeCount", response.updatedLikeCount);
					$('#likeCount' + musidId).text(response.updatedLikeCount);
	                // 로컬 스토리지에 좋아요 눌렀음을 표시
					musicLike.src = '/static/images/like_on.png';
					    response.likedMusicIds.forEach(function(musicId) {
			            $('img[data-like-music-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
			        });

	            },
	            error: function (xhr) {
	            }
	        });

		});
	
		musicAdd.addEventListener('click', function () {
				var musicId = parseInt(musicAdd.getAttribute('data-music-play-add-id'));
				const ColumnMusicAdd =  document.querySelectorAll('[data-add-music-id="' + musicId + '"]');// 음악 재생 바 추가 버튼 
				if (ColumnMusicAdd.length > 0) {
					var ColumnMusicId = parseInt(ColumnMusicAdd[0].getAttribute('data-add-music-id')); // 재생 중인 음악 ID
				}
				
				if (!addMusicIds) {
					alert("로그인 후 이용 가능합니다.");
					return;
				}
	
				if (addMusicIds.includes(musicId)) {

					$.ajax({
						type: 'POST',
						url: '/addMusicCancel/' + musicId, // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						success: function(response) {
							// 서버로부터 받은 데이터를 변수에 할당
							musicAdd.src = '/static/images/black_add_song.png';
							var valueToDelete = musicId; // 삭제할 값
			
							var indexToDelete = addMusicIds.indexOf(valueToDelete); // 삭제할 값의 인덱스 찾기
							if (indexToDelete !== -1) {
								addMusicIds.splice(indexToDelete, 1); // 해당 인덱스에서 1개의 요소 삭제
							}
							
							if(ColumnMusicAdd !== undefined && ColumnMusicId == musicId) {
								ColumnMusicAdd.forEach(element => {
									element.src = '/static/images/add_song.png';
							    });
							}
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
						musicAdd.src = '/static/images/addSong_on.png';
						
						ColumnMusicAdd.forEach(element => {
									element.src = '/static/images/addSong_on.png';
					    });
						addMusicIds.push(parseInt(musicId, 10));
						alert('보관함에서 추가되었습니다.');
					},
					error: function(error, xhr) {
						if (xhr.status === 401) {
							alert('로그인 후 이용해주십시오');
						}
					}
				});
	
			});
				
	    localStorage.setItem('musicInfo', JSON.stringify({
			id: id,
	        musicUrl: musicUrl,
	        imgUrl: imgUrl,
	        aiSinger: aiSinger,
	        title: title,
	        oriSinger: oriSinger,
	        nickname: nickname
	    }));
	// 중복 여부 확인
    if (!allMusic.some(existingMusic => existingMusic.musicUrl === musicUrl)) {
	// 로컬 스토리지에 상태 저장
	    localStorage.setItem('musicStatus', 'playing');

	// 새로운 음악 데이터를 allMusic 배열에 추가
	    allMusic.push({
			id: id,
	        musicUrl: musicUrl,
	        imgUrl: imgUrl,
	        aiSinger: aiSinger,
	        title: title,
	        oriSinger: oriSinger,
	        nickname: nickname
	    });

// 로컬 스토리지에 allMusic 저장
        localStorage.setItem('allMusic', JSON.stringify(allMusic));

		console.log(allMusic);
		displayMusicList();
		}
    }

	function playMusicLoad(id, musicUrl, imgUrl, aiSinger, title, oriSinger, nickname) {
	        //var audio = document.getElementById('main-audio');
	        //var playButton = document.querySelector('.control-play');
	
	        musicAudio.src = musicUrl;  // 음악의 URL을 설정
	        musicAudio.pause();  // 음악을 재생
	
			musicPlayBtn.src = '/static/images/play_button.png';
			
	        // 나머지 이미지 및 음악 정보 업데이트
	        //var currentMusicImg = document.querySelector('.current-music-img');
	       // var uploaderElement = document.getElementById('currentUploader');
	        var titleElement = document.getElementById('currentTitle');
	        currentMusicImg.src = imgUrl;
	        currentMusicUploader.textContent = nickname;
	        currentMusicTitle.textContent = aiSinger + ' - ' + title + '(' + oriSinger + ')';
			
			musicLike.setAttribute("data-music-play-id", id);
			musicAdd.setAttribute('data-music-play-add-id', id);
			currentMusicUploader.setAttribute('data-music-play-nickname', nickname);
			if(nickname == loginNickname) {
				currentUploaderFollow.style.display = 'none';
			} else {
				currentUploaderFollow.style.display = 'block';
				currentUploaderFollow.setAttribute('data-music-play-nickname', nickname);
			}
			
			console.log("likedMusicIds:",likedMusicIds);
			    likedMusicIds.forEach(function(musicId) {
	            $('img[data-music-play-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
	        });
	
			musicLike.setAttribute('data-music-play-id', id);
			
			$.ajax({
	            type: 'GET',
	            url: '/getExistLikedAndAddMusic',
				data: {
			            musicId: id,
			        },
	            dataType: 'json',
	            success: function (response) {
					if (response.likedExist) {
						musicLike.src = '/static/images/like_on.png';
			        } else {
						musicLike.src = '/static/images/black_like.png';
			        }

					if(response.addExist) {
						musicAdd.src = '/static/images/addSong_on.png';
					} else {
						musicAdd.src = '/static/images/black_add_song.png';
					}
	            },
	            error: function (xhr) {
	            }
	        });

			musicLike.addEventListener('click', function () {
				var musicId = parseInt(musicLike.getAttribute('data-music-play-id'));
				
				if (!likedMusicIds) {
					alert("로그인 후 이용 가능합니다.");
					return;
				}
				
				if (likedMusicIds.includes(musicId)) {
	                alert('이미 추천한 음악입니다.');
	                return;
	            }
	
				musicLike.setAttribute('src', '/static/images/like_on.png');
				likedMusicIds.push(musicId);
				
					$.ajax({
		            type: 'POST',
		            url: '/like/' + musicId,
		            dataType: 'json',
		            success: function (response) {
		                // 성공적으로 업데이트된 좋아요 수를 받아와 화면 갱신
		                //$('#likeCount').text(updatedLikeCount);
		                console.log("updatedLikeCount", response.updatedLikeCount);
						$('#likeCount' + musicId).text(response.updatedLikeCount);
		                // 로컬 스토리지에 좋아요 눌렀음을 표시
						musicLike.src = '/static/images/like_on.png';
						    response.likedMusicIds.forEach(function(musicId) {
				            $('img[data-like-music-id="' + musicId + '"]').attr('src', '/static/images/like_on.png');
				        });
	
		            },
		            error: function (xhr) {
		            }
		        });
	
			
			});
			
			musicAdd.addEventListener('click', function () {
				var musicId = parseInt(musicAdd.getAttribute('data-music-play-add-id'));
				const ColumnMusicAdd =  document.querySelectorAll('[data-add-music-id="' + musicId + '"]');// 음악 재생 바 추가 버튼 
				if (ColumnMusicAdd.length > 0) {
					var ColumnMusicId = parseInt(ColumnMusicAdd[0].getAttribute('data-add-music-id')); // 재생 중인 음악 ID
				}
				
				console.log("ColumnMusicId", ColumnMusicAdd);
				if (!addMusicIds) {
					alert("로그인 후 이용 가능합니다.");
					return;
				}
	
				if (addMusicIds.includes(musicId)) {

					$.ajax({
						type: 'POST',
						url: '/addMusicCancel/' + musicId, // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
						success: function(response) {
							// 서버로부터 받은 데이터를 변수에 할당
							musicAdd.src = '/static/images/black_add_song.png';
							var valueToDelete = musicId; // 삭제할 값
			
							var indexToDelete = addMusicIds.indexOf(valueToDelete); // 삭제할 값의 인덱스 찾기
							if (indexToDelete !== -1) {
								addMusicIds.splice(indexToDelete, 1); // 해당 인덱스에서 1개의 요소 삭제
							}
							
							if(ColumnMusicAdd && ColumnMusicId == musicId) {
								
								ColumnMusicAdd.forEach(element => {
									element.src = '/static/images/add_song.png';
							    });
							}
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
						musicAdd.src = '/static/images/addSong_on.png';
						
						if(ColumnMusicAdd && ColumnMusicId == musicId) {
								ColumnMusicAdd.forEach(element => {
									element.src = '/static/images/addSong_on.png';
							    });
						}
						addMusicIds.push(parseInt(musicId, 10));
						alert('보관함에서 추가되었습니다.');
					},
					error: function(error, xhr) {
						if (xhr.status === 401) {
							alert('로그인 후 이용해주십시오');
						}
					}
				});
	
			});
			
	    }

	// 모든 allMusic 배열에 있는 음악 항목을 표시합니다.
	function displayLoadMusicList() {
	    // allMusic 배열에 있는 모든 항목을 순회합니다.
	    allMusic.forEach(function (music) {
	        // music-list 엘리먼트를 복제합니다.
	        var newMusicColumn = musicList.cloneNode(true);
	
	        // 각 음악 항목에 대한 내용을 설정합니다.
	        var imgElement = newMusicColumn.querySelector('.music-list-column-img');
	        imgElement.src = music.imgUrl;
	
	        var titleElement = newMusicColumn.querySelector('.music-list-column-title');
	        titleElement.textContent = music.aiSinger + ' - ' + music.title + '(' + music.oriSinger + ')';
	
	        var uploaderElement = newMusicColumn.querySelector('.music-list-column-uploader');
	        uploaderElement.textContent = music.nickname;
	
	        var liAudioDuration = newMusicColumn.querySelector('.music-list-duration');
	        var liAudio = newMusicColumn.querySelector('.liAudio');
	        liAudio.src = music.musicUrl;
	
	        liAudio.addEventListener("loadeddata", () => {
	            let audioDuration = liAudio.duration;
	            let totalMin = Math.floor(audioDuration / 60);
	            let totalSec = Math.floor(audioDuration % 60);
	            if (totalSec < 10) totalSec = `0${totalSec}`;
	
	            liAudioDuration.innerText = `${totalMin}:${totalSec}`;
	            liAudioDuration.setAttribute("data-duration", `${totalMin}:${totalSec}`);
	        });
	
			// 새로운 음악 엘리먼트를 문서에 추가
	    	musicList.parentNode.appendChild(newMusicColumn);

	        // 표시를 위해 스타일을 설정합니다.
	        newMusicColumn.style.display = '';
			});
			document.addEventListener('click', function (event) {
			    if (event.target.classList.contains('music-list-play-button')) {
			        // 클릭된 요소가 어떤 행인지 확인
			        var clickedRowIndex = Array.from(event.target.closest('.music-list-div-block').querySelectorAll('.music-list-column-div-block')).indexOf(event.target.closest('.music-list-column-div-block')) - 1;
			        
					console.log('parent:', event.target.closest('.music-list-column-div-block').parentNode.children);
					console.log('index:', clickedRowIndex);
			        // 해당 인덱스로 allMusic 배열에서 음악 정보 가져오기
			        var clickedMusic = allMusic[clickedRowIndex];// music-list-play-button이 클릭된 경우의 처리
					console.log("allMusic:", allMusic[0]);
	
			        console.log('Clicked music URL:', clickedMusic.musicUrl);
			        musicAudio.src = clickedMusic.musicUrl;
			        musicAudio.play();
			        musicPlayBtn.src = '/static/images/pause_button.png';

					console.log('Clicked music ID:', clickedMusic.id);
					$.ajax({
			            type: 'GET',
			            url: '/getExistLikedMusic',
						data: {
					            musicId: clickedMusic.id,
					        },
			            dataType: 'json',
			            success: function (response) {
							
							if (response.exist) {
								musicLike.src = '/static/images/like_on.png';
					        } else {
								musicLike.src = '/static/images/black_like.png';
					        }
			            },
			            error: function (xhr) {
			            }
			        });

			        // 나머지 이미지 및 음악 정보 업데이트
			        //var imgElement = document.querySelector('.current-music-img');
			        //var uploaderElement = document.getElementById('currentUploader');
			        //var titleElement = document.getElementById('currentTitle');
			        currentMusicImg.src = clickedMusic.imgUrl;
			        currentMusicUploader.textContent = clickedMusic.nickname;
			        currentMusicTitle.textContent = clickedMusic.aiSinger + ' - ' + clickedMusic.title + '(' + clickedMusic.oriSinger + ')';
					
					musicIndex = clickedRowIndex;
				}
			});
		
			document.addEventListener('click', function (event) {
			    if (event.target.classList.contains('music-list-erase-img')) {
					 var clickedRowIndex = Array.from(event.target.closest('.music-list-div-block').querySelectorAll('.music-list-column-div-block')).indexOf(event.target.closest('.music-list-column-div-block')) - 1;
				
					// 삭제할 음악 인덱스를 clickedMusicIndex에 설정
			        clickedMusicIndex = clickedRowIndex;
			
			        // 배열에서 해당 인덱스의 음악을 삭제
			        if (clickedMusicIndex >= 0 && clickedMusicIndex < allMusic.length) {
						removeMusicFromLocalStorage(clickedMusicIndex);
			            allMusic.splice(clickedMusicIndex, 1);
			        }
					
					var clickedRow = event.target.closest('.music-list-column-div-block');
	        		clickedRow.parentNode.removeChild(clickedRow);
					
				}	
			});
	}


	// allMusic 배열의 마지막 음악 정보를 표시하는 함수
	function displayMusicList() {
		
	    // allMusic 배열이 비어있으면 아무것도 하지 않음
	    if (allMusic.length === 0) {
	        return;
	    }
	
	    // 마지막 음악 정보 가져오기
	    var lastMusic = allMusic[allMusic.length - 1];
	
	    // music-list 엘리먼트 복제
	    var newMusicColumn = musicList.cloneNode(true);
	
	    // 음악 정보 표시
	    var imgElement = newMusicColumn.querySelector('.music-list-column-img');
	    imgElement.src = lastMusic.imgUrl;
	
	    var titleElement = newMusicColumn.querySelector('.music-list-column-title');
	    titleElement.textContent = lastMusic.aiSinger + ' - ' + lastMusic.title + '(' + lastMusic.oriSinger + ')';
	
		var uploaderElement = newMusicColumn.querySelector('.music-list-column-uploader');
		uploaderElement.textContent = lastMusic.nickname;
		
		let liAudioDuration = newMusicColumn.querySelector('.music-list-duration');
	    var liAudio = newMusicColumn.querySelector('.liAudio');
		liAudio.src = lastMusic.musicUrl;
		
		liAudio.addEventListener("loadeddata", () => {
                let audioDuration = liAudio.duration;
                let totalMin = Math.floor(audioDuration / 60);
                let totalSec = Math.floor(audioDuration % 60);
                if (totalSec < 10) totalSec = `0${totalSec}`;

                liAudioDuration.innerText = `${totalMin}:${totalSec}`;
                liAudioDuration.setAttribute("data-duration", `${totalMin}:${totalSec}`);
            });


	    // 새로운 음악 엘리먼트를 문서에 추가
	    musicList.parentNode.appendChild(newMusicColumn);

		// 추가된 행을 표시
	    newMusicColumn.style.display = '';
		
		console.log("플레이버튼:", musicListPlayBtn);
		document.addEventListener('click', function (event) {
		    if (event.target.classList.contains('music-list-play-button')) {
		        // 클릭된 요소가 어떤 행인지 확인
		        var clickedRowIndex = Array.from(event.target.closest('.music-list-div-block').querySelectorAll('.music-list-column-div-block')).indexOf(event.target.closest('.music-list-column-div-block')) - 1;
		        
				console.log('parent:', event.target.closest('.music-list-column-div-block').parentNode.children);
				console.log('index:', clickedRowIndex);
		        // 해당 인덱스로 allMusic 배열에서 음악 정보 가져오기
		        var clickedMusic = allMusic[clickedRowIndex];// music-list-play-button이 클릭된 경우의 처리
				console.log("allMusic:", allMusic[0]);

		        console.log('Clicked music URL:', clickedMusic.musicUrl);
		        musicAudio.src = clickedMusic.musicUrl;
		        musicAudio.play();
		        musicPlayBtn.src = '/static/images/pause_button.png';
				
				$.ajax({
			            type: 'GET',
			            url: '/getExistLikedMusic',
						data: {
					            musicId: clickedMusic.id,
					        },
			            dataType: 'json',
			            success: function (response) {
							
							if (response.exist) {
								musicLike.src = '/static/images/like_on.png';
					        } else {
								musicLike.src = '/static/images/black_like.png';
					        }
			            },
			            error: function (xhr) {
			            }
			        });
		        // 나머지 이미지 및 음악 정보 업데이트
		        //var imgElement = document.querySelector('.current-music-img');
		        //var uploaderElement = document.getElementById('currentUploader');
		        //var titleElement = document.getElementById('currentTitle');
		        currentMusicImg.src = clickedMusic.imgUrl;
		        currentMusicUploader.textContent = clickedMusic.nickname;
		        currentMusicTitle.textContent = clickedMusic.aiSinger + ' - ' + clickedMusic.title + '(' + clickedMusic.oriSinger + ')';
				
				musicIndex = clickedRowIndex;
			}
		});
		
		document.addEventListener('click', function (event) {
		    if (event.target.classList.contains('music-list-erase-img')) {
				 var clickedRowIndex = Array.from(event.target.closest('.music-list-div-block').querySelectorAll('.music-list-column-div-block')).indexOf(event.target.closest('.music-list-column-div-block')) - 1;
			
				// 삭제할 음악 인덱스를 clickedMusicIndex에 설정
		        clickedMusicIndex = clickedRowIndex;
		
		        // 배열에서 해당 인덱스의 음악을 삭제
		        if (clickedMusicIndex >= 0 && clickedMusicIndex < allMusic.length) {
					removeMusicFromLocalStorage(clickedMusicIndex);
		            allMusic.splice(clickedMusicIndex, 1);
		        }
				
				var clickedRow = event.target.closest('.music-list-column-div-block');
        		clickedRow.parentNode.removeChild(clickedRow);
				
			}	
		});
	}


	function removeMusicFromLocalStorage(indexToRemove) {
	    // 로컬 스토리지에서 저장된 음악 리스트를 불러옵니다.
	    var storedMusicList = JSON.parse(localStorage.getItem('allMusic')) || [];
	
	    // 삭제할 음악을 찾아서 제거합니다.
	    if (indexToRemove >= 0 && indexToRemove < storedMusicList.length) {
	        storedMusicList.splice(indexToRemove, 1);
	
	        // 수정된 음악 리스트를 다시 로컬 스토리지에 저장합니다.
	        localStorage.setItem('allMusic', JSON.stringify(storedMusicList));
	    }
	}

	// 플레이 버튼
     function playMusicFunction(){
            musicAudio.play();
     }

	musicPlay.addEventListener('click', () => {
        //var audio = document.getElementById('main-audio');
        //var playButton = document.querySelector('.control-play');

        if (musicAudio.paused) {
            // 음악이 일시 중지된 경우, 재생
            musicAudio.play();
			musicPlayBtn.src = '/static/images/pause_button.png';
        } else {
            // 음악이 재생 중인 경우, 일시 중지
            musicAudio.pause();
            musicPlayBtn.src = '/static/images/play_button.png';
            
        }
    });

	musicPrevBtn.addEventListener('click', () => {
		console.log("musicIndex:", musicIndex);
		if (musicIndex > 0) {
			musicIndex --;
			var prevMusic = allMusic[musicIndex];
			musicAudio.src = prevMusic.musicUrl;
			musicAudio.play();
			musicPlayBtn.src = '/static/images/pause_button.png';
			// 나머지 이미지 및 음악 정보 업데이트
			//var imgElement = document.querySelector('.current-music-img');
			//var uploaderElement = document.getElementById('currentUploader');
		    //var titleElement = document.getElementById('currentTitle');
			currentMusicImg.src = prevMusic.imgUrl;
			currentMusicUploader.textContent = prevMusic.nickname;
			currentMusicTitle.textContent = prevMusic.aiSinger + ' - ' + prevMusic.title + '(' + prevMusic.oriSinger + ')';
		}
	});
	
	musicNextBtn.addEventListener('click', () => {
		console.log("musicIndex:", musicIndex);
		console.log("length:", allMusic.length);
		if (musicIndex <= allMusic.length - 1) {
			musicIndex ++;
			var nextMusic = allMusic[musicIndex];
			musicAudio.src = nextMusic.musicUrl;
			musicAudio.play();
			musicPlayBtn.src = '/static/images/pause_button.png';
			// 나머지 이미지 및 음악 정보 업데이트
			//var imgElement = document.querySelector('.current-music-img');
			//var uploaderElement = document.getElementById('currentUploader');
		    //var titleElement = document.getElementById('currentTitle');
			currentMusicImg.src = nextMusic.imgUrl;
			currentMusicUploader.textContent = nextMusic.nickname;
			currentMusicTitle.textContent = nextMusic.aiSinger + ' - ' + nextMusic.title + '(' + nextMusic.oriSinger + ')';
		}
	});
	

	musicAudio.addEventListener("ended", ()=>{
            if (musicIndex <= allMusic.length - 1) {
				musicIndex ++;
				var nextMusic = allMusic[musicIndex];
				musicAudio.src = nextMusic.musicUrl;
				musicAudio.play();
				musicPlayBtn.src = '/static/images/pause_button.png';
				// 나머지 이미지 및 음악 정보 업데이트
				//var imgElement = document.querySelector('.current-music-img');
				//var uploaderElement = document.getElementById('currentUploader');
			    //var titleElement = document.getElementById('currentTitle');
				currentMusicImg.src = nextMusic.imgUrl;
				currentMusicUploader.textContent = nextMusic.nickname;
				currentMusicTitle.textContent = nextMusic.aiSinger + ' - ' + nextMusic.title + '(' + nextMusic.oriSinger + ')';
			}
        })
	function showFooter() {
        var footer = document.querySelector('.music-play-footer');
        footer.style.display = '';
        // 저장된 상태를 localStorage에 'footerStatus'라는 키로 저장
        localStorage.setItem('footerStatus', 'visible');
    }

    function hideFooter() {
        var footer = document.querySelector('.music-play-footer');
        footer.style.display = 'none';
        // 저장된 상태를 localStorage에 'footerStatus'라는 키로 저장
        localStorage.setItem('footerStatus', 'hidden');
    }


    function onPageLoad() {
	    var storedStatus = localStorage.getItem('footerStatus');
	    var storedMusicStatus = localStorage.getItem('musicStatus');
	    var storedMusicInfo = localStorage.getItem('musicInfo');
	
	    var footer = document.querySelector('.music-play-footer');
	    if (storedStatus === 'visible') {
	        footer.style.display = '';
	    } else {
	        footer.style.display = 'none';
	    }
	
	    if (storedMusicStatus === 'playing' && storedMusicInfo) {
	        // 저장된 음악 정보가 있을 경우 플레이어 초기화
	        var musicInfo = JSON.parse(storedMusicInfo);
	        playMusic(
	            musicInfo.musicUrl,
	            musicInfo.imgUrl,
	            musicInfo.aiSinger,
	            musicInfo.title,
	            musicInfo.oriSinger,
	            musicInfo.nickname
	        );
	    }
	}

	//페이지 로드 시 onPageLoad 함수 호출
	//document.addEventListener('DOMContentLoaded', onPageLoad);


	// 뮤직 진행바
	
        musicAudio.addEventListener("timeupdate", updateTime);
		musicAudio.addEventListener("loadeddata", updateDuration);
		
		function updateTime(e) {
		    const currentTime = e.target.currentTime;
		    const duration = e.target.duration;
		    let progressWidth = (currentTime / duration) * 100;
		    musicProgressBar.style.width = `${progressWidth}%`;
		
		    let currentMin = Math.floor(currentTime / 60);
		    let currentSec = Math.floor(currentTime % 60);
		    if (currentSec < 10) currentSec = `0${currentSec}`;
		    musicProgressCurrent.innerText = `${currentMin}:${currentSec}`;
		}
		
		function updateDuration() {
		    let audioDuration = musicAudio.duration;
		    let totalMin = Math.floor(audioDuration / 60);
		    let totalSec = Math.floor(audioDuration % 60);
		    if (totalSec < 10) totalSec = `0${totalSec}`;
		
		    musicProgressDuration.innerText = `${totalMin}:${totalSec}`;
		}

        // 진행 버튼
        musicProgress.addEventListener("click", e=>{
            let progressWidth = musicProgress.clientWidth;
            let clickedOffsetX = e.offsetX;
            let songDuration = musicAudio.duration;
            
            musicAudio.currentTime = (clickedOffsetX / progressWidth) * songDuration;
            playMusicFunction();
        })

		musicVol.oninput = function(){
			musicAudio.volume = musicVol.value / 100;
			localStorage.setItem("musicVolume", musicVol.value);
		}
		
		function toggleMusicList() {
	        // Get the element with the specified class name
			console.log("toggleMusicList 실행");
	        var musicListDiv = document.querySelector('.music-list-div-block');
	
	        // Toggle the display style between 'none' and an empty string
	        if (musicListDiv.style.display === 'none') {
	            musicListDiv.style.display = '';
	        } else {
	            musicListDiv.style.display = 'none';
	        }
	    }
	
		function showIcons(element) {
	        // Select the child elements within the specified parent element
	        var eraseIcon = element.querySelector('.music-list-erase-img');
	        var playIcon = element.querySelector('.music-list-play-button');
	
	        // Set the display style to an empty string to show the icons
	        eraseIcon.style.display = '';
	        playIcon.style.display = '';
	    }
	
	    function hideIcons(element) {
	        // Select the child elements within the specified parent element
	        var eraseIcon = element.querySelector('.music-list-erase-img');
	        var playIcon = element.querySelector('.music-list-play-button');
	
	        // Set the display style to 'none' to hide the icons
	        eraseIcon.style.display = 'none';
	        playIcon.style.display = 'none';
	    }

		muteBtn.addEventListener('click', () => {
		if (musicAudio.muted) {
			musicAudio.muted = false;
			muteBtn.src = '/static/images/speaker_button.png';
		} else {
			musicAudio.muted = true;
			muteBtn.src = '/static/images/mute_button.png';
		}
	});