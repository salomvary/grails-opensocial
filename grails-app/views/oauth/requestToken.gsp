<html>
  <head>
    <title></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript">
      window.open('${authorizationUrl}','authorize')
    </script>
  </head>
  <body>
    <g:form action="authorized">
      <fieldset>
        <legend>Authorize</legend>
        <ol>
          <li>
            <a href="${authorizationUrl}" target="_blank">Click here to authorize</a>
          </li>
          <li>
            <label>Enter verifier code:<g:textField name="oauth_verifier"/></label>    
          </li>
          <li>
            <g:submitButton name="submit" value="submit"/>
          </li>
        </ol>
      </fieldset>
    </g:form>
  </body>
</html>

