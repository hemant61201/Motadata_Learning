// $("#discoveyDiv").ready( function ()
// {
//   let config = getConfig();
//
//   console.log("config : " + config)
//
//   ajax.get(config);
// })

function myFunction()
{
  let config = getdiscoveryConfig();
  console.log("config: " + config);
  ajax.get(config);
}

var discovery =
  {
    onclick()
    {
      let myData = fetchData();

      console.log(myData)

      ajax.post(myData);
    }
  }

function fetchData()
{
  var myData;

  const deviceName = document.getElementById("name").value;

  console.log(deviceName);

  const ip = document.getElementById("ip").value;

  console.log(ip);

  const method = "POST";

  const url = "/addDiscovery"

  const deviceType = document.getElementById("type").value;

  const credential_userName = document.getElementById("credential_username").value;

  const credential_password = document.getElementById("credential_passwd").value;

  var credential = {

    credential_userName: credential_userName,

    credential_password: credential_password
  };

  myData =
    {
      method: method,

      url: url,

      deviceName: deviceName,

      ip: ip,

      deviceType: deviceType,

      credential : credential,
    };

  return myData;
}

function getdiscoveryConfig()
{
  const method = "GET";

  const url = "/getDiscoveryTable"

  var config =
    {
      method: method,

      url: url
    }

    return config;
}





