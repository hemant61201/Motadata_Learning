<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Log in (v2)</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="all.min.css">
  <!-- icheck bootstrap -->
  <link rel="stylesheet" href="icheck-bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="adminlte.css">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <style>
    *{
      margin:0;
      padding:0;
      overflow: hidden;
    }
  </style>
</head>
<body>
<div class="row justify-content-between px-3" style="position: relative;background-color: black">
  <div class="">

  </div>
  <div class="m-3 row p-2" id="hemant-alert" style="background-color: black;color: #e9ecef;width:15%;font-size: 18px;font-weight: 800">
    <div class="col-4" id="hemant-icon">
    </div>
    <div class="col-8" id="hemant-text" style="color:black">X</div>
  </div>

</div>
<div class="hold-transition login-page" style="top:-20px;background-color: black;">
  <div class="logo">
    <a href="https://www.motadata.com" data-wpel-link="internal">
      <img width="200" height="75" src="https://www.motadata.com/wp-content/themes/motadata/assets/images/logo.svg" alt="" data-lazy-src="https://www.motadata.com/wp-content/themes/motadata/assets/images/logo.svg" data-ll-status="loaded" class="entered lazyloaded">
      <noscript>
        <img width="200" height="75" src="https://www.motadata.com/wp-content/themes/motadata/assets/images/logo.svg" alt="" />
      </noscript>
    </a>
  </div>
  <p class="login-box-msg" style="color: whitesmoke">Sign in to start your session</p>
  <div class="input-group mb-3" style="width:250px">
    <input type="email" name="username" id="Email" class="form-control" placeholder="Email">
    <div class="input-group-append">
      <div class="input-group-text">
        <span class="fa fa-envelope"></span>
      </div>
    </div>
  </div>
  <div class="input-group mb-3" style="width: 250px">
    <input type="password" name="password" id="Password" class="form-control" placeholder="Password">
    <div class="input-group-append">
      <div class="input-group-text">
        <span class="fa fa-lock"></span>
      </div>
    </div>
  </div>
  <div class="row" style="width: 150px">
    <!-- /.col -->
    <button type="submit" class="btn btn-primary btn-block" onclick=verifyLogin()>Sign In</button>
    <!-- /.col -->
  </div>

  <script>
    function verifyLogin() {
      var email = document.getElementById("Email").value;
      var password = document.getElementById("Password").value;
      var alertBox = document.getElementById("hemant-alert");
      var alertText = document.getElementById("hemant-text");
      var alertIcon = document.getElementById("hemant-icon");

      $.ajax({
        method: "POST",
        url: "/login",
        data: JSON.stringify({name: email, password: password}),
        success: function (ajaxResponse){
          if (ajaxResponse === "Success")
            {
              alertText.innerHTML="Login Success";
              alertIcon.innerHTML=" <i class='fa fa-check' style='font-size:18px;color:#fff'></i>";
              alertBox.style.backgroundColor = "#00cc44";
              alertBox.style.color="#fff";
              setTimeout(async ()=>{
                console.log('change color');
                alertIcon.innerHTML="";
                alertBox.style.backgroundColor="#e9ecef";
                alertBox.style.color="#e9ecef";
                window.location.href = "DashBoard.jsp"; // Redirect to dashboard
              },200);
            }
            else {
              alertText.innerHTML="Login Failed";
              alertIcon.innerHTML=" <i class='fa fa-close' style='font-size:18px;color:#fff'></i>";
              alertBox.style.backgroundColor = "#f11";
              alertBox.style.color="#fff";
              setTimeout(async ()=>{
                console.log('change color');
                alertIcon.innerHTML="";
                alertBox.style.backgroundColor="#e9ecef";
                alertBox.style.color="#e9ecef";
              },5000);
            }
        }
      })
    }
  </script>
  <!-- /.login-box -->
</div>
<!-- jQuery -->
<script src="jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="adminlte.min.js"></script>
</body>
</html>

