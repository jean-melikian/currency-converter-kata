package fr.ozoneprojects.currencyconverter.repository

import java.math.BigDecimal

data class Currency(val currencyCode: String, val factor: BigDecimal)