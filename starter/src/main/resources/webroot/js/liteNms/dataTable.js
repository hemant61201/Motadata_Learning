var dataTable =
  {
    loadDataTable: function (id, resultArray)
    {
      $(id).DataTable({

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
              return tableButton.showBtn(id, data);
            }
          }
        ]
      });

      dialogBox.bindDialogBox();
    },
  }
