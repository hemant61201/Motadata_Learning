var ajax = {

    post: function (data)
    {
      if (data.url === "/login")
      {
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

          error: function (ajaxResponse)
          {
            console.log(ajaxResponse)

            console.log("on error else")

            alertText.innerHTML = "Login Failed";

            alertIcon.innerHTML = " <i class='fa fa-close' style='font-size:18px;color:#fff'></i>";

            alertBox.style.backgroundColor = "#f11";

            alertBox.style.color = "#fff";

            setTimeout(async () =>
            {
              console.log('change color');

              alertIcon.innerHTML = "";

              alertBox.style.backgroundColor = "black";

              alertBox.style.color = "black";

            }, 5000);
          }
        })
      }

      else if(data.url === "/addDiscovery")
      {
        $.ajax({

          method: data.method,

          url: data.url,

          data: JSON.stringify({deviceName: data.deviceName, deviceType: data.deviceType, ip: data.ip, credentialName: data.credentialName, credentialPassword: data.credentialPassword}),

          success: function (ajaxResponse){
            console.log(ajaxResponse)
          }

        })
      }
    },

    get : function (){}
}
