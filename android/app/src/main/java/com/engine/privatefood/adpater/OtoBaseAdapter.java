package com.engine.privatefood.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * Created by cat1412266 on 15/7/26.
 */
public abstract class OtoBaseAdapter<T> extends BaseAdapter{
  protected List<T> dataList ;
  protected Context mContext ;

  public OtoBaseAdapter(Context mContext, List<T> dataList)  {
    this.dataList = dataList;
    this.mContext = mContext;
  }

  @Override public int getCount() {
    if(dataList!=null){
    return dataList.size();
    }else{
      return 0;
    }
  }

  public void addList(List<T> list){
    this.dataList.addAll(list) ;
  }

  @Override public T getItem(int position) {
    return dataList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

}
