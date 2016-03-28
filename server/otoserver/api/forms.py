from django import forms
from .models import Shop
class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=50) 
    file = forms.FileField()
class ShopForm(forms.ModelForm):
    class Meta:
        model=Shop
        fields=['shopImage']
