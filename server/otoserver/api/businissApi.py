#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.shortcuts import render
from django.core.files.base import ContentFile
from django.http import HttpResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from .models import Shop,Person,OrderDetail,OtoOrder,Address,Menu
from . import views
from .forms import UploadFileForm,ShopForm
import logging
logger = logging.getLogger('django')
@csrf_exempt
def addShop(request):
    userid =request.POST.get('userid')
    user = views.getPersonById(userid)
    if user is None:
        return views.responseJson(1,"用户名为空")
    shop = Shop(shopImage=request.FILES['file0'])
    shop.bossName=user.userName
    shop.shopName=request.POST.get('shopName')
    shop.phoneNumber=request.POST.get('phoneNumber')
    shop.address=request.POST.get('address')
    shop.limitAmount=request.POST.get('limitAmount')
    shomaxAmount=request.POST.get('maxAmount')
    shop.latitude=request.POST.get('latitude')
    shop.lontitude=request.POST.get('lontitude')
    shop.subtrackPrice=request.POST.get('subtrackPrice')
    try:
        shop.save()
    except Exception as e:
        print e.message
    #shop.save()
    return views.responseJson(0,shop.shopImage.__str__())

