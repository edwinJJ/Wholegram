var i = 0;
var a = "ws://localhost/chat";
var b;

onmessage = function(event) {
	

    i = i + 1;
    b = new WebSocket(a);
    console.log(event.data);
    console.log(a);
    console.log(b);
    
    
    
/* // WebSocket Server connection
    function init() {
    	ws = new WebSocket(wsUrl);  //소켓 객체 생성
    	ws.onopen = function(evt) { // 이벤트 발생시 opOpen()을 실행
    		onOpen(evt);
    	};
    	
    	ws.onmessage = function(evt) {
    		onMessage(evt);
    	}
    	
    	ws.onerror = function(evt) {
    		onError(evt);
    	}
    	ws.onclose = function(evt) {
    		console.log("close");
    	}
    }

    function onOpen(evt) {
    	
    }

    //서버로부터 대화목록 받음
    function onMessage(evt) {
    	showMessage(evt.data);	
//    	console.log(evt.data);
    }

    function onError(evt) {
    	
    }
    */
    
    
    postMessage(b);
}

