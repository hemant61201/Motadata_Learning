const addButton = document.getElementById('add-button');

const addDialog = document.getElementById('add-dialog');

const minusButton = document.getElementById('minus-button');

const closeForm = document.getElementById("addMontior");

const updateDialog = document.getElementById("update-dialog");

const minusUpdatdeButton = document.getElementById("minus-update-button");

addButton.addEventListener('click', () => {
  addDialog.showModal();
});

minusButton.addEventListener('click', () => {
  addDialog.close();
});

minusUpdatdeButton.addEventListener('click', () => {
  updateDialog.close();
})

closeForm.addEventListener('click', () => {
  addDialog.close();
});

$(document).ready(function()
{
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
});
