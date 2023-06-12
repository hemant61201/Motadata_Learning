$(function ()
{
  var eventBus = new EventBus('/eventbus/');

  eventBus.onopen = function ()
  {
    eventBus.registerHandler('updates.pollingdata', function (error, message)
    {
      console.log(`received a message: ${JSON.stringify(message)}`);
    });
  }

})


