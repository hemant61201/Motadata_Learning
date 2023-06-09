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
