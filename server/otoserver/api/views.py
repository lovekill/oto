#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from django.core import serializers
import json
import uuid
import md5
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
    img = ImageStore(name = uuid.uuid1(), img =request.FILES['img'])  
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
    p = getPersonByName(person.userName)
    if p!=None:
        return responseJson(1,"该帐号已经注册")
    requestPassword=request.GET.get('password')
    person.password=md5.new().update(requestPassword)
    person.realName=request.GET.get('realName')
    person.phoneNumber=request.GET.get('phoneNumber')
    person.userType=request.GET.get('userType')
    return responseJson(0,'registSuccess')
def login(request):
    userName=request.GET.get('userName')
    password=request.GET.get('password')
    user = getPersonByName(userName)
    if user!=None:
        if md5.new().update(password) == user.password:
            dict={"userid":user.userid,"userName":user.userName,"realName":user.realName,"userTyp":user.userType,"phoneNumber":user.phoneNumber}
            return responseJson(0,dict)
    return responseJson(1,"登陆失败")

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
    
def responseJson(code,data):
    dict={'code':code,'data':data}
    return HttpResponse(json.dumps(dict))
