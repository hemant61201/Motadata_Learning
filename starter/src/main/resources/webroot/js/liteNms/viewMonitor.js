var availabilityChart = null;

var metricChart = null;

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

      rttValue.get(0).innerText = "RTT: " + Math.max(...jsonData.Avg);

      // Destroy existing charts if they exist
      if (availabilityChart)
      {
        availabilityChart.destroy();
      }

      if (metricChart)
      {
        metricChart.destroy();
      }

      // Create Availability chart
      var availabilityData = {

        labels: ["Success", "Failed"],

        datasets: [
          {
            data: [jsonData.Status.success, jsonData.Status.failed],

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

                  return label + ": " + value;
                }
              }
            }
          },
          responsive: true,

          maintainAspectRatio: false
        }
      });

      // Create Metric chart
      var metricData = {

        labels: ['Max', 'Min', 'Avg'],

        datasets: [
          {
            label: 'Metric Values',

            data: [Math.max(...jsonData.Max), Math.min(...jsonData.Min), Math.max(...jsonData.Avg)],

            backgroundColor: 'rgba(0, 123, 255, 0.2)',

            borderColor: 'rgba(0, 123, 255, 1)',

            borderWidth: 1
          }
        ]
      };

      metricChart = new Chart($('#metricChart').get(0), {

        type: 'bar',

        data: metricData,

        options: {
          scales: {
            x: {
              title: {
                display: true,

                text: 'Metric'
              }
            },
            y: {
              title: {
                display: true,

                text: 'Value'
              }
            }
          },
          responsive: true,

          maintainAspectRatio: false
        }
      });
    }
  };


