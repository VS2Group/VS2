var stompClient = null;

function receiveNewPostReply(newPostReply) {
    console.log("Got new post reply");
    var reply = JSON.parse(newPostReply.body);

    addPostToTop(reply.post);
}

function registerForNewPostsRequest() {
    stompClient.send("/app/stomp/register-for-new-posts", {}, JSON.stringify({
        'sessionId': getSessionId()
    }));
    console.log("Connected with /app/stomp/register-for-new-posts");
}

function registerForNewPostsReply(registerForNewPostsReply) {
    console.log("Got register for new posts reply");
    var reply = JSON.parse(registerForNewPostsReply.body);

}


function newPostAvailable(newPostsAvailableNotification) {
    console.log("Got new post available notification")
    var notification = JSON.parse(newPostsAvailableNotification.body);

    alert("New posts available. Reload page.");
}

function connect() {
    var socket = new SockJS('/mysocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//        setConnected(true);
        console.log('Connected:	' + frame);
        stompClient.subscribe('/topic/posts/new-post-reply:' + getSessionId(), receiveNewPostReply);
        stompClient.subscribe('/topic/posts/registerfornewposts-reply:' + getSessionId(), registerForNewPostsReply);
        stompClient.subscribe('/topic/posts/newposts:' + getSessionId(), newPostAvailable);
        registerForNewPostsRequest();
    });
}


function getPostTemplate(username, imageUrl, content, timestamp) {
    var date = new Date(timestamp);
    //var datestr = "" + date.getDate().getDay() + "." + date.getDate().getMonth() + "." + date.getDate().getYear() + " "
    //   + date.getTime().getHours() + ":" + date.getTime().getMinutes() + ":" + date.getTime().getSeconds();

    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var hour = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();

    if (day < 10) {
        day = "0" + day;
    }
    if (month < 10) {
        month = "0" + month;
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }

    var datestr = day + "." + month + "." + year + ", " + hour + ":" + min + ":" + sec;

    var template = "\
    <div class=\"media post thumbnail\" \
        <div> \
            <div class=\"media-left\"> \
                <img src=\"" + imageUrl + "\" alt=\"profile-pic\" class=\"media-object\" style=\"width:80px\"/> \
            </div> \
            <div class=\"media-body\"> \
                <h4 class=\"media-heading\"> \
                    <a href=\"/dashboard/" + username + "\">" + username + "</a> \
                    <small><i>" + datestr + "</i></small> \
                </h4> \
                <p></p> \
                <p>" + content + "</p> \
            </div> \
        </div>\
    </div>";

    return template;
}




function addPostToTop(post) {
    var username = post.user.username;
    var imageUrl = post.user.imageUrl;
    var timestamp = post.timestamp;
    var content = post.content;

    var postStream = document.getElementById('post-stream');
    postStream.innerHTML = getPostTemplate(username, imageUrl, content, timestamp) + postStream.innerHTML;
}


function getSessionId() {
    return document.getElementById('sessionId').innerHTML;
}

function sendNewPostRequest() {
    var content = document.getElementById('post-content').value;
    stompClient.send("/app/stomp/new-post-request", {}, JSON.stringify({
        'sessionId': getSessionId(),
        'content': content
    }));

    return false;
}

/**
 *
 * @param message
 */
function showGreeting(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}

function setConnected(b) {

}

function disconnect() {
    if (stompClient != null)
        stompClient.disconnect();
    setConnected(false);
}