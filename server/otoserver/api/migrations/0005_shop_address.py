# -*- coding: utf-8 -*-
# Generated by Django 1.9.3 on 2016-03-06 00:41
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0004_auto_20160306_0837'),
    ]

    operations = [
        migrations.AddField(
            model_name='shop',
            name='address',
            field=models.CharField(default='', max_length=126, verbose_name='\u5730\u5740'),
        ),
    ]
