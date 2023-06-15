var availabilityChart = null;

var metricChart = null;

function viewMonitor(data)
{
  var jsonData = JSON.parse(data);

  // Update % Loss box
  var lossValue = document.getElementById('lossValue');

  lossValue.innerText = jsonData.Loss;

  // Update RTT box
  var rttValue = document.getElementById('rttValue');

  rttValue.innerText = "RTT: " + Math.max(...jsonData.Avg);

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

  availabilityChart = new Chart(document.getElementById('availabilityChart'), {

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

  metricChart = new Chart(document.getElementById('metricChart'), {

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

// Modify CSS styles
var availabilityChartContainer = document.getElementById('availabilityChart').parentNode;

availabilityChartContainer.style.height = '300px';

availabilityChartContainer.style.width = '600px';

var metricChartContainer = document.getElementById('metricChart').parentNode;

metricChartContainer.style.height = '300px';

metricChartContainer.style.width = '600px';

