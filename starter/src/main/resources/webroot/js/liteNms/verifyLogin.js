var login =
{
  onclick()
  {
    let myData = fetchData();

    var alertBox = document.getElementById("hemant-alert");

    var alertText = document.getElementById("hemant-text");

    var alertIcon = document.getElementById("hemant-icon");

    console.log("login call gone"),

    genricajax.post(
      myData,
      function (ajaxResponse)
      {
        console.log("login successful");

        alertText.innerHTML = "Login Success";

        alertIcon.innerHTML = " <i class='fa fa-check' style='font-size:18px;color:#fff'></i>";

        alertBox.style.backgroundColor = "#00cc44";

        alertBox.style.color = "#fff";

        setTimeout(async () =>
        {
          alertIcon.innerHTML = "";

          alertBox.style.backgroundColor = "black";

          alertBox.style.color = "black";

          window.location.href = "/api/index.html";
        }, 300);
      },
      function (ajaxResponse)
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
    );
  }
}

function fetchData()
{
  const username = document.getElementById("username").value;

  const password = document.getElementById("password").value;

  const method = "POST";

  const url = "/login"

  const formData = new URLSearchParams();

  formData.append("username", username);

  formData.append("password", password);

  const myData =
    {
    method: method,

    url: url,

    formData: formData,
  };

  return myData;
}


