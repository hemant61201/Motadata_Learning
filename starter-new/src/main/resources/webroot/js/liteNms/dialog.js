var dialogBox =
  {
    bindDialogBox: function ()
    {
      const addButton = $('#add-button');

      const addDialog = $('#add-dialog');

      const minusButton = $('#minus-button');

      const closeAddForm = $("#addMontior");

      const closeUpdateForm = $("#update_btn");

      const updateDialog = $("#update-dialog");

      const minusUpdatdeButton = $("#minus-update-button");

      addButton.on('click', () => {
        addDialog.get(0).showModal();
      });

      minusButton.on('click', () => {
        addDialog.get(0).close();
      });

      minusUpdatdeButton.on('click', () => {
        updateDialog.get(0).close();
      });

      closeAddForm.on('click', () =>
      {
        addDialog.get(0).close();

        addDiscovery.onclick();
      });

      closeUpdateForm.on('click', () =>
      {
        updateDialog.get(0).close();

        updateBtn.onclick();
      });

      $('#credentialsFormGroup').hide();

      $('#type').change(function()
      {
        var selectedValue = $(this).val();

        if (selectedValue === 'SSH')
        {
          $('#credentialsFormGroup').show();
        }
        else
        {
          $('#credentialsFormGroup').hide();
        }
      });
    }
  };


