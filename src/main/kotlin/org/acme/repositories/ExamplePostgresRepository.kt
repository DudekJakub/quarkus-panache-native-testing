package org.acme.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.acme.models.MyPostgresEntity

@ApplicationScoped
class ExamplePostgresRepository : PanacheRepository<MyPostgresEntity>