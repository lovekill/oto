#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
import uuid
import md5
import math
import logging
from .models import Shop,Menu,Person,Address

logger = logging.getLogger('django')
# Create your views here.
def index(request):
    logger.info("我只是一个测试")
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
    if p is not None:
        return responseJson(1,"该帐号已经注册")
    requestPassword=request.GET.get('password')
    person.password=getMD5(requestPassword)
    person.userType=request.GET.get('userType')
    person.save()
    dict=userToDict(person)
    return responseJson(0,dict)
def login(request):
    userName=request.GET.get('userName')
    password=request.GET.get('password')
    user = getPersonByName(userName)
    if user is not None:
        if getMD5(password) == user.password:
            dict = userToDict(user)
            return responseJson(0,dict)
        else:
            return responseJson(2,"userName or password error")
    return responseJson(1,"登陆失败")
def getShopList(request):
    latitude = request.GET.get('latitude')
    lontitude=request.GET.get('lontitude')
    rangeDict=calcu_location(float(latitude),float(lontitude))
    shopSet = Shop.objects.filter(shopStatus=1).filter(latitude__gte=rangeDict['location_x']['min']).filter(
            latitude__lte=rangeDict['location_x']['max']).filter(
                    lontitude__gte=rangeDict['location_y']['min']).filter(
                            lontitude__lte=rangeDict['location_y']['max'])
    shopList=[]
    for shop in shopSet:
        imagepath = "http://api.grayweb.cn:8061/{0}".format(shop.shopImage.__str__());
        shopDict={"shopid":shop.shopid,"shopName":shop.shopName,"shopImage":imagepath,
                "phoneNumber":shop.phoneNumber,"address":shop.address,"limitAmount":shop.limitAmount,
                "maxAmount":shop.maxAmount,"subtrackPrice":shop.subtrackPrice}
        shopList.append(shopDict)
    return responseJson(0,shopList)
def addAddress(request):
    name = request.GET.get("name")
    phoneNumber = request.GET.get("phoneNumber")
    address = request.GET.get("address")
    userid=request.GET.get("userid")
    user = getPersonById(userid)
    adr=Address()
    if user is not None:
        adr.addressName=address
        adr.phoneNumber=phoneNumber
        adr.userName=name
        adr.person=user
        adr.save()
        dict = addressToDict(adr)
        return responseJson(0,dict)
    else:
        return responseJson(1,"请先登录")
def getAddressList(request):
    userid = request.GET.get("userid")
    user = getPersonById(userid)
    if user is not None:
        addressSet = Address.objects.filter(person=user)
        addressList =[]
        for address in addressSet:
            addressList.append(addressToDict(address))
        return responseJson(0,addressList)
    else:
        return responseJson(0,"请先登录")

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
def getPersonById(userid):
    try:
        return Person.objects.get(userid=userid)
    except Exception as e:
        logger.info(e)
        return None
def userToDict(user):
    dict={"userid":user.userid,"userName":user.userName,"userType":user.userType}
    return dict
def addressToDict(adr):
    dict = {"addressid":adr.addressid,"name":adr.userName,"phoneNumber":adr.phoneNumber,"address":adr.addressName}
    return dict

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
