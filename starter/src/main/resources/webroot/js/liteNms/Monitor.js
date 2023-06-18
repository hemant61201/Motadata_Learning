var monitorConfig =
  {
    getmonitorConfig : function ()
    {
      const method = "POST";

      const url = "/getMonitorTable"

      const data = JSON.stringify({tableName: "monitor_table"})

      let config =
        {
          method: method,

          url: url,

          data: data,

          callbacks: {

            success: function (ajaxResponse)
            {
              const jsonArray = JSON.parse(ajaxResponse);

              const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

              dataTable.loadDataTable("#monitorTable", resultArray)
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

