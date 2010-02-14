<html>
  <head>
    <title></title>
    <meta name="layout" content="main"/>
  </head>
  <body>
    <g:form action="requestToken">
      <fieldset>
        <legend>Obtain a token</legend>
        <p>
          <label>
            Provider:
            <g:select name="provider" from="${providers}"
              optionKey="id"/>
            <g:submitButton name="request token" value="request"/>
            <g:submitButton name="login" value="login"/>
          </label>
        </p>
        <p>
          OAuth version:
          <label><g:radio name="version" value="1.0"/>1.0</label>
          <label><g:radio name="version" value="1.0a" chhecked="${true}"/>1.0a</label>
          <label><g:checkBox name="oob"/>Out of band</label>
        </p>
      </fieldset>
    </g:form>
    <g:form> 
      <fieldset>
        <legend>Request</legend>
        <fieldset>
          <legend>3 legged OAuth</legend>
            <label>Choose token: <g:select name="token" from="${tokens}" optionKey="id"/></label>
        </fieldset>
        <fieldset>
          <legend>2 legged OAuth</legend>
            <label>
              Provider: 
              <g:select name="provider" from="${providers}"
              optionKey="id"/>
            </label>
            <label>
              Requestor id:
              <g:textField name="requestor_id"/>
              or choose:
              <g:select name="token" from="${tokens}" optionKey="${userId}"/>
            </label>
        </fieldset>
        <p>
          <label>Service: <g:textField name="service"/></label>
        </p>
        <p>
          <label>Method: <g:select name="method" from="${['GET','POST']}"/>
        </p>
        <p>
          <label>Content type:<g:textField name="contentType"/></label>
        </p>
        <p>
          <label>Body:<br/>
            <g:textArea name="body"/>
          </label>
        </p>
        <p>
          <g:submitButton name="submit" value="submit"/>
        </p>
      </fieldset>
    </g:form>
  </body>
</html>
