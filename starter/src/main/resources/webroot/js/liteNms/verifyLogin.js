var login =
{
  onclick : function ()
  {
    let myData = fetchData.fetchData();

    genricAjax.ajaxCall(myData);
  }
}

var fetchData =
  {
    fetchData : function ()
    {
      var alertBox = $("#alert").get(0);

      var alertText = $("#text").get(0);

      var alertIcon = $("#icon").get(0);

      const username = $("#username").val();

      const password = $("#password").val();

      const method = "POST";

      const url = "/login"

      const formData = new URLSearchParams();

      formData.append("username", username);

      formData.append("password", password);

      const alertName = "login";

      const alertData = "";

      const myData =
        {
          method: method,

          url: url,

          data: formData.toString(),

          callbacks: {

            success: function (ajaxResponse)
            {
              successAlert.success(alertBox, alertIcon, alertText, alertName, alertData);
            },

            fail: function (ajaxResponse)
            {
              failAlert.fail(alertBox, alertIcon, alertText, alertName, alertData);
            }

          }
        };

      return myData;
  }
}


