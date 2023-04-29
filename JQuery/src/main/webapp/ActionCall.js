$(document).ready(function()
{
    var url;

    var type;

    var fname;

    var lname;

    var age;

    $("#btn1").click(function()
    {
        console.log("btn1 called");

        fname = $("#First_Name").val();

        lname = $("#Last_Name").val();

        age = $("#Person_Age").val();

        if ((fname == null || fname === "") || (lname == null || lname === "") || (age == null || age === "") )
        {
            alert("Please enter a value.");

            return false;
        }

        url = "storeDB";

        type = "POST";

        callAJAX();

    });

    $("#btn2").click(function()
    {
        url = "fetchDB";

        type = "GET";

        callAJAX();
    });

    function callAJAX()
    {
        $.ajax({
            url: url,
            type: type,
            data: {
                First_name: fname,
                Last_name: lname,
                age: age
            },
            success: function(data, status) {

                if(type === "POST")
                {
                    if(data.insert_result == null || data.insert_result === "")
                    {
                        $("#output").text("Data Not Inserted");
                    }

                    else
                    {
                        $("#output").text(data.insert_result);
                    }
                }

                else if(type === "GET")
                {
                    $("#output").text("");

                    for (var key in data.result)
                    {
                        $("#output").append(key + " = " + data.result[key] + "<br>");
                    }
                }

            },
            error: function()
            {
                if(type === "POST")
                {
                    $("#output").text("Data Not Inserted")
                }

                else if(type === "GET")
                {
                    $("#output").text("Data Not Fetched")
                }
            }
        });
    }
});