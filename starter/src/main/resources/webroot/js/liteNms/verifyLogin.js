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
    ajaxSuccessMethod : function (ajaxResponse)
    {
      const alertName = "login";

      const alertData = "Login Successful";

      successAlert.success(alertName, alertData);
    },

    ajaxFail : function (ajaxResponse)
    {
      const alertName = "login";

      const alertData = "Login Failed";

      failAlert.fail(alertName, alertData);
    },

    fetchData : function ()
    {
      const username = $("#username").val();

      const password = $("#password").val();

      const method = "POST";

      const url = "/login"

      const formData = new URLSearchParams();

      formData.append("username", username);

      formData.append("password", password);

      const myData =
        {
          method: method,

          url: url,

          data: formData.toString(),

          callbacks:
            {
              success: fetchData.ajaxSuccessMethod,

              fail: fetchData.ajaxFail,
            }
        };

      return myData;
  }
}


