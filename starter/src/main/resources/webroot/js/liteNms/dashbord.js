var dashBoardData =
  {
    updateData: function (data)
    {
      var jsonData = JSON.parse(data);

      console.log(jsonData)

      var successValue = $('#successValue');

      successValue.get(0).innerText = jsonData.success;

      var failedValue = $('#failedValue');

      failedValue.get(0).innerText = jsonData.failed;

      var unknownValue = $('#unknownValue');

      unknownValue.get(0).innerText = jsonData.unknown;

      var maxTable = $('#maxTable').get(0);

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

      var minTable = $('#minTable').get(0);

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
  }

