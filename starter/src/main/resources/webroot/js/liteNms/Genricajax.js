var genricajax =
  {
    post: function (data, successCallback, errorCallback)
    {
      var sendData;

      if(data.url === "/login")
      {
        sendData = data.formData.toString();

        console.log(sendData);
      }
      else
      {
        sendData = data.data

        console.log(sendData);
      }

      $.ajax(
        {
          method: data.method,

          url: data.url,

          data: sendData,

          success: function (ajaxResponse)
          {
            successCallback(ajaxResponse);
          },

          error: function (ajaxResponse)
          {
            errorCallback(ajaxResponse);
          }
        });
    },
    get: function (data, successCallback, errorCallback)
    {
      $.ajax(
        {
          method: data.method,

          url: data.url,

          data: data.data,

          success: function (ajaxResponse)
          {
            successCallback(ajaxResponse);
          },

          error: function (ajaxResponse)
          {
            errorCallback(ajaxResponse);
          }
      });
    }
};
