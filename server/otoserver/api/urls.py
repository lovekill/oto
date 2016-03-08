from django.conf.urls import url
from . import views
urlpatterns=[
            url(r'^index$',views.index,name='index'),
            url(r'regist$',views.regist,name='regist'),
            url(r'login',views.login,name='login'),
        ]
