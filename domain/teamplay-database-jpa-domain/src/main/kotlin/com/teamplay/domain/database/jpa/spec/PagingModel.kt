package com.teamplay.domain.database.jpa.spec

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

abstract class PagingModel {
    abstract val descending: Boolean
    abstract val page: Int
    abstract val rowsPerPage: Int
    abstract val sortBy: String?
    abstract val isAll: Boolean

    fun createPageRequest(): Pageable {
        return PageRequest.of(
                if (this.isAll) 0 else this.page - 1,
                if (this.isAll) Int.MAX_VALUE else this.rowsPerPage,
                Sort.by(if (this.descending) Sort.Direction.DESC else Sort.Direction.ASC, sortBy ?: "id")
        )
    }
}
