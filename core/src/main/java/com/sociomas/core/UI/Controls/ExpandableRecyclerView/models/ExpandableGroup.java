package com.sociomas.core.UI.Controls.ExpandableRecyclerView.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The backing data object for an {@link ExpandableGroup}
 */
public class ExpandableGroup<T extends Parcelable> implements Parcelable {
  private Map<String, T> map;
  private String title;
  private List<T> items;

  public ExpandableGroup(String title, List<T> items) {
    this.title = title;
    this.items = items;
  }

  public ExpandableGroup(String title, Map<String, T> map) {
    this.title = title;
    this.map = (HashMap)map;
  }

  public String getTitle() {
    return title;
  }

  public List<T> getItems() {
    return items;
  }

  public int getItemCount() {
    return items == null ? 0 : items.size();
  }

  @Override
  public String toString() {
    return "ExpandableGroup{" +
        "title='" + title + '\'' +
        ", items=" + items +
        '}';
  }

  protected ExpandableGroup(Parcel in) {
    title = in.readString();
    byte hasItems = in.readByte();
    int size = in.readInt();
    if (hasItems == 0x01) {
      items = new ArrayList<T>(size);
      Class<?> type = (Class<?>) in.readSerializable();
      in.readList(items, type.getClassLoader());
    } else if (hasItems == 0x02){
      Class<?> type = (Class<?>) in.readSerializable();
      in.readMap(map, type.getClassLoader());
    } else {
      items = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    if (items == null) {
      dest.writeByte((byte) (0x00));
      dest.writeInt(0);
    } else if (items != null){
      dest.writeByte((byte) (0x01));
      dest.writeInt(items.size());
      final Class<?> objectsType = items.get(0).getClass();
      dest.writeSerializable(objectsType);
      dest.writeList(items);
    } else {
      dest.writeByte((byte) (0x02));
      dest.writeInt(map.size());
      final Class<?> objects = map.get(title).getClass();
      dest.writeSerializable(objects);
      dest.writeMap(map);
    }
  }

  public Map<String, T> getMap() {
    return (Map<String, T>) map;
  }

  public void setMap(Map<String, T> map) {
    this.map = map;
  }

  public int getMapCount(){
    return map != null ? map.size() : 0;
  }

  @SuppressWarnings("unused")
  public static final Creator<ExpandableGroup> CREATOR =
      new Creator<ExpandableGroup>() {
        @Override
        public ExpandableGroup createFromParcel(Parcel in) {
          return new ExpandableGroup(in);
        }

        @Override
        public ExpandableGroup[] newArray(int size) {
          return new ExpandableGroup[size];
        }
      };
}
