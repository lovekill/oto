from django.contrib import admin
from .models import Person,Shop,Menu,OtoOrder,Address

# Register your models here.
class PersonAdmin(admin.ModelAdmin):
    list_display=('userName','createTime')
class ShopAdmin(admin.ModelAdmin):
    list_display=('shopName','bossName','phoneNumber','createTime','shopImage','shopStatus')
class MenuAdmin(admin.ModelAdmin):
    list_display=('menuName','price','shop','createTime','menuImage','menuStatus')
class OrderAdmin(admin.ModelAdmin):
    list_display=('orderId','shop','person','orderStatus','createTime')
class AddressAdmin(admin.ModelAdmin):
    list_display=('person','addressName')
admin.site.register(Person,PersonAdmin)
admin.site.register(Shop,ShopAdmin)
admin.site.register(Menu,MenuAdmin)
admin.site.register(OtoOrder,OrderAdmin)
admin.site.register(Address,AddressAdmin)
