package com.ip2location

import com.google.gson._

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.{URI, URLEncoder}

/**
 * This class performs the lookup of geolocation data from an IP address by querying the IP2Location.io API.
 * <p>
 * Copyright (c) 2002-2023 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 1.0.0 */
class IPGeolocation(Config: Configuration) {
  private val BASE_URL = "https://api.ip2location.io/"
  private val SOURCE = "sdk-scala-iplio"
  private val FORMAT = "json"
  private val ERROR = "IPGeolocation lookup error."

  /**
   * This function to query IP2Location.io geolocation data.
   *
   * @param ip IP Address you wish to query
   * @return IP2Location.io geolocation data
   * @throws Exception If parameters are incorrect or API call failed. */
  @throws[Exception] def lookUp(ip: String): JsonObject = lookUp(ip, "")

  /**
   * This function to query IP2Location.io geolocation data.
   *
   * @param ip       IP Address you wish to query
   * @param language The translation language
   * @return IP2Location.io geolocation data
   * @throws Exception If parameters are incorrect or API call failed. */
  @throws[Exception] def lookUp(ip: String, language: String): JsonObject = {
    val url = BASE_URL + "?format=" + FORMAT + "&source=" + SOURCE + "&source_version=" + Config.getVersion + "&key=" + Config.getApiKey + "&ip=" + URLEncoder.encode(ip, "UTF-8") + "&lang=" + URLEncoder.encode(language, "UTF-8")
    val request = HttpRequest.newBuilder.uri(new URI(url)).GET.build
    val client = HttpClient.newHttpClient
    val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString).join()
    val statusCode = response.statusCode()
    if (statusCode == 200) {
      val rawJSON = response.body()
      return JsonParser.parseString(rawJSON).getAsJsonObject
    } else if (statusCode == 400 || statusCode == 401) {
      val rawJSON = response.body()
      if (rawJSON.contains("error_message")) throw new Exception(JsonParser.parseString(rawJSON).getAsJsonObject.getAsJsonObject("error").get("error_message").getAsString)
    }
    throw new Exception(ERROR)
  }
}