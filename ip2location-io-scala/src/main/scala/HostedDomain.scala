package com.ip2location

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.{URI, URLEncoder}

import com.google.gson.{JsonObject, JsonParser}

/**
 * This class performs the lookup of hosted domains data from an IP address by querying the IP2Location.io API.
 * <p>
 * Copyright (c) 2002-2025 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 1.1.0 */
class HostedDomain(Config: Configuration) {
  private val BASE_URL = "https://domains.ip2whois.com/domains"
  private val SOURCE = "sdk-scala-iplio"
  private val FORMAT = "json"
  private val ERROR = "HostedDomain lookup error."

  /**
   * This function to query IP2Location.io hosted domains data.
   *
   * @param ip IP Address you wish to query
   * @return IP2Location.io hosted domains data
   * @throws Exception If parameters are incorrect or API call failed. */
  @throws[Exception] def lookUp(ip: String): JsonObject = lookUp(ip, 1)

  /**
   * This function to query IP2Location.io hosted domains data.
   *
   * @param ip       IP Address you wish to query
   * @param page The page of the result
   * @return IP2Location.io hosted domains data
   * @throws Exception If parameters are incorrect or API call failed. */
  @throws[Exception] def lookUp(ip: String, page: Int): JsonObject = {
    val url = BASE_URL + "?format=" + FORMAT + "&source=" + SOURCE + "&source_version=" + Config.getVersion + "&key=" + Config.getApiKey + "&ip=" + URLEncoder.encode(ip, "UTF-8") + "&page=" + page
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