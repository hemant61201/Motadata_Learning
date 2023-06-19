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
              let callbacks = $.Callbacks();

              callbacks.add(data.callbacks.success);

              callbacks.fire(ajaxResponse);

              callbacks.remove(data.callbacks.success)

            }
          },

          error: function (ajaxResponse)
          {
            if (data.callbacks && data.callbacks.success)
            {
              let callbacks = $.Callbacks();

              callbacks.add(data.callbacks.success);

              callbacks.fire(ajaxResponse);

              callbacks.remove(data.callbacks.success)
            }
          }
        });
    },
};
