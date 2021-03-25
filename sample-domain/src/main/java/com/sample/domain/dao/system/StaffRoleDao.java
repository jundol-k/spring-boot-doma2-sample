package com.sample.domain.dao.system;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import com.sample.domain.dto.system.Permission;
import com.sample.domain.dto.system.PermissionCriteria;
import com.sample.domain.dto.system.StaffCriteria;
import com.sample.domain.dto.system.StaffRole;

@ConfigAutowireable
@Dao
public interface StaffRoleDao {

    /**
     * 담당자 권한 조회
     *
     * @param staffCriteria
     * @param permissionCriteria
     * @param options
     * @return
     */
    @Select(strategy = SelectType.COLLECT)
    <R> R selectAll(final StaffCriteria staffCriteria, final PermissionCriteria permissionCriteria,
            final SelectOptions options, final Collector<StaffRole, ?, R> collector);

    /**
     * 스태프 아이디를 이용해 담당자 권한 조회
     * 
     * @param id
     * @param collector
     * @param <R>
     * @return
     */
    @Select(strategy = SelectType.COLLECT)
    <R> R selectByStaffId(Long id, final Collector<StaffRole, ?, R> collector);

    /**
     * 담당자 고유번호를 이용해 담당자 조회.
     *
     * @param id
     * @return
     */
    @Select
    Optional<Permission> selectById(Long id);

    /**
     * 담당자 단 건 조회
     *
     * @param criteria
     * @return
     */
    @Select
    Optional<Permission> select(PermissionCriteria criteria);

    /**
     * 담당자 등록.
     *
     * @param staffRole
     * @return
     */
    @Insert(exclude = { "roleName", "permissionKey", "permissionName" })
    int insert(StaffRole staffRole);

    /**
     * 담당자 갱신
     *
     * @param staffRole
     * @return
     */
    @Update(exclude = { "roleName", "permissionKey", "permissionName" })
    int update(StaffRole staffRole);

    /**
     * 담당자 삭제
     *
     * @param staffRole
     * @return
     */
    @Update(excludeNull = true) // NULL 항목은 갱신대상으로 하지 않는다.
    int delete(StaffRole staffRole);

    /**
     * 담당자 일괄 등록
     *
     * @param staffRoles
     * @return
     */
    @BatchInsert(exclude = { "roleName", "permissionKey", "permissionName" })
    int[] insert(List<StaffRole> staffRoles);
}
