function showDiv(divId) {
  var div = document.getElementById(divId);

  // Hide all other divs
  var allDivs = document.querySelectorAll('.content-wrapper');
  allDivs.forEach(function(div) {
    div.classList.add('hidden');
  });

  // Show the selected div
  div.classList.remove('hidden');

  localStorage.setItem('selectedDiv', divId);

  if (divId === 'monitorDiv') {
    $(div).ready(function() {
      let config = getmonitorConfig();
      console.log("config : " + config);
      ajax.get(config);
    });
  }

  else if (divId === 'discoveyDiv') {
    $(div).ready(function() {
      let config = getdiscoveryConfig();
      console.log("config : " + config);
      ajax.get(config);
    });
  }

  // else if (divId === 'dashboardDiv') {
  //   $(div).ready(function ()
  //   {
  //     var eventBus = new EventBus('http://localhost:8080/eventbus/');
  //
  //     eventBus.onopen = () => {
  //       eventBus.registerHandler('updates.pollingdata', (error, message) => {
  //         console.log(`received a message: ${JSON.stringify(message)}`);
  //       });
  //     }
  //
  //     eventBus.onerror = function (e) {
  //       console.log('General error: ', e);
  //     };
  //     var eventBus = new EventBus('/eventbus/');
  //
  //     eventBus.onopen = function ()
  //     {
  //       eventBus.registerHandler('pollingdata', function (error, message)
  //       {
  //         console.log(`received a message: ${JSON.stringify(message)}`);
  //       });
  //     }
  //   });
  // }
}

window.addEventListener('load', function() {
  var selectedDivId = localStorage.getItem('selectedDiv');
  if (selectedDivId) {
    showDiv(selectedDivId);
  } else {
    showDiv('dashboardDiv');
  }
});
