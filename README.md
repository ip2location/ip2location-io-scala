# IP2Location.io Scala SDK — IP Geolocation, Domain WHOIS & Hosted Domain Lookup

**IP2Location.io Scala SDK** is a Scala 3 library for retrieving **IPv4 and IPv6 geolocation, network, and IP intelligence data** through the [IP2Location.io API](https://www.ip2location.io/). It also supports **domain WHOIS lookup**, domain-name and Punycode utilities, and **hosted-domain lookup**. Use the SDK's Scala classes to query these services without manually constructing HTTP requests.

**Quick links:** [Get an API key](https://www.ip2location.io/pricing) · [Developer documentation](https://ip2location-io-scala.readthedocs.io/en/latest/index.html) · [Scala API reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html) · [Maven Central package](https://central.sonatype.com/artifact/com.ip2location/ip2location-io-scala_3)

## Features

- **IPv4 and IPv6 geolocation:** Look up country, region, district, city, latitude, longitude, postal code, and other location fields.
- **Network and ASN information:** Retrieve available ASN, AS name, ISP, associated domain, connection speed, usage type, and related attributes.
- **Time zone and localization data:** Access available time zone and daylight saving information; request translated continent, country, region, and city names on eligible Plus and Security plans.
- **Proxy and security signals:** Access plan-dependent proxy, VPN, Tor, data center, web crawler, AI crawler, and other indicators, including available fraud scores.
- **Additional IP intelligence:** Access eligible mobile carrier (MCC/MNC), country, weather station, elevation, and advertising category data.
- **Domain WHOIS lookup:** Retrieve available domain registration dates, registrar, nameservers, domain status, and contact information.
- **Internationalized domain and URL utilities:** Convert Unicode domains to/from Punycode, extract a domain from a URL, and obtain a domain extension.
- **Hosted domains:** Retrieve a paginated list of domains hosted on an IPv4 or IPv6 address.

> **Plan availability:** Available fields and security signals vary by API plan and by queried address or domain. Check the [pricing page](https://www.ip2location.io/pricing) and [Scala API field reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html) before relying on optional values.

## SDK classes

The [Scala SDK API reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html) documents these classes and methods:

| Class / method | Description |
| --- | --- |
| `new Configuration()` | Creates an SDK configuration object. |
| `config.setApiKey(apiKey)` | Sets the API key for subsequent lookups. |
| `new IPGeolocation(config)` | Creates a geolocation lookup client. |
| `geo.lookUp(ipAddress, lang)` | Retrieves IP geolocation and available network/security information; `lang` is optional. |
| `new DomainWhois(config)` | Creates a domain WHOIS client. |
| `whois.lookUp(domain)` | Retrieves WHOIS information for a domain. |
| `whois.toPunycode(domain)` | Converts a Unicode domain to Punycode. |
| `whois.toNormalText(domain)` | Converts a Punycode domain to normal text. |
| `whois.toDomainName(url)` | Extracts the domain name from a URL. |
| `whois.toDomainExtension(domainOrUrl)` | Extracts the domain extension from a domain or URL. |
| `new HostedDomain(config)` | Creates a hosted-domain lookup client. |
| `hosted.lookUp(ipAddress, page)` | Retrieves hosted domains for an IP address, with optional pagination. |

The SDK documentation shows lookup results printed as JSON. The response fields for each service are documented in the [API reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html).

## API access and query limits

IP2Location.io provides keyless access to limited IP geolocation requests through its REST API and a Free plan with an API key:

| API service | Keyless (no API key) | Free plan (with API key) |
| --- | --- | --- |
| IP Geolocation API | **1,000 queries/day** | **50,000 queries/month** |
| Domain WHOIS API | — | **500 queries/month** |
| Hosted Domain API | — | **50 queries/month** |

- **Keyless access:** No registration is required for the limited [IP Geolocation REST API](https://www.ip2location.io/ip2location-documentation). The daily quota resets at **00:00 UTC**. The Scala SDK itself requires an API key; keyless lookup is not a documented Scala SDK mode.
- **Free plan:** Register for an API key to use the published monthly allowances, subject to the relevant quotas.
- **Attribution:** Keyless and Free usage require attribution; refer to the [pricing page](https://www.ip2location.io/pricing) for applicable conditions.
- **Paid plans:** Refer to the [pricing page](https://www.ip2location.io/pricing) for current limits, pricing, and features.

For details about authentication and rate limiting, see the [IP Geolocation API documentation](https://www.ip2location.io/ip2location-documentation).

## Requirements

- **Scala 3**. The repository's [`build.sbt`](https://github.com/ip2location/ip2location-io-scala/blob/main/build.sbt) uses Scala **3.3.1**.
- A compatible **Java Development Kit (JDK)** and [sbt](https://www.scala-sbt.org/) for building and running Scala projects.
- **IntelliJ IDEA**, recommended in the [official Scala quickstart](https://ip2location-io-scala.readthedocs.io/en/latest/quickstart.html), if using an IDE.
- An [IP2Location.io API key](https://www.ip2location.io/pricing) for SDK lookups.
- Network access to the API services.

The published library uses Gson to handle JSON. In an sbt project, transitive dependencies are resolved automatically.

## Installation

### 1. Add the Scala SDK to your project

The SDK is published on [Maven Central](https://central.sonatype.com/artifact/com.ip2location/ip2location-io-scala_3). Add this dependency to your project's `build.sbt`:

```scala
libraryDependencies += "com.ip2location" %% "ip2location-io-scala" % "1.2.0"
```

The `%%` notation uses the Scala 3 artifact suffix (`_3`). Version `1.2.0` matches the repository's current `build.sbt` and its published Maven Central artifact. This project uses Scala 3; in a new project, set `scalaVersion` appropriately, for example:

```scala
scalaVersion := "3.3.1"
```

From the project directory, run `sbt update` to resolve the library. Alternatively, use IntelliJ IDEA with an sbt-based Scala 3 project.

### 2. Set your API key

Store the API key in an environment variable instead of committing it to your source code.

**Linux / macOS terminal:**

```bash
export IP2LOCATION_API_KEY='YOUR_API_KEY'
```

**Windows Command Prompt:**

```bat
set IP2LOCATION_API_KEY=YOUR_API_KEY
```

**Windows PowerShell:**

```powershell
$env:IP2LOCATION_API_KEY = 'YOUR_API_KEY'
```

The examples below use `sys.env.getOrElse` to read this environment variable. The official quickstart demonstrates the same `Configuration` and `setApiKey()` calls with a literal API-key placeholder.

## Quickstart: IP geolocation lookup

Create `src/main/scala/Lookup.scala` in your Scala 3 sbt project:

```scala
import com.ip2location.{Configuration, IPGeolocation}

@main def lookup(): Unit =
  val apiKey = sys.env.getOrElse("IP2LOCATION_API_KEY",
    throw new IllegalArgumentException("Set IP2LOCATION_API_KEY."))

  val config = new Configuration()
  config.setApiKey(apiKey)

  val geo = new IPGeolocation(config)
  val result = geo.lookUp("8.8.8.8")
  println(result)
```

Run the example from the project directory:

```bash
sbt run
```

The official [Scala quickstart](https://ip2location-io-scala.readthedocs.io/en/latest/quickstart.html) demonstrates `lookUp("8.8.8.8", "es")` for translated location names; the language parameter is optional and is omitted here for a basic lookup. Returned JSON fields depend on the API plan and queried IP address. See the [geolocation field reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html#ipgeolocation-class).

## More usage examples

The following examples show individual lookups as `@main` programs. They assume that the SDK dependency is installed and that `IP2LOCATION_API_KEY` has been set as described above. Save each example in its own Scala source file (or adapt the snippet within your existing application).

### Request translated location names

The optional `lang` argument accepts an ISO 639-1 language code. Translated continent, country, region, and city names are supported on **Plus and Security** plans.

```scala
import com.ip2location.{Configuration, IPGeolocation}

@main def translatedLookup(): Unit =
  val config = new Configuration()
  config.setApiKey(sys.env("IP2LOCATION_API_KEY"))
  val geo = new IPGeolocation(config)
  val result = geo.lookUp("8.8.8.8", "es")
  println(result)
```

### Inspect available network and ASN information

The geolocation response can include `asn`, `as`, `isp`, `domain`, and the nested `as_info` object, depending on your plan. The following is an adaptation of the official geolocation lookup that prints the complete result for inspection:

```scala
import com.ip2location.{Configuration, IPGeolocation}

@main def networkLookup(): Unit =
  val config = new Configuration()
  config.setApiKey(sys.env("IP2LOCATION_API_KEY"))
  val geo = new IPGeolocation(config)
  val result = geo.lookUp("8.8.8.8")
  println(result)
```

Refer to the [Scala API response fields](https://ip2location-io-scala.readthedocs.io/en/latest/code.html#ipgeolocation-class) for network attributes returned by your plan.

### Inspect available proxy indicators

On eligible plans, the geolocation response contains security fields such as `is_proxy`, `fraud_score`, and an optional `proxy` object with indicators including `is_vpn`, `is_tor`, and `is_data_center`. The code below uses the documented lookup and prints its JSON response; inspect the presence of relevant fields before using them in application decisions.

```scala
import com.ip2location.{Configuration, IPGeolocation}

@main def proxyLookup(): Unit =
  val config = new Configuration()
  config.setApiKey(sys.env("IP2LOCATION_API_KEY"))
  val geo = new IPGeolocation(config)
  val result = geo.lookUp("8.8.8.8")
  println(result)
```

Security signals are supporting context, not proof of malicious behavior.

### Look up domain WHOIS information

Use `DomainWhois.lookUp()` to retrieve available domain registration details. Domain contact records may be missing or redacted.

```scala
import com.ip2location.{Configuration, DomainWhois}

@main def whoisLookup(): Unit =
  val config = new Configuration()
  config.setApiKey(sys.env("IP2LOCATION_API_KEY"))

  val whois = new DomainWhois(config)
  val result = whois.lookUp("locaproxy.com")
  println(result)
```

The WHOIS response can include registration and expiration dates, status, registrar details, nameservers, and available contacts. See the [DomainWhois class reference](https://ip2location-io-scala.readthedocs.io/en/latest/code.html#domainwhois-class).

### Use domain and Punycode utilities

The `DomainWhois` class exposes the following helpers in the official Read the Docs examples:

```scala
import com.ip2location.{Configuration, DomainWhois}

@main def domainUtilities(): Unit =
  val config = new Configuration()
  val whois = new DomainWhois(config)

  println(whois.toPunycode("täst.de"))
  println(whois.toNormalText("xn--tst-qla.de"))
  println(whois.toDomainName("https://www.example.com/exe"))
  println(whois.toDomainExtension("example.com"))
```

These utility methods do not require a WHOIS API lookup in the documented examples.

### Find domains hosted on an IP address

Use `HostedDomain.lookUp(ipAddress, page)` to list domains hosted on an IP address. The Read the Docs quickstart supplies page `1` explicitly.

```scala
import com.ip2location.{Configuration, HostedDomain}

@main def hostedDomainLookup(): Unit =
  val config = new Configuration()
  config.setApiKey(sys.env("IP2LOCATION_API_KEY"))

  val hosted = new HostedDomain(config)
  val result = hosted.lookUp("8.8.8.8", 1)
  println(result)
```

Request additional pages with `2`, `3`, and so on, when the response indicates more pages. Documented response fields include `total_domains`, `page`, `per_page`, `total_pages`, and `domains`.

## Scala project and dependency troubleshooting

- **Cannot resolve the SDK artifact:** Confirm that your project uses Scala 3 and the dependency coordinates `"com.ip2location" %% "ip2location-io-scala" % "1.2.0"`. The published Maven artifact is named `ip2location-io-scala_3`.
- **Import or class not found:** Ensure that sbt has loaded the project dependencies, and import from `com.ip2location`, as shown in the official quickstart.
- **API key errors:** Verify that `IP2LOCATION_API_KEY` is available to the process running sbt or IntelliJ IDEA. Environment variables set after the IDE starts may not be inherited until it is restarted.
- **Missing response fields:** Check API plan eligibility and the specific lookup result. Optional network, translation, and security fields are not guaranteed for every query.
- **Multiple `@main` examples:** Keep examples in separate files or choose the desired entry point when your project defines more than one runnable main method.

## Common use cases

- **Application and website localization:** Suggest region-appropriate content, language, or currency using approximate IP location.
- **Traffic analytics:** Enrich server or application events with country, region, ASN, and ISP information.
- **Security review:** Add available proxy, VPN, Tor, or crawler indicators to a broader risk analysis instead of relying on any single signal.
- **Domain research:** Retrieve available registrar details, registration dates, statuses, and nameservers.
- **Network and hosting research:** Investigate associated network attributes and domains hosted on an IP address.

## Documentation and support

- [Scala SDK quickstart and official sample codes](https://ip2location-io-scala.readthedocs.io/en/latest/quickstart.html)
- [Scala SDK API reference and response fields](https://ip2location-io-scala.readthedocs.io/en/latest/code.html)
- [Official GitHub repository](https://github.com/ip2location/ip2location-io-scala)
- [Maven Central package](https://central.sonatype.com/artifact/com.ip2location/ip2location-io-scala_3)
- [IP2Location.io API plans and query limits](https://www.ip2location.io/pricing)
- [IP Geolocation API authentication and rate limits](https://www.ip2location.io/ip2location-documentation)
- [Report a Scala SDK issue or request a feature](https://github.com/ip2location/ip2location-io-scala/issues)
- [API and account support](https://www.ip2location.io/contact-us)

## License

Licensed under the **MIT License**. See [LICENSE](./LICENSE).
