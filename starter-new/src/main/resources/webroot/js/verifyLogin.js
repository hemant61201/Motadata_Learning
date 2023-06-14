var login =
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
  const username = document.getElementById("username").value;

  console.log(username);

  const password = document.getElementById("password").value;

  console.log(password);

  const method = "POST";

  const url = "/login"

  const contentType = "application/x-www-form-urlencoded";

  const formData = new URLSearchParams();

  formData.append("username", username);

  formData.append("password", password);

  console.log(formData);

  const myData =
    {
    method: method,

    url: url,

    contentType: contentType,

    formData: formData,
  };

  return myData;
}


