#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
import uuid
from .models import Shop,Menu,Person

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
    img = ImageStore(name = uuid.uuid1(), img =
    request.FILES['img'])  
    img.save()
    shop.shopImage=img
    shop.save()
    shop.latitude=request.POST.get('latitude')
    shop.lontitude=request.POST.get('lontitude')
    shop.subtrackPrice=request.POST.get('subtrackPrice')
    return responseJson(0,"add success")
def addMenu(request):
    menu = Menu()
    menu.menuName=request.POST.get('menu')
    shopid=request.POST.get('shopid')
    menu.price=request.POST.get('price')
    return responseJson(0,'add success')
def regist(request):
    person = Person()
    person.userName=request.GET.get('userName')
    person.password=request.GET.get('password')
    person.realName=request.GET.get('realName')
    person.phoneNumber=request.GET.get('phoneNumber')
    person.userType=request.GET.get('userType')
def getShopById(shopId):
    shop = Shop.obejcts.get(shopid=shopId)
    return shop
    
def responseJson(code,data):
    dict={'code':code,'data':data}
    return HttpResponse(json.dumps(dict))
