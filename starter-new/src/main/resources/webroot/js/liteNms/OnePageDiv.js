var monitor =
  {
    monitorPage: function ()
    {
      $("#dashboardDiv").html("<div id=\"monitorDiv\"><section class=\"content-header\"><div class=\"container-fluid\"><div class=\"row mb-2\"><div class=\"col-sm-6\"><h1><i class=\"fa fa-desktop\"></i>Monitor</h1></div><div class=\"col-sm-6\"><ol class=\"breadcrumb float-sm-right\"><li class=\"breadcrumb-item\"><a href=\"index.html\">Home</a></li><li class=\"breadcrumb-item active\">Monitor</li></ol></div></div></div></section><div class=\"card\"><div class=\"card-header\"><h3 class=\"card-title\">Monitor</h3><br><table id=\"monitorTable\" class=\"display\" width=\"100%\"></table></div></div></div>");

      monitor.loadMonitor();
    },

    loadMonitor: function ()
    {
      let config = monitorConfig.getmonitorConfig();

      genricAjax.ajaxCall(config)
    }
  };

var pingMonitor =
  {
    pingMonitor: function ()
    {
      $("#dashboardDiv").html("<div id=\"viewMonitorDiv\"><div class=\"row\"><div class=\"col\"><div class=\"small-box\" id=\"lossBox\" style=\"background-color: red; height: 100px; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px; font-weight: bold;\"><div id=\"lossLabel\">Loss</div><div id=\"lossValue\">00</div></div></div><div class=\"col\"><div class=\"small-box\" id=\"rttBox\" style=\"background-color: blue; height: 100px; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px; font-weight: bold;\"><div id=\"rttLabel\">RTT</div><div id=\"rttValue\">00</div></div></div></div><div class=\"row\"><div class=\"col\" style=\"height: 300px; width: 600px;\"><canvas id=\"availabilityChart\" ></canvas></div></div><div class=\"row\"><div class=\"col\" style=\"height: 300px; width: 600px;\"><div class=\"chart\" id=\"lineChart\">24 Hours Rtt Chart</div></div></div></div>")
    }
  };

