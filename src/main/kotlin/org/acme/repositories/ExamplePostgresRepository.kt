package org.acme.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.acme.models.MyKotlinEntity

@ApplicationScoped
class ExamplePostgresRepository : PanacheRepository<MyKotlinEntity>