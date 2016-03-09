package com.engine.privatefood.api;

import java.util.List;

/**
 * Created by cat1412266 on 15/7/26.
 */
public interface IBus<T> {
  List<T> getListModel();
  T getModel();
}
