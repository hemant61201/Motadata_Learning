var availabilityChart = null;

var monitorView =
  {
    viewMonitor: function (data)
    {
      var jsonData = JSON.parse(data);

      // Update % Loss box
      var lossValue = $('#lossValue');

      lossValue.get(0).innerText = jsonData.Loss;

      // Update RTT box
      var rttValue = $('#rttValue');

      rttValue.get(0).innerText = jsonData.Avg[jsonData.Avg.length - 2];

      // Destroy existing charts if they exist
      if (availabilityChart)
      {
        availabilityChart.destroy();
      }

      // Create Availability chart
      var availabilityData = {

        labels: ["Success", "Failed"],

        datasets: [
          {
            data: [((jsonData.Status.success / (jsonData.Status.success + jsonData.Status.failed)) * 100).toFixed(2), ((jsonData.Status.failed / (jsonData.Status.success + jsonData.Status.failed)) * 100).toFixed(2)],

            backgroundColor: ["limegreen", "red"]
          }
        ]
      };

      availabilityChart = new Chart($('#availabilityChart').get(0), {

        type: 'doughnut',

        data: availabilityData,

        options: {
          plugins: {
            tooltip: {
              callbacks: {
                label: function (context) {

                  var label = context.label || '';

                  var value = context.raw || '';

                  return label + ": " + value + "%";
                }
              }
            }
          },
          responsive: true,

          maintainAspectRatio: false
        }
      });

      var lineChartData = [];

      for (var i = 1; i < jsonData.Avg.length; i += 2) {

        var timestamp = jsonData.Avg[i].split('.')[0];

        var adjustedTimestamp = new Date(timestamp + "Z");

        adjustedTimestamp.setHours(adjustedTimestamp.getHours());

        adjustedTimestamp.setMinutes(adjustedTimestamp.getMinutes());

        lineChartData.push({
          timestamp: adjustedTimestamp.getTime(),
          avgValue: jsonData.Avg[i - 1],
          maxValue: jsonData.Max[i - 1],
          minValue: jsonData.Min[i - 1]
        });
      }

      drawLineChart('lineChart', lineChartData);

      // Create Metric chart
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
          stroke: {
            width: 2
          }
        };

        var chart = new ApexCharts($("#" + chartId).get(0), options);
        chart.render();
      }
    }
  };


