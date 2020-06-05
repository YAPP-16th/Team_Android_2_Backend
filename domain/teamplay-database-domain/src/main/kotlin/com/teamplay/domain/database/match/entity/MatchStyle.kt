package com.teamplay.domain.database.match.entity

enum class MatchStyle(val value: String) {
    THREE_HALF_COURT("3대3 반코트"),
    FIVE_HALF_COURT("5대5 반코트"),
    FIVE_FULL_COURT("5대5 풀코트"),
    ETC("기타");

    override fun toString(): String {
        return value
    }
}
