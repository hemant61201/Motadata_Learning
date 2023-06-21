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
        const alertName = "";

        successAlert.success(alertName, "DiscoveryStarted Successfully");

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
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Discovery Successfully Completed";

      successAlert.success(alertName, alertData);

      let dataTable = $('#discoveryTable').DataTable();

      dataTable.destroy();

      discovery.loadDiscovery();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Discovery Failed";

      failAlert.fail(alertName, alertData);
    },

    runConfig: function (id)
    {
      const method = "POST";

      const url = "/api/runDiscovery";

      const data = JSON.stringify({tableName: "discovery_table", id: id});

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: runConfig.ajaxSuccessMethod,

              fail: runConfig.ajaxFail,
            }
        }

      return config;
    }
};

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
      if(validation.preCheckUpdateValue())
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
      }
      },
  };

var updateConfig =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      var alertData = "Device Updated Successfully"

      successAlert.success(alertName, alertData);

      let dataTable = $('#discoveryTable').DataTable();

      dataTable.destroy();

      discovery.loadDiscovery();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Device Not Updated Successfully";

      failAlert.fail(alertName, alertData);
    },

    updateConfig : function (updateValue)
    {
      const method = "POST";

      const url = "/api/updateDiscovery";

      const data = JSON.stringify({tableName: "discovery_table", id: updateValue})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: updateConfig.ajaxSuccessMethod,

              fail: updateConfig.ajaxFail,
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
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      var alertData;

      if(ajaxResponse === "1")
      {
        alertData = "Device Added Successfully in MonitorTable";

        successAlert.success(alertName, alertData);
      }

      else
      {
        alertData = "Device Already Present in MonitorTable";

        failAlert.fail(alertName, alertData);
      }

      let dataTable = $('#monitorTable').DataTable();

      dataTable.destroy();

      monitor.loadMonitor();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Provision Failed";

      failAlert.fail(alertName, alertData);
    },

    provisonConfig: function (tableValue)
    {
      var tableData = tableValue.data();

      const method = "POST"

      const url = "/api/addMonitorTable"

      const data = JSON.stringify({tableName:"discovery_table", id: tableData.ID});

      var provConf =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: provisonConfig.ajaxSuccessMethod,

              fail: provisonConfig.ajaxFail,
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

      if (confirm("Are you sure you want to delete?"))
      {
        let btnConf = deleteMonitor.deleteMonitorConfig(id);

        genricAjax.ajaxCall(btnConf);
      }
    }
  };

var deleteMonitor =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      var alertData = "Device Deleted Successfully"

      successAlert.success(alertName, alertData);

      let dataTable = $('#monitorTable').DataTable();

      dataTable.destroy();

      monitor.loadMonitor();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Device Not Deleted";

      failAlert.fail(alertName, alertData);
    },

    deleteMonitorConfig: function (id)
    {
      const method = "POST";

      const url = "/api/deleteMonitorTable";

      const data = JSON.stringify({tableName: "monitor_table", id: id})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: deleteMonitor.ajaxSuccessMethod,

              fail: deleteMonitor.ajaxFail,
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

      if (confirm("Are you sure you want to delete?"))
      {
        let btnConf = deleteConfig.deleteConfig(id);

        genricAjax.ajaxCall(btnConf);
      }
    },
  }

var deleteConfig =
  {
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "";

      var alertData = "Device Deleted Successfully"

      successAlert.success(alertName, alertData);

      let dataTable = $('#discoveryTable').DataTable();

      dataTable.destroy();

      discovery.loadDiscovery();
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "";

      const alertData = "Device Not Deleted";

      failAlert.fail(alertName, alertData);
    },

    deleteConfig : function (id)
    {
      const method = "POST";

      const url = "/api/deleteDiscoveryTable";

      const data = JSON.stringify({tableName: "discovery_table", id: id})

      var config =
        {
          method: method,

          url: url,

          data: data,

          callbacks:
            {
              success: deleteConfig.ajaxSuccessMethod,

              fail: deleteConfig.ajaxFail,
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

      var deviceType = data.DEVICETYPE;

      let btnConf = viewMonitorConfig.viewMonitorConfig(ip, deviceType);

      genricAjax.ajaxCall(btnConf);
    }
  };

var viewMonitorConfig =
  {
    viewMonitorConfig: function (ip, deviceType)
    {
      const method = "POST"

      const url = "/api/viewMonitor"

      const data = JSON.stringify({tableName: "polling_table", ip: ip, deviceType: deviceType})

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
                if(deviceType === "Ping")
                {
                  pingMonitor.pingMonitor();

                  monitorView.viewMonitor(ajaxResponse);
                }

                else
                {
                  sshMonitor.sshMonitor();

                  SSHMonitor.sshMonitor(ajaxResponse);
                }

              }
            }
          }
        };

      return viewConf;
    }
  }


