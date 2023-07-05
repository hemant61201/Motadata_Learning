var SNMPMonitor =
  {
    snmpMonitor: function (ajaxResponse)
    {
      var data = JSON.parse(ajaxResponse)

      var successPercentage = ((data.Status.success / (data.Status.success + data.Status.failed)) * 100).toFixed(2);

      var failedPercentage = ((data.Status.failed / (data.Status.success + data.Status.failed)) * 100).toFixed(2);

      var availabilityData = [{
        label: 'Success (' + successPercentage + '%)',
        value: parseFloat(successPercentage)
      }, {
        label: 'Failed (' + failedPercentage + '%)',
        value: parseFloat(failedPercentage)
      }];

      drawDonutChart('availabilityChart', availabilityData);

      var lineChartData = [];

      for (var i = 1; i < data.Avg.length; i += 2) {
        lineChartData.push({
          timestamp: data.Avg[i],
          avgValue: data.Avg[i - 1],
          maxValue: data.Max[i - 1],
          minValue: data.Min[i - 1]
        });
      }

      drawLineChart('lineChart', lineChartData);

      function drawDonutChart(chartId, data)
      {
        // Chart drawing logic for donut chart
        var options = {

          series: data.map(item => item.value),

          labels: data.map(item => item.label),

          chart: {
            type: 'donut',
            height: 200,
          },

          dataLabels: {
            enabled: false
          },

          legend: {
            position: 'bottom'
          },

          colors: ['#00E345', '#FF4560'], // Set green and red colors

          width: 100,

          height: 100,
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);

        chart.render();
      }

      function drawLineChart(chartId, data) {
        // Chart drawing logic for line chart
        var options = {
          series: [{
            name: 'Average',
            data: data.map(item => ({ x: item.timestamp, y: parseFloat(item.avgValue) || 0 }))
          }, {
            name: 'Max',
            data: data.map(item => ({ x: item.timestamp, y: parseFloat(item.maxValue) || 0 }))
          }, {
            name: 'Min',
            data: data.map(item => ({ x: item.timestamp, y: parseFloat(item.minValue) || 0 }))
          }],
          chart: {
            height: 300,
            type: 'line',
            zoom: {
              enabled: false
            }
          },
          dataLabels: {
            enabled: false
          },
          xaxis: {
            type: 'datetime',
            labels: {
              format: 'yyyy-MM-dd HH:mm:ss'
            }
          },
          yaxis: {
            title: {
              text: 'Value'
            }
          },
          legend: {
            position: 'bottom'
          },
          width: 100,
          height: 100
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);

        chart.render();
      }

      // Parse SNMPData as a JSON object
      try {

        var snmpData = JSON.parse(data.SNMPData);
        // Get the table body element
        var tableBody = $('#systemData').get(0);

        // Clear the table body
        tableBody.innerHTML = '';

        // Display system data in the table
        for (var key in snmpData.result.system)
        {
          var value = snmpData.result.system[key];

          // Create a new row
          var row = tableBody.insertRow();

          // Create cells for title and data
          var titleCell = row.insertCell();

          var dataCell = row.insertCell();

          // Set the title and data values
          titleCell.textContent = key;

          dataCell.textContent = value;
        }
      }
      catch (error)
      {
        console.error('Error parsing SNMPData:', error);
      }

      const jsonString = JSON.stringify(snmpData.result.interface);

      const jsonArray = JSON.parse(jsonString);

      const arrayOfArrays = [];

      jsonArray.forEach(obj => {
        const interfaceData = {
          'Interface Name': obj['interface.name'] || '-',
          'Status': obj['interface.admin.status'] || '-',
          'Alias': obj['interface.alias'] || '-',
          'Physical Address': obj['interface.physical.address'] || '-',
          'Index': obj['interface.index'] || '-',
          'Description': obj['interface.description'] || '-',
          'Operational Status': obj['interface.operational.status'] || '-',
          'Sent Error Packets': obj['interface.sent.error.packet'] || '-',
          'Sent Octets': obj['interface.sent.octets'] || '-',
          'Received Error Packets': obj['interface.received.error.packet'] || '-',
          'Received Octets': obj['interface.received.octets'] || '-',
          'Speed': obj['interface.speed'] || '-'
        };

        arrayOfArrays.push(interfaceData);
      });

      $('#interfaceTable').DataTable({
        destroy: true,
        data: arrayOfArrays,
        columns: [{title: 'Interface Name', data: 'Interface Name'},
          { title: 'Status', data: 'Status', render: function(data) {
              return (data === '1') ? 'Up' : 'Down';
            }},
          { title: 'Alias', data: 'Alias'},
          { title: 'Physical Address', data: 'Physical Address'},
          { title: 'Index', data: 'Index' },
          { title: 'Description', data: 'Description' },
          { title: 'Operational Status', data: 'Operational Status' , render: function(data) {
              return (data === '1') ? 'Up' : 'Down';
            }},
          { title: 'Sent Error Packets', data: 'Sent Error Packets' },
          { title: 'Sent Bytes', data: 'Sent Octets' },
          { title: 'Received Error Packets', data: 'Received Error Packets' },
          { title: 'Received Bytes', data: 'Received Octets' },
          { title: 'Bandwidth in BPS', data: 'Speed' },
        ],
      });
    }
  }
