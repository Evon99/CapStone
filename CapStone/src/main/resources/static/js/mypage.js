// tracks-column �겢由� �떆
function TracksClick() {
	$('.tracks-column').toggleClass('active'); // �겢�옒�뒪 �넗湲�

	// playlist-column 珥덇린�솕
	$('.playlist-column').removeClass('active');

	$('#addMusics *').hide();
	$('#uploadMusics *').show();
}

// playlist-column �겢由� �떆
function PlayListClick() {
	$('.playlist-column').toggleClass('active'); // �겢�옒�뒪 �넗湲�

	// tracks-column 珥덇린�솕
	$('.tracks-column').removeClass('active');

	$('#addMusics *').show();
	$('#uploadMusics *').hide();
}


function bindDomEvent() {
	console.log("bindDomEvent function is called!");
	$(".custom-file-input").on("change", function() {
		var fileName = $(this).val().split("\\").pop();  // �씠誘몄� �뙆�씪紐�
		var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); // �솗�옣�옄 異붿텧
		fileExt = fileExt.toLowerCase(); // �냼臾몄옄 蹂��솚

		var formData = new FormData();
		formData.append('file', this.files[0]);
		formData.append('memberName', $("#memberName").text());
		$.ajax({
			url: '/updateImage',
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				if (response.isImage) {
					// 이미지 파일일 경우 프로필 이미지 변경
					displaySelectedImage(response.encodedImage);
				} else {
					displayDefaultImage();
					alert("이미지 파일만 등록이 가능합니다.");
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
			}
		});
	});
}

function displaySelectedImage(encodedImage) {
	const image = document.getElementById("profile");
	const loginImage = document.getElementById("loginPicture");
	const fileInput = document.getElementById("fileInput");

	const selectedFile = fileInput.files[0];

	if (selectedFile) {
		// 이미지 파일의 Base64 데이터를 이용하여 이미지 변경
		image.src = 'data:' + selectedFile.type + ';base64,' + encodedImage;
		loginImage.src = 'data:' + selectedFile.type + ';base64,' + encodedImage;
	}
}

function displayDefaultImage() {
	const image = document.getElementById("profile");
	const defaultImageSrc = image.getAttribute("data-default-src");

	// �씪�젙 �떆媛꾩씠 吏��궃 �썑�뿉 �씠誘몄�瑜� �몴�떆
	setTimeout(function() {
		image.src = defaultImageSrc;
	}, 100); // �삁: 100 諛�由ъ큹 �썑�뿉 �떎�뻾
}
