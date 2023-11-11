  
  document.addEventListener('DOMContentLoaded', function() {
	    // 모달 열기
	    document.getElementById('openModalBtn').addEventListener('click', function() {
	    	console.log('Open button clicked');
	        document.getElementById('myModal').style.display = 'block';
	    });

	    // 모달 닫기
	    document.getElementById('closeModalBtn').addEventListener('click', function() {
	        document.getElementById('myModal').style.display = 'none';
	    });

	    // 모달 외부 클릭 시 닫기
	    window.addEventListener('click', function(event) {
	        var modal = document.getElementById('myModal');
	        if (event.target == modal) {
	            modal.style.display = 'none';
	        }
	    });
	});

 	document.addEventListener('DOMContentLoaded', function() {
	    // 모달 열기
	    document.getElementById('openModalBtn2').addEventListener('click', function() {
	    	console.log('Open button clicked');
	        document.getElementById('myModal').style.display = 'block';
	    });

	    // 모달 닫기
	    document.getElementById('closeModalBtn').addEventListener('click', function() {
	        document.getElementById('myModal').style.display = 'none';
	    });

	    // 모달 외부 클릭 시 닫기
	    window.addEventListener('click', function(event) {
	        var modal = document.getElementById('myModal');
	        if (event.target == modal) {
	            modal.style.display = 'none';
	        }
	    });
	});
	
	
	
	
	
	
	