package org.acme.models

import org.hibernate.proxy.HibernateProxy

abstract class BaseEntity<ID> {

    open val id: ID? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass = if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        val entity = other as BaseEntity<*>
        return id != null && id == entity.id
    }

    override fun hashCode(): Int {
        return if (this is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()
    }

    abstract override fun toString(): String
}
