package com.teamplay.domain.database.match.entity

enum class MatchStyle(val value: String) {
    THREE_HALF_COURT("3대 3 반코드"),
    FIVE_HALF_COURT("5대 5 반코드"),
    THREE_FULL_COURT("3대 3 풀코드"),
    FIVE_FULL_COURT("5대 5 풀코드");

    override fun toString(): String {
        return value
    }

    fun getRule(): String {
        return when(this) {
            THREE_HALF_COURT -> "일반 농구 규칙상으로는 경기 시간은 4개 쿼터(Quarter)로 10분씩 갈라지며, 1-2쿼터 사이와 3-4쿼터 사이는 2분, 2-3쿼터 사이(하프타임)에는 15분 쉰다. 중등부는 쿼터당 8분, 초등부는 전후반 각 15분으로 플레이한다."
            FIVE_HALF_COURT -> "일반 농구 규칙상으로는 경기 시간은 4개 쿼터(Quarter)로 10분씩 갈라지며, 1-2쿼터 사이와 3-4쿼터 사이는 2분, 2-3쿼터 사이(하프타임)에는 15분 쉰다. 중등부는 쿼터당 8분, 초등부는 전후반 각 15분으로 플레이한다."
            THREE_FULL_COURT -> "일반 농구 규칙상으로는 경기 시간은 4개 쿼터(Quarter)로 10분씩 갈라지며, 1-2쿼터 사이와 3-4쿼터 사이는 2분, 2-3쿼터 사이(하프타임)에는 15분 쉰다. 중등부는 쿼터당 8분, 초등부는 전후반 각 15분으로 플레이한다."
            FIVE_FULL_COURT -> "일반 농구 규칙상으로는 경기 시간은 4개 쿼터(Quarter)로 10분씩 갈라지며, 1-2쿼터 사이와 3-4쿼터 사이는 2분, 2-3쿼터 사이(하프타임)에는 15분 쉰다. 중등부는 쿼터당 8분, 초등부는 전후반 각 15분으로 플레이한다."
        }
    }
}
