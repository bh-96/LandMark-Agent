package com.landmark.agent.model.repository;

import com.landmark.agent.model.domain.TourInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourInfoRepository extends JpaRepository<TourInfo, Integer> {

    Page<TourInfo> findAllByTelIsNullAndHomepageIsNullAndOverviewIsNull(Pageable pageable);

}
