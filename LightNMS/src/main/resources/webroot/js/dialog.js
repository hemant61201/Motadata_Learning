const addButton = document.getElementById('add-button');
const addDialog = document.getElementById('add-dialog');
const minusButton = document.getElementById('minus-button');
const closeForm = document.getElementById("addMontior");

addButton.addEventListener('click', () => {
  addDialog.showModal();
});

minusButton.addEventListener('click', () => {
  addDialog.close();
});

closeForm.addEventListener('click', () => {
  addDialog.close();
});

$(document).ready(function() {
  $('#credentialsFormGroup').hide();

  $('#type').change(function() {

    var selectedValue = $(this).val();

    if (selectedValue === 'SSH') {

      $('#credentialsFormGroup').show();
    } else {
      $('#credentialsFormGroup').hide();
    }
  });
});
