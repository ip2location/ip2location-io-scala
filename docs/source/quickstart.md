# Quickstart

## Pre-requisite

IntelliJ IDEA

## Sample Codes

### Lookup IP Address Geolocation Data
```scala
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
```

### Lookup Domain Information
```scala
// Configures IP2Location.io API key
val strApiKey = "YOUR_API_KEY"
val config = new Configuration()
config.setApiKey(strApiKey)

// Lookup domain information
val strDomain = "locaproxy.com"
val whois = new DomainWhois(config)
val myResult = whois.lookUp(strDomain)
System.out.println(myResult)
```

### Convert Normal Text to Punycode
```scala
val config = new Configuration()
val whois = new DomainWhois(config)

// Convert normal text to punycode
System.out.println(whois.toPunycode("täst.de"))
```

### Convert Punycode to Normal Text
```scala
val config = new Configuration()
val whois = new DomainWhois(config)

// Convert punycode to normal text
System.out.println(whois.toNormalText("xn--tst-qla.de"))
```

### Get Domain Name
```scala
val config = new Configuration()
val whois = new DomainWhois(config)

// Get domain name from URL
System.out.println(whois.toDomainName("https://www.example.com/exe"))
```

### Get Domain Extension
```scala
val config = new Configuration()
val whois = new DomainWhois(config)

// Get domain extension (gTLD or ccTLD) from URL or domain name
System.out.println(whois.toDomainExtension("example.com"))
```

### Lookup IP Address Hosted Domains Data
```scala
// Configures IP2Location.io API key
val strApiKey = "YOUR_API_KEY"
val config = new Configuration()
config.setApiKey(strApiKey)

// Lookup ip address hosted domains data
val strIPAddress = "8.8.8.8"
val page = 1
val hd = new HostedDomain(config)
val myResult = hd.lookUp(strIPAddress, page)
System.out.println(myResult)
```