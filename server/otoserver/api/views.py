#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
import uuid
import md5
import math
import datetime
import logging
import random
from .models import Shop,Menu,Person,Address,OtoOrder

logger = logging.getLogger('django')
# Create your views here.
def index(request):
    print request.get_host()
    return HttpResponse(createOrderid())
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
        imagepath = "http://{0}/{1}".format(request.get_host(),shop.shopImage.__str__());
        shopDict={"shopid":shop.shopid,"shopName":shop.shopName,"shopImage":imagepath,
                "phoneNumber":shop.phoneNumber,"address":shop.address,"limitAmount":shop.limitAmount,
                "maxAmount":shop.maxAmount,"subtrackPrice":shop.subtrackPrice}
        shopList.append(shopDict)
    return responseJson(0,shopList)
def getMenuList(request):
    shopid = request.GET.get("shopid")
    shop = getShopById(shopid)
    if shop is not None:
        menuSet = Menu.objects.filter(shop=shop)
        menulist =[] 
        for menu in menuSet:
            imagepath = "http://{0}/{1}".format(request.get_host(),menu.menuImage.__str__());
            dict = {"menuId":menu.menuId,"menuName":menu.menuName,"menuImage":imagepath,"price":menu.price}
            menulist.append(dict)
            return responseJson(0,menulist)
    return responseJson(1,"please input shopid")
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
def getDefaultAddress(request):
    userid = request.GET.get("userid")
    user=getPersonById(userid)
    if user is not None:
        addressSet=Address.objects.filter(person=user).filter(status=1)
        for adr in addressSet:
            return responseJson(0,addressToDict(adr))
    return responseJson(1,"error")
def setDefaultAddress(request):
    userid = request.GET.get("userid")
    addressid = request.GET.get("addressid")
    user = getPersonById(userid)
    if user is not None:
        addressSet = Address.objects.filter(person=user)
        for adr in addressSet:
            print adr.addressid,addressid 
            if adr.addressid == int(addressid):
                adr.status=1
            else:
                adr.status=2
            adr.save()
    return responseJson(0,"update success")
def getShopById(shopId):
    shopSet = Shop.objects.filter(shopid=shopId)
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
    userid = request.GET.get("userid")
    shopid = request.GET.get("shopid")
    addressid = request.GET.get("addressid")
    menuids = request.GET.get("menus")
    order = OtoOrder()
    shop = getShopById(shopid)
    user = getPersonById(userid)
    address = getAddressById(addressid)
    if user is None or shop is None or address is None:
        return responseJson(1,"参数不合法")
    else:
        order.shop = shop
        order.person = user
        order.address=address
        order.orderNumber=createOrderid()
        order.orderStatus=1
        order.save()
        menulist=[]
        ms =menuids.split(",")
        for i in range(len(ms)-1):
            menu = getMenuById(ms[i])
            if menu is not None:
                menulist.append(menu)
            else:
                return responseJson(1,"menuids error")
        totalprice =0 
        for menu in menulist:
            totalprice=totalprice+menu.price
            order.menues.add(menu)
        if totalprice>=shop.maxAmount:
            totalprice = totalprice - shop.subtrackPrice
            order.favourable=shop.subtrackPrice
        order.price=totalprice
        return responseJson(0,"order")
    return responseJson(0,"")
def getOrderListByUser(request):
    userid = request.GET.get("userid")
    user = getPersonById(userid)
    if user is not None:
        orderList=[]
        orderSet = OtoOrder.objects.filter(person=user).order_by('-createTime')
        for order in orderSet:
            orderList.append(orderToDict(order))
        return responseJson(0,orderList)

def responseJson(code,data):
    dict={'code':code,'data':data}
    return HttpResponse(json.dumps(dict))
def getAddressById(addressid):
    try:
        return Address.objects.get(addressid=addressid)
    except Exception as e:
        return None
def orderToDict(otoOrder):
    return {"orderid":otoOrder.orderid,"orderNumber":otoOrder.orderNumber,"shopName":otoOrder.shop.shopName,
        "address":otoOrder.address,"price":otoOrder.price,"time":otoOrder.createTime,"status":otoOrder.orderStatus}
def getMenuById(menuId):
    try:
        return Menu.objects.get(menuId=menuId)
    except Exception as e:
        return None 
def getMD5(src):
    m = md5.new()
    m.update(src)
    return m.hexdigest()
def createOrderid():
    number = random.randint(1000,9999)
    time= datetime.datetime.now().strftime("%Y%m%d%H%I%S")
    return "%s%s"%(time,number)
def calcu_location(location_x, location_y, r=20):
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
