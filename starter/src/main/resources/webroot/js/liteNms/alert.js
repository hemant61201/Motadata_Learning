var successAlert =
  {
    success : function (alertBox, alertIcon, alertText, alertName, alertData)
    {
      alertIcon.innerHTML = "<i class='fa fa-check' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#00cc44";

      alertBox.style.color = "#fff";

      switch (alertName)
      {
        case "login":
        {
          alertText.innerHTML = "Login Successful";

          setTimeout(async () =>
          {
            alertIcon.innerHTML = "";

            alertBox.style.backgroundColor = "black";

            alertBox.style.color = "black";

            window.location.href = "/api/index.html";
          }, 300);
        }
        break;

        case "addDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          if(alertData === 1)
          {
            alertText.innerHTML = "Device Added Successfully";
          }

          else
          {
            alertText.innerHTML = "Device Already Exist";
          }

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "deleteDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Device Deleted Successfully";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "discoveryStart":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = alertData;

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 4000);
        }
        break;

        case "discoveryResult":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Discovery Completed Successfully";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "updateDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Device Updated Successfully";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "provisionDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          if(alertData === 1)
          {
            alertText.innerHTML = "Device Added Successfully in MonitorTable";
          }

          else
          {
            alertText.innerHTML = "Device Already in MonitorTable";
          }

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "deleteMonitor":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Delete Monitor Device Successfully";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
          break;
      }
    }
  };

var failAlert =
  {
    fail: function (alertBox, alertIcon, alertText, alertName, alertData)
    {
      alertIcon.innerHTML = "<i class='fa fa-close' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#f11";

      alertBox.style.color = "#fff";

      switch (alertName)
      {
        case "login":
        {
          alertText.innerHTML = "Login Failed";

          setTimeout(async () =>
          {
            alertIcon.innerHTML = "";

            alertBox.style.backgroundColor = "black";

            alertBox.style.color = "black";
          }, 5000);
        }
        break;

        case "addDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Add Device Failed";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "deleteDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Device Deletion Failed";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "discoveryResult":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Discovery Failed";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "updateDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Device Not Updated Successfully";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "provisionDiscovery":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Provision Device Failed";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
        break;

        case "deleteMonitor":
        {
          $("#alertContainer").show();

          $('#navIcons').hide();

          alertText.innerHTML = "Delete Monitor Device Failed";

          setTimeout(async () =>
          {
            $("#alertContainer").hide();

            $('#navIcons').show();
          }, 2000);
        }
          break;
      }
    }
  }

