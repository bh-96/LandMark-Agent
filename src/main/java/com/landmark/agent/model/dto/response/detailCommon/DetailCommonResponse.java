package com.landmark.agent.model.dto.response.detailCommon;

import com.landmark.agent.model.dto.Header;
import com.landmark.agent.model.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class DetailCommonResponse {

    private Header header;
    private List<DetailCommon> detailCommon;
    private PageInfo pageInfo;

}