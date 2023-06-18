var genricAjax =
  {
    ajaxCall: function (data)
    {
      $.ajax(
        {
          method: data.method,

          url: data.url,

          data: data.data,

          success: function (ajaxResponse)
          {
            if (data.callbacks && data.callbacks.success)
            {
              data.callbacks.success(ajaxResponse);
            }
          },

          error: function (ajaxResponse)
          {
            if (data.callbacks && data.callbacks.fail)
            {
              data.callbacks.fail(ajaxResponse);
            }
          }
        });
    },
};
