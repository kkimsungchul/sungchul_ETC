package com.sungchul.etc.sql.vo;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Query 파라메터", description = "Query 파라메터 VO")
public class QueryParamVO
{
	@ApiModelProperty(name = "sql", value = "Query 파라메터", dataType = "String", required = true, example = "select * from git_lab_project where git_lab_id = :gitLabId and git_project_id = :gitProjectId")
	private String sql;

	@ApiModelProperty(name = "params", value = "Query 파라메터", dataType = "Map", example = "{\"gitLabId\": 85 , \"gitProjectId\": \"215\"}")
	private Map<String, Object> params;

	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
