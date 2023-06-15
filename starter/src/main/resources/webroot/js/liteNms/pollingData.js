$('dashboardDiv').ready(function ()
{
  var eventBus = new EventBus('/api/eventbus');

  eventBus.onopen = function ()
  {
    eventBus.registerHandler('updates.pollingdata', function (error, message)
    {
      updateData(JSON.stringify(message.body));
    });
  }

})


