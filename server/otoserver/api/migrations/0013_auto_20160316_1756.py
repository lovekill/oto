# -*- coding: utf-8 -*-
# Generated by Django 1.9.3 on 2016-03-16 09:56
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0012_auto_20160316_1624'),
    ]

    operations = [
        migrations.AlterField(
            model_name='otoorder',
            name='createTime',
            field=models.DateTimeField(auto_now=True, verbose_name='\u521b\u5efa\u65f6\u95f4'),
        ),
        migrations.AlterField(
            model_name='otoorder',
            name='orderid',
            field=models.AutoField(primary_key=True, serialize=False),
        ),
    ]