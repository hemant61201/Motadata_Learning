function myFunction()
{
  let config = getdiscoveryConfig();

  genricajax.get(
    config,
    function (ajaxResponse)
    {
      const jsonArray = JSON.parse(ajaxResponse);

      const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

      const discoveryTable = resultArray.map(obj => Object.values(obj));

      console.log(discoveryTable);

      var discovery_Table = $('#discoveryTable').DataTable({

        data: resultArray,

        destroy: true,

        columns: [
          { title: 'id', data: 'ID' },
          { title: 'DeviceName', data: 'DEVICENAME' },
          { title: 'IP', data: 'IP' },
          { title: 'DeviceType', data: 'DEVICETYPE' },
          { title: 'Discovery_Status', data: 'STATUS' },
          {
            title: 'Actions',

            data: null,

            render: function (data, type, row)
            {
              var editButton = '<button class="edit-button" onclick="tableButton.openDialog(this)">Edit</button>';

              var runButton = '<button class="run-button" onclick="runButton.onclick(this)">Run</button>';

              var deleteButton = '<button class="delete-button" onclick="tableButton.onclick(this)">Delete</button>';

              if (data.STATUS === "success")
              {
                var provisionButton = '<button class="provision-button" onclick="provisonButton.onclick(this)">Provision</button>';

                return editButton + ' ' + runButton + ' ' + deleteButton + ' ' + provisionButton;
              }

              return editButton + ' ' + runButton + ' ' + deleteButton;
            }
          }
        ]
      });
    }
  );
}

var discovery =
  {
    onclick()
    {
      let myData = fetchData();

      genricajax.post(
        myData,
        function (ajaxResponse)
        {
          if (ajaxResponse !== null)
          {
            let dataTable = $('#discoveryTable').DataTable();

            dataTable.destroy();

            myFunction();
          }
        }
      );
    }
  }

function fetchData()
{
  var myData;

  const deviceName = document.getElementById("name").value;

  const ip = document.getElementById("ip").value;

  const method = "POST";

  const url = "/addDiscovery"

  const deviceType = document.getElementById("type").value;

  const credential_userName = document.getElementById("credential_username").value;

  const credential_password = document.getElementById("credential_passwd").value;

  var credential = {

    credential_userName: credential_userName,

    credential_password: credential_password
  };

  var data = JSON.stringify({
    DEVICENAME: deviceName,
    DEVICETYPE: deviceType,
    IP: ip,
    CREDENTIAL: credential
  })

  myData =
    {
      method: method,

      url: url,

      data: data
    };

  return myData;
}

function getdiscoveryConfig()
{
  const method = "GET";

  const url = "/getDiscoveryTable"

  var config =
    {
      method: method,

      url: url
    }

    return config;
}






