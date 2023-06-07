var ajax = {

  post: function (data) {
    if (data.url === "/login") {
      console.log(data.formData.toString());

      console.log(data.contentType);

      var alertBox = document.getElementById("hemant-alert");

      var alertText = document.getElementById("hemant-text");

      var alertIcon = document.getElementById("hemant-icon");

      $.ajax({
        method: data.method,

        url: data.url,

        data: data.formData.toString(),

        contentType: data.contentType,

        success: function (ajaxResponse) {

          console.log(ajaxResponse)

          console.log("on success if")

          alertText.innerHTML = "Login Success";

          alertIcon.innerHTML = " <i class='fa fa-check' style='font-size:18px;color:#fff'></i>";

          alertBox.style.backgroundColor = "#00cc44";

          alertBox.style.color = "#fff";

          setTimeout(async () => {
            console.log('change color');

            alertIcon.innerHTML = "";

            alertBox.style.backgroundColor = "black";

            alertBox.style.color = "black";

            window.location.href = "/api/index.html"; // Redirect to dashboard
          }, 300);
        },

        error: function (ajaxResponse) {
          console.log(ajaxResponse)

          console.log("on error else")

          alertText.innerHTML = "Login Failed";

          alertIcon.innerHTML = " <i class='fa fa-close' style='font-size:18px;color:#fff'></i>";

          alertBox.style.backgroundColor = "#f11";

          alertBox.style.color = "#fff";

          setTimeout(async () => {
            console.log('change color');

            alertIcon.innerHTML = "";

            alertBox.style.backgroundColor = "black";

            alertBox.style.color = "black";

          }, 5000);
        }
      })
    }
    else if (data.url === "/addDiscovery")
    {
      $.ajax({

        method: data.method,

        url: data.url,

        data: JSON.stringify({
          deviceName: data.deviceName,
          deviceType: data.deviceType,
          ip: data.ip,
          credential: data.credential
        }),

        success: function (ajaxResponse)
        {
          console.log(ajaxResponse)

          if (ajaxResponse !== null)
          {
            location.reload();
          }
        }
      })
    }

    else if (data.url === "/deleteDiscoveryTable")
    {
      console.log("delete id " + data.id)

      $.ajax({

        method: data.method,

        url: data.url,

        data: data.id,

        success: function (ajaxResponse)
        {
          console.log(ajaxResponse)

          if (ajaxResponse !== null)
          {
            location.reload();
          }
        }
      })
    }
  },

  get: function (data)
  {
    if (data.url === "/getDiscoveryTable")
    {
      $.ajax({

        method: data.method,

        url: data.url,

        success: function (ajaxResponse)
        {
          const jsonArray = JSON.parse(ajaxResponse);

          const resultArray = jsonArray.map(jsonString => JSON.parse(jsonString));

          const discoveryTable = resultArray.map(obj => Object.values(obj));

          console.log(discoveryTable);

          var discovery_Table = $('#discoveryTable').DataTable({
            data: resultArray,
            columns: [
              { title: 'id', data: 'ID' },
              { title: 'DeviceName', data: 'DEVICENAME' },
              { title: 'IP', data: 'IP' },
              { title: 'DeviceType', data: 'DEVICETYPE' },
              { title: 'Status', data: 'STATUS' },
              {
                title: 'Actions',
                data: null,
                render: function (data, type, row) {

                  var editButton = '<button class="edit-button" id="editBtn" onclick="tableButton.openDialog()">Edit</button>';
                  var runButton = '<button class="run-button" onclick="tableButton.startIconRotation()">Run</button>';
                  var deleteButton = '<button id="deleteBtn" class="delete-button" onclick="tableButton.onclick(this)">Delete</button>';

                  return editButton + ' ' + runButton + ' ' + deleteButton;
                }
              }
            ]
          });
        }
      });
    }
  }
}
