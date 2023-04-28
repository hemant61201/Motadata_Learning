<html>
    <head>
        <title>Person_Info</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $("#btn1").click(function() {
                    var fname = $("#First_Name").val();
                    var lname = $("#Last_Name").val();
                    var age = $("#Person_Age").val();
                    $.ajax({
                        url: "myStrutsAction",
                        type: "POST",
                        data: {
                            First_name: fname,
                            Last_name: lname,
                            age: age
                        },
                        success: function() {
                            $("#output").text("Successfully Inserted")
                        },
                        error: function() {
                            $("#output").text("Data Not Inserted")
                        }
                    })
                });
                $("#btn2").click(function(){
                    $.ajax({
                        url: "myStrutsAction1",
                        type: "GET",
                        success: function(data, status) {

                            $("#output").text("");
                            for (var key in data.result) {
                                $("#output").append(key + " = " + data.result[key] + "<br>");
                            }
                        },
                        error: function(xhr, status, error) {
                            $("#output").text("Data Not Fetched")
                        }
                    })
                });
            });
        </script>
    </head>
    <body>
        <h1 align="center">Welcome To Person Details Page</h1>
        <div style="align-content: start">
            <label>First Name :</label>
            <input id="First_Name"  type="text"/>
            <br>
            <br>
            <label>Last Name :</label>
            <input id="Last_Name" type="text"/>
            <br>
            <br>
            <label>Age :</label>
            <input id="Person_Age" type="number"/>
            <br>
            <br>
            <button id="btn1">Submit</button>
            <br>
            <br>
            <button id="btn2">Get Details</button>
            <br>
            <br>
            <p id="output"></p>
        </div>
    </body>
</html>
