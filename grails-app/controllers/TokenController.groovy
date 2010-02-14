import net.oauth.*
import net.oauth.http.*
import net.oauth.client.*
import net.oauth.client.httpclient4.*

class UserController {
  def scaffold = User
  def oauthService

  def logout = {
    session.user = null
    redirect(uri:'') 
  }

  def login = {
    //new accessor
    OAuthAccessor accessor = new OAuthAccessor(oauthService.consumer)

    //request
    oauthService.client.getRequestToken(accessor)  
    log.debug "Got request token: "+accessor.requestToken

    //authorize
    def authorizeMessage = accessor.newRequestMessage(
      "GET",
      accessor.consumer.serviceProvider.userAuthorizationURL,
      [
        new OAuth.Parameter('oauth_token', accessor.requestToken),
        //new OAuth.Parameter('oauth_callback', 'http://localhost:8080/grails-opensocial/user/authorized')
      ]
    )
    def httpMessage = authorizeMessage.toHttpRequest(OAuthClient.ParameterStyle.QUERY_STRING)
    session.accessor = accessor
    redirect(url: httpMessage.url)
  }
    
  def authorized = {
    OAuthAccessor accessor = session.accessor
    if(! accessor) {
      render text: "no accessor in session" 
      return
    }
    //response stuff
    def verifier = params['oauth_verifier']
    def token = params['oauth_token']

    //access
    def accessMessage = oauthService.client.getAccessToken(
      accessor, "GET", 
      (token == null) ? null : OAuth.newList(OAuth.OAUTH_VERIFIER, verifier)
    )

    log.debug "Got message "+accessMessage

    //try to find user
    User user = User.findByUserId(accessMessage.getParameter('user_id'))
    if(! user) {
      log.debug('creating user')
      //create if doesn't exist yet
      user = new User(
        userId: accessMessage.getParameter('user_id')
      );
    } else {
      log.debug( (user.accessToken == accessMessage.getParameter('oauth_token') ? 'keeping' : 'replacing') + ' access token')
      log.debug( (user.tokenSecret == accessMessage.getParameter('oauth_token_secret') ? 'keeping' : 'replacing') + ' access token secret')
    }
    user.accessToken = accessMessage.getParameter('oauth_token')
    user.tokenSecret = accessMessage.getParameter('oauth_token_secret')
    user.name = accessMessage.getParameter('screen_name')
    user.save()

    session.user = user
    
    log.debug "Got accessToken: "+user.accessToken
    log.debug "    tokenSecret: "+user.tokenSecret
    log.debug "    userId: "+user.userId
    log.debug "    screenName: "+user.name

    redirect action: 'show', id: user.id
  }
}