var discovery =
  {
    discoveryPage: function ()
    {
      $("#dashboardDiv").html("<div id=\"discoveryDiv\"><section class=\"content-header\"><div class=\"container-fluid\"><div class=\"row mb-2\"><div class=\"col-sm-6\"><h1><i class=\"fa fa-binoculars menu-grid-fa-size\" id=\"icon\"></i>Discovery</h1></div><div class=\"col-sm-6\"><ol class=\"breadcrumb float-sm-right\"><li class=\"breadcrumb-item\"><a href=\"index.html\">Home</a></li><li class=\"breadcrumb-item active\">Discovery</li></ol></div></div></div></section><div class=\"card\"><div class=\"card-header\"><h3 class=\"card-title\">Discovery</h3><div class=\"card-tools\"><button type=\"button\" id=\"add-button\" class=\"btn btn-tool\" style=\"height: 40px; align-content: center\" data-card-widget=\"add\" title=\"Add\"><i class=\"fa fa-plus\"></i></button><dialog id=\"add-dialog\" style='z-index: 9999999'><div class=\"card card-primary\"><div class=\"card-header\"><h3 class=\"card-title\">Discovery Details</h3><div class=\"card-tools\"><button type=\"button\" id=\"minus-button\" class=\"btn btn-tool\" data-card-widget=\"close\" title=\"Close\"><i class=\"fa fa-minus\"></i></button></div></div><div class=\"card-body\"><div class=\"form-group\"><label for=\"name\">Monitor Name</label><input type=\"text\" id=\"name\" class=\"form-control\" name=\"name\" required></div><div class=\"form-group\"><label for=\"ip\">Monitor IP</label><input type=\"text\" id=\"ip\" class=\"form-control\" name=\"ip\" placeholder=\"e.g 10.20.40.156\" required></div><div class=\"form-group\"><label for=\"type\">Type</label><select id=\"type\" class=\"form-control custom-select\" name=\"type\" required><option disabled>Select one</option><option>Ping</option><option>SSH</option></select></div><div class=\"form-group\" id=\"credentialsFormGroup\"><label for=\"credential_username\">UserName</label><input type=\"text\" id=\"credential_username\" class=\"form-control\" name=\"credential\" required><br><label for=\"credential_passwd\">Password</label><input type=\"password\" id=\"credential_passwd\" class=\"form-control\" name=\"credential\" required></div><button type=\"button\" id=\"addMontior\" data-card-widget=\"close\">Add</button></div></div></dialog><dialog id=\"update-dialog\" style='z-index: 99999' aria-hidden=\"true\"><div class=\"card card-primary\"><div class=\"card-header\"><h3 class=\"card-title\">Discovery Details</h3><div class=\"card-tools\"><button type=\"button\" id=\"minus-update-button\" class=\"btn btn-tool\" data-card-widget=\"close\" title=\"Close\"><i class=\"fa fa-minus\"></i></button></div></div><div class=\"card-body\"><div class=\"form-group\"><label for=\"deviceSelect\">Select a field:</label><select id=\"deviceSelect\" onchange=\"toggleBtn.toggleField()\"><option value=\"\">-- Select --</option><option value=\"DeviceName\">Device Name</option><option value=\"IP\">IP</option><option value=\"DeviceType\">Device Type</option><option value=\"credential\">Credential</option></select><br><br><div id=\"DeviceNameField\" class=\"hidden\"><label for=\"update_deviceName\">Device Name:</label><input type=\"text\" id=\"update_deviceName\" required></div><div id=\"IPField\" class=\"hidden\"><label for=\"update_ip\">IP:</label><input type=\"text\" id=\"update_ip\" required></div><div id=\"DeviceTypeField\" class=\"hidden\"><label for=\"update_deviceType\">Device Type:</label><input type=\"text\" id=\"update_deviceType\" required></div><div id=\"credentialField\" class=\"hidden\"><label for=\"update_username\">Username:</label><input type=\"text\" id=\"update_username\" required><br><br><label for=\"update_password\">Password:</label><input type=\"password\" id=\"update_password\" required></div></div><button type=\"button\" id=\"update_btn\">Update</button></div></div></dialog></div><br><table id=\"discoveryTable\" class=\"display\" width=\"100%\"></table></div></div></div>")

      discovery.loadDiscovery();
    },

    loadDiscovery: function ()
    {
      let config = discoveryConfig.getDiscoveryConfig();

      genricAjax.ajaxCall(config)
    }
  };

var sshMonitor =
  {
    sshMonitor : function ()
    {
      $("#dashboardDiv").html("<div class=\"container\"><div class=\"row\"><div class=\"box\"><span class=\"label\">Loss</span><span id=\"lossValue\"></span></div><div class=\"box\"><span class=\"label\">Uptime</span><span id=\"uptimeValue\"></span></div><div class=\"box\"><span class=\"label\">RTT</span><span id=\"rttValue\"></span></div><div class=\"box\"><span class=\"label\">CPU</span><span id=\"cpuValue\"></span></div><div class=\"box\"><span class=\"label\">Memory</span><span id=\"memoryValue\"></span></div><div class=\"box\"><span class=\"label\">Disk</span><span id=\"diskValue\"></span></div></div><div class=\"row\"><div class=\"chart\" id=\"availabilityChart\"></div></div><div class=\"row\"><div class=\"chart\" id=\"lineChart\">24 Hours Rtt Chart</div><div class=\"chart\" id=\"earthChartCPU\">24 Hours CPU Used Chart</div></div><div class=\"row\"><div class=\"chart\" id=\"earthChartMemory\">24 Hours Memory Used Chart</div><div class=\"chart\" id=\"earthChartDisk\">24 Hours Disk Used Chart</div></div></div>")
    }
  };


