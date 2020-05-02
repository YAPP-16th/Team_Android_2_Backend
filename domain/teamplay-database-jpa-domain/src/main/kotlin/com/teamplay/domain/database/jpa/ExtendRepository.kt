package com.teamplay.domain.database.jpa

import com.teamplay.core.database.EntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.io.Serializable
import java.util.*
import javax.transaction.Transactional
import java.util.*
import javax.persistence.Entity
import javax.swing.text.html.Option

@NoRepositoryBean
interface ExtendedRepository<T : EntityId> : JpaRepository<T, Int> {

    @Transactional
    @Modifying
    @Query("delete from #{#entityName} entity where entity.id in :ids")
    fun deleteAllByIdIn(@Param("ids") ids: Collection<Int>)

    @Transactional
    @Modifying
    @Query("delete from #{#entityName} entity where entity.id = :id")
    override fun deleteById(@Param("id") id: Int)

    fun <T> findById(id: Int, type: Class<T>): Optional<T>

    fun <T> findAllBy(type: Class<T>): List<T>

    fun <T> findAllByIdIn(ids: Collection<Int>, type: Class<T>): List<T>
}