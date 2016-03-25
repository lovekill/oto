#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.shortcuts import render
from django.core.files.base import ContentFile
from django.http import HttpResponse
from django.utils import timezone
from .models import Shop,Person,OrderDetail,OtoOrder,Address,Menu
from . import views
import logging
logger = logging.getLogger('django')
def addShop(request):
    print "eeeee"
    return views.responseJson(0,"test")
def addShopa(request):
    logger.info("add shop begin")
    for a,b in request.POST:
        print a,b
    shop = Shop()
    shop.shopName=request.POST.get('shopName')
    shop.bossName=request.POST.get('bossName')
    shop.phoneNumber=request.POST.get('phoneNumber')
    shop.address=request.POST.get('address')
    shop.limitAmount=request.POST.get('limitAmount')
    shomaxAmount=request.POST.get('maxAmount')
#    file_content = ContentFile(request.FILES['file0'].read())  
#    img = ImageStore(name = uuid.uuid1(), img =request.FILES['file0'])  
#    img.save()
#    shop.shopImage=img
#    shop.save()
    shop.latitude=request.POST.get('latitude')
    shop.lontitude=request.POST.get('lontitude')
    shop.subtrackPrice=request.POST.get('subtrackPrice')
#    shop.save()
    return views.responseJson(0,"add success")

