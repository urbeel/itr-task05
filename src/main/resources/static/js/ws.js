let stompClient = null;

const connect = () => {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
};

const onConnected = () => {
    stompClient.subscribe(
        "/user/" + sessionStorage.getItem('username') + "/queue/messages",
        onMessageReceived
    );
};

const onError = () => {
    console.log("Error while connecting to websocket")
}

let message = {
    sender: sessionStorage.getItem('username'),
    recipient: "",
    title: "",
    body: "",
}

const sendMessage = () => {
    message.recipient = document.getElementById("recipient").value;
    message.title = document.getElementById("title").value;
    message.body = document.getElementById("message-body").value;
    if (isValidMessage()){
        stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
    }else {
        alert("Invalid message!")
    }
};

const isValidMessage = () =>{
    message.recipient = message.recipient.trim();
    message.title = message.title.trim();
    message.body = message.body.trim();
    return !(!message.recipient || !message.title || !message.body);
}

const onMessageReceived = (newMessage) => {
    let accordion = document.getElementById("my-accordion");
    let message = JSON.parse(newMessage.body);
    const time = new Date(message.time).toLocaleString()
    accordion.prepend(createAccordionItem(message));
    document.getElementById('message-sender').textContent = message.sender;
    document.getElementById('message-time').textContent = time;
    document.getElementById('message-title').textContent = message.title;
    document.getElementById('message-content').textContent=message.body;
}

const createAccordionItem = (message) =>{
    let accordionItem = document.createElement("div");
    accordionItem.setAttribute("class", "accordion-item");
    accordionItem.innerHTML =
        "<h2 class=\"accordion-header\" id=\"heading" + message.id + "\">\n" +
        "   <button class=\"accordion-button collapsed\" type=\"button\" data-bs-toggle=\"collapse\"\n" +
        "           data-bs-target=\"#collapse" + message.id + "\" aria-expanded=\"false\"\n" +
        "           aria-controls=\"collapse" + message.id + "\">\n" +
        "       <div style='width: 95%'>\n" +
        "           <span>from:</span>\n" +
        "           <strong id='message-sender'></strong>\n" +
        "           <span class='float-end' id='message-time'></span>\n" +
        "           <p class='m-0 mt-3'>" +
        "               title:" +
        "               <span id='message-title' style='word-break: break-word'></span>" +
        "           </p>" +
        "       </div>" +
        "   </button>\n" +
        "</h2>\n" +
        "<div id=\"collapse" + message.id + "\" class=\"accordion-collapse collapse show\"\n" +
        "     aria-labelledby=\"#heading" + message.id + "\"\n" +
        "     data-bs-parent=\"#my-accordion\">\n" +
        "   <div id='message-content' style=\"word-break: break-word\" class=\"accordion-body\"></div>\n" +
        "</div>\n";
    return accordionItem;
}