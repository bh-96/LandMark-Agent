package com.landmark.agent.scheduler;

import com.landmark.agent.model.domain.TourInfo;
import com.landmark.agent.model.dto.request.TourApiRequest;
import com.landmark.agent.model.dto.response.detailCommon.DetailCommon;
import com.landmark.agent.model.dto.response.detailCommon.DetailCommonResponse;
import com.landmark.agent.model.repository.TourInfoRepository;
import com.landmark.agent.service.TourApiService;
import com.landmark.agent.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TourInfoScheduler extends LoggerUtils {

    private TourApiService tourApiService;
    private TourInfoRepository tourInfoRepository;

    @Autowired
    public TourInfoScheduler(TourApiService tourApiService, TourInfoRepository tourInfoRepository) {
        this.tourApiService = tourApiService;
        this.tourInfoRepository = tourInfoRepository;
    }

    // 매일 새벽 1시에 추가할 관광지 정보가 있으면 디비에 저장
    @Scheduled(cron = "0 0 1 * * ?")
    public void addTourInfoFromTourAPI() {
        try {
            tourApiService.saveTourInfoFromTourAPI();
        } catch (Exception e) {
            logger.error("addTourInfoFromTourAPI : " + e.getMessage());
        }
    }

    // 매일 새벽 1시에 tel, homepage, overview 500개씩 저장
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateTourInfoFromTourAPI() {
        try {
            Pageable pageable = PageRequest.of(0, 500, Sort.by("id").ascending());
            List<TourInfo> tourInfos = tourInfoRepository.findAllByTelIsNullAndHomepageIsNullAndOverviewIsNull(pageable).getContent();

            if (!tourInfos.isEmpty()) {
                for (TourInfo tourInfo : tourInfos) {
                    TourApiRequest tourApiRequest = new TourApiRequest();
                    tourApiRequest.setDefaultYn("Y");
                    tourApiRequest.setContentId(tourInfo.getContentId());

                    DetailCommonResponse detailCommonResponse = tourApiService.detailCommon(tourApiRequest);

                    if (detailCommonResponse.getPageInfo() != null) {
                        DetailCommon detailCommon = detailCommonResponse.getDetailCommon().get(0);
                        tourInfo.setHomepage(detailCommon.getHomepage() != null ? detailCommon.getHomepage() : "");
                        tourInfo.setOverview(detailCommon.getOverview() != null ? detailCommon.getOverview() : "");
                        tourInfo.setTel(detailCommon.getTel() != null ? detailCommon.getTel() : "");
                    }
                }

                tourInfoRepository.saveAll(tourInfos);
            }

            logger.info("updateTourInfoFromTourAPI count = " + tourInfos.size());
        } catch (Exception e) {
            logger.error("updateTourInfoFromTourAPI : " + e.getMessage());
        }
    }

}
