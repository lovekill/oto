# -*- coding: utf-8 -*-
# Generated by Django 1.9.3 on 2016-03-17 09:57
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0013_auto_20160316_1756'),
    ]

    operations = [
        migrations.AlterField(
            model_name='otoorder',
            name='createTime',
            field=models.DateTimeField(verbose_name='\u521b\u5efa\u65f6\u95f4'),
        ),
        migrations.AlterField(
            model_name='otoorder',
            name='orderNumber',
            field=models.CharField(max_length=32, verbose_name='\u8ba2\u5355\u53f7'),
        ),
    ]
