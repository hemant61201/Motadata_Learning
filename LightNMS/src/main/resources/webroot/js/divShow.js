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
}

window.addEventListener('load', function() {
  var selectedDivId = localStorage.getItem('selectedDiv');
  if (selectedDivId) {
    showDiv(selectedDivId);
  } else {
    showDiv('dashboardDiv');
  }
});
