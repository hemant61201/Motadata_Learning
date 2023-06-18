var tableButton =
  {
    showBtn: function (id, data)
    {
      switch (id)
      {
        case "#discoveryTable":
        {
          var editButton = '<button class="edit-button" onclick="updateBtn.openDialog(this)">Edit</button>';

          var runButton = '<button class="run-button" onclick="runButton.onclick(this)">Run</button>';

          var deleteButton = '<button class="delete-button" onclick="deleteBtn.onclick(this)">Delete</button>';

          if (data.STATUS === "success")
          {
            var provisionButton = '<button class="provision-button" onclick="provisonButton.onclick(this)">Provision</button>';

            return editButton + ' ' + runButton + ' ' + deleteButton + ' ' + provisionButton;
          }

          return editButton + ' ' + runButton + ' ' + deleteButton;
        }

        case "#monitorTable":
        {
          var viewButton = '<button class="view-button" onclick="viewBtn.onclick(this)">View</button>';

          var deleteButton = '<button class="delete-button" onclick="monitorTableBtn.onclick(this)">Delete</button>';

          return viewButton + ' ' + deleteButton;
        }
      }
    }
  };

var runButton =
    {
      onclick : function (button)
      {
        var dataTable = $(button).closest('table').DataTable();

        var row = dataTable.row($(button).closest('tr'));

        var data = row.data();

        var id = data.ID;

        let btnConf = runConfig.runConfig(id);

        genricAjax.ajaxCall(btnConf);
      },
    };

var runConfig =
  {
    runConfig: function (id)
    {
      const method = "POST";

      const url = "/runDiscovery";

      const data = JSON.stringify({tableName: "discovery_table", id: id});

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:{

            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
                let dataTable = $('#discoveryTable').DataTable();

                dataTable.destroy();

                discovery.loadDiscovery();
              }
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse);
            }
          }
        }

      return config;
    }
}

var updateBtn =
  {
    id: "",

    openDialog: function(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      updateBtn.id = id;

      var dialog = $('#update-dialog');

      dialog.get(0).showModal();
    },

    onclick: function ()
    {
      const field = $("#deviceSelect").val();

      var updatefeild;

      switch (field)
      {
        case "DeviceName":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + $("#update_deviceName").val();

          break;
        }

        case "IP":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + $("#update_ip").val();

          break;
        }

        case "DeviceType":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + $("#update_deviceType").val();

          break;
        }

        case "credential":
        {
          updatefeild = updateBtn.id + "_" + field + "_" + $("#update_username").val() + "." + $("#update_password").val();

          break;
        }
      }

      let updatevalue = updateConfig.updateConfig(updatefeild);

      genricAjax.ajaxCall(updatevalue);
      },
  };

var updateConfig =
  {
    updateConfig : function (updateValue)
    {
      const method = "POST";

      const url = "/updateDiscovery";

      const data = JSON.stringify({tableName: "discovery_table", id: updateValue})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:{
            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
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

        }

      return config;
    }
  };

var provisonButton =
  {
    onclick: function (button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      let provisonBtnConf = provisonConfig.provisonConfig(row);

      genricAjax.ajaxCall(provisonBtnConf);
    }
  };

var provisonConfig =
  {
    provisonConfig: function (tableValue)
    {
      var tableData = tableValue.data();

      const method = "POST"

      const url = "/addMonitorTable"

      const data = JSON.stringify({tableName:"discovery_table", id: tableData.ID});

      var provConf =
        {
          method: method,

          url: url,

          data: data,

          callbacks:{

            success: function (ajaxResponse)
            {
              if(ajaxResponse != null)
              {
                let dataTable = $('#monitorTable').DataTable();

                dataTable.destroy();

                monitor.loadMonitor();
              }
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse);
            }
          }
        };

      return provConf;
    }
  }

var monitorTableBtn =
  {
    onclick: function (button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteMonitor.deleteMonitorConfig(id);

      genricAjax.ajaxCall(btnConf);
    }
  };

var deleteMonitor =
  {
    deleteMonitorConfig: function (id)
    {
      const method = "POST";

      const url = "/deleteMonitorTable";

      const data = JSON.stringify({tableName: "monitor_table", id: id})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:{

            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
                let dataTable = $('#monitorTable').DataTable();

                dataTable.destroy();

                monitor.loadMonitor();
              }
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse);
            }
          }
        }

      return config;
    }
  }


var deleteBtn =
  {
    onclick: function(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = deleteConfig.deleteConfig(id);

      genricAjax.ajaxCall(btnConf);
    },
  }

var deleteConfig =
  {
    deleteConfig : function (id)
    {
      const method = "POST";

      const url = "/deleteDiscoveryTable";

      const data = JSON.stringify({tableName: "discovery_table", id: id})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks: {

            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
                let dataTable = $('#discoveryTable').DataTable();

                dataTable.destroy();

                discovery.loadDiscovery();
              }
            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse);
            }
          }
        }

      return config;
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

      let btnConf = viewMonitorConfig.viewMonitorConfig(ip);

      genricAjax.ajaxCall(btnConf);
    }
  };

var viewMonitorConfig =
  {
    viewMonitorConfig: function (ip)
    {
      const method = "POST"

      const url = "/viewMonitor"

      const data = JSON.stringify({tableName: "polling_table", ip: ip})

      var viewConf =
        {
          method: method,

          url: url,

          data: data,

          callbacks: {

            success: function (ajaxResponse)
            {
              if (ajaxResponse !== null)
              {
                viewMonitor.viewMonitor();

                monitorView.viewMonitor(ajaxResponse);
              }
            }
          }
        };

      return viewConf;
    }
  }


