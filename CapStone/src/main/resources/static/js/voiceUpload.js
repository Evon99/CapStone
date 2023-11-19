 		function voiceFileEvent() {
			    console.log("bindDomEvent function is called!");
			    $(".voice-file-input").on("change", function () {
			        
			        var fileName = $(this).val().split("\\").pop(); // 이미지 파일명
			        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
			        // 확장자 추출
			     	fileExt = fileExt.toLowerCase(); // 소문자 변환
			     	
			     	if(fileExt != "wav" && fileExt != "pth" && fileExt != "index" && fileExt != "zip") {
			     		alert("보이스 파일만 업로드해주세요.");
			     		return;
			     	}
			     	
			     	$("#voiceFileName").text(fileName);
			     

			       
			    });
			}
		  
		  function voiceImageEvent() {
			    console.log("bindDomEvent function is called!");
			    $(".voice-Image-input").on("change", function () {
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
			                    displaySelectedVoiceFileImage(response.encodedImage);
			                } else {
			                	displayDefaultVoiceFileImage();
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
			function displaySelectedVoiceFileImage(encodedImage) {
			    const image = document.getElementById("voiceImage");
			    const fileInput = document.getElementById("voiceImageInput");

			    const selectedFile = fileInput.files[0];

			    if (selectedFile) {
			        // 이미지 파일의 Base64 데이터를 이용하여 이미지 변경
			        image.src = 'data:' + selectedFile.type + ';base64,' + encodedImage;
			    }
			}
			
			// 이미지 파일이 아닐 경우 기본 이미지 유지
			function displayDefaultVoiceFileImage() {
			    const image = document.getElementById("voiceImage");
			    const defaultImageSrc = image.getAttribute("data-default-src");
			
			 // 일정 시간이 지난 후에 이미지를 표시
			    setTimeout(function() {
			        image.src = defaultImageSrc;
			    }, 100); // 예: 100 밀리초 후에 실행
			}
			
			
