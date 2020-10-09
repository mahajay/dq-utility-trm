package com.dq.service.data;

import java.util.List;

public class DQCompareQueryData {

	private DQQueryData sourceData;
	private DQQueryData targetData;
	private List<Join> srcTgtJoins;
	private List<Where> srcTgtWheres;
	public DQQueryData getSourceData() {
		return sourceData;
	}
	public void setSourceData(DQQueryData sourceData) {
		this.sourceData = sourceData;
	}
	public DQQueryData getTargetData() {
		return targetData;
	}
	public void setTargetData(DQQueryData targetData) {
		this.targetData = targetData;
	}
	public List<Join> getSrcTgtJoins() {
		return srcTgtJoins;
	}
	public void setSrcTgtJoins(List<Join> srcTgtJoins) {
		this.srcTgtJoins = srcTgtJoins;
	}
	public List<Where> getSrcTgtWheres() {
		return srcTgtWheres;
	}
	public void setSrcTgtWheres(List<Where> srcTgtWheres) {
		this.srcTgtWheres = srcTgtWheres;
	}
	@Override
	public String toString() {
		return "DQCompareQueryData [sourceData=" + sourceData + ", targetData=" + targetData + ", srcTgtJoins="
				+ srcTgtJoins + ", srcTgtWheres=" + srcTgtWheres + "]";
	}
	
}
