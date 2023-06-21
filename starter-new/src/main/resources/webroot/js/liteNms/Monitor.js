var monitorConfig =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const jsonArray = JSON.parse(ajaxResponse);

      const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

      dataTable.loadDataTable("#monitorTable", resultArray)
    },

    getmonitorConfig : function ()
    {
      const method = "POST";

      const url = "/api/getMonitorTable"

      const data = JSON.stringify({tableName: "monitor_table"})

      let config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: monitorConfig.ajaxSuccessMethod,
            }
        }

      return config;
    }
  }

