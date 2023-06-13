var tableButton =
  {
    onclick(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteConfig(id);

      genricajax.post(
        btnConf,
        function (ajaxResponse)
        {
          if (ajaxResponse !== null)
          {
            // myFunction();
            let dataTable = $('#discoveryTable').DataTable();

            dataTable.destroy();

            myFunction();
          }
        }
        );
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
  };

var runButton =
    {
      onclick(button)
      {
        var dataTable = $(button).closest('table').DataTable();

        var row = dataTable.row($(button).closest('tr'));

        var data = row.data();

        var id = data.ID;

        let btnConf = runConfig(id);

        genricajax.post(
          btnConf,
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
      },
    };

var updateBtn =
  {
    id: "",

    onclick()
    {
      const updateDialog = document.getElementById("update-dialog");

      updateDialog.close();

      const field = document.getElementById("deviceSelect").value;

      var updatefeild;

      switch (field)
      {
        case "DeviceName":
        {
          updatefeild = updateBtn.id + "_" + field + "_" +document.getElementById("update_deviceName").value;

          break;
        }

        case "IP":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_ip").value;

          break;
        }

        case "DeviceType":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_deviceType").value;

          break;
        }

        case "credential":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + document.getElementById("update_username").value + "." + document.getElementById("update_password").value;

          break;
        }
      }

      let updateval = updateConfig(updatefeild);

      genricajax.post(
            updateval,
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
      },
  };

var provisonButton =
  {
    onclick(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      let provisonBtnConf = provisonConfig(row);

      genricajax.post(
        provisonBtnConf,
        function (ajaxResponse)
        {
          if(ajaxResponse != null)
          {
            let dataTable = $('#monitorTable').DataTable();

            dataTable.destroy();

            myMonitorFunction();
          }
        }
        );
    }
  };

var monitorTableBtn =
  {
    onclick(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteMonitorConfig(id);

      genricajax.post(
        btnConf,
        function (ajaxResponse)
        {
          if (ajaxResponse !== null)
          {
            let dataTable = $('#monitorTable').DataTable();

            dataTable.destroy();

            myMonitorFunction();
          }        }
        );
    }
  };

var viewBtn =
  {
    onclick(button)
      {
        var dataTable = $(button).closest('table').DataTable();

        var row = dataTable.row($(button).closest('tr'));

        var data = row.data();

        var ip = data.IP;

        let btnConf = viewMonitorConfig(ip);

        genricajax.post(
          btnConf,
          function (ajaxResponse)
          {
            if (ajaxResponse !== null)
            {
              viewMonitor(ajaxResponse);

              showDiv('viewMonitorDiv');
            }
          }
          );
      }
    };

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
  };

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
};

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
  };

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
  };

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
  };

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
  };
