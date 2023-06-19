var validation =
  {
    preCheckAddValue: function ()
    {
      const nameInput = $("#name");

      const ipInput = $("#ip");

      const typeInput = $("#type");

      const usernameInput = $("#credential_username");

      const passwordInput = $("#credential_passwd");

      if (!nameInput.val() || !ipInput.val() || !typeInput.val()) {
        alert("Please fill in all the required fields.");
        $("#add-dialog").get(0).showModal();
        return false;
      }

      const ipRegex = /^(25[0-5]|2[0-4][0-9]|[01]?[1-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

      if (!ipRegex.test(ipInput.val())) {
        alert("Please enter a valid IP address.");
        $("#add-dialog").get(0).showModal();
        return false;
      }

      if (typeInput.val() === "SSH" && (!usernameInput.val() || !passwordInput.val())) {
        alert("Please fill in the credential fields for SSH type.");
        $("#add-dialog").get(0).showModal();
        return false;
      }

      else
      {
        return true;
      }
    },

    preCheckUpdateValue: function ()
    {
      const deviceSelect = $("#deviceSelect");
      const usernameInput = $("#update_username");
      const passwordInput = $("#update_password");
      const ipInput = $("#update_ip");

      if (!deviceSelect.val()) {
        alert("Please select a field.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      if (deviceSelect.val() === "DeviceName" && !$("#update_deviceName").val()) {
        alert("Please enter a device name.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      const ipRegex = /^(25[0-5]|2[0-4][0-9]|[01]?[1-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;


      if (deviceSelect.val() === "IP" && !ipInput.val()) {
        alert("Please enter an IP address.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      if (deviceSelect.val() === "IP" && !ipRegex.test(ipInput.val())) {
        alert("Please enter a valid IP address.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      if (deviceSelect.val() === "DeviceType" && !$("#update_deviceType").val()) {
        alert("Please enter a device type.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      if (deviceSelect.val() === "credential" && (!usernameInput.val() || !passwordInput.val())) {
        alert("Please fill in the credential fields.");
        $("#update-dialog").get(0).showModal();
        return false;
      }

      return true;
    }
  }
