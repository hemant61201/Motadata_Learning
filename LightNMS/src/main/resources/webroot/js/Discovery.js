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

  myData =
    {
      method: method,

      url: url,

      deviceName: deviceName,

      ip: ip,

      deviceType: deviceType,

      credential_userName: credential_userName,

      credential_password: credential_password,
    };

  return myData;
}






