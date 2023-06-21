$('#dashboardDiv').ready(function ()
{
  var eventBus = new EventBus('/api/eventbus');

  eventBus.onopen = function ()
  {
    eventBus.registerHandler('updates.pollingdata', function (error, message)
    {
      dashBoardData.updateData(JSON.stringify(message.body));
    });
  }

  let pollingData = pollingConfig.getPollingConfig();

  genricAjax.ajaxCall(pollingData);
});

var pollingConfig =
  {
    getPollingConfig: function ()
    {
      const method = "GET";

      const url = "/api/getPolling"

      var config =
        {
          method: method,

          url: url,

          callbacks: {

            success: function (ajaxResponse)
            {
              dashBoardData.updateData(ajaxResponse);

            },

            fail: function (ajaxResponse)
            {
              console.log(ajaxResponse)
            }

          }
        }

      return config;
    }
  }

