<!doctype html>
<html>
<head>
<title>User Info</title>
<meta name = "description" content="User data page">
    <style>
    table {
    border-spacing: 20px;
    }
    </style>
</head>

<body>
<h1>User Data</h1>

<div>
<p><strong>Name: </strong> ${user.name}</p>
<p><strong>Surname: </strong> ${user.surname}</p>
<p><strong>Phone: </strong> ${user.phone}</p>
<p><strong>Email: </strong> ${user.email}</p>
</div>

<div>
<table>

<br><br>

<p><strong>User Experiences</strong></p>
  <tr>
    <th>Title</th>
    <th>Description</th>
  </tr>
   <#list experiences as experience>
  <tr>
    <td>${experience.title}</td>
    <td>${experience.description}</td>
  </tr>
  </#list>
</table>
</div>

</body>
</html>

