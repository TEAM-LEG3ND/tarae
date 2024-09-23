package org.team_leg3nd.tarae

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class TaraeApplication

fun main(args: Array<String>) {
    runApplication<TaraeApplication>(*args)
}
