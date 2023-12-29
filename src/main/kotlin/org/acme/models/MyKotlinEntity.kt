package org.acme.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator

/**
 * Example JPA entity defined as a Kotlin Panache Entity.
 * An ID field of Long type is provided, if you want to define your own ID field extends <code>PanacheEntityBase</code> instead.
 *
 * This uses the active record pattern, you can also use the repository pattern instead:
 * .
 *
 * Usage (more example on the documentation)
 *
 * {@code
 *
 *      fun doSomething() {
 *          val entity1 = MyKotlinEntity();
 *          entity1.field = "field-1"
 *          entity1.persist()
 *
 *         val entities:List<MyKotlinEntity>  = MyKotlinEntity.listAll()
 *     }
 * }
 */
@Entity
class MyKotlinEntity(

    @Column
    var firstname: String,

    @Column
    var surname: String

) : BaseEntity<Long>() {

    @Id
    @SequenceGenerator(name = "example_sq", sequenceName = "example_sq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "example_sq")
    override val id: Long? = null

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstname = $firstname, surname = $surname)"
    }
}

