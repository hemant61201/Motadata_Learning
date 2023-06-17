function myMonitorFunction()
{
  let config = getmonitorConfig();

  console.log("config: " + config);

  genricajax.get(
    config,
    function (ajaxResponse)
    {
      const jsonArray = JSON.parse(ajaxResponse);

      const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

      const monitorTable = resultArray.map(obj => Object.values(obj));

      console.log(monitorTable);

      var monitor_Table = $('#monitorTable').DataTable({

        data: resultArray,

        destroy: true,

        columns: [
          {title: 'id', data: 'ID'},
          {title: 'DeviceName', data: 'DEVICENAME'},
          {title: 'IP', data: 'IP'},
          {title: 'DeviceType', data: 'DEVICETYPE'},
          {title: 'Status', data: 'STATUS'},
          {
            title: 'Actions',
            data: null,
            render: function (data, type, row)
            {
              var viewButton = '<button class="view-button" onclick="viewBtn.onclick(this)">View</button>';

              var deleteButton = '<button class="delete-button" onclick="monitorTableBtn.onclick(this)">Delete</button>';

              return viewButton + ' ' + deleteButton;
            }
          }
        ]
      });
    }
    );
}

function getmonitorConfig()
{
  const method = "POST";

  const url = "/getMonitorTable"

  const data = JSON.stringify({tableName: "monitor_table"})

  let config =
    {
      method: method,

      url: url,

      data: data,
    }

  return config;
}
