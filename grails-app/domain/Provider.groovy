class Provider {

    static constraints = {
      name(blank: false)
      requestTokenUrl(blank: false)
      accessTokenUrl(blank: false) 
      consumerKey(blank: false)
    }

    String name
    String authorizeUrl
    String requestTokenUrl
    String accessTokenUrl
    String restEndpoint
    String consumerKey
    String consumerSecret
    String rsaPublicSignature
    boolean isOpenSocial

    String toString() {
      return name
    }
}
