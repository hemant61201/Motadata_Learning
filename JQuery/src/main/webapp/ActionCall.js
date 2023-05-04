$(document).ready(function()
{
    var url;

    var type;

    var fname;

    var lname;

    var age;

    var callbackMethod;

    var count = 0;

    var callbacks = $.Callbacks();

    var dataTable;

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

        callbackMethod = insertTable;

        type = "POST";

        callAJAX();

    });

    $("#btn2").click(function()
    {
        url = "fetchDB";

        type = "GET";

        callbackMethod = getData;

        callAJAX();
    });


    function insertTable( value )
    {
        $("#output").text(value.insertresult);

        callbacks.remove(callbackMethod);

        if (count > 0)
        {
            url = "fetchDB";

            type = "GET";

            callbackMethod = getData;

            callbacks.remove(callbackMethod);

            callAJAX();
        }
    }

    function getData( value )
    {
        var dataArray = value.result.map(function(arr) {
            return arr.slice();
        });

        dataTable = $(".table_id").DataTable({
            aaData: dataArray,
            "bDestroy": true,
        });

        callbacks.remove(callbackMethod);

        count++;
    }

    function errorDetection( type )
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

    function callAJAX()
    {
        $.ajax({
            url: url,
            type: type,
            data: {
                firstname: fname,
                lastname: lname,
                age: age
            },
            success: function(data, status) {

                callbacks.add( callbackMethod );

                callbacks.fire( data );

            },
            error: function()
            {
                errorDetection(type);
            }
        });
    }
});