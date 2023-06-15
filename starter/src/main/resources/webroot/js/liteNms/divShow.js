function showDiv(divId)
{
  var div = document.getElementById(divId);

  // Hide all other divs
  var allDivs = document.querySelectorAll('.content-wrapper');

  allDivs.forEach(function(div)
  {
    div.classList.add('hidden');
  });

  // Show the selected div
  div.classList.remove('hidden');

  localStorage.setItem('selectedDiv', divId);

  if (divId === 'monitorDiv')
  {
    $(div).ready(function()
    {
      let config = getmonitorConfig();

      genricajax.get(
        config,
        function (ajaxResponse)
        {
          const jsonArray = JSON.parse(ajaxResponse);

          const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

          const monitorTable = resultArray.map(obj => Object.values(obj));

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
                render: function (data, type, row) {

                  var viewButton = '<button class="view-button" onclick="viewBtn.onclick(this)">View</button>';

                  var deleteButton = '<button class="delete-button" onclick="monitorTableBtn.onclick(this)">Delete</button>';

                  return viewButton + ' ' + deleteButton;
                }
              }
            ]
          });
        }
      );
    });
  }

  else if (divId === 'discoveyDiv')
  {
    $(div).ready(function()
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
    });
  }
}

window.addEventListener('load', function()
{
  var selectedDivId = localStorage.getItem('selectedDiv');

  if (selectedDivId)
  {
    showDiv(selectedDivId);
  }
  else
  {
    showDiv('dashboardDiv');
  }
});
