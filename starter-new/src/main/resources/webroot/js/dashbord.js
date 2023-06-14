function updateData(data) {
  var jsonData = JSON.parse(data);

  console.log("dasboard.js" + data);

  var successValue = document.getElementById('successValue');

  successValue.innerText = jsonData.success;

  var failedValue = document.getElementById('failedValue');

  failedValue.innerText = jsonData.failed;

  var unknownValue = document.getElementById('unknownValue');

  unknownValue.innerText = jsonData.unknown;

  var maxTable = document.getElementById('maxTable');

  maxTable.innerHTML = `
    <caption>Max 10 Rtt</caption>
    <tr>
      <th>IP</th>
      <th>Value</th>
    </tr>
    `;

  for (var ip in jsonData.max.Max)
  {
    var value = jsonData.max.Max[ip];

    var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

    maxTable.innerHTML += row;
  }

  var minTable = document.getElementById('minTable');

  minTable.innerHTML = `
    <caption>Min 10 Rtt</caption>
    <tr>
      <th>IP</th>
      <th>Value</th>
    </tr>
  `;

  for (var ip in jsonData.min.Min)
  {
    var value = jsonData.min.Min[ip];

    var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

    minTable.innerHTML += row;
  }
}
