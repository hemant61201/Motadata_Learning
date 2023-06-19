var successAlert =
  {
    success : function (alertName, alertData)
    {
      var alertBox = $("#alert").get(0);

      var alertText = $("#text").get(0);

      var alertIcon = $("#icon").get(0);

      alertIcon.innerHTML = "<i class='fa fa-check' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#00cc44";

      alertBox.style.color = "#fff";

      alertText.innerHTML = alertData;

      if(alertName === "login")
      {
        setTimeout(async () =>
        {
          alertIcon.innerHTML = "";

          alertBox.style.backgroundColor = "black";

          alertBox.style.color = "black";

          window.location.href = "/api/index.html";
        }, 300);
      }

      else
      {
        $("#alertContainer").show();

        $('#navIcons').hide();

        setTimeout(async () =>
        {
          $("#alertContainer").hide();

          $('#navIcons').show();
        }, 2000);
      }
    }
  };

var failAlert =
  {
    fail: function (alertName, alertData)
    {
      var alertBox = $("#alert").get(0);

      var alertText = $("#text").get(0);

      var alertIcon = $("#icon").get(0);

      alertIcon.innerHTML = "<i class='fa fa-close' style='font-size:18px;color:#fff'></i>";

      alertBox.style.backgroundColor = "#f11";

      alertBox.style.color = "#fff";

      alertText.innerHTML = alertData;

      if(alertName === "login")
      {
        setTimeout(async () =>
        {
          alertIcon.innerHTML = "";

          alertBox.style.backgroundColor = "black";

          alertBox.style.color = "black";
        }, 5000);
      }

      else
      {
        $("#alertContainer").show();

        $('#navIcons').hide();

        setTimeout(async () =>
        {
          $("#alertContainer").hide();

          $('#navIcons').show();
        }, 2000);
      }
    }
  }

