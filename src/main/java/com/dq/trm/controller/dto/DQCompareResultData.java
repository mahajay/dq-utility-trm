package com.dq.trm.controller.dto;

import java.util.Map;

import com.dq.config.datasource.JOIN_TYPE;
import com.dq.service.data.DQResultData;

public class DQCompareResultData {

	private Map<JOIN_TYPE, DQResultData> resultData;

	public Map<JOIN_TYPE, DQResultData> getResultData() {
		return resultData;
	}

	public void setResultData(Map<JOIN_TYPE, DQResultData> resultData) {
		this.resultData = resultData;
	}
	
}
