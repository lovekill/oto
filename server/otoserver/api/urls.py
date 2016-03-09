from django.conf.urls import url
from . import views
urlpatterns=[
            url(r'^index$',views.index,name='index'),
            url(r'regist$',views.regist,name='regist'),
            url(r'^login$',views.login,name='login'),
            url(r'^addshop$',view.addShop,name='addShop'),
            url(r'^addmenu$',view.addMenu,name="addMenu"),
            url(r'^getshopList$',view.getShopList,name="getShopList"),
            url(r'^generateorder$',view.generateOrder,name="generateOrder")
        ]
