<html>
    <meta charset = "utf-8" />

    <title>WebSocket Test</title>

    <script type = "text/javascript">

        var wsUri;

        var output;

        var name;

        function init()
        {
            output = document.getElementById("output");

            name = document.getElementById("text1").value;

            if(document.location.protocol === "https:")
            {
                wsUri = "wss://" + document.location.host + "/chat/" + name;
            }

            else
            {
                wsUri = "ws://" + document.location.host + "/chat/" + name;
            }

            ConnectWebSocket();
        }


        function ConnectWebSocket()
        {
            websocket = new WebSocket(wsUri);

            websocket.onopen = function(evt)
            {
                onOpen(evt)
            };

            websocket.onclose = function(evt)
            {
                ConnectWebSocket()
            };

            websocket.onmessage = function(evt)
            {
                onMessage(evt)
            };

            websocket.onerror = function(evt)
            {
                onError("Please Enter Name To Connect")
            };
        }

        function onOpen(evt)
        {
            writeToScreen("CONNECTED");
        }

        function onName(evt)
        {
            onOpen(evt);
        }

        function onClose(evt)
        {
            writeToScreen("DISCONNECTED");

            if(evt === 1)
            {
                websocket.close();
            }
        }

        function onMessage(evt)
        {
            writeToScreen('<span style = "color: blue;">RESPONSE: ' + evt.data+'</span>');
        }

        function onError(evt)
        {
            writeToScreen('<span style = "color: red;">ERROR:</span> ' + evt);
        }

        function doSend()
        {
            var message = document.getElementById("text2").value;

            writeToScreen("SENT: " + message);

            websocket.send(message);
        }

        function writeToScreen(message)
        {
            var pre = document.createElement("p");

            pre.style.wordWrap = "break-word";

            pre.innerHTML = message;

            output.appendChild(pre);
        }

    </script>

    <h2>WebSocket Application</h2>
    <div id = "output">

        <label>Enter Name :</label>

        <input id="text1">

        <label>Message :</label>

        <input id="text2" value="Message_{name of client}">

        <button onclick="doSend()">Send</button>

        <button onclick="init()">Connect</button>

        <button onclick="onClose(1)">DisConnect</button>

    </div>

</html>