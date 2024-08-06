from django.shortcuts import render
from .forms import MarginCalculationForm

def calculate_margin(request):
    result = None
    result2 = None
    kariire = None
    total_deposit = None
    entry_price = None
    current_price = None
    position_amount = None
    mijitsugensoneki = None
    shyoukokin_ijiristu = None
    kariireDummy = None
    
    if request.method == 'POST':
        form = MarginCalculationForm(request.POST)
        if form.is_valid():
            position_value = form.cleaned_data['position_value']
            leverage = form.cleaned_data['leverage']
            
            if leverage <= 1:
                result = position_value
                kariireDummy = '0'
                kariire = kariireDummy
                
            else:
                result = position_value / leverage
                kariire = position_value-result
        
            
            
            total_deposit = form.cleaned_data['total_deposit']
            result2 = total_deposit
            
            entry_price = form.cleaned_data['entry_price']
            current_price = form.cleaned_data['current_price']
            
            position_amount = position_value/entry_price
            
            mijitsugensoneki = position_amount*(current_price-entry_price)
            
            if leverage <= 1:
                shyoukokin_ijiristu = ((total_deposit+mijitsugensoneki)/1)*100
            else:
                shyoukokin_ijiristu = ((total_deposit+mijitsugensoneki)/kariire)*100
            
    else:
        form = MarginCalculationForm()
    return render(request, 'calculate_margin.html', {'form': form, 'result': result, 'result2':result2, 'kariire':kariire, 'entry_price':entry_price, 'current_price':current_price, 'position_amount':position_amount, 'mijitsugensoneki':mijitsugensoneki,'shyoukokin_ijiristu':shyoukokin_ijiristu,'kariireDummy':kariireDummy})


