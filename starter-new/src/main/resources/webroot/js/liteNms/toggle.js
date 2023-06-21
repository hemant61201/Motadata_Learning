var toggleBtn =
  {
    toggleField: function ()
    {
      var select = $("#deviceSelect").get(0);

      var selectedOption = select.options[select.selectedIndex].value;

      var allFields = document.querySelectorAll("[id$='Field']");

      for (var i = 0; i < allFields.length; i++)
      {
        var field = allFields[i];

        if (field.id === selectedOption + "Field")
        {
          field.classList.remove("hidden");
        }

        else
        {
          field.classList.add("hidden");
        }
      }
    }
  }


