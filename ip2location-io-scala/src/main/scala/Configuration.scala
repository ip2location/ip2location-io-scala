package com.ip2location

/**
 * This class gets and sets the IP2Location.io API key that's being used.
 * <p>
 * Copyright (c) 2002-2025 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 1.1.0 */
object Configuration {
  private val VERSION = "1.1.0"
}

class Configuration {
  private var apiKey = ""

  def getVersion: String = Configuration.VERSION

  def setApiKey(key: String): Unit = {
    this.apiKey = key
  }

  def getApiKey: String = this.apiKey
}