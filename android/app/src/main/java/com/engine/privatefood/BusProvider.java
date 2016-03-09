package com.engine.privatefood;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by cat1412266 on 15/7/26.
 */
public class BusProvider {
  private static Bus bus ;
  public static Bus getBus(){
    if(bus==null){
      bus = new Bus(ThreadEnforcer.ANY);
    }
    return bus;
  }
}
