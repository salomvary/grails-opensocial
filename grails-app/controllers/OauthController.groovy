import net.oauth.*
import net.oauth.http.*
import net.oauth.client.*
import net.oauth.client.httpclient4.*

class OauthController {
  def oauthService

  def index = {
    [providers: Provider.list(), tokens: Token.list()]
  }

  def requestToken = {
    def callbackUrl = createLink(controller: 'oauth', action:'authorized', absolute: true)
    //new accessor
    OAuthAccessor accessor = oauthService.getAccessor(params['provider'])

    //request
    oauthService.getRequestToken(
      accessor, 
      params['version'] == '1.0' ? null : params['oob'] ? 'oob' : callbackUrl
    )
    log.debug "Got request token: "+accessor.requestToken

    //store accessor in session
    session.accessor = accessor

    //authorize
    String authorizationUrl = oauthService.getAuthorizationUrl(accessor, callbackUrl)
    log.debug "redirecting to ${authorizationUrl}"
    if(! params.oob) {
      redirect(url: authorizationUrl)
    } else {
      [authorizationUrl: authorizationUrl]
    }
  }

  def authorized = {
    OAuthAccessor accessor = session.accessor
    if(! accessor) {
      render text: "no accessor in session" 
      return
    }

    //response stuff
    def oauth_token = params['oauth_token'] //not used at the moment since we have the accessor in session
    def verifier = params['oauth_verifier']
    log.debug "Got verifier: "+verifier

    //access
    def accessMessage = oauthService.client.getAccessToken(
      accessor, "GET", 
      (verifier == null) ? null : OAuth.newList(OAuth.OAUTH_VERIFIER, verifier)
    )
    log.debug "Got access message "+accessMessage

    //try to find existing access token
    Token token = Token.findByAccessToken(accessMessage.getParameter('oauth_token'))
    if(! token) {
      log.debug('creating access token')
      token = new Token(
         accessToken: accessMessage.getParameter('oauth_token'),
         provider: Provider.findByConsumerKey(accessor.consumer.consumerKey)
      )
    } else {
      log.debug('access token already exists')
      log.debug( (token.tokenSecret == accessMessage.getParameter('oauth_token_secret') ? 'keeping' : 'replacing') + ' access token secret')
    }
    token.tokenSecret = accessMessage.getParameter('oauth_token_secret')
    token.userId = accessMessage.getParameter('user_id')
    token.userName = accessMessage.getParameter('screen_name')
    token.save()
    if(token.errors) {
      log.error token.errors
    }

    //session.user = user
    log.debug "Got accessToken: "+token.accessToken
    log.debug "    tokenSecret: "+token.tokenSecret
    log.debug "    tokenId: "+token.userId
    log.debug "    screenName: "+token.userName

    redirect controller: 'token', action: 'show', id: token.id
  }

  def logout = {
    session.user = null
    redirect(uri:'') 
  }

    

}
