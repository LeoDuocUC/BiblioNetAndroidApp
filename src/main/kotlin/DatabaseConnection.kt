package com.example

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties
import java.nio.file.Paths

object DatabaseConnection {

    private val walletUri = javaClass.classLoader.getResource("wallet")?.toURI()
    private val walletPath = walletUri?.let { Paths.get(it).toString() }

    fun connect(): Connection {
        
        System.setProperty("oracle.net.TNS_ADMIN", walletPath)
        System.setProperty("oracle.net.wallet_location", walletPath)

        val props = Properties()
        props.setProperty("user", "admin")
        props.setProperty("password", "Castlevania#2025") 

        val fullConnectionString = "(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1522)(host=adb.sa-santiago-1.oraclecloud.com))(connect_data=(service_name=g464f66c67d0b60_duocdb_medium.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))"
        val jdbcUrl = "jdbc:oracle:thin:@$fullConnectionString" 

        Class.forName("oracle.jdbc.driver.OracleDriver")
        return DriverManager.getConnection(jdbcUrl, props)
    }
}