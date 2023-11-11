 		function musicFileEvent() {
			    console.log("bindDomEvent function is called!");
			    $(".music-file-input").on("change", function () {
			        // 서버에 이미지 파일 여부 확인 요청
			        var formData = new FormData();
			        formData.append('file', this.files[0]);

			        $.ajax({
			            url: '/checkMusic',
			            type: 'POST',
			            data: formData,
			            processData: false,
			            contentType: false,
			            success: function (response) {
			                if (response.isAudio) {
			                    var fileName = response.musicTitle;
			                	$("#originalTitle").val(fileName);
			                } else {
			                    alert("음악 파일만 등록이 가능합니다.");
			                }
			            },
			            error: function () {
			                alert("server error");
			            }
			        });
			    });
			}
		  
		  function musicImageEvent() {
			    console.log("bindDomEvent function is called!");
			    $(".music-Image-input").on("change", function () {
			        // 서버에 이미지 파일 여부 확인 요청
			        var formData = new FormData();
			        formData.append('file', this.files[0]);

			        $.ajax({
			            url: '/checkImage',
			            type: 'POST',
			            data: formData,
			            processData: false,
			            contentType: false,
			            success: function (response) {
			                if (response.isImage) {
			                    // 이미지 파일일 경우 프로필 이미지 변경
			                    displaySelectedMusicFileImage(response.encodedImage);
			                } else {
			                	displayDefaultMusicFileImage();
			                    alert("이미지 파일만 등록이 가능합니다.");
			                }
			            },
			            error: function () {
			                alert("서버 오류가 발생했습니다.");
			            }
			        });
			    });
			}

			// 이미지 파일이면 프로필 이미지 변경
			function displaySelectedMusicFileImage(encodedImage) {
			    const image = document.getElementById("musicImage");
			    const fileInput = document.getElementById("musicImageInput");

			    const selectedFile = fileInput.files[0];

			    if (selectedFile) {
			        // 이미지 파일의 Base64 데이터를 이용하여 이미지 변경
			        image.src = 'data:' + selectedFile.type + ';base64,' + encodedImage;
			    }
			}
			
			// 이미지 파일이 아닐 경우 기본 이미지 유지
			function displayDefaultMusicFileImage() {
			    const image = document.getElementById("musicImage");
			    const defaultImageSrc = image.getAttribute("data-default-src");
			
			 // 일정 시간이 지난 후에 이미지를 표시
			    setTimeout(function() {
			        image.src = defaultImageSrc;
			    }, 100); // 예: 100 밀리초 후에 실행
			}
			
			
