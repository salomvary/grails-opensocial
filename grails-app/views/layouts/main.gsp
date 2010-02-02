<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />				
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>	
        <div class="logo"><img src="${resource(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div>
        <div class="login">
          <g:if test="${session.user}">
            Logged in as: ${session.user.name.encodeAsHTML()}
            <g:link controller="user" action="logout">Logout</g:link>
          </g:if>
          <g:else>
            <g:link controller="user" action="login">Sign in with twitter</g:link>
          </g:else>
        </div>
        <g:layoutBody />		
    </body>	
</html>
