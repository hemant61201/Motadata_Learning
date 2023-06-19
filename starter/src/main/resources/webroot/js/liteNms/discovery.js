var addDiscovery =
  {
    onclick : function ()
    {
      if(validation.preCheckAddValue())
      {
        let myData = fetchData.fetchData();

        genricAjax.ajaxCall(myData);
      }
    }
  }

var fetchData =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      var alertData;

      if(ajaxResponse === 1)
      {
        alertData = "Device Added Successfully";

        successAlert.success(alertName, alertData);
      }

      else
      {
        alertData = "Device Already Present";

        failAlert.fail(alertName, alertData);
      }

      let dataTable = $('#discoveryTable').DataTable();

      dataTable.destroy();

      discovery.loadDiscovery();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Device Not Added Successfully";

      failAlert.fail(alertName, alertData);
    },

    fetchData: function ()
    {
      var myData;

      const deviceName = $("#name").val();

      const ip = $("#ip").val();

      const method = "POST";

      const url = "/api/addDiscovery"

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

          callbacks:
            {
              success: fetchData.ajaxSuccessMethod,

              fail: fetchData.ajaxFail,
            }
        };

      return myData;
    }
  }


var discoveryConfig =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
        const jsonArray = JSON.parse(ajaxResponse);

        const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

        dataTable.loadDataTable("#discoveryTable", resultArray);
    },

    getDiscoveryConfig: function ()
    {
      const method = "POST";

      const url = "/api/getDiscovery"

      const data = JSON.stringify({tableName: "discovery_table"})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
          {
            success : discoveryConfig.ajaxSuccessMethod,
          }
        }

      return config;
    }
  }







