var tableButton =
  {
    onclick(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteConfig(id);

      ajax.post(btnConf);
    },

    openDialog(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      updateBtn.id = id;

      var dialog = document.getElementById('update-dialog');

      dialog.showModal();
    },

    startIconRotation() {
      var icon = document.getElementById('icon');
      icon.classList.add('rotating-icon');

      setTimeout(function() {
        tableButton.stopIconRotation();
      }, 2000);
    },

    stopIconRotation() {
      var icon = document.getElementById('icon');
      icon.classList.remove('rotating-icon');
    }

  }

  var runButton =
    {
      onclick(button)
      {
        var dataTable = $(button).closest('table').DataTable();

        var row = dataTable.row($(button).closest('tr'));

        var data = row.data();

        var id = data.ID;

        let btnConf = runConfig(id);

        ajax.post(btnConf);
      },
    }

    var updateBtn =
      {
        id: "",

        onclick()
        {
          const updateDialog = document.getElementById("update-dialog");

          updateDialog.close();

          const field = document.getElementById("deviceSelect").value;

          var updatefeild;

          console.log("feild : " + field);

          switch (field)
          {
            case "DeviceName":
            {
              updatefeild = updateBtn.id + "_" + field + "_" +document.getElementById("update_deviceName").value;

              console.log("feildValue : " + updatefeild);

              break;
            }

            case "IP":
            {
              updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_ip").value;

              console.log("feildValue : " + updatefeild);

              break;
            }

            case "DeviceType":
            {
              updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_deviceType").value;

              console.log("feildValue : " + updatefeild);

              break;
            }

            case "credential":
            {
              updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_username").value + "." + document.getElementById("update_password").value;

              console.log("feildValue : " + updatefeild);

              break;
            }
          }

          let updateval = updateConfig(updatefeild);

          ajax.post(updateval);
        },
      }

      var provisonButton =
        {
          onclick(button)
          {
            var dataTable = $(button).closest('table').DataTable();

            var row = dataTable.row($(button).closest('tr'));

            let provisonBtnConf = provisonConfig(row);

            ajax.post(provisonBtnConf);
          }
        }

var monitorTableBtn =
  {
    onclick(button) {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteMonitorConfig(id);

      ajax.post(btnConf);
    }
  }

  var viewBtn =
    {
      onclick(button)
      {
        var dataTable = $(button).closest('table').DataTable();

        var row = dataTable.row($(button).closest('tr'));

        var data = row.data();

        var ip = data.IP;

        let btnConf = viewMonitorConfig(ip);

        ajax.post(btnConf);
      }
    }

  function deleteConfig(id_no)
  {
    const method = "POST";

    const url = "/deleteDiscoveryTable";

    const id = {
      id: id_no
    }

    var config =
      {
        method: method,

        url: url,

        id: id,
      }

    return config;
  }

function deleteMonitorConfig(id_no)
{
  const method = "POST";

  const url = "/deleteMonitorTable";

  const id = {
    id: id_no
  }

  var config =
    {
      method: method,

      url: url,

      id: id,
    }

  return config;
}

  function runConfig(id_no)
  {
    const method = "POST";

    const url = "/runDiscovery";

    const id = {
      id: id_no
    }

    var config =
      {
        method: method,

        url: url,

        id: id,
      }

    return config;
  }

  function updateConfig(updateValue)
  {
    const method = "POST";

    const url = "/updateDiscovery";

    const id = {
      id: updateValue
    }

    var config =
      {
        method: method,

        url: url,

        id: id,
      }

    return config;
  }

  function viewMonitorConfig(ip_no)
  {
    const method = "POST"

    const url = "/viewMonitor"

    const ip = {
      ip: ip_no,
    }

    var viewConf =
      {
        method: method,

        url: url,

        ip: ip,
      };

    return viewConf;
  }

  function provisonConfig(tableValue)
  {
    var data = tableValue.data();

    const method = "POST"

    const url = "/addMonitorTable"

    const id = {
      id: data.ID,
    }

    var provConf =
      {
        method: method,

        url: url,

        id: id,
      };

    return provConf;
  }
