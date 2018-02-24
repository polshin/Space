package space

import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.IntStream

class CurrencyController   {

    def currencyService

    def currency() {
        def allData = currencyService.getByMonthMaps()
        [currencyService: currencyService,allData: allData]
    }
}