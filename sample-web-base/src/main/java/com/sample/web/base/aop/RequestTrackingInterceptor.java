package com.sample.web.base.aop;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import com.sample.common.XORShiftRandom;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * 처리시간을 DEBUG 로그에 출력한다.
 */
@Slf4j
public class RequestTrackingInterceptor extends BaseHandlerInterceptor {

    private static final ThreadLocal<Long> startTimeHolder = new ThreadLocal<>();

    private static final String HEADER_X_TRACK_ID = "X-Track-Id";

    // 난수 생성기
    private final XORShiftRandom random = new XORShiftRandom();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 컨트롤러 동작 전

        // 현재 시각을 기록
        val beforeNanoSec = System.nanoTime();
        startTimeHolder.set(beforeNanoSec);

        // 추적 ID
        val trackId = getTrackId(request);
        MDC.put(HEADER_X_TRACK_ID, trackId);
        response.setHeader(HEADER_X_TRACK_ID, trackId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 처리 완료 후

        val beforeNanoSec = startTimeHolder.get();

        if (beforeNanoSec == null) {
            return;
        }

        val elapsedNanoSec = System.nanoTime() - beforeNanoSec;
        val elapsedMilliSec = NANOSECONDS.toMillis(elapsedNanoSec);
        log.info("path={}, method={}, Elapsed {}ms.", request.getRequestURI(), request.getMethod(), elapsedMilliSec);

        // 파기 한다.
        startTimeHolder.remove();
    }

    /**
     * 추적 ID를 획득한다.
     * 
     * @param request
     * @return
     */
    private String getTrackId(HttpServletRequest request) {
        String trackId = request.getHeader(HEADER_X_TRACK_ID);
        if (trackId == null) {
            int seed = Integer.MAX_VALUE;
            trackId = String.valueOf(random.nextInt(seed));
        }

        return trackId;
    }
}
