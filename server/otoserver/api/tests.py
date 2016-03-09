#!encoding=utf-8
from django.test import TestCase,Client
from models import Person
from . import views
import json

# Create your tests here.
class ApiTestCase(TestCase):
    def setUp(self):
        Person.objects.create(userName="engineli",password="123456",realName="李红波",phoneNumber="13268236246",userType=1)  
    def test_person_get(self):
        engine=Person.objects.get(userName='engineli')
        self.assertEqual(engine.userName,'engineli')
    def test_http_init(self):
        c = Client()
        response=c.get('/oto/index')
    def test_regist_user(self):
        c = Client()
        response = c.get('/oto/regist?userName=cheng&password=12345&realName=Jone&phoneNumber=13268236246&userType=1')
        jsonData=json.loads(response.content)
        person=views.getPersonByName('cheng')
        self.assertEqual(person.realName,'Jone')
        self.assertEqual(jsonData['code'],0)
    def test_login_user(self):
        self.test_regist_user()
        c=Client()
        response = c.get('/oto/login?userName=cheng&password=12345')
        jsonData = json.loads(response.content)
        self.assertEqual(jsonData['code'],0)
    def test_calcu_lacation(self):
        dict= views.calcu_location(-30.376393,-114.33879)
        print dict['location_x']['min']
