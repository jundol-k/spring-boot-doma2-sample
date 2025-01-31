package com.sample.domain.util;

import org.seasar.doma.jdbc.SelectOptions;

import com.sample.domain.dto.common.Pageable;

/**
 * 도마 관련 유틸리티
 */
public class DomaUtils {

    /**
     * SearchOptions를 작성해서 반환한다.
     *
     * @return
     */
    public static SelectOptions createSelectOptions() {
        return SelectOptions.get();
    }

    /**
     * SearchOptions를 작성해서 반환한다.
     *
     * @param pageable
     * @return
     */
    public static SelectOptions createSelectOptions(Pageable pageable) {
        int page = pageable.getPage();
        int perpage = pageable.getPerpage();
        return createSelectOptions(page, perpage);
    }

    /**
     * SearchOptions를 작성해서 반환한다.
     *
     * @param page
     * @param perpage
     * @return
     */
    public static SelectOptions createSelectOptions(int page, int perpage) {
        int offset = (page - 1) * perpage;
        return SelectOptions.get().offset(offset).limit(perpage);
    }
}
