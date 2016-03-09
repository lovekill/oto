#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
import uuid
import md5
import math
from .models import Shop,Menu,Person

# Create your views here.
def index(request):
    return HttpResponse("hello 我是送外卖的")
def addShop(request):
    shop = Shop()
    shop.shopName=request.POST.get('shopName')
    shop.bossName=request.POST.get('bossName')
    shop.phoneNumber=request.POST.get('phoneNumber')
    shop.address=request.POST.get('address')
    shop.limitAmount=request.POST.get('limitAmount')
    shomaxAmount=request.POST.get('maxAmount')
    file_content = ContentFile(request.FILES['img'].read())  
    img = ImageStore(name = uuid.uuid1(), img =request.FILES['img'])  
    img.save()
    shop.shopImage=img
    shop.save()
    shop.latitude=request.POST.get('latitude')
    shop.lontitude=request.POST.get('lontitude')
    shop.subtrackPrice=request.POST.get('subtrackPrice')
    shop.save()
    return responseJson(0,"add success")
def addMenu(request):
    menu = Menu()
    menu.menuName=request.POST.get('menu')
    shopid=request.POST.get('shopid')
    menu.price=request.POST.get('price')
    shop = getShopById(shopid)
    if shop is None:
        return responseJson(1,'shop not exist')
    menu.shop=shop
    menu.save()
    return responseJson(0,'add success')
def regist(request):
    person = Person()
    person.userName=request.GET.get('userName')
    p = getPersonByName(person.userName)
    if p!=None:
        return responseJson(1,"该帐号已经注册")
    requestPassword=request.GET.get('password')
    person.password=getMD5(requestPassword)
    person.realName=request.GET.get('realName')
    person.phoneNumber=request.GET.get('phoneNumber')
    person.userType=request.GET.get('userType')
    person.save()
    return responseJson(0,'registSuccess')
def login(request):
    userName=request.GET.get('userName')
    password=request.GET.get('password')
    user = getPersonByName(userName)
    if user is not None:
        if getMD5(password) == user.password:
            dict={"userid":user.userid,"userName":user.userName,"realName":user.realName,"userTyp":user.userType,"phoneNumber":user.phoneNumber}
            return responseJson(0,dict)
        else:
            return responseJson(2,"userName or password error")
    return responseJson(1,"登陆失败")
def getShopList(request):
    latitude = request.GET.get('latitude')
    lontitude=request.GET.get('lontitude')
    rangeDict=calcu_location(latitude,lontitude)
    shopSet = Shop.objects.filter(shopStatus=1).filter(latitude__gte=rangeDict['location_x']['min']).filter(
            latitude__lte=rangeDict['location_x']['max']).filter(
                    lontitude__gte=rangeDict['location_y']['min']).filter(
                            lontitude__lte=rangeDict['location_y']['max'])
    shopList=[]
    for shop in shopSet:
        shopDict={"shopid":shop.shopid,"shopName":shop.shopName,"shopImage":shop.shopImage,
                "phoneNumber":shop.phoneNumber,"address":shop.address,"limitAmount":shop.limitAmount,
                "maxAmount":shop.maxAmount,"subtrackPrice":shop.subtrackPrice}
        shopList.append(shopDict)
    return responseJson(0,shopLis)
def getShopById(shopId):
    shopSet = Shop.obejcts.filter(shopid=shopId)
    if shopSet.count()==1:
        return shopSet.get(shopid=shopId)
    return None
def getPersonByName(name):
    personSet = Person.objects.filter(userName=name)
    if personSet.count()==1:
        return personSet.get(userName=name)
    return None
def generateOrder(request):
    return responseJson(0,"")
def responseJson(code,data):
    dict={'code':code,'data':data}
    return HttpResponse(json.dumps(dict))
def getMD5(src):
    m = md5.new()
    m.update(src)
    return m.hexdigest()
def calcu_location(location_x, location_y, r=2):
    lat_range = 180 / math.pi * r / 6372.797  # 里面的 1 就代表搜索 1km 之内，单位km
    long_r = lat_range / math.cos(location_x * math.pi / 180)
    max_lat = location_x + lat_range  # 最大纬度
    min_lat = location_x - lat_range  # 最小纬度
    max_long = location_y + long_r  # 最大经度
    min_long = location_y - long_r  # 最小经度
     
    range_xy = {}
    range_xy['location_x'] = {'min':min_lat, 'max':max_lat}
    range_xy['location_y'] = {'min':min_long, 'max':max_long}
    return range_xy
