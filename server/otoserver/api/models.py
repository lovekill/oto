#!encoding=utf-8
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
# Create your models here.
class Person(models.Model):
    userid=models.AutoField(primary_key=True)
    userName=models.CharField('用户名',max_length=16)
    password=models.CharField('密码',max_length=60)
    realName=models.CharField('姓名',max_length=60,default='')
    phoneNumber=models.CharField('电话',max_length=13)
    createTime=models.DateTimeField("创建时间",auto_now_add=True,editable=False)
    def __str__(self):
        return self.userName
class Shop(models.Model):
    STATUS=((1,'正常营业'),(2,'禁止营业'))
    shopid=models.AutoField(primary_key=True)
    shopName = models.CharField("店名",max_length=32,null=False)
    bossName=models.CharField("老板名字",max_length=32,null=False)
    shopImage=models.ImageField("照片",upload_to="image/shop")
    phoneNumber=models.CharField("电话",max_length=40,null=False)
    createTime=models.DateTimeField("创建时间",auto_now_add=True,editable=False)
    shopStatus=models.IntegerField('状态',choices=STATUS,default=2)
    latitude=models.FloatField('纬度',default=0)
    lontitude=models.FloatField('经度',default=0)
    address=models.CharField('地址',max_length=126,default='')
    limitAmount=models.FloatField('最小金额起送',default=20)
    maxAmount=models.FloatField('满减金额',default=0)
    subtrackPrice=models.FloatField('减去金额',default=0)
    def __str__(self):
        return self.shopName
class Menu(models.Model):
    STATUS=((1,'正常'),(2,'下架'))
    menuId = models.AutoField(primary_key=True)
    menuName=models.CharField("菜名",max_length=40)
    price=models.FloatField("价格",default=0.0)
    shop=models.ForeignKey(Shop)
    menuImage=models.ImageField("照片",upload_to="image/menu")
    menuStatus=models.IntegerField('状态',choices=STATUS,default=1)
    createTime=models.DateTimeField("创建时间",auto_now_add=True,editable=False)
    def __str__(self):
        return self.menuName

class Address(models.Model):

    """Docstring for Address. """
    addressid=models.AutoField(primary_key=True)
    addressName=models.CharField('地址',max_length=200)
    person=models.ForeignKey(Person)
    createTime=models.DateTimeField("创建时间",auto_now_add=True,editable=False)
    def __str__(self):
        return self.addressName

class OtoOrder(models.Model):
    STATUS=((1,"已下单"),(2,'已接单'),(3,'已经送出'),(4,'已经完成'))
    orderId=models.CharField(max_length=32,null=False)
    shop=models.ForeignKey(Shop)
    person=models.ForeignKey(Person)
    menues=models.ManyToManyField(Menu)
    price=models.FloatField('总价',default=0)
    favourable=models.FloatField('优惠金额',default=0)
    createTime=models.DateTimeField("创建时间",auto_now_add=True,editable=False)
    orderStatus=models.IntegerField('订单状态',choices=STATUS,default=1)

         
