var successAlert =
  {
    success : function (alertBox, alertIcon, alertText, alertName, alertData)
    {
      $("#alertDiv").removeClass('hidden');

      alertIcon.innerHTML = " <i class='fa fa-check' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#00cc44";

      alertBox.style.color = "#fff";

      switch (alertName)
      {
        case "login":
        {
          alertText.innerHTML = "Login Success";

          setTimeout(async () =>
          {
            alertIcon.innerHTML = "";

            alertBox.style.backgroundColor = "black";

            alertBox.style.color = "black";

            window.location.href = "/api/index.html";
          }, 300);
        }

        case "addDiscovery":
        {
          if(alertData === 1)
          {
            alertText.innerHTML = "Device Added Successfully";
          }

          else
          {
            alertText.innerHTML = "Device Already Exist";
          }
        }
      }
    }
  };

var failAlert =
  {
    fail: function (alertBox, alertIcon, alertText, alertName, alertData)
    {
      alertText.innerHTML = "Login Failed";

      alertIcon.innerHTML = " <i class='fa fa-close' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#f11";

      alertBox.style.color = "#fff";

      setTimeout(async () =>
      {
        alertIcon.innerHTML = "";

        alertBox.style.backgroundColor = "black";

        alertBox.style.color = "black";
      }, 5000);
    }
  }

