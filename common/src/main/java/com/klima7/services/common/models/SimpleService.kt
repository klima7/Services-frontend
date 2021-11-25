package com.klima7.services.common.models

open class SimpleService(
    val id: String,
    val categoryId: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleService

        if (id != other.id) return false
        if (categoryId != other.categoryId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + categoryId.hashCode()
        return result
    }

    override fun toString(): String {
        return "SimpleService(id='$id', categoryId='$categoryId')"
    }

}