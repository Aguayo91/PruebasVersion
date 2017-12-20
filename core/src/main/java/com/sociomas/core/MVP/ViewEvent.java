package com.sociomas.core.MVP;
import com.sociomas.core.MVP.enums.ViewEventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 07/09/17.
 */

public class ViewEvent {
    public static final String ENTRY = "entry_object";
    public static final String ENTRIES_MAP = "map_entries";
    public static final String ENTRIES_ARRAY = "array_object";
    public static final String ENTRIES_LIST = "list_object";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String NEXT_VIEW_TAG = "next_view_tag";
    public static final String NEXT_VIEW = "next_view";
    public static final String RESOURCE_ID = "_resource_id";
    public static final String BOOLEAN_OBJECT = "boolean_object";
    public static final String ERROR = "error";
    public static final String ERROR_MSG = "error_msg";
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_TITLE = "error_title";
    public static final String ERROR_DESCRIPTION = "error_description";

    private ViewEventType eventType;
    private Map<String, Object> model;

    public ViewEvent(ViewEventType eventViewType) {
        this.eventType = eventViewType;
    }

    public ViewEventType getEventType() {
        return eventType;
    }

    public void setEventType(ViewEventType eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getModel() {
        if (model == null){
            model = new HashMap<>();
        }
        return model;
    }

    public Object getModel(String key) {
        return model.get(key);
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
