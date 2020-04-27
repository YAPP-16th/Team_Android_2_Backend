package com.teamplay.core.database

import java.util.*
import kotlin.reflect.KClass

interface DataAccessor<Key, Entity> {
    fun <T : Any> delete(entity: T, type: KClass<T>)

    fun delete(entity: Entity)

    fun deleteById(id: Key)

    fun deleteAllByIdIn(ids: Collection<Key>)

    fun findById(id: Key): Optional<Entity>

    fun <T : Any> findById(id: Key, type: KClass<T>): Optional<T>

    fun findAll(): Collection<Entity>

    fun <T : Any> findAllBy(type: KClass<T>): Collection<T>

    fun <T : Any> findAllByIdIn(ids: Collection<Int>, type: KClass<T>): Collection<T>

    fun getById(id: Key): Entity

    fun <T : Any> save(entity: T, type: KClass<T>): Entity

    fun save(entity: Entity): Entity

    fun <T : Any> saveAll(entities: Collection<T>, type: KClass<T>): Collection<Entity>

    fun saveAll(entities: Collection<Entity>): Collection<Entity>

    fun existsById(id: Key): Boolean
}