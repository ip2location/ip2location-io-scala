package com.ip2location

object FullTest {
  def main(args: Array[String]): Unit = {
    try {
      // Configures IP2Location.io API key
      val strApiKey = "YOUR_API_KEY"

      val config = new Configuration()
      config.setApiKey(strApiKey)

      // Lookup ip address geolocation data
      val strIPAddress = "8.8.8.8"
      val lang = "es"
      val ipl = new IPGeolocation(config)
      val myResult = ipl.lookUp(strIPAddress, lang) // lang param only supported in Plus and Security plans, so omit if not necessary
      System.out.println(myResult)

      // Lookup domain information
      val strDomain = "locaproxy.com"
      val whois = new DomainWhois(config)
      val myResult2 = whois.lookUp(strDomain)
      System.out.println(myResult2)

      // Convert normal text to punycode
      System.out.println(whois.toPunycode("täst.de"))

      // Convert punycode to normal text
      System.out.println(whois.toNormalText("xn--tst-qla.de"))

      // Get domain name from URL
      System.out.println(whois.toDomainName("https://www.example.com/exe"))

      // Get domain extension (gTLD or ccTLD) from URL or domain name
      System.out.println(whois.toDomainExtension("example.com"))

      // Lookup ip address hosted domains data
      val strIPAddress2 = "8.8.8.8"
      val page = 1
      val hd = new HostedDomain(config)
      val myResult3 = hd.lookUp(strIPAddress2, page)
      System.out.println(myResult3)

    } catch {
      case e: Exception => System.out.println(e)
        e.printStackTrace(System.out)
    }
  }
}
