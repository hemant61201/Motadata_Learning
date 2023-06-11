var input = document.getElementById("ip");

var ipPattern = /^(25[0-5]|2[0-4][0-9]|[01]?[1-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

input.addEventListener("input", function()
{
  var inputValue = input.value;

  if (inputValue === "")
  {
    input.setCustomValidity("Input cannot be empty.");
  }

  else if (!ipPattern.test(inputValue))
  {
    input.setCustomValidity("Invalid IP address format.");
  }

  else
  {
    input.setCustomValidity("");
  }
});
