const addMusicBtn = document.querySelector(".main-top-music-function-addsong");
var likedMusicIdsStr = document.getElementById('likedMusicIds').value;
var likedMusicIds = JSON.parse(likedMusicIdsStr);
var addMusicIdsStr = document.getElementById('addMusicIds').value;
var addMusicIds = JSON.parse(addMusicIdsStr);

function addMusic(element) {

	var musicId = parseInt(element.getAttribute('data-add-music-id'), 10); // 클릭한 음악 ID
	//var addMusicIds = JSON.parse(element.getAttribute('data-add-music-ids')); // 사용자가 보관한 음악 ID 
	const playMusicAdd = document.querySelector(".control-music-add"); // 음악 재생 바 추가 버튼 
	var playAddMusicId = parseInt(playMusicAdd.getAttribute('data-music-play-add-id')); // 재생 중인 음악 ID
	console.log("musicId:", musicId);
	console.log("addMusicId:", addMusicIds);
	console.log("playMusicId:", playAddMusicId);
	// 보관함에 추가되있는 경우 삭제
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
				$(element).attr('src', '/static/images/add_song.png');
				var valueToDelete = musicId; // 삭제할 값

				var indexToDelete = addMusicIds.indexOf(valueToDelete); // 삭제할 값의 인덱스 찾기
				if (indexToDelete !== -1) {
					addMusicIds.splice(indexToDelete, 1); // 해당 인덱스에서 1개의 요소 삭제
				}
				
				if(playAddMusicId == musicId) {
					playMusicAdd.src = '/static/images/black_add_song.png';
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
			$(element).attr('src', '/static/images/addSong_on.png');
			
			if(playAddMusicId == musicId) {
				playMusicAdd.src = '/static/images/addSong_on.png';
			}
			alert('보관함에서 추가되었습니다.');

		},
		error: function(error, xhr) {
			if (xhr.status === 401) {
				alert('로그인 후 이용해주십시오');
			}
		}
	});

	$(this).attr('src', '/static/images/addSong_on.png');
	addMusicIds.push(parseInt(musicId, 10));
}

function likeMusic(element) {

	var musicId = parseInt(element.getAttribute('data-like-music-id'), 10); // 클릭한 음악 ID
	//var likeMusicIds = JSON.parse(element.getAttribute('data-like-music-ids')); // 사용자가 보관한 음악 ID 
	const playMusicLike = document.querySelector(".control-music-like");
	var playMusicId = parseInt(playMusicLike.getAttribute('data-music-play-id'));
	console.log("musicId:", musicId);
	console.log("likedMusicId:", likedMusicIds);
	console.log("playMusicId:", playMusicId);
	if (!likedMusicIds) {
		alert("로그인 후 이용 가능합니다.");
		return;
	}

	if (likedMusicIds.includes(musicId)) {
		alert("이미 추천한 음악입니다.");
		return;
	}

	$.ajax({
		type: 'POST',
		url: '/like/' + musicId, // 서버에서 데이터를 얻기 위한 적절한 엔드포인트 사용
		success: function(response) {

			if (response.sessionError) {
				alert("로그인 후 이용해주십시오");
				return; // Ajax 종료
			}
			console.log("updatedLikeCount:", response.updatedLikeCount);
			$('#likeCount' + musicId).text(response.updatedLikeCount);
			// 서버로부터 받은 데이터를 변수에 할당
			$(element).attr('src', '/static/images/like_on.png');
			
			if(musicId == playMusicId) {
				playMusicLike.src = '/static/images/like_on.png';
			}
		},
		error: function(error, xhr) {
			if (xhr.status === 401) {
				//alert('로그인 후 이용해주십시오');
			}
		}
	});

	//$(this).attr('src', '/static/images/addSong_on.png');
	// likedMusicIds에 추가
	likedMusicIds.push(parseInt(musicId, 10));
}