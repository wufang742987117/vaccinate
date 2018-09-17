package com.thinkgem.jeesite.modules.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	private int queueRefresh = 0;
	private int queueDelay = 30;
	private int inPosition = 1;
	private int fontSize = 14;
	private int signReq = 0;
	private int checkReq = 0;
	private int quickOption = 0;
	
	
	private List<vaccBatch> vaccBatch = new ArrayList<vaccBatch>();

	public int getQueueRefresh() {
		return queueRefresh;
	}

	public int getQueueDelay() {
		return queueDelay;
	}

	public int getInPosition() {
		return inPosition;
	}

	public int getFontSize() {
		return fontSize;
	}

	public List<vaccBatch> getVaccBatch() {
		return vaccBatch;
	}

	public void setQueueRefresh(int queueRefresh) {
		this.queueRefresh = queueRefresh;
	}

	public void setQueueDelay(int queueDelay) {
		this.queueDelay = queueDelay;
	}

	public void setInPosition(int inPosition) {
		this.inPosition = inPosition;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setVaccBatch(List<vaccBatch> vaccBatch) {
		this.vaccBatch = vaccBatch;
	}

	public int getSignReq() {
		return signReq;
	}

	public void setSignReq(int signReq) {
		this.signReq = signReq;
	}
	
	public int getCheckReq() {
		return checkReq;
	}

	public void setCheckReq(int checkReq) {
		this.checkReq = checkReq;
	}

	public int getQuickOption() {
		return quickOption;
	}
	
	@JsonIgnore
	public boolean isQuick(){
		return 1 == quickOption;
	}

	public void setQuickOption(int quickOption) {
		this.quickOption = quickOption;
	}

	/**
	 * 将默认批号转换为map
	 * @author fuxin
	 * @date 2017年10月11日 下午9:05:25
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<Map<String, String>> getVaccBatchMap() {
		List<Map<String, String>> returnMap = new ArrayList<Map<String,String>>();
		Map<String, String> tempMap;
		for(vaccBatch vb : vaccBatch){
			tempMap = new HashMap<String, String>();
			tempMap.put("vaccId", vb.getVaccId());
			tempMap.put("batch", vb.getBatch());
			returnMap.add(tempMap);
		}
		return returnMap;
	}

}

class vaccBatch {
	private String vaccId;
	private String batch;

	public String getVaccId() {
		return vaccId;
	}

	public void setVaccId(String vaccId) {
		this.vaccId = vaccId;
	}

	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
}
