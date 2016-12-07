var stompClient = null;
var messageCounter = 0;

function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            newMessage(JSON.parse(greeting.body).content);
        });
    });
}

function newMessage(message) {
    var response = document.getElementById('notification');
    var p = document.createElement('p');
    var messageContent;
    if (messageCounter >= 1) {
        var lastMessage = response.lastElementChild;
        response.removeChild(lastMessage);
        messageContent = (messageCounter + 1) + " neue Posts";
    } else if (messageCounter == 0) {
        messageContent = (messageCounter + 1) + " neuer Post";
        response.style.display = 'inherit';
    }
    messageCounter++;
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageContent));
    response.appendChild(p);

    console.log(message);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendContent() {
    var content = document.getElementById('content').value;
    stompClient.send("/app/hello", {}, JSON.stringify({'content': content}));
}