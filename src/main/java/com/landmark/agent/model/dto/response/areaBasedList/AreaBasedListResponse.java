package com.landmark.agent.model.dto.response.areaBasedList;

import com.landmark.agent.model.dto.Header;
import com.landmark.agent.model.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class AreaBasedListResponse {

    private Header header;
    private List<AreaBasedList> areaBasedList;
    private PageInfo pageInfo;

}
