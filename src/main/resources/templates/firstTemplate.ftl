<!doctype html>
<html>
<head>
<title>${title}</title>
    <meta name = "description" content="User data page">
    <meta name="keywords" content="html to pdf sample">
</head>

<body>
<h1>${title}</h1>
<p>${exampleObject.name} by ${exampleObject.developer}</p>

  <ul>
    <#list systems as system>
      <li>${system_index + 1}. ${system.name} from ${system.developer}</li>
    </#list>
  </ul>
</body>

</html>

