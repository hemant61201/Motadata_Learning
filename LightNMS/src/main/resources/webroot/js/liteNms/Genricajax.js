var genricajax =
  {
    post: function (data, successCallback, errorCallback)
    {
      var sendData;

      if(data.url === "/login")
      {
        sendData = data.formData.toString();
      }

      else if(data.url === "/addDiscovery")
      {
        sendData = JSON.stringify({
          DEVICENAME: data.deviceName,
          DEVICETYPE: data.deviceType,
          IP: data.ip,
          CREDENTIAL: data.credential
        })
      }

      else if(data.url === "/viewMonitor")
      {
        sendData = data.ip
      }

      else
      {
        sendData = data.id
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
