package com.ribbontek.sesdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SesDemoApplication

fun main(args: Array<String>) {
    runApplication<SesDemoApplication>(*args)
}
