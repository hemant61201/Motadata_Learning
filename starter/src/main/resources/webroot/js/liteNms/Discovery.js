var addDiscovery =
  {
    onclick : function ()
    {
      let myData = fetchData.fetchData();

      genricAjax.ajaxCall(myData);
    }
  }

var fetchData =
  {
    fetchData: function ()
    {
      var alertBox = $("#alert").get(0);

      var alertText = $("#text").get(0);

      var alertIcon = $("#icon").get(0);

      var myData;

      const deviceName = $("#name").val();

      const ip = $("#ip").val();

      const alertName = "addDiscovery";

      const method = "POST";

      const url = "/addDiscovery"

      const deviceType = $("#type").val();

      var credential_userName = $("#credential_username").val();

      var credential_password = $("#credential_passwd").val();

      var credential = {

        credential_userName: credential_userName,

        credential_password: credential_password
      };

      var data = JSON.stringify({
        deviceName: deviceName,
        ip: ip,
        deviceType: deviceType,
        status: "unknown",
        credential: credential,
        tableName: "discovery_table"
      })

      myData =
        {
          method: method,

          url: url,

          data: data,

          callbacks: {

            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
                successAlert.success(alertBox, alertIcon, alertText, alertName, ajaxResponse);

                let dataTable = $('#discoveryTable').DataTable();

                dataTable.destroy();

                discovery.loadDiscovery();
              }
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse)
            }
          }
        };

      return myData;
    }
  }


var discoveryConfig =
  {
    getDiscoveryConfig: function ()
    {
      const method = "POST";

      const url = "/getDiscovery"

      const data = JSON.stringify({tableName: "discovery_table"})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks: {

            success: function (ajaxResponse)
            {
              const jsonArray = JSON.parse(ajaxResponse);

              const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

              dataTable.loadDataTable("#discoveryTable", resultArray)
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse)
            }

          }
        }

      return config;
    }
  }







