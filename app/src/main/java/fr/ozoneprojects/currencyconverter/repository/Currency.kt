package fr.ozoneprojects.currencyconverter.repository

import java.math.BigDecimal
import java.util.*

data class Currency(val currencyCode: String, val factor: BigDecimal, val date: Date? = null)