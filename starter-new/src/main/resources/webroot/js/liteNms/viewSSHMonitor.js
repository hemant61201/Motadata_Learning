var SSHMonitor =
  {
    sshMonitor: function (ajaxResponse)
    {
      var data = JSON.parse(ajaxResponse);

      // Update box values
      var lossValue = $('#lossValue').get(0);

      lossValue.innerText = data.Loss;

      var uptimeValue = $('#uptimeValue').get(0);

      uptimeValue.innerText = data.Uptime;

      var rttValue = $('#rttValue').get(0);

      rttValue.innerText = data.Avg[data.Avg.length - 2];

      var cpuValue = $('#cpuValue').get(0);

      cpuValue.innerText = data.CPU[data.CPU.length - 2] + "%";

      var memoryValue = $('#memoryValue').get(0);

      memoryValue.innerText = data.Memory[data.Memory.length - 2];

      var diskValue = $('#diskValue').get(0);

      diskValue.innerText = data.Disk[data.Disk.length - 2];

      // Create availability donut chart
      var availabilityData = [{
        label: 'Success',
        value: data.Status.success
      }, {
        label: 'Failed',
        value: data.Status.failed
      }];

      drawDonutChart('availabilityChart', availabilityData);

      // Create bar chart
      var barChartData = [{
        label: 'Max',
        value: parseFloat(data.Max[0])
      }, {
        label: 'Min',
        value: parseFloat(data.Min[0])
      }, {
        label: 'Avg',
        value: parseFloat(data.Avg[0])
      }];

      drawBarChart('barChart', barChartData);

      // Create earth chart for CPU
      var cpuChartData = [];

      for (var i = 1; i < data.CPU.length; i += 2)
      {
        var timestamp = data.CPU[i].split('.')[0];

        var adjustedTimestamp = new Date(timestamp + "Z");

        adjustedTimestamp.setHours(adjustedTimestamp.getHours());

        adjustedTimestamp.setMinutes(adjustedTimestamp.getMinutes());

        cpuChartData.push({
          x: adjustedTimestamp.getTime(),
          y: parseFloat(data.CPU[i - 1]) || 0
        });
      }

      drawEarthChart('earthChartCPU', cpuChartData);

      // Create earth chart for Memory
      var memoryChartData = [];

      for (var i = 1; i < data.Memory.length; i += 2)
      {
        var timestamp = data.Memory[i].split('.')[0];

        var adjustedTimestamp = new Date(timestamp + "Z");

        adjustedTimestamp.setHours(adjustedTimestamp.getHours());

        adjustedTimestamp.setMinutes(adjustedTimestamp.getMinutes());

        memoryChartData.push({
          x: adjustedTimestamp.getTime(),
          y: parseFloat(data.Memory[i - 1]) || 0
        });
      }

      drawEarthChart('earthChartMemory', memoryChartData);

      // Create earth chart for Disk
      var diskChartData = [];

      for (var i = 1; i < data.Disk.length; i += 2)
      {
        var timestamp = data.Disk[i].split('.')[0];

        var adjustedTimestamp = new Date(timestamp + "Z");

        adjustedTimestamp.setHours(adjustedTimestamp.getHours());

        adjustedTimestamp.setMinutes(adjustedTimestamp.getMinutes());

        diskChartData.push({
          x: adjustedTimestamp.getTime(),
          y: parseFloat(data.Disk[i - 1]) || 0
        });
      }

      drawEarthChart('earthChartDisk', diskChartData);

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

          colors: ['#00E345', '#FF4560'] // Set green and red colors
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);

        chart.render();
      }

      function drawBarChart(chartId, data) {
        // Chart drawing logic for bar chart
        var options = {
          series: [{
            name: 'Value',
            data: data.map(item => item.value)
          }],

          chart: {
            type: 'bar',
            height: 200,
          },

          plotOptions: {
            bar: {
              horizontal: false,
              columnWidth: '55%',
              endingShape: 'rounded'
            },
          },

          dataLabels: {
            enabled: false
          },

          xaxis: {
            categories: data.map(item => item.label)
          },

          yaxis: {
            title: {
              text: 'Value'
            }
          },

          legend: {
            position: 'bottom'
          }
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);

        chart.render();
      }

      function drawEarthChart(chartId, data) {
        // Chart drawing logic for earth chart
        var options = {
          series: [{
            name: 'Value',
            data: data
          }],

          chart: {
            height: 200,
            type: 'area',
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
          }
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);

        chart.render();
      }
    }
  }
