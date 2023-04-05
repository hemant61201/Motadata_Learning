<html>
    <head>
        <meta charset = "utf-8" />

        <title>WebSocket_Struts</title>

        <script type="text/javascript">

            let wsUri;

            let output;

            let name;

            let websocket;

            let flag = false;

            function OnOpen(evt)
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

            function ConnectWebSocket(evt)
            {
                websocket = new WebSocket(wsUri);

                if(name === null)
                {
                    websocket.onerror = function(evt)
                    {
                        onError("Please Enter Name To Connect")
                    };
                }

                else
                {
                    websocket.onopen = function (evt) {
                        flag = true;
                        writeToScreen("Connected");
                    };

                    websocket.onerror = function (evt) {
                        onError(evt);
                    };

                    websocket.onmessage = function(evt)
                    {
                        onMessage(evt)
                    };

                    websocket.onclose = function (evt)
                    {
                        ConnectWebSocket();
                    }
                }
            }

            function onError(evt)
            {
                writeToScreen('<span style = "color: red;">ERROR:</span> ' + evt);
            }

            function onMessage(evt)
            {
                writeToScreen('<span style = "color: blue;">RESPONSE: ' + evt.data+'</span>');
            }

            function doSend()
            {
                let message = document.getElementById("text2").value;

                if(flag)
                {
                    writeToScreen("SENT: " + message);

                    websocket.send(message);
                }

                else
                {
                    onError("Not Connected");
                }
            }

            function writeToScreen(message)
            {
                let pre = document.createElement("p");

                pre.style.wordWrap= "break-word";

                pre.innerHTML = message;

                output.appendChild(pre);
            }



        </script>
    </head>

    <body>
        <h2>WebSocket_Struts Application</h2>

        <div id="output">
            <label for="text1">Enter Name :</label>

            <input id="text1">

            <label for="text2">Message :</label>

            <input id="text2" value="Message_{name of client}">

            <button onclick="doSend()">Send</button>

            <button onclick="OnOpen()">Connect</button>
        </div>
    </body>
</html>
