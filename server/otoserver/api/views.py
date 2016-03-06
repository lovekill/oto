#! encoding=utf-8
from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def index(request):
    return HttpResponse("你好郴州,我是送外卖的");
