package com.engine.privatefood.api;


import java.util.List;

/**
 * Created by cat1412266 on 15/7/26.
 */
public class FailResponse implements IBus<NetErrorModel> {
  public  NetErrorModel netErrorModel=null ;
  @Override public List<NetErrorModel> getListModel() {
    return null;
  }
  @Override public NetErrorModel getModel() {
    return netErrorModel;
  }
}
