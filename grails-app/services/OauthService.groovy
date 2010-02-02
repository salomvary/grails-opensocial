import net.oauth.*
import net.oauth.http.*
import net.oauth.client.*
import net.oauth.client.httpclient4.*
import org.springframework.beans.factory.InitializingBean

class OauthService implements InitializingBean {
  OAuthServiceProvider serviceProvider
  OAuthConsumer consumer
  OAuthServiceProvider provider
  OAuthClient client = new OAuthClient(new HttpClient4())
  def grailsApplication
  static transactional = false

  void afterPropertiesSet() {
    client.httpParameters.disableExpectContinue = true
    def props = [
      consumerKey:"your key",
      consumerSecret:"your secret",
      requestUrl:"http://twitter.com/oauth/request_token",
      accessUrl:"http://twitter.com/oauth/access_token",
      authorizationUrl:"http://twitter.com/oauth/authenticate"
    ]

    provider = new OAuthServiceProvider(
      props.requestUrl, 
      props.authorizationUrl, 
      props.accessUrl
    )

    consumer = new OAuthConsumer(
      null, 
      props.consumerKey, 
      props.consumerSecret,
      provider
    )
  }

}
