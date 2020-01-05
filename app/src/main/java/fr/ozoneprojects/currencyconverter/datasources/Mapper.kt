package fr.ozoneprojects.currencyconverter.datasources

interface Mapper<E, M> {
    fun toModel(entity: E): M
}