var tableButton =
  {
    onclick(button)
    {
      var dataTable = $(button).closest('table').DataTable();

      var row = dataTable.row($(button).closest('tr'));

      var data = row.data();

      var id = data.ID;

      let btnConf = btnConfig(id);

      ajax.post(btnConf);
    },

    openDialog()
    {
      var dialog = document.getElementById('add-dialog');

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

  function btnConfig(id_no)
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
