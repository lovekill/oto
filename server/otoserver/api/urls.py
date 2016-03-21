from django.conf.urls import url
from . import views
from . import businissApi
urlpatterns=[
            url(r'^index$',views.index,name='index'),
            url(r'regist$',views.regist,name='regist'),
            url(r'^login$',views.login,name='login'),
            url(r'^addshop$',businissApi.addShop,name='addShop'),
            url(r'^addmenu$',views.addMenu,name="addMenu"),
            url(r'^getshopList$',views.getShopList,name="getShopList"),
            url(r'^generateorder$',views.generateOrder,name="generateOrder"),
            url(r'^addAddress$',views.addAddress,name="addAddress"),
            url(r'^getAddressList$',views.getAddressList,name="getAddressList"),
            url(r'^getMenuList$',views.getMenuList,name="getMenuList"),
            url(r'^getDefaultAddress$',views.getDefaultAddress,name="getDefaultAddress"),
            url(r'^setDefaultAddress$',views.setDefaultAddress,name="setDefaultAddress"),
            url(r'^generatorOrder$',views.generateOrder,name="generateOrder"),
            url(r'^getOrderListByUser$',views.getOrderListByUser,name="getOrderListByUser"),
            url(r"^getOrderDetail$",views.getOrderDetail,name="getOrderDetail")
        ]
