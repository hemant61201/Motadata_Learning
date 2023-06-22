var dashBoardData =
  {
    updateData: function (data)
    {
      var jsonData = JSON.parse(data);

      var successValue = $('#successValue');

      successValue.get(0).innerText = jsonData.success;

      var failedValue = $('#failedValue');

      failedValue.get(0).innerText = jsonData.failed;

      var unknownValue = $('#unknownValue');

      unknownValue.get(0).innerText = jsonData.unknown;

      var maxTable = $('#maxTable').get(0);

      maxTable.innerHTML = `
        <caption>Max 5 Rtt</caption>
        <tr>
          <th>IP</th>
          <th>Value</th>
        </tr>
        `;

      for (var ip in jsonData.max)
      {
        var value = jsonData.max[ip];

        var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

        maxTable.innerHTML += row;
      }

      var cpuTable = $('#cpuTable').get(0);

      cpuTable.innerHTML = `
        <caption>Max 5 CPU Usage</caption>
        <tr>
          <th>IP</th>
          <th>Value</th>
        </tr>
      `;

      for (var ip in jsonData.cpu)
      {
        var value = jsonData.cpu[ip] + "%";

        var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

        cpuTable.innerHTML += row;
      }

      var memoryTable = $('#memoryTable').get(0);

      memoryTable.innerHTML = `
        <caption>Max 5 Memory Usage</caption>
        <tr>
          <th>IP</th>
          <th>Value</th>
        </tr>
      `;

      for (var ip in jsonData.memory)
      {
        var value = jsonData.memory[ip];

        var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

        memoryTable.innerHTML += row;
      }

      var diskTable = $('#diskTable').get(0);

      diskTable.innerHTML = `
        <caption>Max 5 Disk Usage</caption>
        <tr>
          <th>IP</th>
          <th>Value</th>
        </tr>
      `;

      for (var ip in jsonData.disk)
      {
        var value = jsonData.disk[ip];

        var row = `<tr><td>${ip}</td><td>${value}</td></tr>`;

        diskTable.innerHTML += row;
      }
    }
  }

