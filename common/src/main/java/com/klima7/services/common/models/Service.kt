package com.klima7.services.common.models

class Service(
    id: String,
    categoryId: String,
    val name: String,
    val constrained: Boolean,
): SimpleService(id, categoryId) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Service

        if (name != other.name) return false
        if (constrained != other.constrained) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + constrained.hashCode()
        return result
    }

    override fun toString(): String {
        return "Service(name='$name', constrained=$constrained)"
    }

}
