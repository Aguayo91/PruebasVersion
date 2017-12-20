package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 10/10/2017.
 */

public class ObjectRequestsObtenCoordenada implements Serializable{

    @SerializedName("id")
    private String id;

    @SerializedName("headers")
    private String headers;

    @SerializedName("headerData")
    private List<ObjectHeaderData> headerData;

    @SerializedName("url")
    private String url;

    @SerializedName("queryParams")
    private List<Object> queryParams;

    @SerializedName("pathVariables")
    private List<Object> pathVariables;

    @SerializedName("pathVariableData")
    private List<Object> pathVariableData;

    @SerializedName("preRequestScript")
    private String preRequestScript;

    @SerializedName("method")
    private String method;

    @SerializedName("collectionId")
    private String collectionId;

    @SerializedName("data")
    private List<Object> data;

    @SerializedName("dataMode")
    private String dataMode;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("descriptionFormat")
    private String descriptionFormat;

    @SerializedName("time")
    private long time;

    @SerializedName("version")
    private int version;

    @SerializedName("responses")
    private List<Object> responses;

    @SerializedName("tests")
    private String tests;

    @SerializedName("currentHelper")
    private String currentHelper;

    @SerializedName("helperAttributes")
    private List<Object> helperAttributes;

    @SerializedName("rawModeData")
    private String rawModeData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public List<ObjectHeaderData> getHeaderData() {
        return headerData;
    }

    public void setHeaderData(List<ObjectHeaderData> headerData) {
        this.headerData = headerData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<Object> queryParams) {
        this.queryParams = queryParams;
    }

    public List<Object> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(List<Object> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public List<Object> getPathVariableData() {
        return pathVariableData;
    }

    public void setPathVariableData(List<Object> pathVariableData) {
        this.pathVariableData = pathVariableData;
    }

    public String getPreRequestScript() {
        return preRequestScript;
    }

    public void setPreRequestScript(String preRequestScript) {
        this.preRequestScript = preRequestScript;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getDataMode() {
        return dataMode;
    }

    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFormat() {
        return descriptionFormat;
    }

    public void setDescriptionFormat(String descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Object> getResponses() {
        return responses;
    }

    public void setResponses(List<Object> responses) {
        this.responses = responses;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getCurrentHelper() {
        return currentHelper;
    }

    public void setCurrentHelper(String currentHelper) {
        this.currentHelper = currentHelper;
    }

    public List<Object> getHelperAttributes() {
        return helperAttributes;
    }

    public void setHelperAttributes(List<Object> helperAttributes) {
        this.helperAttributes = helperAttributes;
    }

    public String getRawModeData() {
        return rawModeData;
    }

    public void setRawModeData(String rawModeData) {
        this.rawModeData = rawModeData;
    }

    @Override
    public String toString() {
        return "ObjectRequestsObtenCoordenada{" +
                "id='" + id + '\'' +
                ", headers='" + headers + '\'' +
                ", headerData=" + headerData +
                ", url='" + url + '\'' +
                ", queryParams=" + queryParams +
                ", pathVariables=" + pathVariables +
                ", pathVariableData=" + pathVariableData +
                ", preRequestScript='" + preRequestScript + '\'' +
                ", method='" + method + '\'' +
                ", collectionId='" + collectionId + '\'' +
                ", data=" + data +
                ", dataMode='" + dataMode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", descriptionFormat='" + descriptionFormat + '\'' +
                ", time=" + time +
                ", version=" + version +
                ", responses=" + responses +
                ", tests='" + tests + '\'' +
                ", currentHelper='" + currentHelper + '\'' +
                ", helperAttributes=" + helperAttributes +
                ", rawModeData='" + rawModeData + '\'' +
                '}';
    }
}
