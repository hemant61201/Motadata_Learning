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
}

window.addEventListener('load', function() {
  var selectedDivId = localStorage.getItem('selectedDiv');
  if (selectedDivId) {
    showDiv(selectedDivId);
  } else {
    showDiv('dashboardDiv');
  }
});
