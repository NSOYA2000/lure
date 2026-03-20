package com.lurefish.repository;

import com.lurefish.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    // 可以添加自定义查询方法
    // 例如：按难度等级查找
    // List<Fish> findByDifficulty(String difficulty);
}