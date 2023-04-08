package com.together.domain.subscribe;

import com.together.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    @Modifying
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(@Param("principalId") int principalId, @Param("pageUserId") int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
    int mSubscribeCount(@Param("pageUserId") int pageUserId);

    void deleteAllByFromUserId(int fromUserId);
    void deleteAllByToUserId(int toUserId);

    /* 내가 구독한 사용자 */
    @Query(value = "SELECT toUserId FROM subscribe WHERE fromUserId = :principalId", nativeQuery = true)
    List<Integer> findSubscribeFrom(int principalId);

    /* 나를 구독한 사용자 */
    @Query(value = "SELECT fromUserId FROM subscribe WHERE toUserId = :principalId", nativeQuery = true)
    List<Integer> findSubscribeTo(int principalId);
}