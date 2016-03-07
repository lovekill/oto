#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
from .models import Shop,Menu

# Create your views here.
def index(request):
    return HttpResponse("hello 我是送外卖的")
def getShopList(request):
    shoplist=[]
    for shop in Shop.objects.all():
        dict={}
        dict['shopid']=shop.shopid
        dict['shopName']=shop.shopName
        dict['image']=shop.shopImage.__str__()
        dict['maxAmount']=shop.maxAmount
        dict['limitAmount']=shop.limitAmount
        dict['subtrackPrice'] = shop.subtrackPrice
        dict['phoneNumber'] =shop.phoneNumber
        dict['address'] =shop.address
        shoplist.append(dict)
        return responseJson(0,shoplist)
def addShop(request):
    shop = Shop()
    shop.shopName=request.POST.get('shopName')
    shop.bossName=request.POST.get('bossName')
    shop.phoneNumber=request.POST.get('phoneNumber')
    shop.address=request.POST.get('address')
    shop.limitAmount=request.POST.get('limitAmount')
    shomaxAmount=request.POST.get('maxAmount')
    file_content = ContentFile(request.FILES['img'].read())  
    img = ImageStore(name = request.FILES['img'].name, img =
    request.FILES['img'])  
    img.save()

def responseJson(code,data):
    dict={'code':code,'data':data}
    return HttpResponse(json.dumps(dict))
