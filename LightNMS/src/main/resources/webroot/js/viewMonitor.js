var metricChart; // Declare chart variable outside the function

function viewMonitor(data) {
  var jsonData = JSON.parse(data);

  // Update % Loss box
  var lossBox = document.getElementById('lossBox');
  lossBox.innerText = "Loss: " + jsonData.Loss;

  // Update Availability box
  var availabilityBox = document.getElementById('availabilityBox');
  availabilityBox.innerText = "Availability: " + jsonData.Status;

  // Calculate max value of Avg
  var avgValues = jsonData.Avg;
  var maxAvg = Math.max(...avgValues);

  // Update RTT box
  var rttBox = document.getElementById('rttBox');
  rttBox.innerText = "RTT: " + maxAvg;

  // Extract Min and Max values
  var minValues = jsonData.Min;
  var maxValues = jsonData.Max;

  // Check if chart instance exists
  if (metricChart) {
    // Update existing chart data
    metricChart.data.datasets[0].data = [Math.min(...minValues), Math.max(...maxValues), maxAvg];
    metricChart.update();
  } else {
    // Create new chart instance
    metricChart = new Chart(document.getElementById('metricChart'), {
      type: 'bar',
      data: {
        labels: ['Min', 'Max', 'Avg'],
        datasets: [
          {
            label: 'Metric Values',
            data: [Math.min(...minValues), Math.max(...maxValues), maxAvg],
            backgroundColor: 'rgba(0, 123, 255, 0.2)',
            borderColor: 'rgba(0, 123, 255, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: 1,
            ticks: {
              stepSize: 0.2
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        }
      }
    });
  }
}
