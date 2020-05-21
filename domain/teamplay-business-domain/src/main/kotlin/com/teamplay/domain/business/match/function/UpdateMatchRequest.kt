package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import org.springframework.transaction.annotation.Transactional

class UpdateMatchRequest(
    private val matchRepository: MatchRepository
): Function<MatchRequest, Match> {
    /*
    https://blog.jetbrains.com/idea/2018/10/spring-and-kotlin-final-classes/
    https://github.com/Kotlin/kotlin-examples/issues/72
    코틀린은 기본적으로 class 들이 final 이어서 발생하는 오류라고 하네요. 오류? 라기보다는 경고 같은 느낌인가봐요.
    all-open plugin 이 intellij 에 있다고 하네요.
    무튼 첫번째 링크처럼 @Transactional 과 같이 확장 가능한 어노테이션을 사용하게 될 때 인텔리제이에서
    class could be implicitly subclassed and must not be final
    위 오류가 출력되네요. open 없는채로 실행은 되기는 하는데.. 그래도 잡는게 맞다고 생각해요. 우선은 이야기 한번 해봐요.
    */
    @Transactional
    override fun apply(matchRequest: MatchRequest): Match {
        return matchRepository.getByMatchRequests(matchRequest).let { match ->
            if (matchRequest.matchRequestStatus == MatchRequestStatus.ACCEPT) {
                match.matchStatus = MatchStatus.CLOSE

                match.matchRequests?.map{
                    if (it.id != matchRequest.id) {
                        it.matchRequestStatus = MatchRequestStatus.REJECT
                    }
                }
            }
            match.matchRequests?.indexOf(matchRequest)?.let {
                match.matchRequests!!.set(it, matchRequest)
            }

            match
        }
    }

}
