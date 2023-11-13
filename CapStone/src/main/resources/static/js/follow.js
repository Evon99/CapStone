 	function toggleFollow(element) {
			    var nickname = element.getAttribute('data-nickname');
			    var loginNickname = element.getAttribute('data-login-nickname');

			    // �꽌踰꾩뿉 �뙏濡쒖슦 �삉�뒗 �뼵�뙏濡쒖슦 �슂泥�
			    $.ajax({
			        type: 'POST',
			        url: '/toggleFollow',
			        data: {
			            nickname: nickname,
			            loginNickname: loginNickname
			        },
			        success: function(response) {
			            // �꽌踰� �쓳�떟�뿉 �뵲瑜� �룞�옉
			            console.log(response);
			            if (response === 'Followed' || response === 'Unfollowed') {
			                // �뙏濡쒖슦 �긽�깭�뿉 �뵲�씪 UI �뾽�뜲�씠�듃
			                element.textContent = (response === 'Followed') ? '팔로잉' : '팔로우';
			                element.style.backgroundColor = (response === 'Followed') ? '#ccc' : '#66c';
			                element.style.border = (response === 'Followed') ? '1px solid #ccc' : '1px solid #6c';
			                element.style.color = (response === 'Followed') ? 'black' : 'white';
			            }
			        },
			        error: function(error) {
			            console.error(error);
			        }
			    });

			}
	function ModaltoggleFollow(element) {
		    var nickname = element.getAttribute('data-nickname');
		    var loginNickname = element.getAttribute('data-login-nickname');

		    $.ajax({
		        type: 'POST',
		        url: '/toggleFollow',
		        data: {
		            nickname: nickname,
		            loginNickname: loginNickname
		        },
		        success: function(response) {
		            console.log(response);
		            if (response === 'Followed' || response === 'Unfollowed') {
		                element.textContent = (response === 'Followed') ? '팔로잉' : '팔로우';
		                element.style.backgroundColor = (response === 'Followed') ? '#ccc' : '#66c';
		                element.style.border = (response === 'Followed') ? '1px solid #ccc' : '1px solid #6c';
		                element.style.color = (response === 'Followed') ? 'black' : 'white';
		            }
		        },
		        error: function(error) {
		            console.error(error);
		        }
		    });
		}

		function FollowingModaltoggleFollow(element) {
			    var nickname = element.getAttribute('data-nickname');
			    var loginNickname = element.getAttribute('data-login-nickname');

			    // �꽌踰꾩뿉 �뙏濡쒖슦 �삉�뒗 �뼵�뙏濡쒖슦 �슂泥�
			    $.ajax({
			        type: 'POST',
			        url: '/toggleFollow',
			        data: {
			            nickname: nickname,
			            loginNickname: loginNickname
			        },
			        success: function(response) {
			            // �꽌踰� �쓳�떟�뿉 �뵲瑜� �룞�옉
			            console.log(response);
			            if (response === 'Followed' || response === 'Unfollowed') {
			                // �뙏濡쒖슦 �긽�깭�뿉 �뵲�씪 UI �뾽�뜲�씠�듃
			                element.textContent = (response === 'Followed') ? '팔로잉' : '팔로우';
			                element.style.backgroundColor = (response === 'Followed') ? '#ccc' : '#66c';
			                element.style.border = (response === 'Followed') ? '1px solid #ccc' : '1px solid #6c';
			                element.style.color = (response === 'Followed') ? 'black' : 'white';
			            }
			        },
			        error: function(error) {
			            console.error(error);
			        }
			    });
			}
			
	   function PlayBartoggleFollow(element) {
			    var nickname = element.getAttribute('data-music-play-nickname');

			    // �꽌踰꾩뿉 �뙏濡쒖슦 �삉�뒗 �뼵�뙏濡쒖슦 �슂泥�
			    $.ajax({
			        type: 'POST',
			        url: '/playBartoggleFollow',
			        data: {
			            nickname: nickname
			        },
			        success: function(response) {
			            // �꽌踰� �쓳�떟�뿉 �뵲瑜� �룞�옉
			            console.log(response);
			            if (response === 'Followed' || response === 'Unfollowed') {
			                // �뙏濡쒖슦 �긽�깭�뿉 �뵲�씪 UI �뾽�뜲�씠�듃
			                element.src = (response === 'Followed') ? '/static/images/follow_on.png' : '/static/images/follow_button.png';
			            }
			        },
			        error: function(error) {
			            console.error(error);
			        }
			    });
			}