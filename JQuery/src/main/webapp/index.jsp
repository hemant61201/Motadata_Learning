<html>
    <head>
        <title>Person_Info</title>
        <link
                rel="stylesheet"
                type="text/css"
                href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"
        />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="ActionCall.js"></script>
        <script
                type="text/javascript"
                charset="utf8"
                src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"
        ></script>
        <script
                type="text/javascript"
                charset="utf8"
                src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"
        ></script>
    </head>
    <body>
        <h1 align="center">Welcome To Person Details Page</h1>
        <div style="align-content: start">
            <label>First Name :</label>
            <input id="First_Name"  type="text" required/>
            <br>
            <br>
            <label>Last Name :</label>
            <input id="Last_Name" type="text" required/>
            <br>
            <br>
            <label>Age :</label>
            <input id="Person_Age" type="number" required/>
            <br>
            <br>
            <button id="btn1">Submit</button>
            <br>
            <br>
            <button id="btn2">Get Details</button>
            <br>
            <br>
            <p id="output"></p>
            <br>
            <table class="table_id" style="width: 100%;height: 100px;">
                <thead>
                <tr>
                    <th>First_Name</th>
                    <th>Last_Name</th>
                    <th>Age</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </body>
</html>
