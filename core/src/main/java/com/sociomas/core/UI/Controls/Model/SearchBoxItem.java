package com.sociomas.core.UI.Controls.Model;
import java.io.Serializable;
/**
 * Created by oemy9 on 24/05/2017.
 */

@SuppressWarnings("unused")
public class SearchBoxItem implements Serializable {
    private String id;
    private String value;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SearchBoxItem(String id,String value) {
        this.id = id;
        this.value=value;
    }
}